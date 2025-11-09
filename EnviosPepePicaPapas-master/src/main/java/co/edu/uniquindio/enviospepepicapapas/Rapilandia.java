package co.edu.uniquindio.enviospepepicapapas;

import java.util.ArrayList;
import java.util.List;

public class Rapilandia {

    private String nombreEmpresa;
    private List<Envio> envios;
    private List<Usuario> usuarios;
    private TarifaManager tarifaManager;
    private HistorialEnvios historial;

    public Rapilandia(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
        this.envios = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.tarifaManager = TarifaManager.getInstancia();
        this.historial = new HistorialEnvios();
    }

    // -------------------------
    // MÉTODOS PRINCIPALES
    // -------------------------

    //  Registrar un usuario
    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    //  Registrar un nuevo envío (usa el Builder y Strategy)
    public Envio registrarEnvio(EnvioBuilder builder, TarifaStrategy estrategia) {
        tarifaManager.setEstrategia(estrategia);
        Envio envio = builder.build();
        double costo = tarifaManager.calcularTarifa(envio);
        envio.setCostoTotal(costo);

        envios.add(envio);
        historial.agregarEnvio(envio);

        return envio;
    }

    //  Procesar pago
    public boolean procesarPago(Envio envio, MetodoPago metodoPago) {
        double monto = envio.getCostoTotal();
        boolean resultado = metodoPago.procesarPago(monto);
        if (resultado) {
            System.out.println("💳 Pago aprobado para envío #" + envio.getIdEnvio());
        } else {
            System.out.println("❌ Pago rechazado para envío #" + envio.getIdEnvio());
        }
        return resultado;
    }

    //  Enviar notificación (Bridge)
    public void notificarEnvio(Envio envio, NotificacionSender sender, String destino) {
        Notificacion notificacion = new NotificacionEnvio(sender);
        notificacion.notificar("El envío #" + envio.getIdEnvio() + " está " + envio.getEstado(), destino);
    }

    //  Consultar historial
    public HistorialEnvios getHistorial() {
        return historial;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }
}