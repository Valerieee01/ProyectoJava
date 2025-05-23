package menuInicialAdministrador;

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

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class contenedorInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private Color naranjaPastel = new Color(0xFF, 0xE5, 0xCC);
    private CardLayout cardLayout;

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
		
		//Inicializamos el CardLayout
		cardLayout = new CardLayout();
		
		// Creamos el panel contenedor de layouts
		JPanel ContenedorCardLayout = new JPanel();
		ContenedorCardLayout.setLayout(cardLayout);
		ContenedorCardLayout.setBounds(199, 61, 820, 756);
		contentPane.add(ContenedorCardLayout);
		ContenedorCardLayout.setBackground(naranjaPastel);
		
		
		JLabel labelBienvenida = new JLabel("BIENVENIDO ADMINISTRADOR");
		labelBienvenida.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 30));
		labelBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		ContenedorCardLayout.add(labelBienvenida, "name_12516905000400");
		
		
		//Intancviamos los paneles que se desean agregar al contenedor
		paneInicioAdmi paneInicioAdmin = new paneInicioAdmi();
		panelMantenimientos paneMantenimientos = new panelMantenimientos();
		panelTrabajo paneTrabajos = new panelTrabajo();

		
		
		// agregamos los paneles al contenedor
		ContenedorCardLayout.add(paneInicioAdmin, "paneInicioAdmin");
		ContenedorCardLayout.add(paneMantenimientos, "paneMantenimientos");
		ContenedorCardLayout.add(paneTrabajos, "paneTrabajos");


		
		
		
		
		//creamos el menu lateral 
		JPanel barraLateralMenu = new JPanel();
		barraLateralMenu.setBackground(new Color(255, 255, 255));
		barraLateralMenu.setBounds(0, 0, 199, 817);
		contentPane.add(barraLateralMenu);
		barraLateralMenu.setLayout(null);
	
		//label para imagen de usuario
		JLabel labelImgUser = new JLabel("");
		labelImgUser.setHorizontalAlignment(SwingConstants.CENTER);
		labelImgUser.setBounds(62, 50, 75, 87);
		funciones.cargarImagenEnLabel(labelImgUser, "/menuInicialCliente/img_cliente/user.png", 75, 87); // Llamamos a la función de agregar imagenes al label
		barraLateralMenu.add(labelImgUser);
		
		//label para ir al inicio
		JLabel irInicio = new JLabel("Inicio", SwingConstants.CENTER);
		JLabel iconoLabelInicio = new JLabel();
		funciones.cargarImagenEnLabel(iconoLabelInicio, "/menuInicialCliente/img_cliente/home.png", 20, 20);// carga la imagen 
		irInicio.setIcon(iconoLabelInicio.getIcon()); // se le asigna un icono 
		irInicio.setHorizontalTextPosition(SwingConstants.RIGHT);
		irInicio.setOpaque(true); // NECESARIO
		irInicio.setBackground(new Color(255, 255, 255)); // color base
		irInicio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    cardLayout.show(ContenedorCardLayout, "paneInicioAdmin");
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
		irInicio.setBounds(0, 257, 199, 51);
		barraLateralMenu.add(irInicio);
		
		
		
		JLabel irMnatenimientos = new JLabel("Mantenimientos", SwingConstants.CENTER);
		JLabel iconoLabelMantenimientos = new JLabel();
		funciones.cargarImagenEnLabel(iconoLabelMantenimientos, "/menuInicialCliente/img_cliente/tools.png", 20, 20);
		irMnatenimientos.setIcon(iconoLabelMantenimientos.getIcon());
		irMnatenimientos.setHorizontalTextPosition(SwingConstants.RIGHT);
		irMnatenimientos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irMnatenimientos.setHorizontalAlignment(SwingConstants.CENTER);
		irMnatenimientos.setOpaque(true); // NECESARIO
		irMnatenimientos.setBackground(new Color(255, 255, 255)); // color base
		irMnatenimientos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    cardLayout.show(ContenedorCardLayout, "paneMantenimientos");
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
		irMnatenimientos.setBounds(0, 333, 199, 51);
		barraLateralMenu.add(irMnatenimientos);
		
		JLabel irInventario = new JLabel("Inventario", SwingConstants.CENTER);
		JLabel iconoLabelInventario = new JLabel();
		funciones.cargarImagenEnLabel(iconoLabelInventario, "/menuInicialCliente/img_cliente/invent.png", 30, 30);
		irInventario.setIcon(iconoLabelInventario.getIcon());
		irInventario.setHorizontalTextPosition(SwingConstants.RIGHT);
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
		irInventario.setBounds(0, 408, 199, 51);
		barraLateralMenu.add(irInventario);
		
		JLabel irTrabajos = new JLabel("Control de Trabajos", SwingConstants.CENTER);
		JLabel iconoLabelTrabajos = new JLabel();
		funciones.cargarImagenEnLabel(iconoLabelTrabajos, "/menuInicialCliente/img_cliente/work.png", 25, 25);
		irTrabajos.setIcon(iconoLabelTrabajos.getIcon());
		irTrabajos.setHorizontalTextPosition(SwingConstants.RIGHT);
		irTrabajos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irTrabajos.setHorizontalAlignment(SwingConstants.CENTER);
		irTrabajos.setOpaque(true); // NECESARIO
		irTrabajos.setBackground(new Color(255, 255, 255)); // color base
		irTrabajos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    cardLayout.show(ContenedorCardLayout, "paneTrabajos");

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
		irTrabajos.setBounds(0, 479, 199, 51);
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
		
		JLabel irCuenta = new JLabel("Mi cuenta");
		irCuenta.setOpaque(true);
		irCuenta.setHorizontalAlignment(SwingConstants.CENTER);
		irCuenta.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irCuenta.setBackground(new Color(255, 255, 255)); // color base
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
		irCuenta.setBackground(Color.WHITE);
		irCuenta.setBounds(0, 741, 199, 41);
		barraLateralMenu.add(irCuenta);
		
		JLabel labelImgLogo = new JLabel("");
		labelImgLogo.setBounds(21, 611, 162, 129);
		funciones.cargarImagenEnLabel(labelImgLogo, "../img_login/loga_empresa.jpg", 162, 129);
		barraLateralMenu.add(labelImgLogo);
		
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

	}
}
