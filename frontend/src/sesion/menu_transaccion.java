package sesion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import transaccion.transaccion;
import transaccion.transaccion_db;
import usuario.usuario;

public class menu_transaccion{
    Scanner sc = new Scanner(System.in);
    transaccion_db transaccion_db = new transaccion_db();

    public void mostrar_menu_admin(usuario usuario_actual){
        int opcion;

        do{
            System.out.println("\n------------------------------------------");
            System.out.println("------------- TRANSACCIONES --------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar transaccion");
            System.out.println("2. Modificar transaccion");
            System.out.println("3. Eliminar transaccion");
            System.out.println("4. Consultar transaccion");
            System.out.println("5. Listar transacciones");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_transaccion();
                    break;
                case 2:
                    modificar_transaccion();
                    break;
                case 3:
                    eliminar_transaccion();
                    break;
                case 4:
                    consultar_transaccion();
                    break;
                case 5:
                    listar_transacciones();
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

    public void mostrar_menu_usuario(usuario usuario_actual){
        int opcion;

        do{
            System.out.println("\n------------------------------------------");
            System.out.println("------------- TRANSACCIONES --------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar mi transaccion");
            System.out.println("2. Modificar mi transaccion");
            System.out.println("3. Eliminar mi transaccion");
            System.out.println("4. Consultar mi transaccion");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_mi_transaccion(usuario_actual);
                    break;
                case 2:
                    modificar_mi_transaccion(usuario_actual);
                    break;
                case 3:
                    eliminar_mi_transaccion(usuario_actual);
                    break;
                case 4:
                    consultar_mi_transaccion(usuario_actual);
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

    public void insertar_transaccion(){
        System.out.println("\n------------------------------------------");
        System.out.println("---------- INSERTAR TRANSACCION ----------");
        System.out.println("------------------------------------------");

        transaccion t = new transaccion();

        System.out.print("id detalle: ");
        t.set_id_detalle(Integer.parseInt(sc.nextLine()));

        System.out.print("anio transaccion: ");
        t.set_anio_transaccion(Integer.parseInt(sc.nextLine()));

        System.out.print("mes transaccion: ");
        t.set_mes_transaccion(Integer.parseInt(sc.nextLine()));

        t.set_tipo_transaccion(leer_tipo_transaccion());

        System.out.print("descripcion: ");
        t.set_descripcion(sc.nextLine());

        System.out.print("monto transaccion: ");
        t.set_monto_transaccion(Double.parseDouble(sc.nextLine()));

        t.set_fecha_transaccion(leer_fecha_hora_obligatoria("fecha transaccion (yyyy-mm-dd hh:mm:ss): "));

        t.set_metodo_pago(leer_metodo_pago());

        System.out.print("numero factura (0 si no tiene): ");
        t.set_num_factura(Integer.parseInt(sc.nextLine()));

        System.out.print("observaciones: ");
        t.set_observaciones(sc.nextLine());

        System.out.print("creado por: ");
        t.set_creado_por(sc.nextLine());

        boolean insertado = transaccion_db.insertar_transaccion(t);

        if(insertado){
            System.out.println("La transaccion fue insertada correctamente");
        }
    }

    public void modificar_transaccion(){
        System.out.println("\n------------------------------------------");
        System.out.println("---------- MODIFICAR TRANSACCION ---------");
        System.out.println("------------------------------------------");

        transaccion t = new transaccion();

        System.out.print("id transaccion: ");
        t.set_id_transaccion(Integer.parseInt(sc.nextLine()));

        System.out.print("anio transaccion: ");
        t.set_anio_transaccion(Integer.parseInt(sc.nextLine()));

        System.out.print("mes transaccion: ");
        t.set_mes_transaccion(Integer.parseInt(sc.nextLine()));

        System.out.print("descripcion: ");
        t.set_descripcion(sc.nextLine());

        System.out.print("monto transaccion: ");
        t.set_monto_transaccion(Double.parseDouble(sc.nextLine()));

        t.set_fecha_transaccion(leer_fecha_hora_obligatoria("fecha transaccion (yyyy-mm-dd hh:mm:ss): "));

        t.set_metodo_pago(leer_metodo_pago());

        System.out.print("numero factura (0 si no tiene): ");
        t.set_num_factura(Integer.parseInt(sc.nextLine()));

        System.out.print("observaciones: ");
        t.set_observaciones(sc.nextLine());

        System.out.print("modificado por: ");
        t.set_modificado_por(sc.nextLine());

        boolean modificado = transaccion_db.modificar_transaccion(t);

        if(modificado){
            System.out.println("La transaccion fue modificada correctamente");
        }
    }

    public void eliminar_transaccion(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- ELIMINAR TRANSACCION ---------");
        System.out.println("------------------------------------------");

        System.out.print("id transaccion: ");
        int id_transaccion = Integer.parseInt(sc.nextLine());

        boolean eliminado = transaccion_db.eliminar_transaccion(id_transaccion);

        if(eliminado){
            System.out.println("La transaccion fue eliminada correctamente");
        }
    }

    public void consultar_transaccion(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- CONSULTAR TRANSACCION --------");
        System.out.println("------------------------------------------");

        System.out.print("id transaccion: ");
        int id_transaccion = Integer.parseInt(sc.nextLine());

        transaccion t = transaccion_db.consultar_transaccion(id_transaccion);

        if(t != null){
            System.out.println("------------------------------------------");
            mostrar_transaccion(t);
        }
    }

    public void listar_transacciones(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- LISTAR TRANSACCIONES ---------");
        System.out.println("------------------------------------------");

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("anio (0 para todos): ");
        int anioIngresado = Integer.parseInt(sc.nextLine());

        System.out.print("mes (0 para todos): ");
        int mesIngresado = Integer.parseInt(sc.nextLine());

        String tipo = leer_tipo_transaccion_filtro();

        Integer anio = null;
        Integer mes = null;

        if(anioIngresado > 0){
            anio = anioIngresado;
        }

        if(mesIngresado > 0){
            mes = mesIngresado;
        }

        ArrayList<transaccion> lista = transaccion_db.listar_transacciones_presupuesto(id_presupuesto, anio, mes, tipo);

        if(lista.size() == 0){
            System.out.println("No hay transacciones para mostrar");
        }else{
            for(transaccion t : lista){
                System.out.println("------------------------------------------");
                mostrar_transaccion(t);
            }
        }
    }

    public void insertar_mi_transaccion(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("---------- INSERTAR TRANSACCION ----------");
        System.out.println("------------------------------------------");

        transaccion t = new transaccion();

        System.out.print("id detalle: ");
        int id_detalle = Integer.parseInt(sc.nextLine());

        boolean pertenece = transaccion_db.detalle_pertenece_a_usuario(
            id_detalle,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no se encontro el detalle dentro de tus presupuestos");
            return;
        }

        t.set_id_detalle(id_detalle);

        System.out.print("anio transaccion: ");
        t.set_anio_transaccion(Integer.parseInt(sc.nextLine()));

        System.out.print("mes transaccion: ");
        t.set_mes_transaccion(Integer.parseInt(sc.nextLine()));

        t.set_tipo_transaccion(leer_tipo_transaccion());

        System.out.print("descripcion: ");
        t.set_descripcion(sc.nextLine());

        System.out.print("monto transaccion: ");
        t.set_monto_transaccion(Double.parseDouble(sc.nextLine()));

        t.set_fecha_transaccion(leer_fecha_hora_obligatoria("fecha transaccion (yyyy-mm-dd hh:mm:ss): "));

        t.set_metodo_pago(leer_metodo_pago());

        System.out.print("numero factura (0 si no tiene): ");
        t.set_num_factura(Integer.parseInt(sc.nextLine()));

        System.out.print("observaciones: ");
        t.set_observaciones(sc.nextLine());

        System.out.print("creado por: ");
        t.set_creado_por(sc.nextLine());

        boolean insertado = transaccion_db.insertar_transaccion(t);

        if(insertado){
            System.out.println("La transaccion fue insertada correctamente");
        }
    }

    public void modificar_mi_transaccion(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("---------- MODIFICAR TRANSACCION ---------");
        System.out.println("------------------------------------------");

        System.out.print("id transaccion: ");
        int id_transaccion = Integer.parseInt(sc.nextLine());

        boolean pertenece = transaccion_db.transaccion_pertenece_a_usuario(
            id_transaccion,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no se encontro el id en tus transacciones");
            return;
        }

        transaccion t = new transaccion();
        t.set_id_transaccion(id_transaccion);

        System.out.print("anio transaccion: ");
        t.set_anio_transaccion(Integer.parseInt(sc.nextLine()));

        System.out.print("mes transaccion: ");
        t.set_mes_transaccion(Integer.parseInt(sc.nextLine()));

        System.out.print("descripcion: ");
        t.set_descripcion(sc.nextLine());

        System.out.print("monto transaccion: ");
        t.set_monto_transaccion(Double.parseDouble(sc.nextLine()));

        t.set_fecha_transaccion(leer_fecha_hora_obligatoria("fecha transaccion (yyyy-mm-dd hh:mm:ss): "));

        t.set_metodo_pago(leer_metodo_pago());

        System.out.print("numero factura (0 si no tiene): ");
        t.set_num_factura(Integer.parseInt(sc.nextLine()));

        System.out.print("observaciones: ");
        t.set_observaciones(sc.nextLine());

        System.out.print("modificado por: ");
        t.set_modificado_por(sc.nextLine());

        boolean modificado = transaccion_db.modificar_transaccion(t);

        if(modificado){
            System.out.println("La transaccion fue modificada correctamente");
        }
    }

    public void eliminar_mi_transaccion(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- ELIMINAR TRANSACCION ---------");
        System.out.println("------------------------------------------");

        System.out.print("id transaccion: ");
        int id_transaccion = Integer.parseInt(sc.nextLine());

        boolean pertenece = transaccion_db.transaccion_pertenece_a_usuario(
            id_transaccion,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no se encontro el id en tus transacciones");
            return;
        }

        boolean eliminado = transaccion_db.eliminar_transaccion(id_transaccion);

        if(eliminado){
            System.out.println("La transaccion fue eliminada correctamente");
        }
    }

    public void consultar_mi_transaccion(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- CONSULTAR TRANSACCION --------");
        System.out.println("------------------------------------------");

        System.out.print("id transaccion: ");
        int id_transaccion = Integer.parseInt(sc.nextLine());

        boolean pertenece = transaccion_db.transaccion_pertenece_a_usuario(
            id_transaccion,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no se encontro el id en tus transacciones");
            return;
        }

        transaccion t = transaccion_db.consultar_transaccion(id_transaccion);

        if(t != null){
            System.out.println("------------------------------------------");
            mostrar_transaccion(t);
        }
    }

    public void mostrar_transaccion(transaccion t){
        System.out.println("id transaccion: " + t.get_id_transaccion());
        System.out.println("anio transaccion: " + t.get_anio_transaccion());
        System.out.println("mes transaccion: " + t.get_mes_transaccion());
        System.out.println("tipo transaccion: " + t.get_tipo_transaccion());
        System.out.println("descripcion: " + t.get_descripcion());
        System.out.println("monto transaccion: " + t.get_monto_transaccion());
        System.out.println("fecha transaccion: " + t.get_fecha_transaccion());
        System.out.println("metodo pago: " + t.get_metodo_pago());
        System.out.println("numero factura: " + t.get_num_factura());
        System.out.println("observaciones: " + t.get_observaciones());
        System.out.println("creado por: " + t.get_creado_por());
        System.out.println("modificado por: " + t.get_modificado_por());
        System.out.println("id presupuesto: " + t.get_id_presupuesto());
        System.out.println("id subcategoria: " + t.get_id_subcategoria());
        System.out.println("nombre subcategoria: " + t.get_nombre_subcategoria());
        System.out.println("id categoria: " + t.get_id_categoria());
        System.out.println("nombre categoria: " + t.get_nombre_categoria());
    }

    private String leer_metodo_pago(){
        int opcion;
        do{
            System.out.println("metodo de pago:");
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
    
    private String leer_tipo_transaccion_filtro(){
        int opcion;
        do{
            System.out.println("tipo de transaccion:");
            System.out.println("0. todos");
            System.out.println("1. ingreso");
            System.out.println("2. gasto");
            System.out.println("3. ahorro");
            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 0:
                    return null;
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

    private boolean fecha_hora_valida(String fecha){
        try{
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime.parse(fecha, formato);
            return true;
        }catch(DateTimeParseException e){
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
                System.out.println("Ejemplo: 2026-04-10 00:00:00");
            }
        }while(!fecha_hora_valida(fecha));

        return fecha;
    }
}