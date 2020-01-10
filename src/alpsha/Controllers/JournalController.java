package alpsha.Controllers;

import alpsha.*;
import alpsha.Data.DayData;
import alpsha.Data.FoodData;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleNode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.joda.time.LocalDate;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JournalController implements Initializable {

    @FXML
    JFXToggleNode buttonCalories;
    @FXML
    JFXToggleNode buttonProtein;
    @FXML
    JFXToggleNode buttonCarbs;
    @FXML
    JFXToggleNode buttonFat;

    List<JFXToggleNode> foodTypeButtons;

    @FXML
    JFXListView<Meal> listView;
    @FXML
    Text dateLabel;
    @FXML
    Text totalNutritionLabel;
    @FXML
    Text remainingLabel;

    ObservableList<Meal> mealsList;

    Nutrition currentNut;
    double totalNutrition;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foodTypeButtons = new ArrayList<>();
        foodTypeButtons.add(buttonCalories);
        foodTypeButtons.add(buttonProtein);
        foodTypeButtons.add(buttonCarbs);
        foodTypeButtons.add(buttonFat);

        totalNutritionLabel.setTextAlignment(TextAlignment.RIGHT);
        totalNutritionLabel.setWrappingWidth(63);
        remainingLabel.setTextAlignment(TextAlignment.RIGHT);
        remainingLabel.setWrappingWidth(130);

        for (JFXToggleNode b: foodTypeButtons) {
            b.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if(b.isSelected()) {
                        b.setTextFill(Paint.valueOf("black"));
                        handleNutritionSelection(b);
                    } else {
                        b.setTextFill(Paint.valueOf("#b7b2c1"));
                    }
                }
            });
        }

        mealsList = FXCollections.observableArrayList();
        listView.setCellFactory(journalListView -> new JournalListViewCell(this));

        currentNut = Nutrition.CALORIES;
        selectDate(LocalDate.now());

    }


    public void handleNutritionSelection(JFXToggleNode selectedNut) {
        currentNut = Nutrition.valueOf(selectedNut.getText().toUpperCase());
        selectDate(LocalDate.parse(dateLabel.getText(), Date.formatter));
    }

    public void selectDate(LocalDate date) {
        dateLabel.setText(date.toString(Date.formatter));

        Day day = DayData.getInstance().queryDayByDate(date);
        if(day == null) {
            return;
        }

        mealsList = FXCollections.observableArrayList();
        for(MealType mt: MealType.values()) {
            mealsList.add(day.getMeal(mt));
        }
        listView.setItems(mealsList);

        DecimalFormat df = new DecimalFormat("#");
        totalNutrition = Double.parseDouble(df.format(day.getTotalNutrition(currentNut)));
        this.totalNutritionLabel.setText(String.valueOf(totalNutrition));
        double remainingValue = FoodData.getInstance().getTargetDay(day.isWorkoutDay()).getNutrition(currentNut) - totalNutrition;
        remainingValue = Double.parseDouble(df.format(remainingValue));
        if(remainingValue >= 0 ) {
            remainingLabel.setText("Remaining " + remainingValue);
        } else {
            remainingValue *= -1;
            remainingLabel.setText("Exceeded " + remainingValue);
        }

    }

    @FXML
    public void previousDay() {
        LocalDate currentDay = LocalDate.parse(dateLabel.getText(), Date.formatter);
        selectDate(currentDay.minusDays(1));
    }

    @FXML
    public void nextDay() {
        LocalDate currentDay = LocalDate.parse(dateLabel.getText(), Date.formatter);
        selectDate(currentDay.plusDays(1));
    }
}
