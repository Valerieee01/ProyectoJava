package menuInicialAdministrador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import clasesBotones.BotonEditor;
import clasesBotones.BotonRenderer;
import formularios.FormularioEditar;

public class panelEquipos extends JPanel {

    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;

    public panelEquipos() {
        setLayout(new BorderLayout());

        // Botón para agregar nuevo equipo
        JButton btnNuevoEquipo = new JButton("Nuevo Equipo");
        add(btnNuevoEquipo, BorderLayout.NORTH);
        
        // Modelo de la tabla
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Número", "Placa", "Descripción", "ID Cliente", "Fecha Registro", "Acciones"}, 0) {
            /*
			 */
			private static final long serialVersionUID = 1L;
			@Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna de acciones puede tener botones
                return column == 6;
            }
        };

        // Tabla
        tablaEquipos = new JTable(modeloTabla);
        tablaEquipos.getColumn("Acciones").setCellRenderer(new BotonRenderer());
        tablaEquipos.getColumn("Acciones").setCellEditor(new BotonEditor(new JCheckBox(), modeloTabla, tablaEquipos));
        add(new JScrollPane(tablaEquipos), BorderLayout.CENTER);

        // Acción del botón para nuevo equipo
        btnNuevoEquipo.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            FormularioEditar dialog = new FormularioEditar(parent, modeloTabla);
            dialog.setVisible(true);
        });
    }
 }
