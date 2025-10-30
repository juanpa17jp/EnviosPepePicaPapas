package co.edu.uniquindio.enviospepepicapapas;

public class TarifaManager {
    private static TarifaManager instancia;

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

    public double calcularTarifa(double peso, double volumen, double km, boolean prioridad, boolean fragil) {
        double costo = base + (peso * this.peso) + (volumen * this.volumen) + (km * this.distancia);
        if (prioridad) costo *= this.prioridad;
        if (fragil) costo += this.recargo;
        return costo;
    }
}
