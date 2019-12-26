package alpsha;

import alpsha.Controllers.Controller;
import alpsha.Data.DBConnector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        if(!DBConnector.getInstance().open()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Initialization failed");
            alert.setContentText("Can't receive database connection");
            alert.showAndWait();
            Platform.exit();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Screens/MainLook.fxml"));

        Parent mainRoot = loader.load();
        stage.setTitle("Food Diary");
        stage.setResizable(false);
        stage.setScene(new Scene(mainRoot, 1151, 845));
        stage.show();
    }
}
