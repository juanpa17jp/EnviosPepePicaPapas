package co.edu.uniquindio.enviospepepicapapas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AppView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            stage.setTitle("Rapilandia Express - Sistema de Gestión");
            stage.setScene(scene);
            stage.show();

            System.out.println("Aplicación iniciada correctamente");

        } catch (IOException e) {
            System.err.println("Error al cargar FXML: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}