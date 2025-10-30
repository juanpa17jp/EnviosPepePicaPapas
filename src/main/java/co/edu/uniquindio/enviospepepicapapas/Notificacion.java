package co.edu.uniquindio.enviospepepicapapas;

public abstract class Notificacion {
    protected NotificacionSender sender;

    public Notificacion(NotificacionSender sender) {
        this.sender = sender;
    }

    public abstract void notificar(String mensaje, String destino);
}
