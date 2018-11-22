/*
ViewActivity.java
Launched when a recipe is selected from the list displayed in SearchActivity.
In this activity, user can view recipe name and instructions.
It also contains DELETE and EDIT buttons so user can modify it.
 */

package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {

    int id;

    TextView recipeName, recipeText;
    FloatingActionButton editFAB, deleteFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        recipeName = findViewById(R.id.view_recipeName);
        recipeText = findViewById(R.id.view_recipeText);

        editFAB = findViewById(R.id.view_editFAB); //edit button
        deleteFAB = findViewById(R.id.view_deleteFAB); //delete button

        //Tooltips for buttons
        TooltipCompat.setTooltipText(editFAB, "Edit recipe");
        TooltipCompat.setTooltipText(deleteFAB, "Delete recipe");

        //get recipe id from SearchActivity
        id = getIntent().getExtras().getInt("id");

        displayRecipe();

    }

    //refresh and update recipe contents after user has done editing
    @Override
    protected void onResume() {
        displayRecipe();
        super.onResume();
    }

    //retrieve latest recipe content of the selected id and display it
    protected void displayRecipe(){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        //retrieve latest recipe name and instructions based on id selected
        Recipe r = dbHandler.findRecipe(id);
        String name = r.getRecipeName();
        String text = r.getRecipeText();

        recipeName.setText(name);
        recipeText.setText(text);

        //buttons onClick listeners
        editFAB.setOnClickListener(view -> {
            //launch EditActivity so user can edit recipe
            Intent i = new Intent(getApplicationContext(), EditActivity.class);
            i.putExtra("id", id);
            startActivity(i);
        });

        deleteFAB.setOnClickListener(view -> {
            //display alert dialog to confirm delete action
            AlertDialog.Builder alert = new AlertDialog.Builder(ViewActivity.this);

            alert
                    .setTitle("Are you sure?")
                    .setMessage("This will delete this recipe forever")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        dbHandler.deleteRecipe(id);
                        Toast.makeText(getApplicationContext(),"Recipe deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("No", (dialogInterface, i) -> {
                    });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });
    }
}
