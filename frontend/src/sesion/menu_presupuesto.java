package sesion;
import java.util.ArrayList;
import java.util.Scanner;
import presupuesto.presupuesto;
import presupuesto.presupuesto_db;
import usuario.usuario;

public class menu_presupuesto{
    Scanner sc = new Scanner(System.in);
    presupuesto_db presupuesto_db = new presupuesto_db();

    public void mostrar_menu_admin(usuario usuario_actual){
        int opcion;
        do{
            System.out.println("\n------------------------------------------");
            System.out.println("-------------- PRESUPUESTO ---------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar presupuesto");
            System.out.println("2. Modificar presupuesto");
            System.out.println("3. Eliminar presupuesto");
            System.out.println("4. Consultar presupuesto");
            System.out.println("5. Listar presupuestos");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_presupuesto();
                    break;
                case 2:
                    modificar_presupuesto();
                    break;
                case 3:
                    eliminar_presupuesto();
                    break;
                case 4:
                    consultar_presupuesto();
                    break;
                case 5:
                    listar_presupuestos();
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
            System.out.println("-------------- PRESUPUESTO ---------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar mis presupuestos");
            System.out.println("2. Modificar mis presupuestos");
            System.out.println("3. Eliminar mis presupuestos");
            System.out.println("4. Consultar mis presupuestos");
            System.out.println("0. Volver");

            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_mi_presupuesto(usuario_actual);
                    break;
                case 2:
                    modificar_mi_presupuesto(usuario_actual);
                    break;
                case 3:
                    eliminar_mi_presupuesto(usuario_actual);
                    break;
                case 4:
                    consultar_mis_presupuestos(usuario_actual);
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

    public void insertar_presupuesto(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- INSERTAR PRESUPUESTO ---------");
        System.out.println("------------------------------------------");

        presupuesto p = new presupuesto();

        System.out.print("id usuario: ");
        p.set_id_usuario(Integer.parseInt(sc.nextLine()));

        System.out.print("nombre: ");
        p.set_nombre(sc.nextLine());

        System.out.print("anio inicio: ");
        p.set_anio_inicio(Integer.parseInt(sc.nextLine()));

        System.out.print("mes inicio: ");
        p.set_mes_inicio(Integer.parseInt(sc.nextLine()));

        System.out.print("anio fin: ");
        p.set_anio_fin(Integer.parseInt(sc.nextLine()));

        System.out.print("mes fin: ");
        p.set_mes_fin(Integer.parseInt(sc.nextLine()));

        System.out.print("creado por: ");
        p.set_creado_por(sc.nextLine());

        boolean insertado = presupuesto_db.insertar_presupuesto(p);

        if(insertado){
            System.out.println("El presupuesto fue insertado correctamente");
        }
    }

    public void modificar_presupuesto(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- MODIFICAR PRESUPUESTO --------");
        System.out.println("------------------------------------------");

        presupuesto p = new presupuesto();

        System.out.print("id presupuesto: ");
        p.set_id_presupuesto(Integer.parseInt(sc.nextLine()));

        System.out.print("nombre: ");
        p.set_nombre(sc.nextLine());

        System.out.print("anio inicio: ");
        p.set_anio_inicio(Integer.parseInt(sc.nextLine()));

        System.out.print("mes inicio: ");
        p.set_mes_inicio(Integer.parseInt(sc.nextLine()));

        System.out.print("anio fin: ");
        p.set_anio_fin(Integer.parseInt(sc.nextLine()));

        System.out.print("mes fin: ");
        p.set_mes_fin(Integer.parseInt(sc.nextLine()));

        System.out.print("modificado por: ");
        p.set_modificado_por(sc.nextLine());

        boolean modificado = presupuesto_db.modificar_presupuesto(p);

        if(modificado){
            System.out.println("El presupuesto fue modificado correctamente");
        }
    }

    public void eliminar_presupuesto(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- ELIMINAR PRESUPUESTO ---------");
        System.out.println("------------------------------------------");

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        boolean eliminado = presupuesto_db.eliminar_presupuesto(id_presupuesto);

        if(eliminado){
            System.out.println("El presupuesto fue eliminado correctamente");
        }
    }

    public void consultar_presupuesto(){
        System.out.println("\n------------------------------------------");
        System.out.println("---------- CONSULTAR PRESUPUESTO ---------");
        System.out.println("------------------------------------------");

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        presupuesto p = presupuesto_db.consultar_presupuesto(id_presupuesto);

        if(p != null){
            System.out.println("------------------------------------------");
            mostrar_presupuesto(p);
        }
    }

    public void listar_presupuestos(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- LISTAR PRESUPUESTOS ----------");
        System.out.println("------------------------------------------");

        System.out.print("id usuario: ");
        int id_usuario = Integer.parseInt(sc.nextLine());

        int opcion_estado;
        Boolean estado = null;
        do{
            System.out.println("Seleccione estado:");
            System.out.println("1. activos");
            System.out.println("0. todos");
            System.out.print("Opcion: ");
            opcion_estado = Integer.parseInt(sc.nextLine());

            switch(opcion_estado){
                case 1:
                    estado = true;
                    break;
                case 0:
                    estado = null;
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }while(opcion_estado != 0 && opcion_estado != 1);

        ArrayList<presupuesto> lista = presupuesto_db.listar_presupuestos_usuario(id_usuario, estado);

        if(lista.size() == 0){
            System.out.println("No hay presupuestos para mostrar");
        }else{
            for(presupuesto p : lista){
                System.out.println("------------------------------------------");
                mostrar_presupuesto(p);
            }
        }
    }

    public void insertar_mi_presupuesto(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- INSERTAR PRESUPUESTO ---------");
        System.out.println("------------------------------------------");

        presupuesto p = new presupuesto();

        p.set_id_usuario(usuario_actual.get_id_usuario());

        System.out.print("nombre: ");
        p.set_nombre(sc.nextLine());

        System.out.print("anio inicio: ");
        p.set_anio_inicio(Integer.parseInt(sc.nextLine()));

        System.out.print("mes inicio: ");
        p.set_mes_inicio(Integer.parseInt(sc.nextLine()));

        System.out.print("anio fin: ");
        p.set_anio_fin(Integer.parseInt(sc.nextLine()));

        System.out.print("mes fin: ");
        p.set_mes_fin(Integer.parseInt(sc.nextLine()));

        System.out.print("creado por: ");
        p.set_creado_por(sc.nextLine());

        boolean insertado = presupuesto_db.insertar_presupuesto(p);

        if(insertado){
            System.out.println("El presupuesto fue insertado correctamente");
        }
    }

    public void modificar_mi_presupuesto(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- MODIFICAR PRESUPUESTO --------");
        System.out.println("------------------------------------------");

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        boolean pertenece = presupuesto_db.presupuesto_pertenece_a_usuario(
            id_presupuesto,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no encontre el id en tus presupuestos");
            return;
        }

        presupuesto p = new presupuesto();

        p.set_id_presupuesto(id_presupuesto);

        System.out.print("nombre: ");
        p.set_nombre(sc.nextLine());

        System.out.print("anio inicio: ");
        p.set_anio_inicio(Integer.parseInt(sc.nextLine()));

        System.out.print("mes inicio: ");
        p.set_mes_inicio(Integer.parseInt(sc.nextLine()));

        System.out.print("anio fin: ");
        p.set_anio_fin(Integer.parseInt(sc.nextLine()));

        System.out.print("mes fin: ");
        p.set_mes_fin(Integer.parseInt(sc.nextLine()));

        System.out.print("modificado por: ");
        p.set_modificado_por(sc.nextLine());

        boolean modificado = presupuesto_db.modificar_presupuesto(p);

        if(modificado){
            System.out.println("El presupuesto fue modificado correctamente");
        }
    }

    public void eliminar_mi_presupuesto(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- ELIMINAR PRESUPUESTO ---------");
        System.out.println("------------------------------------------");

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        boolean pertenece = presupuesto_db.presupuesto_pertenece_a_usuario(
            id_presupuesto,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no encontre el id en tus presupuestos");
            return;
        }

        boolean eliminado = presupuesto_db.eliminar_presupuesto(id_presupuesto);

        if(eliminado){
            System.out.println("El presupuesto fue eliminado correctamente");
        }
    }

    public void consultar_mis_presupuestos(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- CONSULTAR MIS PRESUPUESTOS ------");
        System.out.println("------------------------------------------");

        ArrayList<presupuesto> lista = presupuesto_db.listar_presupuestos_usuario(
            usuario_actual.get_id_usuario(),
            null
        );

        if(lista.size() == 0){
            System.out.println("Lo siento, no tienes presupuestos registrados");
        }else{
            for(presupuesto p : lista){
                System.out.println("------------------------------------------");
                mostrar_presupuesto(p);
            }
        }
    }

    public void mostrar_presupuesto(presupuesto p){
        System.out.println("id presupuesto: " + p.get_id_presupuesto());
        System.out.println("id usuario: " + p.get_id_usuario());
        System.out.println("nombre: " + p.get_nombre());
        System.out.println("anio inicio: " + p.get_anio_inicio());
        System.out.println("mes inicio: " + p.get_mes_inicio());
        System.out.println("anio fin: " + p.get_anio_fin());
        System.out.println("mes fin: " + p.get_mes_fin());
        System.out.println("total ingresos: " + p.get_total_ingresos());
        System.out.println("total gastos: " + p.get_total_gastos());
        System.out.println("total ahorros: " + p.get_total_ahorros());
        System.out.println("estado presupuesto: " + p.is_estado_presupuesto());
    }
}