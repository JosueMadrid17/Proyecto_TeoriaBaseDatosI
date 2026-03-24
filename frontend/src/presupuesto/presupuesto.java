package presupuesto;

public class presupuesto{
    private int id_presupuesto;
    private int id_usuario;
    private String nombre;
    private int anio_inicio;
    private int mes_inicio;
    private int anio_fin;
    private int mes_fin;
    private double total_ingresos;
    private double total_gastos;
    private double total_ahorros;
    private boolean estado_presupuesto;
    private String creado_por;
    private String modificado_por;

    public presupuesto(){
    }

    public int get_id_presupuesto(){
        return id_presupuesto;
    }

    public void set_id_presupuesto(int id_presupuesto){
        this.id_presupuesto = id_presupuesto;
    }

    public int get_id_usuario(){
        return id_usuario;
    }

    public void set_id_usuario(int id_usuario){
        this.id_usuario = id_usuario;
    }

    public String get_nombre(){
        return nombre;
    }

    public void set_nombre(String nombre){
        this.nombre = nombre;
    }

    public int get_anio_inicio(){
        return anio_inicio;
    }

    public void set_anio_inicio(int anio_inicio){
        this.anio_inicio = anio_inicio;
    }

    public int get_mes_inicio(){
        return mes_inicio;
    }

    public void set_mes_inicio(int mes_inicio){
        this.mes_inicio = mes_inicio;
    }

    public int get_anio_fin(){
        return anio_fin;
    }

    public void set_anio_fin(int anio_fin){
        this.anio_fin = anio_fin;
    }

    public int get_mes_fin(){
        return mes_fin;
    }

    public void set_mes_fin(int mes_fin){
        this.mes_fin = mes_fin;
    }

    public double get_total_ingresos(){
        return total_ingresos;
    }

    public void set_total_ingresos(double total_ingresos){
        this.total_ingresos = total_ingresos;
    }

    public double get_total_gastos(){
        return total_gastos;
    }

    public void set_total_gastos(double total_gastos){
        this.total_gastos = total_gastos;
    }

    public double get_total_ahorros(){
        return total_ahorros;
    }

    public void set_total_ahorros(double total_ahorros){
        this.total_ahorros = total_ahorros;
    }

    public boolean is_estado_presupuesto(){
        return estado_presupuesto;
    }

    public void set_estado_presupuesto(boolean estado_presupuesto){
        this.estado_presupuesto = estado_presupuesto;
    }

    public String get_creado_por(){
        return creado_por;
    }

    public void set_creado_por(String creado_por){
        this.creado_por = creado_por;
    }

    public String get_modificado_por(){
        return modificado_por;
    }

    public void set_modificado_por(String modificado_por){
        this.modificado_por = modificado_por;
    }
}