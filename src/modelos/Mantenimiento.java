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
    private int idEmpleado;            // Corresponde a 'id_empleado' (INT FOREIGN KEY) // Corregido el comentario
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
     * Constructor para crear un nuevo objeto Mantenimiento (para inserción).
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
     * @param idEmpleado El ID del empleado que realizó el mantenimiento.
     */
    public Mantenimiento(int idEquipo, String descripcionTrabajo, String encargado,
                         TipoMantenimiento tipoMantenimiento, Date fechaMantenimiento,
                         String observaciones, int idEmpleado) {
        this.idEquipo = idEquipo;
        this.idEmpleado = idEmpleado;
        this.descripcionTrabajo = descripcionTrabajo;
        this.encargado = encargado;
        this.tipoMantenimiento = tipoMantenimiento;
        this.fechaMantenimiento = fechaMantenimiento;
        this.observaciones = observaciones;
        // Los campos idMantenimiento, fechaRegistro y fechaActualizacion no se inicializan aquí
        // porque se espera que la base de datos los gestione automáticamente.
    }

    /**
     * Constructor completo para crear un objeto Mantenimiento (para recuperación o actualización).
     * Incluye todos los campos de la base de datos, incluido el ID del mantenimiento.
     *
     * @param idMantenimiento El ID único del mantenimiento.
     * @param idEquipo El ID del equipo al que se le realizó el mantenimiento.
     * @param descripcionTrabajo Una descripción del trabajo realizado durante el mantenimiento.
     * @param encargado El nombre o identificación de la persona o entidad encargada del mantenimiento.
     * @param tipoMantenimiento El tipo de mantenimiento realizado (preventivo o correctivo).
     * @param fechaMantenimiento La fecha en que se realizó el mantenimiento.
     * @param observaciones Notas adicionales o comentarios sobre el mantenimiento.
     * @param idEmpleado El ID del empleado que realizó el mantenimiento.
     */
    public Mantenimiento(int idMantenimiento, int idEquipo, String descripcionTrabajo, String encargado,
                         TipoMantenimiento tipoMantenimiento, Date fechaMantenimiento, String observaciones,
                         int idEmpleado) {
        this.idMantenimiento = idMantenimiento;
        this.idEquipo = idEquipo;
        this.idEmpleado = idEmpleado;
        this.descripcionTrabajo = descripcionTrabajo;
        this.encargado = encargado;
        this.tipoMantenimiento = tipoMantenimiento;
        this.fechaMantenimiento = fechaMantenimiento;
        this.observaciones = observaciones;
        // Los campos fechaRegistro y fechaActualizacion no se inicializan aquí
        // si se espera que la base de datos los gestione.
    }


    // --- Getters y Setters ---
    // (Estos permanecen sin cambios ya que ya eran correctos para 'fechaMantenimiento' y 'idEmpleado')

    public int getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(int idMantenimiento) { this.idMantenimiento = idMantenimiento; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getDescripcionTrabajo() { return descripcionTrabajo; }
    public void setDescripcionTrabajo(String descripcionTrabajo) { this.descripcionTrabajo = descripcionTrabajo; }

    public String getEncargado() { return encargado; }
    public void setEncargado(String encargado) { this.encargado = encargado; }

    public TipoMantenimiento getTipoMantenimiento() { return tipoMantenimiento; }
    public void setTipoMantenimiento(TipoMantenimiento tipoMantenimiento) { this.tipoMantenimiento = tipoMantenimiento; }

    public Date getFechaMantenimiento() { return fechaMantenimiento; }
    public void setFechaMantenimiento(Date fechaMantenimiento) { this.fechaMantenimiento = fechaMantenimiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    // Getters y setters para fechaRegistro y fechaActualizacion (existentes, solo se muestran por completitud)
    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public Timestamp getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(Timestamp fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }


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
               ", idEmpleado=" + idEmpleado + // Añadido idEmpleado al toString
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