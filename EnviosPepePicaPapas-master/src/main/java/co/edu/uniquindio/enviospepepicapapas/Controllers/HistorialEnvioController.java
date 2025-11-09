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

public class HistorialEnvioController {    @FXML private TableView<Envio> tablaEnvios;
    @FXML private TableColumn<Envio, Integer> colId;
    @FXML private TableColumn<Envio, String> colOrigen;
    @FXML private TableColumn<Envio, Double> colPeso;
    @FXML private TableColumn<Envio, String> colEstado;
    @FXML private TableColumn<Envio, Double> colCosto;
    @FXML private ComboBox<EstadoEnvio> cbEstado;
    @FXML private Label lblInfo;

    private Rapilandia empresa;
    private ObservableList<Envio> listaEnvios;
    private EnvioEstado envioEstado;           // Originator
    private EnvioHistoryMemento historialMemento; // Caretaker

    @FXML
    public void initialize() {
        empresa = new Rapilandia("Rapilandia Express");
        envioEstado = new EnvioEstado();
        historialMemento = new EnvioHistoryMemento();

        // Configurar columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costoTotal"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Estados disponibles
        cbEstado.setItems(FXCollections.observableArrayList(EstadoEnvio.values()));

        // Datos de ejemplo (puedes quitarlos cuando conectes con Rapilandia real)
        cargarEnviosEjemplo();

        // Selección
        tablaEnvios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lblInfo.setText("Envío #" + newSel.getIdEnvio() + " seleccionado. Estado actual: " + newSel.getEstado());
            }
        });
    }

    private void cargarEnviosEjemplo() {
        for (int i = 1; i <= 4; i++) {
            Dimension dim = new Dimension(1 + i * 0.2, 0.8, 0.6);
            EnvioBuilder builder = new EnvioBuilder()
                    .setIdEnvio(200 + i)
                    .setOrigen("Cali " + i)
                    .setPeso(2.5 + i)
                    .setPrioridad(i % 2 == 0)
                    .setDimension(dim);

            Envio envio = empresa.registrarEnvio(builder, new PesoStrategy());
            envio.setCostoTotal(12000 + i * 2500);
        }
        actualizarTabla();
    }

    private void actualizarTabla() {
        listaEnvios = FXCollections.observableArrayList(empresa.getEnvios());
        tablaEnvios.setItems(listaEnvios);
    }

    // 🔹 Acción: actualizar estado
    @FXML
    public void onActualizarEstado(ActionEvent event) {
        Envio seleccionado = tablaEnvios.getSelectionModel().getSelectedItem();
        EstadoEnvio nuevoEstado = cbEstado.getValue();

        if (seleccionado == null) {
            mostrarAlerta("Sin selección", "Selecciona un envío primero.", Alert.AlertType.WARNING);
            return;
        }

        if (nuevoEstado == null) {
            mostrarAlerta("Estado vacío", "Selecciona un nuevo estado para el envío.", Alert.AlertType.WARNING);
            return;
        }

        // Guardar estado anterior con Memento
        envioEstado.setEstado(seleccionado.getEstado());
        historialMemento.agregarMemento(envioEstado.guardarEstado());

        // Actualizar estado del envío
        seleccionado.setEstado(nuevoEstado);
        lblInfo.setText("Envío #" + seleccionado.getIdEnvio() + " actualizado a " + nuevoEstado);
        tablaEnvios.refresh();
    }

    // 🔹 Acción: ver detalle del envío
    @FXML
    public void onVerDetalle(ActionEvent event) {
        Envio envio = tablaEnvios.getSelectionModel().getSelectedItem();
        if (envio == null) {
            mostrarAlerta("Sin selección", "Selecciona un envío para ver su detalle.", Alert.AlertType.WARNING);
            return;
        }

        String info = "📦 Envío #" + envio.getIdEnvio() + "\n" +
                "Origen: " + envio.getOrigen() + "\n" +
                "Peso: " + envio.getPeso() + " kg\n" +
                "Estado: " + envio.getEstado() + "\n" +
                "Costo Total: $" + envio.getCostoTotal();

        mostrarAlerta("Detalle de envío", info, Alert.AlertType.INFORMATION);
    }

    // 🔹 Acción: volver al menú principal
    @FXML
    public void onVolverMenu(ActionEvent event) {
        Stage stage = (Stage) tablaEnvios.getScene().getWindow();
        stage.close();
    }

    // 🔸 Utilidades
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
