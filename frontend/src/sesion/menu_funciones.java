package sesion;
import java.util.Scanner;
import funciones.funciones_db;
import usuario.usuario;

public class menu_funciones{
    Scanner sc = new Scanner(System.in);
    funciones_db funciones_db = new funciones_db();

    public void mostrar_menu(usuario usuario_actual){
        int opcion;

        do{
            System.out.println("\n------------------------------------------");
            System.out.println("------------- MENU FUNCIONES -------------");
            System.out.println("------------------------------------------");
            System.out.println("1. Calcular monto ejecutado");
            System.out.println("2. Calcular porcentaje ejecutado");
            System.out.println("3. Obtener balance de subcategoria");
            System.out.println("4. Obtener total categoria del mes");
            System.out.println("5. Obtener total ejecutado categoria del mes");
            System.out.println("6. Dias hasta vencimiento");
            System.out.println("7. Validar vigencia presupuesto");
            System.out.println("8. Obtener categoria por subcategoria");
            System.out.println("9. Calcular proyeccion gasto mensual");
            System.out.println("10. Obtener promedio gasto subcategoria");
            System.out.println("0. Volver");

            System.out.print("Por favor, seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch(opcion){
                case 1:
                    calcular_monto_ejecutado(usuario_actual);
                    break;
                case 2:
                    calcular_porcentaje_ejecutado(usuario_actual);
                    break;
                case 3:
                    obtener_balance_subcategoria(usuario_actual);
                    break;
                case 4:
                    obtener_total_categoria_mes(usuario_actual);
                    break;
                case 5:
                    obtener_total_ejecutado_categoria_mes(usuario_actual);
                    break;
                case 6:
                    dias_hasta_vencimiento(usuario_actual);
                    break;
                case 7:
                    validar_vigencia_presupuesto(usuario_actual);
                    break;
                case 8:
                    obtener_categoria_por_subcategoria(usuario_actual);
                    break;
                case 9:
                    calcular_proyeccion_gasto_mensual(usuario_actual);
                    break;
                case 10:
                    obtener_promedio_gasto_subcategoria(usuario_actual);
                    break;
                case 0:
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Lo siento, opcion invalida");
                    break;
            }
        }while(opcion != 0);
    }

