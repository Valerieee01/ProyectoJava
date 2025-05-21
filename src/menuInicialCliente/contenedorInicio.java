package menuInicialCliente;

/*
 *Importar Clases 
 */

import funcionesLogin.funciones;


/*
 *Importar Librerias 
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.AlphaComposite;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class contenedorInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					contenedorInicio frame = new contenedorInicio();
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
	public contenedorInicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1035, 856);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel barraLateralMenu = new JPanel();
		barraLateralMenu.setBackground(new Color(255, 255, 255));
		barraLateralMenu.setBounds(0, 0, 199, 817);
		contentPane.add(barraLateralMenu);
		barraLateralMenu.setLayout(null);
		
		JLabel labelImgUser = new JLabel("");
		labelImgUser.setHorizontalAlignment(SwingConstants.CENTER);
		labelImgUser.setBounds(62, 50, 75, 87);
		funciones.cargarImagenEnLabel(labelImgUser, "/menuInicialCliente/img_cliente/user.png", 75, 87);
		barraLateralMenu.add(labelImgUser);
		
		JLabel irInicio = new JLabel("Inicio");
		irInicio.setOpaque(true); // NECESARIO
		irInicio.setBackground(new Color(255, 255, 255)); // color base
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
		irInicio.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irInicio.setHorizontalAlignment(SwingConstants.CENTER);
		irInicio.setBounds(0, 257, 199, 41);
		barraLateralMenu.add(irInicio);
		
		JLabel irMnatenimientos = new JLabel("Mantenimientos");
		irMnatenimientos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irMnatenimientos.setHorizontalAlignment(SwingConstants.CENTER);
		irMnatenimientos.setOpaque(true); // NECESARIO
		irMnatenimientos.setBackground(new Color(255, 255, 255)); // color base
		irMnatenimientos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irMnatenimientos.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
				irMnatenimientos.setBackground(new Color(255, 255, 255)); // vuelve al base
		    }
		});
		irMnatenimientos.setBounds(0, 333, 199, 41);
		barraLateralMenu.add(irMnatenimientos);
		
		JLabel irInventario = new JLabel("Inventario");
		irInventario.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irInventario.setHorizontalAlignment(SwingConstants.CENTER);
		irInventario.setOpaque(true); // NECESARIO
		irInventario.setBackground(new Color(255, 255, 255)); // color base
		irInventario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irInventario.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irInventario.setBackground(new Color(255, 255, 255)); // vuelve al base
		    }
		});
		irInventario.setBounds(0, 408, 199, 41);
		barraLateralMenu.add(irInventario);
		
		JLabel irTrabajos = new JLabel("Control de Trabajos");
		irTrabajos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irTrabajos.setHorizontalAlignment(SwingConstants.CENTER);
		irTrabajos.setOpaque(true); // NECESARIO
		irTrabajos.setBackground(new Color(255, 255, 255)); // color base
		irTrabajos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irTrabajos.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irTrabajos.setBackground(new Color(255, 255, 255)); // vuelve al base
		    }
		});
		irTrabajos.setBounds(0, 479, 199, 41);
		barraLateralMenu.add(irTrabajos);
		
		JLabel labelNombreUser = new JLabel("Valerie Zharmel");
		labelNombreUser.setHorizontalAlignment(SwingConstants.CENTER);
		labelNombreUser.setFont(new Font("Franklin Gothic Book", Font.BOLD, 16));
		labelNombreUser.setBounds(0, 148, 199, 41);
		barraLateralMenu.add(labelNombreUser);
		
		JLabel labelApellidoUser = new JLabel("Afanador Perez");
		labelApellidoUser.setHorizontalAlignment(SwingConstants.CENTER);
		labelApellidoUser.setFont(new Font("Franklin Gothic Book", Font.BOLD, 16));
		labelApellidoUser.setBounds(0, 178, 199, 41);
		barraLateralMenu.add(labelApellidoUser);
		
		JPanel menuHorizontal = new JPanel();
		menuHorizontal.setBackground(new Color(255, 128, 64));
		menuHorizontal.setBounds(199, 0, 820, 61);
		contentPane.add(menuHorizontal);
		menuHorizontal.setLayout(null);
		
		JLabel irClientes = new JLabel("Clientes");
		irClientes.setForeground(new Color(255, 255, 255));
		irClientes.setBackground(new Color(255, 128, 64));
		irClientes.setHorizontalAlignment(SwingConstants.CENTER);
		irClientes.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		irClientes.setOpaque(true); // NECESARIO
		irClientes.setBackground(new Color(255, 128, 64)); // color base
		irClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irClientes.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irClientes.setBackground(new Color(255, 128, 64)); // vuelve al base
		    }
		});
		irClientes.setBounds(43, 11, 133, 41);
		menuHorizontal.add(irClientes);
		
		JLabel irProveedores = new JLabel("Proveedores");
		irProveedores.setForeground(new Color(255, 255, 255));
		irProveedores.setHorizontalAlignment(SwingConstants.CENTER);
		irProveedores.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		irProveedores.setOpaque(true); // NECESARIO
		irProveedores.setBackground(new Color(255, 128, 64)); // color base
		irProveedores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			 @Override
		    public void mouseEntered(MouseEvent e) {
				irProveedores.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
				irProveedores.setBackground(new Color(255, 128, 64)); // vuelve al base
		    }
		});
		irProveedores.setBounds(255, 11, 133, 41);
		menuHorizontal.add(irProveedores);
		
		JLabel irEmpleados = new JLabel("Empleados");
		irEmpleados.setForeground(new Color(255, 255, 255));
		irEmpleados.setHorizontalAlignment(SwingConstants.CENTER);
		irEmpleados.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		irEmpleados.setOpaque(true); // NECESARIO
		irEmpleados.setBackground(new Color(255, 128, 64)); // color base
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
		irEmpleados.setBounds(659, 11, 133, 41);
		menuHorizontal.add(irEmpleados);
		
		JLabel irEquipos = new JLabel("Equipos");
		irEquipos.setForeground(new Color(255, 255, 255));
		irEquipos.setHorizontalAlignment(SwingConstants.CENTER);
		irEquipos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		irEquipos.setOpaque(true);
		irEquipos.setBackground(new Color(255, 128, 64)); // color base
		irEquipos.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	irEquipos.setBackground(new Color(255, 180, 100)); // color hover más claro
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irEquipos.setBackground(new Color(255, 128, 64)); // vuelve al base
		    }
		});
		irEquipos.setBounds(468, 11, 133, 41);
		menuHorizontal.add(irEquipos);
		
		JPanel ContenedorCardLayout = new JPanel();
		ContenedorCardLayout.setBounds(199, 61, 820, 756);
		contentPane.add(ContenedorCardLayout);
		ContenedorCardLayout.setLayout(new CardLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("BIENVENIDO ADMINISTRADOR");
		lblNewLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ContenedorCardLayout.add(lblNewLabel, "name_12516905000400");
		
		
		
	}
}
