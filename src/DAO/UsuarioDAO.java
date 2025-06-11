package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelos.Usuario;


// Importa la clase de conexión actualizada

/**
 * Clase Data Access Object (DAO) para la entidad Usuario.
 * Proporciona métodos para interactuar con la tabla 'usuarios' en la base de datos,
 * permitiendo la creación, modificación y eliminación de registros de usuarios.
 */
public class UsuarioDAO {
    // La conexión a la base de datos. Ahora se gestiona externamente, por lo que se inyecta.
    private Connection connection;

    /**
     * Constructor de UsuarioDAO.
     * Recibe una conexión a la base de datos que será utilizada para todas las operaciones.
     * Es buena práctica inyectar la conexión para mantener la flexibilidad y facilitar las pruebas.
     *
     * @param connection La conexión JDBC a la base de datos.
     */
    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserta un nuevo registro de usuario en la base de datos.
     * Los campos 'id_usuario', 'fecha_creacion' y 'fecha_actualizacion' son
     * manejados automáticamente por la base de datos (auto-incremento o valores por defecto).
     *
     * @param usuario El objeto Usuario a insertar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void agregarUsuario(Usuario usuario) throws SQLException {
        // SQL para la inserción. Las columnas auto-generadas y con DEFAULT se omiten explícitamente aquí.
        String sql = "INSERT INTO usuarios (nombres, apellidos, tipo_identificacion, " +
                     "numero_identificacion, edad, correo, contrasena, id_rol, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Uso de try-with-resources para asegurar que el PreparedStatement se cierre automáticamente.
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Establece los valores de los parámetros en el PreparedStatement.
            statement.setString(1, usuario.getNombres());
            statement.setString(2, usuario.getApellidos());
            statement.setInt(3, usuario.getTipoIdentificacion()); // Asumiendo que 'tipo_identificacion' es INT en el modelo
            statement.setString(4, usuario.getNumeroIdentificacion());
            statement.setInt(5, usuario.getEdad());
            statement.setString(6, usuario.getCorreo());
            statement.setString(7, usuario.getContrasena()); // ¡Recordatorio: en producción, esta contraseña debe estar hasheada!
            statement.setInt(8, usuario.getIdRol());
            statement.setString(9, usuario.getEstado().name()); // Para el ENUM, usamos .name() para obtener la representación de cadena.

            // Ejecuta la consulta de inserción.
            int filasAfectadas = statement.executeUpdate();

            // Verifica si la inserción fue exitosa.
            if (filasAfectadas > 0) {
                System.out.println("¡Usuario agregado exitosamente!");
            } else {
                System.out.println("No se pudo agregar el usuario.");
            }
        }
    }

    /**
     * Modifica un registro de usuario existente en la base de datos.
     * El campo 'fecha_actualizacion' se actualiza automáticamente por la base de datos.
     *
     * @param usuario El objeto Usuario con los datos actualizados. El 'idUsuario' debe estar establecido.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void modificarUsuario(Usuario usuario) throws SQLException {
        // SQL para la actualización. Se actualizan todos los campos excepto el ID y las fechas auto-generadas.
        String sql = "UPDATE usuarios SET nombres = ?, apellidos = ?, tipo_identificacion = ?, " +
                     "numero_identificacion = ?, edad = ?, correo = ?, contrasena = ?, " +
                     "id_rol = ?, estado = ? WHERE id_usuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Establece los nuevos valores de los parámetros.
            statement.setString(1, usuario.getNombres());
            statement.setString(2, usuario.getApellidos());
            statement.setInt(3, usuario.getTipoIdentificacion());
            statement.setString(4, usuario.getNumeroIdentificacion());
            statement.setInt(5, usuario.getEdad());
            statement.setString(6, usuario.getCorreo());
            statement.setString(7, usuario.getContrasena()); // ¡Recordatorio: en producción, esta contraseña debe estar hasheada!
            statement.setInt(8, usuario.getIdRol());
            statement.setString(9, usuario.getEstado().name());
            statement.setInt(10, usuario.getIdUsuario()); // El ID del usuario a modificar

            // Ejecuta la consulta de actualización.
            int filasAfectadas = statement.executeUpdate();

            // Verifica si la actualización fue exitosa.
            if (filasAfectadas > 0) {
                System.out.println("¡Usuario modificado exitosamente!");
            } else {
                System.out.println("No se encontró ningún usuario con el ID especificado para modificar.");
            }
        }
    }

    /**
     * Elimina un registro de usuario de la base de datos por su ID.
     *
     * @param idUsuario El ID del usuario a eliminar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void eliminarUsuario(int idUsuario) throws SQLException {
        // SQL para la eliminación.
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Establece el ID del usuario a eliminar.
            statement.setInt(1, idUsuario);

            // Ejecuta la consulta de eliminación.
            int filasAfectadas = statement.executeUpdate();

            // Verifica si la eliminación fue exitosa.
            if (filasAfectadas > 0) {
                System.out.println("¡Usuario eliminado exitosamente!");
            } else {
                System.out.println("No se encontró ningún usuario con el ID especificado para eliminar.");
            }
        }
    }
}