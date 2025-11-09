package co.edu.uniquindio.enviospepepicapapas;

public class EmailSender implements NotificacionSender{
    @Override
    public void enviarNotificacion(String mensaje, String destino) {
        System.out.println("📧 Enviando EMAIL a " + destino + ": " + mensaje);
    }
}
