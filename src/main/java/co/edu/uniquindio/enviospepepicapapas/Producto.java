package co.edu.uniquindio.enviospepepicapapas;

public class Producto implements PedidoItem{
    private String nombre;
    private double costo;

    public Producto(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }

    @Override
    public double calcularCosto() {
        return costo;
    }
}
