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

    private final String COLUMN_SEPARATOR = " | ";
    private final Pattern separatorPattern = Pattern.compile(" \\| ");

    private final String resourcesPath = "C:\\Programming\\MyProjects\\Сашин курсач\\RaceRecords\\resources";
    //private final String resourcesPath = "C:\\Users\\Александр\\Desktop\\RaceRecords\\resources";
    private final String openFileIconPath = resourcesPath + "\\op.png";
    private final String saveFileIconPath = resourcesPath + "\\sav.png";
    private final String addIconPath = resourcesPath + "\\pl.png";
    private final String deleteIconPath = resourcesPath + "\\del.png";

    // Racers model
    private final Object[] racersTableHeaders = new Object[]{"Гонщик", "Команда", "Количество очков"};
    private final Object[][] racersTableCells = new Object[10][3];
    DefaultTableModel racersTableModel = new DefaultTableModel(racersTableCells, racersTableHeaders);

    // Route model
    private final Object[] routeTableHeaders = new Object[]{"Трассы", "Призёр", "Команда призёра"};
    private final Object[][] routeTableCells = new Object[10][3];
    DefaultTableModel routeTableModel = new DefaultTableModel(routeTableCells, routeTableHeaders);

    // Race model
    private final Object[] raceTableHeaders = new Object[]{"Трассы", "Протяженность трассы", "Дата заезда"};
    private final Object[][] raceTableCells = new Object[10][3];
    DefaultTableModel raceTableModel = new DefaultTableModel(raceTableCells, raceTableHeaders);

    DefaultTableModel currentModel;

    JButton buttonSave, buttonOpen, buttonAdd, buttonDelete;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem racersItem, routeItem, raceItem, exitItem;

    JTable table;

    iHandler ihandler = new iHandler();

    public MyForm () {

        super("Соревнования автогонщиков");
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        // Создание подсказок для кнопок:
        buttonOpen.setToolTipText("Открыть список");
        buttonSave.setToolTipText("Сохранить список");
        buttonAdd.setToolTipText("Добавить изменения");
        buttonDelete.setToolTipText("Удалить строку");

        // Установка размеров для кнопок панели инструментов:
        buttonOpen.setPreferredSize(new Dimension(40, 30));
        buttonSave.setPreferredSize(new Dimension(45, 30));
        buttonAdd.setPreferredSize(new Dimension(40, 30));
        buttonDelete.setPreferredSize(new Dimension(40, 30));

        // Добавление кнопок на панель инструментов.
        toolBar.add(buttonOpen);
        toolBar.add(buttonSave);
        toolBar.add(buttonAdd);
        toolBar.add(buttonDelete);

        // Создание меню.
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Меню");
        racersItem = new JMenuItem("Открыть список гонщиков");
        routeItem = new JMenuItem("Открыть список трасс");
        raceItem = new JMenuItem("Открыть список соревнований");
        exitItem = new JMenuItem("Выйти");
        setUpMenu();

        // Добавление обработчиков на кнопки.
        buttonAdd.addActionListener(ihandler);
        buttonDelete.addActionListener(ihandler);
        exitItem.addActionListener(ihandler);
        racersItem.addActionListener(ihandler);
        routeItem.addActionListener(ihandler);
        raceItem.addActionListener(ihandler);
        buttonOpen.addActionListener(ihandler);
        buttonSave.addActionListener(ihandler);

    }

    private void setUpMenu() {
        // Добавление элементов в меню.
        fileMenu.add(racersItem);
        fileMenu.add(routeItem);
        fileMenu.add(raceItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Установка меню.
        menuBar.add(fileMenu);
        menuBar.add(Box.createHorizontalGlue());
        setJMenuBar(menuBar);
    }

    private void createRacersTable () {
        table.setModel(racersTableModel);
        currentModel = racersTableModel;
    }

    private void createRouteTable () {
        table.setModel(routeTableModel);
        currentModel = routeTableModel;
    }

    private void createRaceTable () {
        table.setModel(raceTableModel);
        currentModel = raceTableModel;
    }

    private void openFile(){
        FileDialog openDialog = new FileDialog(this, "Открыть файл", FileDialog.LOAD);
        String fileName = getFileDialogResult(openDialog);

        // Если ничего не было выбрано
        if(fileName == null || fileName.equals(""))
            return;

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
        }

        // Берём заголовки из предыдущей таблицы
        Vector<String> headers = new Vector<>();
        for(int i = 0; i < table.getColumnCount(); i++){
            headers.add(table.getColumnName(i));
            System.out.println(headers.get(i));
        }

        // Создаём новую таблицу с данными из файла
        currentModel = new DefaultTableModel(tableData, headers);
        table.setModel(currentModel);
    }

    private void saveFile(){
        FileDialog saveDialog = new FileDialog(this, "Сохранить файл", FileDialog.SAVE);
        String fileName = getFileDialogResult(saveDialog);

        // Если ничего не было выбрано
        if(fileName == null || fileName.equals(""))
            return;

        try {
            BufferedWriter writer = new BufferedWriter (new FileWriter(fileName));
            for (int i = 0; i < currentModel.getRowCount(); i++) {
                for (int j = 0; j < currentModel.getColumnCount(); j++)  // Для всех столбцов
                {
                    // Записать значение из ячейки
                    writer.write(currentModel.getValueAt(i, j)
                            // Добавить разделитель, если это не последний столбец
                            + (j < currentModel.getColumnCount()- 1 ? COLUMN_SEPARATOR : ""));
                }
                writer.write("\r\n");
            }
            writer.close();
        }
        // Ошибка записи в файл
        catch(IOException e) {
            e.printStackTrace();
        }


    }

    private String getFileDialogResult(FileDialog dialog){
        dialog.setFile("*.txt");
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
                openFile();
            }
            if(e.getSource() == buttonSave && currentModel != null){
                saveFile();
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
        }
    }


}
