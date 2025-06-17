package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Necesario para obtener las claves generadas
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import modelos.Pagos;
import modelos.Pagos.EstadoPago;

public class ReportesPagosDAO {

    public ReportesPagosDAO() {

    }

    /**
     * Inserta un nuevo registro de pago en la base de datos.
     * Retorna el ID generado por la base de datos para el nuevo pago.
     *
     * @param pago El objeto Pagos a insertar, con los datos necesarios.
     * @param connection La conexión JDBC a la base de datos.
     * @return El ID (int) generado por la base de datos para el nuevo pago, o -1 si no se pudo obtener un ID.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public int agregarPago(Pagos pago, Connection connection) throws SQLException { // Renombrado de agregarMantenimiento a agregarPago y parámetro a 'pago'
        // Las columnas 'valor_mora', 'fecha_vencimiento', 'fecha_registro', 'fecha_actualizacion'
        // son GENERADAS/DEFAULT por la DB, NO se incluyen en el INSERT.
        String sql = "INSERT INTO pagos (id_cliente, id_mantenimiento, detalle, valor_trabajo, valor_pagado, "
                + "estado_pago, fecha_facturacion, dias_plazo)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int idGenerado = -1;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, pago.getIdCliente());
            statement.setInt(2, pago.getIdMantenimiento());
            statement.setString(3, pago.getDetalle());
            statement.setBigDecimal(4, pago.getValorTrabajo());
            statement.setBigDecimal(5, pago.getValorPagado());
            statement.setString(6, pago.getEstadoPago().name()); // Convertir Enum a String
            statement.setDate(7, pago.getFechaFacturacion());
            statement.setInt(8, pago.getDiasPlazo());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Pago agregado exitosamente!");
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        pago.setIdPago(idGenerado); // Asigna el ID al objeto Pago
                    }
                }
            } else {
                System.out.println("No se pudo agregar el pago.");
            }
        }
        return idGenerado;
    }

    /**
     * Modifica un registro de pago existente en la base de datos.
     * Las columnas generadas por la DB (valor_mora, fecha_vencimiento, fecha_actualizacion)
     * no se incluyen en el UPDATE ya que se gestionan automáticamente.
     *
     * @param pago El objeto Pagos con los datos actualizados y el ID del pago a modificar.
     * @param connection La conexión JDBC a la base de datos.
     * @return `true` si el pago fue modificado exitosamente, `false` en caso contrario (ej. si no se encontró el ID).
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public boolean modificarPago(Pagos pago, Connection connection) throws SQLException { // Renombrado de modificarMantenimiento a modificarPago y parámetro a 'pago'
        String sql = "UPDATE pagos SET id_cliente = ?, id_mantenimiento = ?, " +
                     "detalle = ?, valor_trabajo = ?, valor_pagado = ?, estado_pago = ?, " +
                     "fecha_facturacion = ?, dias_plazo = ? " + // Las columnas generadas por DB no se actualizan aquí
                     "WHERE id_pago = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pago.getIdCliente());
            statement.setInt(2, pago.getIdMantenimiento());
            statement.setString(3, pago.getDetalle());
            statement.setBigDecimal(4, pago.getValorTrabajo());
            statement.setBigDecimal(5, pago.getValorPagado());
            statement.setString(6, pago.getEstadoPago().name()); // Convertir Enum a String
            statement.setDate(7, pago.getFechaFacturacion());
            statement.setInt(8, pago.getDiasPlazo());
            statement.setInt(9, pago.getIdPago()); // ID para la cláusula WHERE

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Pago modificado exitosamente!");
                return true;
            } else {
                System.out.println("No se encontró ningún pago con el ID especificado para modificar.");
                return false;
            }
        }
    }

    /**
     * Elimina un registro de pago de la base de datos basándose en su ID.
     *
     * @param idPago El ID del pago a eliminar.
     * @param connection La conexión JDBC a la base de datos.
     * @return `true` si el pago fue eliminado exitosamente, `false` en caso contrario (ej. si no se encontró el ID).
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public boolean eliminarPago(int idPago, Connection connection) throws SQLException { // Renombrado de eliminarMantenimiento a eliminarPago
        String sql = "DELETE FROM pagos WHERE id_pago = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPago);
            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Pago eliminado exitosamente!");
                return true;
            } else {
                System.out.println("No se encontró ningún pago con el ID especificado para eliminar.");
                return false;
            }
        }
    }

    /**
     * Obtiene un registro de pago por su ID.
     * Incluye todos los campos de la base de datos, incluso los generados automáticamente.
     *
     * @param idPago El ID del pago a buscar.
     * @param connection La conexión JDBC a la base de datos.
     * @return El objeto Pagos si se encuentra, o `null` si no existe un pago con el ID dado.
     * @throws SQLException Si ocurre un error de base de datos durante la consulta.
     */
    public Pagos obtenerPagoPorId(int idPago, Connection connection) throws SQLException { // Renombrado a obtenerPagoPorId
        // Incluye TODAS las columnas, incluidas las generadas, para tener el objeto completo
        String sql = "SELECT id_pago, id_cliente, id_mantenimiento, detalle, valor_trabajo, valor_pagado, valor_mora, "
                + "estado_pago, fecha_facturacion, dias_plazo, fecha_vencimiento, fecha_registro, fecha_actualizacion "
                + "FROM pagos WHERE id_pago = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPago);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Pagos pago = new Pagos();
                    pago.setIdPago(resultSet.getInt("id_pago"));
                    pago.setIdCliente(resultSet.getInt("id_cliente")); // ¡Faltaba leer estos IDs!
                    pago.setIdMantenimiento(resultSet.getInt("id_mantenimiento")); // ¡Faltaba leer estos IDs!
                    pago.setDetalle(resultSet.getString("detalle"));
                    pago.setValorTrabajo(resultSet.getBigDecimal("valor_trabajo"));
                    pago.setValorPagado(resultSet.getBigDecimal("valor_pagado"));
                    pago.setValorMora(resultSet.getBigDecimal("valor_mora")); // Leer el valor de la mora
                    pago.setEstadoPago(EstadoPago.valueOf(resultSet.getString("estado_pago")));
                    pago.setFechaFacturacion(resultSet.getDate("fecha_facturacion"));
                    pago.setDiasPlazo(resultSet.getInt("dias_plazo"));
                    pago.setFechaVencimiento(resultSet.getDate("fecha_vencimiento")); // Leer la fecha de vencimiento
                    // Si tienes atributos Timestamp en Pagos, también léelos:
                    // pago.setFechaRegistro(resultSet.getTimestamp("fecha_registro"));
                    // pago.setFechaActualizacion(resultSet.getTimestamp("fecha_actualizacion"));
                    return pago;
                }
            }
        }
        return null;
    }

    /**
     * Obtiene una lista de todos los registros de pagos presentes en la base de datos.
     * Incluye todos los campos de la base de datos, incluso los generados automáticamente.
     *
     * @param connection La conexión JDBC a la base de datos.
     * @return Una `List` de objetos `Pagos`. La lista estará vacía si no hay pagos.
     * @throws SQLException Si ocurre un error de base de datos durante la consulta.
     */
    public List<Pagos> obtenerTodosLosPagos(Connection connection) throws SQLException { // Renombrado de obtenerTodosLosMantenimientos a obtenerTodosLosPagos
        List<Pagos> pagosList = new ArrayList<>(); // Renombrado 'pago' a 'pagosList' para claridad
        // Incluye TODAS las columnas, incluidas las generadas, para tener los objetos completos
        String sql = "SELECT id_pago, id_cliente, id_mantenimiento, detalle, valor_trabajo, valor_pagado, valor_mora, "
                + "estado_pago, fecha_facturacion, dias_plazo, fecha_vencimiento, fecha_registro, fecha_actualizacion "
                + "FROM pagos"; // ¡IMPORTANTE! Quitar el WHERE para obtener TODOS

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Pagos pago = new Pagos(); // Usar un nombre de variable singular 'pago' dentro del bucle
                pago.setIdPago(resultSet.getInt("id_pago"));
                pago.setIdCliente(resultSet.getInt("id_cliente"));
                pago.setIdMantenimiento(resultSet.getInt("id_mantenimiento"));
                pago.setDetalle(resultSet.getString("detalle"));
                pago.setValorTrabajo(resultSet.getBigDecimal("valor_trabajo"));
                pago.setValorPagado(resultSet.getBigDecimal("valor_pagado"));
                pago.setValorMora(resultSet.getBigDecimal("valor_mora"));
                pago.setEstadoPago(EstadoPago.valueOf(resultSet.getString("estado_pago")));
                pago.setFechaFacturacion(resultSet.getDate("fecha_facturacion"));
                pago.setDiasPlazo(resultSet.getInt("dias_plazo"));
                pago.setFechaVencimiento(resultSet.getDate("fecha_vencimiento"));
                // Si tienes atributos Timestamp en Pagos, también léelos:
                // pago.setFechaRegistro(resultSet.getTimestamp("fecha_registro"));
                // pago.setFechaActualizacion(resultSet.getTimestamp("fecha_actualizacion"));
                pagosList.add(pago); // Añadir el objeto 'pago' a la lista
            }
        }
        return pagosList;
    }
    
    
    /**
     * Obtiene la cuenta de pagos por cada estado de pago (para Pie Chart).
     * @param connection La conexión JDBC a la base de datos.
     * @return Un mapa donde la clave es el estado (String) y el valor es la cuenta (Integer).
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Map<String, Integer> obtenerConteoPagosPorEstado(Connection connection) throws SQLException {
        Map<String, Integer> conteoPorEstado = new LinkedHashMap<>();
        String sql = "SELECT estado_pago, COUNT(*) as conteo FROM pagos GROUP BY estado_pago";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                conteoPorEstado.put(resultSet.getString("estado_pago"), resultSet.getInt("conteo"));
            }
        }
        return conteoPorEstado;
    }

    /**
     * Obtiene la suma de valor_trabajo, valor_pagado y valor_mora por cliente (para Bar Chart).
     * @param connection La conexión JDBC a la base de datos.
     * @return Una lista de Mapas, donde cada mapa contiene "id_cliente", "valor_trabajo_total", "valor_pagado_total", "valor_mora_total".
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Map<String, Object>> obtenerValoresAgregadosPorCliente(Connection connection) throws SQLException {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT id_cliente, SUM(valor_trabajo) as total_trabajo, " +
                     "SUM(valor_pagado) as total_pagado, SUM(valor_mora) as total_mora " +
                     "FROM pagos GROUP BY id_cliente ORDER BY id_cliente";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Map<String, Object> fila = new LinkedHashMap<>();
                fila.put("id_cliente", resultSet.getInt("id_cliente"));
                fila.put("total_trabajo", resultSet.getBigDecimal("total_trabajo"));
                fila.put("total_pagado", resultSet.getBigDecimal("total_pagado"));
                fila.put("total_mora", resultSet.getBigDecimal("total_mora"));
                resultados.add(fila);
            }
        }
        return resultados;
    }
}