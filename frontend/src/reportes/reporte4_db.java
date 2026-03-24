package reportes;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class reporte4_db {
    public List<reporte4> obtener_reporte4(
        int id_usuario,
        int anio_inicio,
        int mes_inicio,
        int anio_fin,
        int mes_fin,
        String categorias
    ){
        List<reporte4> lista = new ArrayList<>();

        try{
            Connection conn = conexion_bd.obtener_conexion();

            CallableStatement cs = conn.prepareCall(
                "{call sp_reporte_tendencia_gastos_categoria_tiempo(?,?,?,?,?,?)}"
            );

            cs.setInt(1, id_usuario);
            cs.setInt(2, anio_inicio);
            cs.setInt(3, mes_inicio);
            cs.setInt(4, anio_fin);
            cs.setInt(5, mes_fin);
            cs.setString(6, categorias);

            ResultSet rs = cs.executeQuery();

            while(rs.next()){
                reporte4 r = new reporte4();
                r.set_anio(rs.getInt("anio"));
                r.set_mes(rs.getInt("mes"));
                r.set_id_categoria(rs.getInt("id_categoria"));
                r.set_nombre_categoria(rs.getString("nombre_categoria"));
                r.set_monto_gastado(rs.getBigDecimal("monto_gastado"));

                lista.add(r);
            }

            rs.close();
            cs.close();
            conn.close();
        }catch(Exception e){
            System.out.println("Lo siento, ocurrio un error al obtener el reporte 4: " + e.getMessage());
        }
        return lista;
    }
}