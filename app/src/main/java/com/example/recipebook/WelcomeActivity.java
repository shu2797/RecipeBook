/*
WelcomeActivity.java
App launches into this activity. Contains SEARCH and ADD buttons.
 */

package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;

public class WelcomeActivity extends AppCompatActivity {

    FloatingActionButton searchFAB, addFAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        searchFAB = findViewById(R.id.searchFAB); //search button
        addFAB = findViewById(R.id.addFAB); //add button

        //Tooltips on buttons
        TooltipCompat.setTooltipText(searchFAB, "Search and edit recipes");
        TooltipCompat.setTooltipText(addFAB, "Add new recipe to recipe book");

        //Buttons onClick listeners
        searchFAB.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);
        });

        addFAB.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AddActivity.class);
            startActivity(i);
        });
    }
}
