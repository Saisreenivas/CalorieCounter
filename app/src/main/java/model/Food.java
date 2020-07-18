package model;

import java.io.Serializable;

/**
 * Created by Sai sreenivas on 1/30/2017.
 */

public class Food implements Serializable {
    private static final long serialVersionUID = 10L;
    private int foodId;
    private String foodName;
    private int calories;
    private String recordDate;

    public Food(int foodId, String foodName, int calories, String recordDate) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.calories = calories;
        this.recordDate = recordDate;
    }

    public Food() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
