package co.edu.uniquindio.enviospepepicapapas;

public class PesoStrategy implements TarifaStrategy {
    @Override
    public double calcularTarifa(Envio envio) {
        return 5000 + envio.getPeso() * 1200;
    }
}