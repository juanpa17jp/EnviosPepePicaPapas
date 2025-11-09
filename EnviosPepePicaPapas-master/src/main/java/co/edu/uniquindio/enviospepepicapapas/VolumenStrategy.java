package co.edu.uniquindio.enviospepepicapapas;

public class VolumenStrategy implements TarifaStrategy {
    @Override
    public double calcularTarifa(Envio envio) {
        return 5000 + envio.getVolumen() * 0.8;
    }
}