package categoria;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class categoria_db{
    public boolean insertar_categoria(categoria c){
        String sql = "{call sp_insertar_categoria(?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setString(1, c.get_nombre());
            cs.setString(2, c.get_descripcion());
            cs.setString(3, c.get_tipo_categoria());
            cs.setString(4, c.get_creado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al insertar categoria:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean modificar_categoria(categoria c){
        String sql = "{call sp_actualizar_categoria(?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, c.get_id_categoria());
            cs.setString(2, c.get_nombre());
            cs.setString(3, c.get_descripcion());
            cs.setString(4, c.get_modificado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al modificar categoria:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminar_categoria(int id_categoria){
        String sql = "{call sp_eliminar_categoria(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_categoria);
            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al eliminar categoria:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public categoria consultar_categoria(int id_categoria){
        String sql = "{call sp_consultar_categoria(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_categoria);
            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                categoria c = new categoria();

                c.set_id_categoria(rs.getInt("id_categoria"));
                c.set_nombre(rs.getString("nombre"));
                c.set_descripcion(rs.getString("descripcion_detallada"));
                c.set_tipo_categoria(rs.getString("tipo_categoria"));
                c.set_creado_por(rs.getString("creado_por"));
                c.set_modificado_por(rs.getString("modificado_por"));

                return c;
            }

        }catch(SQLException e){
            System.out.println("Error al consultar categoria:");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<categoria> listar_categorias(int id_usuario, String tipo){
        ArrayList<categoria> lista = new ArrayList<>();
        String sql = "{call sp_listar_categorias(?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_usuario);

            if(tipo == null || tipo.trim().equals("")){
                cs.setNull(2, java.sql.Types.VARCHAR);
            }else{
                cs.setString(2, tipo);
            }

            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                categoria c = new categoria();

                c.set_id_categoria(rs.getInt("id_categoria"));
                c.set_nombre(rs.getString("nombre"));
                c.set_descripcion(rs.getString("descripcion_detallada"));
                c.set_tipo_categoria(rs.getString("tipo_categoria"));
                c.set_creado_por(rs.getString("creado_por"));
                c.set_modificado_por(rs.getString("modificado_por"));

                lista.add(c);
            }

        }catch(SQLException e){
            System.out.println("Error al listar categorias:");
            System.out.println(e.getMessage());
        }
        return lista;
    }
}