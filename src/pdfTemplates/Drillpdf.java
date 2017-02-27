/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfTemplates;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controller.Frame;
import controller.textPdf;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import main.Main;
import model.jugada;

/**
 *
 * @author david
 */
public class Drillpdf {
    
    String[] dataLocale = Main.getInstance().getLocale();
    Locale language = new Locale(dataLocale[1]);
    String path = dataLocale[0];
    ResourceBundle messages = ResourceBundle.getBundle(path, language);
    
    public void PrintPressed(jugada selectedPlay, File fileSavePath )
            throws DocumentException, IOException {
      
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(fileSavePath.getPath()));
        // step 3

        document.open();
        // step 4
        document.add(new Paragraph(messages.getString("Name")+ ":  " + selectedPlay.getNombreJugada()));
        document.add(new Paragraph(messages.getString("Type") + ":  " + selectedPlay.getTipoJugada()));
        document.add(new Paragraph(messages.getString("Subtype")+": " + selectedPlay.getSubtipoJugada()));

        document.add(new Paragraph("  "));

        int contador = 1;
        while (contador < selectedPlay.getContadorJugada() + 1) {

            document.add(new Paragraph(messages.getString("Diagram")+" " + contador));
            document.add(new Paragraph(" "));

            Image image2 = Image.getInstance(selectedPlay.getNombreJugada() + "-" + selectedPlay.getTipoJugada() + "-" + selectedPlay.getSubtipoJugada() + "_" + contador);
            image2.scalePercent(50);

            PdfPTable table = new PdfPTable(2);

            table.addCell(image2);
            table.addCell(textPdf.textPdf(contador,selectedPlay.getDescripcionJugada(),selectedPlay.getContadorJugada()));
            document.add(table);

            contador++;

            
        }

        document.close();

    }
    
    
}
