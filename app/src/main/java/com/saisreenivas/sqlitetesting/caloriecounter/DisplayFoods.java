package com.saisreenivas.sqlitetesting.caloriecounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListviewAdapter;
import data.DatabaseHandler;
import model.Food;
import util.Util;

public class DisplayFoods extends AppCompatActivity {

    TextView totalCals, totalItems;
    ListView list;
    DatabaseHandler dba;
    ArrayList<Food> foodList = new ArrayList<>();
    CustomListviewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods);

        totalCals = (TextView) findViewById(R.id.displayTotalCalories);
        totalItems = (TextView) findViewById(R.id.displayTotalItems);
        list = (ListView) findViewById(R.id.list);

        refreshData();
    }

    private void refreshData() {
        foodList.clear();
        dba = new DatabaseHandler(getApplicationContext());
        ArrayList<Food> data = dba.getFoods();

        int calsValue = dba.getTotalCalories();
        int itemsCount = dba.getTotalItems();

        String formattedCals = Util.formatNumber(calsValue);
        String formattedItems = Util.formatNumber(itemsCount);

        totalCals.setText("Total Calories: "+ formattedCals);
        totalItems.setText("Total Items: " + formattedItems);


        for(int i=0;i<data.size();i++){
            String name = data.get(i).getFoodName();
            int calories = data.get(i).getCalories();
            String dateText = data.get(i).getRecordDate();
            int itemId = data.get(i).getFoodId();

            Food myFood = new Food();
            myFood.setFoodName(name);
            myFood.setCalories(calories);
            myFood.setRecordDate(dateText);
            myFood.setFoodId(itemId);

            foodList.add(myFood);
        }

        dba.close();

        //setup adapter
        adapter = new CustomListviewAdapter(DisplayFoods.this, R.layout.list_row, foodList);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DisplayFoods.this, MainActivity.class));
    }
}
