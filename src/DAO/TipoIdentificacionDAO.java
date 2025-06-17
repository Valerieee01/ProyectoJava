package DAO;

import modelos.TipoIdentificacion;
import util.ConnectionADMIN; // Asumo que esta es tu clase de conexión
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoIdentificacionDAO {

    public TipoIdentificacionDAO() {
    }

    /**
     * Obtiene todos los tipos de identificación de la base de datos.
     * @param connection La conexión JDBC a la base de datos.
     * @return Una lista de objetos TipoIdentificacion.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<TipoIdentificacion> obtenerTodosTiposIdentificacion(Connection connection) throws SQLException {
        List<TipoIdentificacion> tipos = new ArrayList<>();
        String sql = "SELECT id_tipo_identificacion, tipo FROM tipos_identificacion";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                TipoIdentificacion tipo = new TipoIdentificacion();
                tipo.setIdTipoIdentificacion(resultSet.getInt("id_tipo_identificacion"));
                tipo.setTipo(resultSet.getString("tipo"));
                tipos.add(tipo);
            }
        }
        return tipos;
    }
}