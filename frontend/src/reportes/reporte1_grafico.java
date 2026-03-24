package reportes;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class reporte1_grafico {

    public JFreeChart crear_grafico(List<reporte1> lista){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(reporte1 r : lista){
            String periodo = String.format("%02d/%d", r.get_mes(), r.get_anio());

            dataset.addValue(r.get_ingresos(), "Ingresos", periodo);
            dataset.addValue(r.get_gastos(), "Gastos", periodo);
            dataset.addValue(r.get_ahorros(), "Ahorros", periodo);
            dataset.addValue(r.get_balance(), "Balance", periodo);
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Resumen Mensual de Ingresos vs Gastos vs Ahorros",
            "Mes / Año",
            "Monto",
            dataset
        );
        return chart;
    }
}