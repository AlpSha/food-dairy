package alpsha.Controllers;

import alpsha.Data.FoodData;
import alpsha.Data.ServingData;
import alpsha.Food;
import alpsha.Meal;
import alpsha.MealType;
import alpsha.Serving;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;

import java.net.URL;
import java.util.ResourceBundle;

public class AddFoodController implements Initializable {

    Meal selectedMeal;
    MealType selectedMealType;

    @FXML
    JFXListView<Food> listView;

    @FXML
    JFXTextField searchField;

    @FXML
    JFXButton buttonAdd;

    private FilteredList<Food> filteredList;

    private ObservableList<Food> foodsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foodsList = FXCollections.observableArrayList();

        FoodData foodData = FoodData.getInstance();
        foodsList.setAll(foodData.foods.values());
        filteredList = new FilteredList<>(foodsList, food -> true);
        listView.setItems(filteredList);

        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Food food, boolean b) {
                super.updateItem(food, b);

                if(food == null || b) {
                    setText(null);
                } else {
                    setText(food.getName());
                }
            }
        });
    }


    @FXML
    public void handleSearchKey() {
        String searchParameter = searchField.getText().toLowerCase();
        if(searchParameter.isBlank()) {
            filteredList.setPredicate(food -> true);
        } else {
            filteredList.setPredicate(food -> food.getName().toLowerCase().contains(searchParameter));
        }
    }

    @FXML
    public void handleAddButton() {
        Food selectedFood = listView.getSelectionModel().getSelectedItem();
        selectedFood.getServingTypes().setAll(FoodData.getInstance().readServingTypesOfFood(selectedFood));
        Serving serving = new Serving(selectedFood.getAnyServingType(), 1, selectedFood);
        selectedMeal.addServing(serving);
        ServingData.getInstance().addServing(selectedMealType, TodayController.todaysDate, serving);

    }

    public void setSelectedMeal(Meal meal, MealType type) {
        this.selectedMeal = meal;
        this.selectedMealType = type;
    }
}
