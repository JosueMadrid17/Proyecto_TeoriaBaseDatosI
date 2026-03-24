package subcategoria;

public class subcategoria{
    private int id_subcategoria;
    private int id_categoria;
    private String nombre_categoria;
    private String tipo_categoria;
    private String nombre_subcategoria;
    private String descripcion;
    private boolean activo;
    private boolean es_por_defecto;
    private String creado_por;
    private String modificado_por;

    public subcategoria(){
    }

    public int get_id_subcategoria(){
        return id_subcategoria;
    }

    public void set_id_subcategoria(int id_subcategoria){
        this.id_subcategoria = id_subcategoria;
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

    public String get_tipo_categoria(){
        return tipo_categoria;
    }

    public void set_tipo_categoria(String tipo_categoria){
        this.tipo_categoria = tipo_categoria;
    }

    public String get_nombre_subcategoria(){
        return nombre_subcategoria;
    }

    public void set_nombre_subcategoria(String nombre_subcategoria){
        this.nombre_subcategoria = nombre_subcategoria;
    }

    public String get_descripcion(){
        return descripcion;
    }

    public void set_descripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public boolean is_activo(){
        return activo;
    }

    public void set_activo(boolean activo){
        this.activo = activo;
    }

    public boolean is_es_por_defecto(){
        return es_por_defecto;
    }

    public void set_es_por_defecto(boolean es_por_defecto){
        this.es_por_defecto = es_por_defecto;
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