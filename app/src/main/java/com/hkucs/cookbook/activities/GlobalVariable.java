package com.hkucs.cookbook.activities;

import android.app.Application;

public class GlobalVariable extends Application {

    private static int recipeOne;
    private static int RecipeTwo;
    private static int numOfRecipe = 0;

    public int getRecipeOne() {
        return recipeOne;
    }

    public void setRecipeOne(int recipeOne) {
        this.recipeOne = recipeOne;
    }

    public int getRecipeTwo() {
        return RecipeTwo;
    }

    public void setRecipeTwo(int recipeTwo) {
        RecipeTwo = recipeTwo;
    }

    public int getNumOfRecipe() {
        return numOfRecipe;
    }

    public void setNumOfRecipe(int numOfRecipe) {
        this.numOfRecipe = numOfRecipe;
    }
}
