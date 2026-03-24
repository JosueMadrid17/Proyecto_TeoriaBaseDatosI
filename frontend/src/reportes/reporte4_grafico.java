package reportes;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class reporte4_grafico {
    public JFreeChart crear_grafico(List<reporte4> lista){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(reporte4 r : lista){
            String periodo = String.format("%02d/%d", r.get_mes(), r.get_anio());

            dataset.addValue(
                r.get_monto_gastado(),
                r.get_nombre_categoria(),
                periodo
            );
        }

        JFreeChart chart = ChartFactory.createLineChart(
            "Tendencia de Gastos por Categoria en el Tiempo",
            "Mes / Anio",
            "Monto Gastado",
            dataset
        );
        return chart;
    }
}