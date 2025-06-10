
package funciones;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class funciones {
	
	public static void mostrarOcultraContrasena(JCheckBox checkBoxMostrar, JPasswordField fieldContrasena, String placeholder, char defaultEchoChar ) {
		 // Evento para mostrar/ocultar la contraseña
		checkBoxMostrar.addActionListener(e -> {
			 if (checkBoxMostrar.isSelected()) {
			        if (!String.valueOf(fieldContrasena.getPassword()).equals(placeholder)) {
			            fieldContrasena.setEchoChar((char) 0); // Mostrar solo si no es placeholder
			        }
			    } else {
			        if (!String.valueOf(fieldContrasena.getPassword()).equals(placeholder)) {
			            fieldContrasena.setEchoChar(defaultEchoChar); // Ocultar si no es placeholder
			        }
			    }
	    });
		
	}
	
	public static void addPlaceholder(JTextField textField, String placeholderText) {
        // Color del placeholder
        Color placeholderColor = Color.GRAY;
        // Guardamos el color original del texto
        Color originalColor = textField.getForeground();
        textField.setText(placeholderText);
        textField.setForeground(placeholderColor);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setText("");
                    textField.setForeground(originalColor);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholderText);
                    textField.setForeground(placeholderColor);
                }
            }
        });
	}
		
	public static void cargarImagenEnLabel(JLabel label, String rutaInterna, int ancho, int alto) {
	    URL url = funciones.class.getResource(rutaInterna);
	    System.out.println("Cargando: " + rutaInterna + " → " + url);

	    if (url == null) {
	        System.err.println("ERROR: Imagen no encontrada en ruta: " + rutaInterna);
	        return;
	    }     

	    ImageIcon iconoOriginal = new ImageIcon(url);
	    Image imagenOriginal = iconoOriginal.getImage();
	    Image imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
	    ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
	    label.setIcon(iconoEscalado);
	}

	public static void irNavegador(JLabel lblLink, String url) {

		lblLink.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        try {
		            Desktop.getDesktop().browse(new URI(url));
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});

	}
	
	public static void cambiarPanel(JPanel contenedor, String nombrePanel) {
	    CardLayout cl = (CardLayout) contenedor.getLayout();
	    cl.show(contenedor, nombrePanel);
	}

}
