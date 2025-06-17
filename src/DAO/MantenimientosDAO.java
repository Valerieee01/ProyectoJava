package DAO;

import java.sql.Connection;
import java.sql.Date; // Importar java.sql.Date explícitamente para evitar ambigüedades
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Necesario para obtener las claves generadas
import java.util.ArrayList;
import java.util.List;

import modelos.Mantenimiento;
import modelos.Mantenimiento.TipoMantenimiento;

public class MantenimientosDAO {

    /**
     * Constructor vacío.
     * La conexión se pasa a cada método, lo cual es una buena práctica para la gestión de recursos
     * y permite mayor flexibilidad (ej. diferentes fuentes de conexión).
     */
    public MantenimientosDAO() {
        // No se requiere inicialización específica aquí, ya que la conexión se maneja externamente.
    }

    /**
     * Inserta un nuevo registro de mantenimiento en la base de datos.
     * Si la base de datos genera un ID automáticamente, este método intenta obtenerlo y asignarlo
     * al objeto Mantenimiento pasado, además de retornarlo.
     *
     * @param mantenimiento El objeto Mantenimiento a insertar, con los datos necesarios.
     * @param connection La conexión JDBC a la base de datos.
     * @return El ID (int) generado por la base de datos para el nuevo mantenimiento, o -1 si no se pudo obtener un ID.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public int agregarMantenimiento(Mantenimiento mantenimiento, Connection connection) throws SQLException {
        // Se añade Statement.RETURN_GENERATED_KEYS para poder recuperar el ID generado por la BD.
        String sql = "INSERT INTO mantenimientos (id_equipo, descripcion_trabajo, encargado, tipo_mantenimiento, fecha_mantenimiento, observaciones, id_empleado)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        int idGenerado = -1; // Valor por defecto si no se puede obtener el ID

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, mantenimiento.getIdEquipo());
            statement.setString(2, mantenimiento.getDescripcionTrabajo());
            statement.setString(3, mantenimiento.getEncargado());
            // Se usa .name() para guardar el nombre del enum como String en la base de datos.
            statement.setString(4, mantenimiento.getTipoMantenimiento().name());
            statement.setDate(5, mantenimiento.getFechaMantenimiento());
            statement.setString(6, mantenimiento.getObservaciones());
            statement.setInt(7, mantenimiento.getIdEmpleado());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Mantenimiento agregado exitosamente!");
                // Intenta recuperar el ID generado automáticamente por la base de datos.
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1); // El primer (y generalmente único) ID generado.
                        mantenimiento.setIdMantenimiento(idGenerado); // Asigna el ID al objeto Mantenimiento.
                    }
                }
            } else {
                System.out.println("No se pudo agregar el mantenimiento.");
            }
        }
        return idGenerado; // Retorna el ID generado o -1.
    }

    /**
     * Modifica un registro de mantenimiento existente en la base de datos.
     *
     * @param mantenimiento El objeto Mantenimiento con los datos actualizados. Es crucial que el `idMantenimiento`
     * dentro de este objeto sea el correcto para identificar el registro a modificar.
     * @param connection La conexión JDBC a la base de datos.
     * @return `true` si el mantenimiento fue modificado exitosamente, `false` en caso contrario (ej. si no se encontró el ID).
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public boolean modificarMantenimiento(Mantenimiento mantenimiento, Connection connection) throws SQLException {
        String sql = "UPDATE mantenimientos SET id_equipo = ?, descripcion_trabajo = ?, " +
                     "encargado = ?, tipo_mantenimiento = ?, fecha_mantenimiento = ?, observaciones = ?, id_empleado = ? " +
                     "WHERE id_mantenimiento = ?"; // La cláusula WHERE es esencial para la modificación.

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mantenimiento.getIdEquipo());
            statement.setString(2, mantenimiento.getDescripcionTrabajo());
            statement.setString(3, mantenimiento.getEncargado());
            statement.setString(4, mantenimiento.getTipoMantenimiento().name()); // Convertir Enum a String para guardar.
            statement.setDate(5, mantenimiento.getFechaMantenimiento());
            statement.setString(6, mantenimiento.getObservaciones());
            statement.setInt(7, mantenimiento.getIdEmpleado());
            statement.setInt(8, mantenimiento.getIdMantenimiento()); // ID para la condición WHERE.

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Mantenimiento modificado exitosamente!");
                return true; // Indica éxito
            } else {
                System.out.println("No se encontró ningún mantenimiento con el ID especificado para modificar.");
                return false; // Indica que no se modificó nada
            }
        }
    }

    /**
     * Elimina un registro de mantenimiento de la base de datos basándose en su ID.
     *
     * @param idMantenimiento El ID del mantenimiento a eliminar.
     * @param connection La conexión JDBC a la base de datos.
     * @return `true` si el mantenimiento fue eliminado exitosamente, `false` en caso contrario (ej. si no se encontró el ID).
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public boolean eliminarMantenimiento(int idMantenimiento, Connection connection) throws SQLException {
        String sql = "DELETE FROM mantenimientos WHERE id_mantenimiento = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idMantenimiento);
            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Mantenimiento eliminado exitosamente!");
                return true; // Indica éxito
            } else {
                System.out.println("No se encontró ningún mantenimiento con el ID especificado para eliminar.");
                return false; // Indica que no se eliminó nada
            }
        }
    }

    /**
     * Obtiene un registro de mantenimiento por su ID.
     * Es crucial para funcionalidades como la edición, donde se necesita precargar los datos de un mantenimiento específico.
     *
     * @param idMantenimiento El ID del mantenimiento a buscar.
     * @param connection La conexión JDBC a la base de datos.
     * @return El objeto Mantenimiento si se encuentra, o `null` si no existe un mantenimiento con el ID dado.
     * @throws SQLException Si ocurre un error de base de datos durante la consulta.
     */
    public Mantenimiento obtenerMantenimientoPorId(int idMantenimiento, Connection connection) throws SQLException {
        // Se incluyen todos los campos relevantes para reconstruir el objeto Mantenimiento.
        String sql = "SELECT id_mantenimiento, id_equipo, descripcion_trabajo, encargado, tipo_mantenimiento, fecha_mantenimiento, observaciones, id_empleado " +
                     "FROM mantenimientos WHERE id_mantenimiento = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idMantenimiento);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Mantenimiento mantenimiento = new Mantenimiento();
                    mantenimiento.setIdMantenimiento(resultSet.getInt("id_mantenimiento"));
                    mantenimiento.setIdEquipo(resultSet.getInt("id_equipo"));
                    mantenimiento.setDescripcionTrabajo(resultSet.getString("descripcion_trabajo"));
                    mantenimiento.setEncargado(resultSet.getString("encargado"));
                    // Se usa TipoMantenimiento.valueOf() para convertir el String de la BD de nuevo a un Enum.
                    mantenimiento.setTipoMantenimiento(TipoMantenimiento.valueOf(resultSet.getString("tipo_mantenimiento")));
                    mantenimiento.setFechaMantenimiento(resultSet.getDate("fecha_mantenimiento"));
                    mantenimiento.setObservaciones(resultSet.getString("observaciones"));
                    mantenimiento.setIdEmpleado(resultSet.getInt("id_empleado"));
                    // No se incluyen fecha_registro y fecha_actualizacion si tu modelo Mantenimiento no los gestiona o son solo para la BD.
                    return mantenimiento;
                }
            }
        }
        return null; // Retorna null si no se encuentra ningún mantenimiento con ese ID.
    }

    /**
     * Obtiene una lista de todos los registros de mantenimiento presentes en la base de datos.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @return Una `List` de objetos `Mantenimiento`. La lista estará vacía si no hay mantenimientos.
     * @throws SQLException Si ocurre un error de base de datos durante la consulta.
     */
    public List<Mantenimiento> obtenerTodosLosMantenimientos(Connection connection) throws SQLException {
        List<Mantenimiento> mantenimientos = new ArrayList<>();
        // Se incluye id_mantenimiento para tener un objeto Mantenimiento completo.
        String sql = "SELECT id_mantenimiento, id_equipo, descripcion_trabajo, encargado, tipo_mantenimiento, fecha_mantenimiento, observaciones, id_empleado FROM mantenimientos";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Mantenimiento mantenimiento = new Mantenimiento();
                mantenimiento.setIdMantenimiento(resultSet.getInt("id_mantenimiento")); // Importante para tener el ID
                mantenimiento.setIdEquipo(resultSet.getInt("id_equipo"));
                mantenimiento.setDescripcionTrabajo(resultSet.getString("descripcion_trabajo"));
                mantenimiento.setEncargado(resultSet.getString("encargado"));
                mantenimiento.setTipoMantenimiento(TipoMantenimiento.valueOf(resultSet.getString("tipo_mantenimiento")));
                mantenimiento.setFechaMantenimiento(resultSet.getDate("fecha_mantenimiento"));
                mantenimiento.setObservaciones(resultSet.getString("observaciones"));
                mantenimiento.setIdEmpleado(resultSet.getInt("id_empleado"));
                mantenimientos.add(mantenimiento);
            }
        }
        return mantenimientos;
    }
}