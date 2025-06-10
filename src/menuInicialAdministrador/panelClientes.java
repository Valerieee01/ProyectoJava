package menuInicialAdministrador;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;

import clasesBotones.BotonEditorClientes;
import clasesBotones.BotonRenderer;
import formularios.FormularioEditarCliente;

public class panelClientes extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public panelClientes() {
        setLayout(new BorderLayout());

        // Botón para agregar nuevo equipo
        JButton btnNuevoProveedor = new JButton("Nuevo Cliente");
        btnNuevoProveedor.setToolTipText("Ingresa tu nuevo Cliente aqui");
        btnNuevoProveedor.setForeground(Color.WHITE);
        btnNuevoProveedor.setBackground(new Color(255, 146, 94));
        add(btnNuevoProveedor, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
        	    new Object[]{"Nombre Completo", "Identificacion", "Correo", "Telefono", "Direccion", "Estado", "Acciones"}, 0) {
        	    private static final long serialVersionUID = 1L;

        	    @Override
        	    public boolean isCellEditable(int row, int column) {
        	        return column == 6; 
        	    }
        	};

        // Crear la tabla correctamente con tooltips personalizados
        	tablaClientes = new JTable(modeloTabla) {
            /**
			 * 
			 */
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

        // Estilo zebra
        tablaClientes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        tablaClientes.setRowHeight(35);
        tablaClientes.getColumn("Acciones").setCellRenderer(new BotonRenderer(tablaClientes));
        tablaClientes.getColumn("Acciones").setCellEditor(new BotonEditorClientes(new JCheckBox(), modeloTabla, tablaClientes));

        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        // Acción del botón para agregar nuevo equipo
        btnNuevoProveedor.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            FormularioEditarCliente dialog = new FormularioEditarCliente(parent, modeloTabla);
            dialog.setVisible(true);
        });
    }
}
