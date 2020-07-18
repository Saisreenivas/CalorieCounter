package com.saisreenivas.sqlitetesting.caloriecounter;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class FoodItemDetails extends AppCompatActivity {

    private TextView detailsName, detailsCals, detailsDate;
    private int foodId;
    private Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_details);

        detailsName = (TextView) findViewById(R.id.detailsName);
        detailsCals = (TextView) findViewById(R.id.detailsCals);
        detailsDate = (TextView) findViewById(R.id.detailsDate);
        share = (Button) findViewById(R.id.detailsShare);

        Food food = (Food) getIntent().getSerializableExtra("userObj");

        detailsName.setText(food.getFoodName());
        detailsCals.setText(String.valueOf(food.getCalories()));
        detailsDate.setText(food.getRecordDate());

        foodId = food.getFoodId();

        detailsCals.setTextColor(Color.parseColor("#ff0000"));
        detailsCals.setTextSize(34.9f);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareButton();
            }
        });
    }

    public void shareButton(){

        StringBuilder stringBuilder = new StringBuilder();

        String name = detailsName.getText().toString();
        String calories = detailsCals.getText().toString();
        String date = detailsDate.getText().toString();

        stringBuilder.append(" Food: " + name + "\n");
        stringBuilder.append(" Calories: " + calories + "\n");
        stringBuilder.append(" Date: " + date + "\n");

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "My Calorie Intake");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"saisreenivas222@gmail.com"});
        i.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());

        try{
            startActivity(Intent.createChooser(i, "Send email..."));

        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(), "Please install email client before sending", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu: this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Handle action bar item clicks here. The action bar will
//        automatically handles clicks on the Hone/Up button, so long
//        as you soecify a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        if(id == R.id.deleteItem){

            AlertDialog.Builder builder = new AlertDialog.Builder(FoodItemDetails.this);
            builder.setTitle("Delete?");
            builder.setMessage("Äre you sure you want to delete?");
            builder.setNegativeButton("No", null);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.deleteFoodItem(foodId);
                    int me = dba.getTotalItems();

                    Toast.makeText(getApplicationContext(), "Food Item Deleted!",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(FoodItemDetails.this, DisplayFoods.class));

                    //remove this activity from the activity stack
                    FoodItemDetails.this.finish();
                }
            });
            builder.show();

        }
        return true;
    }
}
