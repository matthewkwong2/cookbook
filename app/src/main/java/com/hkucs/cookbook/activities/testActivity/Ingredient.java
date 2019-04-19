package com.hkucs.cookbook.activities.testActivity;

public class Ingredient {

    private String ingredient;

    private boolean checked;

    public Ingredient() {
    }

    public Ingredient(String ingredient, boolean checked) {
        this.ingredient = ingredient;
        this.checked = checked;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
