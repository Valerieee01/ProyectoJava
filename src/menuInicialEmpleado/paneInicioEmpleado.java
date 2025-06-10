package menuInicialEmpleado;

/*
 *Importar Librerias 
 */


import funciones.funciones;

import java.awt.CardLayout;
import javax.swing.JLabel;
/*
 * Importacion de librerias 
 */
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;

public class paneInicioEmpleado extends JPanel {

	private static final long serialVersionUID = 1L;
    private Color naranjaPastel = new Color(0xFF, 0xE5, 0xCC);

	/**
	 * Create the panel.
	 */
	public paneInicioEmpleado() {
		setBackground(naranjaPastel);
		setLayout(null);
		
		
		JPanel contenedorCentral = new JPanel(new CardLayout());
		
		panelMantenimientos paneMantenimientos = new panelMantenimientos();
		panelReportes paneTrabajos = new panelReportes();
		paneSaldos paneSaldos = new paneSaldos();
		
		
		contenedorCentral.add(paneMantenimientos, "mantenimientos");
		contenedorCentral.add(paneTrabajos, "reportes");
		contenedorCentral.add(paneSaldos, "saldos");
		
		contenedorCentral.setBounds(720, 100, 600, 400); 
		add(contenedorCentral);

		JPanel panelMantenimientos = new JPanel();
		panelMantenimientos.setBackground(new Color(255, 176, 138));
		panelMantenimientos.setBounds(345, 73, 161, 238);
		panelMantenimientos.setLayout(null);
		add(panelMantenimientos);
		
		JButton btnIrMnto = new JButton("Mantenimientos");
		btnIrMnto.setForeground(new Color(255, 255, 255));
		btnIrMnto.setBackground(new Color(255, 152, 102));
		btnIrMnto.setBounds(10, 187, 141, 23);
		btnIrMnto.addActionListener(e ->funciones.cambiarPanel(contenedorCentral, "mantenimientos"));
		panelMantenimientos.add(btnIrMnto);
		
		JLabel labelimgMnatenimientos = new JLabel("New label");
		labelimgMnatenimientos.setBounds(42, 38, 89, 85);
		funciones.cargarImagenEnLabel(labelimgMnatenimientos, "/imgAdministrador/tuercas.png", 89, 85);
		panelMantenimientos.add(labelimgMnatenimientos);
		
		JPanel panelReportes = new JPanel();
		panelReportes.setBackground(new Color(255, 176, 138));
		panelReportes.setBounds(150, 460, 161, 238);
		panelReportes.setLayout(null);
		add(panelReportes);
		
		JButton btnIrReportes = new JButton("Reportes");
		btnIrReportes.setForeground(new Color(255, 255, 255));
		btnIrReportes.setBackground(new Color(255, 152, 102));
		btnIrReportes.setBounds(24, 190, 114, 23);
		btnIrReportes.addActionListener(e ->funciones.cambiarPanel(contenedorCentral, "reportes"));
		panelReportes.add(btnIrReportes);
		
		JLabel labelimgReportes = new JLabel("");
		labelimgReportes.setBounds(37, 33, 89, 85);
		funciones.cargarImagenEnLabel(labelimgReportes, "/imgAdministrador/reportes.png", 89, 85);
		panelReportes.add(labelimgReportes);
		
		JPanel panelTrabajos = new JPanel();
		panelTrabajos.setBackground(new Color(255, 176, 138));
		panelTrabajos.setBounds(514, 460, 161, 238);
		add(panelTrabajos);
		panelTrabajos.setLayout(null);
		
		JButton btnIrTrabajos = new JButton("Saldos");
		btnIrTrabajos.setForeground(new Color(255, 255, 255));
		btnIrTrabajos.setBackground(new Color(255, 152, 102));
		btnIrTrabajos.setBounds(10, 188, 141, 23);
		btnIrTrabajos.addActionListener(e ->funciones.cambiarPanel(contenedorCentral, "saldos"));
		panelTrabajos.add(btnIrTrabajos);
		
		JLabel labelimgTrabajos = new JLabel("New label");
		labelimgTrabajos.setBounds(37, 32, 89, 85);
		funciones.cargarImagenEnLabel(labelimgTrabajos, "/imgAdministrador/trabajo.png", 89, 85);
		panelTrabajos.add(labelimgTrabajos);
		
		// Establece tamaño y posición del contenedor central
		contenedorCentral.setBounds(720, 100, 600, 400); 
		add(contenedorCentral); 

	}
}
