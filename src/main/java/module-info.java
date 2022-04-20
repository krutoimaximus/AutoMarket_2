module com.example.automarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.automarket to javafx.fxml;
    exports com.example.automarket;
}