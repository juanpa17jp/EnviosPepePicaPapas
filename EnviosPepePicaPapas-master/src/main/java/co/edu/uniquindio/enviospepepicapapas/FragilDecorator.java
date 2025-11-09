package co.edu.uniquindio.enviospepepicapapas;

public class FragilDecorator extends EnvioDecorator {
    public FragilDecorator(EnvioComponent componente) {
        super(componente);
    }

    @Override
    public double calcularTarifa(Envio envio) {
        return super.calcularTarifa(envio) + 10000;
    }
}
