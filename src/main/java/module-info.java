module co.edu.uniquindio.enviospepepicapapas {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.enviospepepicapapas to javafx.fxml;
    exports co.edu.uniquindio.enviospepepicapapas;
}