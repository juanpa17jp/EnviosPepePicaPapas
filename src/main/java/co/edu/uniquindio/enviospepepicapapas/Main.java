package co.edu.uniquindio.enviospepepicapapas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Rapilandia empresa = new Rapilandia("Rapilandia Express");

        // Crear usuario (Prototype)
        Usuario cliente = new Usuario() {}; // o subclase
        cliente.setNombre("Vicen");
        empresa.registrarUsuario(cliente);

        // Crear envío con Builder
        EnvioBuilder builder = new EnvioBuilder()
                .setIdEnvio(101)
                .setOrigen("Cali")
                .setPeso(3.5)
                .setPrioridad(true)
                .setDimension(new Dimension(1.2, 0.8, 0.6))
                .setUsuario(cliente);

        // Registrar envío con estrategia de peso
        Envio envio = empresa.registrarEnvio(builder, new PesoStrategy());

        // Procesar pago
        empresa.procesarPago(envio, new PagoEfectivo());

        // Enviar notificación
        empresa.notificarEnvio(envio, new EmailSender(), "cliente@correo.com");
    }
}