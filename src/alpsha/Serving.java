package alpsha;

import alpsha.util.StringFormatter;
import javafx.beans.property.SimpleStringProperty;

import java.text.DecimalFormat;

public class Serving {
    private ServingType type;
    private double units;
    private Food food;
    private SimpleStringProperty itemStringProperty;

    public Serving(ServingType type, double units, Food food) {
        if(food == null) {
            throw new NullPointerException("Food can't be null");
        }
        this.type = type;
        this.units = units;
        this.food = food;
        this.itemStringProperty = new SimpleStringProperty();
        updateItemStringProperty();
    }

    public double getNutritionAmount(Nutrition nut) {
        // Values in alpsha.ServingType are based on 100 grams of food
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(food.getNutrition(nut) * units * type.getGrams() / 100)) ;
    }

    public ServingType getType() {
        return type;
    }

    public void setType(ServingType type) {
        this.type = type;
        updateItemStringProperty();
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
        updateItemStringProperty();
    }

    public Food getFood() {
        return food;
    }

    public SimpleStringProperty getItemStringProperty() {
        return itemStringProperty;
    }

    public void updateItemStringProperty() {
        StringBuilder sb = new StringBuilder();
        sb.append(units);
        sb.append(" ");
        sb.append(type.getName());
        sb.append(": ");

        String prefix = "";
        for(Nutrition n: Nutrition.values()) {
            double amount = getNutritionAmount(n);
            if(amount != 0) {
                sb.append(prefix);
                sb.append(StringFormatter.capitalizeWord(n.toString()));
                sb.append(" - ");
                sb.append(amount);
                prefix = ", ";
            }
        }
        this.itemStringProperty.setValue(sb.toString());
    }
}
