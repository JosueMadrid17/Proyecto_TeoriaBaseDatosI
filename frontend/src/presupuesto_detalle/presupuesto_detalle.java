package presupuesto_detalle;

public class presupuesto_detalle{
    private int id_detalle;
    private int id_presupuesto;
    private int id_subcategoria;
    private double monto_mensual_asignado;
    private String observaciones;
    private String creado_por;
    private String modificado_por;

    public presupuesto_detalle(){
    }

    public int get_id_detalle(){
        return id_detalle;
    }

    public void set_id_detalle(int id_detalle){
        this.id_detalle = id_detalle;
    }

    public int get_id_presupuesto(){
        return id_presupuesto;
    }

    public void set_id_presupuesto(int id_presupuesto){
        this.id_presupuesto = id_presupuesto;
    }

    public int get_id_subcategoria(){
        return id_subcategoria;
    }

    public void set_id_subcategoria(int id_subcategoria){
        this.id_subcategoria = id_subcategoria;
    }

    public double get_monto_mensual_asignado(){
        return monto_mensual_asignado;
    }

    public void set_monto_mensual_asignado(double monto_mensual_asignado){
        this.monto_mensual_asignado = monto_mensual_asignado;
    }

    public String get_observaciones(){
        return observaciones;
    }

    public void set_observaciones(String observaciones){
        this.observaciones = observaciones;
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