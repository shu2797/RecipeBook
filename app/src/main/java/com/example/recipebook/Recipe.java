package com.example.recipebook;

public class Recipe {
    private int _id;
    private String recipeName, recipeText;

    public Recipe(){
    }

    public Recipe(int id, String recipeName, String recipeText){
        this._id = id;
        this.recipeName = recipeName;
        this.recipeText = recipeText;
    }

    public Recipe(String recipeName, String recipeText){
        this.recipeName = recipeName;
        this.recipeText = recipeText;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    public String getRecipeText() {
        return recipeText;
    }
}
