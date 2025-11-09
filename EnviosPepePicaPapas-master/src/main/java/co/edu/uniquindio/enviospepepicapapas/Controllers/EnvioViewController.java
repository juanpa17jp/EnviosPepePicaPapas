package co.edu.uniquindio.enviospepepicapapas.Controllers;

import co.edu.uniquindio.enviospepepicapapas.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EnvioViewController {

    @FXML private TextField txtIdEnvio;
    @FXML private TextField txtOrigen;
    @FXML private TextField txtPeso;
    @FXML private CheckBox chkPrioridad;
    @FXML private TextField txtAlto;
    @FXML private TextField txtAncho;
    @FXML private TextField txtLargo;
    @FXML private Label lblVolumen;
    @FXML private ComboBox<String> cmbEstrategia;
    @FXML private CheckBox chkFragil;
    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkExpreso;
    @FXML private Label lblCostoTotal;

    private Rapilandia empresa;
    private double costoCalculado;

    @FXML
    public void initialize() {
        empresa = new Rapilandia("Rapilandia Express");

        // Cargar opciones del ComboBox
        cmbEstrategia.getItems().addAll("Por Peso", "Por Volumen ok");
        cmbEstrategia.setValue("Por Peso");

        // Listeners para calcular volumen automáticamente
        if (txtAlto != null) {
            txtAlto.textProperty().addListener((obs, old, newVal) -> calcularVolumen());
        }
        if (txtAncho != null) {
            txtAncho.textProperty().addListener((obs, old, newVal) -> calcularVolumen());
        }
        if (txtLargo != null) {
            txtLargo.textProperty().addListener((obs, old, newVal) -> calcularVolumen());
        }
    }

    @FXML
    public void onCalcularCosto(ActionEvent event) {
        try {
            if (!validarCampos()) {
                return;
            }

            // Obtener valores
            int idEnvio = Integer.parseInt(txtIdEnvio.getText());
            String origen = txtOrigen.getText();
            double peso = Double.parseDouble(txtPeso.getText());
            boolean prioridad = chkPrioridad.isSelected();

            double alto = Double.parseDouble(txtAlto.getText());
            double ancho = Double.parseDouble(txtAncho.getText());
            double largo = Double.parseDouble(txtLargo.getText());
            Dimension dimension = new Dimension(alto, ancho, largo);

            // Crear envío con Builder
            EnvioBuilder builder = new EnvioBuilder()
                    .setIdEnvio(idEnvio)
                    .setOrigen(origen)
                    .setPeso(peso)
                    .setPrioridad(prioridad)
                    .setDimension(dimension);

            // Seleccionar estrategia
            TarifaStrategy estrategia;
            if ("Por Volumen".equals(cmbEstrategia.getValue())) {
                estrategia = new VolumenStrategy();
            } else {
                estrategia = new PesoStrategy();
            }

            // Registrar envío y calcular costo
            Envio envio = empresa.registrarEnvio(builder, estrategia);
            double costoBase = envio.getCostoTotal();

            // Aplicar servicios adicionales
            if (chkFragil.isSelected()) {
                costoBase += 10000;
            }
            if (chkSeguro.isSelected()) {
                costoBase += 5000;
            }
            if (chkExpreso.isSelected()) {
                costoBase += 8000;
            }

            costoCalculado = costoBase;
            lblCostoTotal.setText(String.format("%.2f", costoCalculado));

            mostrarInfo("Cálculo exitoso", "El costo del envío ha sido calculado");

        } catch (NumberFormatException e) {
            mostrarError("Error de formato", "Verifique que los números sean válidos");
        } catch (Exception e) {
            mostrarError("Error en cálculo", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onRegistrarEnvio(ActionEvent event) {
        try {
            if (costoCalculado == 0) {
                mostrarAdvertencia("Calcular primero", "Debe calcular el costo antes de registrar");
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar registro");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Desea proceder al pago del envío?");
            confirmacion.getButtonTypes().setAll(
                    new ButtonType("Ir a Pago"),
                    new ButtonType("Solo Registrar"),
                    ButtonType.CANCEL
            );

            confirmacion.showAndWait().ifPresent(response -> {
                if (response.getText().equals("Ir a Pago")) {
                    // Obtener el último envío registrado
                    Envio envio = empresa.getEnvios().get(empresa.getEnvios().size() - 1);
                    envio.setCostoTotal(costoCalculado);
                    abrirVistaPago(envio);
                } else if (response.getText().equals("Solo Registrar")) {
                    mostrarInfo("Registro exitoso",
                            "Envío #" + txtIdEnvio.getText() + " registrado.\n" +
                                    "Costo: $" + String.format("%.2f", costoCalculado));
                    limpiarFormulario();
                }
            });

        } catch (Exception e) {
            mostrarError("Error en registro", e.getMessage());
        }
    }

    private void abrirVistaPago(Envio envio) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/enviospepepicapapas/PagoView.fxml")
            );
            javafx.scene.Parent root = loader.load();

            // Pasar el envío al controlador de pago
            PagoViewController controller = loader.getController();
            controller.setEnvio(envio);

            Stage stage = new Stage();
            stage.setTitle("Gestión de Pagos - Envío #" + envio.getIdEnvio());
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();

            // Cerrar ventana actual
            Stage currentStage = (Stage) txtIdEnvio.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            mostrarError("Error", "No se pudo abrir la vista de pagos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onLimpiarFormulario(ActionEvent event) {
        limpiarFormulario();
    }

    @FXML
    public void onVolver(ActionEvent event) {
        Stage stage = (Stage) txtIdEnvio.getScene().getWindow();
        stage.close();
    }

    private void calcularVolumen() {
        try {
            double alto = txtAlto.getText().isEmpty() ? 0 : Double.parseDouble(txtAlto.getText());
            double ancho = txtAncho.getText().isEmpty() ? 0 : Double.parseDouble(txtAncho.getText());
            double largo = txtLargo.getText().isEmpty() ? 0 : Double.parseDouble(txtLargo.getText());

            double volumen = alto * ancho * largo;
            if (lblVolumen != null) {
                lblVolumen.setText(String.format("%.2f m³", volumen));
            }
        } catch (NumberFormatException e) {
            if (lblVolumen != null) {
                lblVolumen.setText("0.0 m³");
            }
        }
    }

    private boolean validarCampos() {
        if (txtIdEnvio.getText().isEmpty()) {
            mostrarAdvertencia("Campo vacío", "Ingrese el ID del envío");
            return false;
        }
        if (txtOrigen.getText().isEmpty()) {
            mostrarAdvertencia("Campo vacío", "Ingrese el origen del envío");
            return false;
        }
        if (txtPeso.getText().isEmpty()) {
            mostrarAdvertencia("Campo vacío", "Ingrese el peso del envío");
            return false;
        }
        if (txtAlto.getText().isEmpty() || txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty()) {
            mostrarAdvertencia("Campos vacíos", "Ingrese todas las dimensiones");
            return false;
        }

        try {
            Integer.parseInt(txtIdEnvio.getText());
            Double.parseDouble(txtPeso.getText());
            Double.parseDouble(txtAlto.getText());
            Double.parseDouble(txtAncho.getText());
            Double.parseDouble(txtLargo.getText());
        } catch (NumberFormatException e) {
            mostrarAdvertencia("Formato incorrecto", "Los valores numéricos deben ser válidos");
            return false;
        }

        return true;
    }

    private void limpiarFormulario() {
        txtIdEnvio.clear();
        txtOrigen.clear();
        txtPeso.clear();
        chkPrioridad.setSelected(false);
        txtAlto.clear();
        txtAncho.clear();
        txtLargo.clear();
        lblVolumen.setText("0.0 m³");
        cmbEstrategia.setValue("Por Peso");
        chkFragil.setSelected(false);
        chkSeguro.setSelected(false);
        chkExpreso.setSelected(false);
        lblCostoTotal.setText("0.00");
        costoCalculado = 0;
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