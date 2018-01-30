package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class HTMLSaver {

    public static final String HTML_FILE_TYPE = ".html";

    public static void saveHtmlFile(TableModel tableModel, String fileName){
        // Если ничего не было выбрано
        if(fileName == null || fileName.equals(""))
            return;

        fileName = MyForm.checkType(fileName, HTML_FILE_TYPE);

        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e){
            e.printStackTrace();
            Main.log.error("Ошибка сохранения файла");
            return;
        }

        Vector<?> headers = tableModel.getHeaders();

        printWriter.print("<TABLE BORDER><TR>");
        headers.forEach(header ->  printWriter.print("<TH>" + header));
        printWriter.println("</TR>");

        for (int rowInd = 0; rowInd < tableModel.getRowCount(); rowInd++) {
            printWriter.println("<TR><TD>");
            for (int columnInd = 0; columnInd < tableModel.getColumnCount(); columnInd++) {
                printWriter.println(tableModel.getValueAt(rowInd, columnInd)
                        + (columnInd < tableModel.getColumnCount() - 1 ? "<TD>" : ""));
            }
        }
        printWriter.println("</TABLE>");
        printWriter.close();
        Main.log.info("Файл сохранен в HTML");

    }
}
