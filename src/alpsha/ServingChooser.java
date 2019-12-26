package alpsha;

public class ServingChooser {

    public void addServing(ServingType servingType, double quantity, Food food, Day day, MealType mealType) {
        if(day.getMeal(mealType) == null) {
            day.addMeal(mealType);
        }
        day.addServing(mealType, new Serving(servingType, quantity, food));
    }


    public void addServing(ServingType type, double quantity, Food food, Meal meal) {
        meal.addServing(new Serving(type, quantity, food));
    }

    public void removeServing(Food food, Meal meal) {
        meal.removeServing(food);
    }

    public void changeQuantity(double quantity, Food food, Meal meal) {
        meal.changeQuantity(quantity, food);
    }

    public void changeServingType(ServingType type, Food food, Meal meal) {
        meal.changeServingType(type, food);
    }
}
