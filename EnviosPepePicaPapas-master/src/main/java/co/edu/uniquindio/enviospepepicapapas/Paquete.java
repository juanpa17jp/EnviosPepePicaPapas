package co.edu.uniquindio.enviospepepicapapas;

import java.util.ArrayList;
import java.util.List;

public class Paquete implements PedidoItem{
    private List<PedidoItem> items = new ArrayList<>();

    public void agregarItem(PedidoItem item) {
        items.add(item);
    }

    @Override
    public double calcularCosto() {
        return items.stream().mapToDouble(PedidoItem::calcularCosto).sum();
    }
}
