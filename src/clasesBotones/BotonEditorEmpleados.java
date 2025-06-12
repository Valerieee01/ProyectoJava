package clasesBotones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import formularios.FormularioEditarEmpleado;
import menuInicialAdministrador.panelEmpleados;
import modelos.Persona;
import DAO.PersonasDAO;
import util.ConnectionADMIN;

public class BotonEditorEmpleados extends DefaultCellEditor {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JPanel panel;
    protected JButton btnModificar, btnEliminar, btnImprimir;
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private int currentRow;
    private panelEmpleados panelEmpleadosRef;

    public BotonEditorEmpleados(JCheckBox checkBox, panelEmpleados panelEmpleadosRef, JTable tablaEmpleados) {
        super(checkBox);
        
        ToolTipManager.sharedInstance().setEnabled(true);
        // Establecer tiempo de espera para que aparezcan los tooltips
        ToolTipManager.sharedInstance().setInitialDelay(200); // milisegundos

        this.panelEmpleadosRef = panelEmpleadosRef;
        this.modeloTabla = panelEmpleadosRef.getModeloTabla();
        this.tablaEmpleados = tablaEmpleados;

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        btnModificar = new JButton();
        btnModificar.setIcon(escalarIcono("/clasesBotones/imgBotones/edit.png", 20, 20));
        btnModificar.setBorderPainted(false);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setFocusPainted(false);
        btnModificar.setOpaque(false);

        btnEliminar = new JButton();
        btnEliminar.setIcon(escalarIcono("/clasesBotones/imgBotones/delete.png", 20, 20));
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setOpaque(false);
        
        btnImprimir = new JButton();
        btnImprimir.setIcon(escalarIcono("/clasesBotones/imgBotones/impresora.png", 30, 30));
        btnImprimir.setBorderPainted(false);
        btnImprimir.setContentAreaFilled(false);
        btnImprimir.setFocusPainted(false);
        btnImprimir.setOpaque(false);

        // texto a mostrar al pasar el mouse
        btnModificar.setToolTipText("Modificar equipo");
        btnEliminar.setToolTipText("Eliminar equipo");
        btnImprimir.setToolTipText("Imprimir datos del equipo");

        // Opcional: Ajusta tamaño del botón si es necesario
        btnModificar.setPreferredSize(new Dimension(32, 32));
        btnEliminar.setPreferredSize(new Dimension(32, 32));
        btnImprimir.setPreferredSize(new Dimension(32, 32));
        // Eventos
        btnModificar.addActionListener(e -> modificar(currentRow,modeloTabla));
        btnEliminar.addActionListener(e -> eliminar(currentRow));
        btnImprimir.addActionListener(e -> imprimir(currentRow));

        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnImprimir);
        
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRow = tablaEmpleados.convertRowIndexToModel(row);
        return panel;
    }
    
    
    private void modificar(int row, DefaultTableModel modeloTabla) {
        try {
        	String numeroIdentificacion = (String) modeloTabla.getValueAt(row, 1);
        	 Window parent = SwingUtilities.getWindowAncestor(tablaEmpleados);
             FormularioEditarEmpleado dialog = new FormularioEditarEmpleado(parent, panelEmpleadosRef, numeroIdentificacion);
             dialog.setVisible(true);
             fireEditingStopped();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al abrir el formulario: " + ex.getMessage());
	    }
    }

    public void eliminar(int row) {
        if (row >= 0 && row < modeloTabla.getRowCount()) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este cliente?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String numeroIdentificacion = (String) modeloTabla.getValueAt(row, 1);
                try (Connection conn = ConnectionADMIN.getConnectionADMIN()) { // Abre la conexión aquí para la operación de eliminación
                    PersonasDAO personasDAO = new PersonasDAO(); // Crea el DAO sin conexión en el constructor
                    personasDAO.eliminarPersona(numeroIdentificacion, conn); // Pasa la conexión al método
                    JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    panelEmpleadosRef.cargarDatosEmpleados();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al eliminar cliente: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            System.out.println("Índice de fila inválido: " + row);
        }
    }

 // --- Lógica del botón Imprimir---
    private void imprimir(int row) {
        if (row >= 0 && row < modeloTabla.getRowCount()) {
        	
            try (Connection conn = ConnectionADMIN.getConnectionADMIN()) { 
                String numeroIdentificacion = (String) modeloTabla.getValueAt(row, 1); // Obtener el ID del equipo

					Persona persona = PersonasDAO.obtenerPersonaPorNumeroIdentificacion(numeroIdentificacion, conn);

                if (persona != null) {
                    // --- Iniciar JFileChooser para seleccionar la ubicación de guardado ---
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Guardar PDF del Equipo");
                    fileChooser.setSelectedFile(new File("reporte_equipo_" + persona.getNumeroIdentificacion() + ".pdf")); // Nombre por defecto

                    int userSelection = fileChooser.showSaveDialog(panel); // Mostrar el diálogo de guardar
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        // Asegurarse de que la extensión .pdf esté presente
                        String filePath = fileToSave.getAbsolutePath();
                        if (!filePath.toLowerCase().endsWith(".pdf")) {
                            filePath += ".pdf";
                        }

                        // Llamar al método generarPdfEquipo en panelEquiposRef, pasando la ruta completa
                        panelEmpleadosRef.generarPdfEmpleados(persona, filePath); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Operación de guardado de PDF cancelada por el usuario.", "PDF Cancelado", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo obtener la información del equipo para imprimir.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                                              "Error al obtener datos del equipo para imprimir: " + ex.getMessage(), 
                                              "Error SQL", 
                                              JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (ClassCastException | NullPointerException ex) {
                JOptionPane.showMessageDialog(null, 
                                              "Error al obtener ID del equipo de la tabla para imprimir.", 
                                              "Error Interno", 
                                              JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            System.out.println("Índice de fila inválido para imprimir: " + row);
        }
    }
    private ImageIcon escalarIcono(String ruta, int ancho, int alto) {
    	ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
    	Image imagen = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    	return new ImageIcon(imagen);
    }
}
