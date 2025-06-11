package modelos;

import java.math.BigDecimal; // Importa BigDecimal para manejar valores monetarios con precisión.
import java.sql.Date;       // Importa la clase Date para manejar tipos de datos de fecha de SQL.
import java.sql.Timestamp;  // Importa la clase Timestamp para manejar tipos de datos de fecha y hora de SQL.


/**
 * Clase de modelo (POJO - Plain Old Java Object) que representa la tabla 'pagos'
 * en la base de datos.
 * Esta clase se utiliza para mapear las columnas de la tabla a propiedades de Java,
 * facilitando el manejo de los datos de los pagos en la aplicación.
 */


public class Pagos {
	 // Atributos privados que corresponden a las columnas de la tabla 'pagos'.
    // Cada atributo tiene un tipo de dato Java que se alinea con el tipo de dato SQL.
	private int idPago; //Corresponde al 'id_pago' (INT AUTO_INCREMENT PRIMARY KEY)
	private int idCliente; // Corresponde al 'id_cliente' (INT NOT NULL)
	private int idMantenimiento; // Corresponde al 'id_mantenimientos' (INT NOT NULL)
	private String detalle; // Correspondiente al 'detalle' (TEXT)
	private BigDecimal valorTrabajo; // Correspondiente al 'valor_trabajo' (DECIMAL(10,2) NOT NULL)
	private BigDecimal valorPagado; // Correspondiente al 'valor_pago' (ENUM('vencido', 'pagado', 'mora') NOT NULL DEFAULT 'mora')
	private EstadoPago estadoPago;        // Corresponde a 'estado_pago' (ENUM)
    private Date fechaFacturacion;        // Corresponde a 'fecha_facturacion' (DATE)
    private int diasPlazo;                // Corresponde a 'dias_plazo' (INT)
    private Date fechaVencimiento;        // Corresponde a 'fecha_vencimiento' (DATE)
	
	
    /**
     * Enumeración para el campo 'estado_pago'.
     * Mapea los valores ENUM de la base de datos ('vencido', 'pagado', 'mora') a tipos seguros en Java.
     */
	public enum EstadoPago {
		vencido,
		pagado,
		mora
	}
	

    /**
     * Constructor para crear un nuevo objeto Pago para inserción en la base de datos.
     * Este constructor excluye los campos que son generados automáticamente por la base de datos,
     * como 'id_pago', 'valor_mora', 'fecha_registro' y 'fecha_actualizacion'.
     *
     * @param idCliente El ID del cliente asociado a este pago.
     * @param idMantenimiento El ID del mantenimiento asociado a este pago.
     * @param detalle Descripción detallada del pago.
     * @param valorTrabajo El valor total del trabajo realizado.
     * @param valorPagado El valor que ya ha sido pagado.
     * @param estadoPago El estado actual del pago (vencido, pagado, mora).
     * @param fechaFacturacion La fecha en que se facturó el pago.
     * @param diasPlazo La cantidad de días de plazo para el pago.
     * @param fechaVencimiento La fecha en que vence el pago.
     */
    public Pagos(int idCliente, int idMantenimiento, String detalle, BigDecimal valorTrabajo, BigDecimal valorPagado, EstadoPago estadoPago, Date fechaFacturacion, int diasPlazo, Date fechaVencimiento)
    {
        this.idCliente = idCliente;
        this.idMantenimiento = idMantenimiento;
        this.detalle = detalle;
        this.valorTrabajo = valorTrabajo;
        this.valorPagado = valorPagado;
        this.estadoPago = estadoPago;
        this.fechaFacturacion = fechaFacturacion;
        this.diasPlazo = diasPlazo;
        this.fechaVencimiento = fechaVencimiento;
        // Los campos idPago, valorMora, fechaRegistro y fechaActualizacion no se inicializan aquí
        // porque se espera que la base de datos los gestione automáticamente.
    }

    // --- Getters y Setters ---
    // Métodos públicos para acceder (get) y modificar (set) los atributos privados de la clase.
    // Esto asegura el encapsulamiento de los datos del objeto.

    /**
     * Obtiene el ID único del pago.
     * @return El ID del pago.
     */
    public int getIdPago() {
        return idPago;
    }

    /**
     * Establece el ID único del pago.
     * @param idPago El ID del pago a establecer.
     */
    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    /**
     * Obtiene el ID del cliente asociado al pago.
     * @return El ID del cliente.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el ID del cliente asociado al pago.
     * @param idCliente El ID del cliente a establecer.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Obtiene el ID del mantenimiento asociado al pago.
     * @return El ID del mantenimiento.
     */
    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    /**
     * Establece el ID del mantenimiento asociado al pago.
     * @param idMantenimiento El ID del mantenimiento a establecer.
     */
    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    /**
     * Obtiene el detalle o descripción del pago.
     * @return El detalle del pago.
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * Establece el detalle o descripción del pago.
     * @param detalle El detalle del pago a establecer.
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * Obtiene el valor total del trabajo realizado.
     * @return El valor del trabajo.
     */
    public BigDecimal getValorTrabajo() {
        return valorTrabajo;
    }

    /**
     * Establece el valor total del trabajo realizado.
     * @param valorTrabajo El valor del trabajo a establecer.
     */
    public void setValorTrabajo(BigDecimal valorTrabajo) {
        this.valorTrabajo = valorTrabajo;
    }

    /**
     * Obtiene el valor que ya ha sido pagado.
     * @return El valor pagado.
     */
    public BigDecimal getValorPagado() {
        return valorPagado;
    }

    /**
     * Establece el valor que ya ha sido pagado.
     * @param valorPagado El valor pagado a establecer.
     */
    public void setValorPagado(BigDecimal valorPagado) {
        this.valorPagado = valorPagado;
    }

    /**
     * Obtiene el estado actual del pago (vencido, pagado, mora).
     * @return El estado del pago.
     */
    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    /**
     * Establece el estado actual del pago.
     * @param estadoPago El estado del pago a establecer.
     */
    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    /**
     * Obtiene la fecha en que se facturó el pago.
     * @return La fecha de facturación.
     */
    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    /**
     * Establece la fecha en que se facturó el pago.
     * @param fechaFacturacion La fecha de facturación a establecer.
     */
    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    /**
     * Obtiene la cantidad de días de plazo para el pago.
     * @return Los días de plazo.
     */
    public int getDiasPlazo() {
        return diasPlazo;
    }

    /**
     * Establece la cantidad de días de plazo para el pago.
     * @param diasPlazo Los días de plazo a establecer.
     */
    public void setDiasPlazo(int diasPlazo) {
        this.diasPlazo = diasPlazo;
    }

    /**
     * Obtiene la fecha en que vence el pago.
     * @return La fecha de vencimiento.
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Establece la fecha en que vence el pago.
     * @param fechaVencimiento La fecha de vencimiento a establecer.
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    
    /**
     * Sobreescribe el método toString() para proporcionar una representación
     * de cadena legible del objeto Pago. Es útil para la depuración y el logging.
     * @return Una cadena que representa el estado del objeto Pago.
     */
    @Override
    public String toString() {
        return "Pago{" +
               "idPago=" + idPago +
               ", idCliente=" + idCliente +
               ", idMantenimiento=" + idMantenimiento +
               ", detalle='" + detalle + '\'' +
               ", valorTrabajo=" + valorTrabajo +
               ", valorPagado=" + valorPagado +
               ", estadoPago=" + estadoPago +
               ", fechaFacturacion=" + fechaFacturacion +
               ", diasPlazo=" + diasPlazo +
               ", fechaVencimiento=" + fechaVencimiento +
               '}';
    }
}
