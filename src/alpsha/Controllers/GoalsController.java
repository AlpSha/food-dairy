package alpsha.Controllers;

import alpsha.Data.FoodData;
import alpsha.Food;
import alpsha.Nutrition;
import alpsha.util.StringFormatter;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GoalsController implements Initializable {

    @FXML
    VBox nutritionBox;
    @FXML
    JFXRadioButton radioWorkoutDay;
    @FXML
    JFXRadioButton radioRestDay;

    List<JFXTextField> fields;

    Food targetDay;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fields = new ArrayList<>();
        nutritionBox.setSpacing(10);
        nutritionBox.getChildren().setAll(new ArrayList<>());
        for(Nutrition nutrition: Nutrition.values()) {
            Label label = new Label();
            if(nutrition == Nutrition.VITAMIN_A || nutrition ==  Nutrition.VITAMIN_C) {
                label.setText(StringFormatter.formatVitaminName(nutrition.toString()));
            } else {
                label.setText(StringFormatter.capitalizeWord(nutrition.toString()));
            }
            JFXTextField field = new JFXTextField();
            field.setId(nutrition.toString());
            Text empty = new Text();
            nutritionBox.getChildren().add(label);
            nutritionBox.getChildren().add(field);
            nutritionBox.getChildren().add(empty);
            fields.add(field);
        }

        radioWorkoutDay.setSelected(true);
        changeDayType();
    }

    @FXML
    public void changeDayType() {
        targetDay = FoodData.getInstance().getTargetDay(radioWorkoutDay.isSelected());
        for(JFXTextField field: fields) {
            Nutrition nutrition = Nutrition.valueOf(field.getId());
            field.setText(String.valueOf(targetDay.getNutrition(nutrition)));
        }
    }

    @FXML
    public void handleSaveButton() {
        for(JFXTextField field: fields) {
            Nutrition nutrition = Nutrition.valueOf(field.getId());
            double value;
            try {
                value = Double.parseDouble(field.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong format");
                alert.setHeaderText("Input format isn't correct");
                alert.setContentText("Check the " + nutrition.toString() + " format");
                alert.showAndWait();
                return;
            }
            targetDay.setNutrition(nutrition, value);
        }

        FoodData.getInstance().updateFood(targetDay);

    }

    @FXML
    public void handleCancelButton() {
        changeDayType();
    }
}
