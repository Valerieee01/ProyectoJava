package menuInicialEmpleado;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser; 
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter; 
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import com.toedter.calendar.JDateChooser;

import DAO.MantenimientosDAO;
import formulariosEmp.FormularioMantenimientoEmp;
import modelos.Mantenimiento;
import util.ConnectionADMIN;

public class panelMantenimientosEmp extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtBuscarEquipo;
    private JComboBox<String> comboTipo;
    private JDateChooser dateChooser;
    private TableRowSorter<DefaultTableModel> sorter;
    private MantenimientosDAO mantenimientosDAO;

    public panelMantenimientosEmp() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(255, 235, 215));

        mantenimientosDAO = new MantenimientosDAO();

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(new Color(255, 200, 160));

        txtBuscarEquipo = new JTextField(10);
        comboTipo = new JComboBox<>(new String[]{"Todos", "Preventivo", "Correctivo"});
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        panelFiltros.add(new JLabel("Equipo:"));
        panelFiltros.add(txtBuscarEquipo);
        panelFiltros.add(new JLabel("Tipo:"));
        panelFiltros.add(comboTipo);
      

        JButton btnAplicarFiltro = new JButton("Aplicar Filtro");
        btnAplicarFiltro.setBackground(new Color(255, 255, 255));
        btnAplicarFiltro.setForeground(new Color(255, 128, 64));
        btnAplicarFiltro.addActionListener(e -> aplicarFiltros());
        panelFiltros.add(btnAplicarFiltro);

        add(panelFiltros, BorderLayout.NORTH);

        String[] columnas = {"ID", "ID Equipo", "Descripción Trabajo", "Encargado", "Tipo Mantenimiento", "Fecha Mantenimiento", "Observaciones", "ID Empleado"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        // Si sorter es nulo, esta línea lanzará un NullPointerException.
        // Asegúrate de inicializarlo: sorter = new TableRowSorter<>(modelo);
        // Sin embargo, si no lo estás usando activamente para ordenar, no es crítico.
        // tabla.setRowSorter(sorter); // Comentado por ahora si no está inicializado

        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(255, 220, 190) : new Color(255, 240, 220));
                if (isSelected) {
                    c.setBackground(new Color(255, 170, 100));
                }
                return c;
            }
        });

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(255, 200, 160));

        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.setBackground(new Color(255, 255, 255));
        btnNuevo.setForeground(new Color(255, 128, 64));
        btnNuevo.addActionListener(e -> abrirFormularioNuevoMantenimiento());

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 255, 255));
        btnEditar.setForeground(new Color(255, 128, 64));
        btnEditar.addActionListener(e -> editarMantenimientoSeleccionado());

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(255, 255, 255));
        btnEliminar.setForeground(new Color(255, 128, 64));
        btnEliminar.addActionListener(e -> eliminarMantenimientoSeleccionado());

        JButton btnGenerarPDF = new JButton("Generar PDF");
        btnGenerarPDF.setBackground(new Color(255, 255, 255));
        btnGenerarPDF.setForeground(new Color(255, 128, 64));
        btnGenerarPDF.addActionListener(e -> generarPdfMantenimientoSeleccionado());


        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGenerarPDF);

        add(panelBotones, BorderLayout.SOUTH);

        cargarMantenimientosEnTabla();
    }

    private void abrirFormularioNuevoMantenimiento() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        FormularioMantenimientoEmp dialog = new FormularioMantenimientoEmp(parent, mantenimientosDAO, this);
        dialog.setVisible(true);
    }

    private void editarMantenimientoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un mantenimiento de la tabla para editar.", "Ningún Mantenimiento Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMantenimiento = (int) modelo.getValueAt(tabla.convertRowIndexToModel(filaSeleccionada), 0);

        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            Mantenimiento mantenimientoAEditar = mantenimientosDAO.obtenerMantenimientoPorId(idMantenimiento, conn);
            if (mantenimientoAEditar != null) {
                Window parent = SwingUtilities.getWindowAncestor(this);
                FormularioMantenimientoEmp dialog = new FormularioMantenimientoEmp(parent, mantenimientosDAO, this, mantenimientoAEditar);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar el mantenimiento con ID: " + idMantenimiento, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener los datos del mantenimiento para editar: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void eliminarMantenimientoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un mantenimiento de la tabla para eliminar.", "Ningún Mantenimiento Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este mantenimiento?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            int idMantenimiento = (int) modelo.getValueAt(tabla.convertRowIndexToModel(filaSeleccionada), 0);

            try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
                boolean eliminado = mantenimientosDAO.eliminarMantenimiento(idMantenimiento, conn);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Mantenimiento eliminado exitosamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    cargarMantenimientosEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el mantenimiento.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el mantenimiento de la base de datos: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void aplicarFiltros() {
        cargarMantenimientosEnTabla();
    }

    public void cargarMantenimientosEnTabla() {
        modelo.setRowCount(0);

        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            List<Mantenimiento> mantenimientos = mantenimientosDAO.obtenerTodosLosMantenimientos(conn);

            String textoEquipo = txtBuscarEquipo.getText().toLowerCase();
            String tipoSeleccionado = (String) comboTipo.getSelectedItem();
            Date fechaSeleccionada = (dateChooser.getDate() != null) ? new Date(dateChooser.getDate().getTime()) : null;

            for (Mantenimiento mantenimiento : mantenimientos) {
                boolean cumpleFiltroEquipo = textoEquipo.isEmpty() ||
                                             String.valueOf(mantenimiento.getIdEquipo()).toLowerCase().contains(textoEquipo) ||
                                             mantenimiento.getEncargado().toLowerCase().contains(textoEquipo);

                boolean cumpleFiltroTipo = tipoSeleccionado.equals("Todos") ||
                                           mantenimiento.getTipoMantenimiento().name().equalsIgnoreCase(tipoSeleccionado);

                boolean cumpleFiltroFecha = (fechaSeleccionada == null) ||
                                            (mantenimiento.getFechaMantenimiento() != null &&
                                             mantenimiento.getFechaMantenimiento().equals(fechaSeleccionada));

                if (cumpleFiltroEquipo && cumpleFiltroTipo && cumpleFiltroFecha) {
                    modelo.addRow(new Object[]{
                        mantenimiento.getIdMantenimiento(),
                        mantenimiento.getIdEquipo(),
                        mantenimiento.getDescripcionTrabajo(),
                        mantenimiento.getEncargado(),
                        mantenimiento.getTipoMantenimiento(),
                        mantenimiento.getFechaMantenimiento(),
                        mantenimiento.getObservaciones(),
                        mantenimiento.getIdEmpleado()
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los Mantenimientos de la base de datos: " + e.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Genera un documento PDF para el mantenimiento seleccionado en la tabla.
     * Permite al usuario elegir la ubicación de guardado.
     */
    private void generarPdfMantenimientoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un mantenimiento de la tabla para generar el PDF.", "Ningún Mantenimiento Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID del mantenimiento de la fila seleccionada
        int idMantenimiento = (int) modelo.getValueAt(tabla.convertRowIndexToModel(filaSeleccionada), 0);

        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) {
            Mantenimiento mantenimientoParaPdf = mantenimientosDAO.obtenerMantenimientoPorId(idMantenimiento, conn);
            if (mantenimientoParaPdf != null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar PDF del Mantenimiento"); // Corregido typo
                // Establecer el directorio inicial a Escritorio del usuario si existe
                File defaultDir = new File(System.getProperty("user.home") + File.separator + "Desktop");
                if (defaultDir.exists() && defaultDir.isDirectory()) {
                    fileChooser.setCurrentDirectory(defaultDir);
                } else {
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                }

                // Nombre de archivo por defecto
                fileChooser.setSelectedFile(new File("Reporte_Mantenimiento_" + idMantenimiento + ".pdf"));

                // Filtro para solo mostrar archivos PDF
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setFileFilter(filter); // Establece el filtro por defecto

                int userSelection = fileChooser.showSaveDialog(this); // Importante: usar 'this' para el padre del diálogo
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();

                    // Asegurarse de que la extensión .pdf esté presente
                    if (!filePath.toLowerCase().endsWith(".pdf")) {
                        filePath += ".pdf";
                    }

                    // Ahora, usa la 'filePath' obtenida del JFileChooser para generar el PDF
                    generarPdfMantenimientos(mantenimientoParaPdf, filePath);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar el mantenimiento con ID: " + idMantenimiento + " para generar el PDF.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener los datos del mantenimiento para el PDF: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Genera un documento PDF con los detalles del mantenimiento proporcionado en la ruta especificada.
     *
     * @param mantenimiento El objeto Mantenimiento cuyos detalles se usarán para generar el PDF.
     * @param filePath La ruta completa del archivo donde se guardará el PDF.
     */
    // Corregido el nombre del método de nuevo
    public void generarPdfMantenimientos(Mantenimiento mantenimiento, String filePath) {
        if (mantenimiento == null) {
            JOptionPane.showMessageDialog(this, "No se encontró información del mantenimiento para generar el PDF.", "Error de PDF", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Asegurarse de que el directorio padre existe antes de intentar escribir el archivo
            File outputFile = new File(filePath);
            File parentDir = outputFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                // Intenta crear los directorios padre. Si falla, lanza una IOException que será capturada.
                if (!parentDir.mkdirs()) {
                    throw new IOException("No se pudieron crear los directorios necesarios: " + parentDir.getAbsolutePath());
                }
            }

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");

            document.add(new Paragraph("Reporte de Mantenimiento - " + mantenimiento.getIdMantenimiento()).setFontSize(18).setBold());
            document.add(new Paragraph("\n"));

            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            table.addCell(new Paragraph("ID Mantenimiento:").setBold());
            table.addCell(new Paragraph(String.valueOf(mantenimiento.getIdMantenimiento())));
            
            table.addCell(new Paragraph("ID Equipo:").setBold());
            table.addCell(new Paragraph(String.valueOf(mantenimiento.getIdEquipo())));

            table.addCell(new Paragraph("Descripción:").setBold());
            table.addCell(new Paragraph(mantenimiento.getDescripcionTrabajo()));

            table.addCell(new Paragraph("Encargado:").setBold());
            table.addCell(new Paragraph(mantenimiento.getEncargado()));

            table.addCell(new Paragraph("Tipo Mantenimiento:").setBold());
            table.addCell(new Paragraph(String.valueOf(mantenimiento.getTipoMantenimiento())));

            table.addCell(new Paragraph("Fecha Mantenimiento:").setBold());
            String fechaFormateada = "";
            Date fechaMantenimiento = mantenimiento.getFechaMantenimiento();
            if (fechaMantenimiento != null) {
                fechaFormateada = formatoFecha.format(fechaMantenimiento);
            } else {
                fechaFormateada = "No especificada";
            }
            table.addCell(new Paragraph(fechaFormateada));

            table.addCell(new Paragraph("Observaciones:").setBold());
            table.addCell(new Paragraph(mantenimiento.getObservaciones()));
            
            table.addCell(new Paragraph("ID Empleado:").setBold());
            table.addCell(new Paragraph(String.valueOf(mantenimiento.getIdEmpleado())));
            document.add(table);
            
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Reporte generado por SmartCount_P1_DBA"));

            document.close();

            JOptionPane.showMessageDialog(this, "PDF del mantenimiento generado exitosamente en: " + filePath, "PDF Generado", JOptionPane.INFORMATION_MESSAGE);

            try {
                File file = new File(filePath);
                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                }
            } catch (IOException ex) {
                System.err.println("No se pudo abrir el PDF automáticamente: " + ex.getMessage());
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
}