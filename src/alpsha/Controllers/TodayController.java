package alpsha.Controllers;

import alpsha.*;
import alpsha.Data.DayData;
import alpsha.Data.FoodData;
import alpsha.Data.ServingData;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TodayController implements Initializable {

    @FXML
    JFXButton buttonPlus;

    @FXML
    JFXToggleNode buttonBreakfast;
    @FXML
    JFXToggleNode buttonMorning;
    @FXML
    JFXToggleNode buttonLunch;
    @FXML
    JFXToggleNode buttonEvening;
    @FXML
    JFXToggleNode buttonDinner;
    @FXML
    JFXToggleNode buttonNight;

    @FXML
    JFXListView<Serving> listView;
    @FXML
    JFXCheckBox workOutDayBox;

    ObservableList<Serving> servingsList;

    private List<JFXToggleNode> mealTypeButtons;


    // Food Details Pane
    @FXML
    ScrollPane foodDetailsPane;
    @FXML
    JFXTextField foodNameField;
    @FXML
    JFXComboBox<ServingType> servingSelectionBox;
    @FXML
    JFXTextField servingAmountField;
    @FXML
    JFXTextField caloriesField;
    @FXML
    JFXTextField proteinField;
    @FXML
    JFXTextField fatField;
    @FXML
    JFXTextField carbsField;
    @FXML
    JFXTextField fiberField;
    @FXML
    JFXTextField cholesterolField;
    @FXML
    JFXTextField sodiumField;
    @FXML
    JFXTextField potassiumField;
    @FXML
    JFXTextField calciumField;
    @FXML
    JFXTextField vitaminAField;
    @FXML
    JFXTextField vitaminCField;
    @FXML
    JFXTextField ironField;



    static LocalDate todaysDate;
    Day today;
    Meal currentMeal;
    Serving selectedServing;
    MealType currentMealType;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servingsList = FXCollections.observableArrayList();

        buttonPlus.setShape(new Circle(30));
        buttonPlus.setMinSize(60, 60);
        buttonPlus.setMaxSize(60, 60);

        foodDetailsPane.setVisible(false);


        todaysDate = LocalDate.now();


        mealTypeButtons = new ArrayList<>();
        mealTypeButtons.add(buttonBreakfast);
        mealTypeButtons.add(buttonDinner);
        mealTypeButtons.add(buttonEvening);
        mealTypeButtons.add(buttonLunch);
        mealTypeButtons.add(buttonMorning);
        mealTypeButtons.add(buttonNight);

        for (JFXToggleNode b : mealTypeButtons) {
            b.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if (b.isSelected()) {
                        b.setTextFill(Paint.valueOf("black"));
                        handleMealSelection(b);
                    } else {
                        b.setTextFill(Paint.valueOf("#b7b2c1"));
                    }
                }
            });
        }

        DayData dataInstance = DayData.getInstance();
        today = dataInstance.queryDayByDate(todaysDate);
        DayData.getInstance().createDayIfNotExists(today);
        workOutDayBox.setSelected(DayData.getInstance().queryDayStatus(today));

        buttonBreakfast.setSelected(true);

        servingSelectionBox.setPromptText("Select a serving type");
        servingSelectionBox.setCellFactory(st -> new ListCell<>() {

            @Override
            protected void updateItem(ServingType servingType, boolean b) {
                super.updateItem(servingType, b);
                if (servingType == null || b) {
                    setText(null);
                } else {
                    setText(servingType.getName());
                }
            }
        });

        servingSelectionBox.setConverter(new StringConverter<ServingType>() {
            @Override
            public String toString(ServingType servingType) {
                return servingType.getName();
            }

            @Override
            public ServingType fromString(String s) {
                return selectedServing.getFood().getServingType(s);
            }
        });

        servingAmountField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    servingAmountField.setText(newValue.replaceAll("[^0-9.]", ""));
                }
            }
        });

        servingAmountField.setPromptText("Amount");
    }

    private void handleMealSelection (JFXToggleNode selectedMeal) {
       currentMealType = MealType.valueOf(selectedMeal.getText().toUpperCase());

        this.currentMeal =  today.getMeal(currentMealType);
        addAllListItems();
    }

    private void addAllListItems() {
        servingsList = currentMeal.getServings();
        listView.setItems(servingsList);
        listView.setCellFactory(servingListView -> new ServingListViewCell(this)
        );
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Serving>() {
            @Override
            public void changed(ObservableValue<? extends Serving> observableValue, Serving serving, Serving t1) {
                setFoodDetails();
            }
        });


    }

    public void setFoodDetails() {
        if(listView.getSelectionModel().getSelectedItem() == null) {
            foodDetailsPane.setVisible(false);
            return;
        }
        this.selectedServing = listView.getSelectionModel().getSelectedItem();
        foodNameField.setText(selectedServing.getFood().getName());

        servingSelectionBox.setItems(FXCollections.observableList(FoodData.getInstance().readServingTypesOfFood(selectedServing.getFood())));

        servingSelectionBox.getSelectionModel().select(selectedServing.getType());
        servingAmountField.setText(String.valueOf(selectedServing.getUnits()));

        caloriesField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.CALORIES)));
        proteinField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.PROTEIN)));
        fatField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.FAT)));
        carbsField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.CARBS)));
        fiberField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.FIBER)));
        cholesterolField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.CHOLESTEROL)));
        sodiumField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.SODIUM)));
        potassiumField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.POTASSIUM)));
        calciumField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.CALCIUM)));
        vitaminAField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.VITAMIN_A)));
        vitaminCField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.VITAMIN_C)));
        ironField.setText(String.valueOf(selectedServing.getFood().getNutrition(Nutrition.IRON)));




        foodDetailsPane.setVisible(true);

    }

    @FXML
    public void handleSaveServing() {
        ServingType type = servingSelectionBox.getSelectionModel().getSelectedItem();
        Double amount;
        try {
            amount = Double.parseDouble(servingAmountField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Format Error");
            alert.setHeaderText("Incorrect number format");
            alert.setContentText("Please check your input");
            alert.showAndWait();
            return;
        }

        Serving serving = new Serving(type, amount, selectedServing.getFood());

        ServingData.getInstance().updateServing(currentMealType, todaysDate, selectedServing.getType(), serving);
        selectedServing.setType(type);
        selectedServing.setUnits(amount);
    }

    public void deleteCurrentServing() {
        ServingData.getInstance().removeServing(currentMealType, todaysDate, selectedServing);
        this.currentMeal.removeServing(selectedServing);
    }



    @FXML
    public void onButtonPlusAction() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/alpsha/Screens/AddFood.fxml"));
            root = loader.load();

            ((AddFoodController) loader.getController()).setSelectedMeal(currentMeal, currentMealType);

            Stage stage = new Stage();
            stage.setTitle("Add food");
            stage.setScene(new Scene(root, 495, 600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println("Error opening add food window " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleWorkoutDay() {
        DayData.getInstance().updateDayStatus(today, workOutDayBox.isSelected());
    }



}
