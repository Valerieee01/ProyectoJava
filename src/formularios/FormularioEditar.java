package formularios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import DAO.EquiposDAO;
import menuInicialAdministrador.panelEquipos; 
import modelos.Equipo; 
import util.ConnectionADMIN;

import java.awt.*;
import java.sql.Connection; // Importar Connection
import java.sql.SQLException; // Manejar excepciones de SQL

public class FormularioEditar extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField txtNumero, txtPlaca, txtDescripcion, txtIdCliente;
    private JButton btnGuardar, btnCancelar;

    private DefaultTableModel modeloTabla; // Referencia al modelo de la tabla
    private EquiposDAO equiposDAO;        // Referencia  DAO
    private panelEquipos panelEquiposRef; // Referencia al panel para refrescar la tabla
    private boolean esEdicion;            // Indica si es modo edición
    private int filaSeleccionada;         // Índice de la fila en modo edición
    private int idEquipoAEditar = -1;     // ID del equipo real si estamos en modo edición (de la DB)


    // Constructor para MODO AGREGAR
    public FormularioEditar(Window parent, DefaultTableModel modeloTabla, EquiposDAO equiposDAO, panelEquipos panelEquiposRef) {
        super(parent, "Agregar Equipo", ModalityType.APPLICATION_MODAL);
        this.modeloTabla = modeloTabla;
        this.equiposDAO = equiposDAO;
        this.panelEquiposRef = panelEquiposRef;
        this.esEdicion = false;
        configurarFormulario(); // Llama al método de configuración, ahora sin parámetros booleanos o de índice
    }

    // Constructor para MODO EDITAR
    public FormularioEditar(Window parent, DefaultTableModel modeloTabla, EquiposDAO equiposDAO, panelEquipos panelEquiposRef, int filaSeleccionada) {
        super(parent, "Editar Equipo", ModalityType.APPLICATION_MODAL);
        this.modeloTabla = modeloTabla;
        this.equiposDAO = equiposDAO;
        this.panelEquiposRef = panelEquiposRef;
        this.esEdicion = true;
        this.filaSeleccionada = filaSeleccionada;
        configurarFormulario(); // Llama al método de configuración
    }

    // Método interno que arma la interfaz y lógica (ahora sin parámetros)
    private void configurarFormulario() {
        setSize(550, 480);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.decode("#f4f6f8"));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 25, 15, 25));
        panelPrincipal.setBackground(Color.decode("#f4f6f8"));

        JLabel titulo = new JLabel(esEdicion ? "Editar Equipo" : "Agregar Equipo");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.white);
        panelFormulario.setBorder(new TitledBorder(
            BorderFactory.createLineBorder(Color.gray, 1, true),
            "Datos del Equipo",
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

        agregarCampo(panelFormulario, gbc, "Número de Equipo:", txtNumero = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Placa:", txtPlaca = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "Descripción:", txtDescripcion = crearCampo(campoFont), labelFont);
        agregarCampo(panelFormulario, gbc, "ID Cliente:", txtIdCliente = crearCampo(campoFont), labelFont);

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

        // Si es edición, rellena campos con datos de la fila de la tabla
        if (esEdicion && filaSeleccionada >= 0) {
            // Se asume que la columna 0 es el ID del equipo en la tabla
            // Y el resto de columnas corresponden a numero, placa, descripcion, id_cliente
            idEquipoAEditar = (int) modeloTabla.getValueAt(filaSeleccionada, 0); // Captura el ID real del equipo de la DB
            txtNumero.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtPlaca.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtIdCliente.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString()); // ID Cliente ahora es columna 4
        }
        
        // --- Lógica del botón Guardar/Actualizar ---
        btnGuardar.addActionListener(e -> {
            // Validar entrada del usuario (ej. que ID Cliente sea un número)
            String numeroEquipo = txtNumero.getText().trim();
            String placa = txtPlaca.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            int idCliente = -1; // Valor por defecto para indicar error

            if (numeroEquipo.isEmpty() || placa.isEmpty() || descripcion.isEmpty() || txtIdCliente.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return; // Detener la ejecución si hay campos vacíos
            }

            try {
                idCliente = Integer.parseInt(txtIdCliente.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID Cliente debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return; // Detener la ejecución si el ID Cliente no es un número
            }

            // Crear o actualizar el objeto Equipo
            Equipo equipo = new Equipo(numeroEquipo, placa, descripcion, idCliente);

            try (Connection conn = ConnectionADMIN.getConnectionADMIN()) { // Obtener la conexión aquí
                if (esEdicion) {
                    equipo.setIdEquipo(idEquipoAEditar); // Establecer el ID del equipo a editar
                    equiposDAO.modificarEquipo(equipo, conn); // Pasar la conexión
                    JOptionPane.showMessageDialog(this, "Equipo modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    equiposDAO.agregarEquipo(equipo, conn); // Pasar la conexión
                    JOptionPane.showMessageDialog(this, "Equipo agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
                
                // Refrescar la tabla en el panel principal
                if (panelEquiposRef != null) {
                    panelEquiposRef.cargarEquiposEnTabla();
                }
                dispose(); // Cerrar el diálogo después de la operación exitosa

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                                              "Error de base de datos: " + ex.getMessage(), 
                                              "Error SQL", 
                                              JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Imprime el stack trace para depuración
            }
        });
        
        btnCancelar.addActionListener(e -> dispose()); // Cierra sin guardar
    }

    // Métodos auxiliares
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

    private void estilizarBoton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.white);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 30));
    }
}
