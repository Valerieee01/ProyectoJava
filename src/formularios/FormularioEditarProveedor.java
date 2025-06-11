package formularios;

import javax.swing.*;
import javax.swing.border.*;

import DAO.PersonasDAO;
import modelos.Persona;
import menuInicialAdministrador.panelProveedores;
import util.ConnectionADMIN;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp; // Importar Timestamp

public class FormularioEditarProveedor extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField txtNombre, txtNumeroId, txtCorreo, txtTelefono, txtDireccion, txtCiudad;
    private JComboBox<String> comboTipoId;
    private JRadioButton radioActivo, radioInactivo;
    private JButton btnGuardar, btnCancelar;
    

    private panelProveedores panelProveedoresRef;
    private String numeroIdentificacionOriginal;

    /**
     * Constructor para un nuevo proveedor.
     * @param parent El padre del diálogo.
     * @param panelProveedoresRef La referencia al panel de proveedores para recargar la tabla.
     */
    public FormularioEditarProveedor(Window parent, panelProveedores panelProveedoresRef) {
        super(parent, "Nuevo Proveedor", ModalityType.APPLICATION_MODAL);
        this.panelProveedoresRef = panelProveedoresRef;
        configurarFormulario(false, null);
    }

    /**
     * Constructor para editar un proveedor existente.
     * @param parent El padre del diálogo.
     * @param panelProveedoresRef La referencia al panel de proveedores para recargar la tabla.
     * @param numeroIdentificacionEditar El número de identificación del proveedor a editar.
     */
    public FormularioEditarProveedor(Window parent, panelProveedores panelProveedoresRef, String numeroIdentificacionEditar) {
        super(parent, "Editar Proveedor", ModalityType.APPLICATION_MODAL);
        this.panelProveedoresRef = panelProveedoresRef;
        this.numeroIdentificacionOriginal = numeroIdentificacionEditar;
        configurarFormulario(true, numeroIdentificacionEditar);
    }
    
    private void configurarFormulario(boolean esEdicion, String numeroIdentificacionEditar) {
        setSize(520, 520);
        setLocationRelativeTo(getParent());
        setResizable(false);
        getContentPane().setBackground(Color.decode("#f4f6f8"));
        setLayout(new BorderLayout(10, 10));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 25, 15, 25));
        panelPrincipal.setBackground(Color.decode("#f4f6f8"));

        // *** CORRECCIÓN: Título del diálogo para "Proveedor" ***
        JLabel titulo = new JLabel(esEdicion ? "Editar Proveedor" : "Nuevo Proveedor");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.white);
        panelFormulario.setBorder(new TitledBorder(
            BorderFactory.createLineBorder(Color.gray, 1, true),
            // *** CORRECCIÓN: Título del borde del formulario para "Proveedor" ***
            "Datos del Proveedor",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font campoFont = new Font("Segoe UI", Font.PLAIN, 14);

        agregarCampo(panelFormulario, gbc, "Nombre Completo:", txtNombre = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Tipo de Identificación:", comboTipoId = crearCombo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Número de Identificación:", txtNumeroId = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Correo Electrónico:", txtCorreo = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Teléfono:", txtTelefono = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Dirección:", txtDireccion = crearCampo(campoFont), labelFont);
        // Considera agregar un campo para la Ciudad si no está fijo en 1
        // agregarCampo(panelFormulario, gbc, "Ciudad:", txtCiudad = crearCampo(campoFont), labelFont);


        gbc.gridx = 0; gbc.gridy++;
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(labelFont);
        panelFormulario.add(lblEstado, gbc);
        gbc.gridx = 1;
        radioActivo = new JRadioButton("Activo", true);
        radioInactivo = new JRadioButton("Inactivo");
        radioActivo.setFont(campoFont);
        radioInactivo.setFont(campoFont);
        radioActivo.setBackground(Color.white);
        radioInactivo.setBackground(Color.white);
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radioActivo);
        grupo.add(radioInactivo);
        JPanel pnlEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlEstado.setBackground(Color.white);
        pnlEstado.add(radioActivo);
        pnlEstado.add(radioInactivo);
        panelFormulario.add(pnlEstado, gbc);

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

        if (esEdicion && numeroIdentificacionEditar != null && !numeroIdentificacionEditar.isEmpty()) {
            try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
                PersonasDAO personasDAO = new PersonasDAO();
                Persona persona = personasDAO.obtenerPersonaPorNumeroIdentificacion(numeroIdentificacionEditar, conn);
                if (persona != null) {
                    txtNombre.setText(persona.getNombres());
                    String tipoIdentificacionStr = "";
                    switch (persona.getTipoIdentificacion()) {
                        case 1: tipoIdentificacionStr = "CC"; break;
                        case 2: tipoIdentificacionStr = "TI"; break;
                        case 3: tipoIdentificacionStr = "CE"; break;
                        case 4: tipoIdentificacionStr = "PAS"; break;
                        default: tipoIdentificacionStr = "CC";
                    }
                    comboTipoId.setSelectedItem(tipoIdentificacionStr);

                    txtNumeroId.setText(persona.getNumeroIdentificacion());
                    txtCorreo.setText(persona.getCorreo());
                    txtTelefono.setText(persona.getTelefono());
                    txtDireccion.setText(persona.getDireccion());
                    // txtCiudad.setText(String.valueOf(persona.getIdCiudad())); // Si tienes un campo para la ciudad
                    // *** CORRECCIÓN: Uso de constantes de enum en mayúsculas ***
                    if (persona.getEstado() == Persona.Estado.activo) radioActivo.setSelected(true);
                    else radioInactivo.setSelected(true);

                    txtNumeroId.setEnabled(false);
                } else {
                    // *** CORRECCIÓN: Mensaje a "Proveedor" ***
                    JOptionPane.showMessageDialog(this, "Proveedor no encontrado para edición.", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // *** CORRECCIÓN: Mensaje a "Proveedor" ***
                JOptionPane.showMessageDialog(this, "Error al cargar datos del proveedor: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        }

        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || txtNumeroId.getText().trim().isEmpty() ||
                txtCorreo.getText().trim().isEmpty() || txtTelefono.getText().trim().isEmpty() ||
                txtDireccion.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos requeridos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Persona persona = new Persona();
            persona.setNombres(txtNombre.getText().trim());

            int idTipoIdentificacion = 0;
            String selectedTipoId = (String) comboTipoId.getSelectedItem();
            switch (selectedTipoId) {
                case "CC": idTipoIdentificacion = 1; break;
                case "TI": idTipoIdentificacion = 2; break;
                case "CE": idTipoIdentificacion = 3; break;
                case "PAS": idTipoIdentificacion = 4; break;
                default: idTipoIdentificacion = 1;
            }
            persona.setTipoIdentificacion(idTipoIdentificacion);
            persona.setNumeroIdentificacion(esEdicion ? numeroIdentificacionOriginal : txtNumeroId.getText().trim());
            persona.setCorreo(txtCorreo.getText().trim());
            persona.setTelefono(txtTelefono.getText().trim());
            persona.setDireccion(txtDireccion.getText().trim());
            persona.setIdCiudad(1); // Asumiendo ID de ciudad fijo, considera permitir selección de ciudad
            // *** CORRECCIÓN: Uso de constantes de enum en mayúsculas ***
            persona.setEstado(radioActivo.isSelected() ? Persona.Estado.activo : Persona.Estado.inactivo);

            try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
                PersonasDAO personasDAO = new PersonasDAO();
                if (esEdicion) {
                    personasDAO.modificarPersona(persona, conn); // Se mantiene modificarPersona para edición
                    // *** CORRECCIÓN: Mensaje a "Proveedor" ***
                    JOptionPane.showMessageDialog(this, "Proveedor actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // *** CAMBIO CLAVE AQUÍ: Se usa almacenarProveedor para nuevos proveedores ***
                    personasDAO.almacenarProveedor(conn , persona);
                    // *** CORRECCIÓN: Mensaje a "Proveedor" ***
                    JOptionPane.showMessageDialog(this, "Proveedor guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                }
                panelProveedoresRef.cargarDatosProveedores();
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                if (ex.getMessage().contains("Duplicate entry") || ex.getMessage().contains("Duplicate key")) {
                    // *** CORRECCIÓN: Mensaje a "proveedor" ***
                    JOptionPane.showMessageDialog(this, "Ya existe un proveedor con ese número de identificación o correo electrónico.", "Error de Datos Duplicados", JOptionPane.ERROR_MESSAGE);
                } else {
                    // *** CORRECCIÓN: Mensaje a "proveedor" ***
                    JOptionPane.showMessageDialog(this, "Error al guardar/actualizar proveedor: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, String etiqueta, Component campo, Font labelFont) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(labelFont);
        panel.add(lbl, gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
    }

    private JTextField crearCampo(Font fuente) {
        JTextField txt = new JTextField(20);
        txt.setFont(fuente);
        return txt;
    }

    private JComboBox<String> crearCombo(Font fuente) {
        JComboBox<String> combo = new JComboBox<>(new String[]{"CC","TI","CE","PAS"});
        combo.setFont(fuente);
        return combo;
    }

    private void estilizarBoton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.white);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 30));
    }
}
