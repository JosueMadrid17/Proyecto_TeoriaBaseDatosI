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

public class reporte1_pdf {

    public void generar_pdf(
        String ruta_archivo,
        List<reporte1> lista,
        usuario usuario_actual
    ){
        try{
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(ruta_archivo));
            documento.open();

            documento.add(new Paragraph("REPORTE 1: RESUMEN MENSUAL DE INGRESOS VS GASTOS VS AHORROS"));
            documento.add(new Paragraph("Usuario: " + usuario_actual.get_primer_nombre() + " " + usuario_actual.get_primer_apellido()));
            documento.add(new Paragraph(" "));

            reporte1_grafico grafico_helper = new reporte1_grafico();
            JFreeChart chart = grafico_helper.crear_grafico(lista);

            BufferedImage bufferedImage = chart.createBufferedImage(500, 300);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);

            Image imagen = Image.getInstance(baos.toByteArray());
            imagen.scaleToFit(500, 300);
            documento.add(imagen);

            documento.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);

            tabla.addCell("Anio");
            tabla.addCell("Mes");
            tabla.addCell("Ingresos");
            tabla.addCell("Gastos");
            tabla.addCell("Ahorros");
            tabla.addCell("Balance");

            for(reporte1 r : lista){
                tabla.addCell(String.valueOf(r.get_anio()));
                tabla.addCell(String.format("%02d", r.get_mes()));
                tabla.addCell(r.get_ingresos().toString());
                tabla.addCell(r.get_gastos().toString());
                tabla.addCell(r.get_ahorros().toString());
                tabla.addCell(r.get_balance().toString());
            }

            documento.add(tabla);

            documento.close();

            System.out.println("Reporte PDF generado correctamente en: " + ruta_archivo);

        }catch(Exception e){
            System.out.println("Lo siento, ocurrio un error al generar el PDF del reporte 1: " + e.getMessage());
        }
    }
}