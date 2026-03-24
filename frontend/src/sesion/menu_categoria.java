package sesion;
import categoria.categoria;
import categoria.categoria_db;
import java.util.ArrayList;
import java.util.Scanner;
import usuario.usuario;

public class menu_categoria{
    Scanner sc = new Scanner(System.in);
    categoria_db categoria_db = new categoria_db();

    public void mostrar_menu_admin(usuario usuario_actual){
        int opcion;

        do{
            System.out.println("\n------------------------------------------");
            System.out.println("--------------- CATEGORIA ----------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar categoria");
            System.out.println("2. Modificar categoria");
            System.out.println("3. Eliminar categoria");
            System.out.println("4. Consultar categoria");
            System.out.println("5. Listar categorias");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_categoria();
                    break;
                case 2:
                    modificar_categoria();
                    break;
                case 3:
                    eliminar_categoria();
                    break;
                case 4:
                    consultar_categoria();
                    break;
                case 5:
                    listar_categorias();
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

    public void insertar_categoria(){
        System.out.println("\n------------------------------------------");
        System.out.println("------------ INSERTAR CATEGORIA ----------");
        System.out.println("------------------------------------------");

        categoria c = new categoria();

        System.out.print("nombre: ");
        c.set_nombre(sc.nextLine());

        System.out.print("descripcion: ");
        c.set_descripcion(sc.nextLine());

        int opcion_tipo;
        do{
            System.out.println("Seleccione tipo de categoria:");
            System.out.println("1. gasto");
            System.out.println("2. ingreso");
            System.out.println("3. ahorro");
            System.out.print("Opcion: ");
            opcion_tipo = Integer.parseInt(sc.nextLine());

            switch(opcion_tipo){
                case 1:
                    c.set_tipo_categoria("gasto");
                    break;
                case 2:
                    c.set_tipo_categoria("ingreso");
                    break;
                case 3:
                    c.set_tipo_categoria("ahorro");
                    break;
                default:
                    System.out.println("Lo siento, opcion invalida");
                    break;
            }
        }while(opcion_tipo < 1 || opcion_tipo > 3);

        System.out.print("creado por: ");
        c.set_creado_por(sc.nextLine());

        boolean insertado = categoria_db.insertar_categoria(c);

        if(insertado){
            System.out.println("La categoria fue insertada correctamente");
        }
    }

    public void modificar_categoria(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- MODIFICAR CATEGORIA ----------");
        System.out.println("------------------------------------------");

        categoria c = new categoria();

        System.out.print("id categoria: ");
        c.set_id_categoria(Integer.parseInt(sc.nextLine()));

        System.out.print("nombre: ");
        c.set_nombre(sc.nextLine());

        System.out.print("descripcion: ");
        c.set_descripcion(sc.nextLine());

        System.out.print("modificado por: ");
        c.set_modificado_por(sc.nextLine());

        boolean modificado = categoria_db.modificar_categoria(c);

        if(modificado){
            System.out.println("La categoria fue modificada correctamente");
        }
    }

    public void eliminar_categoria(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- ELIMINAR CATEGORIA -----------");
        System.out.println("------------------------------------------");

        System.out.print("id categoria: ");
        int id = Integer.parseInt(sc.nextLine());

        boolean eliminado = categoria_db.eliminar_categoria(id);

        if(eliminado){
            System.out.println("La categoria fue eliminada correctamente");
        }
    }

    public void consultar_categoria(){
        System.out.println("\n------------------------------------------");
        System.out.println("---------- CONSULTAR CATEGORIA -----------");
        System.out.println("------------------------------------------");

        System.out.print("id categoria: ");
        int id = Integer.parseInt(sc.nextLine());

        categoria c = categoria_db.consultar_categoria(id);

        if(c != null){
            System.out.println("------------------------------------------");
            mostrar_categoria(c);
        }
    }

   public void listar_categorias(){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- LISTAR CATEGORIAS ------------");
        System.out.println("------------------------------------------");

        System.out.print("id usuario: ");
        int id_usuario = Integer.parseInt(sc.nextLine());

        int opcion_tipo;
        String tipo = null;
        do{
            System.out.println("Seleccione tipo de categoria:");
            System.out.println("1. gasto");
            System.out.println("2. ingreso");
            System.out.println("3. ahorro");
            System.out.println("0. todos");
            System.out.print("Opcion: ");
            opcion_tipo = Integer.parseInt(sc.nextLine());

            switch(opcion_tipo){
                case 1:
                    tipo = "gasto";
                    break;
                case 2:
                    tipo = "ingreso";
                    break;
                case 3:
                    tipo = "ahorro";
                    break;
                case 0:
                    tipo = null;
                    break;
                default:
                    System.out.println("Lo siento, opcion invalida");
                    break;
            }

        }while(opcion_tipo < 0 || opcion_tipo > 3);

        ArrayList<categoria> lista = categoria_db.listar_categorias(
            id_usuario,
            tipo
        );

        if(lista.size() == 0){
            System.out.println("Lo siento, no hay categorias asociadas al usuario con ese tipo");
        }else{
            for(categoria c : lista){
                System.out.println("------------------------------------------");
                mostrar_categoria(c);
            }
        }
    }

    public void mostrar_categoria(categoria c){
        System.out.println("id categoria: " + c.get_id_categoria());
        System.out.println("nombre: " + c.get_nombre());
        System.out.println("descripcion: " + c.get_descripcion());
        System.out.println("tipo: " + c.get_tipo_categoria());
        System.out.println("creado por: " + c.get_creado_por());
        System.out.println("modificado por: " + c.get_modificado_por());
    }
}