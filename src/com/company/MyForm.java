package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Pattern;

public class MyForm extends JFrame {

    // Адреса путей к файлам
    public static final String resourcesPath = "resources";
    private final String openFileIconPath = resourcesPath + "\\op.png";
    private final String saveFileIconPath = resourcesPath + "\\sav.png";
    private final String addIconPath = resourcesPath + "\\pl.png";
    private final String deleteIconPath = resourcesPath + "\\del.png";
    private final String saveFilePDFIconPath = resourcesPath + "\\36.png";
    private final String saveFileHtmlIconPath = resourcesPath + "\\html.png";
    private final String saveFileAllIconPath = resourcesPath + "\\47.png";

    // Racers model
    private final Object[] racersTableHeaders = new Object[]{"Гонщик", "Команда", "Количество очков"};
    private final Object[][] racersTableCells = new Object[10][3];
    private final String[] racersXmlAttributes = new String[]{"Name", "Team", "Points"};
    private final XmlTableModel.XmlParams racersXmlParams = new XmlTableModel.XmlParams("RacerList",
            "RacerElement", racersXmlAttributes);
    private XmlTableModel racersTableModel = new XmlTableModel(racersTableCells, racersTableHeaders, racersXmlParams);

    // Route model
    private final Object[] routeTableHeaders = new Object[]{"Трассы", "Призёр", "Команда призёра"};
    private final Object[][] routeTableCells = new Object[10][3];
    private final String[] routeXmlAttributes = new String[]{"Route", "Name", "Team"};
    private final XmlTableModel.XmlParams routeXmlParams = new XmlTableModel.XmlParams("RouteList",
            "RouteElement", routeXmlAttributes);
    private XmlTableModel routeTableModel = new XmlTableModel(routeTableCells, routeTableHeaders, routeXmlParams);

    // Race model
    private final Object[] raceTableHeaders = new Object[]{"Трассы", "Протяженность трассы", "Дата заезда"};
    private final Object[][] raceTableCells = new Object[10][3];
    private final String[] raceXmlAttributes = new String[]{"Route", "Distance", "Date"};
    private final XmlTableModel.XmlParams raceXmlParams = new XmlTableModel.XmlParams("RaceList",
            "RaceElement", raceXmlAttributes);
    private XmlTableModel raceTableModel = new XmlTableModel(raceTableCells, raceTableHeaders, raceXmlParams);

    XmlTableModel currentModel;

    JMenuBar menuBar;

    JButton buttonSave, buttonOpen, buttonAdd, buttonDelete;
    JMenuItem racersItem, routeItem, raceItem, exitItem;

    JButton buttonSavePDF, buttonSaveHtml, buttonSaveAll;

    JTable table;

    iHandler ihandler = new iHandler();

    public MyForm () {

        super("Соревнования автогонщиков");
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание таблицы
        table = new JTable();
        table.setAutoCreateRowSorter(true);

        this.add(new JScrollPane(table));

        // racersTable.addMouseListener(mouse);

        // создание панели инструментов JToolBar
        JToolBar toolBar = new JToolBar("Панель инструментов");
        toolBar.setFloatable(false); // запрет передвижения панели инструментов.
        add(toolBar, BorderLayout.NORTH); // добавили панель инструментов в главное окно программы.

        // Создание кнопок и иконок:
        buttonOpen = new JButton(new ImageIcon(openFileIconPath));
        buttonSave = new JButton(new ImageIcon(saveFileIconPath));
        buttonAdd = new JButton(new ImageIcon(addIconPath));
        buttonDelete = new JButton(new ImageIcon(deleteIconPath));
        buttonSaveHtml = new JButton(new ImageIcon(saveFileHtmlIconPath));
        buttonSavePDF = new JButton(new ImageIcon(saveFilePDFIconPath));
        buttonSaveAll = new JButton(new ImageIcon(saveFileAllIconPath));

        // Создание подсказок для кнопок:
        buttonOpen.setToolTipText("Открыть список");
        buttonSave.setToolTipText("Сохранить список");
        buttonAdd.setToolTipText("Добавить изменения");
        buttonDelete.setToolTipText("Удалить строку");
        buttonSaveHtml.setToolTipText("Сохранить в html");
        buttonSavePDF.setToolTipText("Сохранить в PDF");
        buttonSaveAll.setToolTipText("Сохранить во всех форматах");

        // Установка размеров для кнопок панели инструментов:
        buttonOpen.setPreferredSize(new Dimension(40, 30));
        buttonSave.setPreferredSize(new Dimension(45, 30));
        buttonAdd.setPreferredSize(new Dimension(40, 30));
        buttonDelete.setPreferredSize(new Dimension(40, 30));
        buttonSaveHtml.setPreferredSize(new Dimension(40, 30));
        buttonSavePDF.setPreferredSize(new Dimension(40, 30));
        buttonSaveAll.setPreferredSize(new Dimension(40, 30));

        // Добавление кнопок на панель инструментов.
        toolBar.add(buttonOpen);
        toolBar.add(buttonSave);
        toolBar.add(buttonAdd);
        toolBar.add(buttonDelete);
        toolBar.add(buttonSaveHtml);
        toolBar.add(buttonSavePDF);
        toolBar.add(buttonSaveAll);

        // Создание полоски меню
        menuBar = new JMenuBar();

        // Создание меню
        menuBar.add(getFileMenu());
        menuBar.add(getXmlMenu());
        // Добавление полоски меню в Frame
        menuBar.add(Box.createHorizontalGlue());
        setJMenuBar(menuBar);

        // Добавление обработчиков на кнопки.
        buttonAdd.addActionListener(ihandler);
        buttonDelete.addActionListener(ihandler);
        exitItem.addActionListener(ihandler);
        racersItem.addActionListener(ihandler);
        routeItem.addActionListener(ihandler);
        raceItem.addActionListener(ihandler);
        buttonOpen.addActionListener(ihandler);
        buttonSave.addActionListener(ihandler);
        buttonSaveHtml.addActionListener(ihandler);
        buttonSavePDF.addActionListener(ihandler);
        buttonSaveAll.addActionListener(ihandler);
    }

