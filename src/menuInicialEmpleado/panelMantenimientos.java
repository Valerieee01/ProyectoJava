package menuInicialEmpleado;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

public class panelMantenimientos extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtBuscarEquipo;
    private JComboBox<String> comboTipo;
    private JDateChooser dateChooser;
    private TableRowSorter<DefaultTableModel> sorter;


    public panelMantenimientos() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(255, 235, 215));

        // Panel superior con filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(new Color(255, 200, 160));

        txtBuscarEquipo = new JTextField(10);
        comboTipo = new JComboBox<>(new String[]{"Todos", "Preventivo", "Correctivo"});
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        panelFiltros.add(new JLabel("Equipo:"));
        panelFiltros.add(txtBuscarEquipo);
        panelFiltros.add(new JLabel("Tipo:") );
        panelFiltros.add(comboTipo);
        panelFiltros.add(new JLabel("Fecha:"));
        panelFiltros.add(dateChooser);

        add(panelFiltros, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Equipo", "Tipo Mantenimiento", "Fecha", "Empleado"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        sorter = new TableRowSorter<>(modelo);

        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 220, 190) : new Color(255, 240, 220));
                return c;
            }
        });

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(255, 200, 160));

        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.setBackground(new Color(255, 255, 255));
        btnNuevo.setForeground(new Color(255, 128, 64));
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 255, 255));
        btnEditar.setForeground(new Color(255, 128, 64));
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(255, 255, 255));
        btnEliminar.setForeground(new Color(255, 128, 64));

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        // Datos de ejemplo
        modelo.addRow(new Object[]{1, "Compresor", "Preventivo", "2025-06-01", "Juan Pérez"});
        modelo.addRow(new Object[]{2, "Generador", "Correctivo", "2025-06-03", "María Gómez"});
      
    }
}
