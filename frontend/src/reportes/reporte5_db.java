package reportes;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class reporte5_db {
    public List<reporte5> obtener_reporte5(
        int id_usuario,
        int anio,
        int mes,
        String estado
    ){
        List<reporte5> lista = new ArrayList<>();

        try{
            Connection conn = conexion_bd.obtener_conexion();

            CallableStatement cs = conn.prepareCall(
                "{call sp_reporte_estado_obligaciones_fijas(?,?,?,?)}"
            );

            cs.setInt(1, id_usuario);
            cs.setInt(2, anio);
            cs.setInt(3, mes);
            cs.setString(4, estado);

            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                reporte5 r = new reporte5();

                r.set_id_obligacion(rs.getInt("id_obligacion"));
                r.set_nombre_obligacion(rs.getString("nombre_obligacion"));
                r.set_nombre_categoria(rs.getString("nombre_categoria"));
                r.set_monto_fijo_mensual(rs.getBigDecimal("monto_fijo_mensual"));
                r.set_fecha_vencimiento_mes(rs.getDate("fecha_vencimiento_mes"));
                r.set_estado_pago(rs.getString("estado_pago"));
                r.set_dias_referencia(rs.getInt("dias_referencia"));
                r.set_fecha_ultimo_pago(rs.getTimestamp("fecha_ultimo_pago"));

                lista.add(r);
            }

            rs.close();
            cs.close();
            conn.close();
        }catch(Exception e){
            System.out.println("Lo siento, ocurrio un error al obtener el reporte 5: " + e.getMessage());
        }
        return lista;
    }
}