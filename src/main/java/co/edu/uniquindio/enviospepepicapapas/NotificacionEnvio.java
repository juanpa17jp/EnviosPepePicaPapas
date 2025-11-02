package co.edu.uniquindio.enviospepepicapapas;

public class NotificacionEnvio extends Notificacion{
    private StatusNotificacion status;

    public NotificacionEnvio(NotificacionSender sender) {
        super(sender);
    }

    @Override
    public void notificar(String mensaje, String destino) {
        try {
            sender.enviarNotificacion("Actualización del envío: " + mensaje, destino);
            status = StatusNotificacion.ENVIADA;
        } catch (Exception e) {
            status = StatusNotificacion.FALLIDA;
        }
    }

    public StatusNotificacion getStatus() {
        return status;
    }

}
