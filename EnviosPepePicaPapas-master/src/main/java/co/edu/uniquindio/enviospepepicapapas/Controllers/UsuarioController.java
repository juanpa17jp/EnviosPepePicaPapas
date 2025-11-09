package co.edu.uniquindio.enviospepepicapapas.Controllers;

import javafx.event.ActionEvent;
import co.edu.uniquindio.enviospepepicapapas.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UsuarioController {

    @FXML private Button btnVolver;
    @FXML private TextField txtNombre;

    @FXML private TextField txtEmail;

    @FXML private ComboBox<String> cbTipo;

    private Rapilandia empresa;

    // Usuario actual que se está creando o duplicando
    private Usuario usuarioSeleccionado;

    // Inicialización automática del FXML
    @FXML
    public void initialize() {
        empresa = new Rapilandia("Rapilandia Express");

        // Cargar tipos de usuario
        cbTipo.getItems().addAll("Cliente", "Repartidor", "Administrador");
    }

    // 🔹 Guardar usuario nuevo
    @FXML
    private void onGuardar() {
        String nombre = txtNombre.getText();
        String email = txtEmail.getText();
        String tipo = cbTipo.getValue();

        if (nombre.isEmpty() || email.isEmpty() || tipo == null) {
            mostrarAlerta("Campos incompletos", "Por favor completa todos los campos antes de guardar.", Alert.AlertType.WARNING);
            return;
        }

        // Crear usuario genérico (puedes tener subclases reales)
        Usuario nuevo = new Usuario() {};
        nuevo.setNombre(nombre);
        nuevo.setEmail(email);

        empresa.registrarUsuario(nuevo);

        mostrarAlerta("Éxito", "Usuario registrado correctamente en Rapilandia.", Alert.AlertType.INFORMATION);
        limpiarCampos();
    }

    // 🔹 Duplicar usuario (usa Prototype)
    @FXML
    private void onDuplicarUsuario() {
        if (usuarioSeleccionado == null) {
            // Si no hay uno en memoria, crea uno base
            usuarioSeleccionado = new Usuario() {};
            usuarioSeleccionado.setNombre(txtNombre.getText());
            usuarioSeleccionado.setEmail(txtEmail.getText());
        }

        // Aplicar Prototype
        Usuario copia = usuarioSeleccionado.clone();
        copia.setNombre(usuarioSeleccionado.getNombre() + " (Copia)");

        empresa.registrarUsuario(copia);

        mostrarAlerta("Duplicación exitosa", "Usuario duplicado con éxito usando Prototype.", Alert.AlertType.INFORMATION);
    }

    // 🔹 Cancelar acción
    @FXML
    private void onCancelar() {
        limpiarCampos();
        mostrarAlerta("Acción cancelada", "Los campos fueron limpiados.", Alert.AlertType.INFORMATION);
    }



    // 🔸 Métodos auxiliares

    private void limpiarCampos() {
        txtNombre.clear();
        txtEmail.clear();
        cbTipo.setValue(null);
        usuarioSeleccionado = null;
    }
    @FXML
    public void onVolver(ActionEvent event) {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}