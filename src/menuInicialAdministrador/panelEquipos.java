package menuInicialAdministrador;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import clasesBotones.BotonEditorEquipos; // Asegúrate de que esta clase exista y sea correcta
import clasesBotones.BotonRenderer;     // Asegúrate de que esta clase exista y sea correcta
import formularios.FormularioEditar;    // Importa tu formulario de edición
import DAO.EquiposDAO;                  // Importa tu DAO de equipos
import modelos.Equipo;                  // Importa tu modelo Equipo
import util.ConnectionADMIN;
import util.ConnectionDBA;              // Importa tu clase de conexión a la base de datos (¡usar esta en lugar de ConnectionADMIN si es el caso!)

/**
 * Panel que muestra una tabla de equipos, con funcionalidades para agregar,
 * modificar y eliminar equipos, interactuando con una base de datos.
 */
public class panelEquipos extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tablaEquipos_1;
    private DefaultTableModel modeloTabla;
    private EquiposDAO equiposDAO; // Instancia del DAO para interactuar con la DB
    // La conexión no debe ser un campo de clase si cada método la va a gestionar.
    // Se elimina 'private Connection dbConnection;' o se mantiene solo para inicialización.

    public panelEquipos() {
        // Establece el layout principal del panel a BorderLayout
        setLayout(new BorderLayout());

        // --- Configuración del Botón "Nuevo Equipo" ---
        JButton btnNuevoEquipo = new JButton("Nuevo Equipo");
        btnNuevoEquipo.setToolTipText("Ingresa tu nuevo equipo aquí");
        btnNuevoEquipo.setForeground(Color.WHITE);
        btnNuevoEquipo.setBackground(new Color(255, 146, 94)); // Un color naranja cálido
        
        // --- Configuración del Modelo de la Tabla ---
        // Se definen las columnas de la tabla. "ID Equipo" se añade para almacenar el ID real de la DB.
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID Equipo", "Número", "Placa", "Descripción", "ID Cliente", "Acciones"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna "Acciones" (índice 5) es editable (contiene botones)
                return column == 5; 
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Define el tipo de datos para cada columna, importante para el renderizado y edición
                if (columnIndex == 0 || columnIndex == 4) return Integer.class; // ID Equipo, ID Cliente son enteros
                if (columnIndex == 5) return Object.class; // La columna de acciones contiene botones
                return String.class; // Las demás columnas son cadenas de texto
            }
        };

        // --- Configuración de la JTable ---
        tablaEquipos_1 = new JTable(modeloTabla) {
            private static final long serialVersionUID = 1L;

            @Override
            public String getToolTipText(MouseEvent e) {
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                int col = columnAtPoint(p);

                // Personaliza los tooltips para los botones en la columna "Acciones"
                if (col == 5) { // Si es la columna de botones (índice 5)
                    // Calcula la posición X dentro de la celda para determinar qué botón es
                    int xDentroCelda = e.getX() - getCellRect(row, col, true).x;
                    if (xDentroCelda < 32) return "Modificar equipo";
                    else if (xDentroCelda < 64) return "Eliminar equipo";
                    else return "Imprimir datos del equipo";
                }
                return super.getToolTipText(e); // Para otras columnas, usa el tooltip por defecto
            }
        };

        // --- Estilo Zebra para Filas de la Tabla ---
        tablaEquipos_1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    // Alterna colores de fondo para las filas (efecto cebra)
                    c.setBackground(row % 2 == 0 ? new Color(255, 220, 190) : new Color(255, 240, 220));
                }
                return c;
            }
        });

        // Configuración de la altura de las filas y los renderers/editores de celda
        tablaEquipos_1.setRowHeight(35);
        tablaEquipos_1.getColumn("Acciones").setCellRenderer(new BotonRenderer(tablaEquipos_1));
        
        // --- Inicialización del DAO aquí ---
        equiposDAO = new EquiposDAO(); // Crea una instancia del DAO (ya no estática)

        // Aquí es crucial pasar 'this' (la instancia de panelEquipos) al editor,
        // para que BotonEditorEquipos pueda llamar a 'cargarEquiposEnTabla'
        // después de una operación de eliminación o modificación.
        tablaEquipos_1.getColumn("Acciones").setCellEditor(new BotonEditorEquipos(new JCheckBox(), modeloTabla, tablaEquipos_1, this.equiposDAO, this));

        // Añade la tabla a un JScrollPane para que sea desplazable si hay muchas filas
        add(new JScrollPane(tablaEquipos_1), BorderLayout.CENTER);

        // --- Carga inicial de datos ---
        cargarEquiposEnTabla(); // Carga los datos en la tabla al inicio del panel


        // --- Acción del Botón "Nuevo Equipo" ---
        btnNuevoEquipo.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this); // Obtiene la ventana padre
            // Pasa el modelo, el DAO y una referencia a sí mismo (este panel) al FormularioEditar
            // para que pueda agregar, refrescar la tabla y usar el DAO.
            FormularioEditar dialog = new FormularioEditar(parent, modeloTabla, equiposDAO, this);
            dialog.setVisible(true); // Hace visible el diálogo
        });
        add(btnNuevoEquipo, BorderLayout.NORTH); // Añade el botón en la parte superior
    }

    /**
     * Método para cargar todos los equipos desde la base de datos
     * y mostrarlos en la JTable.
     * Este método limpia la tabla actual y la repobla con datos frescos de la DB.
     */
    public void cargarEquiposEnTabla() {
        // Limpia todas las filas existentes en el modelo de la tabla
        modeloTabla.setRowCount(0); 

        try (Connection conn = ConnectionADMIN.getConnectionADMIN()) { // ¡Obtener la conexión aquí para esta operación!
            // Obtiene la lista de objetos Equipo desde la base de datos usando el DAO
            List<Equipo> equipos = equiposDAO.obtenerTodosLosEquipos(conn); // ¡Pasar la conexión!
            
            // Itera sobre cada equipo en la lista
            for (Equipo equipo : equipos) {
                // Añade una nueva fila al modelo de la tabla con los datos del equipo.
                // Asegúrate de que el orden de los datos coincida con el orden de las columnas en modeloTabla.
                modeloTabla.addRow(new Object[]{
                    equipo.getIdEquipo(),     // Columna 0: ID Equipo (usado para edición/eliminación)
                    equipo.getNumeroEquipo(), // Columna 1: Número
                    equipo.getPlaca(),        // Columna 2: Placa
                    equipo.getDescripcion(),  // Columna 3: Descripción
                    equipo.getIdCliente(),    // Columna 4: ID Cliente
                    ""                        // Columna 5: Acciones (el CellRenderer se encargará de los botones)
                });
            }
        } catch (SQLException e) {
            // Manejo de errores si la carga de datos falla
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los equipos de la base de datos: " + e.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprime el stack trace para depuración
        }
    }

    // Ya no es necesario un método closeConnection aquí si las conexiones se gestionan con try-with-resources
    // en cada operación del DAO.
}
