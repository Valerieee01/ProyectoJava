package modelos;

import java.sql.Timestamp; // Importa la clase Timestamp para manejar tipos de datos de fecha y hora de SQL.
import java.sql.Date;     // Importa la clase Date para manejar tipos de datos de fecha de SQL.

/**
 * Clase de modelo (POJO - Plain Old Java Object) que representa la tabla 'mantenimientos'
 * en la base de datos.
 * Esta clase se utiliza para mapear las columnas de la tabla a propiedades de Java,
 * facilitando el manejo de los datos de los mantenimientos en la aplicación.
 */
public class Mantenimiento {
    // Atributos privados que corresponden a las columnas de la tabla 'mantenimientos'.
    // Cada atributo tiene un tipo de dato Java que se alinea con el tipo de dato SQL.
    private int idMantenimiento;       // Corresponde a 'id_mantenimiento' (INT AUTO_INCREMENT PRIMARY KEY)
    private int idEquipo;              // Corresponde a 'id_equipo' (INT FOREIGN KEY)
    private String descripcionTrabajo; // Corresponde a 'descripcion_trabajo' (TEXT)
    private String encargado;          // Corresponde a 'encargado' (VARCHAR)
    private TipoMantenimiento tipoMantenimiento; // Corresponde a 'tipo_mantenimiento' (ENUM)
    private Date fechaMantenimiento;   // Corresponde a 'fecha_mantenimiento' (DATE)
    private String observaciones;      // Corresponde a 'observaciones' (TEXT)
    private Timestamp fechaRegistro;   // Corresponde a 'fecha_registro' (TIMESTAMP)
    private Timestamp fechaActualizacion; // Corresponde a 'fecha_actualizacion' (TIMESTAMP)

    /**
     * Enumeración para el campo 'tipo_mantenimiento'.
     * Mapea los valores ENUM de la base de datos ('preventivo', 'correctivo') a tipos seguros en Java.
     */
    public enum TipoMantenimiento {
        preventivo,
        correctivo
    }

    /**
     * Constructor por defecto de la clase Mantenimiento.
     * Es necesario para muchos frameworks y para la creación de objetos sin inicializar valores.
     */
    public Mantenimiento() {
        // No hay lógica específica aquí; se usa para crear una instancia vacía.
    }

    /**
     * Constructor para crear un nuevo objeto Mantenimiento.
     * Este constructor se utiliza típicamente cuando se va a insertar un nuevo registro
     * en la base de datos, excluyendo los campos que son generados automáticamente
     * por la base de datos (como id_mantenimiento, fecha_registro y fecha_actualizacion).
     *
     * @param idEquipo El ID del equipo al que se le realizó el mantenimiento.
     * @param descripcionTrabajo Una descripción del trabajo realizado durante el mantenimiento.
     * @param encargado El nombre o identificación de la persona o entidad encargada del mantenimiento.
     * @param tipoMantenimiento El tipo de mantenimiento realizado (preventivo o correctivo).
     * @param fechaMantenimiento La fecha en que se realizó el mantenimiento.
     * @param observaciones Notas adicionales o comentarios sobre el mantenimiento.
     */
    public Mantenimiento(int idEquipo, String descripcionTrabajo, String encargado,
                         TipoMantenimiento tipoMantenimiento, Date fechaMantenimiento,
                         String observaciones) {
        this.idEquipo = idEquipo;
        this.descripcionTrabajo = descripcionTrabajo;
        this.encargado = encargado;
        this.tipoMantenimiento = tipoMantenimiento;
        this.fechaMantenimiento = fechaMantenimiento;
        this.observaciones = observaciones;
        // Los campos idMantenimiento, fechaRegistro y fechaActualizacion no se inicializan aquí
        // porque se espera que la base de datos los gestione automáticamente.
    }

    // --- Getters y Setters ---
    // Métodos públicos para acceder (get) y modificar (set) los atributos privados de la clase.
    // Esto asegura el encapsulamiento de los datos del objeto.

    /**
     * Obtiene el ID único del mantenimiento.
     * @return El ID del mantenimiento.
     */
    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    /**
     * Establece el ID único del mantenimiento.
     * @param idMantenimiento El ID del mantenimiento a establecer.
     */
    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    /**
     * Obtiene el ID del equipo al que se realizó el mantenimiento.
     * @return El ID del equipo.
     */
    public int getIdEquipo() {
        return idEquipo;
    }

    /**
     * Establece el ID del equipo al que se realizó el mantenimiento.
     * @param idEquipo El ID del equipo a establecer.
     */
    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    /**
     * Obtiene la descripción del trabajo realizado.
     * @return La descripción del trabajo.
     */
    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    /**
     * Establece la descripción del trabajo realizado.
     * @param descripcionTrabajo La descripción del trabajo a establecer.
     */
    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

    /**
     * Obtiene el nombre o identificación del encargado del mantenimiento.
     * @return El encargado.
     */
    public String getEncargado() {
        return encargado;
    }

    /**
     * Establece el nombre o identificación del encargado del mantenimiento.
     * @param encargado El encargado a establecer.
     */
    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    /**
     * Obtiene el tipo de mantenimiento (preventivo o correctivo).
     * @return El tipo de mantenimiento.
     */
    public TipoMantenimiento getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    /**
     * Establece el tipo de mantenimiento (preventivo o correctivo).
     * @param tipoMantenimiento El tipo de mantenimiento a establecer.
     */
    public void setTipoMantenimiento(TipoMantenimiento tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    /**
     * Obtiene la fecha en que se realizó el mantenimiento.
     * @return La fecha del mantenimiento.
     */
    public Date getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    /**
     * Establece la fecha en que se realizó el mantenimiento.
     * @param fechaMantenimiento La fecha del mantenimiento a establecer.
     */
    public void setFechaMantenimiento(Date fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }

    /**
     * Obtiene las observaciones adicionales sobre el mantenimiento.
     * @return Las observaciones.
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece las observaciones adicionales sobre el mantenimiento.
     * @param observaciones Las observaciones a establecer.
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

  
    /**
     * Sobreescribe el método toString() para proporcionar una representación
     * de cadena legible del objeto Mantenimiento. Es útil para la depuración y el logging.
     * @return Una cadena que representa el estado del objeto Mantenimiento.
     */
    @Override
    public String toString() {
        return "Mantenimiento{" +
               "idMantenimiento=" + idMantenimiento +
               ", idEquipo=" + idEquipo +
               ", descripcionTrabajo='" + descripcionTrabajo + '\'' +
               ", encargado='" + encargado + '\'' +
               ", tipoMantenimiento=" + tipoMantenimiento +
               ", fechaMantenimiento=" + fechaMantenimiento +
               ", observaciones='" + observaciones + '\'' +
               ", fechaRegistro=" + fechaRegistro +
               ", fechaActualizacion=" + fechaActualizacion +
               '}';
    }
}