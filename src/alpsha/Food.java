package alpsha;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class Food {
    private String name;
    ObservableList<ServingType> servingTypes;
    private Map<Nutrition, Double> nutritions;


    public Food(String name, Double calorie, Double protein, Double fat, Double carbs, Double fiber, Double cholesterol, Double sodium, Double potassium, Double calcium, Double vitaminA, Double vitaminC, Double iron) {
        this.name = name;
        nutritions = new HashMap<>();
        servingTypes = FXCollections.observableArrayList();
        nutritions.put(Nutrition.CALORIES, calorie);
        nutritions.put(Nutrition.PROTEIN, protein);
        nutritions.put(Nutrition.FAT, fat);
        nutritions.put(Nutrition.CARBS, carbs);
        nutritions.put(Nutrition.FIBER, fiber);
        nutritions.put(Nutrition.CHOLESTEROL, cholesterol);
        nutritions.put(Nutrition.SODIUM, sodium);
        nutritions.put(Nutrition.POTASSIUM, potassium);
        nutritions.put(Nutrition.CALCIUM, calcium);
        nutritions.put(Nutrition.VITAMIN_A, vitaminA);
        nutritions.put(Nutrition.VITAMIN_C, vitaminC);
        nutritions.put(Nutrition.IRON, iron);
    }

    public boolean addServingType(ServingType type) {
        return servingTypes.add(type);
    }

    public boolean removeServingType(ServingType type) {
        return servingTypes.remove(type);
    }



    public Food(String name, Double calorie, Double protein, Double fat, Double carbs) {
        this(name, calorie, protein, fat, carbs, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public double getNutrition(Nutrition nut) {
        return this.nutritions.get(nut);
    }

    public String getName() {
        return name;
    }

    public void setNutrition(Nutrition nut, double value) {
        if(value > 0) {
            this.nutritions.put(nut, value);
        }
    }

    public ServingType getAnyServingType() {
        for(ServingType type: servingTypes) {
            return type;
        }
        return null;
    }

    public ObservableList<ServingType> getServingTypes() {
        return this.servingTypes;
    }

    public ServingType getServingType(String type) {
        for (ServingType st: servingTypes) {
            if(st.getName().equals(type)) {
                return st;
            }
        }
        return null;
    }

}
