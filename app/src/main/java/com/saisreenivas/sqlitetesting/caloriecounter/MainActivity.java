package com.saisreenivas.sqlitetesting.caloriecounter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {

    private EditText name, amount;
    private Button submit;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.mainName);
        amount = (EditText) findViewById(R.id.mainCalories);
        submit = (Button) findViewById(R.id.mainButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });
    }

    private void saveToDB() {
        databaseHandler = new DatabaseHandler(getApplicationContext());
        Food food = new Food();
        String nameItem, calories;
        nameItem = name.getText().toString().trim();
        calories = amount.getText().toString().trim();


        if(nameItem.equals("") || calories.equals("")){
            Toast.makeText(getApplicationContext(), "No empty fields allowed", Toast.LENGTH_SHORT).show();
        }
        else {
            int cals = Integer.parseInt(calories);
            food.setFoodName(nameItem);
            food.setCalories(cals);
            databaseHandler.addFoodItem(food);
            databaseHandler.close();

            //clear the form
            name.setText("");
            amount.setText("");

            //take users to next screen
            Intent i = new Intent(getApplicationContext(), DisplayFoods.class);
            startActivity(i);
        }
    }
}
