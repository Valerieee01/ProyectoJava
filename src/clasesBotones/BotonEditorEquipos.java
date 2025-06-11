package clasesBotones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException; // Importar SQLException

import formularios.FormularioEditar;
import DAO.EquiposDAO; // Importar tu DAO
import menuInicialAdministrador.panelEquipos; // Importar el panel para refrescar la tabla

public class BotonEditorEquipos extends DefaultCellEditor {
    private static final long serialVersionUID = 1L;

    protected JPanel panel;
    protected JButton btnModificar, btnEliminar, btnImprimir;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private int currentRow; // Almacena la fila actual que está siendo editada
    
    // --- Nuevas referencias ---
    private EquiposDAO equiposDAO;
    private panelEquipos panelEquiposRef; // Referencia al panel principal para recargar la tabla

    public BotonEditorEquipos(JCheckBox checkBox, DefaultTableModel modeloTabla, JTable tablaEquipos, EquiposDAO equiposDAO, panelEquipos panelEquiposRef) {
        super(checkBox);
        
        ToolTipManager.sharedInstance().setEnabled(true);
        ToolTipManager.sharedInstance().setInitialDelay(200);

        this.modeloTabla = modeloTabla;
        this.tablaEquipos = tablaEquipos;
        this.equiposDAO = equiposDAO; // Asignar la instancia del DAO
        this.panelEquiposRef = panelEquiposRef; // Asignar la referencia al panel

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        
        // --- Botón Modificar ---
        btnModificar = new JButton();
        btnModificar.setIcon(escalarIcono("/clasesBotones/imgBotones/edit.png", 20, 20));
        btnModificar.setBorderPainted(false);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setFocusPainted(false);
        btnModificar.setOpaque(false);
        btnModificar.setToolTipText("Modificar equipo");

        // --- Botón Eliminar ---
        btnEliminar = new JButton();
        btnEliminar.setIcon(escalarIcono("/clasesBotones/imgBotones/delete.png", 20, 20));
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setOpaque(false);
        btnEliminar.setToolTipText("Eliminar equipo");
        
        // --- Botón Imprimir ---
        btnImprimir = new JButton();
        btnImprimir.setIcon(escalarIcono("/clasesBotones/imgBotones/impresora.png", 30, 30));
        btnImprimir.setBorderPainted(false);
        btnImprimir.setContentAreaFilled(false);
        btnImprimir.setFocusPainted(false);
        btnImprimir.setOpaque(false);
        btnImprimir.setToolTipText("Imprimir datos del equipo");

        // Opcional: Ajusta tamaño preferido de los botones
        btnModificar.setPreferredSize(new Dimension(32, 32));
        btnEliminar.setPreferredSize(new Dimension(32, 32));
        btnImprimir.setPreferredSize(new Dimension(32, 32));

        // --- Manejadores de Eventos (ActionListeners) ---
        // Usar Lambdas para mayor brevedad y claridad
        btnModificar.addActionListener(e -> modificar(currentRow)); // Modificar ahora recibe solo la fila
        btnEliminar.addActionListener(e -> eliminar(currentRow));
        btnImprimir.addActionListener(e -> imprimir(currentRow));

        // Añadir botones al panel
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnImprimir);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRow = row; // Guarda la fila real del modelo
        return panel;
    }
    
    @Override
    public Object getCellEditorValue() {
        return ""; // Se devuelve un valor vacío ya que el editor solo contiene botones
    }

    // --- Lógica del botón Modificar ---
    private void modificar(int row) {
        fireEditingStopped(); // Detener la edición de la celda inmediatamente

        // Obtener la ventana padre para el diálogo
        Window parent = SwingUtilities.getWindowAncestor(tablaEquipos);

        // Validar que la fila sea válida antes de intentar acceder a los datos
        if (row < 0 || row >= modeloTabla.getRowCount()) {
            JOptionPane.showMessageDialog(parent, "Fila seleccionada inválida para modificar.", "Error de Edición", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear una instancia de FormularioEditar en modo edición
        // Pasa todas las dependencias necesarias: modeloTabla, equiposDAO, panelEquiposRef y la fila
        // Este constructor debe existir en FormularioEditar
        FormularioEditar dialog = new FormularioEditar(parent, modeloTabla, equiposDAO, panelEquiposRef, row);
        dialog.setVisible(true); // Mostrar el diálogo
    }

    // --- Lógica del botón Eliminar ---
    public void eliminar(int row) {
        fireEditingStopped(); // Detener la edición de la celda
        int idEquipo1 = (int) modeloTabla.getValueAt(row, 0); 

        if (row >= 0 && row < modeloTabla.getRowCount()) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este equipo?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Obtener el ID del equipo de la primera columna del modelo
                    // Asumiendo que la columna 0 contiene el ID_EQUIPO de la base de datos
                    int idEquipo = (int) modeloTabla.getValueAt(row, 0); 

                    // Llamar al método eliminarEquipo del DAO
                    equiposDAO.eliminarEquipo(idEquipo);

                    // Mensaje de éxito
                    JOptionPane.showMessageDialog(null, "Equipo eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    // --- Refrescar la tabla en el panel principal ---
                    if (panelEquiposRef != null) {
                        panelEquiposRef.cargarEquiposEnTabla();
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, 
                                                  "Error al eliminar el equipo de la base de datos: " + ex.getMessage(), 
                                                  "Error SQL", 
                                                  JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Imprime el stack trace para depuración
                } catch (ClassCastException | NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, 
                                                  "Error al obtener ID del equipo. Datos inconsistentes en la tabla.", 
                                                  "Error Interno", 
                                                  JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } else {
            System.out.println("Índice de fila inválido para eliminar: " + row);
        }
    }

    // --- Lógica del botón Imprimir ---
    private void imprimir(int row) {
        fireEditingStopped(); // Detener la edición de la celda
        if (row >= 0 && row < modeloTabla.getRowCount()) {
            String numeroEquipo = modeloTabla.getValueAt(row, 1).toString(); // Asumiendo columna 1 es "Número"
            String placa = modeloTabla.getValueAt(row, 2).toString();       // Asumiendo columna 2 es "Placa"

            JOptionPane.showMessageDialog(null, "Generar PDF para el equipo:\n" + 
                                                "Número: " + numeroEquipo + "\n" +
                                                "Placa: " + placa, "Imprimir Equipo", JOptionPane.INFORMATION_MESSAGE);
            // Aquí llamarías a tu método para generar el PDF, por ejemplo:
            // miGeneradorPDF.generarReporteEquipo(numeroEquipo, placa, ...);
        } else {
            System.out.println("Índice de fila inválido para imprimir: " + row);
        }
    }
    
    // Método auxiliar para escalar iconos
    private ImageIcon escalarIcono(String ruta, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
        if (iconoOriginal.getImage() == null) {
            System.err.println("Advertencia: No se pudo cargar el icono de la ruta: " + ruta);
            return new ImageIcon(); // Devuelve un icono vacío para evitar NullPointerException
        }
        Image imagen = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
}