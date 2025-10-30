package co.edu.uniquindio.enviospepepicapapas;

import java.util.ArrayList;
import java.util.List;

public class EnvioHistoryMemento {
    private List<EnvioMemento> historial = new ArrayList<>();
    public void agregarMemento(EnvioMemento m) { historial.add(m); }
    public EnvioMemento getUltimo() { return historial.get(historial.size()-1); }
}
