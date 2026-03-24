package procedimientos;
import config.conexion_bd;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class procedimientos_logicos_db{
    
    public boolean crear_presupuesto_completo(
        int id_usuario,
        String nombre,
        String descripcion,
        int anio_inicio,
        int mes_inicio,
        int anio_fin,
        int mes_fin,
        String lista_subcategorias_json,
        String creado_por
    ){
        String sql = "{call sp_crear_presupuesto_completo(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_usuario);
            cs.setString(2, nombre);
            cs.setString(3, descripcion);
            cs.setInt(4, anio_inicio);
            cs.setInt(5, mes_inicio);
            cs.setInt(6, anio_fin);
            cs.setInt(7, mes_fin);
            cs.setString(8, lista_subcategorias_json);
            cs.setString(9, creado_por);

            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                System.out.println("id presupuesto creado: " + rs.getInt("id_presupuesto_creado"));
                System.out.println("total ingresos: " + rs.getDouble("total_ingresos"));
                System.out.println("total gastos: " + rs.getDouble("total_gastos"));
                System.out.println("total ahorros: " + rs.getDouble("total_ahorros"));
            }
            return true;

        }catch(SQLException e){
            System.out.println("Error al crear presupuesto completo:");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public boolean registrar_transaccion_completa(
        int id_detalle,
        int anio,
        int mes,
        String tipo,
        String descripcion,
        double monto,
        String fecha,
        String metodo_pago,
        int num_factura,
        String observaciones,
        String creado_por
    ){
        String sql = "{call sp_registrar_transaccion_completa(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){
            cs.setInt(1, id_detalle);
            cs.setInt(2, anio);
            cs.setInt(3, mes);
            cs.setString(4, tipo);
            cs.setString(5, descripcion);
            cs.setDouble(6, monto);
            cs.setTimestamp(7, java.sql.Timestamp.valueOf(fecha));
            cs.setString(8, metodo_pago);

            if(num_factura <= 0){
                cs.setNull(9, java.sql.Types.INTEGER);
            }else{
                cs.setInt(9, num_factura);
            }

            cs.setString(10, observaciones);
            cs.setString(11, creado_por);

            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                System.out.println("id transaccion creada: " + rs.getInt("id_transaccion"));
            }
            return true;
            
        }catch(IllegalArgumentException e){
            System.out.println("Error en la fecha de la transaccion:");
            System.out.println("Debe usar este formato: yyyy-mm-dd hh:mm:ss");
            return false;
        }catch(SQLException e){
            System.out.println("Error al registrar transaccion completa:");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public boolean procesar_obligaciones_mes(
        int id_usuario,
        int anio,
        int mes,
        int id_presupuesto
    ){
        String sql = "{call sp_procesar_obligaciones_mes(?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_usuario);
            cs.setInt(2, anio);
            cs.setInt(3, mes);
            cs.setInt(4, id_presupuesto);

            ResultSet rs = cs.executeQuery();

            boolean hayRegistros = false;

            while(rs.next()){
                hayRegistros = true;
                System.out.println("------------------------------------------");
                System.out.println("id obligacion: " + rs.getInt("id_obligacion"));
                System.out.println("nombre: " + rs.getString("nombre"));
                System.out.println("descripcion detallada: " + rs.getString("descripcion_detallada"));
                System.out.println("categoria: " + rs.getString("categoria"));
                System.out.println("subcategoria: " + rs.getString("subcategoria"));
                System.out.println("monto fijo mensual: " + rs.getDouble("monto_fijo_mensual"));
                System.out.println("dia vencimiento: " + rs.getInt("dia_vencimiento"));
                System.out.println("anio: " + rs.getInt("anio"));
                System.out.println("mes: " + rs.getInt("mes"));
                System.out.println("estado alerta: " + rs.getString("estado_alerta"));
            }
            if(!hayRegistros){
                System.out.println("Lo siento, no hay obligaciones para procesar en ese mes.");
            }
            return true;
        }catch(SQLException e){
            System.out.println("Error al procesar obligaciones del mes:");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public boolean calcular_balance_mensual(
        int id_usuario,
        int id_presupuesto,
        int anio,
        int mes
    ){
        String sql = "{call sp_calcular_balance_mensual(?, ?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_usuario);
            cs.setInt(2, id_presupuesto);
            cs.setInt(3, anio);
            cs.setInt(4, mes);
            cs.registerOutParameter(5, java.sql.Types.DECIMAL);
            cs.registerOutParameter(6, java.sql.Types.DECIMAL);
            cs.registerOutParameter(7, java.sql.Types.DECIMAL);
            cs.registerOutParameter(8, java.sql.Types.DECIMAL);

            boolean tieneResultado = cs.execute();

            if(tieneResultado){
                ResultSet rs = cs.getResultSet();

                if(rs.next()){
                    System.out.println("------------------------------------------");
                    System.out.println("id usuario: " + rs.getInt("id_usuario"));
                    System.out.println("nombre usuario: " + rs.getString("nombre_usuario"));
                    System.out.println("id presupuesto: " + rs.getInt("id_presupuesto"));
                    System.out.println("nombre presupuesto: " + rs.getString("nombre_presupuesto"));
                    System.out.println("anio: " + rs.getInt("anio"));
                    System.out.println("mes: " + rs.getInt("mes"));
                    System.out.println("total ingresos: " + rs.getDouble("total_ingresos"));
                    System.out.println("total gastos: " + rs.getDouble("total_gastos"));
                    System.out.println("total ahorros: " + rs.getDouble("total_ahorros"));
                    System.out.println("balance final: " + rs.getDouble("balance_final"));
                }
            }

            System.out.println("------------------------------------------");
            System.out.println("salida (out) total ingresos: " + cs.getDouble(5));
            System.out.println("salida (out) total gastos: " + cs.getDouble(6));
            System.out.println("salida (out) total ahorros: " + cs.getDouble(7));
            System.out.println("salida (out) balance final: " + cs.getDouble(8));
            return true;
            
        }catch(SQLException e){
            System.out.println("Error al calcular balance mensual:");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public boolean calcular_monto_ejecutado_mes(
        int id_subcategoria,
        int id_presupuesto,
        int anio,
        int mes
    ){
        String sql = "{call sp_calcular_monto_ejecutado_mes(?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_subcategoria);
            cs.setInt(2, id_presupuesto);
            cs.setInt(3, anio);
            cs.setInt(4, mes);
            cs.setDouble(5, 0);

            boolean tieneResultado = cs.execute();

            if(tieneResultado){
                ResultSet rs = cs.getResultSet();

                if(rs.next()){
                    System.out.println("------------------------------------------");
                    System.out.println("id subcategoria: " + rs.getInt("id_subcategoria"));
                    System.out.println("nombre subcategoria: " + rs.getString("nombre_subcategoria"));
                    System.out.println("id presupuesto: " + rs.getInt("id_presupuesto"));
                    System.out.println("nombre presupuesto: " + rs.getString("nombre_presupuesto"));
                    System.out.println("anio: " + rs.getInt("anio"));
                    System.out.println("mes: " + rs.getInt("mes"));
                    System.out.println("monto ejecutado: " + rs.getDouble("monto_ejecutado"));
                }
            }
            return true;
        }catch(SQLException e){
            System.out.println("Error al calcular monto ejecutado del mes:");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public boolean calcular_porcentaje_ejecutado_mes(
        int id_subcategoria,
        int id_presupuesto,
        int anio,
        int mes
    ){
        String sql = "{call sp_calcular_porcentaje_ejecutado_mes(?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_subcategoria);
            cs.setInt(2, id_presupuesto);
            cs.setInt(3, anio);
            cs.setInt(4, mes);
            cs.registerOutParameter(5, java.sql.Types.DECIMAL);

            boolean tieneResultado = cs.execute();

            if(tieneResultado){
                ResultSet rs = cs.getResultSet();

                if(rs.next()){
                    System.out.println("------------------------------------------");
                    System.out.println("id subcategoria: " + rs.getInt("id_subcategoria"));
                    System.out.println("nombre subcategoria: " + rs.getString("nombre_subcategoria"));
                    System.out.println("id presupuesto: " + rs.getInt("id_presupuesto"));
                    System.out.println("nombre presupuesto: " + rs.getString("nombre_presupuesto"));
                    System.out.println("anio: " + rs.getInt("anio"));
                    System.out.println("mes: " + rs.getInt("mes"));
                    System.out.println("monto presupuestado: " + rs.getDouble("monto_presupuestado"));
                    System.out.println("monto ejecutado: " + rs.getDouble("monto_ejecutado"));
                    System.out.println("porcentaje: " + rs.getDouble("porcentaje"));
                }
            }

            System.out.println("------------------------------------------");
            System.out.println("porcentaje (out): " + cs.getDouble(5));
            return true;
        }catch(SQLException e){
            System.out.println("Error al calcular porcentaje ejecutado del mes:");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public boolean cerrar_presupuesto(
        int id_presupuesto,
        String modificado_por
    ){
        String sql = "{call sp_cerrar_presupuesto(?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_presupuesto);
            cs.setString(2, modificado_por);

            ResultSet rs = cs.executeQuery();

            if(rs.next()){
                System.out.println("------------------------------------------");
                System.out.println("id presupuesto: " + rs.getInt("id_presupuesto"));
                System.out.println("nombre presupuesto: " + rs.getString("nombre_prespuesto"));
                System.out.println("id usuario: " + rs.getInt("id_usuario"));
                System.out.println("anio inicio: " + rs.getInt("anio_inicio"));
                System.out.println("mes inicio: " + rs.getInt("mes_inicio"));
                System.out.println("anio fin: " + rs.getInt("anio_fin"));
                System.out.println("mes fin: " + rs.getInt("mes_fin"));
                System.out.println("total ingresos ejecutados: " + rs.getDouble("total_ingresos_ejecutados"));
                System.out.println("total gastos ejecutados: " + rs.getDouble("total_gastos_ejecutados"));
                System.out.println("total ahorros ejecutados: " + rs.getDouble("total_ahorros_ejecutados"));
                System.out.println("balance final: " + rs.getDouble("balance_final"));
            }
            return true;
        }catch(SQLException e){
            System.out.println("Error al cerrar presupuesto:");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public boolean obtener_resumen_categoria_mes(
        int id_categoria,
        int id_presupuesto,
        int anio,
        int mes
    ){
        String sql = "{call sp_obtener_resumen_categoria_mes(?, ?, ?, ?, ?, ?, ?)}";

        try(Connection conexion = conexion_bd.obtener_conexion();
            CallableStatement cs = conexion.prepareCall(sql)){

            cs.setInt(1, id_categoria);
            cs.setInt(2, id_presupuesto);
            cs.setInt(3, anio);
            cs.setInt(4, mes);

            cs.registerOutParameter(5, java.sql.Types.DECIMAL);
            cs.registerOutParameter(6, java.sql.Types.DECIMAL);
            cs.registerOutParameter(7, java.sql.Types.DECIMAL);

            boolean tieneResultado = cs.execute();

            if(tieneResultado){
                ResultSet rs = cs.getResultSet();

                if(rs.next()){
                    System.out.println("------------------------------------------");
                    System.out.println("id categoria: " + rs.getInt("id_categoria"));
                    System.out.println("nombre categoria: " + rs.getString("nombre_categoria"));
                    System.out.println("id presupuesto: " + rs.getInt("id_presupuesto"));
                    System.out.println("nombre presupuesto: " + rs.getString("nombre_presupuesto"));
                    System.out.println("anio: " + rs.getInt("anio"));
                    System.out.println("mes: " + rs.getInt("mes"));
                    System.out.println("monto presupuestado: " + rs.getDouble("monto_presupuestado"));
                    System.out.println("monto ejecutado: " + rs.getDouble("monto_ejecutado"));
                    System.out.println("porcentaje: " + rs.getDouble("porcentaje"));
                }
            }

            System.out.println("------------------------------------------");
            System.out.println("monto presupuestado (out): " + cs.getDouble(5));
            System.out.println("monto ejecutado (out): " + cs.getDouble(6));
            System.out.println("porcentaje (out): " + cs.getDouble(7));
            return true;
        }catch(SQLException e){
            System.out.println("Error al obtener resumen de categoria del mes:");
            System.out.println(e.getMessage());
            return false;
        }
    }
}