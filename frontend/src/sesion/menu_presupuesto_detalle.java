package sesion;
import java.util.ArrayList;
import java.util.Scanner;
import presupuesto_detalle.presupuesto_detalle;
import presupuesto_detalle.presupuesto_detalle_db;
import usuario.usuario;

public class menu_presupuesto_detalle{
    Scanner sc = new Scanner(System.in);
    presupuesto_detalle_db presupuesto_detalle_db = new presupuesto_detalle_db();

    public void mostrar_menu_admin(usuario usuario_actual){
        int opcion;
        do{
            System.out.println("\n------------------------------------------");
            System.out.println("---------- PRESUPUESTO DETALLE -----------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar detalle");
            System.out.println("2. Modificar detalle");
            System.out.println("3. Eliminar detalle");
            System.out.println("4. Consultar detalle");
            System.out.println("5. Listar detalle");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_detalle();
                    break;
                case 2:
                    modificar_detalle();
                    break;
                case 3:
                    eliminar_detalle();
                    break;
                case 4:
                    consultar_detalle();
                    break;
                case 5:
                    listar_detalle();
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
            System.out.println("---------- PRESUPUESTO DETALLE -----------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar mis detalles");
            System.out.println("2. Modificar mis detalles");
            System.out.println("3. Eliminar mis detalles");
            System.out.println("4. Consultar mis detalles");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_mi_detalle(usuario_actual);
                    break;
                case 2:
                    modificar_mi_detalle(usuario_actual);
                    break;
                case 3:
                    eliminar_mi_detalle(usuario_actual);
                    break;
                case 4:
                    consultar_mis_detalles(usuario_actual);
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

    public void insertar_detalle(){
        System.out.println("\n------------------------------------------");
        System.out.println("------ INSERTAR PRESUPUESTO DETALLE ------");
        System.out.println("------------------------------------------");

        presupuesto_detalle pd = new presupuesto_detalle();

        System.out.print("id presupuesto: ");
        pd.set_id_presupuesto(Integer.parseInt(sc.nextLine()));

        System.out.print("id subcategoria: ");
        pd.set_id_subcategoria(Integer.parseInt(sc.nextLine()));

        System.out.print("monto mensual asignado: ");
        pd.set_monto_mensual_asignado(Double.parseDouble(sc.nextLine()));

        System.out.print("observaciones: ");
        pd.set_observaciones(sc.nextLine());

        System.out.print("creado por: ");
        pd.set_creado_por(sc.nextLine());

        boolean insertado = presupuesto_detalle_db.insertar_presupuesto_detalle(pd);

        if(insertado){
            System.out.println("El detalle fue insertado correctamente");
        }
    }

    public void modificar_detalle(){
        System.out.println("\n------------------------------------------");
        System.out.println("------ MODIFICAR PRESUPUESTO DETALLE -----");
        System.out.println("------------------------------------------");

        presupuesto_detalle pd = new presupuesto_detalle();

        System.out.print("id detalle: ");
        pd.set_id_detalle(Integer.parseInt(sc.nextLine()));

        System.out.print("monto mensual asignado: ");
        pd.set_monto_mensual_asignado(Double.parseDouble(sc.nextLine()));

        System.out.print("observaciones: ");
        pd.set_observaciones(sc.nextLine());

        System.out.print("modificado por: ");
        pd.set_modificado_por(sc.nextLine());

        boolean modificado = presupuesto_detalle_db.modificar_presupuesto_detalle(pd);

        if(modificado){
            System.out.println("El detalle fue modificado correctamente");
        }
    }

    public void eliminar_detalle(){
        System.out.println("\n------------------------------------------");
        System.out.println("------ ELIMINAR PRESUPUESTO DETALLE ------");
        System.out.println("------------------------------------------");

        System.out.print("id detalle: ");
        int id_detalle = Integer.parseInt(sc.nextLine());

        boolean eliminado = presupuesto_detalle_db.eliminar_presupuesto_detalle(id_detalle);

        if(eliminado){
            System.out.println("El detalle fue eliminado correctamente");
        }
    }

    public void consultar_detalle(){
        System.out.println("\n------------------------------------------");
        System.out.println("------ CONSULTAR PRESUPUESTO DETALLE -----");
        System.out.println("------------------------------------------");

        System.out.print("id detalle: ");
        int id_detalle = Integer.parseInt(sc.nextLine());

        presupuesto_detalle pd = presupuesto_detalle_db.consultar_presupuesto_detalle(id_detalle);

        if(pd != null){
            System.out.println("------------------------------------------");
            mostrar_presupuesto_detalle(pd);
        }
    }

    public void listar_detalle(){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- LISTAR DETALLE PRESUPUESTO ------");
        System.out.println("------------------------------------------");

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        ArrayList<presupuesto_detalle> lista = presupuesto_detalle_db.listar_presupuesto_detalle(id_presupuesto);

        if(lista.size() == 0){
            System.out.println("No hay detalles para mostrar");
        }else{
            for(presupuesto_detalle pd : lista){
                System.out.println("------------------------------------------");
                mostrar_presupuesto_detalle(pd);
            }
        }
    }

    public void insertar_mi_detalle(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------ INSERTAR PRESUPUESTO DETALLE ------");
        System.out.println("------------------------------------------");

        presupuesto_detalle pd = new presupuesto_detalle();

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        boolean pertenece = presupuesto_detalle_db.presupuesto_pertenece_a_usuario(
            id_presupuesto,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no encontre el presupuesto en tus presupuestos");
            return;
        }

        pd.set_id_presupuesto(id_presupuesto);

        System.out.print("id subcategoria: ");
        pd.set_id_subcategoria(Integer.parseInt(sc.nextLine()));

        System.out.print("monto mensual asignado: ");
        pd.set_monto_mensual_asignado(Double.parseDouble(sc.nextLine()));

        System.out.print("observaciones: ");
        pd.set_observaciones(sc.nextLine());

        System.out.print("creado por: ");
        pd.set_creado_por(sc.nextLine());

        boolean insertado = presupuesto_detalle_db.insertar_presupuesto_detalle(pd);

        if(insertado){
            System.out.println("El detalle fue insertado correctamente");
        }
    }

    public void modificar_mi_detalle(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------ MODIFICAR PRESUPUESTO DETALLE -----");
        System.out.println("------------------------------------------");

        System.out.print("id detalle: ");
        int id_detalle = Integer.parseInt(sc.nextLine());

        boolean pertenece = presupuesto_detalle_db.detalle_pertenece_a_usuario(
            id_detalle,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no encontre el id en tus detalles");
            return;
        }

        presupuesto_detalle pd = new presupuesto_detalle();

        pd.set_id_detalle(id_detalle);

        System.out.print("monto mensual asignado: ");
        pd.set_monto_mensual_asignado(Double.parseDouble(sc.nextLine()));

        System.out.print("observaciones: ");
        pd.set_observaciones(sc.nextLine());

        System.out.print("modificado por: ");
        pd.set_modificado_por(sc.nextLine());

        boolean modificado = presupuesto_detalle_db.modificar_presupuesto_detalle(pd);

        if(modificado){
            System.out.println("El detalle fue modificado correctamente");
        }
    }

    public void eliminar_mi_detalle(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------ ELIMINAR PRESUPUESTO DETALLE ------");
        System.out.println("------------------------------------------");

        System.out.print("id detalle: ");
        int id_detalle = Integer.parseInt(sc.nextLine());

        boolean pertenece = presupuesto_detalle_db.detalle_pertenece_a_usuario(
            id_detalle,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no encontre el id en tus detalles");
            return;
        }

        boolean eliminado = presupuesto_detalle_db.eliminar_presupuesto_detalle(id_detalle);

        if(eliminado){
            System.out.println("El detalle fue eliminado correctamente");
        }
    }

    public void consultar_mis_detalles(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------ CONSULTAR PRESUPUESTO DETALLE -----");
        System.out.println("------------------------------------------");

        System.out.print("id detalle: ");
        int id_detalle = Integer.parseInt(sc.nextLine());

        boolean pertenece = presupuesto_detalle_db.detalle_pertenece_a_usuario(
            id_detalle,
            usuario_actual.get_id_usuario()
        );

        if(!pertenece){
            System.out.println("Lo siento, no encontre el id en tus detalles");
            return;
        }

        presupuesto_detalle pd = presupuesto_detalle_db.consultar_presupuesto_detalle(id_detalle);

        if(pd != null){
            System.out.println("------------------------------------------");
            mostrar_presupuesto_detalle(pd);
        }
    }

    public void mostrar_presupuesto_detalle(presupuesto_detalle pd){
        System.out.println("id detalle: " + pd.get_id_detalle());
        System.out.println("id presupuesto: " + pd.get_id_presupuesto());
        System.out.println("id subcategoria: " + pd.get_id_subcategoria());
        System.out.println("monto mensual asignado: " + pd.get_monto_mensual_asignado());
        System.out.println("observaciones: " + pd.get_observaciones());
        System.out.println("creado por: " + pd.get_creado_por());
        System.out.println("modificado por: " + pd.get_modificado_por());
    }
}