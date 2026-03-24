package sesion;
import java.util.Scanner;
import usuario.usuario;
import usuario.usuario_db;

public class menu_inicial{
    Scanner sc = new Scanner(System.in);
    sesion sesion_actual = new sesion();
    usuario_db usuario_db = new usuario_db();
    menu_principal menu_principal = new menu_principal();

    public void mostrar(){
        int opcion;
        do{
            System.out.println("\n------------------------------------------");
            System.out.println("------ SISTEMA PRESUPUESTO PERSONAL ------");
            System.out.println("------------------------------------------");
            System.out.println("1. Iniciar Sesion");
            System.out.println("2. Registrarse");
            System.out.println("0. Salir");
            System.out.print("Por favor, seleccione una opcion: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    iniciar_sesion();
                    break;
                case 2:
                    registrarse();
                    break;
                case 0:
                    System.out.println("Sistema Cerrado");
                    break;
                default:
                    System.out.println("Lo siento, opcion invalida");
                    break;
            }
        }while(opcion != 0);
    }

    public void registrarse(){
        System.out.println("\n------------------------------------------");
        System.out.println("--------------- REGISTRARSE --------------");
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
            System.out.println("El usuario fue registrado correctamente");
            usuario usuario_logueado = usuario_db.iniciar_sesion(u.get_correo_electronico(), u.get_clave());

            if(usuario_logueado != null){
                sesion_actual.set_usuario_actual(usuario_logueado);
                menu_principal.mostrar(sesion_actual.get_usuario_actual());
            }
        }
    }

    public void iniciar_sesion(){
        System.out.println("\n------------------------------------------");
        System.out.println("------------- INICIAR SESION -------------");
        System.out.println("------------------------------------------");

        System.out.print("correo electronico: ");
        String correo_electronico = sc.nextLine();

        System.out.print("clave: ");
        String clave = sc.nextLine();

        usuario usuario_logueado = usuario_db.iniciar_sesion(correo_electronico, clave);

        if(usuario_logueado != null){
            sesion_actual.set_usuario_actual(usuario_logueado);
            System.out.println("Se inicio sesion correctamente");
            menu_principal.mostrar(sesion_actual.get_usuario_actual());
        }else{
            System.out.println("Lo siento, no se pudo iniciar sesion");
        }
    }
}