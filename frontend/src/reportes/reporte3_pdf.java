package reportes;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.jfree.chart.JFreeChart;
import usuario.usuario;

public class reporte3_pdf {
    public void generar_pdf(
        String ruta_archivo,
        List<reporte3> lista,
        usuario usuario_actual,
        int anio,
        int mes,
        String tipo_categoria
    ){
        try{
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(ruta_archivo));
            documento.open();

            documento.add(new Paragraph("REPORTE 3: ANALISIS DE CUMPLIMIENTO DE PRESUPUESTO"));
            documento.add(new Paragraph("Usuario: " + usuario_actual.get_primer_nombre() + " " + usuario_actual.get_primer_apellido()));
            documento.add(new Paragraph("Periodo: " + String.format("%02d/%d", mes, anio)));
            documento.add(new Paragraph("Tipo categoria: " + tipo_categoria));
            documento.add(new Paragraph(" "));

            reporte3_grafico grafico_helper = new reporte3_grafico();
            JFreeChart chart = grafico_helper.crear_grafico(lista);

            BufferedImage bufferedImage = chart.createBufferedImage(500, 300);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);

            Image imagen = Image.getInstance(baos.toByteArray());
            imagen.scaleToFit(500, 300);
            documento.add(imagen);

            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("DETALLE POR CATEGORIA Y SUBCATEGORIA"));
            documento.add(new Paragraph(" "));

            Map<String, BigDecimal[]> totalesPorCategoria = new LinkedHashMap<>();

            for(reporte3 r : lista){
                if(!totalesPorCategoria.containsKey(r.get_nombre_categoria())){
                    totalesPorCategoria.put(
                        r.get_nombre_categoria(),
                        new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO}
                    );
                }

                BigDecimal[] totales = totalesPorCategoria.get(r.get_nombre_categoria());
                totales[0] = totales[0].add(r.get_monto_presupuestado());
                totales[1] = totales[1].add(r.get_monto_ejecutado());
            }

            for(String categoria : totalesPorCategoria.keySet()){
                BigDecimal[] totales = totalesPorCategoria.get(categoria);

                documento.add(new Paragraph(
                    "Categoria: " + categoria +
                    " (Total presupuestado: L. " + totales[0].toString() +
                    " / Total ejecutado: L. " + totales[1].toString() + ")"
                ));

                PdfPTable tabla = new PdfPTable(6);
                tabla.setWidthPercentage(100);

                tabla.addCell("Subcategoria");
                tabla.addCell("Presupuestado");
                tabla.addCell("Ejecutado");
                tabla.addCell("Diferencia");
                tabla.addCell("% Ejecucion");
                tabla.addCell("Indicador");

                for(reporte3 r : lista){
                    if(r.get_nombre_categoria().equals(categoria)){
                        tabla.addCell(r.get_nombre_subcategoria());
                        tabla.addCell(r.get_monto_presupuestado().toString());
                        tabla.addCell(r.get_monto_ejecutado().toString());
                        tabla.addCell(r.get_diferencia().toString());
                        tabla.addCell(r.get_porcentaje_ejecucion().toString() + "%");
                        tabla.addCell(r.get_indicador_visual());
                    }
                }
                documento.add(tabla);
                documento.add(new Paragraph(" "));
            }
            documento.close();

            System.out.println("Reporte PDF generado correctamente en: " + ruta_archivo);
        }catch(Exception e){
            System.out.println("Lo siento, ocurrio un error al generar el PDF del reporte 3: " + e.getMessage());
        }
    }
}