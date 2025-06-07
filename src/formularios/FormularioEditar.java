package formularios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormularioEditar extends JDialog {

    private JTextField txtNumero = new JTextField();
    private JTextField txtPlaca = new JTextField();
    private JTextField txtDescripcion = new JTextField();
    private JTextField txtIdCliente = new JTextField();

    public FormularioEditar(Window parent, DefaultTableModel modeloTabla) {
        super(parent, "Agregar Equipo", ModalityType.APPLICATION_MODAL);
        configurarFormulario(modeloTabla, false, -1);
    }

    public FormularioEditar(Window parent, DefaultTableModel modeloTabla, int rowIndex) {
        super(parent, "Modificar Equipo", ModalityType.APPLICATION_MODAL);
        configurarFormulario(modeloTabla, true, rowIndex);
    }

    private void configurarFormulario(DefaultTableModel modeloTabla, boolean esEdicion, int rowIndex) {
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        if (esEdicion && rowIndex >= 0) {
            txtNumero.setText(modeloTabla.getValueAt(rowIndex, 1).toString());
            txtPlaca.setText(modeloTabla.getValueAt(rowIndex, 2).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(rowIndex, 3).toString());
            txtIdCliente.setText(modeloTabla.getValueAt(rowIndex, 4).toString());
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Número de Equipo:"));
        panel.add(txtNumero);
        panel.add(new JLabel("Placa:"));
        panel.add(txtPlaca);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);
        panel.add(new JLabel("ID Cliente:"));
        panel.add(txtIdCliente);

        JButton btnGuardar = new JButton(esEdicion ? "Actualizar" : "Guardar");
        btnGuardar.addActionListener(e -> {
            if (esEdicion) {
                modeloTabla.setValueAt(txtNumero.getText(), rowIndex, 1);
                modeloTabla.setValueAt(txtPlaca.getText(), rowIndex, 2);
                modeloTabla.setValueAt(txtDescripcion.getText(), rowIndex, 3);
                modeloTabla.setValueAt(txtIdCliente.getText(), rowIndex, 4);
            } else {
                modeloTabla.addRow(new Object[]{
                        "Auto",
                        txtNumero.getText(),
                        txtPlaca.getText(),
                        txtDescripcion.getText(),
                        txtIdCliente.getText(),
                        "Ahora",
                        "Acciones"
                });
            }
            dispose();
        });

        panel.add(btnGuardar);
        add(panel);
    }
}
