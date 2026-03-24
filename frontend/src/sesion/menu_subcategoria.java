package sesion;

import java.util.ArrayList;
import java.util.Scanner;
import subcategoria.subcategoria;
import subcategoria.subcategoria_db;

public class menu_subcategoria{
    Scanner sc = new Scanner(System.in);
    subcategoria_db subcategoria_db = new subcategoria_db();

    public void mostrar_menu_admin(){
        int opcion;
        do{
            System.out.println("\n------------------------------------------");
            System.out.println("------------- SUBCATEGORIA ---------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar subcategoria");
            System.out.println("2. Modificar subcategoria");
            System.out.println("3. Eliminar subcategoria");
            System.out.println("4. Consultar subcategoria");
            System.out.println("5. Listar subcategorias por categoria");
            System.out.println("0. Volver");

            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_subcategoria();
                    break;
                case 2:
                    modificar_subcategoria();
                    break;
                case 3:
                    eliminar_subcategoria();
                    break;
                case 4:
                    consultar_subcategoria();
                    break;
                case 5:
                    listar_subcategorias_por_categoria();
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

    public void insertar_subcategoria(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- INSERTAR SUBCATEGORIA --------");
        System.out.println("------------------------------------------");

        subcategoria s = new subcategoria();

        System.out.print("id categoria: ");
        s.set_id_categoria(Integer.parseInt(sc.nextLine()));

        System.out.print("nombre subcategoria: ");
        s.set_nombre_subcategoria(sc.nextLine());

        System.out.print("descripcion: ");
        s.set_descripcion(sc.nextLine());

        int opcion_defecto;
        do{
            System.out.println("Es por defecto?");
            System.out.println("1. si");
            System.out.println("0. no");
            System.out.print("Opcion: ");
            opcion_defecto = Integer.parseInt(sc.nextLine());

            switch(opcion_defecto){
                case 1:
                    s.set_es_por_defecto(true);
                    break;
                case 0:
                    s.set_es_por_defecto(false);
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }

        }while(opcion_defecto != 0 && opcion_defecto != 1);

        System.out.print("creado por: ");
        s.set_creado_por(sc.nextLine());

        boolean insertado = subcategoria_db.insertar_subcategoria(s);

        if(insertado){
            System.out.println("La subcategoria fue insertada correctamente");
        }
    }

    public void modificar_subcategoria(){
        System.out.println("\n------------------------------------------");
        System.out.println("---------- MODIFICAR SUBCATEGORIA --------");
        System.out.println("------------------------------------------");

        subcategoria s = new subcategoria();

        System.out.print("id subcategoria: ");
        s.set_id_subcategoria(Integer.parseInt(sc.nextLine()));

        System.out.print("nombre subcategoria: ");
        s.set_nombre_subcategoria(sc.nextLine());

        System.out.print("descripcion: ");
        s.set_descripcion(sc.nextLine());

        System.out.print("modificado por: ");
        s.set_modificado_por(sc.nextLine());

        boolean modificado = subcategoria_db.modificar_subcategoria(s);

        if(modificado){
            System.out.println("La subcategoria fue modificada correctamente");
        }
    }

    public void eliminar_subcategoria(){
        System.out.println("\n------------------------------------------");
        System.out.println("---------- ELIMINAR SUBCATEGORIA ---------");
        System.out.println("------------------------------------------");

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        boolean eliminado = subcategoria_db.eliminar_subcategoria(id_subcategoria);

        if(eliminado){
            System.out.println("La subcategoria fue eliminada correctamente");
        }
    }

    public void consultar_subcategoria(){
        System.out.println("\n------------------------------------------");
        System.out.println("--------- CONSULTAR SUBCATEGORIA ---------");
        System.out.println("------------------------------------------");

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        subcategoria s = subcategoria_db.consultar_subcategoria(id_subcategoria);

        if(s != null){
            System.out.println("------------------------------------------");
            mostrar_subcategoria(s);
        }
    }

    public void listar_subcategorias_por_categoria(){
        System.out.println("\n------------------------------------------");
        System.out.println("----- LISTAR SUBCATEGORIAS CATEGORIA -----");
        System.out.println("------------------------------------------");

        System.out.print("id categoria: ");
        int id_categoria = Integer.parseInt(sc.nextLine());

        ArrayList<subcategoria> lista = subcategoria_db.listar_subcategorias_por_categoria(id_categoria);

        if(lista.size() == 0){
            System.out.println("Lo siento, no hay subcategorias para esa categoria");
        }else{
            for(subcategoria s : lista){
                System.out.println("------------------------------------------");
                mostrar_subcategoria(s);
            }
        }
    }

    public void mostrar_subcategoria(subcategoria s){
        System.out.println("id subcategoria: " + s.get_id_subcategoria());
        System.out.println("id categoria: " + s.get_id_categoria());
        System.out.println("nombre categoria: " + s.get_nombre_categoria());
        System.out.println("tipo categoria: " + s.get_tipo_categoria());
        System.out.println("nombre subcategoria: " + s.get_nombre_subcategoria());
        System.out.println("descripcion: " + s.get_descripcion());
        System.out.println("activo: " + s.is_activo());
        System.out.println("es por defecto: " + s.is_es_por_defecto());
        System.out.println("creado por: " + s.get_creado_por());
        System.out.println("modificado por: " + s.get_modificado_por());
    }
}