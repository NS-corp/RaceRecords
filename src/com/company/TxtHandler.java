package com.company;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Pattern;

public class TxtHandler {

    public static final String COLUMN_SEPARATOR = " | ";
    public static final Pattern separatorPattern = Pattern.compile(" \\| ");

    public static final String TEXT_FILE_TYPE = ".txt";

    public static XmlTableModel openFileTXT(JTable table, XmlTableModel tableModel, String fileName){
        // Если ничего не было выбрано
        if(fileName == null || fileName.equals(""))
            return tableModel;

        // Считываем файл
        Vector<Vector<String>> tableData = new Vector<>(); // Данные для таблицы
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            // Считываем строки из файла пока не дойдем до конца
            String line = reader.readLine();
            while (line != null){
                // Вырезаем все разделители и отделяем каждый столбец
                String[] columns = separatorPattern.split(line);
                tableData.add(new Vector<>(Arrays.asList(columns)));
                line = reader.readLine();
            }
        }
        // Ошибка чтения файла
        catch(IOException e) {
            e.printStackTrace();
            Main.log.error("Ошибка открытия файла");
        }

        // Создаём новую таблицу с данными из файла
        tableModel = new XmlTableModel(tableData, tableModel.getHeaders(), tableModel.getXmlParams());
        table.setModel(tableModel);
        Main.log.info("Файл открыт");
        return tableModel;
    }

    public static void saveFileTXT(XmlTableModel tableModel, String fileName){
        // Если ничего не было выбрано
        if(fileName == null || fileName.equals(""))
            return;

        fileName = MyForm.checkType(fileName, TEXT_FILE_TYPE);

        try {
            BufferedWriter writer = new BufferedWriter (new FileWriter(fileName));
            for (int rowInd = 0; rowInd < tableModel.getRowCount(); rowInd++) {
                StringBuilder line = new StringBuilder();
                for (int columnInd = 0; columnInd < tableModel.getColumnCount(); columnInd++)  // Для всех столбцов
                {
                    String value = (String) tableModel.getValueAt(rowInd, columnInd);
                    if (value == null) {
                        value = "";
                    }
                    // Записать значение из ячейки в линию
                    line.append(value
                            // Добавить разделитель, если это не последний столбец
                            + (columnInd < tableModel.getColumnCount() - 1 ? COLUMN_SEPARATOR : ""));
                }

                writer.write(line.toString() + "\r\n");

            }
            writer.close();
            Main.log.info("Файл сохранен в TXT");
        }
        // Ошибка записи в файл
        catch(IOException e) {
            e.printStackTrace();
            Main.log.error("Ошибка сохранения файла");
        }
    }
}
