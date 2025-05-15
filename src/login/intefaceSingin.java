package login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class intefaceSingin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPaneRegister;
	private final JPanel contentPane_1 = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField fieldNombre;
	private JTextField fieldApellidos;
	private JTextField fieldCorreo;
	private JTextField fieldConfirmaContra;
	private JTextField fieldTipoId;
	private JTextField fieldNumId;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					intefaceSingin frame = new intefaceSingin();
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
	public intefaceSingin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1425, 858);
		setLocationRelativeTo(null);

		contentPaneRegister = new JPanel();
		contentPaneRegister.setBackground(new Color(255, 255, 255));
		contentPaneRegister.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPaneRegister);
		contentPaneRegister.setLayout(null);
		contentPane_1.setBounds(217, 10, 1, 1);
		contentPaneRegister.add(contentPane_1);
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setBackground(Color.WHITE);
		
		JLabel lblNewLabel = new JLabel("SmartCount");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 102, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblNewLabel.setBounds(522, 115, 419, 82);
		contentPane_1.add(lblNewLabel);
		
		
		JLabel labelNombre = new JLabel("Nombre:");
		labelNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelNombre.setBounds(335, 282, 151, 22);
		contentPane_1.add(labelNombre);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		textField.setBounds(335, 314, 402, 50);
		contentPane_1.add(textField);
		
		JLabel labelContrasena = new JLabel("Contraseña: ");
		labelContrasena.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelContrasena.setBounds(335, 389, 188, 27);
		contentPane_1.add(labelContrasena);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		textField_1.setBounds(335, 426, 402, 50);
		contentPane_1.add(textField_1);
	
		JButton btnInicioSesion = new JButton("Iniciar Sesion");
		btnInicioSesion.setForeground(Color.WHITE);
		btnInicioSesion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInicioSesion.setBackground(new Color(255, 128, 0));
		btnInicioSesion.setBounds(649, 569, 180, 37);
		contentPane_1.add(btnInicioSesion);
		
		JLabel lblNewLabel_2 = new JLabel("¿Ya tienes una cuenta?");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(1049, 41, 208, 22);
		contentPane_1.add(lblNewLabel_2);
		
		JButton btnIrRegistro = new JButton("Registrate Aquí");
		btnIrRegistro.setForeground(Color.WHITE);
		btnIrRegistro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIrRegistro.setBackground(new Color(255, 128, 0));
		btnIrRegistro.setBounds(1219, 27, 136, 37);
		contentPane_1.add(btnIrRegistro);
	
		JLabel lblApellido = new JLabel("Correo:");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblApellido.setBounds(770, 282, 151, 22);
		contentPane_1.add(lblApellido);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		textField_2.setBounds(770, 314, 402, 50);
		contentPane_1.add(textField_2);
		
		JLabel lblConfirmacinOntrasea = new JLabel("Confirmación contraseña: ");
		lblConfirmacinOntrasea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblConfirmacinOntrasea.setBounds(770, 389, 188, 27);
		contentPane_1.add(lblConfirmacinOntrasea);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		textField_3.setBounds(770, 426, 402, 50);
		contentPane_1.add(textField_3);
		
		JLabel labelTitulo = new JLabel("SmartCount");
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setForeground(new Color(255, 102, 0));
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 32));
		labelTitulo.setBounds(496, 37, 419, 82);
		contentPaneRegister.add(labelTitulo);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setBackground(new Color(255, 255, 255));
		labelLogo.setBounds(762, 19, 200, 100);
		contentPaneRegister.add(labelLogo);
		cargarImagenEnLabel(labelLogo, "img_login/loga_empresa.jpg", 200, 100);
		
		JLabel labelNombre_1 = new JLabel("Nombres:");
		labelNombre_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelNombre_1.setBounds(45, 195, 151, 22);
		contentPaneRegister.add(labelNombre_1);
		
		fieldNombre = new JTextField();
		fieldNombre.setColumns(10);
		fieldNombre.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldNombre.setBounds(45, 227, 402, 50);
		contentPaneRegister.add(fieldNombre);
		
		JLabel labelApellidos = new JLabel("Apellidos: ");
		labelApellidos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelApellidos.setBounds(45, 302, 188, 27);
		contentPaneRegister.add(labelApellidos);
		
		fieldApellidos = new JTextField();
		fieldApellidos.setColumns(10);
		fieldApellidos.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldApellidos.setBounds(45, 339, 402, 50);
		contentPaneRegister.add(fieldApellidos);
		
		JLabel labelEdad = new JLabel("Edad: ");
		labelEdad.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelEdad.setBounds(508, 195, 151, 22);
		contentPaneRegister.add(labelEdad);
		
		fieldCorreo = new JTextField();
		fieldCorreo.setColumns(10);
		fieldCorreo.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldCorreo.setBounds(508, 227, 402, 50);
		contentPaneRegister.add(fieldCorreo);
		
		JLabel lblConfirmacinOntrasea_1 = new JLabel("Correo: ");
		lblConfirmacinOntrasea_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblConfirmacinOntrasea_1.setBounds(508, 302, 188, 27);
		contentPaneRegister.add(lblConfirmacinOntrasea_1);
		
		fieldConfirmaContra = new JTextField();
		fieldConfirmaContra.setColumns(10);
		fieldConfirmaContra.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldConfirmaContra.setBounds(508, 339, 402, 50);
		contentPaneRegister.add(fieldConfirmaContra);
		
		JButton btnRegistro = new JButton("Iniciar Sesion");
		btnRegistro.setForeground(Color.WHITE);
		btnRegistro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRegistro.setBackground(new Color(255, 128, 0));
		btnRegistro.setBounds(1110, 486, 180, 37);
		contentPaneRegister.add(btnRegistro);
		
		JLabel labelInstagram = new JLabel("");
		labelInstagram.setBounds(87, 720, 50, 50);
		contentPaneRegister.add(labelInstagram);
		cargarImagenEnLabel(labelInstagram, "img_login/logotipo-de-instagram.png", 50, 50);

		
		JLabel labelFacebook = new JLabel("");
		labelFacebook.setBounds(176, 720, 50, 50);
		contentPaneRegister.add(labelFacebook);
		cargarImagenEnLabel(labelFacebook, "img_login/facebook.png", 50, 50);

		
		JLabel labelGoogle = new JLabel("");
		labelGoogle.setBounds(270, 720, 50, 50);
		contentPaneRegister.add(labelGoogle);
		cargarImagenEnLabel(labelGoogle, "img_login/cromo.png", 50, 50);

		
		JLabel labelGithub = new JLabel("");
		labelGithub.setBounds(349, 720, 50, 50);
		contentPaneRegister.add(labelGithub);
		cargarImagenEnLabel(labelGithub, "img_login/github.png", 50, 50);
		
		JLabel labelTienesCuenta = new JLabel("¿Ya tienes una cuenta?");
		labelTienesCuenta.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTienesCuenta.setBounds(1082, 51, 208, 22);
		contentPaneRegister.add(labelTienesCuenta);
		
		JButton btnIrInicio = new JButton("Registrate Aquí");
		btnIrInicio.addActionListener(e ->{
			conectarFrames.AbrirFrameLogin(this);
		});
		btnIrInicio.setForeground(Color.WHITE);
		btnIrInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIrInicio.setBackground(new Color(255, 128, 0));
		btnIrInicio.setBounds(1252, 37, 136, 37);
		contentPaneRegister.add(btnIrInicio);
		
		JLabel labelIdentificacion = new JLabel("Tipo Identificación:");
		labelIdentificacion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelIdentificacion.setBounds(45, 416, 188, 27);
		contentPaneRegister.add(labelIdentificacion);
		
		fieldTipoId = new JTextField();
		fieldTipoId.setColumns(10);
		fieldTipoId.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldTipoId.setBounds(45, 453, 402, 50);
		contentPaneRegister.add(fieldTipoId);
		
		JLabel labelNumId = new JLabel("Número Identificación: ");
		labelNumId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelNumId.setBounds(45, 534, 188, 27);
		contentPaneRegister.add(labelNumId);
		
		fieldNumId = new JTextField();
		fieldNumId.setColumns(10);
		fieldNumId.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldNumId.setBounds(45, 571, 402, 50);
		contentPaneRegister.add(fieldNumId);
		
		JLabel lblApellido_1_1 = new JLabel("Contraseña:");
		lblApellido_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblApellido_1_1.setBounds(508, 427, 151, 22);
		contentPaneRegister.add(lblApellido_1_1);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		textField_6.setBounds(508, 459, 402, 50);
		contentPaneRegister.add(textField_6);
		
		JLabel lblConfirmacinOntrasea_1_1 = new JLabel("Confirmación contraseña: ");
		lblConfirmacinOntrasea_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblConfirmacinOntrasea_1_1.setBounds(508, 534, 188, 27);
		contentPaneRegister.add(lblConfirmacinOntrasea_1_1);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		textField_7.setBounds(508, 571, 402, 50);
		contentPaneRegister.add(textField_7);
		
		JLabel lblApellido_1_2 = new JLabel("Rol: ");
		lblApellido_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblApellido_1_2.setBounds(986, 195, 151, 22);
		contentPaneRegister.add(lblApellido_1_2);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		textField_8.setBounds(986, 227, 402, 50);
		contentPaneRegister.add(textField_8);
		
		JLabel lblConfirmacinOntrasea_1_2 = new JLabel("Estado: ");
		lblConfirmacinOntrasea_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblConfirmacinOntrasea_1_2.setBounds(986, 302, 188, 27);
		contentPaneRegister.add(lblConfirmacinOntrasea_1_2);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		textField_9.setBounds(986, 339, 402, 50);
		contentPaneRegister.add(textField_9);
	}
	
	public void cargarImagenEnLabel(JLabel label, String rutaInterna, int ancho, int alto) {
	    ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaInterna));
	    Image imagenOriginal = iconoOriginal.getImage();
	    Image imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
	    ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
	    label.setIcon(iconoEscalado);
	}
}
