package co.edu.uniquindio.enviospepepicapapas;

public class TarifaManager {
    private static TarifaManager instancia;
    private TarifaStrategy estrategia;

    // Factores de costo
    private double base = 5000;
    private double distancia = 2000;
    private double peso = 1000;
    private double volumen = 0.5;
    private double prioridad = 1.5;
    private double recargo = 3000;

    private TarifaManager() {}

    public static TarifaManager getInstancia() {
        if (instancia == null) {
            instancia = new TarifaManager();
        }
        return instancia;
    }

    public void setEstrategia(TarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public double calcularTarifa(Envio envio) {
        if (estrategia == null) throw new IllegalStateException("No hay estrategia seleccionada");
        return estrategia.calcularTarifa(envio);
    }
    public double getBase() {
        return base;
    }
    public void setBase(double base) {
        this.base = base;
    }
    public double getDistancia() {
        return distancia;
    }
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public double getVolumen() {
        return volumen;
    }
    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }
    public double getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(double prioridad) {
        this.prioridad = prioridad;
    }
    public double getRecargo() {
        return recargo;
    }
    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }

}

