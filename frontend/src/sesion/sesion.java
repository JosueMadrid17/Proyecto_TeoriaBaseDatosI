package sesion;
import usuario.usuario;

public class sesion{
    private usuario usuario_actual;

    public usuario get_usuario_actual(){
        return usuario_actual;
    }

    public void set_usuario_actual(usuario usuario_actual){
        this.usuario_actual = usuario_actual;
    }

    public boolean hay_sesion_activa(){
        return usuario_actual != null;
    }

    public void cerrar_sesion(){
        usuario_actual = null;
    }
}