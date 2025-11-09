package co.edu.uniquindio.enviospepepicapapas;

public class EnvioMemento {
    private EstadoEnvio estado;
    public EnvioMemento(EstadoEnvio estado) { this.estado = estado; }
    public EstadoEnvio getEstado() {
        return estado;
    }
}
