package reportes;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class reporte5_grafico {
    public JFreeChart crear_grafico(List<reporte5> lista){
        int pagadas = 0;
        int pendientes = 0;
        int por_vencer = 0;
        int vencidas = 0;

        for(reporte5 r : lista){
            if(r.get_estado_pago().equalsIgnoreCase("PAGADA")){
                pagadas++;
            }else if(r.get_estado_pago().equalsIgnoreCase("PENDIENTE")){
                pendientes++;
            }else if(r.get_estado_pago().equalsIgnoreCase("POR VENCER")){
                por_vencer++;
            }else if(r.get_estado_pago().equalsIgnoreCase("VENCIDA")){
                vencidas++;
            }
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Pagadas", pagadas);
        dataset.setValue("Pendientes", pendientes);
        dataset.setValue("Por vencer", por_vencer);
        dataset.setValue("Vencidas", vencidas);

        JFreeChart chart = ChartFactory.createPieChart(
            "Resumen de Estado de Obligaciones",
            dataset,
            true,
            true,
            false
        );
        return chart;
    }
}