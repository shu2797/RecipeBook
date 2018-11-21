package com.example.recipebook;

import android.content.DialogInterface;
import android.support.design.circularreveal.CircularRevealWidget;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        cancelFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!recipeText.getText().toString().isEmpty()) || (!recipeName.getText().toString().isEmpty())){
                    AlertDialog.Builder alert = new AlertDialog.Builder(AddActivity.this);

                    alert
                            .setTitle("Are you sure?")
                            .setMessage("This will discard everything you typed and go back")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                } else {
                    finish();
                }
            }
        });

        saveFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        } else {
            super.onBackPressed();
        }
    }
}
