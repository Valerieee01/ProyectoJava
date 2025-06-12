package menuInicialAdministrador;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.io.IOException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import clasesBotones.BotonEditorClientes;
import clasesBotones.BotonRenderer;
import formularios.FormularioEditarCliente;
import modelos.Equipo;
import modelos.Persona;
import DAO.PersonasDAO;
import util.ConnectionADMIN; // Asegúrate de que esta clase exista y funcione correctamente

public class panelClientes extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public panelClientes() {
        setLayout(new BorderLayout());

        JButton btnNuevoCliente = new JButton("Nuevo Cliente");
        btnNuevoCliente.setToolTipText("Ingresa tu nuevo Cliente aqui");
        btnNuevoCliente.setForeground(Color.WHITE);
        btnNuevoCliente.setBackground(new Color(255, 146, 94));
        add(btnNuevoCliente, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
        	    new Object[]{"Nombre Completo", "Identificacion", "Correo", "Telefono", "Direccion", "Estado", "Acciones"}, 0) {
        	    private static final long serialVersionUID = 1L;

        	    @Override
        	    public boolean isCellEditable(int row, int column) {
        	        return column == 6; // Solo la columna de acciones es editable
        	    }
        	};

        tablaClientes = new JTable(modeloTabla) {
            private static final long serialVersionUID = 1L;

            @Override
            public String getToolTipText(MouseEvent e) {
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                int col = columnAtPoint(p);

                if (col == 6) { // Columna de botones
                    int xDentroCelda = e.getX() - getCellRect(row, col, true).x;
                    if (xDentroCelda < 32) return "Modificar cliente";
                    else if (xDentroCelda < 64) return "Eliminar cliente";
                    else return "Imprimir datos del cliente";
                }
                return super.getToolTipText(e);
            }
        };

        tablaClientes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(255, 220, 190) : new Color(255, 240, 220));
                }
                return c;
            }
        });

        tablaClientes.setRowHeight(35);
        tablaClientes.getColumn("Acciones").setCellRenderer(new BotonRenderer(tablaClientes));
        tablaClientes.getColumn("Acciones").setCellEditor(new BotonEditorClientes(new JCheckBox(), this, tablaClientes));

        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        btnNuevoCliente.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            FormularioEditarCliente dialog = new FormularioEditarCliente(parent, this);
            dialog.setVisible(true);
        });

        cargarDatosClientes();
    }

    /**
     * Carga todos los datos de los clientes desde la base de datos
     * y los muestra en la tabla.
     */
    public void cargarDatosClientes() {
        modeloTabla.setRowCount(0); // Limpia todas las filas existentes en la tabla

        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) { // Obtiene la conexión a la base de datos
            PersonasDAO personasDAO = new PersonasDAO(); // Crea el DAO sin pasar la conexión al constructor
            List<Persona> listaPersonas = personasDAO.obtenerTodasLasPersonasClientes(conn); // Pasa la conexión al método

            for (Persona persona : listaPersonas) {
                modeloTabla.addRow(new Object[]{
                    persona.getNombres(),
                    persona.getNumeroIdentificacion(),
                    persona.getCorreo(),
                    persona.getTelefono(),
                    persona.getDireccion(),
                    persona.getEstado().name(),
                    "Acciones"
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de clientes: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Genera un documento PDF con los detalles del equipo proporcionado en la ruta especificada.
     *
     * @param equipo El objeto Equipo cuyos detalles se usarán para generar el PDF.
     * @param filePath La ruta completa del archivo donde se guardará el PDF.
     */
    public void generarPdfClientes(Persona persona, String filePath) { // ¡Método actualizado para aceptar filePath!
        if (persona == null) {
            JOptionPane.showMessageDialog(this, "No se encontró información del equipo para generar el PDF.", "Error de PDF", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PdfWriter writer = new PdfWriter(filePath); // Usar la ruta de archivo proporcionada
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título del documento
            document.add(new Paragraph("Reporte de Cliente - " + persona.getNumeroIdentificacion()).setFontSize(18).setBold());
            document.add(new Paragraph("\n")); // Salto de línea

            // Detalles del equipo en formato de tabla
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2})); // Dos columnas: Etiqueta y Valor
            table.setWidth(UnitValue.createPercentValue(100)); // Ancho de tabla al 100%

            table.addCell(new Paragraph("ID Persona:").setBold());
            table.addCell(new Paragraph(String.valueOf(persona.getIdPersona())));

            table.addCell(new Paragraph("Nombre:").setBold());
            table.addCell(new Paragraph(persona.getNombres()));

            table.addCell(new Paragraph("Tipo Identificacion:").setBold());
            table.addCell(new Paragraph(String.valueOf(persona.getTipoIdentificacion())));

            table.addCell(new Paragraph("Numero Identificacion:").setBold());
            table.addCell(new Paragraph(persona.getNumeroIdentificacion()));

            table.addCell(new Paragraph("Correo :").setBold());
            table.addCell(new Paragraph(persona.getCorreo()));
            
            table.addCell(new Paragraph("Telefono:").setBold());
            table.addCell(new Paragraph(persona.getTelefono()));

            table.addCell(new Paragraph("Direccion:").setBold());
            table.addCell(new Paragraph(persona.getDireccion()));

            table.addCell(new Paragraph("Id Ciudad:").setBold());
            table.addCell(new Paragraph(persona.getNumeroIdentificacion()));

            table.addCell(new Paragraph("Estado :").setBold());
            table.addCell(new Paragraph(String.valueOf(persona.getEstado())));
            
            document.add(table);

            document.add(new Paragraph("\n")); // Salto de línea
            document.add(new Paragraph("Reporte generado por SmartCount_P1_DBA"));

            document.close(); 

            JOptionPane.showMessageDialog(this, "PDF del equipo generado exitosamente en: " + filePath, "PDF Generado", JOptionPane.INFORMATION_MESSAGE);

            // Opcional: Abrir el PDF automáticamente
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
            JOptionPane.showMessageDialog(this, "Error: No se pudo crear el archivo PDF. Asegúrate de tener permisos de escritura en la ubicación.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) { // Captura otros errores de I/O
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al escribir el archivo PDF: " + e.getMessage(), "Error de Escritura", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) { // Captura cualquier otra excepción inesperada de iText
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado al generar el PDF: " + e.getMessage(), "Error de PDF", JOptionPane.ERROR_MESSAGE);
        }
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
}
