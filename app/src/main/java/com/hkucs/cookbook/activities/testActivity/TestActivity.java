package com.hkucs.cookbook.activities.testActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hkucs.cookbook.R;
import com.hkucs.cookbook.activities.CookbookActivity;
import com.hkucs.cookbook.activities.DatabaseHelper;
import com.hkucs.cookbook.activities.GlobalVariable;
import com.hkucs.cookbook.activities.recipeMenuActivity.RecipeItem;
import com.hkucs.cookbook.activities.slideshow.SlideShowActivity;
import com.hkucs.cookbook.activities.testActivity.adapter.IngredientListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.core.content.res.ResourcesCompat;

public class TestActivity extends CookbookActivity {

    DatabaseHelper databaseHelper;

    Integer id;
    RecipeItem recipe;
    String TAG = "Recipe";
    String name;
    String time;
    String score;
    String image;
    String[] ingredients;
    ArrayList<Ingredient> listInput = new ArrayList<Ingredient>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        Integer recipeId = intent.getIntExtra("recipeId",1);
        Log.d(TAG,"On create.");
        loadData(recipeId);
        initComponents();
    }

    private void initComponents() {
        ListView listView = (ListView)findViewById(R.id.ingredientlistView);
        IngredientListAdapter adapter = new IngredientListAdapter(this, R.layout.ingredient_list, listInput);
        listView.setAdapter(adapter);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked, this.ingredients);
//        ListView listView = (ListView)findViewById(R.id.ingredientlistView);
//        listView.setAdapter(adapter);
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Picasso.get().load(image).into(imageView);
        TextView titleTextView = (TextView)findViewById(R.id.titleTextView);
        titleTextView.setText(name);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setRating(Integer.valueOf(score)/2.0f);
        TextView timeTextView = (TextView)findViewById(R.id.timeTextView);
        timeTextView.setText(time);
        GlobalVariable gv = new GlobalVariable();

        Button start = findViewById(R.id.start);
        start.setText("Start");
        start.setOnClickListener(view -> {
            Intent intent = new Intent(this, SlideShowActivity.class);
            if(gv.getNumOfRecipe()==0){
                intent.putExtra("recipeId",Integer.valueOf(this.id));
            }
            startActivity(intent);
        });

        Button select = findViewById(R.id.select);
        select.setText(String.valueOf(gv.getNumOfRecipe())+ "/2");
        select.setOnClickListener(view -> {
            if (gv.getNumOfRecipe()==0){
                gv.setRecipeOne(this.id);
                gv.setNumOfRecipe(1);
                select.setText("1/2");
            }
            else if (gv.getNumOfRecipe()==1){
                gv.setRecipeTwo(this.id);
                gv.setNumOfRecipe(2);
                select.setText("2/2");
            }
            else{
                gv.setNumOfRecipe(0);
                select.setText("0/2");
            }
        });


    }

    private void loadData(Integer Id){
        Log.d(TAG,"load data");
        this.id = Id;
        recipe = databaseHelper.getRecipesById(Id);
        name = recipe.getName();
        time = String.valueOf(recipe.getTime()) + " mins";
        score = String.valueOf(recipe.getScore());
        image = recipe.getDrawableId();
        String ingredientStr = recipe.getIngredients();
        ingredients = ingredientStr.split(",");
        for (String i: ingredients){
            listInput.add(new Ingredient(i,false));
        }

    }

}
