package clasesBotones;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class BotonRenderer extends JPanel implements TableCellRenderer {

    public BotonRenderer() {
        setOpaque(true);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        // Limpiar componentes previos
        this.removeAll();

        // Crear botones nuevos para cada celda
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnImprimir = new JButton("Imprimir");

        // (Opcional: Deshabilitar porque este es solo el render, no deben hacer nada aquí)
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnImprimir.setEnabled(false);

        this.add(btnModificar);
        this.add(btnEliminar);
        this.add(btnImprimir);

        return this;
    }
}
