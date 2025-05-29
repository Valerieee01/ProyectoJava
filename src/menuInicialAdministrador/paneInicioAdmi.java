package menuInicialAdministrador;

/*
 *Importar Librerias 
 */
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import funciones.funciones;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * Importacion de librerias 
 */
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.CardLayout;

public class paneInicioAdmi extends JPanel {

	private static final long serialVersionUID = 1L;
    private Color naranjaPastel = new Color(0xFF, 0xE5, 0xCC);

	/**
	 * Create the panel.
	 */
	public paneInicioAdmi() {
		setBackground(naranjaPastel);
		setLayout(null);
		
		JPanel panelInventario = new JPanel();
		panelInventario.setBackground(new Color(255, 176, 138));
		panelInventario.setBounds(150, 69, 161, 238);
		add(panelInventario);
		panelInventario.setLayout(null);
		
		JPanel contenedorCentral = new JPanel(new CardLayout());
		contenedorCentral.add(new paneInventario(), "inventario");
		contenedorCentral.add(new panelMantenimientos(), "mantenimientos");
		contenedorCentral.add(new panelReportes(), "reportes");
		contenedorCentral.add(new paneSaldos(), "saldos");

		
		JButton btnIrInventario = new JButton("Inventario");
		btnIrInventario.setForeground(new Color(255, 255, 255));
		btnIrInventario.setBackground(new Color(255, 152, 102));
		btnIrInventario.setBounds(23, 188, 116, 23);
		btnIrInventario.addActionListener(e ->funciones.cambiarPanel(contenedorCentral, "inventario"));
		panelInventario.add(btnIrInventario);
		
		JLabel labelimgInventario = new JLabel("");
		labelimgInventario.setBounds(37, 40, 89, 85);
		funciones.cargarImagenEnLabel(labelimgInventario, "/imgAdministrador/inventario.png", 89, 85);
		panelInventario.add(labelimgInventario);
		
		JPanel panelMantenimientos = new JPanel();
		panelMantenimientos.setBackground(new Color(255, 176, 138));
		panelMantenimientos.setBounds(514, 69, 161, 238);
		add(panelMantenimientos);
		panelMantenimientos.setLayout(null);
		
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
		add(panelReportes);
		panelReportes.setLayout(null);
		
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

	}
}
