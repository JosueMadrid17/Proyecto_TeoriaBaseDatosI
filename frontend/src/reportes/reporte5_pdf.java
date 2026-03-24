package reportes;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import javax.imageio.ImageIO;
import org.jfree.chart.JFreeChart;
import usuario.usuario;

public class reporte5_pdf {
    public void generar_pdf(
        String ruta_archivo,
        List<reporte5> lista,
        usuario usuario_actual,
        int anio,
        int mes,
        String estado
    ){
        try{
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(ruta_archivo));
            documento.open();

            documento.add(new Paragraph("REPORTE 5: ESTADO DE OBLIGACIONES FIJAS Y CUMPLIMIENTO DE PAGOS"));
            documento.add(new Paragraph("Usuario: " + usuario_actual.get_primer_nombre() + " " + usuario_actual.get_primer_apellido()));
            documento.add(new Paragraph("Periodo: " + String.format("%02d/%d", mes, anio)));
            documento.add(new Paragraph("Filtro estado: " + estado));
            documento.add(new Paragraph(" "));

            reporte5_grafico grafico_helper = new reporte5_grafico();
            JFreeChart chart = grafico_helper.crear_grafico(lista);

            BufferedImage bufferedImage = chart.createBufferedImage(500, 300);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);

            Image imagen = Image.getInstance(baos.toByteArray());
            imagen.scaleToFit(500, 300);
            documento.add(imagen);

            documento.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);

            tabla.addCell("Obligacion");
            tabla.addCell("Categoria");
            tabla.addCell("Monto mensual");
            tabla.addCell("Fecha vencimiento");
            tabla.addCell("Estado");
            tabla.addCell("Dias");
            tabla.addCell("Ultimo pago");

            for(reporte5 r : lista){
                tabla.addCell(r.get_nombre_obligacion());
                tabla.addCell(r.get_nombre_categoria());
                tabla.addCell(r.get_monto_fijo_mensual().toString());
                tabla.addCell(r.get_fecha_vencimiento_mes().toString());

                PdfPCell celdaEstado = new PdfPCell();
                celdaEstado.setPhrase(new com.lowagie.text.Phrase(r.get_estado_pago()));

                if(r.get_estado_pago().equalsIgnoreCase("PAGADA")){
                    celdaEstado.setBackgroundColor(Color.GREEN);
                }else if(r.get_estado_pago().equalsIgnoreCase("PENDIENTE")){
                    celdaEstado.setBackgroundColor(Color.YELLOW);
                }else if(r.get_estado_pago().equalsIgnoreCase("POR VENCER")){
                    celdaEstado.setBackgroundColor(Color.ORANGE);
                }else if(r.get_estado_pago().equalsIgnoreCase("VENCIDA")){
                    celdaEstado.setBackgroundColor(Color.RED);
                }

                tabla.addCell(celdaEstado);

                String textoDias = "";
                if(r.get_estado_pago().equalsIgnoreCase("PAGADA")){
                    textoDias = "0";
                }else if(r.get_estado_pago().equalsIgnoreCase("VENCIDA")){
                    textoDias = r.get_dias_referencia() + " dias de retraso";
                }else{
                    textoDias = r.get_dias_referencia() + " dias para vencer";
                }

                tabla.addCell(textoDias);

                if(r.get_fecha_ultimo_pago() != null){
                    tabla.addCell(r.get_fecha_ultimo_pago().toString());
                }else{
                    tabla.addCell("Sin pago");
                }
            }

            documento.add(tabla);
            documento.close();

            System.out.println("Reporte PDF generado correctamente en: " + ruta_archivo);
        }catch(Exception e){
            System.out.println("Lo siento, ocurrio un error al generar el PDF del reporte 5: " + e.getMessage());
        }
    }
}