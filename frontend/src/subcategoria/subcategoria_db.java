package subcategoria;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class subcategoria_db{
    public boolean insertar_subcategoria(subcategoria s){
        String sql = "{call sp_insertar_subcategoria(?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, s.get_id_categoria());
            cs.setString(2, s.get_nombre_subcategoria());
            cs.setString(3, s.get_descripcion());
            cs.setBoolean(4, s.is_es_por_defecto());
            cs.setString(5, s.get_creado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al insertar subcategoria:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean modificar_subcategoria(subcategoria s){
        String sql = "{call sp_actualizar_subcategoria(?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, s.get_id_subcategoria());
            cs.setString(2, s.get_nombre_subcategoria());
            cs.setString(3, s.get_descripcion());
            cs.setString(4, s.get_modificado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al modificar subcategoria:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminar_subcategoria(int id_subcategoria){
        String sql = "{call sp_eliminar_subcategoria(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_subcategoria);
            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al eliminar subcategoria:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public subcategoria consultar_subcategoria(int id_subcategoria){
        String sql = "{call sp_consultar_subcategoria(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_subcategoria);
            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                subcategoria s = new subcategoria();

                s.set_id_subcategoria(rs.getInt("id_subcategoria"));
                s.set_id_categoria(rs.getInt("id_categoria"));
                s.set_nombre_categoria(rs.getString("nombre_categoria"));
                s.set_tipo_categoria(rs.getString("tipo_categoria"));
                s.set_nombre_subcategoria(rs.getString("nombre_subcategoria"));
                s.set_descripcion(rs.getString("descripcion_detallada"));
                s.set_activo(rs.getBoolean("activo"));
                s.set_es_por_defecto(rs.getBoolean("es_por_defecto"));
                s.set_creado_por(rs.getString("creado_por"));
                s.set_modificado_por(rs.getString("modificado_por"));

                return s;
            }

        }catch(SQLException e){
            System.out.println("Error al consultar subcategoria:");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<subcategoria> listar_subcategorias_por_categoria(int id_categoria){
        ArrayList<subcategoria> lista = new ArrayList<>();
        String sql = "{call sp_listar_subcategorias_por_categoria(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_categoria);
            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                subcategoria s = new subcategoria();

                s.set_id_subcategoria(rs.getInt("id_subcategoria"));
                s.set_id_categoria(rs.getInt("id_categoria"));
                s.set_nombre_subcategoria(rs.getString("nombre"));
                s.set_descripcion(rs.getString("descripcion_detallada"));
                s.set_activo(rs.getBoolean("activo"));
                s.set_es_por_defecto(rs.getBoolean("es_por_defecto"));
                s.set_creado_por(rs.getString("creado_por"));
                s.set_modificado_por(rs.getString("modificado_por"));

                lista.add(s);
            }

        }catch(SQLException e){
            System.out.println("Error al listar subcategorias:");
            System.out.println(e.getMessage());
        }
        return lista;
    }
}