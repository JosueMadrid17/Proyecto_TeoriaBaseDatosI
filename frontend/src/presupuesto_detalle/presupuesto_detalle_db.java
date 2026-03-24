package presupuesto_detalle;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class presupuesto_detalle_db{

    public boolean insertar_presupuesto_detalle(presupuesto_detalle pd){
        String sql = "{call sp_insertar_presupuesto_detalle(?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, pd.get_id_presupuesto());
            cs.setInt(2, pd.get_id_subcategoria());
            cs.setDouble(3, pd.get_monto_mensual_asignado());
            cs.setString(4, pd.get_observaciones());
            cs.setString(5, pd.get_creado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al insertar detalle de presupuesto:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean modificar_presupuesto_detalle(presupuesto_detalle pd){
        String sql = "{call sp_actualizar_presupuesto_detalle(?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, pd.get_id_detalle());
            cs.setDouble(2, pd.get_monto_mensual_asignado());
            cs.setString(3, pd.get_observaciones());
            cs.setString(4, pd.get_modificado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al modificar detalle de presupuesto:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminar_presupuesto_detalle(int id_detalle){
        String sql = "{call sp_eliminar_presupuesto_detalle(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_detalle);
            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al eliminar detalle de presupuesto:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public presupuesto_detalle consultar_presupuesto_detalle(int id_detalle){
        String sql = "{call sp_consultar_presupuesto_detalle(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_detalle);
            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                presupuesto_detalle pd = new presupuesto_detalle();

                pd.set_id_detalle(rs.getInt("id_detalle"));
                pd.set_id_presupuesto(rs.getInt("id_presupuesto"));
                pd.set_id_subcategoria(rs.getInt("id_subcategoria"));
                pd.set_monto_mensual_asignado(rs.getDouble("monto_mensual_asignado"));
                pd.set_observaciones(rs.getString("observaciones"));
                pd.set_creado_por(rs.getString("creado_por"));
                pd.set_modificado_por(rs.getString("modificado_por"));

                return pd;
            }

        }catch(SQLException e){
            System.out.println("Error al consultar detalle de presupuesto:");
            System.out.println(e.getMessage());
        }

        return null;
    }

    public ArrayList<presupuesto_detalle> listar_presupuesto_detalle(int id_presupuesto){
        ArrayList<presupuesto_detalle> lista = new ArrayList<>();
        String sql = "{call sp_listar_presupuesto_detalle(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_presupuesto);
            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                presupuesto_detalle pd = new presupuesto_detalle();

                pd.set_id_detalle(rs.getInt("id_detalle"));
                pd.set_id_presupuesto(rs.getInt("id_presupuesto"));
                pd.set_id_subcategoria(rs.getInt("id_subcategoria"));
                pd.set_monto_mensual_asignado(rs.getDouble("monto_mensual_asignado"));
                pd.set_observaciones(rs.getString("observaciones"));
                pd.set_creado_por(rs.getString("creado_por"));
                pd.set_modificado_por(rs.getString("modificado_por"));
                lista.add(pd);
            }
        }catch(SQLException e){
            System.out.println("Error al listar detalle de presupuesto:");
            System.out.println(e.getMessage());
        }

        return lista;
    }

    public boolean presupuesto_pertenece_a_usuario(int id_presupuesto, int id_usuario){
        String sql = "{call sp_validar_presupuesto_usuario(?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_presupuesto);
            cs.setInt(2, id_usuario);

            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                return rs.getInt("pertenece") == 1;
            }

        }catch(SQLException e){
            System.out.println("Error al validar presupuesto del usuario:");
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean detalle_pertenece_a_usuario(int id_detalle, int id_usuario){
        String sql = "{call sp_validar_detalle_usuario(?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_detalle);
            cs.setInt(2, id_usuario);

            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                return rs.getInt("pertenece") == 1;
            }

        }catch(SQLException e){
            System.out.println("Error al validar detalle del usuario:");
            System.out.println(e.getMessage());
        }

        return false;
    }
}