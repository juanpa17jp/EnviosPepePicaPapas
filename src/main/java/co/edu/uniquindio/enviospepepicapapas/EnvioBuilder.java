package co.edu.uniquindio.enviospepepicapapas;

public class EnvioBuilder {
    private int idEnvio;
    private String origen;
    private double peso;
    private boolean prioridad;
    private Dimension dimension;
    private Usuario usuario;
    private Usuario repartidor;
    private Pago pago;
    private String serviciosAdicionales;

    public EnvioBuilder() {
        reset();
    }

    public void reset() {
        idEnvio = 0;
        origen = "";
        peso = 0;
        prioridad = false;
        dimension = null;
        usuario = null;
        repartidor = null;
        pago = null;
        serviciosAdicionales = "";
    }
    public EnvioBuilder setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
        return this;
    }

    public EnvioBuilder setOrigen(String origen) {
        this.origen = origen;
        return this;
    }

    public EnvioBuilder setPeso(double peso) {
        this.peso = peso;
        return this;
    }

    public EnvioBuilder setPrioridad(boolean prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public EnvioBuilder setDimension(Dimension dimension) {
        this.dimension = dimension;
        return this;
    }

    public EnvioBuilder setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public EnvioBuilder setRepartidor(Usuario repartidor) {
        this.repartidor = repartidor;
        return this;
    }

    public EnvioBuilder setPago(Pago pago) {
        this.pago = pago;
        return this;
    }

    public EnvioBuilder setServiciosAdicionales(String serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
        return this;
    }

    public Envio build() {
        Envio envio = new Envio(idEnvio, origen, peso, prioridad, dimension);
        envio.setUsuario(usuario);
        envio.setRepartidor(repartidor);
        envio.setPago(pago);
        envio.setServiciosAdicionales(serviciosAdicionales);

        // Se calcula el costo al crear
        double costo = envio.calcularCosto();
        envio.setCostoTotal(costo);

        // Opcional: restaurar el builder para nuevo uso
        reset();
        return envio;
    }
}

