package login;

/*
 *Importación clases 
 */
import conectionFrames.conectarFrames;
import funcionesLogin.funciones;

/*
 * Importación de librerias
 */
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

/*Inicio lineas de codigo*/
public class intefaceSingin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPaneRegister;
	private JTextField fieldNombre;
	private JTextField fieldApellidos;
	private JTextField fieldEdad;
	private JTextField fieldCorreo;
	private JTextField fieldTipoId;
	private JTextField fieldNumId;
	private JPasswordField fieldContrasena;
	private JPasswordField fieldConfirmContra;
	private JTextField rol;

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
		setBounds(100, 100, 1035, 856);
		setLocationRelativeTo(null);

		contentPaneRegister = new JPanel();
		contentPaneRegister.setBackground(new Color(255, 255, 255));
		contentPaneRegister.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPaneRegister);
		contentPaneRegister.setLayout(null);
		
		JLabel labelTienesCuenta = new JLabel("¿Ya tienes una cuenta?");
		labelTienesCuenta.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTienesCuenta.setBounds(703, 24, 208, 22);
		contentPaneRegister.add(labelTienesCuenta);
		
		JLabel labelTitulo = new JLabel("SmartCount");
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setForeground(new Color(255, 102, 0));
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 32));
		labelTitulo.setBounds(302, 69, 419, 82);
		contentPaneRegister.add(labelTitulo);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setBackground(new Color(255, 255, 255));
		labelLogo.setBounds(568, 51, 200, 100);
		funciones.cargarImagenEnLabel(labelLogo, "/img_login/loga_empresa.jpg", 200, 150);
		contentPaneRegister.add(labelLogo);
		
		JLabel labelNombre = new JLabel("Nombres:");
		labelNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelNombre.setBounds(242, 180, 151, 22);
		contentPaneRegister.add(labelNombre);
		
		fieldNombre = new JTextField();
		fieldNombre.setColumns(10);
		fieldNombre.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldNombre.setBounds(242, 199, 239, 37);
		contentPaneRegister.add(fieldNombre);
		fieldNombre.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		funciones.addPlaceholder(fieldNombre, "Escribe tu nombre..." );
		
		JLabel labelApellidos = new JLabel("Apellidos: ");
		labelApellidos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelApellidos.setBounds(242, 247, 188, 27);
		contentPaneRegister.add(labelApellidos);
		
		fieldApellidos = new JTextField();
		fieldApellidos.setColumns(10);
		fieldApellidos.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldApellidos.setBounds(242, 273, 239, 37);
		contentPaneRegister.add(fieldApellidos);
		fieldApellidos.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		funciones.addPlaceholder(fieldApellidos, "Escribe tu Apellido..." );

		
		JLabel labelEdad = new JLabel("Edad: ");
		labelEdad.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelEdad.setBounds(242, 510, 151, 22);
		contentPaneRegister.add(labelEdad);
		
		fieldEdad = new JTextField();
		fieldEdad.setColumns(10);
		fieldEdad.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldEdad.setBounds(242, 534, 239, 37);
		contentPaneRegister.add(fieldEdad);
		fieldEdad.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		funciones.addPlaceholder(fieldEdad, "Escribe tu Edad..." );

		
		JLabel labelCorreo = new JLabel("Correo: ");
		labelCorreo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelCorreo.setBounds(546, 178, 188, 27);
		contentPaneRegister.add(labelCorreo);
		
		fieldCorreo = new JTextField();
		fieldCorreo.setColumns(10);
		fieldCorreo.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldCorreo.setBounds(546, 199, 239, 37);
		contentPaneRegister.add(fieldCorreo);
		fieldCorreo.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		funciones.addPlaceholder(fieldCorreo, "Escribe tu correo..." );

		
		JButton btnRegistro = new JButton("Registrase");
		btnRegistro.setForeground(Color.WHITE);
		btnRegistro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRegistro.setBackground(new Color(255, 128, 0));
		btnRegistro.setBounds(425, 624, 180, 37);
		contentPaneRegister.add(btnRegistro);
		
		JButton btnIrInicio = new JButton("Ir Inicio Sesion");
		btnIrInicio.addActionListener(e ->{
			conectarFrames.AbrirFrameLogin(this);
		});
		btnIrInicio.setForeground(Color.WHITE);
		btnIrInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIrInicio.setBackground(new Color(255, 128, 0));
		btnIrInicio.setBounds(873, 10, 136, 37);
		contentPaneRegister.add(btnIrInicio);
		
		JLabel labelIdentificacion = new JLabel("Tipo Identificación:");
		labelIdentificacion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelIdentificacion.setBounds(242, 321, 188, 27);
		contentPaneRegister.add(labelIdentificacion);
		
		// Crear arreglo con tipos de identificación
		String[] tiposIdentificacion = {
		    "Seleccione tipo ID...",
		    "Cédula de Ciudadanía",
		    "Tarjeta de Identidad",
		    "Cédula de Extranjería",
		    "Pasaporte",
		    "NIT",
		    "Registro Civil",
		    "Permiso Especial de Permanencia (PEP)",
		    "Documento Nacional de Identidad (DNI)"
		};

		JComboBox<String> comboIdentificacion = new JComboBox<>(tiposIdentificacion);
		comboIdentificacion.setBackground(new Color(255, 255, 255));
		comboIdentificacion.setBounds(242, 368, 239, 37); // Ajusta según tu layout
		comboIdentificacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboIdentificacion.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		contentPaneRegister.add(comboIdentificacion);
		

		JLabel labelNumId = new JLabel("Número Identificación: ");
		labelNumId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelNumId.setBounds(242, 438, 188, 27);
		contentPaneRegister.add(labelNumId);
		
		fieldNumId = new JTextField();
		fieldNumId.setColumns(10);
		fieldNumId.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldNumId.setBounds(242, 462, 239, 37);
		contentPaneRegister.add(fieldNumId);
		fieldNumId.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		funciones.addPlaceholder(fieldNumId, "Escribe tu número ID..." );

		
		JLabel labelContrasena = new JLabel("Contraseña:");
		labelContrasena.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelContrasena.setBounds(546, 249, 151, 22);
		contentPaneRegister.add(labelContrasena);
		
		// Define el placeholder
		String placeholder = "Ingrese Contraseña...";
		char defaultEchoChar = '\u2022'; 
		
		// field contrasena 
		fieldContrasena = new JPasswordField ();
		fieldContrasena.setBounds(546, 273, 239, 37);
		fieldContrasena.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		contentPaneRegister.add(fieldContrasena);
		fieldContrasena.setColumns(10);
	
		funciones.addPlaceholder(fieldContrasena, placeholder);	
		fieldContrasena.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		fieldContrasena.setEchoChar((char) 0); // Mostrar texto legible

		JCheckBox checkBoxMostrar = new JCheckBox("Mostrar Contraseña");
		checkBoxMostrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		checkBoxMostrar.setBackground(new Color(255, 255, 255));
		checkBoxMostrar.setBounds(543, 318, 191, 23);
		contentPaneRegister.add(checkBoxMostrar);
		funciones.mostrarOcultraContrasena(checkBoxMostrar, fieldContrasena,  placeholder, defaultEchoChar);

		// field confirmacion contrasena 
		JLabel lblConfirmacinOntrasea_1_1 = new JLabel("Confirmación contraseña: ");
		lblConfirmacinOntrasea_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblConfirmacinOntrasea_1_1.setBounds(546, 342, 188, 27);
		contentPaneRegister.add(lblConfirmacinOntrasea_1_1);
		
		fieldConfirmContra = new JPasswordField();
		fieldConfirmContra.setColumns(10);
		fieldConfirmContra.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		fieldConfirmContra.setBounds(546, 370, 239, 37);
		contentPaneRegister.add(fieldConfirmContra);
		
		funciones.addPlaceholder(fieldConfirmContra, placeholder );
		fieldConfirmContra.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		fieldConfirmContra.setEchoChar((char) 0); // Mostrar texto legible
		
		JCheckBox checkBoxMostrarConfirm = new JCheckBox("Mostrar Contraseña");
		checkBoxMostrarConfirm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		checkBoxMostrarConfirm.setBackground(new Color(255, 255, 255));
		checkBoxMostrarConfirm.setBounds(546, 412, 191, 23);
		contentPaneRegister.add(checkBoxMostrarConfirm);
		checkBoxMostrarConfirm.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		funciones.mostrarOcultraContrasena(checkBoxMostrarConfirm, fieldConfirmContra,  placeholder, defaultEchoChar);
		
		JLabel labelEstado = new JLabel("Estado: ");
		labelEstado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelEstado.setBounds(546, 508, 188, 27);
		contentPaneRegister.add(labelEstado);
		
		JLabel labelRol = new JLabel("Rol: ");
		labelRol.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelRol.setBounds(546, 432, 151, 22);
		contentPaneRegister.add(labelRol);
		
		// Crear arreglo con tipos de identificación
		String[] roles = {
		    "Seleccione un rol...",
		    "Administrador",
		    "Cliente",
		    "Proveedor"
		};

		JComboBox<String> comboRoles = new JComboBox<>(roles);
		comboRoles.setBackground(new Color(255, 255, 255));
		comboRoles.setBounds(546, 462, 239, 37); // Ajusta según tu layout
		comboRoles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboRoles.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		comboRoles.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		contentPaneRegister.add(comboRoles);
		
		JCheckBox checkEstado = new JCheckBox("Usuario Activo");
		checkEstado.setBackground(new Color(255, 255, 255));
		checkEstado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkEstado.setBounds(543, 534, 228, 43);
		contentPaneRegister.add(checkEstado);
		
		JLabel labelInstagram = new JLabel("");
		labelInstagram.setBounds(372, 694, 30, 30);
		funciones.irNavegador(labelInstagram, "https://www.instagram.com/tata__accesorios/");
		funciones.cargarImagenEnLabel(labelInstagram,  "/img_login/insta.png", 30, 30);
		contentPaneRegister.add(labelInstagram);
		
		JLabel labelFacebook = new JLabel("");
		labelFacebook.setBounds(461, 694, 30, 30);
		funciones.irNavegador(labelFacebook, "https://www.facebook.com/facebook/");
		funciones.cargarImagenEnLabel(labelFacebook, "/img_login/facebook.png", 30, 30);
		contentPaneRegister.add(labelFacebook);
		
		JLabel labelGoogle = new JLabel("");
		labelGoogle.setBounds(555, 694, 30, 30);
		funciones.irNavegador(labelGoogle, "https://mail.google.com");
		funciones.cargarImagenEnLabel(labelGoogle,"/img_login/chorme.png", 30, 30);
		contentPaneRegister.add(labelGoogle);
		
		JLabel labelGithub = new JLabel("");
		labelGithub.setBounds(634, 694, 30, 30);
		funciones.irNavegador(labelGithub, "https://github.com/Valerieee01");
		funciones.cargarImagenEnLabel(labelGithub, "/img_login/git.png", 30, 30);
		contentPaneRegister.add(labelGithub);
		
		
		
		

	}
	
	
}
