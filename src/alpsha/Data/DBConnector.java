package alpsha.Data;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector extends Queries {
    private DBConnector(){}

    private static DBConnector connector = new DBConnector();
    public static DBConnector getInstance() {
        return connector;
    }



    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

            preparedStatements.add(queryFoods = conn.prepareStatement(QUERY_FOODS));
            preparedStatements.add(queryDayByDate = conn.prepareStatement(QUERY_DAY_BY_DATE));
            preparedStatements.add(queryServingTypeByFoodName = conn.prepareStatement(QUERY_SERVING_TYPE_BY_FOOD_NAME));
            preparedStatements.add(insertIntoMeals = conn.prepareStatement(INSERT_INTO_MEALS));
            preparedStatements.add(queryMealId = conn.prepareStatement(QUERY_MEAL_ID));
            preparedStatements.add(queryFoodByName = conn.prepareStatement(QUERY_FOOD_BY_NAME));
            preparedStatements.add(insertIntoServings = conn.prepareStatement(INSERT_INTO_SERVINGS));
            preparedStatements.add(updateServing = conn.prepareStatement(UPDATE_SERVING));
            preparedStatements.add(deleteFromServings = conn.prepareStatement(DELETE_FROM_SERVINGS));
            preparedStatements.add(updateDayStatus = conn.prepareStatement(UPDATE_DAY_STATUS));
            preparedStatements.add(queryDayStatus = conn.prepareStatement(QUERY_DAY_STATUS));
            preparedStatements.add(updateFoodByName = conn.prepareStatement(UPDATE_FOOD_BY_NAME));
            preparedStatements.add(insertIntoDays = conn.prepareStatement(INSERT_INTO_DAYS));


            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean close() {
        //TODO
        return false;
    }
}
