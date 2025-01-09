module com.example.a7map {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.web;

    opens com.example.a7map.gui to javafx.fxml;
    exports com.example.a7map.gui;
}
