package com.example.marisayeung.gocook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by marisayeung on 2/16/16.
 */
public class Recipe {
    private String title;
    private String author;
    private String time;
    private String yield;
    private String description;
    private List<String> notes;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private List<String> images;

    private Meta meta;

    public Recipe(JSONObject jsonRecipe) {
        try {
            meta = new Meta(jsonRecipe.getJSONObject("meta"));

            title = jsonRecipe.getString("name");
            author = jsonRecipe.getString("author");
            time = jsonRecipe.getString("time");
            yield = jsonRecipe.getString("yield");
            description = jsonRecipe.getString("description");

            JSONArray noteJSON = jsonRecipe.getJSONArray("notes");
            for (int i = 0; i < noteJSON.length(); i++) {
                notes.add(noteJSON.getString(i));
            }

            JSONArray ingredientsJSON = jsonRecipe.getJSONArray("ingredients");
            for (int j = 0; j < ingredientsJSON.length(); j++) {
                ingredients.add(new Ingredient(ingredientsJSON.getJSONObject(j)));
            }

            JSONArray stepsJSON = jsonRecipe.getJSONArray("steps");
            for (int k = 0; k < stepsJSON.length(); k++) {
                steps.add(stepsJSON.getString(k));
            }

            JSONArray imagesJSON = jsonRecipe.getJSONArray("images");
            for (int l = 0; l < imagesJSON.length(); l++) {
                images.add(imagesJSON.getString(l));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

    public Meta getMeta() {
        return meta;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public String getYield() {
        return yield;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getNotes() {
        return notes;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public List<String> getImages() {
        return images;
    }
}
