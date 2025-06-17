package DAO;

import java.sql.Connection;
import java.sql.Date; // Importar java.sql.Date explícitamente para evitar ambigüedades
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Necesario para obtener las claves generadas
import java.util.ArrayList;
import java.util.List;

import modelos.Pagos;
import modelos.Pagos.EstadoPago;

public class ReportesPagosDAO {

    /**
     * Constructor vacío.
     * La conexión se pasa a cada método, lo cual es una buena práctica para la gestión de recursos
     * y permite mayor flexibilidad (ej. diferentes fuentes de conexión).
     */
    public ReportesPagosDAO() {
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
    public int agregarMantenimiento(Pagos pagos , Connection connection) throws SQLException {
        // Se añade Statement.RETURN_GENERATED_KEYS para poder recuperar el ID generado por la BD.
        String sql = "INSERT INTO pagos ( id_cliente, id_mantenimiento, detalle, valor_trabajo, valor_pagado,"
        		+ "    estado_pago, fecha_facturacion, dias_plazo)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int idGenerado = -1; // Valor por defecto si no se puede obtener el ID

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, pagos.getIdCliente());
            statement.setInt(2, pagos.getIdMantenimiento());
            statement.setString(3, pagos.getDetalle());
            statement.setBigDecimal(4, pagos.getValorTrabajo());
            statement.setBigDecimal(5, pagos.getValorPagado());
            statement.setString(6, pagos.getEstadoPago().name());
            statement.setDate(7, pagos.getFechaFacturacion());
            statement.setInt(8, pagos.getDiasPlazo());


            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Pago agregado exitosamente!");
                // Intenta recuperar el ID generado automáticamente por la base de datos.
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1); // El primer (y generalmente único) ID generado.
                        pagos.setIdMantenimiento(idGenerado); // Asigna el ID al objeto Mantenimiento.
                    }
                }
            } else {
                System.out.println("No se pudo agregar el mantenimiento.");
            }
        }
        return idGenerado; // Retorna el ID generado o -1.
    }

    /**
     * Modifica un registro de pagos existente en la base de datos.
     *
     * @param pago El objeto Pago con los datos actualizados. Es crucial que el `idPago`
     * dentro de este objeto sea el correcto para identificar el registro a modificar.
     * @param connection La conexión JDBC a la base de datos.
     * @return `true` si el pago fue modificado exitosamente, `false` en caso contrario (ej. si no se encontró el ID).
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public boolean modificarMantenimiento(Pagos pagos, Connection connection) throws SQLException {
        String sql = "UPDATE mantenimientos SET id_cliente = ?, id_mantenimiento = ?, " +
                     "detalle = ?, valor_trabajo = ?, valor_pagado = ?, estado_pago = ?, fecha_facturacion = ? , dias_plazo = ?" +
                     "WHERE id_pago = ?"; 

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setInt(1, pagos.getIdCliente());
            statement.setInt(2, pagos.getIdMantenimiento());
            statement.setString(3, pagos.getDetalle());
            statement.setBigDecimal(4, pagos.getValorTrabajo());
            statement.setBigDecimal(5, pagos.getValorPagado());
            statement.setString(6, pagos.getEstadoPago().name());
            statement.setDate(7, pagos.getFechaFacturacion());
            statement.setInt(8, pagos.getDiasPlazo());
            statement.setInt(9, pagos.getIdPago());


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
     * @param idPago El ID del pago a eliminar.
     * @param connection La conexión JDBC a la base de datos.
     * @return `true` si el pago fue eliminado exitosamente, `false` en caso contrario (ej. si no se encontró el ID).
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public boolean eliminarMantenimiento(int idPago, Connection connection) throws SQLException {
        String sql = "DELETE FROM pagos WHERE id_pago = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPago);
            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Pago eliminado exitosamente!");
                return true; // Indica éxito
            } else {
                System.out.println("No se encontró ningún pago con el ID especificado para eliminar.");
                return false; // Indica que no se eliminó nada
            }
        }
    }

    /**
     * Obtiene un registro de pago por su ID.
     * Es crucial para funcionalidades como la edición, donde se necesita precargar los datos de un mantenimiento específico.
     *
     * @param idMantenimiento El ID del mantenimiento a buscar.
     * @param connection La conexión JDBC a la base de datos.
     * @return El objeto Mantenimiento si se encuentra, o `null` si no existe un mantenimiento con el ID dado.
     * @throws SQLException Si ocurre un error de base de datos durante la consulta.
     */
    public Pagos obtenerPagosPorId(int idPago, Connection connection) throws SQLException {
        // Se incluyen todos los campos relevantes para reconstruir el objeto Mantenimiento.
        String sql = "SELECT id_mantenimiento, id_equipo, descripcion_trabajo, encargado, tipo_mantenimiento, fecha_mantenimiento, observaciones, id_empleado " +
                     "FROM mantenimientos WHERE id_mantenimiento = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPago);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Pagos pagos = new Pagos();
                    pagos.setIdPago(resultSet.getInt("id_pago"));
                    pagos.setDetalle(resultSet.getString("detalle"));
                    pagos.setValorTrabajo(resultSet.getBigDecimal("valor_trabajo"));
                    pagos.setValorPagado(resultSet.getBigDecimal("valor_pagado"));
                    pagos.setEstadoPago(EstadoPago.valueOf(resultSet.getString("estado_pago")));
                    pagos.setFechaFacturacion(resultSet.getDate("fecha_facturacion"));
                    pagos.setDiasPlazo(resultSet.getInt("dias_plazo"));
                    // No se incluyen fecha_registro y fecha_actualizacion son solo para la BD.
                    return pagos;
                }
            }
        }
        return null; // Retorna null si no se encuentra ningún pago con ese ID.
    }

    /**
     * Obtiene una lista de todos los registros de mantenimiento presentes en la base de datos.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @return Una `List` de objetos `Mantenimiento`. La lista estará vacía si no hay mantenimientos.
     * @throws SQLException Si ocurre un error de base de datos durante la consulta.
     */
    public List<Pagos> obtenerTodosLosMantenimientos(Connection connection) throws SQLException {
        List<Pagos> pago = new ArrayList<>();
        // Se incluye id_mantenimiento para tener un objeto Mantenimiento completo.
        String sql = "SELECT id_mantenimiento, id_equipo, descripcion_trabajo, encargado, tipo_mantenimiento, fecha_mantenimiento, observaciones, id_empleado FROM mantenimientos";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Pagos pagos = new Pagos();
                pagos.(resultSet.getInt("id_mantenimiento")); // Importante para tener el ID
                pagos.setIdEquipo(resultSet.getInt("id_equipo"));
                pagos.setDescripcionTrabajo(resultSet.getString("descripcion_trabajo"));
                pagos.setEncargado(resultSet.getString("encargado"));
                pagos.setTipoMantenimiento(TipoMantenimiento.valueOf(resultSet.getString("tipo_mantenimiento")));
                pagos.setFechaMantenimiento(resultSet.getDate("fecha_mantenimiento"));
                pagos.setObservaciones(resultSet.getString("observaciones"));
                pagos.setIdEmpleado(resultSet.getInt("id_empleado"));
                pago.add(pagos);
            }
        }
        return mantenimientos;
    }
}