package com.company;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfSaver {
    final static String FONT_PATH = "\\C:\\Универ\\ООП\\Лабы\\Лабы 2-10\\resources\\Шрифты\\arial.ttf";
    public static void savePdfFile(DefaultTableModel tableModel){
        Document document = new Document(PageSize.A4, 50, 50 ,50 ,50);

        PdfPTable t= new PdfPTable(4);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("\\C:\\Универ\\ООП\\Лабы\\Лабы 2-10\\resources\\RaceInfo.pdf"));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (DocumentException e){
            e.printStackTrace();
        }

        BaseFont bfComic = null;
        try {
            bfComic = BaseFont.createFont("\\C:\\Универ\\ООП\\Лабы\\Лабы 2-10\\resources\\Шрифты\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException el){
            el.printStackTrace();
        } catch (IOException el){
            el.printStackTrace();
        }

        Font font1= new Font(bfComic, 12);

        t.addCell(new PdfPCell(new Phrase("Гонщик",font1)));
        t.addCell(new PdfPCell(new Phrase("Команда",font1)));
        t.addCell(new PdfPCell(new Phrase("Призёр на трассе",font1)));
        t.addCell(new PdfPCell(new Phrase("Количество очков",font1)));

        for (int i=0; i< tableModel.getRowCount(); i++){
            t.addCell(new Phrase((String) tableModel.getValueAt(i,0), font1));
            t.addCell(new Phrase((String) tableModel.getValueAt(i,1), font1));
            t.addCell(new Phrase((String) tableModel.getValueAt(i,2), font1));
            t.addCell(new Phrase((String) tableModel.getValueAt(i,3), font1));
        }

        document.open();

        try {
            document.add(t);
        } catch (DocumentException e){
            e.printStackTrace();
        }

        document.close();
    }
}
