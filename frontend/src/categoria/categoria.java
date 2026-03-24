package categoria;

public class categoria{
    private int id_categoria;
    private String nombre;
    private String descripcion;
    private String tipo_categoria;
    private String creado_por;
    private String modificado_por;

    public categoria(){
    }

    public int get_id_categoria(){
        return id_categoria;
    }

    public void set_id_categoria(int id_categoria){
        this.id_categoria = id_categoria;
    }

    public String get_nombre(){
        return nombre;
    }

    public void set_nombre(String nombre){
        this.nombre = nombre;
    }

    public String get_descripcion(){
        return descripcion;
    }

    public void set_descripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String get_tipo_categoria(){
        return tipo_categoria;
    }

    public void set_tipo_categoria(String tipo_categoria){
        this.tipo_categoria = tipo_categoria;
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