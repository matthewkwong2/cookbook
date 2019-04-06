package com.hkucs.cookbook.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hkucs.cookbook.activities.recipeMenuActivity.RecipeItem;

import java.io.File;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG         = "DatabaseHelper";
    public static String DATABASE_NAME      = "Cookbook";
    private static int DATABASE_VERSION     = 1;
    private static final String TABLE_NAME  = "recipe";
    private static final String KEY         = "recipe_id";
    private static final String CATEGORY_KEY = "category_id";
    private static final String NAME        = "name";
    private static final String TIME        = "time";
    private static final String SCORE       = "score";
    private static final String IMAGE_ID    = "image_id";
    private static Context con;




    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME +
                "("+KEY+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +NAME+" TEXT, "
                +CATEGORY_KEY+" INTEGER, "
                +TIME+" INTEGER, "
                +SCORE+" INTEGER, "
                +IMAGE_ID+ " TEXT"
                +" );";
        if(doesDatabaseExist(con,DATABASE_NAME)){
            db.execSQL(createTable);
        }else{
            Log.d(TAG, "onCreate: The database "+DATABASE_NAME+" exist already.");
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    @Override
    public  void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

//    get all the recipes and their information in the category
    public ArrayList<RecipeItem> getRecipesByCategory(Integer category_id){
        if(category_id < 1 || category_id == null){
            return null;
        }
        ArrayList<RecipeItem> recipeItemList = new ArrayList<RecipeItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+ TABLE_NAME + " WHERE "+CATEGORY_KEY+"="+category_id;
        Cursor c = db.rawQuery(selectQuery,null);
        if(c.moveToFirst()){
            do{
                int cat_id = c.getInt(c.getColumnIndex(CATEGORY_KEY));
                int id = c.getInt(c.getColumnIndex(KEY));
                int time = c.getInt(c.getColumnIndex(TIME));
                int score = c.getInt(c.getColumnIndex(SCORE));
                String name = c.getString(c.getColumnIndex(NAME));
                String image_id = c.getString(c.getColumnIndex(IMAGE_ID));
                RecipeItem item = new RecipeItem(name,time,score,id,image_id,cat_id);

                recipeItemList.add(item);
            }while (c.moveToNext());
        }

        return recipeItemList;

    }

//    insert multiple recipe
    public boolean insertMultipleRecipe(ArrayList<RecipeItem> recipeItemList){
        if(recipeItemList == null)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();
        String columnStr =
                CATEGORY_KEY+","
                +NAME+","
                +TIME+","
                +SCORE+","
                +IMAGE_ID;

        StringBuilder contentStr = new StringBuilder();
        int listLen = recipeItemList.size();
        for( RecipeItem item:  recipeItemList){
            String itemStr = "("
                    +item.getCatId()+","
                    +item.getName()+","
                    +item.getTime()+","
                    +item.getScore()+","
                    +item.getDrawableId()
                    +")";
            listLen -= 1;
            if(listLen >0)
                itemStr += ",";
            contentStr.append(itemStr);
        }
        String queryStr = "INSERT INTO "+TABLE_NAME+" ("+columnStr+") VALUES "+contentStr.toString();
        try{
            db.execSQL(queryStr);
        }catch (SQLException e){
            Log.d(TAG,"ERROR: "+e);
            return false;
        }
        return true;

    }

//    do checking
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public  void cleanTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    }

