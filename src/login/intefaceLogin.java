package login;
/**
 * Importacion Clases
 * */
import login.conectarFrames;

/**
 * Importación de librerias
 * */
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class intefaceLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fieldNombre;
	private JTextField fieldContrasena;
	private Color naranjaLogo = new Color(255, 102, 0); // RGB aproximado


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					intefaceLogin frame = new intefaceLogin();
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
	public intefaceLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1425, 858);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelTitulo = new JLabel("SmartCount");
		labelTitulo.setForeground(naranjaLogo);
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 32));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setBounds(505, 140, 419, 82);
		contentPane.add(labelTitulo);
		
		JLabel labelNombre = new JLabel("Nombre Usuario: ");
		labelNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelNombre.setBounds(522, 302, 151, 22);
		contentPane.add(labelNombre);
		
		fieldNombre = new JTextField();
		fieldNombre.setBounds(522, 334, 402, 50);
		fieldNombre.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		contentPane.add(fieldNombre);
		fieldNombre.setColumns(10);
		
		
		JLabel labelContrasena = new JLabel("Contraseña: ");
		labelContrasena.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelContrasena.setBounds(522, 409, 188, 27);
		contentPane.add(labelContrasena);
		
		fieldContrasena = new JTextField();
		fieldContrasena.setBounds(522, 446, 402, 50);
		fieldContrasena.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		contentPane.add(fieldContrasena);
		fieldContrasena.setColumns(10);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setBounds(629, 207, 200, 100);
		contentPane.add(labelLogo);
		cargarImagenEnLabel(labelLogo, "img_login/loga_empresa.jpg", 200, 100);
		
		JButton btnInicioSesion = new JButton("Iniciar Sesion");
		btnInicioSesion.addActionListener(e ->{
			
		});
		btnInicioSesion.setForeground(new Color(255, 255, 255));
		btnInicioSesion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInicioSesion.setBackground(new Color(255, 128, 0));
		btnInicioSesion.setBounds(630, 568, 180, 37);
		contentPane.add(btnInicioSesion);
		
		JLabel labelOlvidarContrasena = new JLabel("¿Olvidaste tu contraseña? Click Aquí");
		labelOlvidarContrasena.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelOlvidarContrasena.setHorizontalAlignment(SwingConstants.LEFT);
		labelOlvidarContrasena.setBounds(522, 519, 343, 22);
		contentPane.add(labelOlvidarContrasena);
		
		JLabel labelTienesCuenta = new JLabel("¿Aún tienes una cuenta?");
		labelTienesCuenta.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTienesCuenta.setBounds(1049, 41, 208, 22);
		contentPane.add(labelTienesCuenta);
		
		JButton btnIrRegistro = new JButton("Registrate Aquí");
		btnIrRegistro.addActionListener(e ->{
			conectarFrames.AbrirFrameSingin(this);
		});
			
		btnIrRegistro.setForeground(new Color(255, 255, 255));
		btnIrRegistro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIrRegistro.setBackground(new Color(255, 128, 0));
		btnIrRegistro.setBounds(1219, 27, 136, 37);
		contentPane.add(btnIrRegistro);
		
		JLabel labelInstagram = new JLabel("");
		labelInstagram.setBounds(558, 658, 50, 50);
		contentPane.add(labelInstagram);
		cargarImagenEnLabel(labelInstagram, "img_login/logotipo-de-instagram.png", 50, 50);

		
		JLabel labelFacebook = new JLabel("");
		labelFacebook.setBounds(647, 658, 50, 50);
		contentPane.add(labelFacebook);
		cargarImagenEnLabel(labelFacebook, "img_login/facebook.png", 50, 50);

		
		JLabel labelGoogle = new JLabel("");
		labelGoogle.setBounds(738, 658, 50, 50);
		contentPane.add(labelGoogle);
		cargarImagenEnLabel(labelGoogle, "img_login/cromo.png", 50, 50);

		
		JLabel labelGithub = new JLabel("");
		labelGithub.setBounds(820, 658, 50, 50);
		contentPane.add(labelGithub);
		cargarImagenEnLabel(labelGithub, "img_login/github.png", 50, 50);

	}
	
	
	
	
	public void cargarImagenEnLabel(JLabel label, String rutaInterna, int ancho, int alto) {
	    ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaInterna));
	    Image imagenOriginal = iconoOriginal.getImage();
	    Image imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
	    ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
	    label.setIcon(iconoEscalado);
	}
}
