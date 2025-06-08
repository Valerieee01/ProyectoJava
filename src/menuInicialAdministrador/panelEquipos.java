package menuInicialAdministrador;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;

import clasesBotones.BotonEditorEquipos;
import clasesBotones.BotonRenderer;
import formularios.FormularioEditar;

public class panelEquipos extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaEquipos_1;
    private DefaultTableModel modeloTabla;

    public panelEquipos() {
        setLayout(new BorderLayout());

        // Botón para agregar nuevo equipo
        JButton btnNuevoEquipo = new JButton("Nuevo Equipo");
        btnNuevoEquipo.setToolTipText("Ingresa tu nuevo equipo aqui");
        btnNuevoEquipo.setForeground(Color.WHITE);
        btnNuevoEquipo.setBackground(new Color(255, 146, 94));
        add(btnNuevoEquipo, BorderLayout.NORTH);

        // Modelo de la tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Número", "Placa", "Descripción", "ID Cliente", "Fecha Registro", "Acciones"}, 0) {
            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Solo la columna de acciones es editable
            }
        };

        // Crear la tabla correctamente con tooltips personalizados
        tablaEquipos_1 = new JTable(modeloTabla) {
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
                    if (xDentroCelda < 32) return "Modificar equipo";
                    else if (xDentroCelda < 64) return "Eliminar equipo";
                    else return "Imprimir datos del equipo";
                }
                return super.getToolTipText(e);
            }
        };

        // Estilo zebra
        tablaEquipos_1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        tablaEquipos_1.setRowHeight(35);
        tablaEquipos_1.getColumn("Acciones").setCellRenderer(new BotonRenderer(tablaEquipos_1));
        tablaEquipos_1.getColumn("Acciones").setCellEditor(new BotonEditorEquipos(new JCheckBox(), modeloTabla, tablaEquipos_1));

        add(new JScrollPane(tablaEquipos_1), BorderLayout.CENTER);

        // Acción del botón para agregar nuevo equipo
        btnNuevoEquipo.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            FormularioEditar dialog = new FormularioEditar(parent, modeloTabla);
            dialog.setVisible(true);
        });
    }
}
