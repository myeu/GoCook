package com.example.marisayeung.gocook;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marisayeung on 2/16/16.
 */
public class Ingredient {
    String ingredient;
    String amount;
    String unit;
    String processing;
    String note;

    public Ingredient(JSONObject unparsedIngredient) {
        try {
            ingredient = unparsedIngredient.getString("ingredient");
            amount = unparsedIngredient.getString("amount");
            unit = unparsedIngredient.getString("unit");
            processing = unparsedIngredient.getString("note");
            note = unparsedIngredient.getString("note");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }


}
