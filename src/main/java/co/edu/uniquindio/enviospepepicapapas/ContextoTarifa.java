package co.edu.uniquindio.enviospepepicapapas;

public class ContextoTarifa {
    private TarifaStrategy estrategia;

    public void setEstrategia(TarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public double ejecutarEstrategia(Envio envio) {
        if (estrategia == null) throw new IllegalStateException("No se ha definido una estrategia.");
        return estrategia.calcularTarifa(envio);
    }
}