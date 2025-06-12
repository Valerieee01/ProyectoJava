package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
     * Elimina un registro de persona de la base de datos basándose en su número de identificación.
     * Con las claves foráneas configuradas con ON DELETE CASCADE en la base de datos,
     * todos los registros relacionados en tablas dependientes serán eliminados automáticamente.
     *
     * @param numeroIdentificacion El número de identificación de la persona a eliminar.
     * @param connection La conexión JDBC a la base de datos.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void eliminarPersona(String numeroIdentificacion, Connection connection) throws SQLException {
        // 1. Obtener el id_persona de la persona a eliminar
        int idPersonaAEliminar = -1;
        String sqlGetIdPersona = "SELECT id_persona FROM personas WHERE numero_identificacion = ?";
        try (PreparedStatement psGetId = connection.prepareStatement(sqlGetIdPersona)) {
            psGetId.setString(1, numeroIdentificacion);
            try (ResultSet rs = psGetId.executeQuery()) {
                if (rs.next()) {
                    idPersonaAEliminar = rs.getInt("id_persona");
                }
            }
        }

        if (idPersonaAEliminar == -1) {
            System.out.println("No se encontró ninguna persona con el número de identificación especificado para eliminar.");
            return; // Salir si no se encuentra la persona
        }

        // 2. Eliminar la persona de la tabla 'personas'.
        // La base de datos se encargará de eliminar los registros relacionados
        // en 'clientes', 'empleados', 'proveedores', 'equipos', 'mantenimientos', 'pagos',
        // y 'empleados_mantenimiento' debido a la configuración ON DELETE CASCADE.
        String sqlDeletePersona = "DELETE FROM personas WHERE id_persona = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlDeletePersona)) {
            ps.setInt(1, idPersonaAEliminar);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Persona y todos sus registros relacionados eliminados exitosamente (por ON DELETE CASCADE)!");
            } else {
                System.out.println("No se encontró ninguna persona con el ID especificado para eliminar en la tabla principal.");
            }
        }
        // Ya no es necesario manejar transacciones manuales aquí,
        // la base de datos se encarga de la atomicidad de la eliminación en cascada.
    }


    /**
     * Obtiene un registro de persona de la base de datos por su número de identificación.
     *
     * @param numeroIdentificacion El número de identificación de la persona a buscar.
     * @param connection La conexión JDBC a la base de datos.
     * @return Un objeto Persona si se encuentra un registro con ese ID, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public static Persona obtenerPersonaPorNumeroIdentificacion(String numeroIdentificacion, Connection connection) throws SQLException {
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
     * Obtiene todos los registros de personas que son clientes de la base de datos.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @return Una lista de objetos Persona.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<Persona> obtenerTodasLasPersonasClientes(Connection connection) throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT p.id_persona, p.nombre_completo_razon_social, p.id_tipo_identificacion, " +
                "p.numero_identificacion, p.correo, p.telefono, p.direccion, p.id_ciudad, " +
                "p.estado, p.fecha_registro, p.fecha_actualizacion " +
                "FROM personas p " +
                "JOIN clientes c ON c.id_cliente = p.id_persona";

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
    

    
    /**
     * Obtiene todos los registros de personas que son empleados de la base de datos.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @return Una lista de objetos Persona.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<Persona> obtenerTodasLasPersonasEmpleados(Connection connection) throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT p.id_persona, p.nombre_completo_razon_social, p.id_tipo_identificacion, " +
                "p.numero_identificacion, p.correo, p.telefono, p.direccion, p.id_ciudad, " +
                "p.estado, p.fecha_registro, p.fecha_actualizacion " +
                "FROM personas p " +
                "JOIN empleados e ON e.id_empleado = p.id_persona";

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
    
    
 
    /**
     * Obtiene todos los registros de personas que son proveedores de la base de datos.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @return Una lista de objetos Persona.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<Persona> obtenerTodasLasPersonasProveedores(Connection connection) throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT p.id_persona, p.nombre_completo_razon_social, p.id_tipo_identificacion, " +
                "p.numero_identificacion, p.correo, p.telefono, p.direccion, p.id_ciudad, " +
                "p.estado, p.fecha_registro, p.fecha_actualizacion " +
                "FROM personas p " +
                "JOIN proveedores pr ON pr.id_proveedor = p.id_persona";

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
    /**
     * Almacena una nueva persona en la tabla 'personas'. Este es un método genérico
     * para ser usado por métodos de almacenamiento específicos (cliente, empleado, proveedor).
     * Este método gestiona la generación de ID y la inserción inicial.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @param persona El objeto Persona a almacenar. Este objeto será actualizado
     * con el 'idPersona' generado por la base de datos.
     * @return El ID generado de la persona.
     * @throws SQLException Si ocurre un error durante la interacción con la base de datos.
     */
    private int almacenarPersona(Connection connection, Persona persona) throws SQLException {
        String sqlInsertarPersona = "INSERT INTO personas (" +
                "nombre_completo_razon_social, id_tipo_identificacion, numero_identificacion, " +
                "correo, telefono, direccion, id_ciudad, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; 

        int idGenerado = -1;

        try (PreparedStatement psPersona = connection.prepareStatement(sqlInsertarPersona, Statement.RETURN_GENERATED_KEYS)) {
            psPersona.setString(1, persona.getNombres());
            psPersona.setInt(2, persona.getTipoIdentificacion());
            psPersona.setString(3, persona.getNumeroIdentificacion());
            psPersona.setString(4, persona.getCorreo());
            psPersona.setString(5, persona.getTelefono());
            psPersona.setString(6, persona.getDireccion());
            psPersona.setInt(7, persona.getIdCiudad());
            psPersona.setString(8, persona.getEstado().name());
      
            int filasAfectadas = psPersona.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("La inserción de la persona falló, no se insertaron filas.");
            }

            try (ResultSet clavesGeneradas = psPersona.getGeneratedKeys()) {
                if (clavesGeneradas.next()) {
                    idGenerado = clavesGeneradas.getInt(1);
                    persona.setIdPersona(idGenerado);
                } else {
                    throw new SQLException("La inserción de la persona falló, no se obtuvo ID generado.");
                }
            }
        }
        return idGenerado;
    }

    /**
     * Almacena un nuevo cliente en la base de datos.
     * Inserta la persona en la tabla 'personas' y luego la asocia como cliente en la tabla 'clientes'.
     * Utiliza una transacción para asegurar que ambas operaciones se completen exitosamente o ninguna lo haga.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @param persona El objeto Persona que representa al cliente a almacenar.
     * Este objeto será actualizado con el 'idPersona' generado por la base de datos.
     * @return El ID de la persona/cliente recién creada.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos,
     * se realizará un 'rollback' de la transacción.
     */
    public int almacenarCliente(Connection connection, Persona persona) throws SQLException {
        String sqlInsertarCliente = "INSERT INTO clientes (id_cliente) VALUES (?)";
        int idGenerado = -1;
        boolean autoCommitOriginal = connection.getAutoCommit();

        try {
            connection.setAutoCommit(false);

            idGenerado = almacenarPersona(connection, persona);

            try (PreparedStatement psCliente = connection.prepareStatement(sqlInsertarCliente)) {
                psCliente.setInt(1, idGenerado);
                psCliente.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error al almacenar el cliente: " + e.getMessage());
            if (connection != null) {
                try {
                    System.err.println("Realizando rollback de la transacción.");
                    connection.rollback();
                } catch (SQLException exRollback) {
                    System.err.println("Error al realizar rollback: " + exRollback.getMessage());
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(autoCommitOriginal);
            }
        }
        return idGenerado;
    }

    /**
     * Almacena un nuevo empleado en la base de datos.
     * Inserta la persona en la tabla 'personas' y luego la asocia como empleado en la tabla 'empleados'.
     * Utiliza una transacción para asegurar que ambas operaciones se completen exitosamente o ninguna lo haga.
     * Asume que la tabla 'empleados' tiene 'id_empleado' como clave foránea a 'personas.id_persona'.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @param persona El objeto Persona que representa al empleado a almacenar.
     * Este objeto será actualizado con el 'idPersona' generado por la base de datos.
     * @return El ID de la persona/empleado recién creada.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public int almacenarEmpleado(Connection connection, Persona persona) throws SQLException {
        String sqlInsertarEmpleado = "INSERT INTO empleados (id_empleado) VALUES (?)";
        int idGenerado = -1;
        boolean autoCommitOriginal = connection.getAutoCommit();

        try {
            connection.setAutoCommit(false);

            idGenerado = almacenarPersona(connection, persona);

            try (PreparedStatement psEmpleado = connection.prepareStatement(sqlInsertarEmpleado)) {
                psEmpleado.setInt(1, idGenerado);
                psEmpleado.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error al almacenar el empleado: " + e.getMessage());
            if (connection != null) {
                try {
                    System.err.println("Realizando rollback de la transacción.");
                    connection.rollback();
                } catch (SQLException exRollback) {
                    System.err.println("Error al realizar rollback: " + exRollback.getMessage());
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(autoCommitOriginal);
            }
        }
        return idGenerado;
    }

    /**
     * Almacena un nuevo proveedor en la base de datos.
     * Inserta la persona en la tabla 'personas' y luego la asocia como proveedor en la tabla 'proveedores'.
     * Utiliza una transacción para asegurar que ambas operaciones se completen exitosamente o ninguna lo haga.
     * Asume que la tabla 'proveedores' tiene 'id_proveedor' como clave foránea a 'personas.id_persona'.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @param persona El objeto Persona que representa al proveedor a almacenar.
     * Este objeto será actualizado con el 'idPersona' generado por la base de datos.
     * @return El ID de la persona/proveedor recién creada.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public int almacenarProveedor(Connection connection, Persona persona) throws SQLException {
        String sqlInsertarProveedor = "INSERT INTO proveedores (id_proveedor) VALUES (?)";
        int idGenerado = -1;
        boolean autoCommitOriginal = connection.getAutoCommit();

        try {
            connection.setAutoCommit(false);

            idGenerado = almacenarPersona(connection, persona);

            try (PreparedStatement psProveedor = connection.prepareStatement(sqlInsertarProveedor)) {
                psProveedor.setInt(1, idGenerado);
                psProveedor.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error al almacenar el proveedor: " + e.getMessage());
            if (connection != null) {
                try {
                    System.err.println("Realizando rollback de la transacción.");
                    connection.rollback();
                } catch (SQLException exRollback) {
                    System.err.println("Error al realizar rollback: " + exRollback.getMessage());
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(autoCommitOriginal);
            }
        }
        return idGenerado;
    }

    
}
