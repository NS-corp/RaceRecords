package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyForm extends JFrame {

    //final String resourcesPath = "C:\\Programming\\MyProjects\\Сашин курсач\\project_1\\resources";
    final String resourcesPath = "C:\\Users\\Александр\\Desktop\\RaceRecords\\resources";
    final String openFileIconPath = resourcesPath + "\\op.png";
    final String saveFileIconPath = resourcesPath + "\\sav.png";
    final String addIconPath = resourcesPath + "\\pl.png";
    final String deleteIconPath = resourcesPath + "\\del.png";

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

    JButton button_save, button_open, button_add, button_delete;
    JMenuBar menuBar;
    JMenu filemenu;
    JMenuItem RacersItem, RouteItem, RaceItem, ExitItem;

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
        button_open = new JButton(new ImageIcon(openFileIconPath));
        button_save = new JButton(new ImageIcon(saveFileIconPath));
        button_add = new JButton(new ImageIcon(addIconPath));
        button_delete = new JButton(new ImageIcon(deleteIconPath));

        // Создание подсказок для кнопок:
        button_open.setToolTipText("Открыть список");
        button_save.setToolTipText("Сохранить список");
        button_add.setToolTipText("Добавить изменения");
        button_delete.setToolTipText("Удалить строку");

        // Установка размеров для кнопок панели инструментов:
        button_open.setPreferredSize(new Dimension(40, 30));
        button_save.setPreferredSize(new Dimension(45, 30));
        button_add.setPreferredSize(new Dimension(40, 30));
        button_delete.setPreferredSize(new Dimension(40, 30));

        // Добавление кнопок на панель инструментов.
        toolBar.add(button_open);
        toolBar.add(button_save);
        toolBar.add(button_add);
        toolBar.add(button_delete);

        // Создание меню.
        menuBar = new JMenuBar();
        filemenu = new JMenu("Меню");
        RacersItem = new JMenuItem("Открыть список гонщиков");
        RouteItem = new JMenuItem("Открыть список трасс");
        RaceItem = new JMenuItem("Открыть список соревнований");
        ExitItem = new JMenuItem("Выйти");
        Menu();

        // Добавление обработчиков на кнопки.
        button_add.addActionListener(ihandler);
        button_delete.addActionListener(ihandler);
        ExitItem.addActionListener(ihandler);
        RacersItem.addActionListener(ihandler);
        RouteItem.addActionListener(ihandler);
        RaceItem.addActionListener(ihandler);
        //button_open.addActionListener(ihandler);
        //button_save.addActionListener(ihandler);

    }

    private void Menu() {
        // Добавление элементов в меню.
        filemenu.add(RacersItem);
        filemenu.add(RouteItem);
        filemenu.add(RaceItem);
        filemenu.addSeparator();
        filemenu.add(ExitItem);

        // Установка меню.
        menuBar.add(filemenu);
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

    // Обработчики событий.
    public class iHandler implements ActionListener {
        public void actionPerformed (ActionEvent e) {

            if (e.getSource() == button_add) {
                currentModel.addRow(new Object[] {null, null, null});
            }
            if (e.getSource() == button_delete) {
                int selectedRow = table.getSelectedRow();
                currentModel.removeRow(selectedRow);
            }
            if (e.getSource() == ExitItem) {
                dispose();
            }
            if (e.getSource() == RacersItem) {
                createRacersTable();
            }
            if(e.getSource() == RouteItem){
                createRouteTable();
            }
            if(e.getSource() == RaceItem){
                createRaceTable();
            }
        }
    }


}
