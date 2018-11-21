package com.example.recipebook;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.TooltipCompat;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    FloatingActionButton searchFAB, addFAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        searchFAB = findViewById(R.id.searchFAB);
        addFAB = findViewById(R.id.addFAB);

        TooltipCompat.setTooltipText(searchFAB, "Search and edit recipes");
        TooltipCompat.setTooltipText(addFAB, "Add new recipe to recipe book");


        searchFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(i);
            }
        });

        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(i);
            }
        });
    }
}
