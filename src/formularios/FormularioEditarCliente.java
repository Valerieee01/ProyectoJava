package formularios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormularioEditarCliente extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField txtNombre, txtNumeroId, txtCorreo, txtTelefono, txtDireccion;
    private JComboBox<String> comboTipoId;
    private JRadioButton radioActivo, radioInactivo;
    private JButton btnGuardar, btnCancelar;

    public FormularioEditarCliente(Window parent, DefaultTableModel modeloTabla) {
        super(parent, "Nuevo Cliente", ModalityType.APPLICATION_MODAL);
        configurarFormulario(modeloTabla, false, -1);
    }

    public FormularioEditarCliente(Window parent, DefaultTableModel modeloTabla, int rowIndex) {
        super(parent, "Editar Cliente", ModalityType.APPLICATION_MODAL);
        configurarFormulario(modeloTabla, true, rowIndex);
    }

    private void configurarFormulario(DefaultTableModel modeloTabla, boolean esEdicion, int rowIndex) {
        setSize(520, 520);
        setLocationRelativeTo(getParent());
        setResizable(false);
        getContentPane().setBackground(Color.decode("#f4f6f8"));
        setLayout(new BorderLayout(10, 10));

        // Panel principal con título y fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 25, 15, 25));
        panelPrincipal.setBackground(Color.decode("#f4f6f8"));

        // Título
        JLabel titulo = new JLabel(esEdicion ? "Editar Cliente" : "Nuevo Cliente");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Panel de formulario interno
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.white);
        panelFormulario.setBorder(new TitledBorder(
            BorderFactory.createLineBorder(Color.gray, 1, true),
            "Datos del Cliente",
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

        // Agregar campos usando la función auxiliar
        agregarCampo(panelFormulario, gbc, "Nombre Completo:", txtNombre = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Tipo de Identificación:", comboTipoId = crearCombo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Número de Identificación:", txtNumeroId = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Correo Electrónico:", txtCorreo = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Teléfono:", txtTelefono = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Dirección:", txtDireccion = crearCampo(campoFont), labelFont);

        // Estado (radio buttons)
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

        // Panel de botones
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

        // Cargar valores si es edición
        if (esEdicion && rowIndex >= 0) {
            txtNombre.setText(modeloTabla.getValueAt(rowIndex, 0).toString());
            txtNumeroId.setText(modeloTabla.getValueAt(rowIndex, 1).toString());
            txtCorreo.setText(modeloTabla.getValueAt(rowIndex, 2).toString());
            txtTelefono.setText(modeloTabla.getValueAt(rowIndex, 3).toString());
            txtDireccion.setText(modeloTabla.getValueAt(rowIndex, 4).toString());
            String estado = modeloTabla.getValueAt(rowIndex, 5).toString();
            if ("activo".equalsIgnoreCase(estado)) radioActivo.setSelected(true);
            else radioInactivo.setSelected(true);
        }

        // Acción Guardar
        btnGuardar.addActionListener(e -> {
        	Object[] datos = {
        		    txtNombre.getText().trim(),            
        		    txtNumeroId.getText().trim(),          
        		    txtCorreo.getText().trim(),            
        		    txtTelefono.getText().trim(),          
        		    txtDireccion.getText().trim(),         
        		    radioActivo.isSelected() ? "activo" : "inactivo", 
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

