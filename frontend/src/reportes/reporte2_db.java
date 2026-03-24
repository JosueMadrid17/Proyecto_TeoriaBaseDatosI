package reportes;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class reporte2_db {

    public List<reporte2> obtener_reporte2(
        int id_usuario,
        int anio,
        int mes
    ){
        List<reporte2> lista = new ArrayList<>();

        try{
            Connection conn = conexion_bd.obtener_conexion();

            CallableStatement cs = conn.prepareCall(
                "{call sp_reporte_distribucion_gastos_categoria(?,?,?)}"
            );

            cs.setInt(1, id_usuario);
            cs.setInt(2, anio);
            cs.setInt(3, mes);

            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                reporte2 r = new reporte2();

                r.set_id_categoria(rs.getInt("id_categoria"));
                r.set_nombre_categoria(rs.getString("nombre_categoria"));
                r.set_monto_total_gastado(rs.getBigDecimal("monto_total_gastado"));
                r.set_porcentaje_total_gastos(rs.getBigDecimal("porcentaje_total_gastos"));
                r.set_cantidad_transacciones(rs.getInt("cantidad_transacciones"));

                lista.add(r);
            }

            rs.close();
            cs.close();
            conn.close();
        }catch(Exception e){
            System.out.println("Lo siento, ocurrio un error al obtener el reporte 2: " + e.getMessage());
        }
        return lista;
    }
}