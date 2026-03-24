package reportes;
import java.math.BigDecimal;

public class reporte1 {
    private int anio;
    private int mes;
    private BigDecimal ingresos;
    private BigDecimal gastos;
    private BigDecimal ahorros;
    private BigDecimal balance;

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

    public BigDecimal get_ingresos() {
        return ingresos;
    }

    public void set_ingresos(BigDecimal ingresos) {
        this.ingresos = ingresos;
    }

    public BigDecimal get_gastos() {
        return gastos;
    }

    public void set_gastos(BigDecimal gastos) {
        this.gastos = gastos;
    }

    public BigDecimal get_ahorros() {
        return ahorros;
    }

    public void set_ahorros(BigDecimal ahorros) {
        this.ahorros = ahorros;
    }

    public BigDecimal get_balance() {
        return balance;
    }

    public void set_balance(BigDecimal balance) {
        this.balance = balance;
    }
}