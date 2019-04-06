package com.hkucs.cookbook.activities.recipeMenuActivity;

import java.io.Serializable;

public class RecipeItem implements Serializable {
    private String name;
    private int time;
    private int score;
    private int itemId;
    private int catId;
    private String drawableId;

    public RecipeItem(String name,int time, int score, int itemId, String drawableId, int catId ) {
        this.name = name;
        this.time = time;
        this.score = score;
        this.itemId = itemId;
        this.drawableId = drawableId;
        this.catId = catId;
    }
    public int getTime(){
        return this.time;
    }
    public String getName(){
        return this.name;
    }
    public int getScore(){
        return this.score;
    }
    public int getItemId(){
        return this.itemId;
    }
    public String getDrawableId(){
        return this.drawableId;
    }
    public int getCatId(){return this.catId;}

//    setter
    public void setTime(int time){
        this.time = time;
}
    public void setName(String name){
        this.name = name;
    }
    public void setScore(int score){
        this.score = score;
    }
    public void setItemId(int itemId){
        this.itemId = itemId;
    }
    public void setDrawableId(String drawableId){
        this.drawableId = drawableId;
    }
    public void setCatId(int catId) { this.catId = catId; }
}
