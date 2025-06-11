package modelos;


/**
 * Clase modelo para la tabla 'usuarios'.
 * Representa la estructura de un usuario en la base de datos.
 */
public class Persona {


    /**
     * Enum para el estado del usuario.
     */
    public enum Estado {
        activo,
        inactivo
    }

    // --- Atributos de la clase que corresponden a las columnas de la tabla ---
    private String nombre_completo_razon_social;
    private int IdTipoIdentificacion;
    private String numeroIdentificacion;
    private int edad;
    private String correo;
    private String telefono;
    private String direccion;
    private int idCiudad;
    private Estado estado;

    // --- Constructor(es) ---

    /**
     * Constructor vacío por defecto.
     * Útil para frameworks ORM como Hibernate o Spring Data JPA.
     */
    public Persona() {
        // Inicializa el estado por defecto si es necesario, o déjalo para ser asignado.
        this.estado = Estado.activo; // Coincide con el DEFAULT 'activo' de la tabla SQL
    }

    /**
     * Constructor para crear un nuevo usuario antes de que sea insertado en la BD
     * (sin id_persona, fecha_creacion y fecha_actualizacion, ya que la BD los generará).
     * @param nombre_completo_razon_social Los nombres del usuario.
     * @param tipoIdentificacion El tipo de identificación del usuario (CC, TI, CE, PAS).
     * @param numeroIdentificacion El número de identificación único del usuario.
     * @param edad La edad del usuario.
     * @param correo El correo electrónico único del usuario.
     * @param contrasena La contraseña del usuario (debería estar hasheada).
     * @param idRol El ID del rol asociado al usuario.
     */
    public Persona(String nombre_completo_razon_social, int IdTipoIdentificacion,
                   String numeroIdentificacion, int edad, String correo, String telefono, String direccion, int idCiudad) {
        this.nombre_completo_razon_social = nombre_completo_razon_social;
        this.IdTipoIdentificacion = IdTipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.idCiudad = idCiudad;
        this.estado = Estado.activo; // Por defecto al crear un nuevo usuario
        // fechaCreacion y fechaActualizacion se establecerán por la BD
    }

    // --- Getters y Setters ---

  

    public String getNombres() {
        return nombre_completo_razon_social;
    }

    public void setNombres(String nombres) {
        this.nombre_completo_razon_social = nombres;
    }

    public int getTipoIdentificacion() {
        return IdTipoIdentificacion;
    }

    public void setTipoIdentificacion(int IdTipoIdentificacion) {
        this.IdTipoIdentificacion = IdTipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

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
        return "Usuario{" +
               ", nombres='" + nombre_completo_razon_social + '\'' +
               ", tipoIdentificacion=" + IdTipoIdentificacion +
               ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
               ", edad=" + edad +
               ", correo='" + correo + '\'' +
               ", telefono=" + telefono + 
               ", direccion=" + direccion +
               ", idCiudad =" + idCiudad +
               ", estado=" + estado +
               '}';
    }

 
}
