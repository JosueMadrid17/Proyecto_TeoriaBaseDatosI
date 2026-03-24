package transaccion;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class transaccion_db{

    public boolean insertar_transaccion(transaccion t){
        String sql = "{call sp_insertar_transaccion(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, t.get_id_detalle());
            cs.setInt(2, t.get_anio_transaccion());
            cs.setInt(3, t.get_mes_transaccion());
            cs.setString(4, t.get_tipo_transaccion());
            cs.setString(5, t.get_descripcion());
            cs.setDouble(6, t.get_monto_transaccion());
            cs.setTimestamp(7, Timestamp.valueOf(t.get_fecha_transaccion()));
            cs.setString(8, t.get_metodo_pago());

            if(t.get_num_factura() <= 0){
                cs.setNull(9, java.sql.Types.INTEGER);
            }else{
                cs.setInt(9, t.get_num_factura());
            }

            cs.setString(10, t.get_observaciones());
            cs.setString(11, t.get_creado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al insertar transaccion:");
            System.out.println(e.getMessage());
            return false;
        }catch(IllegalArgumentException e){
            System.out.println("Error en la fecha de la transaccion:");
            System.out.println("Debe usar este formato: yyyy-mm-dd hh:mm:ss");
            return false;
        }
    }

    public boolean modificar_transaccion(transaccion t){
        String sql = "{call sp_actualizar_transaccion(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, t.get_id_transaccion());
            cs.setInt(2, t.get_anio_transaccion());
            cs.setInt(3, t.get_mes_transaccion());
            cs.setString(4, t.get_descripcion());
            cs.setDouble(5, t.get_monto_transaccion());
            cs.setTimestamp(6, Timestamp.valueOf(t.get_fecha_transaccion()));
            cs.setString(7, t.get_metodo_pago());

            if(t.get_num_factura() <= 0){
                cs.setNull(8, java.sql.Types.INTEGER);
            }else{
                cs.setInt(8, t.get_num_factura());
            }

            cs.setString(9, t.get_observaciones());
            cs.setString(10, t.get_modificado_por());

            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al modificar transaccion:");
            System.out.println(e.getMessage());
            return false;
        }catch(IllegalArgumentException e){
            System.out.println("Error en la fecha de la transaccion:");
            System.out.println("Debe usar este formato: yyyy-mm-dd hh:mm:ss");
            return false;
        }
    }

    public boolean eliminar_transaccion(int id_transaccion){
        String sql = "{call sp_eliminar_transaccion(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_transaccion);
            cs.execute();
            return true;

        }catch(SQLException e){
            System.out.println("Error al eliminar transaccion:");
            System.out.println(e.getMessage());
            return false;
        }
    }

   public transaccion consultar_transaccion(int id_transaccion){
        String sql = "{call sp_consultar_transaccion(?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_transaccion);
            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                transaccion t = new transaccion();

                t.set_id_transaccion(rs.getInt("id_transaccion"));
                t.set_id_detalle(rs.getInt("id_detalle"));
                t.set_anio_transaccion(rs.getInt("anio_transaccion"));
                t.set_mes_transaccion(rs.getInt("mes_transaccion"));
                t.set_tipo_transaccion(rs.getString("tipo_transaccion"));
                t.set_descripcion(rs.getString("descripcion"));
                t.set_monto_transaccion(rs.getDouble("monto_transaccion"));

                if(rs.getTimestamp("fecha_transaccion") != null){
                    t.set_fecha_transaccion(rs.getTimestamp("fecha_transaccion").toString());
                }

                t.set_metodo_pago(rs.getString("metodo_pago"));

                if(rs.getObject("num_factura") != null){
                    t.set_num_factura(rs.getInt("num_factura"));
                }else{
                    t.set_num_factura(0);
                }

                t.set_observaciones(rs.getString("observaciones"));
                t.set_creado_por(rs.getString("creado_por"));
                t.set_modificado_por(rs.getString("modificado_por"));

                t.set_id_presupuesto(rs.getInt("id_presupuesto"));
                t.set_id_subcategoria(rs.getInt("id_subcategoria"));
                t.set_nombre_subcategoria(rs.getString("nombre_subcategoria"));
                t.set_id_categoria(rs.getInt("id_categoria"));
                t.set_nombre_categoria(rs.getString("nombre_categoria"));

                return t;
            }
        }catch(SQLException e){
            System.out.println("Error al consultar transaccion:");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<transaccion> listar_transacciones_presupuesto(int id_presupuesto, Integer anio, Integer mes, String tipo){
        ArrayList<transaccion> lista = new ArrayList<>();
        String sql = "{call sp_listar_transacciones_presupuesto(?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_presupuesto);

            if(anio == null){
                cs.setNull(2, java.sql.Types.INTEGER);
            }else{
                cs.setInt(2, anio);
            }

            if(mes == null){
                cs.setNull(3, java.sql.Types.INTEGER);
            }else{
                cs.setInt(3, mes);
            }

            if(tipo == null || tipo.trim().isEmpty()){
                cs.setNull(4, java.sql.Types.VARCHAR);
            }else{
                cs.setString(4, tipo);
            }

            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                transaccion t = new transaccion();

                t.set_id_transaccion(rs.getInt("id_transaccion"));
                t.set_anio_transaccion(rs.getInt("anio_transaccion"));
                t.set_mes_transaccion(rs.getInt("mes_transaccion"));
                t.set_tipo_transaccion(rs.getString("tipo_transaccion"));
                t.set_descripcion(rs.getString("descripcion"));
                t.set_monto_transaccion(rs.getDouble("monto_transaccion"));

                if(rs.getTimestamp("fecha_transaccion") != null){
                    t.set_fecha_transaccion(rs.getTimestamp("fecha_transaccion").toString());
                }

                t.set_metodo_pago(rs.getString("metodo_pago"));

                if(rs.getObject("num_factura") != null){
                    t.set_num_factura(rs.getInt("num_factura"));
                }else{
                    t.set_num_factura(0);
                }

                t.set_observaciones(rs.getString("observaciones"));
                t.set_creado_por(rs.getString("creado_por"));
                t.set_modificado_por(rs.getString("modificado_por"));

                t.set_id_presupuesto(rs.getInt("id_presupuesto"));
                t.set_id_subcategoria(rs.getInt("id_subcategoria"));
                t.set_nombre_subcategoria(rs.getString("nombre_subcategoria"));
                t.set_id_categoria(rs.getInt("id_categoria"));
                t.set_nombre_categoria(rs.getString("nombre_categoria"));

                lista.add(t);
            }
        }catch(SQLException e){
            System.out.println("Error al listar transacciones:");
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public boolean detalle_pertenece_a_usuario(int id_detalle, int id_usuario){
        String sql = "{call sp_validar_detalle_transaccion_usuario(?, ?)}";

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

   public boolean transaccion_pertenece_a_usuario(int id_transaccion, int id_usuario){
        String sql = "{call sp_validar_transaccion_usuario(?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_transaccion);
            cs.setInt(2, id_usuario);

            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                return rs.getInt("pertenece") == 1;
            }
        }catch(SQLException e){
            System.out.println("Error al validar transaccion del usuario:");
            System.out.println(e.getMessage());
        }
        return false;
    }
}