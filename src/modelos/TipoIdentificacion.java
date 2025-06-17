package modelos;

/**
 * Clase modelo para la tabla 'tipos_identificacion'.
 */
public class TipoIdentificacion {
    private int idTipoIdentificacion;
    private String tipo; // Corresponde al ENUM ('CC', 'TI', 'CE', 'PAS') en la DB

    public TipoIdentificacion() {
    }

    public TipoIdentificacion(int idTipoIdentificacion, String tipo) {
        this.idTipoIdentificacion = idTipoIdentificacion;
        this.tipo = tipo;
    }

    // Getters y Setters
    public int getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setIdTipoIdentificacion(int idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo; // Esto es crucial para que el JComboBox muestre el 'tipo' (ej. "CC", "TI")
    }
}