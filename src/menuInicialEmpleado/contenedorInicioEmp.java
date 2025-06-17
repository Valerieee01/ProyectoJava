package menuInicialEmpleado;

/*
 *Importar Librerias 
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import funciones.funciones;
import menuInicialAdministrador.paneCuentaAdmin;
import modelos.Usuario;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class contenedorInicioEmp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private Color naranjaPastel = new Color(0xFF, 0xE5, 0xCC);
    private CardLayout cardLayout;
    private static Usuario usuarioLogueado;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					contenedorInicioEmp frame = new contenedorInicioEmp(usuarioLogueado);
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
	public contenedorInicioEmp(Usuario usuario) {
		this.usuarioLogueado = usuario; 

		
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
		
		
		JLabel labelBienvenida = new JLabel("BIENVENIDO EMPLEADO");
		labelBienvenida.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 30));
		labelBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		ContenedorCardLayout.add(labelBienvenida, "name_12516905000400");
		
		
		//Intancviamos los paneles que se desean agregar al contenedor
		paneInicioEmpleado paneInicioEmpleado = new paneInicioEmpleado(cardLayout, ContenedorCardLayout);
		panelMantenimientosEmp paneMantenimientos = new panelMantenimientosEmp();
		panelReportesEmp paneTrabajos = new panelReportesEmp();
		paneSaldosEmp paneSaldos = new paneSaldosEmp();
		paneCuentaEmp paneCuentaEmp = new paneCuentaEmp(usuario);
		
		// agregamos los paneles al contenedor
		ContenedorCardLayout.add(paneMantenimientos, "paneMantenimientos");
		ContenedorCardLayout.add(paneTrabajos, "paneTrabajos");
		ContenedorCardLayout.add(paneInicioEmpleado, "paneInicioEmpleado");
		ContenedorCardLayout.add(paneSaldos, "paneSaldos");
		ContenedorCardLayout.add(paneCuentaEmp, "paneCuentaEmp");
		
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
		funciones.cargarImagenEnLabel(labelImgUser, "/menuInicialCliente/img_cliente/user.png", 75, 87); 
		barraLateralMenu.add(labelImgUser);
		
		//label para ir al inicio
		JLabel irInicio = new JLabel("Inicio", SwingConstants.CENTER);
		JLabel iconoLabelInicio = new JLabel();
		funciones.cargarImagenEnLabel(iconoLabelInicio, "/menuInicialCliente/img_cliente/home.png", 20, 20);
		irInicio.setIcon(iconoLabelInicio.getIcon()); 
		irInicio.setHorizontalTextPosition(SwingConstants.RIGHT);
		irInicio.setOpaque(true); 
		irInicio.setBackground(new Color(255, 255, 255)); 
		irInicio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    cardLayout.show(ContenedorCardLayout, "paneInicioEmpleado");
			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irInicio.setBackground(new Color(255, 180, 100));
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irInicio.setBackground(new Color(255, 255, 255)); 
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
		irMnatenimientos.setOpaque(true); 
		irMnatenimientos.setBackground(new Color(255, 255, 255)); 
		irMnatenimientos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    cardLayout.show(ContenedorCardLayout, "paneMantenimientos");
			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irMnatenimientos.setBackground(new Color(255, 180, 100)); 
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
				irMnatenimientos.setBackground(new Color(255, 255, 255));
		    }
		});
		irMnatenimientos.setBounds(0, 333, 199, 51);
		barraLateralMenu.add(irMnatenimientos);
		
		
		JLabel irTrabajos = new JLabel("Reportes", SwingConstants.CENTER);
		JLabel iconoLabelTrabajos = new JLabel();
		funciones.cargarImagenEnLabel(iconoLabelTrabajos, "/menuInicialCliente/img_cliente/work.png", 25, 25);
		irTrabajos.setIcon(iconoLabelTrabajos.getIcon());
		irTrabajos.setHorizontalTextPosition(SwingConstants.RIGHT);
		irTrabajos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irTrabajos.setHorizontalAlignment(SwingConstants.CENTER);
		irTrabajos.setOpaque(true);
		irTrabajos.setBackground(new Color(255, 255, 255)); 
		irTrabajos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    cardLayout.show(ContenedorCardLayout, "paneTrabajos");

			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irTrabajos.setBackground(new Color(255, 180, 100)); 
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irTrabajos.setBackground(new Color(255, 255, 255));
		    }
		});
		irTrabajos.setBounds(0, 410, 199, 51);
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
		
		if (usuarioLogueado != null) {
			labelNombreUser.setText(usuarioLogueado.getNombres());
			labelApellidoUser.setText(usuarioLogueado.getApellidos());
		} else {
			labelNombreUser.setText("Invitado");
			labelApellidoUser.setText("Admin");
		}
		
		
		JLabel irCuenta = new JLabel("Mi cuenta");
		irCuenta.setOpaque(true);
		irCuenta.setHorizontalAlignment(SwingConstants.CENTER);
		irCuenta.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irCuenta.setBackground(new Color(255, 255, 255)); // color base
		irCuenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    cardLayout.show(ContenedorCardLayout, "paneCuentaEmp");

			}
			@Override
		    public void mouseEntered(MouseEvent e) {
				irCuenta.setBackground(new Color(255, 180, 100)); 
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	irCuenta.setBackground(new Color(255, 255, 255)); 
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
		
		
	}
}
