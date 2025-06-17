package menuInicialAdministrador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import DAO.UsuariosDAO;
import DAO.TipoIdentificacionDAO; // Necesario para el FormularioUsuario
import DAO.RolDAO;               // Necesario para el FormularioUsuario
import modelos.Usuario;
import modelos.Rol; // Para obtener el nombre del rol
import util.ConnectionADMIN;
import login.intefaceLogin;
import formularios.FormularioUsuario; // ¡Importante!

public class paneCuentaAdmin extends JPanel {

    private static final long serialVersionUID = 1L;

    private Usuario usuarioLogueado;
    private UsuariosDAO usuarioDAO;
    private TipoIdentificacionDAO tipoIdentificacionDAO; // Inicializar para pasar al formulario
    private RolDAO rolDAO;                               // Inicializar para pasar al formulario

    // Componentes de la UI para mostrar información
    private JLabel lblNombres;
    private JLabel lblApellidos;
    private JLabel lblCorreo;
    private JLabel lblNumeroIdentificacion;
    private JLabel lblEdad;
    private JLabel lblRol;
    private JLabel lblEstado;

    /**
     * Constructor del panel. Recibe el usuario que ha iniciado sesión.
     * @param usuario El objeto Usuario del empleado logueado.
     */
    public paneCuentaAdmin(Usuario usuario) {
        this.usuarioLogueado = usuario;
        this.usuarioDAO = new UsuariosDAO();
        this.tipoIdentificacionDAO = new TipoIdentificacionDAO(); // Inicializar
        this.rolDAO = new RolDAO();                               // Inicializar

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255));

        // --- Título del Panel ---
        JLabel tituloPanel = new JLabel("Mi Cuenta de Empleado");
        tituloPanel.setFont(new Font("Arial", Font.BOLD, 26));
        tituloPanel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloPanel.setForeground(new Color(60, 60, 60));
        add(tituloPanel, BorderLayout.NORTH);

        // --- Panel Central de Información del Usuario ---
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setBorder(new TitledBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
            "Datos del Usuario",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 16),
            new Color(90, 90, 90)
        ));
        panelInfo.setPreferredSize(new Dimension(500, 350));
        panelInfo.setMaximumSize(new Dimension(600, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 14);

        lblNombres = addInfoRow(panelInfo, gbc, "Nombres:", labelFont, valueFont);
        lblApellidos = addInfoRow(panelInfo, gbc, "Apellidos:", labelFont, valueFont);
        lblCorreo = addInfoRow(panelInfo, gbc, "Correo:", labelFont, valueFont);
        lblNumeroIdentificacion = addInfoRow(panelInfo, gbc, "Identificación:", labelFont, valueFont);
        lblEdad = addInfoRow(panelInfo, gbc, "Edad:", labelFont, valueFont);
        lblRol = addInfoRow(panelInfo, gbc, "Rol:", labelFont, valueFont);
        lblEstado = addInfoRow(panelInfo, gbc, "Estado:", labelFont, valueFont);
        
        // Cargar los datos iniciales del usuario
        cargarDatosUsuario(usuario); // Pasa el usuario inicial

        JPanel wrapperPanelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        wrapperPanelInfo.setBackground(getBackground());
        wrapperPanelInfo.add(panelInfo);
        add(wrapperPanelInfo, BorderLayout.CENTER);


        // --- Panel de Botones de Acción ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setBackground(new Color(240, 248, 255));

        // Nuevo botón Modificar
        JButton btnModificarDatos = new JButton("Modificar Mis Datos");
        btnModificarDatos.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnModificarDatos.setBackground(new Color(64, 150, 220)); // Azul
        btnModificarDatos.setForeground(Color.WHITE);
        btnModificarDatos.setPreferredSize(new Dimension(200, 45));
        btnModificarDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarDatosUsuario();
            }
        });
        panelBotones.add(btnModificarDatos);

        JButton btnEliminarCuenta = new JButton("Eliminar Mi Cuenta");
        btnEliminarCuenta.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnEliminarCuenta.setBackground(new Color(220, 50, 50));
        btnEliminarCuenta.setForeground(Color.WHITE);
        btnEliminarCuenta.setPreferredSize(new Dimension(200, 45));
        btnEliminarCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCuenta();
            }
        });
        panelBotones.add(btnEliminarCuenta);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCerrarSesion.setBackground(new Color(255, 128, 64));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setPreferredSize(new Dimension(200, 45));
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
        panelBotones.add(btnCerrarSesion);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Método auxiliar para añadir una fila de etiqueta y valor a un panel usando GridBagLayout.
     */
    private JLabel addInfoRow(JPanel panel, GridBagConstraints gbc, String labelText, Font labelFont, Font valueFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        panel.add(label, gbc);

        JLabel valueLabel = new JLabel();
        valueLabel.setFont(valueFont);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(valueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.3;
        return valueLabel;
    }

    /**
     * Carga los datos de un usuario en las etiquetas del panel.
     * Esto se llama al inicializar el panel y después de una modificación exitosa.
     * @param usuario El objeto Usuario con los datos a mostrar.
     */
    public void cargarDatosUsuario(Usuario usuario) { // Ahora público y acepta un Usuario
        this.usuarioLogueado = usuario; // Actualiza el atributo de la clase con los datos más recientes
        if (usuarioLogueado != null) {
            lblNombres.setText(usuarioLogueado.getNombres());
            lblApellidos.setText(usuarioLogueado.getApellidos());
            lblCorreo.setText(usuarioLogueado.getCorreo());
            lblNumeroIdentificacion.setText(usuarioLogueado.getNumeroIdentificacion());
            lblEdad.setText(String.valueOf(usuarioLogueado.getEdad()));
            
            // Mejorar la obtención del nombre del Rol
            String nombreRol = "Cargando...";
            try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
                // Aquí necesitarías un método en RolDAO como obtenerRolPorId(int idRol, Connection conn)
                // y luego obtener rol.getNombreRol()
                // Por ahora, solo muestra el ID o implementa RolDAO.obtenerRolPorId si lo tienes.
                Rol rol = rolDAO.obtenerRolPorId(usuarioLogueado.getIdRol(), conn);
                nombreRol = (rol != null) ? rol.getNombreRol() : "ID Rol: " + usuarioLogueado.getIdRol();
            } catch (SQLException e) {
                nombreRol = "Error Rol (" + usuarioLogueado.getIdRol() + ")";
                System.err.println("Error al obtener el nombre del rol: " + e.getMessage());
            }
            lblRol.setText(nombreRol);

            lblEstado.setText(usuarioLogueado.getEstado().name());
        } else {
            lblNombres.setText("N/A");
            lblApellidos.setText("N/A");
            lblCorreo.setText("N/A");
            lblNumeroIdentificacion.setText("N/A");
            lblEdad.setText("N/A");
            lblRol.setText("N/A");
            lblEstado.setText("N/A");
            JOptionPane.showMessageDialog(this, "No se pudo cargar la información del usuario.", "Error de Usuario", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Maneja la lógica para abrir el formulario de modificación de datos del usuario.
     */
    private void modificarDatosUsuario() {
        if (usuarioLogueado == null) {
            JOptionPane.showMessageDialog(this, "No hay un usuario logueado para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Abre el FormularioUsuario en modo edición, pasándole el usuario actual
        Window parent = SwingUtilities.getWindowAncestor(this);
        // Le pasamos 'this' (paneCuentaEmp) como referencia para que el formulario pueda llamar a 'cargarDatosUsuario(usuarioActualizado)'
        FormularioUsuario dialog = new FormularioUsuario(parent, usuarioDAO, tipoIdentificacionDAO, rolDAO, this, usuarioLogueado);
        dialog.setVisible(true);

        // Después de que el formulario se cierra (si hubo modificación y la ventana no se recarga completamente)
        // se supone que FormularioUsuario ya llamó a recargarDatosUsuario en este panel.
    }

    /**
     * Maneja la lógica para eliminar la cuenta del usuario actual.
     */
    private void eliminarCuenta() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea ELIMINAR SU CUENTA PERMANENTEMENTE?\n" +
                "Esta acción no se puede deshacer.",
                "Confirmar Eliminación de Cuenta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
                boolean eliminado = usuarioDAO.eliminarUsuario(usuarioLogueado.getIdUsuario(), conn);

                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Su cuenta ha sido eliminada exitosamente.", "Cuenta Eliminada", JOptionPane.INFORMATION_MESSAGE);
                    cerrarSesion(); // Cierra sesión después de eliminar la cuenta
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar su cuenta. Intente de nuevo.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error de base de datos al intentar eliminar la cuenta: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    /**
     * Maneja la lógica para cerrar la sesión del usuario.
     */
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea cerrar su sesión?",
                "Confirmar Cierre de Sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame != null) {
                parentFrame.dispose();
            }

            EventQueue.invokeLater(() -> {
                try {
                    intefaceLogin loginFrame = new intefaceLogin();
                    loginFrame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al abrir la pantalla de inicio de sesión.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }
}