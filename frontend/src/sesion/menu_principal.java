package sesion;
import java.util.Scanner;
import usuario.usuario;

public class menu_principal{
    Scanner sc = new Scanner(System.in);
    menu_usuario menu_usuario = new menu_usuario();
    menu_categoria menu_categoria = new menu_categoria();
    menu_subcategoria menu_subcategoria = new menu_subcategoria();
    menu_presupuesto menu_presupuesto = new menu_presupuesto();
    menu_presupuesto_detalle menu_presupuesto_detalle = new menu_presupuesto_detalle();
    menu_obligacion_fija menu_obligacion_fija = new menu_obligacion_fija();
    menu_transaccion menu_transaccion = new menu_transaccion();
    menu_procedimientos_logicos menu_procedimientos_logicos = new menu_procedimientos_logicos();
    menu_funciones menu_funciones = new menu_funciones();

    public void mostrar(usuario usuario_actual){
        if(usuario_actual.is_es_admin()){
            menu_admin(usuario_actual);
        }else{
            menu_usuario_normal(usuario_actual);
        }
    }

    public void menu_admin(usuario usuario_actual){
        int opcion;
        do{
            System.out.println("\n------------------------------------------");
            System.out.println("----------- MENU PRINCIPAL ADMIN ---------");
            System.out.println("------------------------------------------");
            System.out.println("Bienvenido: " + usuario_actual.get_primer_nombre() + " " + usuario_actual.get_primer_apellido());
            System.out.println("1. Usuario");
            System.out.println("2. Categoria");
            System.out.println("3. Subcategoria");
            System.out.println("4. Presupuesto");
            System.out.println("5. PresupuestoDetalle");
            System.out.println("6. Obligaciones");
            System.out.println("7. Transacciones");
            System.out.println("8. Procedimientos");
            System.out.println("9. Funciones");
            System.out.println("10.Reportes");
            System.out.println("0. Cerrar Sesion");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    menu_usuario.mostrar_menu_admin(usuario_actual);
                    break;
                case 2:
                    menu_categoria.mostrar_menu_admin(usuario_actual);
                    break;
                case 3:
                    menu_subcategoria.mostrar_menu_admin();
                    break;
                case 4:
                    menu_presupuesto.mostrar_menu_admin(usuario_actual);
                    break;
                case 5:
                    menu_presupuesto_detalle.mostrar_menu_admin(usuario_actual);
                    break;
                case 6:
                    menu_obligacion_fija.mostrar_menu_admin(usuario_actual);
                    break;
                case 7:
                    menu_transaccion.mostrar_menu_admin(usuario_actual);
                    break;
                case 8:
                    menu_procedimientos_logicos.mostrar_menu(usuario_actual);
                    break;
                case 9:
                     menu_funciones.mostrar_menu(usuario_actual);
                    break;
                case 10:
                    menu_reportes();
                    break;
                case 0:
                    System.out.println("Se ha cerrado sesion");
                    break;
                default:
                    System.out.println("Lo siento, opcion invalida");
                    break;
            }
        }while(opcion != 0);
    }

    public void menu_usuario_normal(usuario usuario_actual){
        int opcion;
        do{
            System.out.println("\n------------------------------------------");
            System.out.println("---------- MENU PRINCIPAL USUARIO --------");
            System.out.println("------------------------------------------");
            System.out.println("Bienvenido: " + usuario_actual.get_primer_nombre() + " " + usuario_actual.get_primer_apellido());

            System.out.println("1. Usuario");
            System.out.println("2. Presupuesto");
            System.out.println("3. PresupuestoDetalle");
            System.out.println("4. Obligaciones");
            System.out.println("5. Transacciones");
            System.out.println("6. Reportes");
            System.out.println("0. Cerrar Sesion");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    menu_usuario.mostrar_menu_usuario_normal(usuario_actual);
                    break;
                case 2:
                     menu_presupuesto.mostrar_menu_usuario(usuario_actual);
                    break;
                case 3:
                    menu_presupuesto_detalle.mostrar_menu_usuario(usuario_actual);
                    break;
                case 4:
                    menu_obligacion_fija.mostrar_menu_usuario(usuario_actual);
                    break;
                case 5:
                    menu_transaccion.mostrar_menu_usuario(usuario_actual);
                    break;
                case 6:
                    menu_reportes();
                    break;
                case 0:
                    System.out.println("Se ha cerrado sesion");
                    break;
                default:
                    System.out.println("Lo siento, opcion invalida");
                    break;
            }
        }while(opcion != 0);
    }

    public void menu_reportes(){
        System.out.println("\n------------------------------------------");
        System.out.println("---------------- REPORTES ----------------");
        System.out.println("------------------------------------------");
        System.out.println("Modulo reportes pendiente...");
    }
}