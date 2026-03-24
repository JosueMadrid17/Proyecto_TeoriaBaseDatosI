package reportes;
import java.math.BigDecimal;

public class reporte4 {
    private int anio;
    private int mes;
    private int id_categoria;
    private String nombre_categoria;
    private BigDecimal monto_gastado;

    public int get_anio() {
        return anio;
    }

    public void set_anio(int anio) {
        this.anio = anio;
    }

    public int get_mes() {
        return mes;
    }

    public void set_mes(int mes) {
        this.mes = mes;
    }

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

    public BigDecimal get_monto_gastado() {
        return monto_gastado;
    }

    public void set_monto_gastado(BigDecimal monto_gastado) {
        this.monto_gastado = monto_gastado;
    }
}