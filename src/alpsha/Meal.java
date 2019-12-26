package alpsha;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Meal {
    private ObservableList<Serving> servings;
    private MealType type;

    public Meal(MealType type) {
        servings = FXCollections.observableArrayList();
        this.type = type;
    }

    public MealType getType() {
        return type;
    }

    public double getTotalNutrition(Nutrition nut) {
        if(servings == null) {
            return 0;
        }
        double total = 0;
        for (Serving s: servings) {
            total += s.getNutritionAmount(nut);
        }
        return total;
    }

    public void addServing(Serving serving) {
        if(serving == null) {
            return;
        }
        servings.add(serving);
    }

    public ObservableList<Serving> getServings() {
        return this.servings;
    }

    public void removeServing(Serving serving) {
        servings.remove(serving);
    }

    public void removeServing(Food food) {
        Serving toRemove = null;
        for(Serving s: servings) {
            if (s.getFood().equals(food)) {
                toRemove = s;
                break;
            }
        }
        servings.remove(toRemove);
    }

    public void changeQuantity(double quantity, Food food) {
        for(Serving s: servings) {
            if(s.getFood().equals(food)) {
                s.setUnits(quantity);
                return;
            }
        }
    }

    public void changeServingType(ServingType type, Food food) {
        for(Serving s: servings) {
            if (s.getFood().equals(food)) {
                s.setType(type);
                return;
            }
        }
    }
}
