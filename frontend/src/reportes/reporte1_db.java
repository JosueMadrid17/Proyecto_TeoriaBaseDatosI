package reportes;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class reporte1_db {

    public List<reporte1> obtener_reporte1(
        int id_usuario,
        int anio_inicio,
        int mes_inicio,
        int anio_fin,
        int mes_fin
    ){
        List<reporte1> lista = new ArrayList<>();

        try{
            Connection conn = conexion_bd.obtener_conexion();

            CallableStatement cs = conn.prepareCall(
                "{call sp_reporte_resumen_mensual(?,?,?,?,?)}"
            );

            cs.setInt(1, id_usuario);
            cs.setInt(2, anio_inicio);
            cs.setInt(3, mes_inicio);
            cs.setInt(4, anio_fin);
            cs.setInt(5, mes_fin);

            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                reporte1 r = new reporte1();

                r.set_anio(rs.getInt("anio"));
                r.set_mes(rs.getInt("mes"));
                r.set_ingresos(rs.getBigDecimal("total_ingresos"));
                r.set_gastos(rs.getBigDecimal("total_gastos"));
                r.set_ahorros(rs.getBigDecimal("total_ahorros"));
                r.set_balance(rs.getBigDecimal("balance_final"));

                lista.add(r);
            }

            rs.close();
            cs.close();
            conn.close();
        }catch(Exception e){
            System.out.println("Lo siento, ocurrio un error al obtener el reporte 1: " + e.getMessage());
        }
        return lista;
    }
}