package clasesBotones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import formularios.FormularioEditar;
import formularios.FormularioEditarCliente;

public class BotonEditorEquipos extends DefaultCellEditor {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JPanel panel;
    protected JButton btnModificar, btnEliminar, btnImprimir;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private int currentRow;

    public BotonEditorEquipos(JCheckBox checkBox, DefaultTableModel modeloTabla, JTable tablaEquipos) {
        super(checkBox);
        
        ToolTipManager.sharedInstance().setEnabled(true);
        // Establecer tiempo de espera para que aparezcan los tooltips
        ToolTipManager.sharedInstance().setInitialDelay(200); // milisegundos

        this.modeloTabla = modeloTabla;
        this.tablaEquipos = tablaEquipos;

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
     public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {
        currentRow = row; // Guardamos la fila real
        return panel;
    }
    
    private void modificar(int row, DefaultTableModel modeloTabla) {
        try {
        	Window parent = SwingUtilities.getWindowAncestor(tablaEquipos);
        	FormularioEditarCliente dialog = new FormularioEditarCliente(parent, modeloTabla, row);
        	dialog.setVisible(true);
        	fireEditingStopped();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al abrir el formulario: " + ex.getMessage());
	    }
	    fireEditingStopped();
    }


    public void eliminar(int row) {
        if (row >= 0 && row < modeloTabla.getRowCount()) {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este empleado?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Eliminar del modelo
                modeloTabla.removeRow(row);
                // No intentes acceder a modelo.getValueAt(row, ...) después de eliminar
            }
        } else {
            System.out.println("Índice de fila inválido: " + row);
        }
    }


    private void imprimir(int row) {
        String numero = modeloTabla.getValueAt(row, 1).toString();
        JOptionPane.showMessageDialog(null, "Generar PDF para equipo: " + numero);
        // Llamar aquí a tu método generarPDF(...)
        fireEditingStopped();
    }
    
    private ImageIcon escalarIcono(String ruta, int ancho, int alto) {
    	ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
    	Image imagen = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    	return new ImageIcon(imagen);
    }
}


