package modelos;


import java.time.LocalDateTime; // Para manejar las fechas y horas

/**
 * Clase modelo para la tabla 'usuarios'.
 * Representa la estructura de un usuario en la base de datos.
 */
public class Usuario {


    /**
     * Enum para el estado del usuario.
     */
    public enum Estado {
        activo,
        inactivo
    }

    // --- Atributos de la clase que corresponden a las columnas de la tabla ---
    private int idUsuario;
    private String nombres;
    private String apellidos;
    private int IdTipoIdentificacion;
    private String numeroIdentificacion;
    private int edad;
    private String correo;
    private String contrasena; // Considerar que esta contrasena deberia ser un hash, no texto plano.
    private int idRol;
    private Estado estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // --- Constructor(es) ---

    /**
     * Constructor vacío por defecto.
     * Útil para frameworks ORM como Hibernate o Spring Data JPA.
     */
    public Usuario() {
        // Inicializa el estado por defecto si es necesario, o déjalo para ser asignado.
        this.estado = Estado.activo; // Coincide con el DEFAULT 'activo' de la tabla SQL
    }

    /**
     * Constructor para crear un nuevo usuario antes de que sea insertado en la BD
     * (sin id_usuario, fecha_creacion y fecha_actualizacion, ya que la BD los generará).
     * @param nombres Los nombres del usuario.
     * @param apellidos Los apellidos del usuario.
     * @param tipoIdentificacion El tipo de identificación del usuario (CC, TI, CE, PAS).
     * @param numeroIdentificacion El número de identificación único del usuario.
     * @param edad La edad del usuario.
     * @param correo El correo electrónico único del usuario.
     * @param contrasena La contraseña del usuario (debería estar hasheada).
     * @param idRol El ID del rol asociado al usuario.
     */
    public Usuario(String nombres, String apellidos, int IdTipoIdentificacion,
                   String numeroIdentificacion, int edad, String correo, String contrasena, int idRol) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.IdTipoIdentificacion = IdTipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.edad = edad;
        this.correo = correo;
        this.contrasena = contrasena;
        this.idRol = idRol;
        this.estado = Estado.activo; // Por defecto al crear un nuevo usuario
        // fechaCreacion y fechaActualizacion se establecerán por la BD
    }

    // --- Getters y Setters ---

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
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
               "idUsuario=" + idUsuario +
               ", nombres='" + nombres + '\'' +
               ", apellidos='" + apellidos + '\'' +
               ", tipoIdentificacion=" + IdTipoIdentificacion +
               ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
               ", edad=" + edad +
               ", correo='" + correo + '\'' +
               ", contrasena='[PROTECTED]'" + // No mostrar la contraseña real por seguridad
               ", idRol=" + idRol +
               ", estado=" + estado +
               '}';
    }

 
}
