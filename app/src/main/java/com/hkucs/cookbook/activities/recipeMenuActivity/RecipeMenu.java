package com.hkucs.cookbook.activities.recipeMenuActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.material.appbar.AppBarLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.hkucs.cookbook.R;
import com.hkucs.cookbook.activities.CookbookActivity;
import com.hkucs.cookbook.activities.DatabaseHelper;
import com.hkucs.cookbook.activities.recipeMenuActivity.adapters.RecipeItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RecipeMenu extends CookbookActivity implements AppBarLayout.OnOffsetChangedListener {
    private String TAG = "RecipeMenu";
    private FloatingSearchView searchView;
    private AppBarLayout appBarLayout;
    private RecyclerView recipeMenuRecyclerView;
    private String JSON_FILE = "recipe.json";

    private RecipeItemAdapter recipeItemAdapter;

    DatabaseHelper databaseHelper;

    private ArrayList<RecipeItem> recipeItemArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_menu);
        databaseHelper = new DatabaseHelper(this);
        Intent myintent = getIntent();
        Integer categoryId = myintent.getIntExtra("categoryId",1);
//        do not need to insert if the json file has not changed
//        insertData();

        loadData(categoryId);
        initComponents();

    }
    private void initComponents() {
        setSearchView();
        setRecipeItemRecyclerView();
        setAppBarLayout();
    }
    private void setSearchView() {
        searchView = findViewById(R.id.main_floating_search_view);
        setSearchViewStyle();
        setSearchViewSearchListener();
    }
    private void setSearchViewStyle() {
        searchView.findViewById(R.id.search_query_section).setBackgroundColor(Color.TRANSPARENT);
    }

    private void setSearchViewSearchListener() {
        searchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            recipeItemAdapter.getFilter().filter(newQuery);
        });
    }
    private void setAppBarLayout() {
        appBarLayout = findViewById(R.id.main_app_bar_layout);
        setAppBarAddOffsetListener();
    }
    private void setAppBarAddOffsetListener() {
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        searchView.setTranslationY(verticalOffset);
    }
    private void setRecipeItemRecyclerView() {
        recipeMenuRecyclerView = findViewById(R.id.main_recipe_item_recycler_view);
        recipeItemAdapter = new RecipeItemAdapter(this,recipeItemArrayList);
        recipeMenuRecyclerView.setAdapter(recipeItemAdapter);

        setRecipeMenuRecyclerViewLayoutManager();
    }
    private void setRecipeMenuRecyclerViewLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recipeMenuRecyclerView.setLayoutManager(layoutManager);
    }


    /*
    * read jsonfile and insert them to the database
    * */
    private JSONArray readJson(){
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this));
            return obj.getJSONArray("recipe");
        }catch (JSONException e){
            Log.d(TAG, "readJson: ERROR cannot read json string as object");
            e.printStackTrace();
        }
        return null;
    }
    private String loadJSONFromAsset(Context context){
        String json = null;
        try {
            InputStream is = context.getAssets().open(JSON_FILE);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    private ArrayList<RecipeItem> getDataFromJson (){
        ArrayList<RecipeItem> recipeItemList = new ArrayList<RecipeItem>();
        JSONArray arr = readJson();
        try{
            for(int i=0; i<arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                String name = obj.getString("name");
                Integer time = Integer.valueOf(obj.getString("time"));
                Integer score = Integer.valueOf(obj.getString("score"));
                String image_id = obj.getString("image_url");
                Integer cat_id = Integer.valueOf(obj.getString("cat_id"));
                String ingredients = obj.getString("ingredients");
                RecipeItem item = new RecipeItem(name,time,score,i+1,image_id,cat_id,ingredients);
                recipeItemList.add(item);
            }
        }catch (JSONException e){
            Log.d(TAG, "getDataFromJson: ERROR in getting json object ");
            e.printStackTrace();
        }


        return recipeItemList;
    }
    private void insertData(){
//       get json and read json
        ArrayList<RecipeItem> recipeItemList = getDataFromJson();
        databaseHelper.cleanTable();
        databaseHelper.cleanTable2();
        boolean result = databaseHelper.insertMultipleRecipe(recipeItemList);
        if(result){
            Toast.makeText(this,"Successfully insert data",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Error in insert data",Toast.LENGTH_SHORT).show();
        }

    }
    private void loadData(Integer catId){
        Log.d("RecipeMenu","load data");
        recipeItemArrayList = databaseHelper.getRecipesByCategory(catId);
        Log.d("RecipeMenu","recipeItemArrayList: "+recipeItemArrayList);
    }

}
