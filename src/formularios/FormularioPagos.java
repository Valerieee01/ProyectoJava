package formularios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
// import javax.swing.table.DefaultTableModel; // Ya no es necesario si el panel padre no lo requiere

import DAO.ReportesPagosDAO; // Asumo que tienes este DAO para la tabla Pagos
import menuInicialAdministrador.panelReportes; // Asumo que este es el panel que mostrará los pagos

import modelos.Pagos;
import modelos.Pagos.EstadoPago; // Importar explícitamente el enum EstadoPago
import util.ConnectionADMIN; // Clase para obtener la conexión a la base de datos

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date; // java.util.Date para JDateChooser
import java.math.BigDecimal; // Para manejar DECIMAL
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat; // Para parsear/formatear fechas si es necesario (ya lo tienes)
import java.text.ParseException; // Para manejar errores de parseo de fecha

public class FormularioPagos extends JDialog {

    private static final long serialVersionUID = 1L;

    // Campos de entrada para los datos del Pago
    private JTextField txtIdPago; // Solo para visualización en modo edición
    private JTextField txtIdCliente;
    private JTextField txtIdMantenimiento;
    private JTextField txtDetalle;
    private JTextField txtValorTrabajo;
    private JTextField txtValorPagado;
    // private JTextField txtValorMora; // NO es necesario, es generado
    private JComboBox<EstadoPago> cmbEstadoPago;
    private JDateChooser dateChooserFechaFacturacion;
    private JTextField txtDiasPlazo;
    // private JDateChooser dateChooserFechaVencimiento; // NO es necesario, es generado

    private JButton btnGuardar, btnCancelar;

    private ReportesPagosDAO pagosDAO; // Renombrado a pagosDAO
    private panelReportes panelReportesRef; // Renombrado para reflejar su propósito
    private boolean esEdicion;
    private Pagos pagoOriginal = null; // Almacena el objeto original en modo edición

    // Constructor para MODO AGREGAR
    public FormularioPagos(Window parent, ReportesPagosDAO pagosDAO, panelReportes panelReportesRef) {
        super(parent, "Nuevo Pago", ModalityType.APPLICATION_MODAL); // Título de la ventana
        this.pagosDAO = pagosDAO;
        this.panelReportesRef = panelReportesRef;
        this.esEdicion = false;
        configurarFormulario();
    }

    // Constructor para MODO EDITAR
    public FormularioPagos(Window parent, ReportesPagosDAO pagosDAO, panelReportes panelReportesRef, Pagos pagosParaEditar) {
        super(parent, "Editar Pago", ModalityType.APPLICATION_MODAL); // Título de la ventana
        this.pagosDAO = pagosDAO;
        this.panelReportesRef = panelReportesRef;
        this.esEdicion = true;
        this.pagoOriginal = pagosParaEditar; // Guarda el objeto para precargar los campos
        configurarFormulario();
        cargarDatosPago(pagoOriginal); // Nuevo método para precargar datos
    }

