package com.hkucs.cookbook.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hkucs.cookbook.activities.recipeMenuActivity.RecipeItem;
import com.hkucs.cookbook.activities.slideshow.Procedure;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    public static String DATABASE_NAME = "Cookbook";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "recipe";
    private static final String KEY = "recipe_id";
    private static final String CATEGORY_KEY = "category_id";
    private static final String NAME = "name";
    private static final String TIME = "time";
    private static final String SCORE = "score";
    private static final String IMAGE_ID = "image_id";
    private static final String INGREDIENTS = "ingredients";
    private static final String TABLE2_NAME = "procedure";
    private static final String STEP = "step";
    private static final String RECIPEID = "recipeId";
    private static final String DESCRIPTION = "description";
    private static final String IMGNAME = "imgName";
    public static String DATABASE_NAME = "Cookbook";
    private static int DATABASE_VERSION = 6;
    private static Context con;
    private SQLiteDatabase db;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        con = context;
    }

    //    do checking
    private boolean isTableExist(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + TABLE_NAME + "'", null);
        boolean isExist = cursor.getCount() != 0;
        cursor.close();
        return isExist;

    }

    public boolean isDatabaseEmpty() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
        boolean isEmpty = cursor.getCount() == 0;
        cursor.close();
        return isEmpty;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       System.out.println("on db helper create");
        String createTable = "CREATE TABLE " + TABLE_NAME +
                "(" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + CATEGORY_KEY + " INTEGER, "
                + TIME + " INTEGER, "
                + SCORE + " INTEGER, "
                + IMAGE_ID + " TEXT, "
                + INGREDIENTS + " TEXT"
                + " );";

        String createStepTable = "CREATE TABLE " + TABLE2_NAME +
                "(" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RECIPEID + " INTEGER, "
                + STEP + " INTEGER, "
                + IMGNAME + " TEXT, "
                + DESCRIPTION + " TEXT"
                + " );";

        if (!isTableExist(db)) {
            db.execSQL(createTable);
            db.execSQL(createStepTable);
        } else {
            Log.d(TAG, "onCreate: The database " + DATABASE_NAME + " exist already.");
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);
    }

    //    get all the recipes and their information in the category
    public ArrayList<RecipeItem> getRecipesByCategory(Integer category_id) {
        if (category_id < 1 || category_id == null) {
            return null;
        }
        ArrayList<RecipeItem> recipeItemList = new ArrayList<RecipeItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + CATEGORY_KEY + "=" + category_id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                int cat_id = c.getInt(c.getColumnIndex(CATEGORY_KEY));
                int id = c.getInt(c.getColumnIndex(KEY));
                int time = c.getInt(c.getColumnIndex(TIME));
                int score = c.getInt(c.getColumnIndex(SCORE));
                String name = c.getString(c.getColumnIndex(NAME));
                String image_id = c.getString(c.getColumnIndex(IMAGE_ID));
                String ingredients = c.getString(c.getColumnIndex(INGREDIENTS));
                RecipeItem item = new RecipeItem(name, time, score, id, image_id, cat_id, ingredients);

                recipeItemList.add(item);
            } while (c.moveToNext());
        }
        c.close();

        return recipeItemList;

    }

    public RecipeItem getRecipesById(Integer itemId) {
        if (itemId == null) {
            return null;
        }
        RecipeItem recipe = new RecipeItem();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY + "=" + itemId;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToNext()) {
            int cat_id = c.getInt(c.getColumnIndex(CATEGORY_KEY));
            int id = c.getInt(c.getColumnIndex(KEY));
            int time = c.getInt(c.getColumnIndex(TIME));
            int score = c.getInt(c.getColumnIndex(SCORE));
            String name = c.getString(c.getColumnIndex(NAME));
            String image_id = c.getString(c.getColumnIndex(IMAGE_ID));
            String ingredients = c.getString(c.getColumnIndex(INGREDIENTS));
            recipe = new RecipeItem(name, time, score, id, image_id, cat_id, ingredients);
        }

        c.close();
        return recipe;

    }

    public ArrayList<Procedure> getProcedureById(Integer repId) {
        if (repId < 1 || repId == null) {
            return null;
        }
        ArrayList<Procedure> proceduresList = new ArrayList<Procedure>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE2_NAME + " WHERE " + RECIPEID + "=" + repId + " ORDER BY " + STEP;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(RECIPEID));
                int step = c.getInt(c.getColumnIndex(STEP));
                String imgName = c.getString(c.getColumnIndex(IMGNAME));
                ;
                String description = c.getString(c.getColumnIndex(DESCRIPTION));
                ;
                Procedure p = new Procedure(id, step, imgName, description);

                proceduresList.add(p);
            } while (c.moveToNext());
        }
        c.close();

        return proceduresList;

    }

    //    insert multiple recipe
    public boolean insertMultipleRecipe(ArrayList<RecipeItem> recipeItemList) {
        if (recipeItemList == null)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();
        String columnStr =
                CATEGORY_KEY + ","
                        + NAME + ","
                        + TIME + ","
                        + SCORE + ","
                        + IMAGE_ID + ","
                        + INGREDIENTS;

        StringBuilder contentStr = new StringBuilder();
        int listLen = recipeItemList.size();
        for (RecipeItem item : recipeItemList) {
            String itemStr = "("
                    + item.getCatId() + ","
                    + item.getName() + ","
                    + item.getTime() + ","
                    + item.getScore() + ","
                    + item.getDrawableId() + ","
                    + item.getIngredients()
                    + ")";
            listLen -= 1;
            if (listLen > 0)
                itemStr += ",";
            contentStr.append(itemStr);
        }
        String queryStr = "INSERT INTO " + TABLE_NAME + " (" + columnStr + ") VALUES " + contentStr.toString();
        try {
            db.execSQL(queryStr);
        } catch (SQLException e) {
            Log.d(TAG, "ERROR: " + e);
            return false;
        }
        return true;

    }

    public boolean insertMultipleProcedure(ArrayList<Procedure> procedureList) {
        if (procedureList == null || procedureList.isEmpty()) {
            Log.d("HI", "Empty List");
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String columnStr =
                RECIPEID + ","
                        + STEP + ","
                        + IMGNAME + ","
                        + DESCRIPTION;
        Log.d("HI", columnStr);
        StringBuilder contentStr2 = new StringBuilder();
        int listLen = procedureList.size();
        for (Procedure p : procedureList) {
            String pStr = "("
                    + p.getId() + ","
                    + p.getStep() + ","
                    + p.getImgPath() + ","
                    + p.getDescription()
                    + ")";
            listLen -= 1;
            if (listLen > 0)
                pStr += ",";
            contentStr2.append(pStr);
            Log.d("HI", pStr);
        }
        String queryStr = "INSERT INTO " + TABLE2_NAME + " (" + columnStr + ") VALUES " + contentStr2.toString();
        Log.d("HI", queryStr);
        try {
            db.execSQL(queryStr);
        } catch (SQLException e) {
            Log.d("hi", "ERROR: " + e);
            return false;
        }
        return true;

    }

    public void cleanTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }

    public void cleanTable2() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE2_NAME);
        Log.d("hi", "cleanTable2: ok");
    }

}

