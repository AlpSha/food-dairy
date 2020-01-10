package alpsha.Controllers;

import alpsha.Meal;
import alpsha.MealType;
import alpsha.Serving;
import alpsha.util.StringFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JournalListViewCell extends ListCell<Meal> {

    @FXML
    Text mealType;
    @FXML
    Text mealTotalNutrition;
    @FXML
    VBox foodNameBox;
    @FXML
    VBox nutritionValuesBox;
    @FXML
    GridPane itemPane;


    private FXMLLoader fxmlLoader;
    private JournalController parentController;

    public JournalListViewCell(JournalController parentController) {
        this.parentController = parentController;
    }

    @Override
    protected void updateItem(Meal meal, boolean b) {
        super.updateItem(meal, b);
        setBackground(Background.EMPTY);
        if(meal == null || b) {
            setText(null);
            setGraphic(null);
        } else {
            if(fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/alpsha/Screens/JournalMealPane.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            DecimalFormat df = new DecimalFormat("#.#");
            mealType.setText(StringFormatter.capitalizeWord(meal.getType().toString()));
            double totalOfMeal = 0;
            foodNameBox.getChildren().setAll(new ArrayList<>());
            nutritionValuesBox.getChildren().setAll(new ArrayList<>());
            for(Serving s: meal.getServings()) {
                Text text = new Text();
                text.setText(s.getUnits() + " " + s.getType().getName() + " " + s.getFood().getName());
                foodNameBox.getChildren().add(text);

                Text nutValueText = new Text();
                nutValueText.setTextAlignment(TextAlignment.RIGHT);
                nutValueText.setWrappingWidth(43);
                double nutValue = s.getNutritionAmount(parentController.currentNut);
                nutValueText.setText(df.format(nutValue));
                nutritionValuesBox.getChildren().add(nutValueText);
                totalOfMeal += nutValue;
            }
            totalOfMeal = Double.parseDouble(df.format(totalOfMeal));
            mealTotalNutrition.setText(String.valueOf(totalOfMeal));
            mealTotalNutrition.setTextAlignment(TextAlignment.RIGHT);
            mealTotalNutrition.setWrappingWidth(53);

            setText(null);
            setGraphic(itemPane);

        }
    }


}
