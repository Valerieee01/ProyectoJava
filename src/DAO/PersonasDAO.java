package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // Necesario para métodos de lectura (ej. obtenerPersonaPorId)
import java.sql.SQLException;
import java.sql.Timestamp; // Para convertir LocalDateTime a/desde Timestamp de SQL
import java.time.LocalDateTime; // Para usar LocalDateTime en el modelo Java

import modelos.Persona;
// Importa la clase de conexión que usas
import util.ConnectionDBA;

/**
 * Clase Data Access Object (DAO) para la entidad Persona.
 * Proporciona métodos para interactuar con la tabla 'personas' en la base de datos,
 * permitiendo la creación, modificación, eliminación y consulta de registros de personas.
 */
public class PersonasDAO {
    private Connection connection; // La conexión a la base de datos.

    /**
     * Constructor de PersonaDAO.
     * Recibe una conexión a la base de datos que será utilizada para todas las operaciones.
     * Es buena práctica inyectar la conexión para mantener la flexibilidad y facilitar las pruebas.
     *
     * @param connection La conexión JDBC a la base de datos.
     */
    public PersonasDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserta un nuevo registro de persona en la base de datos.
     * Los campos 'id_persona', 'fecha_creacion' y 'fecha_actualizacion' son
     * manejados automáticamente por la base de datos (auto-incremento o valores por defecto),
     * por lo que no se incluyen en la sentencia SQL INSERT.
     *
     * @param persona El objeto Persona a insertar, con los datos necesarios.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void agregarPersona(Persona persona) throws SQLException {
        // SQL para la inserción. Las columnas auto-generadas y con DEFAULT se omiten explícitamente aquí.
        String sql = "INSERT INTO personas (nombre_completo_razon_social, tipo_identificacion, " +
                     "numero_identificacion, edad, correo, telefono, direccion, id_ciudad, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Uso de try-with-resources para asegurar que el PreparedStatement se cierre automáticamente.
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Establece los valores de los parámetros en el PreparedStatement.
            statement.setString(1, persona.getNombres());
            statement.setInt(2, persona.getTipoIdentificacion());
            statement.setString(3, persona.getNumeroIdentificacion());
            statement.setInt(4, persona.getEdad());
            statement.setString(5, persona.getCorreo());
            statement.setString(6, persona.getTelefono());
            statement.setString(7, persona.getDireccion());
            statement.setInt(8, persona.getIdCiudad());
            // Para el ENUM, usamos .name() para obtener la representación de cadena.
            statement.setString(9, persona.getEstado().name());

            // Ejecuta la consulta de inserción.
            int filasAfectadas = statement.executeUpdate();

            // Verifica si la inserción fue exitosa.
            if (filasAfectadas > 0) {
                System.out.println("¡Persona agregada exitosamente!");
            } else {
                System.out.println("No se pudo agregar la persona.");
            }
        }
    }

    /**
     * Modifica un registro de persona existente en la base de datos.
     * El campo 'fecha_actualizacion' se actualiza automáticamente por la base de datos
     * si la columna está configurada con ON UPDATE CURRENT_TIMESTAMP.
     *
     * @param persona El objeto Persona con los datos actualizados.
     * El campo 'idPersona' del objeto debe estar establecido para identificar el registro a modificar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void modificarPersona(Persona persona) throws SQLException {
        // SQL para la actualización. Se actualizan los campos modificables.
        // id_persona se usa en la cláusula WHERE para identificar el registro a actualizar.
        String sql = "UPDATE personas SET nombre_completo_razon_social = ?, tipo_identificacion = ?, " +
                     "numero_identificacion = ?, edad = ?, correo = ?, telefono = ?, " +
                     "direccion = ?, id_ciudad = ?, estado = ? WHERE numero_identificacion = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Establece los nuevos valores de los parámetros.
            statement.setString(1, persona.getNombres());
            statement.setInt(2, persona.getTipoIdentificacion());
            statement.setString(3, persona.getNumeroIdentificacion());
            statement.setInt(4, persona.getEdad());
            statement.setString(5, persona.getCorreo());
            statement.setString(6, persona.getTelefono());
            statement.setString(7, persona.getDireccion());
            statement.setInt(8, persona.getIdCiudad());
            statement.setString(9, persona.getEstado().name());
            statement.setString(10, persona.getNumeroIdentificacion()); // El ID de la persona a modificar

            // Ejecuta la consulta de actualización.
            int filasAfectadas = statement.executeUpdate();

            // Verifica si la actualización fue exitosa.
            if (filasAfectadas > 0) {
                System.out.println("¡Persona modificada exitosamente!");
            } else {
                System.out.println("No se encontró ninguna persona con el ID especificado para modificar.");
            }
        }
    }

    /**
     * Elimina un registro de persona de la base de datos por su ID.
     *
     * @param idPersona El ID de la persona a eliminar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void eliminarPersona(int idPersona) throws SQLException {
        // SQL para la eliminación.
        String sql = "DELETE FROM personas WHERE id_persona = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Establece el ID de la persona a eliminar.
            statement.setInt(1, idPersona);

            // Ejecuta la consulta de eliminación.
            int filasAfectadas = statement.executeUpdate();

            // Verifica si la eliminación fue exitosa.
            if (filasAfectadas > 0) {
                System.out.println("¡Persona eliminada exitosamente!");
            } else {
                System.out.println("No se encontró ninguna persona con el ID especificado para eliminar.");
            }
        }
    }

    /**
     * Obtiene un registro de persona de la base de datos por su ID.
     * Este método es fundamental para "leer" datos y construir un objeto Persona
     * a partir de los resultados de la consulta.
     *
     * @param idPersona El ID de la persona a buscar.
     * @return Un objeto Persona si se encuentra un registro con ese ID, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public Persona obtenerPersonaPorId(int idPersona) throws SQLException {
        String sql = "SELECT id_persona, nombre_completo_razon_social, tipo_identificacion, " +
                     "numero_identificacion, edad, correo, telefono, direccion, id_ciudad, " +
                     "estado, fecha_creacion, fecha_actualizacion FROM personas WHERE id_persona = ?";
        Persona persona = null; // Se inicializa a null, se asignará si se encuentra un registro.

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPersona); // Establece el ID de la persona en la consulta.

            // Ejecuta la consulta y obtiene el conjunto de resultados.
            try (ResultSet resultSet = statement.executeQuery()) {
                // Si hay un resultado (es decir, se encontró una persona con el ID),
                // procesa los datos y crea un objeto Persona.
                if (resultSet.next()) {
                    persona = new Persona(); // Crea una nueva instancia de Persona.

                    // Asigna los valores de las columnas del ResultSet a los atributos del objeto Persona.
                    persona.setNombres(resultSet.getString("nombre_completo_razon_social"));
                    persona.setNumeroIdentificacion(resultSet.getString("numero_identificacion"));
                    persona.setEdad(resultSet.getInt("edad"));
                    persona.setCorreo(resultSet.getString("correo"));
                    persona.setTelefono(resultSet.getString("telefono"));
                    persona.setDireccion(resultSet.getString("direccion"));
                    persona.setIdCiudad(resultSet.getInt("id_ciudad"));
                    // Convierte el String de la base de datos a un valor del ENUM Estado.
                    persona.setEstado(Persona.Estado.valueOf(resultSet.getString("estado")));

                   
                }
            }
        }
        return persona; // Retorna el objeto Persona (o null si no se encontró).
    }
}