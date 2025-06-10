package menuInicialEmpleado;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;

import classBorder.BordeRedondeado;
import funciones.funciones;

import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import javax.swing.JDialog;

public class panelReportes extends JPanel {

	private static final long serialVersionUID = 1L;
    private Color naranjaPastel = new Color(0xFF, 0xE5, 0xCC);
    private JTextField fieldBusqueda;
    private DefaultTableModel modeloTabla;
    private JTable tablaTrabajos;
    private TableRowSorter<DefaultTableModel> sorter;

	/**
	 * Create the panel.
	 */
	public panelReportes() {
		setForeground(new Color(255, 255, 255));
		setBackground(naranjaPastel);
		setLayout(null);
		
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBackground(new Color(255, 152, 102));
		panelBusqueda.setBounds(0, 11, 820, 77);
		add(panelBusqueda);
		panelBusqueda.setLayout(null);
		
		fieldBusqueda = new JTextField();
		fieldBusqueda.setBackground(new Color(255, 255, 255));
		fieldBusqueda.setForeground(new Color(255, 255, 255));
		fieldBusqueda.setBounds(276, 40, 188, 29);
		fieldBusqueda.setBorder(new BordeRedondeado(20, 20, Color.white)); // Usa tu clase
		fieldBusqueda.setOpaque(false); // importante para ver el fondo redondeado
		panelBusqueda.add(fieldBusqueda);
		fieldBusqueda.setColumns(10);
		
		// Crear el calendario
		JDateChooser dateChooserFecha = new JDateChooser();
		dateChooserFecha.setForeground(new Color(255, 255, 255));
		dateChooserFecha.setDateFormatString("yyyy-MM-dd"); // Formato de fecha
		dateChooserFecha.setBounds(13, 40, 200, 29); 
		panelBusqueda.add(dateChooserFecha);

		
		JLabel labelBusqueda = new JLabel("");
		labelBusqueda.setBounds(474, 40, 46, 29);
		funciones.cargarImagenEnLabel(labelBusqueda, "/imgAdministrador/lupa.png", 20,20);
		panelBusqueda.add(labelBusqueda);
		
		JLabel labelFecha = new JLabel("");
		labelFecha.setBounds(220, 43, 76, 23);
		funciones.cargarImagenEnLabel(labelFecha, "/imgAdministrador/calendar.png",20,20);
		panelBusqueda.add(labelFecha);
		
		JLabel labelTituloPanelBusqueda = new JLabel("Filtro de busqueda");
		labelTituloPanelBusqueda.setForeground(new Color(255, 255, 255));
		labelTituloPanelBusqueda.setHorizontalAlignment(SwingConstants.CENTER);
		labelTituloPanelBusqueda.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelTituloPanelBusqueda.setBounds(231, 5, 425, 24);
		panelBusqueda.add(labelTituloPanelBusqueda);
	
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(721, 32, 89, 37);
		panelBusqueda.add(btnBuscar);
		btnBuscar.setBackground(new Color(255, 177, 140));
		
		        // Acción del botón "Nuevo"
		        btnBuscar.addActionListener(e -> mostrarFormularioNuevoTrabajo());
		
		JPanel panelContenedorTabla = new JPanel();
		panelContenedorTabla.setBackground(new Color(255, 190, 159));
		panelContenedorTabla.setBounds(0, 138, 820, 568);
		add(panelContenedorTabla);
		
		 // Tabla de Trabajos
        String[] columnas = {"Cliente","N° Cotización", "Descripción", "Fecha", "Valor Total","Valor Pagado","Mora","Acciones" };
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaTrabajos = new JTable(modeloTabla);
        sorter = new TableRowSorter<>(modeloTabla);
        panelContenedorTabla.setLayout(null);
        tablaTrabajos.setRowSorter(sorter);
        
        tablaTrabajos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 220, 190) : new Color(255, 240, 220));
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tablaTrabajos);
        scroll.setBounds(33, 65, 747, 469);
        panelContenedorTabla.add(scroll);
        
        JLabel labelReportesTitulo = new JLabel("REPORTES");
        labelReportesTitulo.setForeground(new Color(255, 255, 255));
        labelReportesTitulo.setBackground(new Color(255, 128, 64));
        labelReportesTitulo.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 34));
        labelReportesTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelReportesTitulo.setBounds(273, 23, 301, 30);
        panelContenedorTabla.add(labelReportesTitulo);

        // Datos de prueba
        modeloTabla.addRow(new Object[] { "001", "Cliente A", "Revisión de motor", "2025-05-01", "Pagado" });
        modeloTabla.addRow(new Object[] { "002", "Cliente B", "Cambio de aceite", "2025-05-05", "Pendiente" });
		

	}

	private void mostrarFormularioNuevoTrabajo() {
        
	}
}
