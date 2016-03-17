package com.example.marisayeung.gocook;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marisayeung
 * */
public class Ingredient implements Parcelable{
    private String ingredient;
    private String amount;
    private String unit;
    private String processing;
    private String note;

    /*
        TODO: validate amounts to accept fractions etc.
     */
    public Ingredient(JSONObject unparsedIngredient) {
        try {
            ingredient = unparsedIngredient.getString("ingredient");
            amount = unparsedIngredient.getString("amount");
            unit = unparsedIngredient.getString("unit");
            processing = unparsedIngredient.getString("processing");
            note = unparsedIngredient.getString("note");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

    public Ingredient() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ingredient);
        dest.writeString(this.amount);
        dest.writeString(this.unit);
        dest.writeString(this.processing);
        dest.writeString(this.note);
    }

    protected Ingredient(Parcel in) {
        this.ingredient = in.readString();
        this.amount = in.readString();
        this.unit = in.readString();
        this.processing = in.readString();
        this.note = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getIngredient() {
        return ingredient;
    }

    public String getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getProcessing() {
        return processing;
    }

    public String getNote() {
        return note;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

