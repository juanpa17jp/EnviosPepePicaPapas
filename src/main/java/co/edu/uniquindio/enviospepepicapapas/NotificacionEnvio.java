package co.edu.uniquindio.enviospepepicapapas;

public class NotificacionEnvio extends Notificacion{
    public NotificacionEnvio(NotificacionSender sender) {
        super(sender);
    }

    @Override
    public void notificar(String mensaje, String destino) {
        sender.enviarNotificacion("Actualización del envío: " + mensaje, destino);
    }

}
