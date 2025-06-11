package modelos;

/**
 * Clase modelo para la tabla 'personas'.
 * Representa la estructura de una persona en la base de datos.
 */
public class Persona {

    /**
     * Enum para el estado de la persona.
     */
    public enum Estado {
        activo,
        inactivo
    }

    // --- Atributos de la clase que corresponden a las columnas de la tabla ---
    private int idPersona; // Añadido para el ID de la base de datos
    private String nombre_completo_razon_social;
    private int idTipoIdentificacion; // Usamos el ID INT de la tabla tipos_identificacion
    private String numeroIdentificacion;
    // private int edad; // Eliminado temporalmente ya que no está en el DDL de la tabla 'personas'
    private String correo;
    private String telefono;
    private String direccion;
    private int idCiudad; // Usamos el ID INT de la tabla ciudades
    private Estado estado;

    // --- Constructor(es) ---

    /**
     * Constructor vacío por defecto.
     */
    public Persona() {
        this.estado = Estado.activo; // Coincide con el DEFAULT 'activo' de la tabla SQL
    }

    /**
     * Constructor para crear una nueva persona.
     * Los campos 'id_persona', 'fecha_registro' y 'fecha_actualizacion' son
     * manejados automáticamente por la base de datos.
     */
    public Persona(String nombre_completo_razon_social, int idTipoIdentificacion,
                   String numeroIdentificacion, String correo, String telefono,
                   String direccion, int idCiudad) {
        this.nombre_completo_razon_social = nombre_completo_razon_social;
        this.idTipoIdentificacion = idTipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.idCiudad = idCiudad;
        this.estado = Estado.activo; // Por defecto al crear una nueva persona
    }

    // --- Getters y Setters ---

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombres() {
        return nombre_completo_razon_social;
    }

    public void setNombres(String nombres) {
        this.nombre_completo_razon_social = nombres;
    }

    public int getTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setTipoIdentificacion(int idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    // Comentado ya que 'edad' no está en el DDL de la tabla 'personas'
    /*
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    */

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    // --- Método toString() para una fácil representación de la clase ---
    @Override
    public String toString() {
        return "Persona{" +
               "idPersona=" + idPersona +
               ", nombres='" + nombre_completo_razon_social + '\'' +
               ", idTipoIdentificacion=" + idTipoIdentificacion +
               ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
               // ", edad=" + edad + // Comentado
               ", correo='" + correo + '\'' +
               ", telefono='" + telefono + '\'' +
               ", direccion='" + direccion + '\'' +
               ", idCiudad=" + idCiudad +
               ", estado=" + estado +
               '}';
    }

	
}
