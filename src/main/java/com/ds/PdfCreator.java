package com.ds;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class PdfCreator {

    public void createTemplate(String text, String documentName){
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(documentName));
            document.open();
            document.add(new Paragraph(text));
            document.setPageSize(PageSize.A4);
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(String template, String text, String documentName){
        try{
            PdfReader pdfReader = new PdfReader(template);
            int n = pdfReader.getNumberOfPages();
            Rectangle psize = pdfReader.getPageSize(1);

            Document document = new Document(psize);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(documentName));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            int i = 0;
            int p = 0;
            while (i < n){
                document.newPage();
                p++;
                i++;

                PdfImportedPage page1 = writer.getImportedPage(pdfReader, i);
                AffineTransform affineTransform = new AffineTransform(.5f, 0, 0, .5f, 60, 120);
                cb.addTemplate(page1, .5f, 0, 0, .5f, 60, 120);
                if(i < n){
                    i++;
                    PdfImportedPage page2 = writer.getImportedPage(pdfReader, i);
                    AffineTransform affineTransformP2 =
                            new AffineTransform(.5f, 0, 0, .5f, psize.getWidth() / 2 + 60, 120);
                    cb.addTemplate(page2, .5f, 0, 0, .5f, psize.getWidth() / 2 + 60, 120);
                }
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                cb.beginText();
                cb.setFontAndSize(bf, 14);
                cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "page" + p + "of" +((n / 2) + (n % 2 > 0 ? 1: 0)), psize.getWidth() / 2, 40, 0);
                cb.endText();
            }
            document.close();
            
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
