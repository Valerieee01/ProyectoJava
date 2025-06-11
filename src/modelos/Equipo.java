package modelos;

import java.sql.Timestamp; // Importa la clase Timestamp para manejar tipos de datos de fecha y hora de SQL.

/**
 * Clase de modelo (POJO - Plain Old Java Object) que representa la tabla 'equipos'
 * en la base de datos.
 * Esta clase se utiliza para mapear las columnas de la tabla a propiedades de Java,
 * facilitando el manejo de los datos de los equipos en la aplicación.
 */
public class Equipo {
    // Atributos privados que corresponden a las columnas de la tabla 'equipos'.
    // Cada atributo tiene un tipo de dato Java que se alinea con el tipo de dato SQL.
    private int idEquipo; // Corresponde a 'id_equipo' (INT AUTO_INCREMENT PRIMARY KEY)
    private String numeroEquipo; // Corresponde a 'numero_equipo' (VARCHAR)
    private String placa; // Corresponde a 'placa' (VARCHAR)
    private String descripcion; // Corresponde a 'descripcion' (TEXT)
    private int idCliente; // Corresponde a 'id_cliente' (INT FOREIGN KEY)
  
    /**
     * Constructor por defecto de la clase Equipo.
     * Es necesario para muchos frameworks y para la creación de objetos sin inicializar valores.
     */
    public Equipo() {
        // No hay lógica específica aquí; se usa para crear una instancia vacía.
    }

    /**
     * Constructor para crear un nuevo objeto Equipo.
     * Este constructor se utiliza típicamente cuando se va a insertar un nuevo registro
     * en la base de datos, excluyendo los campos que son generados automáticamente
     * por la base de datos (como id_equipo, fecha_registro y fecha_actualizacion).
     *
     * @param numeroEquipo El número de identificación único del equipo.
     * @param placa La placa asociada al equipo (puede ser nulo en la DB si la columna lo permite).
     * @param descripcion Una descripción detallada del equipo.
     * @param idCliente El ID del cliente al que pertenece este equipo (clave foránea).
     */
    public Equipo(String numeroEquipo, String placa, String descripcion, int idCliente) {
        this.numeroEquipo = numeroEquipo;
        this.placa = placa;
        this.descripcion = descripcion;
        this.idCliente = idCliente;
        // Los campos idEquipo, fechaRegistro y fechaActualizacion no se inicializan aquí
        // porque se espera que la base de datos los gestione automáticamente (auto-incremento,
        // DEFAULT CURRENT_TIMESTAMP, ON UPDATE CURRENT_TIMESTAMP).
    }

    // --- Getters y Setters ---
    // Métodos públicos para acceder (get) y modificar (set) los atributos privados de la clase.
    // Esto asegura el encapsulamiento de los datos del objeto.

    /**
     * Obtiene el ID único del equipo.
     * @return El ID del equipo.
     */
    public int getIdEquipo() {
        return idEquipo;
    }

    /**
     * Establece el ID único del equipo.
     * @param idEquipo El ID del equipo a establecer.
     */
    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    /**
     * Obtiene el número de identificación del equipo.
     * @return El número del equipo.
     */
    public String getNumeroEquipo() {
        return numeroEquipo;
    }

    /**
     * Establece el número de identificación del equipo.
     * @param numeroEquipo El número del equipo a establecer.
     */
    public void setNumeroEquipo(String numeroEquipo) {
        this.numeroEquipo = numeroEquipo;
    }

    /**
     * Obtiene la placa asociada al equipo.
     * @return La placa del equipo.
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Establece la placa asociada al equipo.
     * @param placa La placa del equipo a establecer.
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Obtiene la descripción detallada del equipo.
     * @return La descripción del equipo.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción detallada del equipo.
     * @param descripcion La descripción del equipo a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el ID del cliente al que pertenece el equipo.
     * @return El ID del cliente.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el ID del cliente al que pertenece el equipo.
     * @param idCliente El ID del cliente a establecer.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }



    /**
     * Sobreescribe el método toString() para proporcionar una representación
     * de cadena legible del objeto Equipo. Es útil para la depuración y el logging.
     * @return Una cadena que representa el estado del objeto Equipo.
     */
    @Override
    public String toString() {
        return "Equipo{" +
               "idEquipo=" + idEquipo +
               ", numeroEquipo='" + numeroEquipo + '\'' +
               ", placa='" + placa + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", idCliente=" + idCliente +
               '}';
    }
}
