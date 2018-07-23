package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView description_tv, alsoKnownAsTv, ingredients_tv, origin_tv;
    private ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);

        description_tv = findViewById(R.id.description_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        ingredients_tv = findViewById(R.id.ingredients_tv);
        origin_tv = findViewById(R.id.origin_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
            return;
        }

        populateUI(sandwich.getAlsoKnownAs(), sandwich.getPlaceOfOrigin(), sandwich.getDescription(), sandwich.getIngredients());
        Picasso.with(this)
                .load(sandwich.getImage())
                .resize(800, 600)
                .centerCrop()
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(List<String> alsoKnownAs, String placeOfOrigin, String description, List<String> ingredients) {
        description_tv.setText(description);

        String temp = "";
        for (int i = 0; i < alsoKnownAs.size(); i++) {
            temp += alsoKnownAs.get(i);
            if (i < alsoKnownAs.size() - 1) temp += "\n";
        }
        alsoKnownAsTv.setText(temp);

        temp = "";
        for (int i = 0; i < ingredients.size(); i++) {
            temp += ingredients.get(i);
            if (i < ingredients.size() - 1) temp += "\n";
        }
        ingredients_tv.setText(temp);

        origin_tv.setText(placeOfOrigin);
    }
}
