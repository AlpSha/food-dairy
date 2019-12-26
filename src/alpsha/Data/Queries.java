package alpsha.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class Queries {
    public static final String DB_NAME = "food_diary.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + System.getProperty("user.dir") + "/" + DB_NAME;
    static {
        System.out.println(CONNECTION_STRING);
    }

    public static final String TABLE_FOODS = "foods";
    public static final String COLUMN_FOODS_ID = "id";
    public static final String COLUMN_FOODS_NAME = "name";
    public static final String COLUMN_FOODS_CALORIES = "calories";
    public static final String COLUMN_FOODS_PROTEIN = "protein";
    public static final String COLUMN_FOODS_FAT = "fat";
    public static final String COLUMN_FOODS_CARBS = "carbs";
    public static final String COLUMN_FOODS_FIBER = "fiber";
    public static final String COLUMN_FOODS_CHOLESTEROL = "cholesterol";
    public static final String COLUMN_FOODS_SODIUM = "sodium";
    public static final String COLUMN_FOODS_POTASSIUM = "potassium";
    public static final String COLUMN_FOODS_CALCIUM = "calcium";
    public static final String COLUMN_FOODS_VITAMIN_A = "vitaminA";
    public static final String COLUMN_FOODS_VITAMIN_C = "vitaminC";
    public static final String COLUMN_FOODS_IRON = "iron";
    public static final int INDEX_FOODS_ID = 1;
    public static final int INDEX_FOODS_NAME = 2;
    public static final int INDEX_FOODS_CALORIES = 3;
    public static final int INDEX_FOODS_PROTEIN = 4;
    public static final int INDEX_FOODS_FAT = 5;
    public static final int INDEX_FOODS_CARBS = 6;
    public static final int INDEX_FOODS_FIBER = 7;
    public static final int INDEX_FOODS_CHOLESTEROL = 8;
    public static final int INDEX_FOODS_SODIUM = 9;
    public static final int INDEX_FOODS_POTASSIUM = 10;
    public static final int INDEX_FOODS_CALCIUM = 11;
    public static final int INDEX_FOODS_VITAMIN_A = 12;
    public static final int INDEX_FOODS_VITAMIN_C = 13;
    public static final int INDEX_FOODS_IRON = 14;


    public static final String TABLE_MEALS = "meals";
    public static final String COLUMN_MEALS_ID = "id";
    public static final String COLUMN_MEALS_TYPE = "type";
    public static final String COLUMN_MEALS_DATE = "date";
    public static final int INDEX_MEALS_ID = 1;
    public static final int INDEX_MEALS_TYPE = 2;
    public static final int INDEX_MEALS_DATE = 3;


    public static final String TABLE_SERVINGS = "servings";
    public static final String COLUMN_SERVINGS_MEAL_ID = "meal_id";
    public static final String COLUMN_SERVINGS_FOOD_ID = "food_id";
    public static final String COLUMN_SERVINGS_UNITS = "units";
    public static final String COLUMN_SERVINGS_SERVING_TYPE = "serving_type";
    public static final String COLUMN_SERVINGS_SERVING_CONTENT = "serving_content";
    public static final int INDEX_SERVINGS_MEAL_ID = 1;
    public static final int INDEX_SERVINGS_FOOD_ID = 2;
    public static final int INDEX_SERVINGS_UNITS = 3;
    public static final int INDEX_SERVINGS_SERVING_TYPE = 4;
    public static final int INDEX_SERVINGS_SERVING_CONTENT = 5;


    public static final String TABLE_SERVING_TYPES = "serving_types";
    public static final String COLUMN_SERVING_TYPES_FOOD_ID = "food_id";
    public static final String COLUMN_SERVING_TYPES_SERVING_NAME = "serving_name";
    public static final String COLUMN_SERVING_TYPES_SERVING_CONTENT = "serving_content";
    public static final int INDEX_SERVING_TYPES_FOOD_ID = 1;
    public static final int INDEX_SERVING_TYPES_SERVING_NAME = 2;
    public static final int INDEX_SERVING_TYPES_SERVING_CONTENT = 3;


    public static final String VIEW_DAY = "day_view";
    public static final String COLUMN_DAY_WORKOUT_DAY = "workout_day";
    public static final String COLUMN_DAY_DATE = "date";
    public static final String COLUMN_DAY_MEAL_TYPE = "meal_type";
    public static final String COLUMN_DAY_FOOD_NAME = "food_name";
    public static final String COLUMN_DAY_SERVING_TYPE = "serving_type";
    public static final String COLUMN_DAY_UNITS = "units";
    public static final String COLUMN_DAY_SERVING_CONTENT = "serving_content";
    public static final int INDEX_DAY_DATE = 1;
    public static final int INDEX_DAY_WORKOUT_DAY = 2;
    public static final int INDEX_DAY_MEAL_TYPE = 3;
    public static final int INDEX_DAY_FOOD_NAME = 4;
    public static final int INDEX_DAY_SERVING_TYPE = 5;
    public static final int INDEX_DAY_UNITS = 6;
    public static final int INDEX_DAY_SERVING_CONTENT = 7;


    public static final String TABLE_DAYS = "days";
    public static final String COLUMN_TABLEDAYS_DATE = "date";
    public static final String COLUMN_TABLEDAYS_WORKOUT_DAY = "workout_day";
    public static final int INDEX_TABLEDAYS_DATE = 1;
    public static final int INDEX_TABLEDAYS_WORKOUT_DAY = 2;



    /************* food Table Statements *********/
    static final String QUERY_FOODS = "SELECT * FROM " + TABLE_FOODS;
    static final String QUERY_FOOD_BY_NAME = "SELECT * FROM " + TABLE_FOODS +
            " WHERE " + COLUMN_FOODS_NAME + " = ?";

    static final String UPDATE_FOOD_BY_NAME = "UPDATE " + TABLE_FOODS + " SET " +
            COLUMN_FOODS_CALORIES + " = ?, " +
            COLUMN_FOODS_PROTEIN + " = ?, " +
            COLUMN_FOODS_FAT + " = ?, " +
            COLUMN_FOODS_CARBS + " = ?, " +
            COLUMN_FOODS_FIBER + " = ?, " +
            COLUMN_FOODS_CHOLESTEROL + " = ?, " +
            COLUMN_FOODS_SODIUM + " = ?, " +
            COLUMN_FOODS_POTASSIUM + " = ?, " +
            COLUMN_FOODS_CALCIUM + " = ?, " +
            COLUMN_FOODS_VITAMIN_A + " = ?, " +
            COLUMN_FOODS_VITAMIN_C + " = ?, " +
            COLUMN_FOODS_IRON + " = ? WHERE " +
            COLUMN_FOODS_NAME + " = ? ";


    /************ days VIEW Statements **********/
    static final String QUERY_DAY_BY_DATE = "SELECT * FROM " + VIEW_DAY + " WHERE " + COLUMN_DAY_DATE + " = ?";


    /*********** serving_types Table Statements *****/
    static final String QUERY_SERVING_TYPE_BY_FOOD_NAME = "SELECT * FROM " +
            TABLE_SERVING_TYPES +
            " INNER JOIN " + TABLE_FOODS + " ON " +
            TABLE_FOODS + "." + COLUMN_FOODS_ID + " = " +
            TABLE_SERVING_TYPES + "." + COLUMN_SERVING_TYPES_FOOD_ID +
            " WHERE " + TABLE_FOODS + "." + COLUMN_FOODS_NAME + " = ?";


    /********** servings Table Statements *******/
    static final String INSERT_INTO_SERVINGS = "INSERT INTO " + TABLE_SERVINGS +
            " VALUES (?, ?, ?, ?, ?)";

    static final String UPDATE_SERVING = "UPDATE " + TABLE_SERVINGS +
            " SET " + COLUMN_SERVINGS_UNITS + " = ?, " +
            COLUMN_SERVINGS_SERVING_TYPE + " = ?, "
            + COLUMN_SERVINGS_SERVING_CONTENT + " = ? WHERE " +
            COLUMN_SERVINGS_MEAL_ID + " = ? AND " +
            COLUMN_SERVINGS_FOOD_ID + " = ? AND " +
            COLUMN_SERVINGS_SERVING_TYPE + " = ?";

    static final String DELETE_FROM_SERVINGS = "DELETE FROM " + TABLE_SERVINGS +
            " WHERE " + COLUMN_SERVINGS_MEAL_ID + " = ? AND " +
            COLUMN_SERVINGS_FOOD_ID + " = ? AND " +
            COLUMN_SERVINGS_SERVING_TYPE + " = ?";


    /********** meals Table Statements ********/
    static final String INSERT_INTO_MEALS = "INSERT INTO " + TABLE_MEALS + "(" +
            COLUMN_MEALS_TYPE + ", " + COLUMN_MEALS_DATE + ") VALUES (?, ?)";
    static final String QUERY_MEAL_ID = "SELECT * FROM " + TABLE_MEALS +
            " WHERE " + COLUMN_MEALS_TYPE + " = ? AND " +
            COLUMN_MEALS_DATE + " = ?";


    /********* days Table Statements ********/
    static final String UPDATE_DAY_STATUS = "UPDATE " + TABLE_DAYS +
            " SET " + COLUMN_TABLEDAYS_WORKOUT_DAY + " = ? WHERE " +
            COLUMN_TABLEDAYS_DATE + " = ?";
    static final String QUERY_DAY_STATUS = "SELECT " + COLUMN_TABLEDAYS_WORKOUT_DAY +
            " FROM " + TABLE_DAYS + " WHERE " + COLUMN_TABLEDAYS_DATE + " = ?";


    static Connection conn;

    static PreparedStatement queryFoods;
    static PreparedStatement queryFoodByName;
    static PreparedStatement queryDayByDate;
    static PreparedStatement queryServingTypeByFoodName;
    static PreparedStatement updateFoodByName;

    static PreparedStatement insertIntoMeals;
    static PreparedStatement queryMealId;

    static PreparedStatement insertIntoServings;
    static PreparedStatement updateServing;
    static PreparedStatement deleteFromServings;

    static PreparedStatement updateDayStatus;
    static PreparedStatement queryDayStatus;



    static List<PreparedStatement> preparedStatements = new ArrayList<>();

}
