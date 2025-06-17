package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; // Para manejar fecha_creacion y fecha_actualizacion
import java.util.ArrayList;
import java.util.List;

import modelos.Usuario;
import modelos.Usuario.Estado; // Importa el enum Estado

public class UsuariosDAO {

    /**
     * Constructor vacío.
     * La conexión se pasa a cada método.
     */
    public UsuariosDAO() {
        // No se requiere inicialización aquí.
    }

    /**
     * Inserta un nuevo registro de usuario en la base de datos.
     *
     * @param usuario El objeto Usuario a insertar.
     * @param connection La conexión JDBC a la base de datos.
     * @return El ID del usuario recién insertado, o -1 si no se pudo obtener.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public static int agregarUsuario(Usuario usuario, Connection connection) throws SQLException {
        String sql = "INSERT INTO usuarios (nombres, apellidos, id_tipo_identificacion, numero_identificacion, "
                   + "edad, correo, contrasena, id_rol, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idGenerado = -1;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, usuario.getNombres());
            statement.setString(2, usuario.getApellidos());
            statement.setInt(3, usuario.getTipoIdentificacion());
            statement.setString(4, usuario.getNumeroIdentificacion());
            statement.setInt(5, usuario.getEdad());
            statement.setString(6, usuario.getCorreo());
            statement.setString(7, usuario.getContrasena()); // ¡ATENCIÓN: La contraseña debería ser hasheada antes de aquí!
            statement.setInt(8, usuario.getIdRol());
            statement.setString(9, usuario.getEstado().name()); // Convertir Enum a String

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Usuario agregado exitosamente!");
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        usuario.setIdUsuario(idGenerado); // Asignar el ID al objeto Usuario
                    }
                }
            } else {
                System.out.println("No se pudo agregar el usuario.");
            }
        }
        return idGenerado;
    }

    /**
     * Modifica un registro de usuario existente en la base de datos.
     *
     * @param usuario El objeto Usuario con los datos actualizados y el ID del usuario a modificar.
     * @param connection La conexión JDBC a la base de datos.
     * @return true si el usuario fue modificado exitosamente, false en caso contrario.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public boolean modificarUsuario(Usuario usuario, Connection connection) throws SQLException {
        // No se incluyen fecha_creacion ni fecha_actualizacion (gestionadas por la DB).
        String sql = "UPDATE usuarios SET nombres = ?, apellidos = ?, id_tipo_identificacion = ?, "
                   + "numero_identificacion = ?, edad = ?, correo = ?, contrasena = ?, id_rol = ?, estado = ? "
                   + "WHERE id_usuario = ?"; // Asegúrate de que el ID es la última condición

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getNombres());
            statement.setString(2, usuario.getApellidos());
            statement.setInt(3, usuario.getTipoIdentificacion());
            statement.setString(4, usuario.getNumeroIdentificacion());
            statement.setInt(5, usuario.getEdad());
            statement.setString(6, usuario.getCorreo());
            statement.setString(7, usuario.getContrasena()); // ¡ATENCIÓN: La contraseña debería ser hasheada antes de aquí!
            statement.setInt(8, usuario.getIdRol());
            statement.setString(9, usuario.getEstado().name()); // Convertir Enum a String
            statement.setInt(10, usuario.getIdUsuario()); // ID para la cláusula WHERE

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Usuario modificado exitosamente!");
                return true;
            } else {
                System.out.println("No se encontró ningún usuario con el ID especificado para modificar.");
                return false;
            }
        }
    }

    /**
     * Elimina un registro de usuario de la base de datos basándose en su ID.
     *
     * @param idUsuario El ID del usuario a eliminar.
     * @param connection La conexión JDBC a la base de datos.
     * @return true si el usuario fue eliminado exitosamente, false en caso contrario.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public boolean eliminarUsuario(int idUsuario, Connection connection) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Usuario eliminado exitosamente!");
                return true;
            } else {
                System.out.println("No se encontró ningún usuario con el ID especificado para eliminar.");
                return false;
            }
        }
    }

    /**
     * Obtiene un registro de usuario por su ID.
     *
     * @param idUsuario El ID del usuario a buscar.
     * @param connection La conexión JDBC a la base de datos.
     * @return El objeto Usuario si se encuentra, o null.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Usuario obtenerUsuarioPorId(int idUsuario, Connection connection) throws SQLException {
        String sql = "SELECT id_usuario, nombres, apellidos, id_tipo_identificacion, numero_identificacion, "
                   + "edad, correo, contrasena, id_rol, estado, fecha_creacion, fecha_actualizacion "
                   + "FROM usuarios WHERE id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultSet.getInt("id_usuario"));
                    usuario.setNombres(resultSet.getString("nombres"));
                    usuario.setApellidos(resultSet.getString("apellidos"));
                    usuario.setTipoIdentificacion(resultSet.getInt("id_tipo_identificacion"));
                    usuario.setNumeroIdentificacion(resultSet.getString("numero_identificacion"));
                    usuario.setEdad(resultSet.getInt("edad"));
                    usuario.setCorreo(resultSet.getString("correo"));
                    usuario.setContrasena(resultSet.getString("contrasena")); // ¡Recuerda: si hasheas, no obtengas la contraseña aquí para uso directo!
                    usuario.setIdRol(resultSet.getInt("id_rol"));
                    usuario.setEstado(Estado.valueOf(resultSet.getString("estado")));
                    // Si tu modelo Usuario tiene estos atributos, descomenta y úsalos
                    // usuario.setFechaCreacion(resultSet.getTimestamp("fecha_creacion"));
                    // usuario.setFechaActualizacion(resultSet.getTimestamp("fecha_actualizacion"));
                    return usuario;
                }
            }
        }
        return null;
    }

    /**
     * Obtiene una lista de todos los registros de usuarios en la base de datos.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @return Una `List` de objetos `Usuario`. La lista estará vacía si no hay usuarios.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Usuario> obtenerTodosLosUsuarios(Connection connection) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombres, apellidos, id_tipo_identificacion, numero_identificacion, "
                   + "edad, correo, contrasena, id_rol, estado, fecha_creacion, fecha_actualizacion "
                   + "FROM usuarios";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(resultSet.getInt("id_usuario"));
                usuario.setNombres(resultSet.getString("nombres"));
                usuario.setApellidos(resultSet.getString("apellidos"));
                usuario.setTipoIdentificacion(resultSet.getInt("id_tipo_identificacion"));
                usuario.setNumeroIdentificacion(resultSet.getString("numero_identificacion"));
                usuario.setEdad(resultSet.getInt("edad"));
                usuario.setCorreo(resultSet.getString("correo"));
                usuario.setContrasena(resultSet.getString("contrasena")); // ¡Recuerda el manejo de contraseñas hasheadas!
                usuario.setIdRol(resultSet.getInt("id_rol"));
                usuario.setEstado(Estado.valueOf(resultSet.getString("estado")));
                // Si tu modelo Usuario tiene estos atributos, descomenta y úsalos
                // usuario.setFechaCreacion(resultSet.getTimestamp("fecha_creacion"));
                // usuario.setFechaActualizacion(resultSet.getTimestamp("fecha_actualizacion"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    /**
     * Obtiene un usuario por su correo electrónico (útil para login).
     *
     * @param correo El correo electrónico del usuario a buscar.
     * @param connection La conexión JDBC a la base de datos.
     * @return El objeto Usuario si se encuentra, o null.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public static Usuario obtenerUsuarioPorCorreo(String correo, Connection connection) throws SQLException {
        String sql = "SELECT id_usuario, nombres, apellidos, id_tipo_identificacion, numero_identificacion, "
                   + "edad, correo, contrasena, id_rol, estado, fecha_creacion, fecha_actualizacion "
                   + "FROM usuarios WHERE correo = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, correo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultSet.getInt("id_usuario"));
                    usuario.setNombres(resultSet.getString("nombres"));
                    usuario.setApellidos(resultSet.getString("apellidos"));
                    usuario.setTipoIdentificacion(resultSet.getInt("id_tipo_identificacion"));
                    usuario.setNumeroIdentificacion(resultSet.getString("numero_identificacion"));
                    usuario.setEdad(resultSet.getInt("edad"));
                    usuario.setCorreo(resultSet.getString("correo"));
                    usuario.setContrasena(resultSet.getString("contrasena")); // ¡Cuidado con las contraseñas hasheadas!
                    usuario.setIdRol(resultSet.getInt("id_rol"));
                    usuario.setEstado(Estado.valueOf(resultSet.getString("estado")));
                    // Si tu modelo Usuario tiene estos atributos, descomenta y úsalos
                    // usuario.setFechaCreacion(resultSet.getTimestamp("fecha_creacion"));
                    // usuario.setFechaActualizacion(resultSet.getTimestamp("fecha_actualizacion"));
                    return usuario;
                }
            }
        }
        return null;
    }
}