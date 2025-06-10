package formularios;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormularioEditarEmpleado extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField txtNombre, txtNumeroId, txtCorreo, txtTelefono, txtDireccion, txtCiudad, txtSalario;
    private JComboBox<String> comboTipoId;
    private JRadioButton radioActivo, radioInactivo;
    private JButton btnGuardar, btnCancelar;

    public FormularioEditarEmpleado(Window parent, DefaultTableModel modeloTabla) {
        super(parent, "Nuevo Empleado", ModalityType.APPLICATION_MODAL);
        configurarFormulario(modeloTabla, false, -1);
    }

    public FormularioEditarEmpleado(Window parent, DefaultTableModel modeloTabla, int rowIndex) {
        super(parent, "Editar Empleado", ModalityType.APPLICATION_MODAL);
        configurarFormulario(modeloTabla, true, rowIndex);
    }

    private void configurarFormulario(DefaultTableModel modeloTabla, boolean esEdicion, int rowIndex) {
        setSize(550, 600);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.decode("#f4f6f8"));

        // Panel principal con título
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.white);
        panelFormulario.setBorder(new TitledBorder(
            new LineBorder(Color.gray, 1, true),
            esEdicion ? "Editar Empleado" : "Nuevo Empleado",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font campoFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Nombre
        panelFormulario.add(crearEtiqueta("Nombre o Razón Social:", labelFont), gbc);
        gbc.gridx = 1;
        txtNombre = crearCampoTexto(campoFont);
        panelFormulario.add(txtNombre, gbc);

        // Tipo de identificación
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Tipo de Identificación:", labelFont), gbc);
        gbc.gridx = 1;
        comboTipoId = new JComboBox<>(new String[]{"CC", "TI", "CE", "PAS"});
        comboTipoId.setFont(campoFont);
        panelFormulario.add(comboTipoId, gbc);

        // Número de identificación
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Número de Identificación:", labelFont), gbc);
        gbc.gridx = 1;
        txtNumeroId = crearCampoTexto(campoFont);
        panelFormulario.add(txtNumeroId, gbc);

        // Correo
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Correo Electrónico:", labelFont), gbc);
        gbc.gridx = 1;
        txtCorreo = crearCampoTexto(campoFont);
        panelFormulario.add(txtCorreo, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Teléfono:", labelFont), gbc);
        gbc.gridx = 1;
        txtTelefono = crearCampoTexto(campoFont);
        panelFormulario.add(txtTelefono, gbc);

        // Dirección
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Dirección:", labelFont), gbc);
        gbc.gridx = 1;
        txtDireccion = crearCampoTexto(campoFont);
        panelFormulario.add(txtDireccion, gbc);

        // Ciudad
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Ciudad:", labelFont), gbc);
        gbc.gridx = 1;
        txtCiudad = crearCampoTexto(campoFont);
        panelFormulario.add(txtCiudad, gbc);

        // Salario
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Salario:", labelFont), gbc);
        gbc.gridx = 1;
        txtSalario = crearCampoTexto(campoFont);
        panelFormulario.add(txtSalario, gbc);

        // Estado
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(crearEtiqueta("Estado:", labelFont), gbc);
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

        add(panelFormulario, BorderLayout.CENTER);

        // Botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        pnlBotones.setBackground(Color.decode("#f4f6f8"));
        btnGuardar = new JButton(esEdicion ? "Actualizar" : "Guardar");
        btnCancelar = new JButton("Cancelar");
        estilizarBoton(btnGuardar, new Color(76, 175, 80));
        estilizarBoton(btnCancelar, new Color(244, 67, 54));
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);
        add(pnlBotones, BorderLayout.SOUTH);

        // Cargar valores si es edición
        if (esEdicion) {
            txtNombre.setText(modeloTabla.getValueAt(rowIndex, 0).toString());
            txtNumeroId.setText(modeloTabla.getValueAt(rowIndex, 1).toString());
            txtCorreo.setText(modeloTabla.getValueAt(rowIndex, 2).toString());
            txtTelefono.setText(modeloTabla.getValueAt(rowIndex, 3).toString());
            txtDireccion.setText(modeloTabla.getValueAt(rowIndex, 4).toString());
            txtSalario.setText(modeloTabla.getValueAt(rowIndex, 5).toString());
            String estado = modeloTabla.getValueAt(rowIndex, 6).toString();
            if ("activo".equalsIgnoreCase(estado)) radioActivo.setSelected(true);
            else radioInactivo.setSelected(true);

        }

        // Acción Guardar
        btnGuardar.addActionListener(e -> {
            String[] datos = {
                txtNombre.getText().trim(),
                txtNumeroId.getText().trim(),
                txtCorreo.getText().trim(),
                txtTelefono.getText().trim(),
                txtDireccion.getText().trim(),
                txtSalario.getText().trim(),
        		"Acciones"                      
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
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fuente);
        return lbl;
    }

    private JTextField crearCampoTexto(Font fuente) {
        JTextField txt = new JTextField(20);
        txt.setFont(fuente);
        return txt;
    }

    private void estilizarBoton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.white);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(100, 30));
    }
}

