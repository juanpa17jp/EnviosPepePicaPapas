package co.edu.uniquindio.enviospepepicapapas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HistorialEnvios implements Iterable<Envio> {
    private List<Envio> listaEnvios = new ArrayList<>();

    public void agregarEnvio(Envio envio) {
        listaEnvios.add(envio);
    }

    @Override
    public Iterator<Envio> iterator() {
        return listaEnvios.iterator();
    }
}