    private JMenu getFileMenu() {
        // Создание меню и разделов.
        JMenu fileMenu;
        fileMenu = new JMenu("Меню");
        racersItem = new JMenuItem("Открыть список гонщиков");
        routeItem = new JMenuItem("Открыть список трасс");
        raceItem = new JMenuItem("Открыть список соревнований");
        exitItem = new JMenuItem("Выйти");

        // Добавление элементов в меню.
        fileMenu.add(racersItem);
        fileMenu.add(routeItem);
        fileMenu.add(raceItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        return fileMenu;
    }

    private JMenu getXmlMenu() {
        // Создание меню и разделов.
        JMenu xmlMenu;
        xmlMenu = new JMenu("XML");
        JMenuItem openXmlItem = new JMenuItem("Открыть XML файл");
        openXmlItem.addActionListener(l -> {
            if(currentModel == null){
                return;
            }

            cleanUpTable();
            openFileXML();
        });

        JMenuItem saveXmlItem = new JMenuItem("Сохранить в XML файл");
        saveXmlItem.addActionListener(l -> {
            if(currentModel == null){
                return;
            }

            cleanUpTable();
            saveFileXML();
        });
        // Добавление элементов в меню.
        xmlMenu.add(openXmlItem);
        xmlMenu.add(saveXmlItem);

        return xmlMenu;
    }

    private void createRacersTable () {
        table.setModel(racersTableModel);
        currentModel = racersTableModel;
        Main.log.info("Запущена таблица гонщиков");
    }

    private void createRouteTable () {
        table.setModel(routeTableModel);
        currentModel = routeTableModel;
        Main.log.info("Запущена таблица трасс");
    }

    private void createRaceTable () {
        table.setModel(raceTableModel);
        currentModel = raceTableModel;
        Main.log.info("Запущена таблица гонок");
    }

    private void cleanUpTable() {
        for (int rowInd = 0; rowInd < currentModel.getRowCount();) {
            boolean isRowEmpty = true;
            for (int columnInd = 0; columnInd < currentModel.getColumnCount(); columnInd++)  // Для всех столбцов
            {
                String value = (String) currentModel.getValueAt(rowInd, columnInd);
                if (value == null || value.equals("")) {
                    currentModel.setValueAt("", rowInd, columnInd);
                } else {
                    isRowEmpty = false;
                }
            }

            if (isRowEmpty) {
                currentModel.removeRow(rowInd);
            } else {
                rowInd++;
            }
        }
    }

    public static String checkType(String fileName, String fileType){
        if(!fileName.endsWith(fileType)){
            fileName += fileType;
        }
        return fileName;
    }

    private void openFileTXT(){
        FileDialog openDialog = new FileDialog(this, "Открыть файл", FileDialog.LOAD);
        String fileName = getFileDialogResult(openDialog, TxtHandler.TEXT_FILE_TYPE);

        currentModel = TxtHandler.openFileTXT(table, currentModel, fileName);
    }

    private void saveFileTXT(){
        FileDialog saveDialog = new FileDialog(this, "Сохранить файл", FileDialog.SAVE);
        String fileName = getFileDialogResult(saveDialog, TxtHandler.TEXT_FILE_TYPE);

        TxtHandler.saveFileTXT(currentModel, fileName);
    }

    private void saveFileXML(){
        FileDialog savXML = new FileDialog(this, "Сохранить в XML файл", FileDialog.SAVE);
        //Определяем имя начального каталога или файла
        String fileNameSave = MyForm.getFileDialogResult(savXML, XmlHandler.XML_FILE_TYPE);

        XmlHandler.saveXmlFile(currentModel, fileNameSave);
    }

    private void openFileXML(){
        FileDialog openXML = new FileDialog(this, "Открыть XML файл", FileDialog.LOAD);
        //Определение имени каталога или файла
        String fileNameOpen = MyForm.getFileDialogResult(openXML, XmlHandler.XML_FILE_TYPE);
        XmlHandler.openXmlFile(fileNameOpen, currentModel);
    }

    private void saveFilePDF(){
        FileDialog saveDialog = new FileDialog(this, "Сохранить файл", FileDialog.SAVE);
        String fileName = getFileDialogResult(saveDialog, PdfSaver.PDF_FILE_TYPE);

        // Если ничего не было выбрано
        if(fileName == null || fileName.equals(""))
            return;

        PdfSaver.savePdfFile(currentModel, fileName);
    }

    private void saveFileHtml() {
        FileDialog saveDialog = new FileDialog(this, "Сохранить файл", FileDialog.SAVE);
        String fileName = getFileDialogResult(saveDialog, HTMLSaver.HTML_FILE_TYPE);
        // Если ничего не было выбрано
        if(fileName == null || fileName.equals(""))
            return;
        HTMLSaver.saveHtmlFile(currentModel, fileName);
    }

    private void saveFileAll(){
        FileDialog saveDialog = new FileDialog(this, "Сохранить файл", FileDialog.SAVE);
        saveDialog.setVisible(true);

        String fileName;
        if(saveDialog.getDirectory() == null || saveDialog.getFile() == null)
            return;

        fileName = saveDialog.getDirectory() + saveDialog.getFile().replace(".", "");

        Thread pdfThread = new Thread(() -> PdfSaver.savePdfFile(currentModel, fileName));
        pdfThread.setName("PDF Thread");
        pdfThread.start();

        Thread xmlThread = new Thread(() -> XmlHandler.saveXmlFile(currentModel, fileName));
        xmlThread.setName("XML Thread");
        xmlThread.start();

        Thread htmlThread = new Thread(() -> HTMLSaver.saveHtmlFile(currentModel, fileName));
        htmlThread.setName("HTML Thread");
        htmlThread.start();

    }

    public static String getFileDialogResult(FileDialog dialog, String fileType){
        dialog.setFile(fileType);
        dialog.setVisible(true);

        if(dialog.getDirectory() == null || dialog.getFile() == null)
            return  null;
        else
            return dialog.getDirectory() + dialog.getFile();
    }

    // Обработчики событий.
    public class iHandler implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if(e.getSource() == buttonOpen && currentModel != null){

                openFileTXT();
            }
            if(e.getSource() == buttonSave && currentModel != null){
                cleanUpTable();
                saveFileTXT();
            }
            if (e.getSource() == buttonAdd) {
                currentModel.addRow(new Object[] {null, null, null});
            }
            if (e.getSource() == buttonDelete) {
                int selectedRow = table.getSelectedRow();
                currentModel.removeRow(selectedRow);
            }
            if (e.getSource() == exitItem) {
                dispose();
                Main.log.info("Конец работы");
            }
            if (e.getSource() == racersItem) {
                createRacersTable();
            }
            if(e.getSource() == routeItem){
                createRouteTable();
            }
            if(e.getSource() == raceItem){
                createRaceTable();
            }
            if(e.getSource() == buttonSavePDF){
                cleanUpTable();
                saveFilePDF();
            }
            if (e.getSource() == buttonSaveHtml){
                cleanUpTable();
                saveFileHtml();
            }
            if(e.getSource() == buttonSaveAll){
                cleanUpTable();
                saveFileAll();
            }
        }
    }


}
