package com.example.recipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    TextView recipeName, recipeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        recipeName = findViewById(R.id.view_recipeName);
        recipeText = findViewById(R.id.view_recipeText);

        String name = getIntent().getExtras().getString("name");
        String text = getIntent().getExtras().getString("text");

        recipeName.setText(name);
        recipeText.setText(text);

    }
}
