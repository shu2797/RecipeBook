/*
MyDBHandler.java
database handler which connects to content provider to manage database
 */

package com.example.recipebook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.recipebook.provider.MyContentProvider;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    private ContentResolver myCR;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipeDB.db";
    public static final String TABLE_RECIPES = "recipes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPENAME = "recipename";
    public static final String COLUMN_RECIPETEXT = "recipetext";


    public MyDBHandler( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " +
                TABLE_RECIPES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_RECIPENAME
                + " TEXT," + COLUMN_RECIPETEXT + " TEXT" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    //add recipe to database
    public void addRecipe(Recipe recipe){
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPENAME, recipe.getRecipeName());
        values.put(COLUMN_RECIPETEXT, recipe.getRecipeText());
        myCR.insert(MyContentProvider.CONTENT_URI, values);
    }

    //find recipe using recipe name
    public Recipe findRecipe(String recipename){
        String[] projection = {COLUMN_ID,
                COLUMN_RECIPENAME, COLUMN_RECIPETEXT };
        String selection = COLUMN_RECIPENAME + " = \"" + recipename + "\"";
        Cursor cursor = myCR.query(MyContentProvider.CONTENT_URI,
                projection, selection, null,
                null);
        Recipe recipe = new Recipe();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            recipe.set_id(Integer.parseInt(cursor.getString(0)));
            recipe.setRecipeName(cursor.getString(1));
            recipe.setRecipeText(cursor.getString(2));
            cursor.close();
        } else {
            recipe = null;
        }
        return recipe;
    }

    //find recipe using recipe id
    public Recipe findRecipe(int recipeID){
        String[] projection = {COLUMN_ID, COLUMN_RECIPENAME, COLUMN_RECIPETEXT};
        String selection = COLUMN_ID + " = " + Integer.toString(recipeID);
        Cursor cursor = myCR.query(MyContentProvider.CONTENT_URI, projection, selection, null, null);
        Recipe recipe = new Recipe();
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            recipe.set_id(Integer.parseInt(cursor.getString(0)));
            recipe.setRecipeName(cursor.getString(1));
            recipe.setRecipeText(cursor.getString(2));
            cursor.close();
        } else {
            recipe = null;
        }
        return recipe;
    }

    //update recipe in database
    public void updateRecipe(Recipe recipe, int id){
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPENAME, recipe.getRecipeName());
        values.put(COLUMN_RECIPETEXT, recipe.getRecipeText());
        myCR.update(MyContentProvider.CONTENT_URI, values, COLUMN_ID + " = " + Integer.toString(id), null);
    }

    //return a list of all recipes
    public List<Recipe> listAllRecipes(){
        List<Recipe> recipeList = new ArrayList<>();

        String[] projection = {COLUMN_ID, COLUMN_RECIPENAME, COLUMN_RECIPETEXT};
        String selection = null;
        Cursor c = myCR.query(MyContentProvider.CONTENT_URI, projection, selection, null, null);

        int id;
        String name, text;

        if(c != null){
            if(c.moveToFirst()){
                do {
                    id = c.getInt(c.getColumnIndex(COLUMN_ID));
                    name = c.getString(c.getColumnIndex(COLUMN_RECIPENAME));
                    text = c.getString(c.getColumnIndex(COLUMN_RECIPETEXT));

                    Recipe r = new Recipe(id, name, text);
                    recipeList.add(r);

                } while (c.moveToNext());
            }
        }

        return recipeList;
    }

    //delete recipe using recipe name
    public boolean deleteRecipe(String recipename){
        boolean result = false;
        String selection = "recipename = \"" + recipename + "\"";
        int rowsDeleted = myCR.delete(MyContentProvider.CONTENT_URI,
                selection, null);
        if (rowsDeleted > 0)
            result = true;
        return result;
    }

    //delete recipe using recipe id
    public boolean deleteRecipe(int recipeID){
        boolean result = false;
        String selection = COLUMN_ID + " = " + Integer.toString(recipeID);
        int rowsDeleted = myCR.delete(MyContentProvider.CONTENT_URI,
                selection, null);
        if (rowsDeleted > 0)
            result = true;
        return result;
    }
}
