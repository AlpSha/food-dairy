package alpsha;

import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

public class Day {
    private Map<MealType, Meal> meals;
    private LocalDate date;
    private boolean workoutDay;

    public Day(LocalDate date) {
        workoutDay = false;
        meals = new HashMap<>();
        this.date = date;
        meals.put(MealType.BREAKFAST, new Meal(MealType.BREAKFAST));
        meals.put(MealType.MORNING, new Meal(MealType.MORNING));
        meals.put(MealType.LUNCH, new Meal(MealType.LUNCH));
        meals.put(MealType.EVENING, new Meal(MealType.EVENING));
        meals.put(MealType.DINNER, new Meal(MealType.DINNER));
        meals.put(MealType.NIGHT, new Meal(MealType.NIGHT));
    }

    public Day(String date) {
        this(LocalDate.parse(date, Date.formatter));
    }

    public Day(Meal breakfast, Meal morning, Meal lunch, Meal evening, Meal dinner, Meal night, LocalDate date, boolean workoutDay) {
        meals = new HashMap<>();
        meals.put(MealType.BREAKFAST, breakfast);
        meals.put(MealType.MORNING, morning);
        meals.put(MealType.LUNCH, lunch);
        meals.put(MealType.EVENING, evening);
        meals.put(MealType.DINNER, dinner);
        meals.put(MealType.NIGHT, night);
        this.workoutDay = workoutDay;
    }

    public Day(Meal breakfast, Meal morning, Meal lunch, Meal evening, Meal dinner, Meal night, String date, boolean workoutDay) {
        this(breakfast, morning, lunch, evening, dinner, night, LocalDate.parse(date), workoutDay);
    }

    public void addMeal(MealType type) {
        if(meals != null) {
            meals.put(type, new Meal(type));
        }
    }

    public void addServing(MealType type, Serving serving) {
        meals.get(type).addServing(serving);
    }

    public void removeMeal(MealType type) {
        meals.remove(type);
    }

    public void removeAllMeals() {
        meals = new HashMap<>();
    }


    public Meal getMeal(MealType type) {
        return meals.get(type);
    }

    public Map<MealType, Meal> getMeals() {
        return meals;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isWorkoutDay() {
        return workoutDay;
    }

    public void setWorkoutDay(boolean workoutDay) {
        this.workoutDay = workoutDay;
    }

    public double getTotalNutrition(Nutrition nutrition) {
        double total = 0;
        for (Meal m: meals.values()) {
            for(Serving s: m.getServings()) {
                total += s.getNutritionAmount(nutrition);
            }
        }
        return total;
    }
}
