package login;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
 * Importación de librerias
 */
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import classBorder.BordeRedondeado;
import conectionFrames.conectarFrames;
import funciones.funciones;

import java.awt.Color;
import java.awt.Font;


//--- Nuevas importaciones necesarias ---
import DAO.UsuariosDAO;
import modelos.Usuario;
import util.ConnectionADMIN;
import menuInicialAdministrador.contenedorInicio; 
import menuInicialEmpleado.contenedorInicioEmp;



public class intefaceLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fieldNombre;
	private JPasswordField fieldContrasena;
	private Color naranjaLogo = new Color(255, 102, 0);


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
		setBounds(100, 100, 1035, 856);
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
		labelTitulo.setBounds(313, 69, 419, 82);
		contentPane.add(labelTitulo);
		
		JLabel labelNombre = new JLabel("Nombre Usuario: ");
		labelNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelNombre.setBounds(330, 259, 151, 22);
		contentPane.add(labelNombre);
		
		fieldNombre = new JTextField();
		fieldNombre.setBounds(330, 291, 402, 50);
		fieldNombre.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		contentPane.add(fieldNombre);
		fieldNombre.setColumns(10);
		funciones.addPlaceholder(fieldNombre, "Ingrese Nombre...");	
		fieldNombre.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));

		
		
		JLabel labelContrasena = new JLabel("Contraseña: ");
		labelContrasena.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelContrasena.setBounds(330, 366, 188, 27);
		contentPane.add(labelContrasena);
		
		// Define el placeholder
		String placeholder = "Ingrese Contraseña...";
		char defaultEchoChar = '\u2022'; 
		
		fieldContrasena = new JPasswordField ();
		fieldContrasena.setBounds(330, 403, 402, 50);
		fieldContrasena.setBorder(new BordeRedondeado(10, 10, Color.GRAY));
		contentPane.add(fieldContrasena);
		fieldContrasena.setColumns(10);
	
		funciones.addPlaceholder(fieldContrasena, placeholder);	
		fieldContrasena.setBorder(new MatteBorder(0, 0, 2, 0, new Color(255, 102, 0)));
		fieldContrasena.setEchoChar((char) 0); // Mostrar texto legible

		
		JCheckBox checkBoxMostrar = new JCheckBox("Mostrar Contraseña");
		checkBoxMostrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		checkBoxMostrar.setBackground(new Color(255, 255, 255));
		checkBoxMostrar.setBounds(329, 462, 191, 23);
		contentPane.add(checkBoxMostrar);
		funciones.mostrarOcultraContrasena(checkBoxMostrar, fieldContrasena,  placeholder, defaultEchoChar);
        
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setBounds(433, 120, 200, 100);
		contentPane.add(labelLogo);
		funciones.cargarImagenEnLabel(labelLogo, "/img_login/loga_empresa.jpg", 200, 100);
		
		
		JButton btnInicioSesion = new JButton("Iniciar Sesion");
		btnInicioSesion.addActionListener(e ->{
			inicioSesion();
		
		});
		btnInicioSesion.setForeground(new Color(255, 255, 255));
		btnInicioSesion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInicioSesion.setBackground(new Color(255, 128, 0));
		btnInicioSesion.setBounds(439, 579, 180, 37);
		contentPane.add(btnInicioSesion);
		
		JLabel labelOlvidarContrasena = new JLabel("¿Olvidaste tu contraseña? Click Aquí");
		labelOlvidarContrasena.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelOlvidarContrasena.setHorizontalAlignment(SwingConstants.LEFT);
		labelOlvidarContrasena.setBounds(330, 492, 343, 22);
		contentPane.add(labelOlvidarContrasena);
		
		JLabel labelTienesCuenta = new JLabel("¿Aún tienes una cuenta?");
		labelTienesCuenta.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTienesCuenta.setBounds(704, 43, 208, 22);
		contentPane.add(labelTienesCuenta);
		
		JButton btnIrRegistro = new JButton("Registrate Aquí");
		btnIrRegistro.addActionListener(e ->{
			conectarFrames.AbrirFrameSingin(this);
		});
		
		btnIrRegistro.setForeground(new Color(255, 255, 255));
		btnIrRegistro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIrRegistro.setBackground(new Color(255, 128, 0));
		btnIrRegistro.setBounds(874, 29, 136, 37);
		contentPane.add(btnIrRegistro);
		
		JLabel labelInstagram = new JLabel("");
		labelInstagram.setBounds(398, 671, 30, 30);
		contentPane.add(labelInstagram);
		funciones.irNavegador(labelInstagram, "https://www.instagram.com/tata__accesorios/");
		funciones.cargarImagenEnLabel(labelInstagram, "/img_login/insta.png", 30, 30);

		
		JLabel labelFacebook = new JLabel("");
		labelFacebook.setBounds(470, 671, 30, 30);
		contentPane.add(labelFacebook);
		funciones.irNavegador(labelFacebook, "https://www.facebook.com/facebook/");
		funciones.cargarImagenEnLabel(labelFacebook, "/img_login/facebook.png", 30, 30);

		
		JLabel labelGoogle = new JLabel("");
		labelGoogle.setBounds(553, 671, 30, 30);
		contentPane.add(labelGoogle);
		funciones.irNavegador(labelGoogle, "https://mail.google.com");
		funciones.cargarImagenEnLabel(labelGoogle, "/img_login/chorme.png", 30, 30);

		
		JLabel labelGithub = new JLabel("");
		labelGithub.setBounds(623, 671, 30, 30);
		contentPane.add(labelGithub);
		funciones.irNavegador(labelGithub, "https://github.com/Valerieee01");
		funciones.cargarImagenEnLabel(labelGithub, "/img_login/git.png", 30, 30);
		

	}
	
	
	public void inicioSesion() {
	    String correo = fieldNombre.getText(); // El campoNombre es para el correo en tu DAO
	    String contrasena = new String(fieldContrasena.getPassword());

	    // Validación básica de campos
	    if (correo.isEmpty() || contrasena.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Por favor, ingrese su correo y contraseña.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    Usuario usuarioLogueado = null;
	    try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
	        usuarioLogueado = UsuariosDAO.obtenerUsuarioPorCorreo(correo, conn);

	        if (usuarioLogueado != null) {
	           
	            if (contrasena.equals(usuarioLogueado.getContrasena())) { 
	                JOptionPane.showMessageDialog(this, "¡Inicio de sesión exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);

	                // Lógica para mantener al usuario logueado y redirigir
	                // Ocultar la ventana de login
	                this.setVisible(false);
	                
	                // Redirigir según el rol
	                switch (usuarioLogueado.getIdRol()) {
	                    case 2: // Asumimos que 1 es el rol de Administrador
	                    	contenedorInicio menuAdmin = new contenedorInicio(usuarioLogueado);
	                        menuAdmin.setVisible(true);
	                        break;
	                    case 3: 
	                    	contenedorInicioEmp menuEmpleado = new contenedorInicioEmp(usuarioLogueado);
	                    	menuEmpleado.setVisible(true);
	                        break;
	                    default:
	                        JOptionPane.showMessageDialog(this, "Rol de usuario no reconocido. Contacte al administrador.", "Error de Rol", JOptionPane.ERROR_MESSAGE);
	                        // Volver a mostrar la ventana de login o cerrar
	                        this.setVisible(true);
	                        break;
	                }
	            } else {
	                JOptionPane.showMessageDialog(this, "Contraseña incorrecta.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, "Error de base de datos durante el inicio de sesión: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}
	

}
