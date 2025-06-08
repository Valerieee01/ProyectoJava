package formularios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class FormularioEditarProveedor extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre, txtNumeroId, txtCorreo, txtTelefono, txtDireccion, txtCiudad;
    private JComboBox<String> comboTipoId;
    private JRadioButton radioActivo, radioInactivo;
    private JButton btnGuardar, btnCancelar;

    public FormularioEditarProveedor(Window parent, DefaultTableModel modeloTabla) {
        super(parent, "Nuevo Proveedor", ModalityType.APPLICATION_MODAL);
        configurarFormulario(modeloTabla, false, -1);
    }

    public FormularioEditarProveedor(Window parent, DefaultTableModel modeloTabla, int rowIndex) {
        super(parent, "Editar Proveedor", ModalityType.APPLICATION_MODAL);
        configurarFormulario(modeloTabla, true, rowIndex);
    }

    private void configurarFormulario(DefaultTableModel modeloTabla, boolean esEdicion, int rowIndex) {
        setSize(500, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Campos
        panelFormulario.add(new JLabel("Nombre o Razón Social:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Tipo de Identificación:"), gbc);
        gbc.gridx = 1;
        comboTipoId = new JComboBox<>(new String[]{"CC", "TI", "CE", "PAS"});
        panelFormulario.add(comboTipoId, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Número de Identificación:"), gbc);
        gbc.gridx = 1;
        txtNumeroId = new JTextField();
        panelFormulario.add(txtNumeroId, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Correo Electrónico:"), gbc);
        gbc.gridx = 1;
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Ciudad:"), gbc);
        gbc.gridx = 1;
        txtCiudad = new JTextField();
        panelFormulario.add(txtCiudad, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        radioActivo = new JRadioButton("Activo", true);
        radioInactivo = new JRadioButton("Inactivo");
        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(radioActivo);
        grupoEstado.add(radioInactivo);
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEstado.add(radioActivo);
        panelEstado.add(radioInactivo);
        panelFormulario.add(panelEstado, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton(esEdicion ? "Actualizar" : "Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acción Guardar
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String tipoId = comboTipoId.getSelectedItem().toString();
            String numeroId = txtNumeroId.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String ciudad = txtCiudad.getText().trim();
            String estado = radioActivo.isSelected() ? "activo" : "inactivo";

            if (esEdicion && rowIndex >= 0) {
                modeloTabla.setValueAt(nombre, rowIndex, 0);
                modeloTabla.setValueAt(numeroId, rowIndex, 1);
                modeloTabla.setValueAt(correo, rowIndex, 2);
                modeloTabla.setValueAt(telefono, rowIndex, 3);
                modeloTabla.setValueAt(direccion, rowIndex, 4);
            } else {
                modeloTabla.addRow(new Object[]{
                        nombre, numeroId, correo, telefono, direccion, ciudad, estado, "Acciones"
                });
            }
            dispose();
        });

        // Acción Cancelar
        btnCancelar.addActionListener(e -> dispose());
    }
}
