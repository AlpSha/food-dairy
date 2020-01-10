package alpsha.Data;

import alpsha.Food;
import alpsha.Nutrition;
import alpsha.ServingType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodData extends Queries {

    private FoodData() {}
    private static FoodData instance = new FoodData();
    public static FoodData getInstance() {
        return instance;
    }

    public ObservableMap<String, Food> foods = FXCollections.observableHashMap();

    public Food getTargetDay(boolean isWorkoutDay) {
        if(isWorkoutDay) {
            return foods.get("target_workout");
        } else {
            return foods.get("target_rest");
        }
    }

    public void readAllFoods() {
        try {
            ResultSet resultSet = queryFoods.executeQuery();
            while(resultSet.next()) {
                String name = resultSet.getString(INDEX_FOODS_NAME);
                double calories = resultSet.getDouble(INDEX_FOODS_CALORIES);
                double protein = resultSet.getDouble(INDEX_FOODS_PROTEIN);
                double fat = resultSet.getDouble(INDEX_FOODS_FAT);
                double carbs = resultSet.getDouble(INDEX_FOODS_CARBS);
                double fiber = resultSet.getDouble(INDEX_FOODS_FIBER);
                double cholesterol = resultSet.getDouble(INDEX_FOODS_CHOLESTEROL);
                double sodium = resultSet.getDouble(INDEX_FOODS_SODIUM);
                double potassium = resultSet.getDouble(INDEX_FOODS_POTASSIUM);
                double calcium = resultSet.getDouble(INDEX_FOODS_CALCIUM);
                double vitaminA = resultSet.getDouble(INDEX_FOODS_VITAMIN_A);
                double vitaminC = resultSet.getDouble(INDEX_FOODS_VITAMIN_C);
                double iron = resultSet.getDouble(INDEX_FOODS_IRON);

                foods.put(name, new Food(name, calories, protein, fat, carbs, fiber, cholesterol, sodium, potassium, calcium, vitaminA, vitaminC, iron));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ServingType> readServingTypesOfFood(Food food) {
        ArrayList<ServingType> list = new ArrayList<>();
        try {
            queryServingTypeByFoodName.setString(1, food.getName());
            ResultSet resultSet = queryServingTypeByFoodName.executeQuery();

            while(resultSet.next()) {
                String servingName = resultSet.getString(INDEX_SERVING_TYPES_SERVING_NAME);
                double servingContent = resultSet.getDouble(INDEX_SERVING_TYPES_SERVING_CONTENT);
                list.add(new ServingType(servingName, servingContent));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int queryFoodId(Food food) {
        try {
            queryFoodByName.setString(1, food.getName());
            ResultSet resultSet = queryFoodByName.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(INDEX_FOODS_ID);
            } else {
                throw new SQLException("Food not exist");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public void updateFood(Food food) {
        try {
            updateFoodByName.setDouble(1, food.getNutrition(Nutrition.CALORIES));
            updateFoodByName.setDouble(2, food.getNutrition(Nutrition.PROTEIN));
            updateFoodByName.setDouble(3, food.getNutrition(Nutrition.FAT));
            updateFoodByName.setDouble(4, food.getNutrition(Nutrition.CARBS));
            updateFoodByName.setDouble(5, food.getNutrition(Nutrition.FIBER));
            updateFoodByName.setDouble(6, food.getNutrition(Nutrition.CHOLESTEROL));
            updateFoodByName.setDouble(7, food.getNutrition(Nutrition.SODIUM));
            updateFoodByName.setDouble(8, food.getNutrition(Nutrition.POTASSIUM));
            updateFoodByName.setDouble(9, food.getNutrition(Nutrition.CALCIUM));
            updateFoodByName.setDouble(10, food.getNutrition(Nutrition.VITAMIN_A));
            updateFoodByName.setDouble(11, food.getNutrition(Nutrition.VITAMIN_C));
            updateFoodByName.setDouble(12, food.getNutrition(Nutrition.IRON));
            updateFoodByName.setString(13, food.getName());
            updateFoodByName.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
