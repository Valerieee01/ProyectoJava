package DAO;

import modelos.Rol;
import util.ConnectionADMIN; // Asumo que esta es tu clase de conexión
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    public RolDAO() {
    }

    /**
     * Obtiene todos los roles de la base de datos.
     * @param connection La conexión JDBC a la base de datos.
     * @return Una lista de objetos Rol.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public List<Rol> obtenerTodosRoles(Connection connection) throws SQLException {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT id_rol, nombre_rol FROM roles";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Rol rol = new Rol();
                rol.setIdRol(resultSet.getInt("id_rol"));
                rol.setNombreRol(resultSet.getString("nombre_rol"));
                roles.add(rol);
            }
        }
        return roles;
    }

    /**
     * Obtiene un rol de la base de datos basándose en su ID.
     * Es útil para obtener el nombre legible de un rol a partir de su ID numérico.
     * @param idRol El ID del rol a buscar.
     * @param connection La conexión JDBC a la base de datos.
     * @return El objeto Rol si se encuentra, o null.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public Rol obtenerRolPorId(int idRol, Connection connection) throws SQLException {
        String sql = "SELECT id_rol, nombre_rol FROM roles WHERE id_rol = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRol);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Rol rol = new Rol();
                    rol.setIdRol(resultSet.getInt("id_rol"));
                    rol.setNombreRol(resultSet.getString("nombre_rol"));
                    return rol;
                }
            }
        }
        return null; // Retorna null si no se encuentra ningún rol con ese ID.
    }
}