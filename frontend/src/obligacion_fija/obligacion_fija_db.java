package obligacion_fija;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class obligacion_fija_db{
    public boolean insertar_obligacion_fija(obligacion_fija of){
        String sql = "{call sp_insertar_obligacion_fija(?, ?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, of.get_id_subcategoria());
            cs.setString(2, of.get_nombre());
            cs.setString(3, of.get_descripcion_detallada());
            cs.setDouble(4, of.get_monto_fijo_mensual());
            cs.setInt(5, of.get_dia_vencimiento());
            cs.setTimestamp(6, Timestamp.valueOf(of.get_fecha_inicio()));

            if(of.get_fecha_fin() == null || of.get_fecha_fin().trim().isEmpty()){
                cs.setTimestamp(7, null);
            }else{
                cs.setTimestamp(7, Timestamp.valueOf(of.get_fecha_fin()));
            }

            cs.setString(8, of.get_creado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al insertar obligacion fija:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean modificar_obligacion_fija(obligacion_fija of){
        String sql = "{call sp_actualizar_obligacion_fija(?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, of.get_id_obligacion());
            cs.setString(2, of.get_nombre());
            cs.setString(3, of.get_descripcion_detallada());
            cs.setDouble(4, of.get_monto_fijo_mensual());
            cs.setInt(5, of.get_dia_vencimiento());

            if(of.get_fecha_fin() == null || of.get_fecha_fin().trim().isEmpty()){
                cs.setTimestamp(6, null);
            }else{
                cs.setTimestamp(6, Timestamp.valueOf(of.get_fecha_fin()));
            }

            cs.setString(7, of.get_modificado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al modificar obligacion fija:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminar_obligacion_fija(int id_obligacion){
        String sql = "{call sp_eliminar_obligacion_fija(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_obligacion);

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al eliminar obligacion fija:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public obligacion_fija consultar_obligacion_fija(int id_obligacion){
        String sql = "{call sp_consultar_obligacion_fija(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_obligacion);
            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                obligacion_fija of = new obligacion_fija();

                of.set_id_obligacion(rs.getInt("id_obligacion"));
                of.set_id_subcategoria(rs.getInt("id_subcategoria"));
                of.set_nombre(rs.getString("nombre"));
                of.set_descripcion_detallada(rs.getString("descripcion_detallada"));
                of.set_monto_fijo_mensual(rs.getDouble("monto_fijo_mensual"));
                of.set_dia_vencimiento(rs.getInt("dia_vencimiento"));
                of.set_vigente(rs.getBoolean("vigente"));

                if(rs.getTimestamp("fecha_inicio") != null){
                    of.set_fecha_inicio(rs.getTimestamp("fecha_inicio").toString());
                }

                if(rs.getTimestamp("fecha_fin") != null){
                    of.set_fecha_fin(rs.getTimestamp("fecha_fin").toString());
                }

                of.set_creado_por(rs.getString("creado_por"));
                of.set_modificado_por(rs.getString("modificado_por"));

                return of;
            }
        }catch(SQLException e){
            System.out.println("Error al consultar obligacion fija:");
            System.out.println(e.getMessage());
        }

        return null;
    }

    public ArrayList<obligacion_fija> listar_obligaciones_usuario(int id_usuario, Integer vigente){
        ArrayList<obligacion_fija> lista = new ArrayList<>();
        String sql = "{call sp_listar_obligaciones_usuario(?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_usuario);

            if(vigente == null){
                cs.setNull(2, java.sql.Types.TINYINT);
            }else{
                cs.setInt(2, vigente);
            }

            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                obligacion_fija of = new obligacion_fija();

                of.set_id_obligacion(rs.getInt("id_obligacion"));
                of.set_id_subcategoria(rs.getInt("id_subcategoria"));
                of.set_nombre(rs.getString("nombre"));
                of.set_descripcion_detallada(rs.getString("descripcion_detallada"));
                of.set_monto_fijo_mensual(rs.getDouble("monto_fijo_mensual"));
                of.set_dia_vencimiento(rs.getInt("dia_vencimiento"));
                of.set_vigente(rs.getBoolean("vigente"));

                if(rs.getTimestamp("fecha_inicio") != null){
                    of.set_fecha_inicio(rs.getTimestamp("fecha_inicio").toString());
                }

                if(rs.getTimestamp("fecha_fin") != null){
                    of.set_fecha_fin(rs.getTimestamp("fecha_fin").toString());
                }

                of.set_creado_por(rs.getString("creado_por"));
                of.set_modificado_por(rs.getString("modificado_por"));

                lista.add(of);
            }
        }catch(SQLException e){
            System.out.println("Error al listar obligaciones fijas:");
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public boolean obligacion_pertenece_a_usuario(int id_obligacion, int id_usuario){
        String sql =
            "select 1 " +
            "from obligacion_fija o " +
            "inner join subcategoria s on o.id_subcategoria = s.id_subcategoria " +
            "inner join presupuesto_detalle pd on s.id_subcategoria = pd.id_subcategoria " +
            "inner join presupuesto p on pd.id_presupuesto = p.id_presupuesto " +
            "where o.id_obligacion = ? and p.id_usuario = ?";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_obligacion);
            ps.setInt(2, id_usuario);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        }catch(SQLException e){
            System.out.println("Error al validar obligacion del usuario:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean subcategoria_pertenece_a_usuario(int id_subcategoria, int id_usuario){
        String sql =
            "select 1 " +
            "from subcategoria s " +
            "inner join presupuesto_detalle pd on s.id_subcategoria = pd.id_subcategoria " +
            "inner join presupuesto p on pd.id_presupuesto = p.id_presupuesto " +
            "where s.id_subcategoria = ? and p.id_usuario = ?";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_subcategoria);
            ps.setInt(2, id_usuario);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        }catch(SQLException e){
            System.out.println("Error al validar subcategoria del usuario:");
            System.out.println(e.getMessage());
            return false;
        }
    }
}