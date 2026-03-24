package usuario;

public class usuario{
    private int id_usuario;
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String correo_electronico;
    private String clave;
    private double salario_mensual;
    private boolean estado_usuario;
    private String creado_por;
    private String modificado_por;
    private boolean es_admin;

    public usuario(){
    }

    public int get_id_usuario(){
        return id_usuario;
    }

    public void set_id_usuario(int id_usuario){
        this.id_usuario = id_usuario;
    }

    public String get_primer_nombre(){
        return primer_nombre;
    }

    public void set_primer_nombre(String primer_nombre){
        this.primer_nombre = primer_nombre;
    }

    public String get_segundo_nombre(){
        return segundo_nombre;
    }

    public void set_segundo_nombre(String segundo_nombre){
        this.segundo_nombre = segundo_nombre;
    }

    public String get_primer_apellido(){
        return primer_apellido;
    }

    public void set_primer_apellido(String primer_apellido){
        this.primer_apellido = primer_apellido;
    }

    public String get_segundo_apellido(){
        return segundo_apellido;
    }

    public void set_segundo_apellido(String segundo_apellido){
        this.segundo_apellido = segundo_apellido;
    }

    public String get_correo_electronico(){
        return correo_electronico;
    }

    public void set_correo_electronico(String correo_electronico){
        this.correo_electronico = correo_electronico;
    }

    public String get_clave(){
        return clave;
    }

    public void set_clave(String clave){
        this.clave = clave;
    }

    public double get_salario_mensual(){
        return salario_mensual;
    }

    public void set_salario_mensual(double salario_mensual){
        this.salario_mensual = salario_mensual;
    }

    public boolean is_estado_usuario(){
        return estado_usuario;
    }

    public void set_estado_usuario(boolean estado_usuario){
        this.estado_usuario = estado_usuario;
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

    public boolean is_es_admin(){
        return es_admin;
    }

    public void set_es_admin(boolean es_admin){
        this.es_admin = es_admin;
    }
}