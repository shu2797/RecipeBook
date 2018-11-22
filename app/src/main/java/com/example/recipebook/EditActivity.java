/*
EditActivity.java
Launched after user taps on EDIT button in ViewActivity.
Allows user to edit recipe name, instructions, save and update them in the database
 */

package com.example.recipebook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText recipeName, recipeText;
    FloatingActionButton cancelFAB, saveFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);

        recipeName = findViewById(R.id.edit_recipeName);
        recipeText = findViewById(R.id.edit_recipeText);

        cancelFAB = findViewById(R.id.edit_cancelFAB); //cancel button
        saveFAB = findViewById(R.id.edit_saveFAB); //save button

        //Tooltips for buttons
        TooltipCompat.setTooltipText(cancelFAB, "Cancel and go back");
        TooltipCompat.setTooltipText(saveFAB, "Save and update recipe in recipe book");

        //get recipe id from ViewActivity
        int id = getIntent().getExtras().getInt("id");

        //retrieve recipe from database based on id
        Recipe r = dbHandler.findRecipe(id);

        //display recipe contents
        recipeName.setText(r.getRecipeName());
        recipeText.setText(r.getRecipeText());


        //buttons onClick listeners
        cancelFAB.setOnClickListener(view -> {
            //display dialog to confirm discarding any changes made and go back
            AlertDialog.Builder alert = new AlertDialog.Builder(EditActivity.this);

            alert
                    .setTitle("Are you sure?")
                    .setMessage("This will discard any changes you made")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                    .setNegativeButton("No", (dialogInterface, i) -> {});
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });

        saveFAB.setOnClickListener(view -> {
            if(recipeName.getText().toString().isEmpty()) {
                //display alert dialog if recipe name is left blank
                AlertDialog.Builder alert = new AlertDialog.Builder(EditActivity.this);

                alert
                        .setTitle("Blank name")
                        .setMessage("Sorry, you cannot leave the recipe name blank")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                        });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            } else {
                //display alert dialog before overwriting contents of recipe selected
                AlertDialog.Builder alert = new AlertDialog.Builder(EditActivity.this);

                alert
                        .setTitle("Are you sure?")
                        .setMessage("This will overwrite the recipe selected and update the recipe book")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            Recipe recipe = new Recipe(recipeName.getText().toString(), recipeText.getText().toString());
                            dbHandler.updateRecipe(recipe, id);
                            Toast.makeText(getApplicationContext(), "Recipe updated", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> {
                        });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
    }

    //override function when back button is pressed to confirm if user wants to discard any changes made and go back
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(EditActivity.this);

        alert
                .setTitle("Are you sure?")
                .setMessage("This will discard any changes you made")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                .setNegativeButton("No", (dialogInterface, i) -> {

                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}
