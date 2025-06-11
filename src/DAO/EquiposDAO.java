package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // Necesario para métodos de lectura (ej. obtenerEquipoPorId)
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelos.Equipo;
// Importa la clase de conexión que usas
import util.ConnectionDBA;

/**
 * Clase Data Access Object (DAO) para la entidad Equipo.
 * Proporciona métodos para interactuar con la tabla 'equipos' en la base de datos,
 * permitiendo la creación, modificación, eliminación y consulta de registros de equipos.
 */
public class EquiposDAO {
		private static Connection connection;

    /**
     * Constructor de EquipoDAO.
     * Recibe una conexión a la base de datos que será utilizada para todas las operaciones.
     *
     * @param connection La conexión JDBC a la base de datos.
     */
    public EquiposDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserta un nuevo registro de equipo en la base de datos.
     * Los campos 'id_equipo', 'fecha_registro' y 'fecha_actualizacion' son
     * manejados automáticamente por la base de datos (auto-incremento o valores por defecto),
     * por lo que no se incluyen en la sentencia SQL INSERT.
     *
     * @param equipo El objeto Equipo a insertar, con los datos necesarios.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public static void agregarEquipo(Equipo equipo) throws SQLException {
        // SQL para la inserción. Las columnas auto-generadas y con DEFAULT se omiten explícitamente aquí.
        String sql = "INSERT INTO equipos (numero_equipo, placa, descripcion, id_cliente) " +
                     "VALUES (?, ?, ?, ?)";

        // Uso de try-with-resources para asegurar que el PreparedStatement se cierre automáticamente.
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Establece los valores de los parámetros en el PreparedStatement.
            statement.setString(1, equipo.getNumeroEquipo());
            statement.setString(2, equipo.getPlaca());
            statement.setString(3, equipo.getDescripcion());
            statement.setInt(4, equipo.getIdCliente());

            // Ejecuta la consulta de inserción.
            int filasAfectadas = statement.executeUpdate();

            // Verifica si la inserción fue exitosa.
            if (filasAfectadas > 0) {
                System.out.println("¡Equipo agregado exitosamente!");
            } else {
                System.out.println("No se pudo agregar el equipo.");
            }
        }
    }

    /**
     * Modifica un registro de equipo existente en la base de datos.
     * El campo 'fecha_actualizacion' se actualiza automáticamente por la base de datos
     * si la columna está configurada con ON UPDATE CURRENT_TIMESTAMP.
     *
     * @param equipo El objeto Equipo con los datos actualizados.
     * El campo 'idEquipo' del objeto debe estar establecido para identificar el registro a modificar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public static void modificarEquipo(Equipo equipo) throws SQLException {
        // SQL para la actualización. Se actualizan los campos modificables.
        // id_equipo se usa en la cláusula WHERE para identificar el registro a actualizar.
        String sql = "UPDATE equipos SET numero_equipo = ?, placa = ?, " +
                     "descripcion = ?, id_cliente = ? WHERE id_equipo = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Establece los nuevos valores de los parámetros.
            statement.setString(1, equipo.getNumeroEquipo());
            statement.setString(2, equipo.getPlaca());
            statement.setString(3, equipo.getDescripcion());
            statement.setInt(4, equipo.getIdCliente());
            statement.setInt(5, equipo.getIdEquipo()); // El ID del equipo a modificar

            // Ejecuta la consulta de actualización.
            int filasAfectadas = statement.executeUpdate();

            // Verifica si la actualización fue exitosa.
            if (filasAfectadas > 0) {
                System.out.println("¡Equipo modificado exitosamente!");
            } else {
                System.out.println("No se encontró ningún equipo con el ID especificado para modificar.");
            }
        }
    }

    /**
     * Elimina un registro de equipo de la base de datos por su ID.
     *
     * @param idEquipo El ID del equipo a eliminar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void eliminarEquipo(int idEquipo) throws SQLException {
        // SQL para la eliminación.
        String sql = "DELETE FROM equipos WHERE id_equipo = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Establece el ID del equipo a eliminar.
            statement.setInt(1, idEquipo);

            // Ejecuta la consulta de eliminación.
            int filasAfectadas = statement.executeUpdate();

            // Verifica si la eliminación fue exitosa.
            if (filasAfectadas > 0) {
                System.out.println("¡Equipo eliminado exitosamente!");
            } else {
                System.out.println("No se encontró ningún equipo con el ID especificado para eliminar.");
            }
        }
    }

    /**
     * Obtiene un registro de equipo de la base de datos por su ID.
     * Este método es fundamental para "leer" datos y construir un objeto Equipo
     * a partir de los resultados de la consulta.
     *
     * @param idEquipo El ID del equipo a buscar.
     * @return Un objeto Equipo si se encuentra un registro con ese ID, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public Equipo obtenerEquipoPorId(int idEquipo) throws SQLException {
        String sql = "SELECT id_equipo, numero_equipo, placa, descripcion, id_cliente FROM equipos WHERE id_equipo = ?";
        Equipo equipo = null; // Se inicializa a null, se asignará si se encuentra un registro.

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEquipo); // Establece el ID del equipo en la consulta.

            // Ejecuta la consulta y obtiene el conjunto de resultados.
            try (ResultSet resultSet = statement.executeQuery()) {
                // Si hay un resultado (es decir, se encontró un equipo con el ID),
                // procesa los datos y crea un objeto Equipo.
                if (resultSet.next()) {
                    equipo = new Equipo(); // Crea una nueva instancia de Equipo.

                    // Asigna los valores de las columnas del ResultSet a los atributos del objeto Equipo.
                    equipo.setIdEquipo(resultSet.getInt("id_equipo"));
                    equipo.setNumeroEquipo(resultSet.getString("numero_equipo"));
                    equipo.setPlaca(resultSet.getString("placa"));
                    equipo.setDescripcion(resultSet.getString("descripcion"));
                    equipo.setIdCliente(resultSet.getInt("id_cliente"));
                }
            }
        }
        return equipo; // Retorna el objeto Equipo (o null si no se encontró).
    }
    
 // --- NUEVO MÉTODO PARA OBTENER TODOS LOS EQUIPOS ---
    /**
     * Obtiene todos los registros de equipos de la base de datos.
     *
     * @return Una lista de objetos Equipo, o una lista vacía si no hay equipos.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public static List<Equipo> obtenerTodosLosEquipos() throws SQLException {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT id_equipo, numero_equipo, placa, descripcion, id_cliente FROM equipos";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) { // Ejecuta la consulta y obtiene los resultados

            while (resultSet.next()) { // Itera sobre cada fila del resultado
                Equipo equipo = new Equipo();
                equipo.setIdEquipo(resultSet.getInt("id_equipo"));
                equipo.setNumeroEquipo(resultSet.getString("numero_equipo"));
                equipo.setPlaca(resultSet.getString("placa"));
                equipo.setDescripcion(resultSet.getString("descripcion"));
                equipo.setIdCliente(resultSet.getInt("id_cliente"));
                equipos.add(equipo); // Añade el equipo a la lista
            }
        }
        return equipos; // Retorna la lista de equipos
    }

	
}
