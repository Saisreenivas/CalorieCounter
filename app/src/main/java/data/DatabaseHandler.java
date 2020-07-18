package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Food;

/**
 * Created by Sai sreenivas on 1/30/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private final ArrayList<Food> foodList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME +  "(" + Constants.KEY_ID +
                " INTEGER PRIMARY KEY, " + Constants.FOOD_NAME + " TEXT, " +
                Constants.FOOD_CALORIES_NAME + " INTEGER, " + Constants.DATE_NAME + " LONG);";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME);
        onCreate(db);
    }

    //total items saved
    public int getTotalItems(){

        int totalItems = 0;
        String query = "SELECT * FROM " +  Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        totalItems = cursor.getCount();
        cursor.close();
        db.close();

        return totalItems;
    }

    //get total calories consumed
    public int getTotalCalories(){

        int totalCalories=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + Constants.FOOD_CALORIES_NAME + ") " + "FROM "+
                Constants.TABLE_NAME ;

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            totalCalories = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return totalCalories;
    }

    //delete food Item
    public void deleteFoodItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?", new String[]{String.valueOf(id)});

        db.close();
    }

    //add content to db- add Food
    public void addFoodItem(Food food){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        String name = food.getFoodName();
        String nameMe = name.substring(0,1).toUpperCase() + name.substring(1);
        values.put(Constants.FOOD_NAME, nameMe);
        values.put(Constants.FOOD_CALORIES_NAME, food.getCalories());
        values.put(Constants.DATE_NAME, System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);
        db.close();
    }

    //get all foods
    public ArrayList<Food> getFoods(){
        foodList.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{ Constants.KEY_ID,
                Constants.FOOD_NAME, Constants.FOOD_CALORIES_NAME, Constants.DATE_NAME},
                null, null, null, null, Constants.DATE_NAME + " DESC");

        //loop through....
        if(cursor.moveToFirst()){
            do {
                Food food = new Food();
                food.setFoodId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(Constants.FOOD_CALORIES_NAME)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))));
                food.setRecordDate(date);

                foodList.add(food);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return foodList;
    }
}
