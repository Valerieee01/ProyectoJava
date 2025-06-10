package formularios;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormularioEditarProveedor extends JDialog {

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
        setSize(550, 580);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.decode("#f4f6f8"));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.white);
        panelFormulario.setBorder(new TitledBorder(new LineBorder(Color.gray, 1, true), "Datos del Proveedor", TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 14)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);

        panelFormulario.add(crearEtiqueta("Nombre o Razón Social:", labelFont), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Tipo de Identificación:", labelFont), gbc);
        gbc.gridx = 1;
        comboTipoId = new JComboBox<>(new String[]{"CC", "TI", "CE", "PAS"});
        panelFormulario.add(comboTipoId, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Número de Identificación:", labelFont), gbc);
        gbc.gridx = 1;
        txtNumeroId = new JTextField();
        panelFormulario.add(txtNumeroId, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Correo Electrónico:", labelFont), gbc);
        gbc.gridx = 1;
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Teléfono:", labelFont), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Dirección:", labelFont), gbc);
        gbc.gridx = 1;
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Ciudad:", labelFont), gbc);
        gbc.gridx = 1;
        txtCiudad = new JTextField();
        panelFormulario.add(txtCiudad, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Estado:", labelFont), gbc);
        gbc.gridx = 1;
        radioActivo = new JRadioButton("Activo", true);
        radioInactivo = new JRadioButton("Inactivo");
        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(radioActivo);
        grupoEstado.add(radioInactivo);

        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelEstado.setBackground(Color.white);
        panelEstado.add(radioActivo);
        panelEstado.add(radioInactivo);
        panelFormulario.add(panelEstado, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // Panel botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelBotones.setBackground(Color.decode("#f4f6f8"));
        btnGuardar = new JButton(esEdicion ? "Actualizar" : "Guardar");
        btnCancelar = new JButton("Cancelar");

        estilizarBoton(btnGuardar, new Color(76, 175, 80), Color.white);
        estilizarBoton(btnCancelar, new Color(244, 67, 54), Color.white);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar valores si es edición
        if (esEdicion && rowIndex >= 0) {
        	txtNombre.setText(modeloTabla.getValueAt(rowIndex, 0).toString());
        	txtNumeroId.setText(modeloTabla.getValueAt(rowIndex, 1).toString());
        	txtCorreo.setText(modeloTabla.getValueAt(rowIndex, 2).toString());
        	txtTelefono.setText(modeloTabla.getValueAt(rowIndex, 3).toString());
        	String estado = modeloTabla.getValueAt(rowIndex, 4).toString();
        	if ("activo".equalsIgnoreCase(estado)) radioActivo.setSelected(true);
        	else radioInactivo.setSelected(true);

        }

        // Acción Guardar
        btnGuardar.addActionListener(e -> {
        	Object[] datos = {
        		    txtNombre.getText().trim(),            // 0: Nombre
        		    txtNumeroId.getText().trim(),          // 1: Identificación
        		    txtCorreo.getText().trim(),            // 2: Correo
        		    txtTelefono.getText().trim(),          // 3: Teléfono
        		    radioActivo.isSelected() ? "activo" : "inactivo", // 4: Estado
        		    "Acciones"                             // 5: Acciones
        		};
        	if (esEdicion && rowIndex >= 0) {
        	    for (int i = 0; i < datos.length; i++) {
        	        modeloTabla.setValueAt(datos[i], rowIndex, i);
        	    }
        	} else {
        	    modeloTabla.addRow(datos);
        	}
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private JLabel crearEtiqueta(String texto, Font fuente) {
        JLabel label = new JLabel(texto);
        label.setFont(fuente);
        return label;
    }

    private void estilizarBoton(JButton boton, Color fondo, Color texto) {
        boton.setFocusPainted(false);
        boton.setBackground(fondo);
        boton.setForeground(texto);
        boton.setPreferredSize(new Dimension(100, 30));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setBorder(new LineBorder(fondo.darker(), 1));
    }
}
