package menuInicialAdministrador;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class panelMantenimientos extends JPanel {

	private static final long serialVersionUID = 1L;
    private Color naranjaPastel = new Color(0xFF, 0xE5, 0xCC);

	/**
	 * Create the panel.
	 */
	public panelMantenimientos() {
		setBackground(naranjaPastel);
		setLayout(null);
		

        JPanel panel = new JPanel();
        panel.setBounds(127, 137, 564, 491);
        panel.setLayout(new BorderLayout()); // Establecemos BorderLayout para que la tabla se ajuste
        add(panel);

        // Datos de ejemplo para la tabla
        String[] columnas = {"ID", "Equipo", "Tipo Mantenimiento", "Fecha", "Empleado"};
        Object[][] datos = {
            {"1", "Compresor", "Preventivo", "2025-06-01", "Juan Pérez"},
            {"2", "Generador", "Correctivo", "2025-06-03", "María Gómez"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
            {"3", "Aire Acondicionado", "Preventivo", "2025-06-05", "Carlos Díaz"},
        };

        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(datos, columnas);
        JTable tabla = new JTable(model);
        tabla.setBackground(new Color(255, 170, 130));
        tabla.setFillsViewportHeight(true); // Asegura que la tabla se ajuste al viewport del scroll

        // Crear scrollPane con la tabla y añadirlo al panel
        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);
	}
}
