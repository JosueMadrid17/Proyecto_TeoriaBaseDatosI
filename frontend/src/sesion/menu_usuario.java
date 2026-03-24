package sesion;
import java.util.ArrayList;
import java.util.Scanner;
import usuario.usuario;
import usuario.usuario_db;

public class menu_usuario{
    Scanner sc = new Scanner(System.in);
    usuario_db usuario_db = new usuario_db();

    public void mostrar_menu_admin(usuario usuario_actual){
        int opcion;

        do{
            System.out.println("\n------------------------------------------");
            System.out.println("------------ USUARIO ADMIN ---------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Insertar usuario");
            System.out.println("2. Modificar usuario");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Consultar usuario");
            System.out.println("5. Listar usuarios");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    insertar_usuario();
                    break;
                case 2:
                    modificar_usuario();
                    break;
                case 3:
                    eliminar_usuario();
                    break;
                case 4:
                    consultar_usuario();
                    break;
                case 5:
                    listar_usuarios();
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

    public void mostrar_menu_usuario_normal(usuario usuario_actual){
        int opcion;

        do{
            System.out.println("\n------------------------------------------");
            System.out.println("-------------- MI USUARIO ----------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Gestionar mi usuario");
            System.out.println("2. Modificar mi usuario");
            System.out.println("3. Eliminar mi usuario");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    gestionar_mi_usuario(usuario_actual);
                    break;
                case 2:
                    modificar_mi_usuario(usuario_actual);
                    break;
                case 3:
                    eliminar_mi_usuario(usuario_actual);
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

    public void insertar_usuario(){
        System.out.println("\n------------------------------------------");
        System.out.println("------------- INSERTAR USUARIO -----------");
        System.out.println("------------------------------------------");

        usuario u = new usuario();

        System.out.print("primer nombre: ");
        u.set_primer_nombre(sc.nextLine());

        System.out.print("segundo nombre: ");
        u.set_segundo_nombre(sc.nextLine());

        System.out.print("primer apellido: ");
        u.set_primer_apellido(sc.nextLine());

        System.out.print("segundo apellido: ");
        u.set_segundo_apellido(sc.nextLine());

        System.out.print("correo electronico: ");
        u.set_correo_electronico(sc.nextLine());

        System.out.print("clave: ");
        u.set_clave(sc.nextLine());

        System.out.print("salario mensual: ");
        u.set_salario_mensual(Double.parseDouble(sc.nextLine()));

        System.out.print("creado por: ");
        u.set_creado_por(sc.nextLine());

        boolean insertado = usuario_db.insertar_usuario(u);

        if(insertado){
            System.out.println("El usuario fue insertado correctamente");
        }
    }

    public void modificar_usuario(){
        System.out.println("\n------------------------------------------");
        System.out.println("------------- MODIFICAR USUARIO ----------");
        System.out.println("------------------------------------------");

        usuario u = new usuario();

        System.out.print("id usuario: ");
        u.set_id_usuario(Integer.parseInt(sc.nextLine()));

        System.out.print("primer nombre: ");
        u.set_primer_nombre(sc.nextLine());

        System.out.print("segundo nombre: ");
        u.set_segundo_nombre(sc.nextLine());

        System.out.print("primer apellido: ");
        u.set_primer_apellido(sc.nextLine());

        System.out.print("segundo apellido: ");
        u.set_segundo_apellido(sc.nextLine());

        System.out.print("clave: ");
        u.set_clave(sc.nextLine());

        System.out.print("salario mensual: ");
        u.set_salario_mensual(Double.parseDouble(sc.nextLine()));

        System.out.print("modificado por: ");
        u.set_modificado_por(sc.nextLine());

        boolean modificado = usuario_db.modificar_usuario(u);

        if(modificado){
            System.out.println("El usuario fue modificado correctamente");
        }
    }

    public void eliminar_usuario(){
        System.out.println("\n------------------------------------------");
        System.out.println("------------- ELIMINAR USUARIO -----------");
        System.out.println("------------------------------------------");

        System.out.print("id usuario: ");
        int id_usuario = Integer.parseInt(sc.nextLine());

        boolean eliminado = usuario_db.eliminar_usuario(id_usuario);

        if(eliminado){
            System.out.println("El usuario fue eliminado correctamente");
        }
    }

    public void consultar_usuario(){
        System.out.println("\n------------------------------------------");
        System.out.println("------------ CONSULTAR USUARIO -----------");
        System.out.println("------------------------------------------");

        System.out.print("id usuario: ");
        int id_usuario = Integer.parseInt(sc.nextLine());

        usuario u = usuario_db.consultar_usuario(id_usuario);

        if(u != null){
            System.out.println("------------------------------------------");
            mostrar_datos_usuario(u);
        }
    }

    public void listar_usuarios(){
        System.out.println("\n------------------------------------------");
        System.out.println("-------------- LISTAR USUARIOS -----------");
        System.out.println("------------------------------------------");

        ArrayList<usuario> lista_usuarios = usuario_db.listar_usuarios();

        if(lista_usuarios.size() == 0){
            System.out.println("Lo siento, no hay usuarios para mostrar");
        }else{
            for(usuario u : lista_usuarios){
                System.out.println("------------------------------------------");
                mostrar_datos_usuario(u);
            }
        }
    }

    public void gestionar_mi_usuario(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- GESTIONAR MI USUARIO ---------");
        System.out.println("------------------------------------------");

        usuario u = usuario_db.consultar_usuario(usuario_actual.get_id_usuario());

        if(u != null){
            mostrar_datos_usuario(u);
        }
    }

    public void modificar_mi_usuario(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("----------- MODIFICAR MI USUARIO ---------");
        System.out.println("------------------------------------------");

        usuario u = new usuario();

        u.set_id_usuario(usuario_actual.get_id_usuario());

        System.out.print("primer nombre: ");
        u.set_primer_nombre(sc.nextLine());

        System.out.print("segundo nombre: ");
        u.set_segundo_nombre(sc.nextLine());

        System.out.print("primer apellido: ");
        u.set_primer_apellido(sc.nextLine());

        System.out.print("segundo apellido: ");
        u.set_segundo_apellido(sc.nextLine());

        System.out.print("clave: ");
        u.set_clave(sc.nextLine());

        System.out.print("salario mensual: ");
        u.set_salario_mensual(Double.parseDouble(sc.nextLine()));

        System.out.print("modificado por: ");
        u.set_modificado_por(sc.nextLine());

        boolean modificado = usuario_db.modificar_usuario(u);

        if(modificado){
            System.out.println("El usuario fue modificado correctamente");

            usuario usuario_actualizado = usuario_db.consultar_usuario(usuario_actual.get_id_usuario());

            if(usuario_actualizado != null){
                usuario_actual.set_primer_nombre(usuario_actualizado.get_primer_nombre());
                usuario_actual.set_segundo_nombre(usuario_actualizado.get_segundo_nombre());
                usuario_actual.set_primer_apellido(usuario_actualizado.get_primer_apellido());
                usuario_actual.set_segundo_apellido(usuario_actualizado.get_segundo_apellido());
                usuario_actual.set_correo_electronico(usuario_actualizado.get_correo_electronico());
                usuario_actual.set_clave(usuario_actualizado.get_clave());
                usuario_actual.set_salario_mensual(usuario_actualizado.get_salario_mensual());
                usuario_actual.set_estado_usuario(usuario_actualizado.is_estado_usuario());
                usuario_actual.set_creado_por(usuario_actualizado.get_creado_por());
                usuario_actual.set_modificado_por(usuario_actualizado.get_modificado_por());
            }
        }
    }

    public void eliminar_mi_usuario(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------------ ELIMINAR MI USUARIO ---------");
        System.out.println("------------------------------------------");

        System.out.print("Seguro que desea eliminar su usuario? (si/no): ");
        String respuesta = sc.nextLine();

        if(respuesta.equalsIgnoreCase("si")){
            boolean eliminado = usuario_db.eliminar_usuario(usuario_actual.get_id_usuario());

            if(eliminado){
                System.out.println("El usuario fue eliminado correctamente");
            }
        }else{
            System.out.println("Operacion cancelada");
        }
    }

    public void mostrar_datos_usuario(usuario u){
        System.out.println("id usuario: " + u.get_id_usuario());
        System.out.println("primer nombre: " + u.get_primer_nombre());
        System.out.println("segundo nombre: " + u.get_segundo_nombre());
        System.out.println("primer apellido: " + u.get_primer_apellido());
        System.out.println("segundo apellido: " + u.get_segundo_apellido());
        System.out.println("correo electronico: " + u.get_correo_electronico());
        System.out.println("clave: " + u.get_clave());
        System.out.println("salario mensual: " + u.get_salario_mensual());
        System.out.println("estado usuario: " + u.is_estado_usuario());
        System.out.println("creado por: " + u.get_creado_por());
        System.out.println("modificado por: " + u.get_modificado_por());
    }
}