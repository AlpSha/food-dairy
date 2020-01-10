package alpsha.Controllers;

import alpsha.Data.DayData;
import alpsha.Data.FoodData;
import alpsha.Date;
import alpsha.Day;
import alpsha.Food;
import alpsha.Nutrition;
import alpsha.util.StringFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import org.joda.time.LocalDate;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML
    VBox barBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barBox.getChildren().setAll(new ArrayList<>());
        barBox.setSpacing(20);

        Food targetWorkout = FoodData.getInstance().getTargetDay(true);
        Food targetRest = FoodData.getInstance().getTargetDay(false);

        for(Nutrition nutrition: Nutrition.values()) {

            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Recent Days");

            // Target Values
            XYChart.Series<String, Number> targetValues = new XYChart.Series<>();
            targetValues.setName("Target");

            XYChart.Series<String, Number> consumedValues = new XYChart.Series<>();
            consumedValues.setName("Consumed");

            NumberAxis yAxis = new NumberAxis();
            if(nutrition.equals(Nutrition.CALORIES)) {
                yAxis.setLabel("kcal");
            } else {
                yAxis.setLabel("grams");
            }
            BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);

            LocalDate today = LocalDate.now();
            double totalNut;
            for(int i=6; i >= 0; i--) {
                totalNut = 0;
                LocalDate date = today.minusDays(i);
                Day day = DayData.getInstance().queryDayByDate(date);
                if(day == null) {
                    break;
                }
                totalNut += day.getTotalNutrition(nutrition);

                String stringDate = date.toString(Date.formatter);

                if(day.isWorkoutDay()) {
                    targetValues.getData().add(new XYChart.Data<>(stringDate, targetWorkout.getNutrition(nutrition)));
                } else {
                    targetValues.getData().add(new XYChart.Data<>(stringDate, targetRest.getNutrition(nutrition)));
                }

                consumedValues.getData().add(new XYChart.Data<>(stringDate, totalNut));
            }


            chart.getData().add(consumedValues);
            chart.getData().add(targetValues);
            chart.setTitle(StringFormatter.capitalizeWord(nutrition.toString()));

            //set first bar color
            for(Node n:chart.lookupAll(".default-color0")) {
                n.setStyle("-fx-bar-fill: #ffdc34;");
            }
            //second bar color
            for(Node n:chart.lookupAll(".default-color1")) {
                n.setStyle("-fx-bar-fill: #4dd599;");
            }


            barBox.getChildren().add(chart);
        }

    }
}
