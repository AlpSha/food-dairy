package alpsha.Controllers;

import alpsha.Serving;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServingListViewCell extends ListCell<Serving> implements Initializable {

    @FXML
    Label foodName;
    @FXML
    Label foodContents;
    @FXML
    VBox itemBox;


    private FXMLLoader mLLoader;
    private ContextMenu contextMenu;
    private TodayController parentController;

    public ServingListViewCell(TodayController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parentController.deleteCurrentServing();
            }
        });
        contextMenu.getItems().add(deleteItem);
        itemBox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(itemBox, event.getScreenX() + 10, event.getScreenY() + 5);
            }
        });


    }

    @Override
    protected void updateItem(Serving serving, boolean b) {
        super.updateItem(serving, b);

        if(b || serving == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/alpsha/Screens/ListCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            foodName.setText(serving.getFood().getName());
            if(serving.getType() != null) {
                foodContents.textProperty().bind(serving.getItemStringProperty());
            } else {
                foodContents.setText("");
            }

            setText(null);
            setGraphic(itemBox);
       }
    }

}
