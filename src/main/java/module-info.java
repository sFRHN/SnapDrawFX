module org.example.assignment4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.assignment4 to javafx.fxml;
    exports org.example.assignment4;
}