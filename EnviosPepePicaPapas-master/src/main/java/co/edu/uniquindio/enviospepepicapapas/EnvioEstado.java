package co.edu.uniquindio.enviospepepicapapas;

public class EnvioEstado {
    private EstadoEnvio estado;

    public void setEstado(EstadoEnvio nuevo) {
        this.estado = nuevo;
    }

    public EnvioMemento guardarEstado() {
        return new EnvioMemento(estado);
    }

    public void restaurar(EnvioMemento memento) {
        this.estado = memento.getEstado();
    }
}
