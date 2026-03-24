package sesion;
import java.util.List;
import java.util.Scanner;
import reportes.reporte1;
import reportes.reporte1_db;
import reportes.reporte1_pdf;
import reportes.reporte2;
import reportes.reporte2_db;
import reportes.reporte2_pdf;
import reportes.reporte3;
import reportes.reporte3_db;
import reportes.reporte3_pdf;
import usuario.usuario;

public class menu_reportes {
    Scanner sc = new Scanner(System.in);
    reporte1_db reporte1_db = new reporte1_db();
    reporte1_pdf reporte1_pdf = new reporte1_pdf();
    reporte2_db reporte2_db = new reporte2_db();
    reporte2_pdf reporte2_pdf = new reporte2_pdf();
    reporte3_db reporte3_db = new reporte3_db();
    reporte3_pdf reporte3_pdf = new reporte3_pdf();

    public void mostrar_menu(usuario usuario_actual){
        int opcion;
        do{
            System.out.println("\n------------------------------------------");
            System.out.println("------------- MENU REPORTES --------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Reporte 1: Resumen mensual");
            System.out.println("2. Reporte 2: Distribucion de gastos por categoria");
            System.out.println("3. Reporte 3: Cumplimiento de presupuesto por categoria y subcategoria");
            System.out.println("0. Volver");
            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    generar_reporte1(usuario_actual);
                    break;
                case 2:
                    generar_reporte2(usuario_actual);
                    break;
                case 3:
                    generar_reporte3(usuario_actual);
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

    public void generar_reporte1(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------ REPORTE 1 RESUMEN MENSUAL ---------");
        System.out.println("------------------------------------------");

        System.out.print("anio inicio: ");
        int anio_inicio = Integer.parseInt(sc.nextLine());

        System.out.print("mes inicio: ");
        int mes_inicio = Integer.parseInt(sc.nextLine());

        System.out.print("anio fin: ");
        int anio_fin = Integer.parseInt(sc.nextLine());

        System.out.print("mes fin: ");
        int mes_fin = Integer.parseInt(sc.nextLine());

        List<reporte1> lista = reporte1_db.obtener_reporte1(
            usuario_actual.get_id_usuario(),
            anio_inicio,
            mes_inicio,
            anio_fin,
            mes_fin
        );

        if(lista == null || lista.isEmpty()){
            System.out.println("Lo siento, no hay datos para generar el reporte");
            return;
        }

        String ruta_archivo = "reporte1.pdf";

        reporte1_pdf.generar_pdf(
            ruta_archivo,
            lista,
            usuario_actual
        );
    }
    
    
    public void generar_reporte2(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("---- REPORTE 2 DISTRIBUCION DE GASTOS ----");
        System.out.println("------------------------------------------");

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        List<reporte2> lista = reporte2_db.obtener_reporte2(
            usuario_actual.get_id_usuario(),
            anio,
            mes
        );

        if(lista == null || lista.isEmpty()){
            System.out.println("Lo siento, no hay datos para generar el reporte");
            return;
        }

        String ruta_archivo = "reporte2.pdf";

        reporte2_pdf.generar_pdf(
            ruta_archivo,
            lista,
            usuario_actual,
            anio,
            mes
        );
    }
    
    
    public void generar_reporte3(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("--- REPORTE 3 CUMPLIMIENTO PRESUPUESTO ---");
        System.out.println("------------------------------------------");

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        System.out.print("tipo categoria (ingreso/gasto/ahorro): ");
        String tipo_categoria = sc.nextLine();

        List<reporte3> lista = reporte3_db.obtener_reporte3(
            usuario_actual.get_id_usuario(),
            anio,
            mes,
            tipo_categoria
        );

        if(lista == null || lista.isEmpty()){
            System.out.println("Lo siento, no hay datos para generar el reporte");
            return;
        }

        String ruta_archivo = "reporte3.pdf";

        reporte3_pdf.generar_pdf(
            ruta_archivo,
            lista,
            usuario_actual,
            anio,
            mes,
            tipo_categoria
        );
    }
}