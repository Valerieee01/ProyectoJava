package clasesBotones;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.TableCellRenderer;

public class BotonRenderer extends JPanel implements TableCellRenderer {

    private JButton btnModificar, btnEliminar, btnImprimir;

    public BotonRenderer( JTable tablaEquipos ) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        tablaEquipos.setToolTipText(""); // activar las tooltips
        
        ToolTipManager.sharedInstance().setEnabled(true);
        // Establecer tiempo de espera para que aparezcan los tooltips
        ToolTipManager.sharedInstance().setInitialDelay(200); // milisegundos

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
        btnImprimir.setIcon(escalarIcono("/clasesBotones/imgBotones/impresora.png", 32, 32));
        btnImprimir.setBorderPainted(false);
        btnImprimir.setContentAreaFilled(false);
        btnImprimir.setFocusPainted(false);
        btnImprimir.setOpaque(false);     
        
        //Mostrar texto al pasar el mouse
        btnModificar.setToolTipText("Modificar equipo");
        btnEliminar.setToolTipText("Eliminar equipo");
        btnImprimir.setToolTipText("Imprimir datos del equipo");
        
        // Ajusta tamaño del botón si es necesario
        btnModificar.setPreferredSize(new Dimension(32, 32));
        btnEliminar.setPreferredSize(new Dimension(32, 32));
        btnImprimir.setPreferredSize(new Dimension(32, 32));

        add(btnModificar);
        add(btnEliminar);
        add(btnImprimir);
    }
    
    private ImageIcon escalarIcono(String ruta, int ancho, int alto) {
    	ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
    	Image imagen = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    	return new ImageIcon(imagen);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
