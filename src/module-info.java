module JavaFxApplication {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;
    requires java.sql;
    requires org.joda.time;

    exports alpsha to javafx.graphics;

    opens alpsha.Controllers;
    opens alpsha.Screens to javafx.fxml;
}