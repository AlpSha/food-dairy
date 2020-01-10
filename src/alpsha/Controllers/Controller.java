package alpsha.Controllers;

import alpsha.Data.FoodData;
import alpsha.Food;
import alpsha.Screens.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static Controller instance;
    private static BorderPane currentMainPane;

    public Controller() {
        instance = this;
    }

    public static Controller getInstance() {
        return instance;
    }

    public static BorderPane getCurrentMainPane() {
        return currentMainPane;
    }


    @FXML
    BorderPane mainPane;
    @FXML
    JFXButton buttonToday;
    @FXML
    JFXButton buttonJournal;
    @FXML
    JFXButton buttonReport;
    @FXML
    JFXButton buttonGoals;

    TodayScreen todayScreen;
    JournalScreen journalScreen;
    GoalsScreen goalsScreen;
    ReportScreen reportScreen;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FoodData foodData = FoodData.getInstance();
        foodData.readAllFoods();

        currentMainPane = mainPane;
        todayScreen = new TodayScreen();
        goalsScreen = new GoalsScreen();

        mainPane.setCenter(todayScreen);
    }

    @FXML
    void changeMenu(ActionEvent event) {
        Screen screen;
        switch (((Control)event.getSource()).getId()) {
            case "buttonJournal":
                journalScreen = new JournalScreen();
                screen = journalScreen;
                break;
            case "buttonReport":
                reportScreen = new ReportScreen();
                screen = reportScreen;
                break;
            case "buttonGoals":
                screen = goalsScreen;
                break;
            case "buttonToday":
            default:
                screen = todayScreen;
                break;
        }
        mainPane.setCenter(screen);
    }

}
