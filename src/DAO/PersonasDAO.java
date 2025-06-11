package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelos.Persona;
// import util.ConnectionDBA; // Ya no se importa aquí, la conexión se pasa por parámetro

/**
 * Clase Data Access Object (DAO) para la entidad Persona.
 * Proporciona métodos para interactuar con la tabla 'personas' en la base de datos,
 * permitiendo la creación, modificación, eliminación y consulta de registros de personas.
 */
public class PersonasDAO {
    // Ya no se necesita un campo de conexión aquí

    /**
     * Constructor vacío. La conexión ahora se pasa a cada método.
     */
    public PersonasDAO() {
        // No se necesita inicializar la conexión aquí
    }

    /**
     * Inserta un nuevo registro de persona en la base de datos.
     *
     * @param persona El objeto Persona a insertar, con los datos necesarios.
     * @param connection La conexión JDBC a la base de datos.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void agregarPersona(Persona persona, Connection connection) throws SQLException {
        String sql = "INSERT INTO personas (nombre_completo_razon_social, id_tipo_identificacion, " +
                     "numero_identificacion, correo, telefono, direccion, id_ciudad, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, persona.getNombres());
            statement.setInt(2, persona.getTipoIdentificacion());
            statement.setString(3, persona.getNumeroIdentificacion());
            statement.setString(4, persona.getCorreo());
            statement.setString(5, persona.getTelefono());
            statement.setString(6, persona.getDireccion());
            statement.setInt(7, persona.getIdCiudad());
            statement.setString(8, persona.getEstado().name());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Persona agregada exitosamente!");
            } else {
                System.out.println("No se pudo agregar la persona.");
            }
        }
    }

    /**
     * Modifica un registro de persona existente en la base de datos.
     *
     * @param persona El objeto Persona con los datos actualizados.
     * @param connection La conexión JDBC a la base de datos.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void modificarPersona(Persona persona, Connection connection) throws SQLException {
        String sql = "UPDATE personas SET nombre_completo_razon_social = ?, id_tipo_identificacion = ?, " +
                     "correo = ?, telefono = ?, direccion = ?, id_ciudad = ?, estado = ? " +
                     "WHERE numero_identificacion = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, persona.getNombres());
            statement.setInt(2, persona.getTipoIdentificacion());
            statement.setString(3, persona.getCorreo());
            statement.setString(4, persona.getTelefono());
            statement.setString(5, persona.getDireccion());
            statement.setInt(6, persona.getIdCiudad());
            statement.setString(7, persona.getEstado().name());
            statement.setString(8, persona.getNumeroIdentificacion());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Persona modificada exitosamente!");
            } else {
                System.out.println("No se encontró ninguna persona con el ID especificado para modificar.");
            }
        }
    }

    /**
     * Elimina un registro de persona de la base de datos por su número de identificación.
     *
     * @param numeroIdentificacion El número de identificación de la persona a eliminar.
     * @param connection La conexión JDBC a la base de datos.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void eliminarPersona(String numeroIdentificacion, Connection connection) throws SQLException {
        String sql = "DELETE FROM personas WHERE numero_identificacion = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, numeroIdentificacion);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Persona eliminada exitosamente!");
            } else {
                System.out.println("No se encontró ninguna persona con el número de identificación especificado para eliminar.");
            }
        }
    }

    /**
     * Obtiene un registro de persona de la base de datos por su número de identificación.
     *
     * @param numeroIdentificacion El número de identificación de la persona a buscar.
     * @param connection La conexión JDBC a la base de datos.
     * @return Un objeto Persona si se encuentra un registro con ese ID, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public Persona obtenerPersonaPorNumeroIdentificacion(String numeroIdentificacion, Connection connection) throws SQLException {
        String sql = "SELECT id_persona, nombre_completo_razon_social, id_tipo_identificacion, " +
                     "numero_identificacion, correo, telefono, direccion, id_ciudad, " +
                     "estado, fecha_registro, fecha_actualizacion FROM personas WHERE numero_identificacion = ?";
        Persona persona = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, numeroIdentificacion);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    persona = new Persona();
                    persona.setIdPersona(resultSet.getInt("id_persona"));
                    persona.setNombres(resultSet.getString("nombre_completo_razon_social"));
                    persona.setTipoIdentificacion(resultSet.getInt("id_tipo_identificacion"));
                    persona.setNumeroIdentificacion(resultSet.getString("numero_identificacion"));
                    persona.setCorreo(resultSet.getString("correo"));
                    persona.setTelefono(resultSet.getString("telefono"));
                    persona.setDireccion(resultSet.getString("direccion"));
                    persona.setIdCiudad(resultSet.getInt("id_ciudad"));
                    persona.setEstado(Persona.Estado.valueOf(resultSet.getString("estado")));
                }
            }
        }
        return persona;
    }

    /**
     * Obtiene todos los registros de personas de la base de datos.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @return Una lista de objetos Persona.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<Persona> obtenerTodasLasPersonas(Connection connection) throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT id_persona, nombre_completo_razon_social, id_tipo_identificacion, " +
                     "numero_identificacion, correo, telefono, direccion, id_ciudad, " +
                     "estado, fecha_registro, fecha_actualizacion FROM personas";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Persona persona = new Persona();
                persona.setIdPersona(resultSet.getInt("id_persona"));
                persona.setNombres(resultSet.getString("nombre_completo_razon_social"));
                persona.setTipoIdentificacion(resultSet.getInt("id_tipo_identificacion"));
                persona.setNumeroIdentificacion(resultSet.getString("numero_identificacion"));
                persona.setCorreo(resultSet.getString("correo"));
                persona.setTelefono(resultSet.getString("telefono"));
                persona.setDireccion(resultSet.getString("direccion"));
                persona.setIdCiudad(resultSet.getInt("id_ciudad"));
                persona.setEstado(Persona.Estado.valueOf(resultSet.getString("estado")));

                personas.add(persona);
            }
        }
        return personas;
    }
}
