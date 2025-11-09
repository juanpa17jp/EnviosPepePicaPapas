package co.edu.uniquindio.enviospepepicapapas;

public class EnvioBase implements EnvioComponent {
    @Override
    public double calcularTarifa(Envio envio) {
        return envio.getCostoTotal();
    }
}
