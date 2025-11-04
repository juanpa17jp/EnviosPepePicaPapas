package co.edu.uniquindio.enviospepepicapapas.Controllers;

import co.edu.uniquindio.enviospepepicapapas.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SelectorEnviosController {

    @FXML private TableView<Envio> tablaEnvios;
    @FXML private TableColumn<Envio, Integer> colId;
    @FXML private TableColumn<Envio, String> colOrigen;
    @FXML private TableColumn<Envio, Double> colPeso;
    @FXML private TableColumn<Envio, Double> colCosto;
    @FXML private TableColumn<Envio, String> colEstado;
    @FXML private Button btnPagarSeleccionado;
    @FXML private Button btnVolver;

    private Rapilandia empresa;
    private ObservableList<Envio> listaEnvios;

    @FXML
    public void initialize() {
        empresa = new Rapilandia("Rapilandia Express");

        // Configurar columnas de la tabla
        colId.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costoTotal"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Cargar datos de ejemplo
        cargarEnviosEjemplo();

        // Listener de selección
        tablaEnvios.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    btnPagarSeleccionado.setDisable(newSelection == null);
                }
        );

        btnPagarSeleccionado.setDisable(true);
    }

    private void cargarEnviosEjemplo() {
        // Crear algunos envíos de ejemplo
        for (int i = 1; i <= 5; i++) {
            Dimension dim = new Dimension(1.0 + i * 0.1, 0.8, 0.6);
            EnvioBuilder builder = new EnvioBuilder()
                    .setIdEnvio(100 + i)
                    .setOrigen("Ciudad " + i)
                    .setPeso(2.0 + i)
                    .setPrioridad(i % 2 == 0)
                    .setDimension(dim);

            Envio envio = empresa.registrarEnvio(builder, new PesoStrategy());
            envio.setCostoTotal(10000 + i * 2000);
        }

        actualizarTabla();
    }

    public void cargarEnvios(Rapilandia empresa) {
        this.empresa = empresa;
        actualizarTabla();
    }

    private void actualizarTabla() {
        listaEnvios = FXCollections.observableArrayList(empresa.getEnvios());
        tablaEnvios.setItems(listaEnvios);
    }

    @FXML
    public void onPagarSeleccionado(ActionEvent event) {
        Envio envioSeleccionado = tablaEnvios.getSelectionModel().getSelectedItem();

        if (envioSeleccionado == null) {
            mostrarAdvertencia("Sin selección", "Seleccione un envío de la tabla");
            return;
        }

        abrirVistaPago(envioSeleccionado);
    }

    @FXML
    public void onVolver(ActionEvent event) {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }

    private void abrirVistaPago(Envio envio) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/enviospepepicapas/PagoView.fxml")
            );
            Parent root = loader.load();

            PagoViewController controller = loader.getController();
            controller.setEnvio(envio);

            Stage stage = new Stage();
            stage.setTitle("Gestión de Pagos - Envío #" + envio.getIdEnvio());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            mostrarError("Error", "No se pudo abrir la vista de pagos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}