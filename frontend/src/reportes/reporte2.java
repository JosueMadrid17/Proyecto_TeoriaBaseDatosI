package reportes;
import java.math.BigDecimal;

public class reporte2 {
    private int id_categoria;
    private String nombre_categoria;
    private BigDecimal monto_total_gastado;
    private BigDecimal porcentaje_total_gastos;
    private int cantidad_transacciones;

    public int get_id_categoria() {
        return id_categoria;
    }

    public void set_id_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String get_nombre_categoria() {
        return nombre_categoria;
    }

    public void set_nombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public BigDecimal get_monto_total_gastado() {
        return monto_total_gastado;
    }

    public void set_monto_total_gastado(BigDecimal monto_total_gastado) {
        this.monto_total_gastado = monto_total_gastado;
    }

    public BigDecimal get_porcentaje_total_gastos() {
        return porcentaje_total_gastos;
    }

    public void set_porcentaje_total_gastos(BigDecimal porcentaje_total_gastos) {
        this.porcentaje_total_gastos = porcentaje_total_gastos;
    }

    public int get_cantidad_transacciones() {
        return cantidad_transacciones;
    }

    public void set_cantidad_transacciones(int cantidad_transacciones) {
        this.cantidad_transacciones = cantidad_transacciones;
    }
}