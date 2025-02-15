module com.example.test_v1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.sql;

    opens com.example.test_v1 to javafx.fxml;
    exports com.example.test_v1;
}