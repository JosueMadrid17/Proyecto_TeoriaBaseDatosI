package usuario;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class usuario_db{
    private final String correo_admin = "admin@gmail.com";
    private final String clave_admin = "admin1234";

    public boolean insertar_usuario(usuario u){
        String sql = "{call sp_insertar_usuario(?, ?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setString(1, u.get_primer_nombre());
            cs.setString(2, u.get_segundo_nombre());
            cs.setString(3, u.get_primer_apellido());
            cs.setString(4, u.get_segundo_apellido());
            cs.setString(5, u.get_correo_electronico());
            cs.setString(6, u.get_clave());
            cs.setDouble(7, u.get_salario_mensual());
            cs.setString(8, u.get_creado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al insertar usuario:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public usuario iniciar_sesion(String correo_electronico, String clave){
        String sql = "{call sp_iniciar_sesion_usuario(?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setString(1, correo_electronico);
            cs.setString(2, clave);

            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                usuario u = new usuario();

                u.set_id_usuario(rs.getInt("id_usuario"));
                u.set_primer_nombre(rs.getString("primer_nombre"));
                u.set_segundo_nombre(rs.getString("segundo_nombre"));
                u.set_primer_apellido(rs.getString("primer_apellido"));
                u.set_segundo_apellido(rs.getString("segundo_apellido"));
                u.set_correo_electronico(rs.getString("correo_electronico"));
                u.set_clave(rs.getString("clave"));
                u.set_salario_mensual(rs.getDouble("salario_mensual"));
                u.set_estado_usuario(rs.getBoolean("estado_usuario"));
                u.set_creado_por(rs.getString("creado_por"));
                u.set_modificado_por(rs.getString("modificado_por"));

                if(correo_electronico.equals(correo_admin) && clave.equals(clave_admin)){
                    u.set_es_admin(true);
                }else{
                    u.set_es_admin(false);
                }
                return u;
            }

        }catch(SQLException e){
            System.out.println("Error al iniciar sesion:");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public usuario consultar_usuario(int id_usuario){
        String sql = "{call sp_consultar_usuario(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_usuario);

            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                usuario u = new usuario();

                u.set_id_usuario(rs.getInt("id_usuario"));
                u.set_primer_nombre(rs.getString("primer_nombre"));
                u.set_segundo_nombre(rs.getString("segundo_nombre"));
                u.set_primer_apellido(rs.getString("primer_apellido"));
                u.set_segundo_apellido(rs.getString("segundo_apellido"));
                u.set_correo_electronico(rs.getString("correo_electronico"));
                u.set_clave(rs.getString("clave"));
                u.set_salario_mensual(rs.getDouble("salario_mensual"));
                u.set_estado_usuario(rs.getBoolean("estado_usuario"));
                u.set_creado_por(rs.getString("creado_por"));
                u.set_modificado_por(rs.getString("modificado_por"));

                return u;
            }

        }catch(SQLException e){
            System.out.println("Error al consultar usuario:");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar_usuario(usuario u){
        String sql = "{call sp_actualizar_usuario(?, ?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, u.get_id_usuario());
            cs.setString(2, u.get_primer_nombre());
            cs.setString(3, u.get_segundo_nombre());
            cs.setString(4, u.get_primer_apellido());
            cs.setString(5, u.get_segundo_apellido());
            cs.setString(6, u.get_clave());
            cs.setDouble(7, u.get_salario_mensual());
            cs.setString(8, u.get_modificado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al modificar usuario:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminar_usuario(int id_usuario){
        String sql = "{call sp_eliminar_usuario(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_usuario);
            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al eliminar usuario:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<usuario> listar_usuarios(){
        ArrayList<usuario> lista_usuarios = new ArrayList<>();
        String sql = "{call sp_listar_usuarios()}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql);
            ResultSet rs = cs.executeQuery()){

            while(rs.next()){
                usuario u = new usuario();
                
                u.set_id_usuario(rs.getInt("id_usuario"));
                u.set_primer_nombre(rs.getString("primer_nombre"));
                u.set_segundo_nombre(rs.getString("segundo_nombre"));
                u.set_primer_apellido(rs.getString("primer_apellido"));
                u.set_segundo_apellido(rs.getString("segundo_apellido"));
                u.set_correo_electronico(rs.getString("correo_electronico"));
                u.set_clave(rs.getString("clave"));
                u.set_salario_mensual(rs.getDouble("salario_mensual"));
                u.set_estado_usuario(rs.getBoolean("estado_usuario"));
                u.set_creado_por(rs.getString("creado_por"));
                u.set_modificado_por(rs.getString("modificado_por"));

                lista_usuarios.add(u);
            }

        }catch(SQLException e){
            System.out.println("Error al listar usuarios:");
            System.out.println(e.getMessage());
        }
        return lista_usuarios;
    }
}