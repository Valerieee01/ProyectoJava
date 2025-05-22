package menuInicialProveedor;


/*
 *Importar Clases 
 */

import funcionesLogin.funciones;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;


public class contenedorInicioProveedor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					contenedorInicioProveedor frame = new contenedorInicioProveedor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public contenedorInicioProveedor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1035, 856);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel barraLateralMenu = new JPanel();
		barraLateralMenu.setLayout(null);
		barraLateralMenu.setBackground(Color.WHITE);
		barraLateralMenu.setBounds(0, 0, 199, 817);
		contentPane.add(barraLateralMenu);
		
		JLabel labelImgUser = new JLabel("");
		labelImgUser.setHorizontalAlignment(SwingConstants.CENTER);
		labelImgUser.setBounds(62, 50, 75, 87);
		funciones.cargarImagenEnLabel(labelImgUser, "/menuInicialCliente/img_cliente/user.png", 75, 87);
		barraLateralMenu.add(labelImgUser);
		
		JLabel irInicio = new JLabel("Inicio", SwingConstants.CENTER);
		JLabel iconoLabelInicio = new JLabel();
		funciones.cargarImagenEnLabel(iconoLabelInicio, "/menuInicialCliente/img_cliente/home.png", 20, 20);
		irInicio.setIcon(iconoLabelInicio.getIcon());
		irInicio.setOpaque(true);
		irInicio.setHorizontalTextPosition(SwingConstants.RIGHT);
		irInicio.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irInicio.setBackground(Color.WHITE);
		irInicio.setBounds(0, 257, 199, 51);
		irInicio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irInicio.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irInicio.setBackground(new Color(255, 255, 255)); // vuelve al base
		    }
		});
		barraLateralMenu.add(irInicio);
		
		JLabel irContacto = new JLabel("Contacto", SwingConstants.CENTER);
		JLabel iconoLabelContacto = new JLabel();
		funciones.cargarImagenEnLabel(iconoLabelContacto, "/menuInicialCliente/img_cliente/contact.png", 30, 30);
		irContacto.setIcon(iconoLabelContacto.getIcon());
		irContacto.setOpaque(true);
		irContacto.setHorizontalTextPosition(SwingConstants.RIGHT);
		irContacto.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irContacto.setBackground(Color.WHITE);
		irContacto.setBounds(0, 333, 199, 51);
		irContacto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irContacto.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irContacto.setBackground(new Color(255, 255, 255)); // vuelve al base
		    }
		});
		barraLateralMenu.add(irContacto);
		
		JLabel lblNombrProveedor = new JLabel("Nombr Proveedor");
		lblNombrProveedor.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombrProveedor.setFont(new Font("Franklin Gothic Book", Font.BOLD, 16));
		lblNombrProveedor.setBounds(0, 148, 199, 41);
		barraLateralMenu.add(lblNombrProveedor);
		
		JLabel lblApellidoProveedor = new JLabel("Apellido Proveedor");
		lblApellidoProveedor.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidoProveedor.setFont(new Font("Franklin Gothic Book", Font.BOLD, 16));
		lblApellidoProveedor.setBounds(0, 178, 199, 41);
		barraLateralMenu.add(lblApellidoProveedor);
		
		JLabel irCuenta = new JLabel("Mi cuenta");
		irCuenta.setOpaque(true);
		irCuenta.setHorizontalAlignment(SwingConstants.CENTER);
		irCuenta.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irCuenta.setBackground(Color.WHITE);
		irCuenta.setBounds(0, 741, 199, 41);
		irCuenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irCuenta.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irCuenta.setBackground(new Color(255, 255, 255)); // vuelve al base
		    }
		});
		barraLateralMenu.add(irCuenta);
		
		JLabel labelImgLogo = new JLabel("");
		labelImgLogo.setBounds(21, 611, 162, 129);
		funciones.cargarImagenEnLabel(labelImgLogo, "../img_login/loga_empresa.jpg", 162, 129);
		barraLateralMenu.add(labelImgLogo);
		
		JPanel menuHorizontal = new JPanel();
		menuHorizontal.setLayout(null);
		menuHorizontal.setBackground(new Color(255, 128, 64));
		menuHorizontal.setBounds(199, 0, 820, 61);
		contentPane.add(menuHorizontal);
		
		JLabel irEmpleados = new JLabel("Empleados");
		irEmpleados.setOpaque(true);
		irEmpleados.setHorizontalAlignment(SwingConstants.CENTER);
		irEmpleados.setForeground(Color.WHITE);
		irEmpleados.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		irEmpleados.setBackground(new Color(255, 128, 64));
		irEmpleados.setBounds(659, 11, 133, 41);
		irEmpleados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
			 @Override
		    public void mouseEntered(MouseEvent e) {
			 	irEmpleados.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irEmpleados.setBackground(new Color(255, 128, 64)); // vuelve al base
		    }
		});
		menuHorizontal.add(irEmpleados);
		
		JLabel irEquipos = new JLabel("Equipos");
		irEquipos.setOpaque(true);
		irEquipos.setHorizontalAlignment(SwingConstants.CENTER);
		irEquipos.setForeground(Color.WHITE);
		irEquipos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		irEquipos.setBackground(new Color(255, 128, 64));
		irEquipos.setBounds(468, 11, 133, 41);
		irEquipos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	irEquipos.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irEquipos.setBackground(new Color(255, 128, 64)); // vuelve al base
		    }
		});
		menuHorizontal.add(irEquipos);
		
		JPanel panel = new JPanel();
		panel.setBounds(199, 61, 820, 756);
		contentPane.add(panel);
		panel.setLayout(new CardLayout(0, 0));
	}
}
