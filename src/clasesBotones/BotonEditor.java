package clasesBotones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import formularios.FormularioEditar;

public class BotonEditor extends DefaultCellEditor {
    protected JPanel panel;
    protected JButton btnModificar, btnEliminar, btnImprimir;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private int currentRow;

    public BotonEditor(JCheckBox checkBox, DefaultTableModel modeloTabla, JTable tablaEquipos) {
        super(checkBox);
        this.modeloTabla = modeloTabla;
        this.tablaEquipos = tablaEquipos;

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnImprimir = new JButton("Imprimir");

        // Eventos
        btnModificar.addActionListener(e -> modificarEquipo(currentRow,modeloTabla));
        btnEliminar.addActionListener(e -> eliminarEquipo(currentRow));
        btnImprimir.addActionListener(e -> imprimirEquipo(currentRow));

        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnImprimir);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {
        currentRow = row; // Guardamos la fila real
        return panel;
    }

    private void modificarEquipo(int row, DefaultTableModel modeloTabla) {
        Window parent = SwingUtilities.getWindowAncestor(tablaEquipos);
        FormularioEditar dialog = new FormularioEditar(parent, modeloTabla, row);
        dialog.setVisible(true);
        fireEditingStopped();
    }


    private void eliminarEquipo(int row) {
        String numero = modeloTabla.getValueAt(row, 1).toString();
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Seguro que deseas eliminar el equipo: " + numero + "?",
                "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            modeloTabla.removeRow(row);
        }
        fireEditingStopped();
    }

    private void imprimirEquipo(int row) {
        String numero = modeloTabla.getValueAt(row, 1).toString();
        JOptionPane.showMessageDialog(null, "Generar PDF para equipo: " + numero);
        // Llamar aquí a tu método generarPDF(...)
        fireEditingStopped();
    }
}
