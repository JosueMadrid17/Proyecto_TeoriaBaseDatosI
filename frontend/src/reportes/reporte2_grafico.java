package reportes;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class reporte2_grafico {

    public JFreeChart crear_grafico(List<reporte2> lista){
        DefaultPieDataset dataset = new DefaultPieDataset();

        for(reporte2 r : lista){
            dataset.setValue(
                r.get_nombre_categoria(),
                r.get_monto_total_gastado()
            );
        }

        JFreeChart chart = ChartFactory.createPieChart(
            "Distribucion de Gastos por Categoria",
            dataset,
            true,
            true,
            false
        );

        return chart;
    }
}