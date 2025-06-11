// Archivo: clasesBotones/BotonEditorClientes.java
package clasesBotones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import DAO.PersonasDAO;
import formularios.FormularioEditarCliente;
import menuInicialAdministrador.panelClientes; // Referencia al panel

/**
 * Editor de celda personalizado para JTable que muestra botones "Modificar" y "Eliminar"
 * y "Imprimir" en una celda.
 */
public class BotonEditorClientes extends DefaultCellEditor {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnImprimir; // Botón de imprimir, si lo necesitas
    private int currentRow; // Almacena la fila actual que se está editando
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private PersonasDAO personasDAO; // Instancia del DAO
    private panelClientes panelClientesRef; // Referencia al panel para refrescar la tabla

    public BotonEditorClientes(JCheckBox checkBox, DefaultTableModel modeloTabla, JTable tabla, PersonasDAO personasDAO, panelClientes panelClientesRef) {
        super(checkBox);
        this.modeloTabla = modeloTabla;
        this.tabla = tabla;
        this.personasDAO = personasDAO;
        this.panelClientesRef = panelClientesRef;

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Espacio entre botones
        panel.setBackground(Color.WHITE); // Fondo blanco para el panel de botones

        btnModificar = new JButton("");
        btnModificar.setPreferredSize(new Dimension(30, 30));
        btnModificar.setIcon(new ImageIcon(getClass().getResource("/img/editar.png"))); // Ruta a tu icono
        btnModificar.setToolTipText("Modificar cliente");
        btnModificar.setFocusPainted(false);
        btnModificar.setContentAreaFilled(false); // Para ver el color de fondo del panel
        btnModificar.setBorderPainted(false); // Sin borde para el botón
        btnModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEliminar = new JButton("");
        btnEliminar.setPreferredSize(new Dimension(30, 30));
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/img/eliminar.png"))); // Ruta a tu icono
        btnEliminar.setToolTipText("Eliminar cliente");
        btnEliminar.setFocusPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnImprimir = new JButton("");
        btnImprimir.setPreferredSize(new Dimension(30, 30));
        btnImprimir.setIcon(new ImageIcon(getClass().getResource("/img/imprimir.png"))); // Ruta a tu icono
        btnImprimir.setToolTipText("Imprimir datos del cliente");
        btnImprimir.setFocusPainted(false);
        btnImprimir.setContentAreaFilled(false);
        btnImprimir.setBorderPainted(false);
        btnImprimir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnImprimir); // Agregando el botón de imprimir

        // --- ACCIONES DE LOS BOTONES ---
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // Detener la edición de la celda
                modificar(currentRow);
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // Detener la edición de la celda
                eliminar(currentRow);
            }
        });
        
        btnImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // Detener la edición de la celda
                imprimir(currentRow); // Llama a tu método de imprimir
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRow = row; // Almacena la fila actual para usarla en los ActionListeners
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return new String(""); // Retorna un valor dummy, ya que los botones manejan la acción
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    // --- Lógica del botón Modificar ---
    private void modificar(int row) {
        if (personasDAO == null) {
            JOptionPane.showMessageDialog(null, "El DAO de Personas no está inicializado. No se puede modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (panelClientesRef == null) {
             JOptionPane.showMessageDialog(null, "Referencia al panel de clientes no disponible.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }

        Window parent = SwingUtilities.getWindowAncestor(tabla);
        // Abrimos el formulario en modo edición, pasando el DAO y la referencia al panel
        FormularioEditarCliente dialog = new FormularioEditarCliente(parent, modeloTabla, row, personasDAO, panelClientesRef);
        dialog.setVisible(true);
    }

    // --- Lógica del botón Eliminar ---
    private void eliminar(int row) {
        if (personasDAO == null) {
            JOptionPane.showMessageDialog(null, "El DAO de Personas no está inicializado. No se puede eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (panelClientesRef == null) {
             JOptionPane.showMessageDialog(null, "Referencia al panel de clientes no disponible.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }
        
        Object idValue = modeloTabla.getValueAt(row, 0); // Obtener el ID de la primera columna (oculta)
        int idPersona = -1;

        if (idValue == null) {
            JOptionPane.showMessageDialog(null, "Error: El ID del cliente es nulo. No se puede eliminar.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (idValue instanceof Integer) {
                idPersona = (Integer) idValue;
            } else if (idValue instanceof Long) {
                idPersona = ((Long) idValue).intValue();
            } else if (idValue instanceof String) {
                idPersona = Integer.parseInt((String) idValue);
            } else {
                JOptionPane.showMessageDialog(null, "Error: Tipo de dato inesperado para el ID del cliente: " + idValue.getClass().getName(), "Error de Datos", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: El ID del cliente no es un número válido: " + idValue, "Error de Formato", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(null, "Error: Falló la conversión de tipo de dato para el ID del cliente.", "Error de Conversión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        if (idPersona == -1) { // Si no se obtuvo un ID válido por alguna razón
            JOptionPane.showMessageDialog(null, "No se pudo obtener un ID de cliente válido para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este cliente?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                personasDAO.eliminarPersona(idPersona); // Llamar al DAO para eliminar
                JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                panelClientesRef.cargarClientesEnTabla(); // Refrescar la tabla en el panel
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                                              "Error al eliminar el cliente de la base de datos: " + ex.getMessage(),
                                              "Error SQL",
                                              JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    // --- Lógica del botón Imprimir (ejemplo) ---
    private void imprimir(int row) {
        // Aquí puedes implementar la lógica para imprimir los datos del cliente
        // Por ejemplo, abrir un reporte, mostrar un JOptionPane con los datos, etc.
        JOptionPane.showMessageDialog(null, "Funcionalidad de imprimir no implementada aún.", "Imprimir", JOptionPane.INFORMATION_MESSAGE);
        // Puedes obtener los datos de la fila de la tabla:
        // String nombre = modeloTabla.getValueAt(row, 1).toString();
        // String identificacion = modeloTabla.getValueAt(row, 3).toString();
        // JOptionPane.showMessageDialog(null, "Datos del cliente a imprimir:\n" +
        //                                     "Nombre: " + nombre + "\n" +
        //                                     "ID: " + identificacion, "Imprimir Cliente", JOptionPane.INFORMATION_MESSAGE);
    }
}