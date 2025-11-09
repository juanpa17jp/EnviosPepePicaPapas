module co.edu.uniquindio.enviospepepicapapas {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    opens co.edu.uniquindio.enviospepepicapapas to javafx.fxml;
    exports co.edu.uniquindio.enviospepepicapapas;
    exports co.edu.uniquindio.enviospepepicapapas.Controllers;
    opens co.edu.uniquindio.enviospepepicapapas.Controllers to javafx.fxml;
}