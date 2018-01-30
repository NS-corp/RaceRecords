package com.company;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;


public class XmlHandler {

    public static final String XML_FILE_TYPE = ".xml";

    public static boolean saveXmlFile(XmlTableModel xmlTableModel, String fileNameSave){
        if (fileNameSave == null)
            return false;

        fileNameSave = MyForm.checkType(fileNameSave, XML_FILE_TYPE);

        Document doc;
        try {
            doc = getNewDocument();
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

        // Создаём корневой элемент и добавляем его в документ
        Node root = doc.createElement(xmlTableModel.getXmlParams().getRootElementName());
        doc.appendChild(root);
        for (int rowInd = 0; rowInd < xmlTableModel.getRowCount(); rowInd++){
            // Создаём элемент строки таблицы
            Element element = doc.createElement(xmlTableModel.getXmlParams().getTableElementName());
            root.appendChild(element);

            // Получаем аттрибуты модели таблицы
            String[] attributes = xmlTableModel.getXmlParams().getAttributes();

            // Добавляем аттрибуты для элемента
            for(int columnInd = 0; columnInd < xmlTableModel.getColumnCount(); columnInd++){
                element.setAttribute(attributes[columnInd], (String) xmlTableModel.getValueAt(rowInd, columnInd));
            }
        }

        try {
            // Создание преобразования документа
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(fileNameSave)));
            Main.log.info("Файл сохранен в XML");
        } // Ошибка создания XML преобразователя
        catch (TransformerConfigurationException e){
            e.printStackTrace();
            Main.log.error("Ошибка сохранения файла");
            return false;
        } //Ошибка работы XML преобразователя
        catch (TransformerException e){
            e.printStackTrace();
            Main.log.error("Ошибка сохранения файла");
            return false;
        } //Ошибка ввода-вывода
        catch (IOException e){
            e.printStackTrace();
            Main.log.error("Ошибка открытия файла");
            return false;
        }

        return true;

    }

    public static XmlTableModel openXmlFile(String fileNameOpen, XmlTableModel tableModel){
        if (fileNameOpen == null)
            return null;

        tableModel.setRowCount(0);

        try {
            Document doc = getDocumentByName(fileNameOpen);
            doc.getDocumentElement().normalize();
            //Получение списка элементов
            NodeList elements = doc.getElementsByTagName(tableModel.getXmlParams().getTableElementName());
            if(elements == null || elements.getLength() == 0)
                return tableModel;

            for (int t = 0; t < elements.getLength(); t++){
                Node element = elements.item(t);
                NamedNodeMap attrs = element.getAttributes();
                String[] attrNames = tableModel.getXmlParams().getAttributes();
                Vector<String> rowData = new Vector<>();

                // Находим все колонки по их именам
                Arrays.stream(attrNames).forEach(attrName -> {
                    Node namedItem = attrs.getNamedItem(attrName);
                    rowData.add(namedItem != null ? namedItem.getNodeValue() : ""); // Проверяем на существование элемента
                });

                // Добавляем строку в таблицу
                tableModel.addRow(rowData);
            }
            Main.log.info("Файл открыт");
            //Ошибка при чтении файла
        } catch (SAXException e) {
            e.printStackTrace();
            Main.log.error("Ошибка открытия файла");
            return null;
            //Ошибка ввода-вывода
        } catch (Exception e){
            e.printStackTrace();
            Main.log.error("Ошибка открытия файла");
            return null;
        }


        return tableModel;
    }

    private static Document getNewDocument() throws Exception{
        try {
            // Получаем парсер, порождающий дерево объектов XML - документов
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance(); //Новый экземпляр
            // Создание пустого элемента
            DocumentBuilder builder = f.newDocumentBuilder();
            // Разбирает (получает) данные по пути
            return builder.newDocument();
        } catch (Exception exception){
            throw new Exception("XML parcing error!");
        }
    }

    private static Document getDocumentByName(String fileName) throws Exception{
        try{
            // Получаем парсер, порождающий дерево объектов XML - документов
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance(); //Новый экземпляр
            //Создаем пустой документ
            DocumentBuilder builder = f.newDocumentBuilder();
            return builder.parse(new File(fileName));
        } catch (Exception exception){
            throw  new Exception("XML parcing error!");
        }
    }
}
