package sesion;
import java.util.Scanner;
import usuario.usuario;
import procedimientos.procedimientos_logicos_db;

public class menu_procedimientos_logicos{
    Scanner sc = new Scanner(System.in);
    procedimientos_logicos_db procedimientos_logicos_db = new procedimientos_logicos_db();

    public void mostrar_menu(usuario usuario_actual){
        int opcion;
        do{
            System.out.println("\n------------------------------------------");
            System.out.println("------ MENU PROCEDIMIENTOS LOGICOS -------");
            System.out.println("------------------------------------------");
            System.out.println("1. Crear presupuesto completo");
            System.out.println("2. Registrar transaccion completa");
            System.out.println("3. Procesar obligaciones del mes");
            System.out.println("4. Calcular balance mensual");
            System.out.println("5. Calcular monto ejecutado del mes");
            System.out.println("6. Calcular porcentaje ejecutado del mes");
            System.out.println("7. Cerrar presupuesto");
            System.out.println("8. Obtener resumen categoria del mes");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());
            switch(opcion){
                case 1:
                    crear_presupuesto_completo(usuario_actual);
                    break;
                case 2:
                    registrar_transaccion_completa(usuario_actual);
                    break;
                case 3:
                    procesar_obligaciones_mes(usuario_actual);
                    break;
                case 4:
                    calcular_balance_mensual(usuario_actual);
                    break;
                case 5:
                    calcular_monto_ejecutado_mes(usuario_actual);
                    break;
                case 6:
                    calcular_porcentaje_ejecutado_mes(usuario_actual);
                    break;
                case 7:
                    cerrar_presupuesto(usuario_actual);
                    break;
                case 8:
                    obtener_resumen_categoria_mes(usuario_actual);
                    break;
                case 0:
                    System.out.println("Se regresa al menu principal");
                    break;
                default:
                    System.out.println("Lo siento, opcion invalida");
                    break;
            }
        }while(opcion != 0);
    }

    public void crear_presupuesto_completo(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------ CREAR PRESUPUESTO COMPLETO --------");
        System.out.println("------------------------------------------");

        System.out.print("id usuario: ");
        int id_usuario = Integer.parseInt(sc.nextLine());

        System.out.print("nombre presupuesto: ");
        String nombre = sc.nextLine();

        System.out.print("descripcion: ");
        String descripcion = sc.nextLine();

        System.out.print("anio inicio: ");
        int anio_inicio = Integer.parseInt(sc.nextLine());

        System.out.print("mes inicio: ");
        int mes_inicio = Integer.parseInt(sc.nextLine());

        System.out.print("anio fin: ");
        int anio_fin = Integer.parseInt(sc.nextLine());

        System.out.print("mes fin: ");
        int mes_fin = Integer.parseInt(sc.nextLine());

        String json_subcategorias = construir_json_subcategorias();

        boolean creado = procedimientos_logicos_db.crear_presupuesto_completo(
            id_usuario,
            nombre,
            descripcion,
            anio_inicio,
            mes_inicio,
            anio_fin,
            mes_fin,
            json_subcategorias,
            usuario_actual.get_primer_nombre()
        );

        if(creado){
            System.out.println("El presupuesto completo fue creado correctamente");
        }
    }

    private String construir_json_subcategorias(){
        StringBuilder json = new StringBuilder();
        json.append("[");

        boolean primero = true;
        String respuesta;

        do{
            System.out.print("id subcategoria: ");
            int id_subcategoria = Integer.parseInt(sc.nextLine());

            System.out.print("monto mensual: ");
            double monto_mensual = Double.parseDouble(sc.nextLine());

            if(!primero){
                json.append(",");
            }

            json.append("{");
            json.append("\"id_subcategoria\":").append(id_subcategoria).append(",");
            json.append("\"monto_mensual\":").append(monto_mensual);
            json.append("}");

            primero = false;

            System.out.print("Desea agregar otra subcategoria? (s/n): ");
            respuesta = sc.nextLine();

        }while(respuesta.equalsIgnoreCase("s"));
        json.append("]");
        return json.toString();
    }
    

    public void registrar_transaccion_completa(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("---- REGISTRAR TRANSACCION COMPLETA ------");
        System.out.println("------------------------------------------");

        System.out.print("id detalle: ");
        int id_detalle = Integer.parseInt(sc.nextLine());

        System.out.print("anio transaccion: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes transaccion: ");
        int mes = Integer.parseInt(sc.nextLine());

        String tipo = leer_tipo_transaccion();

        System.out.print("descripcion: ");
        String descripcion = sc.nextLine();

        System.out.print("monto transaccion: ");
        double monto = Double.parseDouble(sc.nextLine());

        String fecha = leer_fecha_hora_obligatoria("fecha transaccion (yyyy-mm-dd hh:mm:ss): ");

        String metodo_pago = leer_metodo_pago();

        System.out.print("numero factura (0 si no tiene): ");
        int num_factura = Integer.parseInt(sc.nextLine());

        System.out.print("observaciones: ");
        String observaciones = sc.nextLine();

        boolean creado = procedimientos_logicos_db.registrar_transaccion_completa(
            id_detalle,
            anio,
            mes,
            tipo,
            descripcion,
            monto,
            fecha,
            metodo_pago,
            num_factura,
            observaciones,
            usuario_actual.get_primer_nombre()
        );
        
        if(creado){
            System.out.println("La transaccion completa fue creada correctamente");
        }
    }
    
    private boolean fecha_hora_valida(String fecha){
        try{
            java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            java.time.LocalDateTime.parse(fecha, formato);
            return true;
        }catch(java.time.format.DateTimeParseException e){
            return false;
        }
    }

    private String leer_fecha_hora_obligatoria(String mensaje){
        String fecha;

        do{
            System.out.print(mensaje);
            fecha = sc.nextLine();

            if(!fecha_hora_valida(fecha)){
                System.out.println("Error: debe escribir la fecha y hora en este formato: yyyy-mm-dd hh:mm:ss");
                System.out.println("Ejemplo: 2026-09-17 00:00:00");
            }
        }while(!fecha_hora_valida(fecha));
        return fecha;
    }

    private String leer_tipo_transaccion(){
        int opcion;
        do{
            System.out.println("tipo de transaccion:");
            System.out.println("1. ingreso");
            System.out.println("2. gasto");
            System.out.println("3. ahorro");
            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    return "ingreso";
                case 2:
                    return "gasto";
                case 3:
                    return "ahorro";
                default:
                    System.out.println("Lo siento, opcion invalida");
            }
        }while(true);
    }

    private String leer_metodo_pago(){
        int opcion;
        do{
            System.out.println("Metodo de pago:");
            System.out.println("1. efectivo");
            System.out.println("2. tarjeta_debito");
            System.out.println("3. tarjeta_credito");
            System.out.println("4. transferencia");
            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    return "efectivo";
                case 2:
                    return "tarjeta_debito";
                case 3:
                    return "tarjeta_credito";
                case 4:
                    return "transferencia";
                default:
                    System.out.println("Lo siento, opcion invalida");
            }
        }while(true);
    }
    
  
    public void procesar_obligaciones_mes(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------ PROCESAR OBLIGACIONES DEL MES -----");
        System.out.println("------------------------------------------");

        System.out.print("id usuario: ");
        int id_usuario = Integer.parseInt(sc.nextLine());

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        procedimientos_logicos_db.procesar_obligaciones_mes(
            id_usuario,
            anio,
            mes,
            id_presupuesto
        );
    }
    
    
    public void calcular_balance_mensual(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- CALCULAR BALANCE MENSUAL --------");
        System.out.println("------------------------------------------");

        System.out.print("id usuario: ");
        int id_usuario = Integer.parseInt(sc.nextLine());

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        procedimientos_logicos_db.calcular_balance_mensual(
            id_usuario,
            id_presupuesto,
            anio,
            mes
        );
    }
    
    
    public void calcular_monto_ejecutado_mes(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("--- CALCULAR MONTO EJECUTADO DEL MES -----");
        System.out.println("------------------------------------------");

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        procedimientos_logicos_db.calcular_monto_ejecutado_mes(
            id_subcategoria,
            id_presupuesto,
            anio,
            mes
        );
    }
    
    
    public void calcular_porcentaje_ejecutado_mes(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("-- CALCULAR PORCENTAJE EJECUTADO DEL MES --");
        System.out.println("------------------------------------------");

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        procedimientos_logicos_db.calcular_porcentaje_ejecutado_mes(
            id_subcategoria,
            id_presupuesto,
            anio,
            mes
        );
    }
    
    
    public void cerrar_presupuesto(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- CERRAR PRESUPUESTO -----------");
        System.out.println("------------------------------------------");

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        procedimientos_logicos_db.cerrar_presupuesto(
            id_presupuesto,
            usuario_actual.get_primer_nombre()
        );
    }
    
    
    public void obtener_resumen_categoria_mes(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("---- OBTENER RESUMEN CATEGORIA DEL MES ---");
        System.out.println("------------------------------------------");

        System.out.print("id categoria: ");
        int id_categoria = Integer.parseInt(sc.nextLine());

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        procedimientos_logicos_db.obtener_resumen_categoria_mes(
            id_categoria,
            id_presupuesto,
            anio,
            mes
        );
    }
}