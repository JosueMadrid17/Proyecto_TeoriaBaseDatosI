package reportes;
import java.math.BigDecimal;

public class reporte3 {
    private int id_categoria;
    private String nombre_categoria;
    private int id_subcategoria;
    private String nombre_subcategoria;
    private BigDecimal monto_presupuestado;
    private BigDecimal monto_ejecutado;
    private BigDecimal diferencia;
    private BigDecimal porcentaje_ejecucion;
    private String indicador_visual;

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

    public int get_id_subcategoria() {
        return id_subcategoria;
    }

    public void set_id_subcategoria(int id_subcategoria) {
        this.id_subcategoria = id_subcategoria;
    }

    public String get_nombre_subcategoria() {
        return nombre_subcategoria;
    }

    public void set_nombre_subcategoria(String nombre_subcategoria) {
        this.nombre_subcategoria = nombre_subcategoria;
    }

    public BigDecimal get_monto_presupuestado() {
        return monto_presupuestado;
    }

    public void set_monto_presupuestado(BigDecimal monto_presupuestado) {
        this.monto_presupuestado = monto_presupuestado;
    }

    public BigDecimal get_monto_ejecutado() {
        return monto_ejecutado;
    }

    public void set_monto_ejecutado(BigDecimal monto_ejecutado) {
        this.monto_ejecutado = monto_ejecutado;
    }

    public BigDecimal get_diferencia() {
        return diferencia;
    }

    public void set_diferencia(BigDecimal diferencia) {
        this.diferencia = diferencia;
    }

    public BigDecimal get_porcentaje_ejecucion() {
        return porcentaje_ejecucion;
    }

    public void set_porcentaje_ejecucion(BigDecimal porcentaje_ejecucion) {
        this.porcentaje_ejecucion = porcentaje_ejecucion;
    }

    public String get_indicador_visual() {
        return indicador_visual;
    }

    public void set_indicador_visual(String indicador_visual) {
        this.indicador_visual = indicador_visual;
    }
}