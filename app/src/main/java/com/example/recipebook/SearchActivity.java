package com.example.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = findViewById(R.id.search_listView);

        MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);

        List<Recipe> recipeList = new ArrayList<>();

        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM recipes", null);

        int id;
        String name, text;

        if(c != null){
            if(c.moveToFirst()){
                do {
                    id = c.getInt(c.getColumnIndex("id"));
                    name = c.getString(c.getColumnIndex("recipename"));
                    text = c.getString(c.getColumnIndex("recipetext"));

                    Recipe r = new Recipe(id, name, text);
                    recipeList.add(r);
                    //recipeList.add(c.getString(c.getColumnIndex("recipename")));

                } while (c.moveToNext());
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //recipeList.sort();
            recipeList.sort(Recipe.RecipeNameComparator);
        }


        //while (recipeList.)

        //List<String> nameList =

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Recipe selectedRecipe = (Recipe) listView.getItemAtPosition(pos);
                Toast.makeText(getApplicationContext(), "ID selected: " + Integer.toString(selectedRecipe.get_id()), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), ViewActivity.class);
                i.putExtra("id", selectedRecipe.get_id());
                i.putExtra("name", selectedRecipe.getRecipeName());
                i.putExtra("text", selectedRecipe.getRecipeText());
                startActivity(i);
            }
        });
    }
}
