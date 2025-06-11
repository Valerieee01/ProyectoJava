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
import util.ConnectionADMIN;			// Importa tu clase de conexión a la base de datos

/**
 * Panel que muestra una tabla de equipos, con funcionalidades para agregar,
 * modificar y eliminar equipos, interactuando con una base de datos.
 */
public class panelEquipos extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tablaEquipos_1;
    private DefaultTableModel modeloTabla;
    private EquiposDAO equiposDAO; // Instancia del DAO para interactuar con la DB
    private Connection dbConnection; // La conexión a la base de datos

    public panelEquipos() {
        // Establece el layout principal del panel a BorderLayout
        setLayout(new BorderLayout());

        // --- Configuración del Botón "Nuevo Equipo" ---
        JButton btnNuevoEquipo = new JButton("Nuevo Equipo");
        btnNuevoEquipo.setToolTipText("Ingresa tu nuevo equipo aquí");
        btnNuevoEquipo.setForeground(Color.WHITE);
        btnNuevoEquipo.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this); // Obtiene la ventana padre
            if (equiposDAO != null) {
                // Pasa el modelo, el DAO y una referencia a sí mismo (este panel) al FormularioEditar
                // para que pueda agregar, refrescar la tabla y usar el DAO.
                FormularioEditar dialog = new FormularioEditar(parent, modeloTabla, equiposDAO, this);
                dialog.setVisible(true); // Hace visible el diálogo
            } else {
                 JOptionPane.showMessageDialog(this,
                    "La base de datos no está disponible. No se puede agregar un equipo.",
                    "Error de Aplicación",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        btnNuevoEquipo.setBackground(new Color(255, 146, 94)); // Un color naranja cálido
        add(btnNuevoEquipo, BorderLayout.NORTH); // Añade el botón en la parte superior

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
        // Aquí es crucial pasar 'this' (la instancia de panelEquipos) al editor,
        // para que BotonEditorEquipos pueda llamar a 'cargarEquiposEnTabla'
        // después de una operación de eliminación o modificación.
        tablaEquipos_1.getColumn("Acciones").setCellEditor(new BotonEditorEquipos(new JCheckBox(), modeloTabla, tablaEquipos_1, this.equiposDAO, this));

        // Añade la tabla a un JScrollPane para que sea desplazable si hay muchas filas
        add(new JScrollPane(tablaEquipos_1), BorderLayout.CENTER);

        // --- Inicialización de la Conexión a la Base de Datos y el DAO ---
        try {
            dbConnection = ConnectionADMIN.getConnectionADMIN(); // Obtiene la conexión
            if (dbConnection != null) {
                equiposDAO = new EquiposDAO(dbConnection); // Crea la instancia del DAO
                cargarEquiposEnTabla(); // Carga los datos en la tabla al inicio del panel
            } else {
                // Si la conexión es null, muestra un mensaje y deshabilita funcionalidades de DB
                JOptionPane.showMessageDialog(this,
                        "No se pudo establecer conexión con la base de datos.",
                        "Error de Conexión",
                        JOptionPane.ERROR_MESSAGE);
                btnNuevoEquipo.setEnabled(false); // Deshabilita el botón si no hay DB
            }
        } catch (SQLException e) {
            // Captura cualquier error SQL durante la conexión o inicialización del DAO
            JOptionPane.showMessageDialog(this,
                    "Error al conectar con la base de datos o al inicializar: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprime el stack trace para depuración
            btnNuevoEquipo.setEnabled(false); // Deshabilita el botón
        }

        // --- Acción del Botón "Nuevo Equipo" ---
        btnNuevoEquipo.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this); // Obtiene la ventana padre
            if (equiposDAO != null) {
                // Pasa el modelo, el DAO y una referencia a sí mismo (este panel) al FormularioEditar
                // para que pueda agregar, refrescar la tabla y usar el DAO.
                FormularioEditar dialog = new FormularioEditar(parent, modeloTabla, equiposDAO, this);
                dialog.setVisible(true); // Hace visible el diálogo
            } else {
                 JOptionPane.showMessageDialog(this,
                    "La base de datos no está disponible. No se puede agregar un equipo.",
                    "Error de Aplicación",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * Método para cargar todos los equipos desde la base de datos
     * y mostrarlos en la JTable.
     * Este método limpia la tabla actual y la repobla con datos frescos de la DB.
     */
    public void cargarEquiposEnTabla() {
        // Limpia todas las filas existentes en el modelo de la tabla
        modeloTabla.setRowCount(0); 

        try {
            // Obtiene la lista de objetos Equipo desde la base de datos usando el DAO
            List<Equipo> equipos = EquiposDAO.obtenerTodosLosEquipos(); 
            
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
                    // La columna 5 ("Acciones") se llenará automáticamente con los botones por el CellRenderer
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

    // Opcional: Método para cerrar la conexión cuando el panel ya no sea necesario
    // Es una buena práctica, pero depende de cómo manejas el ciclo de vida de tu aplicación.
    public void closeConnection() {
        if (dbConnection != null) {
            try {
                dbConnection.close();
                System.out.println("Conexión a la base de datos cerrada desde panelEquipos.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión de la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}