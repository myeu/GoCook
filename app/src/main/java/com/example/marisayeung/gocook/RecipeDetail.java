package com.example.marisayeung.gocook;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra("chosenRecipe");

        displayRecipe(recipe);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void displayRecipe(Recipe recipe) {

//        Set title
        updateTextView(recipe.getTitle(), R.id.title);

//        Set top image
        ImageView topPhoto = (ImageView) findViewById(R.id.topPhoto);
        List<String> images = recipe.getImages();
        try {
            if (images.size() > 0) {
                Drawable image = Drawable.createFromStream(getApplicationContext().getAssets().open(images.get(0)), null);
                topPhoto.setImageDrawable(image);
            } else if (images.size() > 1) {
                // TODO: add image view elements to content xml
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Set author
        updateTextView(recipe.getAuthor(), R.id.author);

//        Set yield info
        updateTextView(recipe.getYield(), R.id.yield);

//        Set description
        updateTextView(recipe.getDescription(), R.id.description);

//        Set notes
        List<String> notes = recipe.getNotes();
        if (notes.size() > 0) {
            updateTextView(notes.get(0), R.id.notes);
            /* TODO: add new textview elements to content xml
            for (String note : notes) {
                updateTextView(note, R.id.notes);
            }*/

        }

//        Set ingredients
        LinearLayout ingredientRoot = (LinearLayout) findViewById(R.id.ingredients);
        List<Ingredient> ingredients = recipe.getIngredients();
        if (ingredients.size() > 0) {
            //updateTextView(ingredients.get(0), R.id.notes);
            //TODO: add new textview elements to ingredients layout in content xml
            for (Ingredient i : ingredients) {

                displayIngredient(i, ingredientRoot);
                //updateTextView(note, R.id.notes);
            }

        }
    }

    private void displayIngredient(Ingredient i, LinearLayout root) {
//        Add ingredient row
        LinearLayout ingredientLayout = new LinearLayout(this);
        ingredientLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams ingredientParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ingredientLayout.setLayoutParams(ingredientParams);
        root.addView(ingredientLayout);

        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lineParams.setMargins(16, 0, 0, 0);

        TextView amountView = new TextView(this);
        amountView.setText(i.getAmount());
        amountView.setLayoutParams(lineParams);
        ingredientLayout.addView(amountView);

//        TODO: check whether to add plural ending
        TextView unitView = new TextView(this);
        if (!i.getUnit().equals("each")) {
            unitView.setText(i.getUnit());
            unitView.setLayoutParams(lineParams);
            ingredientLayout.addView(unitView);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(i.getIngredient());
        if (!i.getProcessing().equals("")) {
            builder.append(", ");
            builder.append(i.getProcessing());
        }
//        if (!i.getNote().equals("")) {
//            builder.append(" Note: ");
//            builder.append(i.getNote());
//        }

        TextView ingredientView = new TextView(this);
        ingredientView.setText(builder.toString());
        ingredientView.setLayoutParams(lineParams);
        ingredientLayout.addView(ingredientView);

    }


    private void updateTextView(String newText, int id) {
        if (!newText.equals("")) {
            TextView view = (TextView) findViewById(id);
            view.setText(newText);
        }
    }
}
