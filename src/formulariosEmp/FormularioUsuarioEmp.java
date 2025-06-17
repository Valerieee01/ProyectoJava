package formulariosEmp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DAO.UsuariosDAO; // Corregido a UsuarioDAO (asumo que es tu DAO correcto para Usuario)
import menuInicialAdministrador.paneCuentaAdmin; // Importa tu panel de cuenta de administrador
import menuInicialEmpleado.paneCuentaEmp; // Importa tu panel de cuenta de empleado
import DAO.TipoIdentificacionDAO;
import DAO.RolDAO;
import modelos.Usuario;
import modelos.Usuario.Estado;
import modelos.TipoIdentificacion;
import modelos.Rol;
import util.ConnectionADMIN;

public class FormularioUsuarioEmp extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField txtIdUsuario; // Solo para visualización en modo edición
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JComboBox<TipoIdentificacion> cmbTipoIdentificacion;
    private JTextField txtNumeroIdentificacion;
    private JTextField txtEdad;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena; // Para la contraseña (se puede dejar como String si no se permite cambiar)
    private JPasswordField txtConfirmContrasena; // Para confirmar contraseña
    private JComboBox<Rol> cmbRol;
    private JCheckBox chkEstadoActivo; // Para el estado (activo/inactivo)

    private JButton btnGuardar, btnCancelar;

    private UsuariosDAO usuarioDAO; // Corregido a UsuarioDAO
    private TipoIdentificacionDAO tipoIdentificacionDAO; // Necesario para poblar ComboBox
    private RolDAO rolDAO;                               // Necesario para poblar ComboBox
    private JPanel panelPadreRef; // Referencia al panel que contiene la tabla (ej. paneCuentaEmp, o un panel de gestión de usuarios)
    private boolean esEdicion;
    private Usuario usuarioOriginal = null; // Almacena el objeto original en modo edición

    // Constructor para MODO AGREGAR (si decides permitir agregar usuarios desde aquí, diferente a Signin)
    public FormularioUsuarioEmp(Window parent, UsuariosDAO usuarioDAO, TipoIdentificacionDAO tipoIdentificacionDAO, RolDAO rolDAO, JPanel panelPadreRef) {
        super(parent, "Nuevo Usuario", ModalityType.APPLICATION_MODAL);
        this.usuarioDAO = usuarioDAO;
        this.tipoIdentificacionDAO = tipoIdentificacionDAO;
        this.rolDAO = rolDAO;
        this.panelPadreRef = panelPadreRef;
        this.esEdicion = false;
        configurarFormulario();
        cargarComboBoxes(); // Cargar los ComboBoxes al iniciar el formulario
    }

    // Constructor para MODO EDITAR
    // Recibe el panelPadreRef para poder llamar a su método de recarga (ej. cargarDatosUsuario())
    public FormularioUsuarioEmp(Window parent, UsuariosDAO usuarioDAO, TipoIdentificacionDAO tipoIdentificacionDAO, RolDAO rolDAO, JPanel panelPadreRef, Usuario usuarioParaEditar) {
        super(parent, "Editar Usuario", ModalityType.APPLICATION_MODAL);
        this.usuarioDAO = usuarioDAO;
        this.tipoIdentificacionDAO = tipoIdentificacionDAO;
        this.rolDAO = rolDAO;
        this.panelPadreRef = panelPadreRef;
        this.esEdicion = true;
        this.usuarioOriginal = usuarioParaEditar;
        configurarFormulario();
        cargarComboBoxes(); // Cargar los ComboBoxes ANTES de precargar los datos
        cargarDatosUsuario(usuarioOriginal); // Precargar datos del usuario
    }

    private void configurarFormulario() {
        setSize(550, 700); // Ajustar tamaño para más campos
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.decode("#f4f6f8"));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 25, 15, 25));
        panelPrincipal.setBackground(Color.decode("#f4f6f8"));

        JLabel titulo = new JLabel(esEdicion ? "Editar Usuario" : "Agregar Usuario");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.white);
        panelFormulario.setBorder(new TitledBorder(
                BorderFactory.createLineBorder(Color.gray, 1, true),
                "Datos del Usuario",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3; // Para las etiquetas

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font campoFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ID Usuario (Solo visible y no editable en modo edición)
        txtIdUsuario = crearCampo(campoFont);
        if (esEdicion) {
            txtIdUsuario.setEditable(false);
            txtIdUsuario.setBackground(new Color(240, 240, 240));
            addFormField(panelFormulario, gbc, "ID Usuario:", txtIdUsuario, labelFont);
        } else {
             // En modo agregar, el ID de Usuario no es necesario (se genera en la DB)
             // Puedes ocultar el campo o no agregarlo.
        }

        txtNombres = crearCampo(campoFont);
        addFormField(panelFormulario, gbc, "Nombres:", txtNombres, labelFont);

        txtApellidos = crearCampo(campoFont);
        addFormField(panelFormulario, gbc, "Apellidos:", txtApellidos, labelFont);

        // JComboBox para TipoIdentificacion
        cmbTipoIdentificacion = new JComboBox<>();
        cmbTipoIdentificacion.setFont(campoFont);
        addFormField(panelFormulario, gbc, "Tipo Identificación:", cmbTipoIdentificacion, labelFont);

        txtNumeroIdentificacion = crearCampo(campoFont);
        addFormField(panelFormulario, gbc, "Número Identificación:", txtNumeroIdentificacion, labelFont);

        txtEdad = crearCampo(campoFont);
        addFormField(panelFormulario, gbc, "Edad:", txtEdad, labelFont);

        txtCorreo = crearCampo(campoFont);
        addFormField(panelFormulario, gbc, "Correo:", txtCorreo, labelFont);

        txtContrasena = new JPasswordField(20);
        txtContrasena.setFont(campoFont);
        // En modo edición, la contraseña no se precarga por seguridad.
        // Se puede dejar vacío y el usuario la cambiará si lo desea.
        // Opcional: Solo permitir cambiar contraseña en una sección separada.
        addFormField(panelFormulario, gbc, "Contraseña:", txtContrasena, labelFont);

        txtConfirmContrasena = new JPasswordField(20);
        txtConfirmContrasena.setFont(campoFont);
        addFormField(panelFormulario, gbc, "Confirmar Contraseña:", txtConfirmContrasena, labelFont);

        // JComboBox para Rol
        cmbRol = new JComboBox<>();
        cmbRol.setFont(campoFont);
        addFormField(panelFormulario, gbc, "Rol:", cmbRol, labelFont);

        // CheckBox para Estado
        chkEstadoActivo = new JCheckBox("Activo");
        chkEstadoActivo.setFont(campoFont);
        addFormField(panelFormulario, gbc, "Estado:", chkEstadoActivo, labelFont);
        chkEstadoActivo.setSelected(true); // Por defecto activo

        gbc.weightx = 0.7; // Ajustar peso para la segunda columna (campos de entrada)

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        pnlBotones.setBackground(Color.decode("#f4f6f8"));
        btnGuardar = new JButton(esEdicion ? "Actualizar" : "Guardar");
        btnCancelar = new JButton("Cancelar");
        estilizarBoton(btnGuardar, new Color(76, 175, 80));
        estilizarBoton(btnCancelar, new Color(244, 67, 54));
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);
        panelPrincipal.add(pnlBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Lógica del botón Guardar/Actualizar
        btnGuardar.addActionListener(e -> guardarOActualizarUsuario());

        // Lógica del botón Cancelar
        btnCancelar.addActionListener(e -> dispose());
    }

    /**
     * Carga los datos de un objeto Usuario en los campos del formulario.
     * Este método solo se llama en modo edición.
     * @param usuario El objeto Usuario con los datos a precargar.
     */
    private void cargarDatosUsuario(Usuario usuario) {
        if (usuario != null) {
            txtIdUsuario.setText(String.valueOf(usuario.getIdUsuario()));
            txtNombres.setText(usuario.getNombres());
            txtApellidos.setText(usuario.getApellidos());

            // Seleccionar el tipo de identificación en el JComboBox
            selectComboBoxItem(cmbTipoIdentificacion, usuario.getTipoIdentificacion());

            txtNumeroIdentificacion.setText(usuario.getNumeroIdentificacion());
            txtEdad.setText(String.valueOf(usuario.getEdad()));
            txtCorreo.setText(usuario.getCorreo());
            // La contraseña NO se precarga en JPasswordField por seguridad.
            // Si el usuario quiere cambiarla, la introducirá de nuevo.
            txtContrasena.setText("");
            txtConfirmContrasena.setText("");

            // Seleccionar el rol en el JComboBox
            selectComboBoxItem(cmbRol, usuario.getIdRol());

            // Establecer el estado del CheckBox
            chkEstadoActivo.setSelected(usuario.getEstado() == Estado.activo);
        }
    }

    /**
     * Carga los datos en los JComboBox de Tipos de Identificación y Roles desde la base de datos.
     */
    private void cargarComboBoxes() {
        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            // Cargar Tipos de Identificación
            List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionDAO.obtenerTodosTiposIdentificacion(conn);
            cmbTipoIdentificacion.removeAllItems();
            cmbTipoIdentificacion.addItem(null); // Opción por defecto "Seleccione..."
            for (TipoIdentificacion tipo : tiposIdentificacion) {
                cmbTipoIdentificacion.addItem(tipo);
            }

            // Cargar Roles
            List<Rol> roles = rolDAO.obtenerTodosRoles(conn);
            cmbRol.removeAllItems();
            cmbRol.addItem(null); // Opción por defecto "Seleccione..."
            for (Rol rol : roles) {
                cmbRol.addItem(rol);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los tipos de identificación o roles: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Selecciona un ítem en un JComboBox basado en su ID.
     */
    private <T> void selectComboBoxItem(JComboBox<T> comboBox, int idToSelect) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            T item = comboBox.getItemAt(i);
            if (item instanceof TipoIdentificacion && ((TipoIdentificacion) item).getIdTipoIdentificacion() == idToSelect) {
                comboBox.setSelectedItem(item);
                return;
            } else if (item instanceof Rol && ((Rol) item).getIdRol() == idToSelect) {
                comboBox.setSelectedItem(item);
                return;
            }
        }
    }


    /**
     * Maneja la lógica de guardar un nuevo usuario o actualizar uno existente.
     */
    private void guardarOActualizarUsuario() {
        // 1. Obtener y validar datos de los campos
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        TipoIdentificacion tipoIdentificacionSeleccionado = (TipoIdentificacion) cmbTipoIdentificacion.getSelectedItem();
        String numeroIdentificacion = txtNumeroIdentificacion.getText().trim();
        String edadStr = txtEdad.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();
        String confirmContrasena = new String(txtConfirmContrasena.getPassword()).trim();
        Rol rolSeleccionado = (Rol) cmbRol.getSelectedItem();
        Estado estado = chkEstadoActivo.isSelected() ? Estado.activo : Estado.inactivo;

        // Validaciones de campos obligatorios
        if (nombres.isEmpty() || apellidos.isEmpty() || tipoIdentificacionSeleccionado == null ||
            numeroIdentificacion.isEmpty() || edadStr.isEmpty() || correo.isEmpty() ||
            rolSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Si es edición, y no se cambia la contraseña, no es obligatoria
        // Solo requerimos contraseña si es NUEVO o si el usuario TIENE ALGO en los campos de contraseña
        if (!esEdicion || (!contrasena.isEmpty() || !confirmContrasena.isEmpty())) {
            if (contrasena.isEmpty() || confirmContrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Si desea cambiar la contraseña (o si es un nuevo usuario), debe completar ambos campos de contraseña.", "Contraseña Incompleta", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!contrasena.equals(confirmContrasena)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error de Contraseña", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Agrega validaciones de fortaleza de contraseña aquí si lo deseas
        } else {
             // Si es edición y los campos de contraseña están vacíos, se asume que no se quiere cambiar la contraseña
             // No se realiza validación de campos vacíos para la contraseña
        }


        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.", "Formato Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idTipoIdentificacion = tipoIdentificacionSeleccionado.getIdTipoIdentificacion();
        int idRol = rolSeleccionado.getIdRol();

        // Determinar la contraseña a enviar
        // Si es edición y los campos de contraseña están vacíos, se asume que no se quiere cambiar la contraseña.
        // En un escenario real con hashing, no deberías enviar una contraseña vacía.
        // Si la BD no permite UPDATE con contraseñas vacías, tendrías que obtener el hash existente.
        String contrasenaAEnviar;
        if (esEdicion && contrasena.isEmpty()) {
            contrasenaAEnviar = usuarioOriginal.getContrasena(); // Mantener la contraseña original (el hash)
        } else {
            // ¡ATENCIÓN: HASH DE CONTRASEÑA!
            // contrasenaAEnviar = BCrypt.hashpw(contrasena, BCrypt.gensalt());
            contrasenaAEnviar = contrasena; // Solo para desarrollo, sin hashing
        }


        // 2. Crear el objeto Usuario
        Usuario usuario;
        if (esEdicion) {
            usuario = new Usuario(nombres, apellidos, idTipoIdentificacion,
                                  numeroIdentificacion, edad, correo, contrasenaAEnviar, idRol);
            usuario.setIdUsuario(usuarioOriginal.getIdUsuario()); // El ID es crucial para la edición
            usuario.setEstado(estado);
        } else {
            usuario = new Usuario(nombres, apellidos, idTipoIdentificacion,
                                  numeroIdentificacion, edad, correo, contrasenaAEnviar, idRol);
            usuario.setEstado(estado); // Estado por defecto activo, se puede ajustar
        }

        // 3. Persistir los datos usando el DAO
        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            if (esEdicion) {
                boolean modificado = usuarioDAO.modificarUsuario(usuario, conn);
                if (modificado) {
                    JOptionPane.showMessageDialog(this, "Usuario modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // --- Lógica de recarga del panel padre ---
                    if (panelPadreRef instanceof paneCuentaEmp) {
                        ((paneCuentaEmp) panelPadreRef).cargarDatosUsuario(usuario); 
                    }
                    // 2. Si tienes un panel de cuenta de administrador y quieres que se refresque:
                    else if (panelPadreRef instanceof paneCuentaEmp) { 
                        ((paneCuentaEmp) panelPadreRef).cargarDatosUsuario(usuario);
                    }
                  
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo modificar el usuario. ¿Existe el ID?", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else { 
                int idGenerado = usuarioDAO.agregarUsuario(usuario, conn);
                if (idGenerado != -1) {
                    JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente. ID: " + idGenerado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo agregar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            dispose(); // Cerrar el formulario después de guardar/actualizar
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("for key 'correo'")) {
                JOptionPane.showMessageDialog(this, "El correo electrónico ya está registrado.", "Error de Registro/Edición", JOptionPane.ERROR_MESSAGE);
            } else if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("for key 'numero_identificacion'")) {
                JOptionPane.showMessageDialog(this, "El número de identificación ya está registrado.", "Error de Registro/Edición", JOptionPane.ERROR_MESSAGE);
            } else if (ex.getMessage().contains("FOREIGN KEY")) {
                JOptionPane.showMessageDialog(this, "Error: El tipo de identificación o el rol seleccionado no son válidos en la base de datos.", "Error de Clave Foránea", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error de base de datos al guardar/actualizar usuario: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    // --- Métodos Auxiliares para UI ---
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, Component field, Font labelFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.3;
    }

    private JTextField crearCampo(Font fuente) {
        JTextField txt = new JTextField(20);
        txt.setFont(fuente);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.lightGray, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return txt;
    }

    private void estilizarBoton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.white);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setBorder(BorderFactory.createLineBorder(bg.darker(), 1));
    }
}