package reportes;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class reporte5 {
    private int id_obligacion;
    private String nombre_obligacion;
    private String nombre_categoria;
    private BigDecimal monto_fijo_mensual;
    private Date fecha_vencimiento_mes;
    private String estado_pago;
    private int dias_referencia;
    private Timestamp fecha_ultimo_pago;

    public int get_id_obligacion() {
        return id_obligacion;
    }

    public void set_id_obligacion(int id_obligacion) {
        this.id_obligacion = id_obligacion;
    }

    public String get_nombre_obligacion() {
        return nombre_obligacion;
    }

    public void set_nombre_obligacion(String nombre_obligacion) {
        this.nombre_obligacion = nombre_obligacion;
    }

    public String get_nombre_categoria() {
        return nombre_categoria;
    }

    public void set_nombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public BigDecimal get_monto_fijo_mensual() {
        return monto_fijo_mensual;
    }

    public void set_monto_fijo_mensual(BigDecimal monto_fijo_mensual) {
        this.monto_fijo_mensual = monto_fijo_mensual;
    }

    public Date get_fecha_vencimiento_mes() {
        return fecha_vencimiento_mes;
    }

    public void set_fecha_vencimiento_mes(Date fecha_vencimiento_mes) {
        this.fecha_vencimiento_mes = fecha_vencimiento_mes;
    }

    public String get_estado_pago() {
        return estado_pago;
    }

    public void set_estado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }

    public int get_dias_referencia() {
        return dias_referencia;
    }

    public void set_dias_referencia(int dias_referencia) {
        this.dias_referencia = dias_referencia;
    }

    public Timestamp get_fecha_ultimo_pago() {
        return fecha_ultimo_pago;
    }

    public void set_fecha_ultimo_pago(Timestamp fecha_ultimo_pago) {
        this.fecha_ultimo_pago = fecha_ultimo_pago;
    }
}
