package funciones;
import config.conexion_bd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class funciones_db{

    public Double calcular_monto_ejecutado(
        int id_subcategoria,
        int anio,
        int mes
    ){
        String sql = "select fn_calcular_monto_ejecutado(?, ?, ?) as monto_ejecutado";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_subcategoria);
            ps.setInt(2, anio);
            ps.setInt(3, mes);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getDouble("monto_ejecutado");
            }
        }catch(SQLException e){
            System.out.println("Error al calcular monto ejecutado:");
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public Double calcular_porcentaje_ejecutado(
        int id_subcategoria,
        int id_presupuesto,
        int anio,
        int mes
    ){
        String sql = "select fn_calcular_porcentaje_ejecutado(?, ?, ?, ?) as porcentaje_ejecutado";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_subcategoria);
            ps.setInt(2, id_presupuesto);
            ps.setInt(3, anio);
            ps.setInt(4, mes);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getDouble("porcentaje_ejecutado");
            }
        }catch(SQLException e){
            System.out.println("Error al calcular porcentaje ejecutado:");
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public Double obtener_balance_subcategoria(
        int id_presupuesto,
        int id_subcategoria,
        int anio,
        int mes
    ){
        String sql = "select fn_obtener_balance_subcategoria(?, ?, ?, ?) as balance_subcategoria";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_presupuesto);
            ps.setInt(2, id_subcategoria);
            ps.setInt(3, anio);
            ps.setInt(4, mes);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getDouble("balance_subcategoria");
            }
        }catch(SQLException e){
            System.out.println("Error al obtener balance de subcategoria:");
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public Double obtener_total_categoria_mes(
        int id_categoria,
        int id_presupuesto,
        int anio,
        int mes
    ){
        String sql = "select fn_obtener_total_categoria_mes(?, ?, ?, ?) as total_categoria";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_categoria);
            ps.setInt(2, id_presupuesto);
            ps.setInt(3, anio);
            ps.setInt(4, mes);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getDouble("total_categoria");
            }
        }catch(SQLException e){
            System.out.println("Error al obtener total de categoria:");
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public Double obtener_total_ejecutado_categoria_mes(
        int id_categoria,
        int anio,
        int mes
    ){
        String sql = "select fn_obtener_total_ejecutado_categoria_mes(?, ?, ?) as total_ejecutado_categoria";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_categoria);
            ps.setInt(2, anio);
            ps.setInt(3, mes);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getDouble("total_ejecutado_categoria");
            }
        }catch(SQLException e){
            System.out.println("Error al obtener total ejecutado de categoria:");
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public Integer dias_hasta_vencimiento(int id_obligacion){
        String sql = "select fn_dias_hasta_vencimiento(?) as dias_restantes";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_obligacion);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("dias_restantes");
            }
        }catch(SQLException e){
            System.out.println("Error al calcular dias hasta vencimiento:");
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public Integer validar_vigencia_presupuesto(
        String fecha,
        int id_presupuesto
    ){
        String sql = "select fn_validar_vigencia_presupuesto(?, ?) as vigencia_presupuesto";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setDate(1, java.sql.Date.valueOf(fecha));
            ps.setInt(2, id_presupuesto);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("vigencia_presupuesto");
            }
        }catch(IllegalArgumentException e){
            System.out.println("Error en la fecha:");
            System.out.println("Debe usar este formato: yyyy-mm-dd");
        }catch(SQLException e){
            System.out.println("Error al validar vigencia del presupuesto:");
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public Integer obtener_categoria_por_subcategoria(int id_subcategoria){
        String sql = "select fn_obtener_categoria_por_subcategoria(?) as id_categoria";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_subcategoria);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("id_categoria");
            }
        }catch(SQLException e){
            System.out.println("Error al obtener categoria por subcategoria:");
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public Double calcular_proyeccion_gasto_mensual(
        int id_subcategoria,
        int anio,
        int mes
    ){
        String sql = "select fn_calcular_proyeccion_gasto_mensual(?, ?, ?) as proyeccion_gasto";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_subcategoria);
            ps.setInt(2, anio);
            ps.setInt(3, mes);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getDouble("proyeccion_gasto");
            }
        }catch(SQLException e){
            System.out.println("Error al calcular proyeccion de gasto:");
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    public Double obtener_promedio_gasto_subcategoria(
        int id_usuario,
        int id_subcategoria,
        int cantidad_meses
    ){
        String sql = "select fn_obtener_promedio_gasto_subcategoria(?, ?, ?) as promedio_gasto_subcategoria";

        try(Connection conexion = conexion_bd.obtener_conexion();
            PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setInt(1, id_usuario);
            ps.setInt(2, id_subcategoria);
            ps.setInt(3, cantidad_meses);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getDouble("promedio_gasto_subcategoria");
            }
        }catch(SQLException e){
            System.out.println("Error al obtener promedio de gasto por subcategoria:");
            System.out.println(e.getMessage());
        }
        return null;
    }
}