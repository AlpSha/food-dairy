package alpsha.Data;

import alpsha.*;
import org.joda.time.LocalDate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DayData extends Queries {

    private DayData() {}
    private static DayData instance = new DayData();
    public static DayData getInstance() {
        return instance;
    }

    public Day queryDayByDate(LocalDate date) {
        FoodData foodData = FoodData.getInstance();
        Day day = new Day(date);
        boolean workoutDay = false;
        try {
            queryDayByDate.setString(1, date.toString(Date.formatter));
            ResultSet resultSet = queryDayByDate.executeQuery();
            while (resultSet.next()) {
                MealType type = MealType.valueOf(resultSet.getString(INDEX_DAY_MEAL_TYPE).toUpperCase());
                workoutDay = resultSet.getBoolean(INDEX_DAY_WORKOUT_DAY);
                String foodName = resultSet.getString(INDEX_DAY_FOOD_NAME);
                String servingType = resultSet.getString(INDEX_DAY_SERVING_TYPE);
                double unitsOfServing = resultSet.getDouble(INDEX_DAY_UNITS);
                double contentPerServing = resultSet.getDouble(INDEX_DAY_SERVING_CONTENT);

                Food food = foodData.foods.get(foodName);
                day.addServing(type, new Serving(new ServingType(servingType, contentPerServing), unitsOfServing, food));
            }
            return day;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateDayStatus(Day day, boolean isWorkoutDay) {
        try {
            updateDayStatus.setBoolean(1, isWorkoutDay);
            updateDayStatus.setString(2, day.getDate().toString(Date.formatter));
            updateDayStatus.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean queryDayStatus(Day day) {
        try {
            queryDayStatus.setString(1, day.getDate().toString(Date.formatter));
            ResultSet resultSet = queryDayStatus.executeQuery();
            if(resultSet.next()) {
                return resultSet.getBoolean(1);
            } else {
                throw new SQLException("Day couldn't be found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}


