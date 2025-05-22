package menuInicialAdministrador;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;

public class paneInicio extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public paneInicio() {
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		JButton btnMantenimientos = new JButton("Mantenimientos");
		btnMantenimientos.setBounds(168, 148, 122, 125);
		add(btnMantenimientos);
		
		JButton btnTrabajos = new JButton("Control Trabajos");
		btnTrabajos.setBounds(493, 148, 122, 125);
		add(btnTrabajos);
		
		JButton btnInventario = new JButton("Inventario");
		btnInventario.setBounds(168, 410, 122, 125);
		add(btnInventario);
		
		JButton btnReportes = new JButton("Reportes");
		btnReportes.setBounds(493, 410, 122, 125);
		add(btnReportes);

	}
}
