package co.edu.uniquindio.enviospepepicapapas;

public abstract class EnvioDecorator implements EnvioComponent {
    protected EnvioComponent componente;
    public EnvioDecorator(EnvioComponent componente) {
        this.componente = componente;
    }

    @Override
    public double calcularTarifa(Envio envio) {
        return componente.calcularTarifa(envio);
    }
}
