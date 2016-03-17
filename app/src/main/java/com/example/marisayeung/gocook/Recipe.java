package com.example.marisayeung.gocook;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marisayeung
 */
public class Recipe implements Parcelable{
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

    public Recipe(){

    }

    public Recipe(JSONObject jsonRecipe) {
        try {
            meta = new Meta(jsonRecipe.getJSONObject("meta"));

            title = jsonRecipe.getString("title");
            author = jsonRecipe.getString("author");
            time = jsonRecipe.getString("time");
            yield = jsonRecipe.getString("yield");
            description = jsonRecipe.getString("description");

            JSONArray noteJSON = jsonRecipe.getJSONArray("notes");
            notes = new ArrayList<>();
            String note;
            for (int i = 0; i < noteJSON.length(); i++) {
                note = noteJSON.getString(i);
                if (!note.equals("")) {
                    notes.add(note);
                }
            }

            JSONArray ingredientsJSON = jsonRecipe.getJSONArray("ingredients");
            ingredients = new ArrayList<>();
            for (int j = 0; j < ingredientsJSON.length(); j++) {
                ingredients.add(new Ingredient(ingredientsJSON.getJSONObject(j)));
            }

            JSONArray stepsJSON = jsonRecipe.getJSONArray("steps");
            steps = new ArrayList<>();
            for (int k = 0; k < stepsJSON.length(); k++) {
                steps.add(stepsJSON.getString(k));
            }

            JSONArray imagesJSON = jsonRecipe.getJSONArray("images");
            images = new ArrayList<>();
            String image;
            for (int l = 0; l < imagesJSON.length(); l++) {
                image = imagesJSON.getString(l);
                if (!image.equals("")) {
                    images.add(image);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        author = in.readString();
        time = in.readString();
        yield = in.readString();
        description = in.readString();
        notes = in.createStringArrayList();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createStringArrayList();
        images = in.createStringArrayList();
        meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(time);
        dest.writeString(yield);
        dest.writeString(description);
        dest.writeStringList(notes);
        dest.writeTypedList(ingredients);
        dest.writeStringList(steps);
        dest.writeStringList(images);
        dest.writeParcelable(meta, flags);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
