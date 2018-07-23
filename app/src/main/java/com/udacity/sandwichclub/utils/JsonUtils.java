package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static String mainName;
    private static String placeOfOrigin;
    private static String description;
    private static String image;
    private static List<String> alsoKnownAs;
    private static List<String> ingredients;

    public static Sandwich parseSandwichJson(String json) {
        Log.d("JSON", "parseSandwichJson: " + json);

        if (json != null) {
            try {

                JSONObject jsonObject = new JSONObject(json);
                placeOfOrigin = jsonObject.getString("placeOfOrigin");
                description = jsonObject.getString("description");
                image = jsonObject.getString("image");

                JSONObject nameJson = jsonObject.getJSONObject("name");
                mainName = nameJson.getString("mainName");

                JSONArray alsoKnownAsJson = nameJson.getJSONArray("alsoKnownAs");
                alsoKnownAs = new ArrayList<String>();

                for (int i = 0; i < alsoKnownAsJson.length(); i++) {
                    String id = alsoKnownAsJson.getString(i);
                    alsoKnownAs.add(id);
                }

                JSONArray ingredientsJson = jsonObject.getJSONArray("ingredients");
                ingredients = new ArrayList<String>();

                for (int i = 0; i < ingredientsJson.length(); i++) {
                    String id = ingredientsJson.getString(i);
                    ingredients.add(id);
                }

            } catch (JSONException e) {
                Log.e("JSON", "Json parsing error: " + e.getMessage());
            }

        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }


}

