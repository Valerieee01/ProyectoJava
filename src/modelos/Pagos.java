package modelos;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp; // Mantén este si planeas usar fecha_registro/fecha_actualizacion en el futuro

/**
 * Clase de modelo (POJO - Plain Old Java Object) que representa la tabla 'pagos'
 * en la base de datos.
 */
public class Pagos {
    private int idPago;
    private int idCliente;
    private int idMantenimiento;
    private String detalle;
    private BigDecimal valorTrabajo;
    private BigDecimal valorPagado;
    private BigDecimal valorMora; // Corresponde a 'valor_mora' (DECIMAL(10,2) NOT NULL) calculado por la db
    private EstadoPago estadoPago;
    private Date fechaFacturacion;
    private int diasPlazo;
    private Date fechaVencimiento;
    // Si deseas mapear las columnas de tiempo de la BD, añádelas:
    // private Timestamp fechaRegistro;
    // private Timestamp fechaActualizacion;

    /**
     * Enumeración para el campo 'estado_pago'.
     */
    public enum EstadoPago {
        vencido,
        pagado,
        mora
    }

    /**
     * Constructor para crear un nuevo objeto Pago para inserción en la base de datos.
     * Este constructor excluye los campos que son generados automáticamente por la base de datos,
     * como 'id_pago', 'valor_mora', 'fecha_vencimiento', 'fecha_registro' y 'fecha_actualizacion'.
     *
     * @param idCliente El ID del cliente asociado a este pago.
     * @param idMantenimiento El ID del mantenimiento asociado a este pago.
     * @param detalle Descripción detallada del pago.
     * @param valorTrabajo El valor total del trabajo realizado.
     * @param valorPagado El valor que ya ha sido pagado.
     * @param estadoPago El estado actual del pago (vencido, pagado, mora).
     * @param fechaFacturacion La fecha en que se facturó el pago.
     * @param diasPlazo La cantidad de días de plazo para el pago.
     */
    public Pagos(int idCliente, int idMantenimiento, String detalle, BigDecimal valorTrabajo, BigDecimal valorPagado, EstadoPago estadoPago, Date fechaFacturacion, int diasPlazo) {
        this.idCliente = idCliente;
        this.idMantenimiento = idMantenimiento;
        this.detalle = detalle;
        this.valorTrabajo = valorTrabajo;
        this.valorPagado = valorPagado;
        this.estadoPago = estadoPago;
        this.fechaFacturacion = fechaFacturacion;
        this.diasPlazo = diasPlazo;
        // Los campos idPago, valorMora, fechaVencimiento, fechaRegistro y fechaActualizacion
        // no se inicializan aquí porque se espera que la base de datos los gestione.
    }

    /**
     * Constructor para RECONSTRUIR un objeto Pago DESDE la base de datos.
     * INCLUYE todos los campos, incluso los generados o auto-incrementales.
     *
     * @param idPago El ID único del pago.
     * @param idCliente El ID del cliente asociado al pago.
     * @param idMantenimiento El ID del mantenimiento asociado al pago.
     * @param detalle Descripción detallada del pago.
     * @param valorTrabajo El valor total del trabajo realizado.
     * @param valorPagado El valor que ya ha sido pagado.
     * @param valorMora El valor calculado de la mora (generado por la DB).
     * @param estadoPago El estado actual del pago.
     * @param fechaFacturacion La fecha de facturación.
     * @param diasPlazo La cantidad de días de plazo.
     * @param fechaVencimiento La fecha de vencimiento (generada por la DB).
     */
    public Pagos(int idPago, int idCliente, int idMantenimiento, String detalle, BigDecimal valorTrabajo, BigDecimal valorPagado, BigDecimal valorMora, EstadoPago estadoPago, Date fechaFacturacion, int diasPlazo, Date fechaVencimiento) {
        this.idPago = idPago;
        this.idCliente = idCliente;
        this.idMantenimiento = idMantenimiento;
        this.detalle = detalle;
        this.valorTrabajo = valorTrabajo;
        this.valorPagado = valorPagado;
        this.valorMora = valorMora;
        this.estadoPago = estadoPago;
        this.fechaFacturacion = fechaFacturacion;
        this.diasPlazo = diasPlazo;
        this.fechaVencimiento = fechaVencimiento;
        // Si incluyes Timestamp, también deberían ir aquí:
        // this.fechaRegistro = fechaRegistro;
        // this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Constructor vacío para cuando se usan setters (útil para mapeo de ResultSet).
     */
    public Pagos() {
    }

    // --- Getters y Setters ---

    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(int idMantenimiento) { this.idMantenimiento = idMantenimiento; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public BigDecimal getValorTrabajo() { return valorTrabajo; }
    public void setValorTrabajo(BigDecimal valorTrabajo) { this.valorTrabajo = valorTrabajo; }

    public BigDecimal getValorPagado() { return valorPagado; }
    public void setValorPagado(BigDecimal valorPagado) { this.valorPagado = valorPagado; }

    public BigDecimal getValorMora() { return valorMora; }
    public void setValorMora(BigDecimal valorMora) { this.valorMora = valorMora; }

    public EstadoPago getEstadoPago() { return estadoPago; }
    public void setEstadoPago(EstadoPago estadoPago) { this.estadoPago = estadoPago; }

    public Date getFechaFacturacion() { return fechaFacturacion; }
    public void setFechaFacturacion(Date fechaFacturacion) { this.fechaFacturacion = fechaFacturacion; }

    public int getDiasPlazo() { return diasPlazo; }
    public void setDiasPlazo(int diasPlazo) { this.diasPlazo = diasPlazo; }

    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    // Si incluyes Timestamp, añade sus getters/setters:
    // public Timestamp getFechaRegistro() { return fechaRegistro; }
    // public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    // public Timestamp getFechaActualizacion() { return fechaActualizacion; }
    // public void setFechaActualizacion(Timestamp fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    @Override
    public String toString() {
        return "Pago{" +
               "idPago=" + idPago +
               ", idCliente=" + idCliente +
               ", idMantenimiento=" + idMantenimiento +
               ", detalle='" + detalle + '\'' +
               ", valorTrabajo=" + valorTrabajo +
               ", valorPagado=" + valorPagado +
               ", valorMora=" + valorMora + // Incluir en toString
               ", estadoPago=" + estadoPago +
               ", fechaFacturacion=" + fechaFacturacion +
               ", diasPlazo=" + diasPlazo +
               ", fechaVencimiento=" + fechaVencimiento + // Incluir en toString
               // Si incluyes Timestamp, añade aquí:
               // ", fechaRegistro=" + fechaRegistro +
               // ", fechaActualizacion=" + fechaActualizacion +
               '}';
    }
}