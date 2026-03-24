package reportes;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import javax.imageio.ImageIO;
import org.jfree.chart.JFreeChart;
import usuario.usuario;

public class reporte2_pdf {

    public void generar_pdf(
        String ruta_archivo,
        List<reporte2> lista,
        usuario usuario_actual,
        int anio,
        int mes
    ){
        try{
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(ruta_archivo));
            documento.open();

            documento.add(new Paragraph("REPORTE 2: DISTRIBUCION DE GASTOS POR CATEGORIA"));
            documento.add(new Paragraph("Usuario: " + usuario_actual.get_primer_nombre() + " " + usuario_actual.get_primer_apellido()));
            documento.add(new Paragraph("Periodo: " + String.format("%02d/%d", mes, anio)));
            documento.add(new Paragraph(" "));

            reporte2_grafico grafico_helper = new reporte2_grafico();
            JFreeChart chart = grafico_helper.crear_grafico(lista);

            BufferedImage bufferedImage = chart.createBufferedImage(500, 300);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);

            Image imagen = Image.getInstance(baos.toByteArray());
            imagen.scaleToFit(500, 300);
            documento.add(imagen);

            documento.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);

            tabla.addCell("Categoria");
            tabla.addCell("Monto Total Gastado");
            tabla.addCell("Porcentaje");
            tabla.addCell("Cantidad Transacciones");

            for(reporte2 r : lista){
                tabla.addCell(r.get_nombre_categoria());
                tabla.addCell(r.get_monto_total_gastado().toString());
                tabla.addCell(r.get_porcentaje_total_gastos().toString() + "%");
                tabla.addCell(String.valueOf(r.get_cantidad_transacciones()));
            }

            documento.add(tabla);

            documento.close();

            System.out.println("Reporte PDF generado correctamente en: " + ruta_archivo);
        }catch(Exception e){
            System.out.println("Lo siento, ocurrio un error al generar el PDF del reporte 2: " + e.getMessage());
        }
    }
}