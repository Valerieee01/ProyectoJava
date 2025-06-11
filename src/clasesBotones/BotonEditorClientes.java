package clasesBotones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

import formularios.FormularioEditarCliente;
import menuInicialAdministrador.panelClientes;
import DAO.PersonasDAO;
import util.ConnectionADMIN;

public class BotonEditorClientes extends DefaultCellEditor {
    private static final long serialVersionUID = 1L;
    protected JPanel panel;
    protected JButton btnModificar, btnEliminar, btnImprimir;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private int currentRow;
    private panelClientes panelClientesRef;

    public BotonEditorClientes(JCheckBox checkBox, panelClientes panelClientesRef, JTable tablaClientes) {
        super(checkBox);

        ToolTipManager.sharedInstance().setEnabled(true);
        ToolTipManager.sharedInstance().setInitialDelay(200);

        this.panelClientesRef = panelClientesRef;
        this.tablaClientes = tablaClientes;
        this.modeloTabla = panelClientesRef.getModeloTabla();

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setBackground(new Color(255, 240, 220));

        btnModificar = new JButton();
        btnModificar.setIcon(escalarIcono("/clasesBotones/imgBotones/edit.png", 20, 20));
        btnModificar.setBorderPainted(false);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setFocusPainted(false);
        btnModificar.setOpaque(false);

        btnEliminar = new JButton();
        btnEliminar.setIcon(escalarIcono("/clasesBotones/imgBotones/delete.png", 20, 20));
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setOpaque(false);

        btnImprimir = new JButton();
        btnImprimir.setIcon(escalarIcono("/clasesBotones/imgBotones/impresora.png", 20, 20));
        btnImprimir.setBorderPainted(false);
        btnImprimir.setContentAreaFilled(false);
        btnImprimir.setFocusPainted(false);
        btnImprimir.setOpaque(false);

        btnModificar.setToolTipText("Modificar cliente");
        btnEliminar.setToolTipText("Eliminar cliente");
        btnImprimir.setToolTipText("Imprimir datos del cliente");

        btnModificar.setPreferredSize(new Dimension(32, 32));
        btnEliminar.setPreferredSize(new Dimension(32, 32));
        btnImprimir.setPreferredSize(new Dimension(32, 32));

        btnModificar.addActionListener(e -> modificar(currentRow));
        btnEliminar.addActionListener(e -> eliminar(currentRow));
        btnImprimir.addActionListener(e -> imprimir(currentRow));

        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnImprimir);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRow = tablaClientes.convertRowIndexToModel(row);
        return panel;
    }

    private void modificar(int row) {
        try {
            String numeroIdentificacion = (String) modeloTabla.getValueAt(row, 1);
            Window parent = SwingUtilities.getWindowAncestor(tablaClientes);
            FormularioEditarCliente dialog = new FormularioEditarCliente(parent, panelClientesRef, numeroIdentificacion);
            dialog.setVisible(true);
            fireEditingStopped();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir el formulario de modificación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminar(int row) {
        if (row >= 0 && row < modeloTabla.getRowCount()) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este cliente?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String numeroIdentificacion = (String) modeloTabla.getValueAt(row, 1);
                try (Connection conn = ConnectionADMIN.getConnectionADMIN()) { // Abre la conexión aquí para la operación de eliminación
                    PersonasDAO personasDAO = new PersonasDAO(); // Crea el DAO sin conexión en el constructor
                    personasDAO.eliminarPersona(numeroIdentificacion, conn); // Pasa la conexión al método
                    JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    panelClientesRef.cargarDatosClientes();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al eliminar cliente: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            System.out.println("Índice de fila inválido: " + row);
        }
        fireEditingStopped();
    }

    private void imprimir(int row) {
        String numero = modeloTabla.getValueAt(row, 1).toString();
        JOptionPane.showMessageDialog(null, "Generar PDF para cliente: " + numero);
        fireEditingStopped();
    }

    private ImageIcon escalarIcono(String ruta, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
        Image imagen = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
}
