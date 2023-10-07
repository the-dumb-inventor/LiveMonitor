module com.example.livemonitor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.livemonitor to javafx.fxml;
    exports com.example.livemonitor;
}