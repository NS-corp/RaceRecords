package com.company;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfSaver {
    public static final String PDF_FILE_TYPE = ".pdf";
    final static String FONT_PATH = MyForm.resourcesPath + "\\Шрифты\\arial.ttf";

    public static void savePdfFile(TableModel tableModel, String filePath){
        if(filePath == null || filePath.equals(""))
            return;

        filePath = MyForm.checkType(filePath, PDF_FILE_TYPE);

        Document document = new Document(PageSize.A4, 50, 50 ,50 ,50);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (DocumentException e){
            e.printStackTrace();
        }

        PdfPTable pdfTable = new PdfPTable(tableModel.getColumnCount());

        BaseFont bfComic = null;
        try {
            bfComic = BaseFont.createFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException el){
            el.printStackTrace();
        } catch (IOException el){
            el.printStackTrace();
        }

        Font font1 = new Font(bfComic, 12);

        // Добавляем заголовки
        tableModel.getHeaders().forEach(header -> {
            pdfTable.addCell(new PdfPCell(new Phrase((String) header, font1)));
        });

        for (int rowInd = 0; rowInd < tableModel.getRowCount(); rowInd++){
            for(int columnInd = 0; columnInd < tableModel.getColumnCount(); columnInd++){
                pdfTable.addCell(new Phrase((String) tableModel.getValueAt(rowInd,columnInd), font1));
            }
        }

        document.open();

        try {
            document.add(pdfTable);
        } catch (DocumentException e){
            e.printStackTrace();
        }

        document.close();
    }
}
