package menuInicialAdministrador;

import funcionesLogin.funciones;


import javax.swing.JPanel;
import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;

import classBorder.BordeRedondeado;

import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class panelTrabajo extends JPanel {

	private static final long serialVersionUID = 1L;
    private Color naranjaPastel = new Color(0xFF, 0xE5, 0xCC);
    private JTextField fieldBusqueda;


	/**
	 * Create the panel.
	 */
	public panelTrabajo() {
		setForeground(new Color(255, 255, 255));
		setBackground(naranjaPastel);
		setLayout(null);
		
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBackground(new Color(255, 152, 102));
		panelBusqueda.setBounds(79, 22, 643, 82);
		add(panelBusqueda);
		panelBusqueda.setLayout(null);
		
		fieldBusqueda = new JTextField();
		fieldBusqueda.setForeground(new Color(255, 255, 255));
		fieldBusqueda.setBounds(385, 40, 188, 29);
		fieldBusqueda.setBorder(new BordeRedondeado(20, 20, Color.GRAY)); // Usa tu clase
		fieldBusqueda.setOpaque(false); // importante para ver el fondo redondeado
		panelBusqueda.add(fieldBusqueda);
		fieldBusqueda.setColumns(10);
		
		// Crear el calendario
		JDateChooser dateChooserFecha = new JDateChooser();
		dateChooserFecha.setForeground(new Color(255, 255, 255));
		dateChooserFecha.setDateFormatString("yyyy-MM-dd"); // Formato de fecha
		dateChooserFecha.setBounds(13, 40, 200, 29); 
		dateChooserFecha.setOpaque(false); // importante para ver el fondo redondeado
		dateChooserFecha.setBorder(new BordeRedondeado(20, 20, Color.GRAY)); // Usa tu clase
		panelBusqueda.add(dateChooserFecha);

		
		JLabel labelBusqueda = new JLabel("");
		labelBusqueda.setBounds(583, 41, 46, 29);
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
		labelTituloPanelBusqueda.setBounds(114, 8, 425, 24);
		panelBusqueda.add(labelTituloPanelBusqueda);
		
		JButton btnCrearTrabajo = new JButton("Nuevo");
		btnCrearTrabajo.setBackground(new Color(255, 155, 106));
		btnCrearTrabajo.setBounds(633, 136, 89, 37);
		add(btnCrearTrabajo);
		
		

	}
}
