package co.edu.uniquindio.enviospepepicapapas.Controllers;

import co.edu.uniquindio.enviospepepicapapas.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class PagoViewController {

    @FXML private Label lblIdEnvio;
    @FXML private Label lblOrigen;
    @FXML private Label lblEstado;
    @FXML private Label lblMontoPagar;
    @FXML private Label lblEstadoPago;

    @FXML private RadioButton rbTarjeta;
    @FXML private RadioButton rbTransferencia;
    @FXML private RadioButton rbEfectivo;
    @FXML private ToggleGroup metodoPago;

    @FXML private VBox panelTarjeta;
    @FXML private VBox panelTransferencia;
    @FXML private VBox panelEfectivo;

    @FXML private TextField txtNumeroTarjeta;
    @FXML private TextField txtTitular;
    @FXML private TextField txtMes;
    @FXML private TextField txtAnio;
    @FXML private TextField txtCVV;

    @FXML private TextField txtCuentaDestino;
    @FXML private ComboBox<String> cmbBanco;

    private Envio envioActual;
    private Rapilandia empresa;
    private double montoOriginal;

    @FXML
    public void initialize() {
        empresa = new Rapilandia("Rapilandia Express ok");

        // Configurar ToggleGroup
        metodoPago = new ToggleGroup();
        rbTarjeta.setToggleGroup(metodoPago);
        rbTransferencia.setToggleGroup(metodoPago);
        rbEfectivo.setToggleGroup(metodoPago);

        // Cargar bancos
        cmbBanco.getItems().addAll(
                "Banco de Bogotá",
                "Bancolombia",
                "Davivienda",
                "BBVA",
                "Banco Popular"
        );

        // Listeners para cambiar paneles
        metodoPago.selectedToggleProperty().addListener((obs, old, newVal) -> {
            actualizarPanelesMetodoPago();
        });

        // NO cargar datos de ejemplo automáticamente
        // Se cargarán cuando se llame a setEnvio()
        actualizarPanelesMetodoPago();
    }

    public void setEnvio(Envio envio) {
        this.envioActual = envio;
        this.montoOriginal = envio.getCostoTotal();

        lblIdEnvio.setText(String.valueOf(envio.getIdEnvio()));
        lblOrigen.setText(envio.getOrigen());
        lblEstado.setText(envio.getEstado().toString());
        lblMontoPagar.setText(String.format("%.2f", montoOriginal));
        lblEstadoPago.setText("Pendiente");

        System.out.println("Envío cargado: #" + envio.getIdEnvio() + " - Monto: $" + montoOriginal);
    }

    @FXML
    public void onProcesarPago(ActionEvent event) {
        try {
            if (envioActual == null) {
                mostrarAdvertencia("Sin envío", "No hay un envío seleccionado");
                return;
            }

            MetodoPago metodo = obtenerMetodoPago();
            if (metodo == null) {
                mostrarAdvertencia("Método incompleto", "Complete los datos del método de pago");
                return;
            }

            // Simular procesamiento de pago (siempre exitoso en demo)
            boolean resultado = true;

            if (resultado) {
                lblEstadoPago.setText("Aprobado");
                lblEstadoPago.setStyle("-fx-text-fill: #00FF00; -fx-font-size: 16px;");

                double montoFinal = Double.parseDouble(lblMontoPagar.getText());
                mostrarInfo("Pago exitoso",
                        "Pago del envío #" + envioActual.getIdEnvio() +
                                " procesado correctamente.\n" +
                                "Monto: $" + String.format("%.2f", montoFinal));

            } else {
                lblEstadoPago.setText("Rechazado");
                lblEstadoPago.setStyle("-fx-text-fill: #FF0000; -fx-font-size: 16px;");
                mostrarError("Pago rechazado", "Verifique los datos e intente nuevamente");
            }

        } catch (Exception e) {
            mostrarError("Error en pago", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onVerRecibo(ActionEvent event) {
        if (envioActual == null) {
            mostrarAdvertencia("Sin envío", "No hay un envío seleccionado");
            return;
        }

        if (!"Aprobado".equals(lblEstadoPago.getText())) {
            mostrarAdvertencia("Pago no procesado", "Debe procesar el pago primero");
            return;
        }

        StringBuilder recibo = new StringBuilder();
        recibo.append("========== RECIBO DE PAGO ==========\n\n");
        recibo.append("ID Envío: ").append(envioActual.getIdEnvio()).append("\n");
        recibo.append("Origen: ").append(envioActual.getOrigen()).append("\n");
        recibo.append("Peso: ").append(envioActual.getPeso()).append(" kg\n");
        recibo.append("Estado: ").append(envioActual.getEstado()).append("\n\n");
        recibo.append("Método de pago: ").append(obtenerNombreMetodoPago()).append("\n");
        recibo.append("Fecha: ").append(LocalDate.now()).append("\n\n");
        recibo.append("MONTO TOTAL: $").append(lblMontoPagar.getText()).append("\n");
        recibo.append("\n====================================");

        mostrarInfo("Recibo de Pago", recibo.toString());
    }

    @FXML
    public void onCancelar(ActionEvent event) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cancelar pago");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de cancelar el proceso de pago?");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                limpiarFormulario();
                mostrarInfo("Cancelado", "Proceso de pago cancelado");
            }
        });
    }

    @FXML
    public void onVolver(ActionEvent event) {
        Stage stage = (Stage) lblIdEnvio.getScene().getWindow();
        stage.close();
    }

    private void actualizarPanelesMetodoPago() {
        panelTarjeta.setVisible(false);
        panelTarjeta.setManaged(false);
        panelTransferencia.setVisible(false);
        panelTransferencia.setManaged(false);
        panelEfectivo.setVisible(false);
        panelEfectivo.setManaged(false);

        if (rbTarjeta.isSelected()) {
            panelTarjeta.setVisible(true);
            panelTarjeta.setManaged(true);
            lblMontoPagar.setText(String.format("%.2f", montoOriginal));
        } else if (rbTransferencia.isSelected()) {
            panelTransferencia.setVisible(true);
            panelTransferencia.setManaged(true);
            lblMontoPagar.setText(String.format("%.2f", montoOriginal));
        } else if (rbEfectivo.isSelected()) {
            panelEfectivo.setVisible(true);
            panelEfectivo.setManaged(true);
            // Recargo 5%
            double montoConRecargo = montoOriginal * 1.05;
            lblMontoPagar.setText(String.format("%.2f", montoConRecargo));
        }
    }

    private MetodoPago obtenerMetodoPago() {
        if (rbTarjeta.isSelected()) {
            if (validarTarjeta()) {
                return new PagoTarjeta();
            }
            return null;
        } else if (rbTransferencia.isSelected()) {
            if (validarTransferencia()) {
                return new PagoTransferencia();
            }
            return null;
        } else if (rbEfectivo.isSelected()) {
            return new PagoEfectivo();
        }
        return null;
    }

    private String obtenerNombreMetodoPago() {
        if (rbTarjeta.isSelected()) {
            return "Tarjeta de Crédito/Débito";
        } else if (rbTransferencia.isSelected()) {
            return "Transferencia Bancaria";
        } else if (rbEfectivo.isSelected()) {
            return "Efectivo (Contra entrega)";
        }
        return "No especificado";
    }

    private boolean validarTarjeta() {
        if (txtNumeroTarjeta.getText().isEmpty()) {
            mostrarAdvertencia("Campo vacío", "Ingrese el número de tarjeta");
            return false;
        }
        if (txtTitular.getText().isEmpty()) {
            mostrarAdvertencia("Campo vacío", "Ingrese el titular");
            return false;
        }
        if (txtMes.getText().isEmpty() || txtAnio.getText().isEmpty()) {
            mostrarAdvertencia("Campo vacío", "Ingrese la fecha de vencimiento");
            return false;
        }
        if (txtCVV.getText().isEmpty()) {
            mostrarAdvertencia("Campo vacío", "Ingrese el CVV");
            return false;
        }
        return true;
    }

    private boolean validarTransferencia() {
        if (txtCuentaDestino.getText().isEmpty()) {
            mostrarAdvertencia("Campo vacío", "Ingrese la cuenta destino");
            return false;
        }
        if (cmbBanco.getValue() == null) {
            mostrarAdvertencia("Sin selección", "Seleccione un banco");
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtNumeroTarjeta.clear();
        txtTitular.clear();
        txtMes.clear();
        txtAnio.clear();
        txtCVV.clear();
        txtCuentaDestino.clear();
        cmbBanco.setValue(null);
        rbTarjeta.setSelected(true);
        lblEstadoPago.setText("Pendiente");
        lblEstadoPago.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 16px;");
        lblMontoPagar.setText(String.format("%.2f", montoOriginal));
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

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}