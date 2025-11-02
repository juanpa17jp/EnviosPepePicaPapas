package co.edu.uniquindio.enviospepepicapapas;

public class PagoTransferencia implements MetodoPago{
    private String cuentaDestino;
    @Override
    public boolean procesarPago(double monto) {
        return false;
    }
}
