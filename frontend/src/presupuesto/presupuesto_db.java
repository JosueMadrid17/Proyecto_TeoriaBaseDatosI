package presupuesto;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class presupuesto_db{
    public boolean insertar_presupuesto(presupuesto p){
        String sql = "{call sp_insertar_presupuesto(?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, p.get_id_usuario());
            cs.setString(2, p.get_nombre());
            cs.setInt(3, p.get_anio_inicio());
            cs.setInt(4, p.get_mes_inicio());
            cs.setInt(5, p.get_anio_fin());
            cs.setInt(6, p.get_mes_fin());
            cs.setString(7, p.get_creado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al insertar presupuesto:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean modificar_presupuesto(presupuesto p){
        String sql = "{call sp_actualizar_presupuesto(?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, p.get_id_presupuesto());
            cs.setString(2, p.get_nombre());
            cs.setInt(3, p.get_anio_inicio());
            cs.setInt(4, p.get_mes_inicio());
            cs.setInt(5, p.get_anio_fin());
            cs.setInt(6, p.get_mes_fin());
            cs.setString(7, p.get_modificado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al modificar presupuesto:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminar_presupuesto(int id_presupuesto){
        String sql = "{call sp_eliminar_presupuesto(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_presupuesto);
            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al eliminar presupuesto:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public presupuesto consultar_presupuesto(int id_presupuesto){
        String sql = "{call sp_consultar_presupuesto(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_presupuesto);
            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                presupuesto p = new presupuesto();

                p.set_id_presupuesto(rs.getInt("id_presupuesto"));
                p.set_id_usuario(rs.getInt("id_usuario"));
                p.set_nombre(rs.getString("nombre"));
                p.set_anio_inicio(rs.getInt("anio_inicio"));
                p.set_mes_inicio(rs.getInt("mes_inicio"));
                p.set_anio_fin(rs.getInt("anio_fin"));
                p.set_mes_fin(rs.getInt("mes_fin"));
                p.set_total_ingresos(rs.getDouble("total_ingresos"));
                p.set_total_gastos(rs.getDouble("total_gastos"));
                p.set_total_ahorros(rs.getDouble("total_ahorros"));
                p.set_estado_presupuesto(rs.getBoolean("estado_presupuesto"));
                p.set_creado_por(rs.getString("creado_por"));
                p.set_modificado_por(rs.getString("modificado_por"));

                return p;
            }

        }catch(SQLException e){
            System.out.println("Error al consultar presupuesto:");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<presupuesto> listar_presupuestos_usuario(int id_usuario, Boolean estado){
        ArrayList<presupuesto> lista = new ArrayList<>();
        String sql = "{call sp_listar_presupuestos_usuario(?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_usuario);

            if(estado == null){
                cs.setNull(2, java.sql.Types.TINYINT);
            }else{
                cs.setBoolean(2, estado);
            }

            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                presupuesto p = new presupuesto();

                p.set_id_presupuesto(rs.getInt("id_presupuesto"));
                p.set_id_usuario(rs.getInt("id_usuario"));
                p.set_nombre(rs.getString("nombre"));
                p.set_anio_inicio(rs.getInt("anio_inicio"));
                p.set_mes_inicio(rs.getInt("mes_inicio"));
                p.set_anio_fin(rs.getInt("anio_fin"));
                p.set_mes_fin(rs.getInt("mes_fin"));
                p.set_total_ingresos(rs.getDouble("total_ingresos"));
                p.set_total_gastos(rs.getDouble("total_gastos"));
                p.set_total_ahorros(rs.getDouble("total_ahorros"));
                p.set_estado_presupuesto(rs.getBoolean("estado_presupuesto"));

                lista.add(p);
            }

        }catch(SQLException e){
            System.out.println("Error al listar presupuestos:");
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
    public boolean presupuesto_pertenece_a_usuario(int id_presupuesto, int id_usuario){
        presupuesto p = consultar_presupuesto(id_presupuesto);

        if(p == null){
            return false;
        }
        return p.get_id_usuario() == id_usuario;
    }
}

