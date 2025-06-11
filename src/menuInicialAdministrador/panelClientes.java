package menuInicialAdministrador;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import clasesBotones.BotonEditorClientes;
import clasesBotones.BotonRenderer;
import formularios.FormularioEditarCliente;
import modelos.Persona;
import DAO.PersonasDAO;
import util.ConnectionADMIN; // Asegúrate de que esta clase exista y funcione correctamente

public class panelClientes extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public panelClientes() {
        setLayout(new BorderLayout());

        JButton btnNuevoCliente = new JButton("Nuevo Cliente");
        btnNuevoCliente.setToolTipText("Ingresa tu nuevo Cliente aqui");
        btnNuevoCliente.setForeground(Color.WHITE);
        btnNuevoCliente.setBackground(new Color(255, 146, 94));
        add(btnNuevoCliente, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
        	    new Object[]{"Nombre Completo", "Identificacion", "Correo", "Telefono", "Direccion", "Estado", "Acciones"}, 0) {
        	    private static final long serialVersionUID = 1L;

        	    @Override
        	    public boolean isCellEditable(int row, int column) {
        	        return column == 6; // Solo la columna de acciones es editable
        	    }
        	};

        tablaClientes = new JTable(modeloTabla) {
            private static final long serialVersionUID = 1L;

            @Override
            public String getToolTipText(MouseEvent e) {
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                int col = columnAtPoint(p);

                if (col == 6) { // Columna de botones
                    int xDentroCelda = e.getX() - getCellRect(row, col, true).x;
                    if (xDentroCelda < 32) return "Modificar cliente";
                    else if (xDentroCelda < 64) return "Eliminar cliente";
                    else return "Imprimir datos del cliente";
                }
                return super.getToolTipText(e);
            }
        };

        tablaClientes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(255, 220, 190) : new Color(255, 240, 220));
                }
                return c;
            }
        });

        tablaClientes.setRowHeight(35);
        tablaClientes.getColumn("Acciones").setCellRenderer(new BotonRenderer(tablaClientes));
        tablaClientes.getColumn("Acciones").setCellEditor(new BotonEditorClientes(new JCheckBox(), this, tablaClientes));

        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        btnNuevoCliente.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            FormularioEditarCliente dialog = new FormularioEditarCliente(parent, this);
            dialog.setVisible(true);
        });

        cargarDatosClientes();
    }

    /**
     * Carga todos los datos de los clientes desde la base de datos
     * y los muestra en la tabla.
     */
    public void cargarDatosClientes() {
        modeloTabla.setRowCount(0); // Limpia todas las filas existentes en la tabla

        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) { // Obtiene la conexión a la base de datos
            PersonasDAO personasDAO = new PersonasDAO(); // Crea el DAO sin pasar la conexión al constructor
            List<Persona> listaPersonas = personasDAO.obtenerTodasLasPersonas(conn); // Pasa la conexión al método

            for (Persona persona : listaPersonas) {
                modeloTabla.addRow(new Object[]{
                    persona.getNombres(),
                    persona.getNumeroIdentificacion(),
                    persona.getCorreo(),
                    persona.getTelefono(),
                    persona.getDireccion(),
                    persona.getEstado().name(),
                    "Acciones"
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de clientes: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
}
