package com.example.recipebook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        populateList();
    }

    @Override
    protected void onResume() {
        populateList();
        super.onResume();
    }

    protected void populateList(){
        listView = findViewById(R.id.search_listView);

        MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);

        List<Recipe> recipeList = dbHandler.listAllRecipes();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            recipeList.sort(Recipe.RecipeNameComparator);
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, pos, l) -> {
            Recipe selectedRecipe = (Recipe) listView.getItemAtPosition(pos);
            Toast.makeText(getApplicationContext(), "ID selected: " + Integer.toString(selectedRecipe.get_id()), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getApplicationContext(), ViewActivity.class);
            i.putExtra("id", selectedRecipe.get_id());
            startActivity(i);
        });
    }
}
