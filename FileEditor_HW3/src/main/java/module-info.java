module com.mycompany.fileeditor_hw3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.fileeditor_hw3 to javafx.fxml;
    exports com.mycompany.fileeditor_hw3;
}
