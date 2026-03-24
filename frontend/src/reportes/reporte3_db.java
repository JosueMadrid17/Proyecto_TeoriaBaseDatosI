package reportes;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class reporte3_db {
    public List<reporte3> obtener_reporte3(
        int id_usuario,
        int anio,
        int mes,
        String tipo_categoria
    ){
        List<reporte3> lista = new ArrayList<>();

        try{
            Connection conn = conexion_bd.obtener_conexion();

            CallableStatement cs = conn.prepareCall(
                "{call sp_reporte_cumplimiento_presupuesto(?,?,?,?)}"
            );

            cs.setInt(1, id_usuario);
            cs.setInt(2, anio);
            cs.setInt(3, mes);
            cs.setString(4, tipo_categoria);

            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                reporte3 r = new reporte3();

                r.set_id_categoria(rs.getInt("id_categoria"));
                r.set_nombre_categoria(rs.getString("nombre_categoria"));
                r.set_id_subcategoria(rs.getInt("id_subcategoria"));
                r.set_nombre_subcategoria(rs.getString("nombre_subcategoria"));
                r.set_monto_presupuestado(rs.getBigDecimal("monto_presupuestado"));
                r.set_monto_ejecutado(rs.getBigDecimal("monto_ejecutado"));
                r.set_diferencia(rs.getBigDecimal("diferencia"));
                r.set_porcentaje_ejecucion(rs.getBigDecimal("porcentaje_ejecucion"));
                r.set_indicador_visual(rs.getString("indicador_visual"));

                lista.add(r);
            }

            rs.close();
            cs.close();
            conn.close();
        }catch(Exception e){
            System.out.println("Lo siento, ocurrio un error al obtener el reporte 3: " + e.getMessage());
        }
        return lista;
    }
}