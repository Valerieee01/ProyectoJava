package formularios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel; // Aunque no se usa directamente para el CRUD, podría ser útil para referencia.

import DAO.MantenimientosDAO;
import menuInicialAdministrador.panelMantenimientos;
import modelos.Mantenimiento;
import modelos.Mantenimiento.TipoMantenimiento; // Importar explícitamente el enum
import util.ConnectionADMIN;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date; // java.util.Date para JDateChooser (importante)
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat; // Para el parseo y formateo de fechas
import java.text.ParseException; // Para manejar errores de parseo de fecha

public class FormularioMantenimiento extends JDialog {

    private static final long serialVersionUID = 1L; // Es buena práctica declarar como final

    private JTextField txtIdMantenimiento;
    private JTextField txtIdEquipo; // Renombrados para consistencia
    private JTextField txtDescripcion;
    private JTextField txtEncargado;
    private JComboBox<TipoMantenimiento> cmbTipoMto;
    private JDateChooser dateChooserFechaMantenimiento;
    private JTextField txtObservaciones;
    private JTextField txtIdEmpleado;
    private JButton btnGuardar, btnCancelar;

    // Se eliminó DefaultTableModel modeloTabla ya que no es necesario aquí.
    private MantenimientosDAO mantenimientosDAO;
    private panelMantenimientos panelMantenimientosRef;
    private boolean esEdicion;
    private Mantenimiento mantenimientoOriginal = null; // Almacena el objeto original en modo edición

    // Constructor para MODO AGREGAR (simplificado)
    public FormularioMantenimiento(Window parent, MantenimientosDAO mantenimientosDAO, panelMantenimientos panelMantenimientosRef) {
        super(parent, "Nuevo Mantenimiento", ModalityType.APPLICATION_MODAL);
        this.mantenimientosDAO = mantenimientosDAO;
        this.panelMantenimientosRef = panelMantenimientosRef;
        this.esEdicion = false;
        configurarFormulario();
    }

    // Constructor para MODO EDITAR (más limpio: se pasa el objeto Mantenimiento)
    public FormularioMantenimiento(Window parent, MantenimientosDAO mantenimientosDAO, panelMantenimientos panelMantenimientosRef, Mantenimiento mantenimientoParaEditar) {
        super(parent, "Editar Mantenimiento", ModalityType.APPLICATION_MODAL);
        this.mantenimientosDAO = mantenimientosDAO;
        this.panelMantenimientosRef = panelMantenimientosRef;
        this.esEdicion = true;
        this.mantenimientoOriginal = mantenimientoParaEditar; // Guarda el objeto para precargar los campos
        configurarFormulario();
        cargarDatosMantenimiento(mantenimientoParaEditar); // Nuevo método para precargar datos
    }

