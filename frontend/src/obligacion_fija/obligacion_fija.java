package obligacion_fija;

public class obligacion_fija{
    private int id_obligacion;
    private int id_subcategoria;
    private String nombre;
    private String descripcion_detallada;
    private double monto_fijo_mensual;
    private int dia_vencimiento;
    private boolean vigente;
    private String fecha_inicio;
    private String fecha_fin;
    private String creado_por;
    private String modificado_por;

    public obligacion_fija(){
    }

    public int get_id_obligacion(){
        return id_obligacion;
    }

    public void set_id_obligacion(int id_obligacion){
        this.id_obligacion = id_obligacion;
    }

    public int get_id_subcategoria(){
        return id_subcategoria;
    }

    public void set_id_subcategoria(int id_subcategoria){
        this.id_subcategoria = id_subcategoria;
    }

    public String get_nombre(){
        return nombre;
    }

    public void set_nombre(String nombre){
        this.nombre = nombre;
    }

    public String get_descripcion_detallada(){
        return descripcion_detallada;
    }

    public void set_descripcion_detallada(String descripcion_detallada){
        this.descripcion_detallada = descripcion_detallada;
    }

    public double get_monto_fijo_mensual(){
        return monto_fijo_mensual;
    }

    public void set_monto_fijo_mensual(double monto_fijo_mensual){
        this.monto_fijo_mensual = monto_fijo_mensual;
    }

    public int get_dia_vencimiento(){
        return dia_vencimiento;
    }

    public void set_dia_vencimiento(int dia_vencimiento){
        this.dia_vencimiento = dia_vencimiento;
    }

    public boolean is_vigente(){
        return vigente;
    }

    public void set_vigente(boolean vigente){
        this.vigente = vigente;
    }

    public String get_fecha_inicio(){
        return fecha_inicio;
    }

    public void set_fecha_inicio(String fecha_inicio){
        this.fecha_inicio = fecha_inicio;
    }

    public String get_fecha_fin(){
        return fecha_fin;
    }

    public void set_fecha_fin(String fecha_fin){
        this.fecha_fin = fecha_fin;
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