package menuInicialAdministrador;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;

// iText imports
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.element.Cell;

import DAO.ReportesPagosDAO;
import formularios.FormularioPagos; 
import modelos.Pagos;
import modelos.Pagos.EstadoPago; 
import util.ConnectionADMIN; 

public class panelReportes extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable tablaPagos; // Renombrado para claridad
    private DefaultTableModel modeloTablaPagos; // Renombrado
    private JTextField txtFiltroIdCliente;
    private JTextField txtFiltroIdMantenimiento;
    private JComboBox<String> comboFiltroEstado;
    private TableRowSorter<DefaultTableModel> sorter; // Se puede usar para filtrar directamente en la vista
    
    private ReportesPagosDAO pagosDAO; // Instancia del DAO para interactuar con la DB

    public panelReportes() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(255, 235, 215)); // Color de fondo claro

        // --- Inicializar el DAO ---
        pagosDAO = new ReportesPagosDAO();

        // --- Panel Superior: Filtros ---
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.setBackground(new Color(255, 200, 160)); // Color de fondo para filtros

        txtFiltroIdCliente = new JTextField(8);
        txtFiltroIdMantenimiento = new JTextField(8);
        comboFiltroEstado = new JComboBox<>(new String[]{"Todos", "vencido", "pagado", "mora"});

        panelFiltros.add(new JLabel("ID Cliente:"));
        panelFiltros.add(txtFiltroIdCliente);
        panelFiltros.add(new JLabel("ID Mantenimiento:"));
        panelFiltros.add(txtFiltroIdMantenimiento);
        panelFiltros.add(new JLabel("Estado:"));
        panelFiltros.add(comboFiltroEstado);

        JButton btnAplicarFiltro = new JButton("Aplicar Filtro");
        btnAplicarFiltro.setBackground(new Color(255, 255, 255));
        btnAplicarFiltro.setForeground(new Color(255, 128, 64));
        btnAplicarFiltro.addActionListener(e -> aplicarFiltros());
        panelFiltros.add(btnAplicarFiltro);

        add(panelFiltros, BorderLayout.NORTH);

        // --- Configuración de la Tabla de Pagos ---
        // Columnas visibles en la tabla (no todas las de la DB, ya que algunas son generadas y podrían no ser útiles aquí)
        // Incluimos valor_mora y fecha_vencimiento que son GENERADAS pero útiles para mostrar.
        String[] columnas = {
            "ID Pago", "ID Cliente", "ID Mantenimiento", "Detalle", "Valor Trabajo",
            "Valor Pagado", "Valor Mora", "Estado", "Fecha Facturación", "Días Plazo", "Fecha Vencimiento"
            // Se excluyen fecha_registro y fecha_actualizacion para simplificar la vista, pero se pueden añadir.
        };
        modeloTablaPagos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas de la tabla no son editables directamente
            }
        };
        tablaPagos = new JTable(modeloTablaPagos);
        sorter = new TableRowSorter<>(modeloTablaPagos);
        tablaPagos.setRowSorter(sorter);

        // Renderizador de celdas para alternar colores de fila y centrar algunas
        tablaPagos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 220, 190) : new Color(255, 240, 220));
                if (isSelected) {
                    c.setBackground(new Color(255, 170, 100)); // Color de selección
                }
                
                // Alinear contenido de columnas numéricas/fecha al centro o derecha
                if (column == 0 || column == 1 || column == 2 || column == 4 || column == 5 || column == 6 || column == 8 || column == 9 || column == 10) {
                    setHorizontalAlignment(SwingConstants.CENTER); // O SwingConstants.RIGHT para números
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }
                return c;
            }
        });

        add(new JScrollPane(tablaPagos), BorderLayout.CENTER);

        // --- Panel Inferior: Botones de Acción ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(new Color(255, 200, 160));

        JButton btnNuevo = new JButton("Nuevo Pago");
        btnNuevo.setBackground(new Color(255, 255, 255));
        btnNuevo.setForeground(new Color(255, 128, 64));
        btnNuevo.addActionListener(e -> abrirFormularioNuevoPago());

        JButton btnEditar = new JButton("Editar Pago");
        btnEditar.setBackground(new Color(255, 255, 255));
        btnEditar.setForeground(new Color(255, 128, 64));
        btnEditar.addActionListener(e -> editarPagoSeleccionado());

        JButton btnEliminar = new JButton("Eliminar Pago");
        btnEliminar.setBackground(new Color(255, 255, 255));
        btnEliminar.setForeground(new Color(255, 128, 64));
        btnEliminar.addActionListener(e -> eliminarPagoSeleccionado());
        
        JButton btnGenerarPDF = new JButton("Generar PDF");
        btnGenerarPDF.setBackground(new Color(255, 255, 255));
        btnGenerarPDF.setForeground(new Color(255, 128, 64));
        btnGenerarPDF.addActionListener(e -> generarPdfPagoSeleccionado());

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGenerarPDF);

        add(panelBotones, BorderLayout.SOUTH);

        // --- Cargar datos iniciales al arrancar el panel ---
        cargarPagosEnTabla();
    }

    /**
     * Abre el formulario para añadir un nuevo pago.
     */
    private void abrirFormularioNuevoPago() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        // Pasa solo el DAO y la referencia a este panel.
        FormularioPagos dialog = new FormularioPagos(parent, pagosDAO, this);
        dialog.setVisible(true);
    }

    /**
     * Edita el pago seleccionado en la tabla.
     * Recupera el ID del pago de la fila seleccionada y abre el formulario en modo edición.
     */
    private void editarPagoSeleccionado() {
        int filaSeleccionada = tablaPagos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un pago de la tabla para editar.", "Ningún Pago Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convertir la fila de la vista a la fila del modelo (importante si hay ordenamiento)
        int idPagoseleccionado = (int) modeloTablaPagos.getValueAt(tablaPagos.convertRowIndexToModel(filaSeleccionada), 0);

        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            Pagos pagoAEditar = pagosDAO.obtenerPagoPorId(idPagoseleccionado, conn);
            if (pagoAEditar != null) {
                Window parent = SwingUtilities.getWindowAncestor(this);
                // Pasa el objeto Pagos completo al formulario para que lo cargue
                FormularioPagos dialog = new FormularioPagos(parent, pagosDAO, this, pagoAEditar);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar el pago con ID: " + idPagoseleccionado, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener los datos del pago para editar: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Elimina el pago seleccionado de la tabla y la base de datos.
     */
    private void eliminarPagoSeleccionado() {
        int filaSeleccionada = tablaPagos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un pago de la tabla para eliminar.", "Ningún Pago Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este pago?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            int idPagoseleccionado = (int) modeloTablaPagos.getValueAt(tablaPagos.convertRowIndexToModel(filaSeleccionada), 0);

            try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
                boolean eliminado = pagosDAO.eliminarPago(idPagoseleccionado, conn);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Pago eliminado exitosamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    cargarPagosEnTabla(); // Refrescar la tabla
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el pago.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el pago de la base de datos: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Aplica los filtros actuales (búsqueda por ID Cliente, ID Mantenimiento y Estado) a la tabla.
     */
    private void aplicarFiltros() {
        // En lugar de recargar todos los datos y filtrarlos en Java,
        // podemos aplicar los filtros directamente al TableRowSorter
        // Esto es más eficiente si ya tienes todos los datos cargados.
        // Si la cantidad de datos es muy grande, deberías pasar los filtros al DAO.

        sorter.setRowFilter(new RowFilter<DefaultTableModel, Object>() {
            public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                String idClienteStr = txtFiltroIdCliente.getText().trim();
                String idMantenimientoStr = txtFiltroIdMantenimiento.getText().trim();
                String estadoSeleccionado = (String) comboFiltroEstado.getSelectedItem();

                String rowIdCliente = entry.getStringValue(1); 
                String rowIdMantenimiento = entry.getStringValue(2); 
                String rowEstado = entry.getStringValue(7); 

                boolean matchesIdCliente = idClienteStr.isEmpty() || rowIdCliente.toLowerCase().contains(idClienteStr.toLowerCase());
                boolean matchesIdMantenimiento = idMantenimientoStr.isEmpty() || rowIdMantenimiento.toLowerCase().contains(idMantenimientoStr.toLowerCase());
                boolean matchesEstado = estadoSeleccionado.equals("Todos") || rowEstado.equalsIgnoreCase(estadoSeleccionado);

                return matchesIdCliente && matchesIdMantenimiento && matchesEstado;
            }
        });
    }


    /**
     * Método para cargar todos los pagos desde la base de datos
     * y mostrarlos en la JTable.
     * Este método limpia la tabla actual y la repobla con datos frescos de la DB.
     */
    public void cargarPagosEnTabla() {
        modeloTablaPagos.setRowCount(0); // Limpia todas las filas existentes

        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            List<Pagos> pagos = pagosDAO.obtenerTodosLosPagos(conn); // Usar el método correcto del DAO

            // Antes de añadir las filas, elimina cualquier filtro aplicado por el sorter
            // para que la tabla se cargue completa y el filtro se aplique después si es necesario.
            sorter.setRowFilter(null);

            for (Pagos pago : pagos) {
                // Añade una nueva fila al modelo de la tabla con los datos del pago.
                // Asegúrate de que el orden de los datos coincida con el orden de las columnas.
                modeloTablaPagos.addRow(new Object[]{
                    pago.getIdPago(),
                    pago.getIdCliente(),
                    pago.getIdMantenimiento(),
                    pago.getDetalle(),
                    pago.getValorTrabajo(),
                    pago.getValorPagado(),
                    pago.getValorMora(), // Incluir la mora calculada por la DB
                    pago.getEstadoPago(),
                    pago.getFechaFacturacion(),
                    pago.getDiasPlazo(),
                    pago.getFechaVencimiento() // Incluir la fecha de vencimiento generada por la DB
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los Pagos de la base de datos: " + e.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Genera un documento PDF para el pago seleccionado en la tabla.
     * Permite al usuario elegir la ubicación de guardado.
     */
    private void generarPdfPagoSeleccionado() {
        int filaSeleccionada = tablaPagos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un pago de la tabla para generar el PDF.", "Ningún Pago Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPagoseleccionado = (int) modeloTablaPagos.getValueAt(tablaPagos.convertRowIndexToModel(filaSeleccionada), 0);

        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            Pagos pagoParaPdf = pagosDAO.obtenerPagoPorId(idPagoseleccionado, conn);
            if (pagoParaPdf != null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar Reporte de Pago");
                // Establecer el directorio inicial a Escritorio del usuario
                File defaultDir = new File(System.getProperty("user.home") + File.separator + "Desktop");
                if (defaultDir.exists() && defaultDir.isDirectory()) {
                    fileChooser.setCurrentDirectory(defaultDir);
                } else {
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                }

                fileChooser.setSelectedFile(new File("Reporte_Pago_" + idPagoseleccionado + ".pdf"));

                FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setFileFilter(filter);

                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();

                    if (!filePath.toLowerCase().endsWith(".pdf")) {
                        filePath += ".pdf";
                    }
                    
                    generarPdfPago(pagoParaPdf, filePath); // Llama al método de generación de PDF
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar el pago con ID: " + idPagoseleccionado + " para generar el PDF.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener los datos del pago para el PDF: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    
    /**
     * Genera un documento PDF con los detalles del pago proporcionado en la ruta especificada.
     *
     * @param pago El objeto Pagos cuyos detalles se usarán para generar el PDF.
     * @param filePath La ruta completa del archivo donde se guardará el PDF.
     */
    public void generarPdfPago(Pagos pago, String filePath) {
        if (pago == null) {
            JOptionPane.showMessageDialog(this, "No se encontró información del pago para generar el PDF.", "Error de PDF", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            File outputFile = new File(filePath);
            File parentDir = outputFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new IOException("No se pudieron crear los directorios necesarios: " + parentDir.getAbsolutePath());
                }
            }

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");

            // Título del documento
            document.add(new Paragraph("Reporte de Pago - ID: " + pago.getIdPago())
                         .setFontSize(20)
                         .setBold()); // Removed setTextAlignment
            document.add(new Paragraph("\n"));

            // Detalles del pago en formato de tabla
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
            table.setWidth(UnitValue.createPercentValue(90));
            // table.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER); // Removed setHorizontalAlignment

            // Añadir celdas al reporte
            addCellToTable(table, "ID Pago:", String.valueOf(pago.getIdPago()));
            addCellToTable(table, "ID Cliente:", String.valueOf(pago.getIdCliente()));
            addCellToTable(table, "ID Mantenimiento:", String.valueOf(pago.getIdMantenimiento()));
            addCellToTable(table, "Detalle:", pago.getDetalle());
            addCellToTable(table, "Valor Trabajo:", "$" + (pago.getValorTrabajo() != null ? pago.getValorTrabajo().toPlainString() : "N/A"));
            addCellToTable(table, "Valor Pagado:", "$" + (pago.getValorPagado() != null ? pago.getValorPagado().toPlainString() : "N/A"));
            addCellToTable(table, "Valor Mora:", "$" + (pago.getValorMora() != null ? pago.getValorMora().toPlainString() : "N/A"));
            addCellToTable(table, "Estado Pago:", String.valueOf(pago.getEstadoPago()));
            addCellToTable(table, "Fecha Facturación:", (pago.getFechaFacturacion() != null ? formatoFecha.format(pago.getFechaFacturacion()) : "N/A"));
            addCellToTable(table, "Días Plazo:", String.valueOf(pago.getDiasPlazo()));
            addCellToTable(table, "Fecha Vencimiento:", (pago.getFechaVencimiento() != null ? formatoFecha.format(pago.getFechaVencimiento()) : "N/A"));

            document.add(table);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Reporte generado por SmartCount_P1_DBA - " + formatoFecha.format(new Date()))
                         .setFontSize(10)); // Removed setTextAlignment

            document.close();

            JOptionPane.showMessageDialog(this, "PDF del pago generado exitosamente en: " + filePath, "PDF Generado", JOptionPane.INFORMATION_MESSAGE);

            try {
                File file = new File(filePath);
                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                }
            } catch (IOException ex) {
                System.err.println("No se pudo abrir el PDF automáticamente: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "PDF generado, pero no se pudo abrir automáticamente. Por favor, ábralo desde: " + filePath, "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: No se pudo crear el archivo PDF. Asegúrate de tener permisos de escritura o que la ruta sea válida.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al escribir el archivo PDF: " + e.getMessage(), "Error de Escritura", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado al generar el PDF: " + e.getMessage(), "Error de PDF", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Método auxiliar para añadir celdas a la tabla del PDF con estilo
    private void addCellToTable(Table table, String label, String value) {
        table.addCell(new Cell().add(new Paragraph(label).setBold()));
        table.addCell(new Cell().add(new Paragraph(value)));
    }
}