    private void configurarFormulario() {
        setSize(600, 650); // Ajustar tamaño para más campos
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.decode("#f4f6f8"));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 25, 15, 25));
        panelPrincipal.setBackground(Color.decode("#f4f6f8"));

        JLabel titulo = new JLabel(esEdicion ? "Editar Pago" : "Agregar Pago");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.white);
        panelFormulario.setBorder(new TitledBorder(
                BorderFactory.createLineBorder(Color.gray, 1, true),
                "Datos del Pago",
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

        // --- Campos del Formulario ---

        // ID Pago (Solo visible y no editable en modo edición)
        txtIdPago = crearCampo(campoFont);
        if (esEdicion) {
            txtIdPago.setEditable(false);
            txtIdPago.setBackground(new Color(240, 240, 240));
            agregarCampo(panelFormulario, gbc, "ID Pago:", txtIdPago, labelFont);
        } else {
            // No agregamos este campo en modo agregar, ya que es auto-incremental.
            // Si tu diseño requiere que se vea algo, podrías poner un JLabel "Generado automáticamente"
            // o dejar un campo no editable y vacío.
        }

        txtIdCliente = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "ID Cliente:", txtIdCliente, labelFont);

        txtIdMantenimiento = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "ID Mantenimiento:", txtIdMantenimiento, labelFont);

        txtDetalle = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "Detalle:", txtDetalle, labelFont);

        txtValorTrabajo = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "Valor Trabajo:", txtValorTrabajo, labelFont);

        txtValorPagado = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "Valor Pagado:", txtValorPagado, labelFont);

        // JComboBox para EstadoPago
        cmbEstadoPago = new JComboBox<>(EstadoPago.values());
        cmbEstadoPago.setFont(campoFont);
        agregarCampo(panelFormulario, gbc, "Estado Pago:", cmbEstadoPago, labelFont);

        // JDateChooser para Fecha Facturación
        dateChooserFechaFacturacion = new JDateChooser();
        dateChooserFechaFacturacion.setFont(campoFont);
        dateChooserFechaFacturacion.setPreferredSize(new Dimension(200, 28));
        dateChooserFechaFacturacion.setDateFormatString("dd/MM/yyyy");
        agregarCampo(panelFormulario, gbc, "Fecha Facturación:", dateChooserFechaFacturacion, labelFont);

        txtDiasPlazo = crearCampo(campoFont);
        agregarCampo(panelFormulario, gbc, "Días Plazo:", txtDiasPlazo, labelFont);

        // Los campos como valor_mora, fecha_vencimiento, fecha_registro,
        // y fecha_actualizacion NO se añaden aquí porque son generados por la DB.
        
        gbc.weightx = 0.7; // Ajustar peso para la segunda columna (campos de entrada)
        
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
        btnGuardar.addActionListener(e -> guardarOActualizarPago());

        // Lógica del botón Cancelar
        btnCancelar.addActionListener(e -> dispose());
    }

    /**
     * Carga los datos de un objeto Pagos en los campos del formulario.
     * Este método solo se llama en modo edición.
     * @param pago El objeto Pagos con los datos a precargar.
     */
    private void cargarDatosPago(Pagos pago) {
        if (pago != null) {
            txtIdPago.setText(String.valueOf(pago.getIdPago()));
            txtIdCliente.setText(String.valueOf(pago.getIdCliente()));
            txtIdMantenimiento.setText(String.valueOf(pago.getIdMantenimiento()));
            txtDetalle.setText(pago.getDetalle());
            txtValorTrabajo.setText(pago.getValorTrabajo() != null ? pago.getValorTrabajo().toPlainString() : "");
            txtValorPagado.setText(pago.getValorPagado() != null ? pago.getValorPagado().toPlainString() : "");
            
            // Seleccionar el valor correcto en el JComboBox
            cmbEstadoPago.setSelectedItem(pago.getEstadoPago());

            dateChooserFechaFacturacion.setDate(pago.getFechaFacturacion()); // java.sql.Date es subclase de java.util.Date
            txtDiasPlazo.setText(String.valueOf(pago.getDiasPlazo()));
            // No cargamos valor_mora, fecha_vencimiento, fecha_registro, fecha_actualizacion
            // porque son generados por la DB o solo para lectura desde la DB.
        }
    }

    /**
     * Maneja la lógica de guardar un nuevo pago o actualizar uno existente.
     */
    private void guardarOActualizarPago() {
        // 1. Obtener y validar datos de los campos
        String strIdCliente = txtIdCliente.getText().trim();
        String strIdMantenimiento = txtIdMantenimiento.getText().trim();
        String detalle = txtDetalle.getText().trim();
        String strValorTrabajo = txtValorTrabajo.getText().trim();
        String strValorPagado = txtValorPagado.getText().trim();
        EstadoPago estadoPago = (EstadoPago) cmbEstadoPago.getSelectedItem();
        Date fechaFacturacionUtil = dateChooserFechaFacturacion.getDate();
        String strDiasPlazo = txtDiasPlazo.getText().trim();

        // Validaciones de campos obligatorios
        if (strIdCliente.isEmpty() || strIdMantenimiento.isEmpty() || detalle.isEmpty() ||
            strValorTrabajo.isEmpty() || strValorPagado.isEmpty() || estadoPago == null ||
            fechaFacturacionUtil == null || strDiasPlazo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCliente, idMantenimiento, diasPlazo;
        BigDecimal valorTrabajo, valorPagado;

        try {
            idCliente = Integer.parseInt(strIdCliente);
            idMantenimiento = Integer.parseInt(strIdMantenimiento);
            diasPlazo = Integer.parseInt(strDiasPlazo);
            valorTrabajo = new BigDecimal(strValorTrabajo);
            valorPagado = new BigDecimal(strValorPagado);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los campos ID de Cliente, ID de Mantenimiento, Días Plazo, Valor Trabajo y Valor Pagado deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir java.util.Date a java.sql.Date
        java.sql.Date sqlFechaFacturacion = new java.sql.Date(fechaFacturacionUtil.getTime());

        // 2. Crear el objeto Pagos
        Pagos pago;
        if (esEdicion) {
            // En modo edición, usamos el ID del pago original
            pago = new Pagos(pagoOriginal.getIdPago(), idCliente, idMantenimiento, detalle,
                             valorTrabajo, valorPagado, pagoOriginal.getValorMora(), 
                             estadoPago, sqlFechaFacturacion, diasPlazo, pagoOriginal.getFechaVencimiento()); 
        } else {
            pago = new Pagos(idCliente, idMantenimiento, detalle, valorTrabajo, valorPagado, estadoPago, sqlFechaFacturacion, diasPlazo); // fechaVencimiento es null al insertar
        }

        // 3. Persistir los datos usando el DAO
        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            if (esEdicion) {
                boolean modificado = pagosDAO.modificarPago(pago, conn);
                if (modificado) {
                    JOptionPane.showMessageDialog(this, "Pago modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo modificar el pago. ¿Existe el ID?", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                int idGenerado = pagosDAO.agregarPago(pago, conn);
                if (idGenerado != -1) {
                    JOptionPane.showMessageDialog(this, "Pago agregado exitosamente (ID: " + idGenerado + ").", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo agregar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // 4. Refrescar la tabla en el panel principal y cerrar el formulario
            if (panelReportesRef != null) {
                panelReportesRef.cargarPagosEnTabla(); // Asumo que tienes un método así en panelReportes
            }
            dispose();

        } catch (SQLException ex) {
            // Mejorar el mensaje de error para claves foráneas
            if (ex.getMessage().contains("FOREIGN KEY (`id_cliente`) REFERENCES `clientes`") ||
                ex.getMessage().contains("FOREIGN KEY (`id_mantenimiento`) REFERENCES `mantenimientos`")) {
                JOptionPane.showMessageDialog(this,
                                              "Error de base de datos: El ID de Cliente o el ID de Mantenimiento no existen.",
                                              "Error de Clave Foránea",
                                              JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                                              "Error de base de datos: " + ex.getMessage(),
                                              "Error SQL",
                                              JOptionPane.ERROR_MESSAGE);
            }
            ex.printStackTrace();
        }
    }

    // Métodos auxiliares
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, String etiqueta, Component campo, Font labelFont) {
        double originalWeightX = gbc.weightx;

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(labelFont);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(campo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = originalWeightX;
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
        btn.setPreferredSize(new Dimension(120, 35)); // Botones un poco más grandes
        btn.setBorder(BorderFactory.createLineBorder(bg.darker(), 1));
    }
}