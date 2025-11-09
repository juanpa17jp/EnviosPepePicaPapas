package co.edu.uniquindio.enviospepepicapapas.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class AppViewController {

    @FXML
    public void initialize() {
        System.out.println("AppViewController inicializado correctamente");
    }

    @FXML
    public void onRegistrarEnvio(ActionEvent event) {
        System.out.println("Botón Registrar Envío presionado");
        try {
            cargarVista("/co/edu/uniquindio/enviospepepicapapas/EnvioView.fxml", "Registrar Nuevo Envío");
        } catch (IOException e) {
            mostrarError("Error al cargar vista", "No se pudo abrir la vista de envíos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onHistorialEnvios(ActionEvent event) {
        System.out.println("Botón Historial presionado");
        try {
            cargarVista("/co/edu/uniquindio/enviospepepicapapas/HistorialEnviosView.fxml", "Registrar Nuevo Historial");
        } catch (IOException e) {
            mostrarError("Error al cargar vista", "No se pudo abrir la vista de historial: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onGestionPagos(ActionEvent event) {
        System.out.println("Botón Pagos presionado");
        try {
            cargarVista("/co/edu/uniquindio/enviospepepicapapas/SelectorEnviosView.fxml", "Seleccionar Envío para Pagar");
        } catch (IOException e) {
            mostrarError("Error al cargar vista", "No se pudo abrir el selector de envíos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onGestionUsuarios(ActionEvent event) {
        System.out.println("Botón Registrar Usuario presionado");
        try {
            cargarVista("/co/edu/uniquindio/enviospepepicapapas/UsuarioView.fxml", "Registrar Nuevo Usuario");
        } catch (IOException e) {
            mostrarError("Error al cargar vista", "No se pudo abrir la vista de envios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onNotificaciones(ActionEvent event) {
        System.out.println("Botón Registrar Notificacion presionado");
        try {
            cargarVista("/co/edu/uniquindio/enviospepepicapapas/NotificacionView.fxml", "Notificaciones");
        } catch (IOException e) {
            mostrarError("Error al cargar vista", "No se pudo abrir la vista de Notificacion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarVista(String rutaFXML, String titulo) throws IOException {
        System.out.println("Intentando cargar: " + rutaFXML);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root));
        stage.show();

        System.out.println("Vista cargada exitosamente: " + titulo);
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}