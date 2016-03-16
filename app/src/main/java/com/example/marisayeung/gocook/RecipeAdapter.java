package com.example.marisayeung.gocook;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/**
 * Created by marisayeung
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private final List<Recipe> recipes;
    static int preview_length = 20;

    public RecipeAdapter(Context context, int resource, List<Recipe> recipes) {
        super(context, resource, recipes);
        this.recipes = recipes;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe recipe = recipes.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.recipe_list_row, null);

//        show thumbnail
        ImageView thumbnail = (ImageView) row.findViewById(R.id.thumbnail);
        List<String> images = recipe.getImages();
        setThumbnail(images, thumbnail);
        String fname = recipe.getImages().get(0);
        try {
            Drawable image = Drawable.createFromStream(getContext().getAssets().open(fname), null);
            thumbnail.setImageDrawable(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        show recipe title
        TextView titleView = (TextView) row.findViewById(R.id.rowTitle);
        String title = recipe.getTitle();
        titleView.setText(title);

//        show recipe description preview
        TextView descView = (TextView) row.findViewById(R.id.rowText);
        String description = recipe.getDescription();
        String descPreview;
        if (description.length() < preview_length) {
            descPreview = description;
        } else {
            descPreview = description.substring(0, preview_length) + "...";
        }
        descView.setText(descPreview);

        return row;
    }

    private void setThumbnail(List<String> images, ImageView thumbnail) {
        if (images.size() > 0) {
            String fname = images.get(0);
            try {
                Drawable image = Drawable.createFromStream(getContext().getAssets().open(fname), null);
                thumbnail.setImageDrawable(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
