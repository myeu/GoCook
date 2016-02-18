package com.example.marisayeung.gocook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RecipeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onResume() {
        super.onResume();
        List<Recipe> recipes = new ArrayList<>();

        String recipe1File = "recipe1.json";
        String recipe1String = readJsonFileToString(recipe1File);
        Log.d("marisa-test", "read recipe1");
        addJsonStringToRecipeList(recipes, recipe1String);

        String recipe2File = "recipe2.json";
        String recipe2String = readJsonFileToString(recipe2File);
        Log.d("marisa-test", "read recipe2");
        addJsonStringToRecipeList(recipes, recipe2String);

        // clear "create new note" textview if it exists
        TextView emptyList = (TextView) findViewById(R.id.empty_list);
        if (emptyList != null) {
            ((ViewGroup) emptyList.getParent()).removeView(emptyList);
        }

        ListView listView = (ListView) findViewById(R.id.recipe_list_view);
        RecipeAdapter recipeAdapter = new RecipeAdapter(this, R.layout.recipe_list_row, recipes);
        listView.setAdapter(recipeAdapter);

    }

    private void addJsonStringToRecipeList(List<Recipe> recipes, String jsonString) {
        try {
            JSONObject recipe = new JSONObject(jsonString);
            recipes.add(new Recipe(recipe));
        } catch (JSONException e) {
            System.out.println(jsonString.substring(0,30));
            e.printStackTrace();
        }
    }

    private String readJsonFileToString(String jsonFile) {
        BufferedReader bufferedReader = null;
        String json = "";

        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream jsonStream = getAssets().open(jsonFile);
            bufferedReader = new BufferedReader(new InputStreamReader(jsonStream, "UTF-8"));

            int ch;
            while ((ch = bufferedReader.read()) != -1) {
                stringBuilder.append((char) ch);
            }
            json = stringBuilder.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("marisa-test", "Error opening asset " + jsonFile);
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.d("marisa-test", "Error closing asset " + jsonFile);
                }
            }
        }

        return json;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
