package sesion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import obligacion_fija.obligacion_fija;
import obligacion_fija.obligacion_fija_db;
import usuario.usuario;

public class menu_obligacion_fija{
    Scanner sc = new Scanner(System.in);
    obligacion_fija_db obligacion_fija_db = new obligacion_fija_db();

    public void mostrar_menu_admin(usuario usuario_actual){
        int opcion;
        do{
            System.out.println("\n------------------------------------------");
            System.out.println("------------ OBLIGACION FIJA -------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar obligacion");
            System.out.println("2. Modificar obligacion");
            System.out.println("3. Eliminar obligacion");
            System.out.println("4. Consultar obligacion");
            System.out.println("5. Listar obligaciones");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_obligacion();
                    break;
                case 2:
                    modificar_obligacion();
                    break;
                case 3:
                    eliminar_obligacion();
                    break;
                case 4:
                    consultar_obligacion();
                    break;
                case 5:
                    listar_obligaciones(usuario_actual);
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
            System.out.println("------------ OBLIGACION FIJA -------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar mi obligacion");
            System.out.println("2. Modificar mi obligacion");
            System.out.println("3. Eliminar mi obligacion");
            System.out.println("4. Consultar mi obligacion");
            System.out.println("5. Listar mis obligaciones");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_mi_obligacion(usuario_actual);
                    break;
                case 2:
                    modificar_mi_obligacion(usuario_actual);
                    break;
                case 3:
                    eliminar_mi_obligacion(usuario_actual);
                    break;
                case 4:
                    consultar_mi_obligacion(usuario_actual);
                    break;
                case 5:
                    listar_mis_obligaciones(usuario_actual);
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

    public void insertar_obligacion(){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- INSERTAR OBLIGACION FIJA --------");
        System.out.println("------------------------------------------");

        obligacion_fija of = new obligacion_fija();

        System.out.print("id subcategoria: ");
        of.set_id_subcategoria(Integer.parseInt(sc.nextLine()));

        System.out.print("nombre: ");
        of.set_nombre(sc.nextLine());

        System.out.print("descripcion detallada: ");
        of.set_descripcion_detallada(sc.nextLine());

        System.out.print("monto fijo mensual: ");
        of.set_monto_fijo_mensual(Double.parseDouble(sc.nextLine()));

        System.out.print("dia vencimiento: ");
        of.set_dia_vencimiento(Integer.parseInt(sc.nextLine()));

        of.set_fecha_inicio(leer_fecha_hora_obligatoria("fecha inicio (yyyy-mm-dd hh:mm:ss): "));
        
        of.set_fecha_fin(leer_fecha_hora_opcional("fecha fin (yyyy-mm-dd hh:mm:ss o vacio): "));

        System.out.print("creado por: ");
        of.set_creado_por(sc.nextLine());

        boolean insertado = obligacion_fija_db.insertar_obligacion_fija(of);

        if(insertado){
            System.out.println("La obligacion fue insertada correctamente");
        }
    }

    public void modificar_obligacion(){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- MODIFICAR OBLIGACION FIJA -------");
        System.out.println("------------------------------------------");

        obligacion_fija of = new obligacion_fija();

        System.out.print("id obligacion: ");
        of.set_id_obligacion(Integer.parseInt(sc.nextLine()));

        System.out.print("nombre: ");
        of.set_nombre(sc.nextLine());

        System.out.print("descripcion detallada: ");
        of.set_descripcion_detallada(sc.nextLine());

        System.out.print("monto fijo mensual: ");
        of.set_monto_fijo_mensual(Double.parseDouble(sc.nextLine()));

        System.out.print("dia vencimiento: ");
        of.set_dia_vencimiento(Integer.parseInt(sc.nextLine()));

        of.set_fecha_fin(leer_fecha_hora_opcional("fecha fin (yyyy-mm-dd hh:mm:ss o vacio): "));

        System.out.print("modificado por: ");
        of.set_modificado_por(sc.nextLine());

        boolean modificado = obligacion_fija_db.modificar_obligacion_fija(of);

        if(modificado){
            System.out.println("La obligacion fue modificada correctamente");
        }
    }

    public void eliminar_obligacion(){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- ELIMINAR OBLIGACION FIJA --------");
        System.out.println("------------------------------------------");

        System.out.print("id obligacion: ");
        int id_obligacion = Integer.parseInt(sc.nextLine());

        boolean eliminado = obligacion_fija_db.eliminar_obligacion_fija(id_obligacion);

        if(eliminado){
            System.out.println("La obligacion fue eliminada correctamente");
        }
    }

    public void consultar_obligacion(){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- CONSULTAR OBLIGACION FIJA -------");
        System.out.println("------------------------------------------");

        System.out.print("id obligacion: ");
        int id_obligacion = Integer.parseInt(sc.nextLine());

        obligacion_fija of = obligacion_fija_db.consultar_obligacion_fija(id_obligacion);

        if(of != null){
            System.out.println("------------------------------------------");
            mostrar_obligacion_fija(of);
        }
    }

    public void listar_obligaciones(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("--------- LISTAR OBLIGACIONES FIJAS ------");
        System.out.println("------------------------------------------");

        System.out.print("id usuario: ");
        int id_usuario = Integer.parseInt(sc.nextLine());

        System.out.print("vigente (1 = si, 0 = no, -1 = todos): ");
        int filtro = Integer.parseInt(sc.nextLine());

        Integer vigente = null;
        if(filtro == 0 || filtro == 1){
            vigente = filtro;
        }

        ArrayList<obligacion_fija> lista = obligacion_fija_db.listar_obligaciones_usuario(id_usuario, vigente);

        if(lista.size() == 0){
            System.out.println("Lo siento, no hay obligaciones para mostrar");
        }else{
            for(obligacion_fija of : lista){
                System.out.println("------------------------------------------");
                mostrar_obligacion_fija(of);
            }
        }
    }

    public void insertar_mi_obligacion(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- INSERTAR OBLIGACION FIJA --------");
        System.out.println("------------------------------------------");

        obligacion_fija of = new obligacion_fija();

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        boolean pertenece = obligacion_fija_db.subcategoria_pertenece_a_usuario(
            id_subcategoria,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no se encontro la subcategoria en tus presupuestos");
            return;
        }

        of.set_id_subcategoria(id_subcategoria);

        System.out.print("nombre: ");
        of.set_nombre(sc.nextLine());

        System.out.print("descripcion detallada: ");
        of.set_descripcion_detallada(sc.nextLine());

        System.out.print("monto fijo mensual: ");
        of.set_monto_fijo_mensual(Double.parseDouble(sc.nextLine()));

        System.out.print("dia vencimiento: ");
        of.set_dia_vencimiento(Integer.parseInt(sc.nextLine()));

        of.set_fecha_inicio(leer_fecha_hora_obligatoria("fecha inicio (yyyy-mm-dd hh:mm:ss): "));
        
        of.set_fecha_fin(leer_fecha_hora_opcional("fecha fin (yyyy-mm-dd hh:mm:ss o vacio): "));

        System.out.print("creado por: ");
        of.set_creado_por(sc.nextLine());

        boolean insertado = obligacion_fija_db.insertar_obligacion_fija(of);

        if(insertado){
            System.out.println("La obligacion fue insertada correctamente");
        }
    }

    public void modificar_mi_obligacion(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- MODIFICAR OBLIGACION FIJA -------");
        System.out.println("------------------------------------------");

        System.out.print("id obligacion: ");
        int id_obligacion = Integer.parseInt(sc.nextLine());

        boolean pertenece = obligacion_fija_db.obligacion_pertenece_a_usuario(
            id_obligacion,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no se encontro el id en tus obligaciones");
            return;
        }

        obligacion_fija of = new obligacion_fija();

        of.set_id_obligacion(id_obligacion);

        System.out.print("nombre: ");
        of.set_nombre(sc.nextLine());

        System.out.print("descripcion detallada: ");
        of.set_descripcion_detallada(sc.nextLine());

        System.out.print("monto fijo mensual: ");
        of.set_monto_fijo_mensual(Double.parseDouble(sc.nextLine()));

        System.out.print("dia vencimiento: ");
        of.set_dia_vencimiento(Integer.parseInt(sc.nextLine()));

        of.set_fecha_fin(leer_fecha_hora_opcional("fecha fin (yyyy-mm-dd hh:mm:ss o vacio): "));

        System.out.print("modificado por: ");
        of.set_modificado_por(sc.nextLine());

        boolean modificado = obligacion_fija_db.modificar_obligacion_fija(of);

        if(modificado){
            System.out.println("La obligacion fue modificada correctamente");
        }
    }

   public void eliminar_mi_obligacion(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- ELIMINAR OBLIGACION FIJA --------");
        System.out.println("------------------------------------------");

        System.out.print("id obligacion: ");
        int id_obligacion = Integer.parseInt(sc.nextLine());

        boolean pertenece = obligacion_fija_db.obligacion_pertenece_a_usuario(
            id_obligacion,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no se encontro el id en tus obligaciones");
            return;
        }

        boolean eliminado = obligacion_fija_db.eliminar_obligacion_fija(id_obligacion);

        if(eliminado){
            System.out.println("La obligacion fue eliminada correctamente");
        }
    }

    public void consultar_mi_obligacion(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- CONSULTAR OBLIGACION FIJA -------");
        System.out.println("------------------------------------------");

        System.out.print("id obligacion: ");
        int id_obligacion = Integer.parseInt(sc.nextLine());

        boolean pertenece = obligacion_fija_db.obligacion_pertenece_a_usuario(
            id_obligacion,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no encontre el id en tus obligaciones");
            return;
        }

        obligacion_fija of = obligacion_fija_db.consultar_obligacion_fija(id_obligacion);

        if(of != null){
            System.out.println("------------------------------------------");
            mostrar_obligacion_fija(of);
        }
    }

    public void listar_mis_obligaciones(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("--------- LISTAR MIS OBLIGACIONES --------");
        System.out.println("------------------------------------------");

        System.out.print("vigente (1 = si, 0 = no, -1 = todos): ");
        int filtro = Integer.parseInt(sc.nextLine());

        Integer vigente = null;
        if(filtro == 0 || filtro == 1){
            vigente = filtro;
        }

        ArrayList<obligacion_fija> lista = obligacion_fija_db.listar_obligaciones_usuario(
            usuario_actual.get_id_usuario(),
            vigente
        );

        if(lista.size() == 0){
            System.out.println("Lo siento, no hay obligaciones para mostrar");
        }else{
            for(obligacion_fija of : lista){
                System.out.println("------------------------------------------");
                mostrar_obligacion_fija(of);
            }
        }
    }

    public void mostrar_obligacion_fija(obligacion_fija of){
        System.out.println("id obligacion: " + of.get_id_obligacion());
        System.out.println("id subcategoria: " + of.get_id_subcategoria());
        System.out.println("nombre: " + of.get_nombre());
        System.out.println("descripcion detallada: " + of.get_descripcion_detallada());
        System.out.println("monto fijo mensual: " + of.get_monto_fijo_mensual());
        System.out.println("dia vencimiento: " + of.get_dia_vencimiento());
        System.out.println("vigente: " + of.is_vigente());
        System.out.println("fecha inicio: " + of.get_fecha_inicio());
        System.out.println("fecha fin: " + of.get_fecha_fin());
        System.out.println("creado por: " + of.get_creado_por());
        System.out.println("modificado por: " + of.get_modificado_por());
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
                System.out.println("Ejemplo: 2026-12-31 00:00:00");
            }
        }while(!fecha_hora_valida(fecha));

        return fecha;
    }

    private String leer_fecha_hora_opcional(String mensaje){
        String fecha;

        do{
            System.out.print(mensaje);
            fecha = sc.nextLine();

            if(fecha.trim().isEmpty()){
                return "";
            }

            if(!fecha_hora_valida(fecha)){
                System.out.println("Error: debe escribir la fecha y hora en este formato: yyyy-mm-dd hh:mm:ss");
                System.out.println("Ejemplo: 2026-12-31 23:59:59");
                System.out.println("O deje vacio si no desea registrar fecha fin.");
            }
        }while(!fecha_hora_valida(fecha));

        return fecha;
    }
}

