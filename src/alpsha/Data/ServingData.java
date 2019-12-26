package alpsha.Data;

import alpsha.MealType;
import alpsha.Serving;
import alpsha.ServingType;
import org.joda.time.LocalDate;

import java.sql.SQLException;

public class ServingData extends Queries {
    private ServingData() {}
    private static ServingData instance = new ServingData();
    public static ServingData getInstance() {
        return instance;
    }

    public void addServing(MealType type, LocalDate date, Serving serving) {
        MealData mdInstance = MealData.getInstance();
        int mealId = mdInstance.getMealId(type, date);
        if(mealId == -1) {
            mealId = mdInstance.addMeal(type, date);
        }

        int foodId = FoodData.getInstance().queryFoodId(serving.getFood());

        try {
            insertIntoServings.setInt(INDEX_SERVINGS_MEAL_ID, mealId);
            insertIntoServings.setInt(INDEX_SERVINGS_FOOD_ID, foodId);
            insertIntoServings.setDouble(INDEX_SERVINGS_UNITS, serving.getUnits());
            insertIntoServings.setString(INDEX_SERVINGS_SERVING_TYPE, serving.getType().getName());
            insertIntoServings.setDouble(INDEX_SERVINGS_SERVING_CONTENT, serving.getType().getGrams());
            insertIntoServings.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateServing(MealType mealType, LocalDate date, ServingType oldType,  Serving serving) {
        MealData mdInstance = MealData.getInstance();
        int mealId = mdInstance.getMealId(mealType, date);
        if(mealId == -1) {
            mealId = mdInstance.addMeal(mealType, date);
        }

        int foodId = FoodData.getInstance().queryFoodId(serving.getFood());

        try {
            updateServing.setDouble(1, serving.getUnits());
            updateServing.setString(2, serving.getType().getName());
            updateServing.setDouble(3, serving.getType().getGrams());
            updateServing.setInt(4, mealId);
            updateServing.setInt(5, foodId);
            updateServing.setString(6, oldType.getName());
            updateServing.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeServing(MealType mealType, LocalDate date, Serving serving) {
        MealData mdInstance = MealData.getInstance();
        int mealId = mdInstance.getMealId(mealType, date);
        if(mealId == -1) {
            mealId = mdInstance.addMeal(mealType, date);
        }

        int foodId = FoodData.getInstance().queryFoodId(serving.getFood());

        try {
            deleteFromServings.setInt(1, mealId);
            deleteFromServings.setInt(2, foodId);
            deleteFromServings.setString(3, serving.getType().getName());
            deleteFromServings.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
