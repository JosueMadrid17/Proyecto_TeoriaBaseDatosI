package reportes;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class reporte3_grafico {
    public JFreeChart crear_grafico(List<reporte3> lista){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(reporte3 r : lista){
            String etiqueta = r.get_nombre_subcategoria();

            dataset.addValue(r.get_monto_presupuestado(), "Presupuestado", etiqueta);
            dataset.addValue(r.get_monto_ejecutado(), "Ejecutado", etiqueta);
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Cumplimiento de Presupuesto por Subcategoria",
            "Subcategoria",
            "Monto",
            dataset
        );
        return chart;
    }
}