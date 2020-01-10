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
        try {
            queryDayByDate.setString(1, date.toString(Date.formatter));
            ResultSet resultSet = queryDayByDate.executeQuery();
            while (resultSet.next()) {
                MealType type = MealType.valueOf(resultSet.getString(INDEX_DAY_MEAL_TYPE).toUpperCase());
                boolean workoutDay = resultSet.getBoolean(INDEX_DAY_WORKOUT_DAY);
                String foodName = resultSet.getString(INDEX_DAY_FOOD_NAME);
                String servingType = resultSet.getString(INDEX_DAY_SERVING_TYPE);
                double unitsOfServing = resultSet.getDouble(INDEX_DAY_UNITS);
                double contentPerServing = resultSet.getDouble(INDEX_DAY_SERVING_CONTENT);

                Food food = foodData.foods.get(foodName);
                day.addServing(type, new Serving(new ServingType(servingType, contentPerServing), unitsOfServing, food));
                day.setWorkoutDay(workoutDay);
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

    public boolean queryDayExists(Day day) {
        try {
            queryDayByDate.setString(1, day.getDate().toString(Date.formatter));
            ResultSet set = queryDayByDate.executeQuery();
            if(set.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createDayIfNotExists(Day day) {
        if ( !queryDayExists(day)) {
            try {
                insertIntoDays.setString(1, day.getDate().toString(Date.formatter));
                insertIntoDays.setBoolean(2, false);
                insertIntoDays.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}


