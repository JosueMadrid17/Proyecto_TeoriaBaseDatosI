package transaccion;

public class transaccion{
    private int id_transaccion;
    private int id_detalle;
    private int anio_transaccion;
    private int mes_transaccion;
    private String tipo_transaccion;
    private String descripcion;
    private double monto_transaccion;
    private String fecha_transaccion;
    private String metodo_pago;
    private int num_factura;
    private String observaciones;
    private String creado_por;
    private String modificado_por;

    private int id_presupuesto;
    private int id_subcategoria;
    private String nombre_subcategoria;
    private int id_categoria;
    private String nombre_categoria;

    public transaccion(){
    }

    public int get_id_transaccion(){
        return id_transaccion;
    }

    public void set_id_transaccion(int id_transaccion){
        this.id_transaccion = id_transaccion;
    }

    public int get_id_detalle(){
        return id_detalle;
    }

    public void set_id_detalle(int id_detalle){
        this.id_detalle = id_detalle;
    }

    public int get_anio_transaccion(){
        return anio_transaccion;
    }

    public void set_anio_transaccion(int anio_transaccion){
        this.anio_transaccion = anio_transaccion;
    }

    public int get_mes_transaccion(){
        return mes_transaccion;
    }

    public void set_mes_transaccion(int mes_transaccion){
        this.mes_transaccion = mes_transaccion;
    }

    public String get_tipo_transaccion(){
        return tipo_transaccion;
    }

    public void set_tipo_transaccion(String tipo_transaccion){
        this.tipo_transaccion = tipo_transaccion;
    }

    public String get_descripcion(){
        return descripcion;
    }

    public void set_descripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public double get_monto_transaccion(){
        return monto_transaccion;
    }

    public void set_monto_transaccion(double monto_transaccion){
        this.monto_transaccion = monto_transaccion;
    }

    public String get_fecha_transaccion(){
        return fecha_transaccion;
    }

    public void set_fecha_transaccion(String fecha_transaccion){
        this.fecha_transaccion = fecha_transaccion;
    }

    public String get_metodo_pago(){
        return metodo_pago;
    }

    public void set_metodo_pago(String metodo_pago){
        this.metodo_pago = metodo_pago;
    }

    public int get_num_factura(){
        return num_factura;
    }

    public void set_num_factura(int num_factura){
        this.num_factura = num_factura;
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

    public String get_nombre_subcategoria(){
        return nombre_subcategoria;
    }

    public void set_nombre_subcategoria(String nombre_subcategoria){
        this.nombre_subcategoria = nombre_subcategoria;
    }

    public int get_id_categoria(){
        return id_categoria;
    }

    public void set_id_categoria(int id_categoria){
        this.id_categoria = id_categoria;
    }

    public String get_nombre_categoria(){
        return nombre_categoria;
    }

    public void set_nombre_categoria(String nombre_categoria){
        this.nombre_categoria = nombre_categoria;
    }
}