    private void configurarFormulario() {
        setSize(550, 600); // Aumentado ligeramente para mejor espacio
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.decode("#f4f6f8")); // Un color de fondo claro y moderno

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 25, 15, 25));
        panelPrincipal.setBackground(Color.decode("#f4f6f8"));

        JLabel titulo = new JLabel(esEdicion ? "Editar Mantenimiento" : "Agregar Mantenimiento");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.white);
        panelFormulario.setBorder(new TitledBorder(
                BorderFactory.createLineBorder(Color.gray, 1, true),
                "Datos del Mantenimiento",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12); // Ajustar insets para mejor espaciado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3; // Distribución de ancho para etiquetas
        
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font campoFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Inicializar todos los JTextField y JDateChooser/JComboBox
        txtIdMantenimiento = crearCampo(campoFont);
        if (esEdicion) {
            txtIdMantenimiento.setEditable(false); // No se permite editar el ID del mantenimiento
            txtIdMantenimiento.setBackground(new Color(240, 240, 240)); // Fondo gris para indicar que no es editable
        } else {
             // En modo agregar, el ID de Mantenimiento no es necesario (se genera en la DB)
             // Puedes ocultarlo o dejarlo no editable y vacío.
             txtIdMantenimiento.setVisible(false); // Ocultar el campo si es autoincremental
             // lblIdMantenimiento también se debería ocultar o no agregar
        }
        agregarCampo(panelFormulario, gbc, "ID Mantenimiento:", txtIdMantenimiento, labelFont);

        txtIdEquipo = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "ID Equipo:", txtIdEquipo, labelFont);

        txtDescripcion = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "Descripción Trabajo:", txtDescripcion, labelFont);

        txtEncargado = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "Encargado:", txtEncargado, labelFont);

        // Configuración para TipoMantenimiento (JComboBox)
        cmbTipoMto = new JComboBox<>(TipoMantenimiento.values());
        cmbTipoMto.setFont(campoFont);
        agregarCampo(panelFormulario, gbc, "Tipo Mantenimiento:", cmbTipoMto, labelFont);

        // Configuración para FechaMantenimiento (JDateChooser)
        dateChooserFechaMantenimiento = new JDateChooser();
        dateChooserFechaMantenimiento.setFont(campoFont);
        dateChooserFechaMantenimiento.setPreferredSize(new Dimension(200, 28));
        dateChooserFechaMantenimiento.setDateFormatString("dd/MM/yyyy");
        agregarCampo(panelFormulario, gbc, "Fecha Mantenimiento:", dateChooserFechaMantenimiento, labelFont);

        txtObservaciones = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "Observaciones:", txtObservaciones, labelFont);

        txtIdEmpleado = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "ID Empleado:", txtIdEmpleado, labelFont);
        
        // Ajustar el peso para la segunda columna (campos de entrada)
        gbc.weightx = 0.7;

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        pnlBotones.setBackground(Color.decode("#f4f6f8"));
        btnGuardar = new JButton(esEdicion ? "Actualizar" : "Guardar");
        btnCancelar = new JButton("Cancelar");
        estilizarBoton(btnGuardar, new Color(76, 175, 80)); // Verde
        estilizarBoton(btnCancelar, new Color(244, 67, 54)); // Rojo
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);
        panelPrincipal.add(pnlBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Lógica del botón Guardar/Actualizar
        btnGuardar.addActionListener(e -> guardarOActualizarMantenimiento());

        // Lógica del botón Cancelar
        btnCancelar.addActionListener(e -> dispose());
    }

    /**
     * Carga los datos de un objeto Mantenimiento en los campos del formulario.
     * Este método solo se llama en modo edición.
     * @param mantenimiento El objeto Mantenimiento con los datos a precargar.
     */
    private void cargarDatosMantenimiento(Mantenimiento mantenimiento) {
        if (mantenimiento != null) {
            txtIdMantenimiento.setText(String.valueOf(mantenimiento.getIdMantenimiento()));
            txtIdEquipo.setText(String.valueOf(mantenimiento.getIdEquipo()));
            txtDescripcion.setText(mantenimiento.getDescripcionTrabajo());
            txtEncargado.setText(mantenimiento.getEncargado());

            // Seleccionar el valor correcto en el JComboBox
            cmbTipoMto.setSelectedItem(mantenimiento.getTipoMantenimiento());

            dateChooserFechaMantenimiento.setDate(mantenimiento.getFechaMantenimiento()); // java.sql.Date es subclase de java.util.Date
            txtObservaciones.setText(mantenimiento.getObservaciones());
            txtIdEmpleado.setText(String.valueOf(mantenimiento.getIdEmpleado()));
        }
    }

    /**
     * Maneja la lógica de guardar un nuevo mantenimiento o actualizar uno existente.
     */
    private void guardarOActualizarMantenimiento() {
        // 1. Obtener y validar datos de los campos
        String strIdEquipo = txtIdEquipo.getText().trim();
        String descripcionTrabajo = txtDescripcion.getText().trim();
        String encargado = txtEncargado.getText().trim();
        TipoMantenimiento tipoMto = (TipoMantenimiento) cmbTipoMto.getSelectedItem();
        String observaciones = txtObservaciones.getText().trim();
        String strIdEmpleado = txtIdEmpleado.getText().trim();
        Date fechaMantenimientoUtil = dateChooserFechaMantenimiento.getDate();

        // Validaciones de campos obligatorios
        if (strIdEquipo.isEmpty() || descripcionTrabajo.isEmpty() || encargado.isEmpty() ||
            tipoMto == null || observaciones.isEmpty() || strIdEmpleado.isEmpty() ||
            fechaMantenimientoUtil == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEquipo;
        int idEmpleado;

        try {
            idEquipo = Integer.parseInt(strIdEquipo);
            idEmpleado = Integer.parseInt(strIdEmpleado);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los campos ID de Equipo e ID de Empleado deben ser números enteros válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir java.util.Date a java.sql.Date
        java.sql.Date sqlFechaMantenimiento = new java.sql.Date(fechaMantenimientoUtil.getTime());

        // 2. Crear el objeto Mantenimiento
        Mantenimiento mantenimiento;
        if (esEdicion) {
            // En modo edición, usamos el ID del mantenimiento original
            mantenimiento = new Mantenimiento(mantenimientoOriginal.getIdMantenimiento(), idEquipo, descripcionTrabajo, encargado,
                                              tipoMto, sqlFechaMantenimiento, observaciones, idEmpleado);
        } else {
            // En modo agregar, el ID de Mantenimiento se espera que sea auto-generado en la DB
            mantenimiento = new Mantenimiento(idEquipo, descripcionTrabajo, encargado,
                                              tipoMto, sqlFechaMantenimiento, observaciones, idEmpleado);
        }

        // 3. Persistir los datos usando el DAO
        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            if (esEdicion) {
                boolean modificado = mantenimientosDAO.modificarMantenimiento(mantenimiento, conn);
                if (modificado) {
                    JOptionPane.showMessageDialog(this, "Mantenimiento modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo modificar el mantenimiento. ¿Existe el ID?", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                int idGenerado = mantenimientosDAO.agregarMantenimiento(mantenimiento, conn); // Asume que devuelve el ID
                if (idGenerado != -1) {
                    JOptionPane.showMessageDialog(this, "Mantenimiento agregado exitosamente (ID: " + idGenerado + ").", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                     JOptionPane.showMessageDialog(this, "No se pudo agregar el mantenimiento.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // 4. Refrescar la tabla en el panel principal y cerrar el formulario
            if (panelMantenimientosRef != null) {
                panelMantenimientosRef.cargarMantenimientosEnTabla();
            }
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                                          "Error de base de datos al guardar/actualizar: " + ex.getMessage(),
                                          "Error SQL",
                                          JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Métodos auxiliares (sin cambios significativos, solo ajustes menores para consistencia)
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, String etiqueta, Component campo, Font labelFont) {
        // Guardar gbc original para restaurar weightx
        double originalWeightX = gbc.weightx;

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(labelFont);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Dar más peso a la columna del campo
        panel.add(campo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = originalWeightX; // Restaurar el peso para la siguiente etiqueta
    }

    private JTextField crearCampo(Font fuente) {
        JTextField txt = new JTextField(20);
        txt.setFont(fuente);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.lightGray, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8) // Padding interno
        ));
        return txt;
    }

    private void estilizarBoton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.white);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 30));
        btn.setBorder(BorderFactory.createLineBorder(bg.darker(), 1)); // Borde sutil
    }
}