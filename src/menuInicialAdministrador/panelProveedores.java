package menuInicialAdministrador;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import clasesBotones.BotonEditorProveedores;
import clasesBotones.BotonRenderer;
import formularios.FormularioEditarProveedor;
import modelos.Persona;
import DAO.PersonasDAO;
import util.ConnectionADMIN; 


public class panelProveedores extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaProveedores;
    private DefaultTableModel modeloTabla;

    public panelProveedores() {
        setLayout(new BorderLayout());

        // Botón para agregar nuevo equipo
        JButton btnNuevoProveedor = new JButton("Nuevo Proveedor");
        btnNuevoProveedor.setToolTipText("Ingresa tu nuevo proveedor aqui");
        btnNuevoProveedor.setForeground(Color.WHITE);
        btnNuevoProveedor.setBackground(new Color(255, 146, 94));
        add(btnNuevoProveedor, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
        	    new Object[]{"Nombre Completo", "Identificacion", "Correo", "Telefono","Estado", "Acciones"}, 0) {
        	    private static final long serialVersionUID = 1L;

        	    @Override
        	    public boolean isCellEditable(int row, int column) {
        	        return column == 5; 
        	    }
        	};

        // Crear la tabla correctamente con tooltips personalizados
        tablaProveedores = new JTable(modeloTabla) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public String getToolTipText(MouseEvent e) {
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                int col = columnAtPoint(p);

                if (col == 5) { // Columna de botones
                    int xDentroCelda = e.getX() - getCellRect(row, col, true).x;
                    if (xDentroCelda < 32) return "Modificar equipo";
                    else if (xDentroCelda < 64) return "Eliminar equipo";
                    else return "Imprimir datos del equipo";
                }
                return super.getToolTipText(e);
            }
        };

        // Estilo zebra
        tablaProveedores.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            /**
			 * 
			 */
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

        tablaProveedores.setRowHeight(35);
        tablaProveedores.setAutoCreateRowSorter(true);
        tablaProveedores.getColumn("Acciones").setCellRenderer(new BotonRenderer(tablaProveedores));
        tablaProveedores.getColumn("Acciones").setCellEditor(new BotonEditorProveedores(new JCheckBox(), this, tablaProveedores));

        add(new JScrollPane(tablaProveedores), BorderLayout.CENTER);

        // Acción del botón para agregar nuevo equipo
        btnNuevoProveedor.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            FormularioEditarProveedor dialog = new FormularioEditarProveedor(parent,this);
            dialog.setVisible(true);
        });
        cargarDatosProveedores();
    }
    
    /**
     * Carga todos los datos de los clientes desde la base de datos
     * y los muestra en la tabla.
     */
    public void cargarDatosProveedores() {
        modeloTabla.setRowCount(0); // Limpia todas las filas existentes en la tabla

        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) { // Obtiene la conexión a la base de datos
            PersonasDAO personasDAO = new PersonasDAO(); // Crea el DAO sin pasar la conexión al constructor
            List<Persona> listaPersonas = personasDAO.obtenerTodasLasPersonasProveedores(conn); // Pasa la conexión al método

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
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de proveedores: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
}
