package data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.saisreenivas.sqlitetesting.caloriecounter.FoodItemDetails;
import com.saisreenivas.sqlitetesting.caloriecounter.R;

import java.util.ArrayList;

import model.Food;

/**
 * Created by Sai sreenivas on 1/30/2017.
 */

public class CustomListviewAdapter extends ArrayAdapter<Food> {

    private int layoutresource;
    private Activity activity;
    private ArrayList<Food> foodList = new ArrayList<>();

    public CustomListviewAdapter(Activity act, int resource, ArrayList<Food> data) {
        super(act, resource, data);
        layoutresource = resource;
        activity = act;
        foodList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Nullable
    @Override
    public Food getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public int getPosition(Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewRow = convertView;
        ViewHolder viewHolder = null;
        if(viewRow == null || (viewRow.getTag() == null)){

            LayoutInflater inflater = LayoutInflater.from(activity);
            viewRow = inflater.inflate(layoutresource, null);
            viewHolder = new ViewHolder();

            viewHolder.foodName = (TextView) viewRow.findViewById(R.id.rowTitle);
            viewHolder.foodCalories = (TextView) viewRow.findViewById(R.id.rowCalories);
            viewHolder.foodDate = (TextView) viewRow.findViewById(R.id.rowDate);

            viewRow.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) viewRow.getTag();
        }

        viewHolder.food = getItem(position);

        viewHolder.foodName.setText(viewHolder.food.getFoodName());
        viewHolder.foodCalories.setText(String.valueOf(viewHolder.food.getCalories()));
        viewHolder.foodDate.setText(viewHolder.food.getRecordDate());


        final ViewHolder finalViewHolder = viewHolder;
        viewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FoodItemDetails.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("userObj", finalViewHolder.food);
                intent.putExtras(mBundle);

                activity.startActivity(intent);
            }
        });
        return viewRow;
    }

    public class ViewHolder{
        Food food;
        TextView foodName, foodCalories, foodDate;
    }
}
