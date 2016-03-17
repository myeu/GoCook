package com.example.marisayeung.gocook;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by marisayeung
 */
public class RecipeParser {
    Recipe recipe;
    NYTimesRecipe nyt;

    public RecipeParser() {
        recipe = new Recipe();
    }

    public boolean parseNYTimesRecipe(String fName, boolean fileName) {
        nyt = new NYTimesRecipe(fName, fileName);

        if (nyt.isNYTimesRecipe()) {
            recipe.setTitle(nyt.getTitle());
            recipe.setAuthor(nyt.getAuthor());
            recipe.setTime(nyt.getTime());
            recipe.setYield(nyt.getYield());
            recipe.setDescription(nyt.getDescription());
            recipe.setNotes(nyt.getNotes());
            recipe.setIngredients(nyt.getIngredients());
            recipe.setSteps(nyt.getSteps());
            recipe.setImages(nyt.getImageURLs());
            return true;
        } else {
            return false;
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public RecipeParser(JSONObject jsonRecipe) {
        recipe = new Recipe();
        try {
            recipe.setMeta(new Meta(jsonRecipe.getJSONObject("meta")));

            recipe.setTitle(jsonRecipe.getString("title"));
            recipe.setAuthor(jsonRecipe.getString("author"));
            recipe.setTime(jsonRecipe.getString("time"));
            recipe.setYield(jsonRecipe.getString("yield"));
            recipe.setDescription(jsonRecipe.getString("description"));

            JSONArray noteJSON = jsonRecipe.getJSONArray("notes");
            ArrayList<String> notes = new ArrayList<>();
            String note;
            for (int i = 0; i < noteJSON.length(); i++) {
                note = noteJSON.getString(i);
                if (!note.equals("")) {
                    notes.add(note);
                }
            }
            recipe.setNotes(notes);

            JSONArray ingredientsJSON = jsonRecipe.getJSONArray("ingredients");
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            for (int j = 0; j < ingredientsJSON.length(); j++) {
                ingredients.add(new Ingredient(ingredientsJSON.getJSONObject(j)));
            }
            recipe.setIngredients(ingredients);

            JSONArray stepsJSON = jsonRecipe.getJSONArray("steps");
            ArrayList<String> steps = new ArrayList<>();
            for (int k = 0; k < stepsJSON.length(); k++) {
                steps.add(stepsJSON.getString(k));
            }
            recipe.setSteps(steps);

            JSONArray imagesJSON = jsonRecipe.getJSONArray("images");
            ArrayList<String> images = new ArrayList<>();
            String image;
            for (int l = 0; l < imagesJSON.length(); l++) {
                image = imagesJSON.getString(l);
                if (!image.equals("")) {
                    images.add(image);
                }
            }
            recipe.setImages(images);

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void main(String[] args) {
        NYTimesRecipe nyt = new NYTimesRecipe("/Users/marisayeung/Dropbox/Winter-2016/COEN268/GoCook/app/src/main/assets/Stovetop-Braised Carrots and Parsnips.html", true);
        RecipeParser r = new RecipeParser();
        Log.d("PARSER", nyt.getTitle() + "");
    }

}
