package co.edu.uniquindio.enviospepepicapapas;

public class Envio {
    private int idEnvio;
    private String origen;
    private double peso;
    private boolean prioridad;
    private double costoTotal;
    private EstadoEnvio estado;
    private Dimension dimension;
    private Usuario usuario;
    private Usuario repartidor;
    private Pago pago;
    private String serviciosAdicionales;

    public Envio(int idEnvio, String origen, double peso, boolean prioridad, Dimension dimension) {
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.peso = peso;
        this.prioridad = prioridad;
        this.dimension = dimension;
        this.estado = EstadoEnvio.SOLICITADO; // valor por defecto
    }
    public double getVolumen() {
        return dimension.calcularVolumen();
    }

    public double calcularCosto() {
        double base = 5000;
        double volumen = getVolumen();
        return base + peso * 1000 + volumen * 0.5;
    }

    public int getIdEnvio() {
        return idEnvio;
    }
    public void setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }
    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public boolean isPrioridad() {
        return prioridad;
    }
    public void setPrioridad(boolean prioridad) {
        this.prioridad = prioridad;
    }
    public double getCostoTotal() {
        return costoTotal;
    }
    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
    public EstadoEnvio getEstado() {
        return estado;
    }
    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
    }
    public Dimension getDimension() {
        return dimension;
    }
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Usuario getRepartidor() {
        return repartidor;
    }
    public void setRepartidor(Usuario repartidor) {
        this.repartidor = repartidor;
    }
    public Pago getPago() {
        return pago;
    }
    public void setPago(Pago pago) {
        this.pago = pago;
    }
    public String getServiciosAdicionales() {
        return serviciosAdicionales;
    }
    public void setServiciosAdicionales(String serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }

}
