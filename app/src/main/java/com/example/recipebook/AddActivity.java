/*
AddActivity.java
Launched when ADD button is pressed from WelcomeActivity.
Allows user to add new recipe to database
 */

package com.example.recipebook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    FloatingActionButton cancelFAB, saveFAB;
    EditText recipeName, recipeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Log.d("recipeBook", "Add");

        cancelFAB = findViewById(R.id.add_cancelFAB); //cancel button
        saveFAB = findViewById(R.id.add_saveFAB); //save button

        recipeName = findViewById(R.id.add_recipeName);
        recipeText = findViewById(R.id.add_recipeText);

        //Tooltips for buttons
        TooltipCompat.setTooltipText(cancelFAB, "Discard and go back");
        TooltipCompat.setTooltipText(saveFAB, "Add new recipe record to recipe book");

        //Buttons onClick listeners
        cancelFAB.setOnClickListener(view -> {
            //if text input fields are not empty, warn user that anything typed will be discarded
            if((!recipeText.getText().toString().isEmpty()) || (!recipeName.getText().toString().isEmpty())){
                AlertDialog.Builder alert = new AlertDialog.Builder(AddActivity.this);

                alert
                        .setTitle("Are you sure?")
                        .setMessage("This will discard everything you typed and go back")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                        .setNegativeButton("No", (dialogInterface, i) -> {});
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            } else {
                finish();
            }
        });

        saveFAB.setOnClickListener(view -> {
            if(recipeName.getText().toString().isEmpty()){
                //Alert user that recipe name cannot be left blank if user tries to save recipe while leaving recipe name field blank
                AlertDialog.Builder alert = new AlertDialog.Builder(AddActivity.this);

                alert
                        .setTitle("Blank name")
                        .setMessage("Sorry, you cannot leave the recipe name blank")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                        });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            } else {
                //save new recipe to database
                MyDBHandler dbHandler = new MyDBHandler(getBaseContext(), null, null, 1);
                Recipe recipe = new Recipe(recipeName.getText().toString(), recipeText.getText().toString());
                dbHandler.addRecipe(recipe);
                Log.d("recipeBook", "Recipe saved");
                Toast.makeText(getApplicationContext(),"Recipe saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //override function when back button is pressed so that if text fields are not empty, warn user that any contents typed will be lost
    @Override
    public void onBackPressed() {
        if((!recipeText.getText().toString().isEmpty()) || (!recipeName.getText().toString().isEmpty())){
            AlertDialog.Builder alert = new AlertDialog.Builder(AddActivity.this);

            alert
                    .setTitle("Are you sure?")
                    .setMessage("This will discard everything you typed and go back")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                    .setNegativeButton("No", (dialogInterface, i) -> {

                    });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        } else {
            super.onBackPressed();
        }
    }
}