    public void calcular_monto_ejecutado(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("-------- CALCULAR MONTO EJECUTADO --------");
        System.out.println("------------------------------------------");

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        Double monto = funciones_db.calcular_monto_ejecutado(
            id_subcategoria,
            anio,
            mes
        );

        if(monto != null){
            System.out.println("Monto ejecutado: " + monto);
        }
    }
    
    
    public void calcular_porcentaje_ejecutado(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("----- CALCULAR PORCENTAJE EJECUTADO ------");
        System.out.println("------------------------------------------");

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        Double porcentaje = funciones_db.calcular_porcentaje_ejecutado(
            id_subcategoria,
            id_presupuesto,
            anio,
            mes
        );

        if(porcentaje != null){
            System.out.println("Porcentaje ejecutado: " + porcentaje);
        }
    }
    
    
    public void obtener_balance_subcategoria(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------ OBTENER BALANCE SUBCATEGORIA ------");
        System.out.println("------------------------------------------");

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        Double balance = funciones_db.obtener_balance_subcategoria(
            id_presupuesto,
            id_subcategoria,
            anio,
            mes
        );

        if(balance != null){
            System.out.println("Balance subcategoria: " + balance);
        }
    }
    
    
    public void obtener_total_categoria_mes(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("----- OBTENER TOTAL CATEGORIA DEL MES ----");
        System.out.println("------------------------------------------");

        System.out.print("id categoria: ");
        int id_categoria = Integer.parseInt(sc.nextLine());

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        Double total = funciones_db.obtener_total_categoria_mes(
            id_categoria,
            id_presupuesto,
            anio,
            mes
        );

        if(total != null){
            System.out.println("Total categoria: " + total);
        }
    }
    
    
    public void obtener_total_ejecutado_categoria_mes(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("-- OBTENER TOTAL EJECUTADO CATEGORIA MES -");
        System.out.println("------------------------------------------");

        System.out.print("id categoria: ");
        int id_categoria = Integer.parseInt(sc.nextLine());

        System.out.print("anio: ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes: ");
        int mes = Integer.parseInt(sc.nextLine());

        Double total = funciones_db.obtener_total_ejecutado_categoria_mes(
            id_categoria,
            anio,
            mes
        );

        if(total != null){
            System.out.println("Total ejecutado categoria: " + total);
        }
    }
    
    
    public void dias_hasta_vencimiento(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------- DIAS HASTA VENCIMIENTO -----------");
        System.out.println("------------------------------------------");

        System.out.print("id obligacion: ");
        int id_obligacion = Integer.parseInt(sc.nextLine());

        Integer dias = funciones_db.dias_hasta_vencimiento(id_obligacion);

        if(dias != null){
            System.out.println("Dias restantes: " + dias);

            if(dias < 0){
                System.out.println("Obligacion vencida");
            }else if(dias <= 3){
                System.out.println("Obligacion por vencerse");
            }else{
                System.out.println("Estas a tiempo aun");
            }
        }
    }
    
    
    public void validar_vigencia_presupuesto(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------ VALIDAR VIGENCIA PRESUPUESTO ------");
        System.out.println("------------------------------------------");

        System.out.print("fecha (yyyy-mm-dd): ");
        String fecha = sc.nextLine();

        System.out.print("id presupuesto: ");
        int id_presupuesto = Integer.parseInt(sc.nextLine());

        Integer vigencia = funciones_db.validar_vigencia_presupuesto(
            fecha,
            id_presupuesto
        );

        if(vigencia != null){
            System.out.println("Vigencia presupuesto: " + vigencia);

            if(vigencia == 1){
                System.out.println("Estado: Vigente para esa fecha");
            }else{
                System.out.println("Estado: No vigente para esa fecha");
            }
        }
    }
    
    
    public void obtener_categoria_por_subcategoria(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("--- OBTENER CATEGORIA POR SUBCATEGORIA ---");
        System.out.println("------------------------------------------");

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        Integer id_categoria = funciones_db.obtener_categoria_por_subcategoria(id_subcategoria);

        if(id_categoria != null){
            System.out.println("id categoria: " + id_categoria);
        }
    }
    
    
    public void calcular_proyeccion_gasto_mensual(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("------ PROYECCION DE GASTO MENSUAL -------");
        System.out.println("------------------------------------------");

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("anio (actual): ");
        int anio = Integer.parseInt(sc.nextLine());

        System.out.print("mes (actual): ");
        int mes = Integer.parseInt(sc.nextLine());

        Double proyeccion = funciones_db.calcular_proyeccion_gasto_mensual(
            id_subcategoria,
            anio,
            mes
        );

        if(proyeccion != null){
            System.out.println("Proyeccion gasto mensual: " + proyeccion);
        }
    }
    
    
    public void obtener_promedio_gasto_subcategoria(usuario usuario_actual){
        System.out.println("\n------------------------------------------");
        System.out.println("- OBTENER PROMEDIO GASTO SUBCATEGORIA ----");
        System.out.println("------------------------------------------");

        System.out.print("id usuario: ");
        int id_usuario = Integer.parseInt(sc.nextLine());

        System.out.print("id subcategoria: ");
        int id_subcategoria = Integer.parseInt(sc.nextLine());

        System.out.print("cantidad meses: ");
        int cantidad_meses = Integer.parseInt(sc.nextLine());

        Double promedio = funciones_db.obtener_promedio_gasto_subcategoria(
            id_usuario,
            id_subcategoria,
            cantidad_meses
        );

        if(promedio != null){
            System.out.println("Promedio gasto subcategoria: " + promedio);
        }
    }
}