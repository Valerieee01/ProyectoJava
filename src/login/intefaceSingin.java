package login;

/*
 *Importación clases
 */
import conectionFrames.conectarFrames;
import funciones.funciones;

import DAO.UsuariosDAO;
import DAO.TipoIdentificacionDAO; 
import DAO.RolDAO;               

import modelos.Usuario;
import modelos.Usuario.Estado;
import modelos.TipoIdentificacion; 
import modelos.Rol;               

import util.ConnectionADMIN;

/*
 * Importación de librerias
 */
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import classBorder.BordeRedondeado;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List; 

/*Inicio lineas de codigo*/
public class intefaceSingin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPaneRegister;
	private JTextField fieldNombre;
	private JTextField fieldApellidos;
	private JTextField fieldEdad;
	private JTextField fieldCorreo;
	private JTextField fieldNumId;
	private JPasswordField fieldContrasena;
	private JPasswordField fieldConfirmContra;

    private JComboBox<TipoIdentificacion> comboIdentificacion; 
    private JComboBox<Rol> comboRoles;                         
    private JCheckBox checkEstado;

    private UsuariosDAO usuarioDAO;
    private TipoIdentificacionDAO tipoIdentificacionDAO;
    private RolDAO rolDAO;                             

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
		btnRegistro.addActionListener(e -> registroUsuario()); // Asignar ActionListener
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
		
		// JComboBox para Tipo de Identificación
		comboIdentificacion = new JComboBox<>(); // ¡Sin elementos iniciales!
		comboIdentificacion.setBackground(new Color(255, 255, 255));
		comboIdentificacion.setBounds(242, 368, 239, 37);
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
		
		// JComboBox para Roles
		comboRoles = new JComboBox<>(); // ¡Sin elementos iniciales!
		comboRoles.setBackground(new Color(255, 255, 255));
		comboRoles.setBounds(546, 462, 239, 37);
		comboRoles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboRoles.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		contentPaneRegister.add(comboRoles);
		
		checkEstado = new JCheckBox("Usuario Activo"); // Asignar al atributo de clase
		checkEstado.setBackground(new Color(255, 255, 255));
		checkEstado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkEstado.setBounds(543, 534, 228, 43);
		checkEstado.setSelected(true); // Por defecto activo
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

        // Inicializar DAOs y poblar ComboBoxes
        usuarioDAO = new UsuariosDAO();
        tipoIdentificacionDAO = new TipoIdentificacionDAO();
        rolDAO = new RolDAO();
        
        cargarComboBoxes(); // Llama al nuevo método para poblar los ComboBoxes
	}
	
    // --- Nuevo método para cargar los ComboBoxes ---
    private void cargarComboBoxes() {
        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            // Cargar Tipos de Identificación
            List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionDAO.obtenerTodosTiposIdentificacion(conn);
            comboIdentificacion.removeAllItems(); // Limpiar items existentes
            comboIdentificacion.addItem(null); // Añadir opción por defecto "Seleccione tipo ID..."
            for (TipoIdentificacion tipo : tiposIdentificacion) {
                comboIdentificacion.addItem(tipo); // toString() de TipoIdentificacion mostrará el nombre
            }

            // Cargar Roles
            List<Rol> roles = rolDAO.obtenerTodosRoles(conn);
            comboRoles.removeAllItems(); // Limpiar items existentes
            comboRoles.addItem(null); // Añadir opción por defecto "Seleccione un rol..."
            for (Rol rol : roles) {
                comboRoles.addItem(rol); // toString() de Rol mostrará el nombre
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos de tipo de identificación o roles: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


	public void registroUsuario() {
	    String nombres = fieldNombre.getText().trim();
	    String apellidos = fieldApellidos.getText().trim();
	    
	    // Obtener el objeto TipoIdentificacion seleccionado directamente
	    TipoIdentificacion tipoIdentificacionSeleccionado = (TipoIdentificacion) comboIdentificacion.getSelectedItem();
	    
	    String numeroIdentificacion = fieldNumId.getText().trim();
	    String edadStr = fieldEdad.getText().trim();
	    String correo = fieldCorreo.getText().trim();
	    String contrasena = new String(fieldContrasena.getPassword()).trim();
	    String confirmContrasena = new String(fieldConfirmContra.getPassword()).trim();
	    
	    // Obtener el objeto Rol seleccionado directamente
	    Rol rolSeleccionado = (Rol) comboRoles.getSelectedItem();
	    
	    Estado estado = checkEstado.isSelected() ? Estado.activo : Estado.inactivo;

	    // Validaciones
	    if (nombres.isEmpty() || apellidos.isEmpty() || numeroIdentificacion.isEmpty() ||
	        edadStr.isEmpty() || correo.isEmpty() || contrasena.isEmpty() ||
	        confirmContrasena.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    // Validar que se haya seleccionado un ítem real de los JComboBox
	    if (tipoIdentificacionSeleccionado == null) {
	        JOptionPane.showMessageDialog(this, "Por favor, seleccione un tipo de identificación.", "Validación", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    if (rolSeleccionado == null) {
	        JOptionPane.showMessageDialog(this, "Por favor, seleccione un rol.", "Validación", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
	        JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.", "Formato Inválido", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    if (!contrasena.equals(confirmContrasena)) {
	        JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error de Contraseña", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    int edad;
	    try {
	        edad = Integer.parseInt(edadStr);
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "La edad debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

        // Obtener los IDs de los objetos seleccionados
        int idTipoIdentificacion = tipoIdentificacionSeleccionado.getIdTipoIdentificacion();
        int idRol = rolSeleccionado.getIdRol();

        // ¡ATENCIÓN: HASH DE CONTRASEÑA!
        // String contrasenaHasheada = BCrypt.hashpw(contrasena, BCrypt.gensalt());
        String contrasenaAEnviar = contrasena;

	    Usuario nuevoUsuario = new Usuario(nombres, apellidos, idTipoIdentificacion,
	                                       numeroIdentificacion, edad, correo, contrasenaAEnviar, idRol);
	    nuevoUsuario.setEstado(estado);

	    try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
	        int idGenerado = usuarioDAO.agregarUsuario(nuevoUsuario, conn);

	        if (idGenerado != -1) {
	            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente. ID: " + idGenerado, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
	            limpiarCampos();
	            conectarFrames.AbrirFrameLogin(this);
	        } else {
	            JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
	        }
	    } catch (SQLException ex) {
            if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("for key 'correo'")) {
                JOptionPane.showMessageDialog(this, "El correo electrónico ya está registrado.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
            } else if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("for key 'numero_identificacion'")) {
                JOptionPane.showMessageDialog(this, "El número de identificación ya está registrado.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
            } else if (ex.getMessage().contains("FOREIGN KEY (`id_tipo_identificacion`)") || ex.getMessage().contains("FOREIGN KEY (`id_rol`)")) {
                JOptionPane.showMessageDialog(this, "Error: El tipo de identificación o el rol seleccionado no son válidos en la base de datos.", "Error de Clave Foránea", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el usuario en la base de datos: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
	    }
	}

    private void limpiarCampos() {
        fieldNombre.setText("");
        fieldApellidos.setText("");
        fieldEdad.setText("");
        fieldCorreo.setText("");
        comboIdentificacion.setSelectedIndex(0);
        fieldNumId.setText("");
        fieldContrasena.setText("");
        fieldConfirmContra.setText("");
        comboRoles.setSelectedIndex(0);
        checkEstado.setSelected(true);
    }
}
