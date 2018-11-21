package com.example.recipebook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    FloatingActionButton cancelFAB, saveFAB;
    EditText recipeName, recipeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        cancelFAB = findViewById(R.id.add_cancelFAB);
        saveFAB = findViewById(R.id.add_saveFAB);

        recipeName = findViewById(R.id.add_recipeName);
        recipeText = findViewById(R.id.add_recipeText);

        TooltipCompat.setTooltipText(cancelFAB, "Discard and go back");
        TooltipCompat.setTooltipText(saveFAB, "Add new recipe record to recipe book");

        cancelFAB.setOnClickListener(view -> {
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
                MyDBHandler dbHandler = new MyDBHandler(getBaseContext(), null, null, 1);
                Recipe recipe = new Recipe(recipeName.getText().toString(), recipeText.getText().toString());
                dbHandler.addRecipe(recipe);
                Toast.makeText(getApplicationContext(),"Recipe saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

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
