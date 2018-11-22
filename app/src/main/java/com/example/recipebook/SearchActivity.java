/*
SearchActivity.java
Launches when Search button is pressed from WelcomeActivity.
Displays recipe names in database in a list view where user can select a recipe and view it
 */

package com.example.recipebook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        populateList();
    }

    //update list when user has done viewing/editing/deleting a recipe
    @Override
    protected void onResume() {
        populateList();
        super.onResume();
    }

    //refresh and update list with latest database contents
    protected void populateList(){
        listView = findViewById(R.id.search_listView);

        //database handler
        MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);

        //retrieve list of recipes from database
        List<Recipe> recipeList = dbHandler.listAllRecipes();

        //sort recipe list in alphabetical order
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            recipeList.sort(Recipe.RecipeNameComparator);
        }

        //adapter to associate with listview
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeList);

        listView.setAdapter(adapter);

        //when user selects a recipe in the list
        listView.setOnItemClickListener((adapterView, view, pos, l) -> {
            Recipe selectedRecipe = (Recipe) listView.getItemAtPosition(pos);

            //launch ViewActivity to view recipe
            Intent i = new Intent(getApplicationContext(), ViewActivity.class);
            i.putExtra("id", selectedRecipe.get_id());
            startActivity(i);
        });
    }
}
