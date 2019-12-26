package alpsha.Data;

import alpsha.Date;
import alpsha.Meal;
import alpsha.MealType;
import alpsha.Serving;
import org.joda.time.LocalDate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MealData extends Queries {
    private MealData() {}
    private static MealData instance = new MealData();
    public static MealData getInstance() {
        return instance;
    }

    public int addMeal(MealType type, LocalDate date) {
        try {
            insertIntoMeals.setString(1, type.toString());
            insertIntoMeals.setString(2, date.toString(Date.formatter));
            insertIntoMeals.execute();
            return insertIntoMeals.getGeneratedKeys().getInt(INDEX_MEALS_ID);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    int getMealId(MealType type, LocalDate date) {
        try {
            queryMealId.setString(1, type.toString());
            queryMealId.setString(2, date.toString(Date.formatter));
            ResultSet resultSet = queryMealId.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(INDEX_MEALS_ID);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


}
