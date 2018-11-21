package com.example.recipebook;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Recipe> {
    Context context;
    int layoutResourceId;
    ArrayList<Recipe> data = null;

    public CustomAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = (ArrayList) objects;
    }


}
