package menuCliente;

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

public class contenedorInicioCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					contenedorInicioCliente frame = new contenedorInicioCliente();
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
	public contenedorInicioCliente() {
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
		irInicio.setHorizontalTextPosition(SwingConstants.RIGHT);
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
		
		JLabel irMnatenimientos = new JLabel("Mantenimientos", SwingConstants.CENTER);
		JLabel iconoLabelMantenimientos = new JLabel();
		funciones.cargarImagenEnLabel(iconoLabelMantenimientos, "/menuInicialCliente/img_cliente/tools.png", 20, 20);
		irMnatenimientos.setIcon(iconoLabelMantenimientos.getIcon());
		irMnatenimientos.setHorizontalTextPosition(SwingConstants.RIGHT);
		irMnatenimientos.setOpaque(true);
		irMnatenimientos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irMnatenimientos.setBackground(Color.WHITE);
		irMnatenimientos.setBounds(0, 333, 199, 51);
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
		barraLateralMenu.add(irMnatenimientos);
		
		JLabel irTrabajos = new JLabel("Control de Trabajos", SwingConstants.CENTER);
		JLabel iconoLabelTrabajos = new JLabel();
		funciones.cargarImagenEnLabel(iconoLabelTrabajos, "/menuInicialCliente/img_cliente/work.png", 25, 25);
		irTrabajos.setIcon(iconoLabelTrabajos.getIcon());
		irTrabajos.setOpaque(true);
		irTrabajos.setHorizontalTextPosition(SwingConstants.RIGHT);
		irTrabajos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		irTrabajos.setBackground(Color.WHITE);
		irTrabajos.setBounds(0, 411, 199, 51);
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
		barraLateralMenu.add(irTrabajos);
		
		
		JLabel lblNombreCliente = new JLabel("Nombre Cliente");
		lblNombreCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreCliente.setFont(new Font("Franklin Gothic Book", Font.BOLD, 16));
		lblNombreCliente.setBounds(0, 148, 199, 41);
		barraLateralMenu.add(lblNombreCliente);
		
		JLabel lblApellidoCliente = new JLabel("Apellido Cliente");
		lblApellidoCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidoCliente.setFont(new Font("Franklin Gothic Book", Font.BOLD, 16));
		lblApellidoCliente.setBounds(0, 178, 199, 41);
		barraLateralMenu.add(lblApellidoCliente);
		
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
		
		JLabel irEquipos = new JLabel("Equipos");
		irEquipos.setOpaque(true);
		irEquipos.setHorizontalAlignment(SwingConstants.CENTER);
		irEquipos.setForeground(Color.WHITE);
		irEquipos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		irEquipos.setBackground(new Color(255, 128, 64));
		irEquipos.setBounds(658, 11, 133, 41);
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
		
		JLabel lblBienvenidoCliente = new JLabel("BIENVENIDO CLIENTE");
		lblBienvenidoCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenidoCliente.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 30));
		lblBienvenidoCliente.setBounds(199, 61, 820, 756);
		contentPane.add(lblBienvenidoCliente);
		
		
		
		
	}

}
