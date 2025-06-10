package formularios;

import javax.swing.*;  // Importa componentes Swing básicos
import javax.swing.border.EmptyBorder;  // Para márgenes internos en paneles
import javax.swing.border.TitledBorder;  // Para bordes con título
import javax.swing.table.DefaultTableModel;  // Modelo de tabla por defecto
import java.awt.*;  // Importa clases AWT (layouts, eventos, etc.)

public class FormularioEditar extends JDialog {

    private static final long serialVersionUID = 1L;  // Versión de serialización para JDialog

    // Campos de texto para los datos del equipo
    private JTextField txtNumero, txtPlaca, txtDescripcion, txtIdCliente;
    private JButton btnGuardar, btnCancelar;  // Botones de acción

    // Constructor para modo "Agregar"
    public FormularioEditar(Window parent, DefaultTableModel modeloTabla) {
        super(parent, "Agregar Equipo", ModalityType.APPLICATION_MODAL);  // Inicializa JDialog modal
        configurarFormulario(modeloTabla, false, -1);  // Llama al método de configuración en modo agregar
    }

    // Constructor para modo "Editar" con índice de fila
    public FormularioEditar(Window parent, DefaultTableModel modeloTabla, int rowIndex) {
        super(parent, "Editar Equipo", ModalityType.APPLICATION_MODAL);  // Título y modalidad
        configurarFormulario(modeloTabla, true, rowIndex);  // Configuración en modo edición
    }

    // Método interno que arma la interfaz y lógica
    private void configurarFormulario(DefaultTableModel modeloTabla, boolean esEdicion, int rowIndex) {
        setSize(550, 480);  // Dimensiones del diálogo
        setLocationRelativeTo(getParent());  // Centra respecto a la ventana padre
        setResizable(false);  // No permite cambiar tamaño
        setLayout(new BorderLayout(10, 10));  // Layout principal con márgenes
        getContentPane().setBackground(Color.decode("#f4f6f8"));  // Color de fondo suave

        // Panel principal para contener todo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 25, 15, 25));  // Margen interno
        panelPrincipal.setBackground(Color.decode("#f4f6f8"));  // Coincide fondo

        // Título en la parte superior del diálogo
        JLabel titulo = new JLabel(esEdicion ? "Editar Equipo" : "Agregar Equipo");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));  // Fuente grande
        titulo.setHorizontalAlignment(SwingConstants.CENTER);  // Centrado horizontal
        panelPrincipal.add(titulo, BorderLayout.NORTH);  // Agrega al norte

        // Panel con grid para campos del formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.white);  // Fondo blanco para contraste
        panelFormulario.setBorder(new TitledBorder(
            BorderFactory.createLineBorder(Color.gray, 1, true),  // Borde gris redondeado
            "Datos del Equipo",  // Texto del borde
            TitledBorder.LEFT,  // Alineación del título
            TitledBorder.TOP,  // Posición del título
            new Font("SansSerif", Font.BOLD, 14)  // Fuente del título
        ));

        // Configuración de GridBagConstraints para posición de componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);  // Margen entre elementos
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Rellenar horizontalmente
        gbc.gridx = 0;  // Columna inicial
        gbc.gridy = 0;  // Fila inicial

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);  // Fuente de etiquetas
        Font campoFont = new Font("Segoe UI", Font.PLAIN, 14);  // Fuente de campos

        // Agrega cada campo llamando al método auxiliar
        agregarCampo(panelFormulario, gbc, "Número de Equipo:", txtNumero = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Placa:", txtPlaca = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Descripción:", txtDescripcion = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "ID Cliente:", txtIdCliente = crearCampo(campoFont), labelFont);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);  // Agrega formulario al centro

        // Panel para botones "Guardar" y "Cancelar"
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        pnlBotones.setBackground(Color.decode("#f4f6f8"));  // Mismo fondo que principal
        btnGuardar = new JButton(esEdicion ? "Actualizar" : "Guardar");  // Texto según modo
        btnCancelar = new JButton("Cancelar");
        estilizarBoton(btnGuardar, new Color(76, 175, 80));  // Botón verde
        estilizarBoton(btnCancelar, new Color(244, 67, 54));  // Botón rojo
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);
        panelPrincipal.add(pnlBotones, BorderLayout.SOUTH);  // Al sur del principal

        add(panelPrincipal);  // Agrega panel principal al JDialog

        // Si es edición, rellena campos con datos de la fila
        if (esEdicion && rowIndex >= 0) {
            txtNumero.setText(modeloTabla.getValueAt(rowIndex, 0).toString());
            txtPlaca.setText(modeloTabla.getValueAt(rowIndex, 1).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(rowIndex, 2).toString());
            txtIdCliente.setText(modeloTabla.getValueAt(rowIndex, 3).toString());
        }

        // Acción del botón Guardar/Actualizar
        btnGuardar.addActionListener(e -> {
            Object[] datos = {
                txtNumero.getText().trim(),  // Número equipo
                txtPlaca.getText().trim(),   // Placa
                txtDescripcion.getText().trim(), // Descripción
                txtIdCliente.getText().trim(),   // ID Cliente
                "Acciones"  // Placeholder para columna de acciones
            };
            if (esEdicion && rowIndex >= 0) {
                // Actualiza fila existente
            	for (int i = 0; i < datos.length; i++) {
                    modeloTabla.setValueAt(datos[i], rowIndex, i);
                }
            } else {
                // Agrega nueva fila
                modeloTabla.addRow(datos);
            }
            dispose();  // Cierra el diálogo
        });
        btnCancelar.addActionListener(e -> dispose());  // Cierra sin guardar
    }

    // Método auxiliar para agregar etiquetas y campos al GridBagLayout
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, String etiqueta, Component campo, Font labelFont) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(labelFont);  // Aplica fuente a etiqueta
        panel.add(lbl, gbc);  // Coloca etiqueta
        gbc.gridx = 1;  // Mueve a siguiente columna
        panel.add(campo, gbc);  // Coloca campo
        gbc.gridx = 0;  // Resetea columna
        gbc.gridy++;  // Sube una fila
    }

    // Crea un JTextField estilizado
    private JTextField crearCampo(Font fuente) {
        JTextField txt = new JTextField(20);  // Ancho de 20 caracteres
        txt.setFont(fuente);
        return txt;
    }

    // Aplica estilo de colores y fuente a un botón
    private void estilizarBoton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.white);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 30));
    }
}

