package com.example.marisayeung.gocook;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/*
    No longer used
    Code worked but decided to change format

 */

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
        }

//        Set ingredients
        LinearLayout ingredientRoot = (LinearLayout) findViewById(R.id.ingredients);
        List<Ingredient> ingredients = recipe.getIngredients();
        LinearLayout ingredientRow;
        if (ingredients.size() > 0) {
            for (Ingredient i : ingredients) {
                ingredientRow = RecipeViewHelper.displayIngredient(i, this);
                ingredientRoot.addView(ingredientRow);
            }
        }

//        Set steps
        LinearLayout preparationRoot = (LinearLayout) findViewById(R.id.steps);
        List<String> steps = recipe.getSteps();
        LinearLayout stepRow;
        if (steps.size() > 0) {
            for (int i = 0; i < steps.size(); i++) {
                stepRow = RecipeViewHelper.displayStep(steps.get(i), i + 1, this);
                preparationRoot.addView(stepRow);
            }
        }
    }

    /*private void displayStep(String step, int num, LinearLayout root) {
//        Add Step row
        LinearLayout stepLayout = new LinearLayout(this);
        stepLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams stepParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        stepLayout.setLayoutParams(stepParams);
        root.addView(stepLayout);

//        Add number
        TextView number = new TextView(this);
        LinearLayout.LayoutParams numberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numberParams.setMargins(16, 0, 0, 0);
        number.setLayoutParams(numberParams);

        StringBuilder builder = new StringBuilder();
        builder.append(num);
        builder.append(". ");
        number.setText(builder);
        stepLayout.addView(number);

//        Add step
        TextView stepView = new TextView(this);
        LinearLayout.LayoutParams stepTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        stepTextParams.setMargins(16, 0, 0, 0);
        stepView.setLayoutParams(stepTextParams);

        stepView.setText(step);
        stepLayout.addView(stepView);
    }*/

    /*private void displayIngredient(Ingredient i, LinearLayout root) {
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
        if (!i.getNote().equals("")) {
            builder.append(" Note: ");
            builder.append(i.getNote());
        }

        TextView ingredientView = new TextView(this);
        ingredientView.setText(builder.toString());
        ingredientView.setLayoutParams(lineParams);
        ingredientLayout.addView(ingredientView);

    }*/

    private void updateTextView(String newText, int id) {
        if (!newText.equals("")) {
            TextView view = (TextView) findViewById(id);
            view.setText(newText);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuHelper.handleOnItemSelected(this, item);
    }
}
