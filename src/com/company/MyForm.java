package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyForm extends JFrame {

    final String resourcesPath = "C:\\Programming\\MyProjects\\Сашин курсач\\RaceRecords\\resources";
    final String openFileIconPath = resourcesPath + "\\op.png";
    final String saveFileIconPath = resourcesPath + "\\sav.png";
    final String addIconPath = resourcesPath + "\\pl.png";
    final String deleteIconPath = resourcesPath + "\\del.png";


    private JPanel MainPanel;

    JButton button_save, button_open, button_add, button_delete;
    JMenuBar menuBar;
    JMenu filemenu;
    JMenuItem RacersItem, RouteItem, RaceItem, ExitItem;

    JTable RacersTable, RouteTable, RaceTable;

    JScrollPane RacersPane; ///////

    JScrollPane scrollPaneRacers, scrollPaneRoute, scrollPaneRace;
    DefaultTableModel RacersModel, RouteModel, RaceModel;

    Object headers[];
    Object data[][];

    Container container;

    iHandler ihandler = new iHandler();

    public MyForm () {

        super("Соревнования автогонщиков");
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание таблицы для гонщиков.
        headers = new Object[]{"Гонщик", "Команда", "Количество очков"};
        data = new Object[10][3];

        // Инициализация контейнеров.
        RacersModel = new DefaultTableModel(data, headers);
        RacersTable = new JTable(RacersModel);
        RacersTable.setAutoCreateRowSorter(true);


        //RacersPane = new JScrollPane(RacersTable);
        //getContentPane().add(RacersPane); //ВЫВОД ТАБЛИЦЫ В ГЛАВНОЕ ОКНО
        getContentPane().add(new JScrollPane(RacersTable));
        RacersTable.setVisible(false);
        RacersTable.getTableHeader().setVisible(false);


        // RacersTable.addMouseListener(mouse);

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

    private void CreateRacersTable () {
        System.out.println("ХУЙ");
        RacersTable.setVisible(true);
        RacersTable.getTableHeader().setVisible(true);
    }

    // Обработчики событий.
    public class iHandler implements ActionListener {
        public void actionPerformed (ActionEvent e) {

            if (e.getSource() == button_add) {
                RacersModel.addRow(new Object[] {null, null, null});
            }
            if (e.getSource() == button_delete) {
                int selectedRow = RacersTable.getSelectedRow();
                RacersModel.removeRow(selectedRow);
            }
            if (e.getSource() == ExitItem) {
                dispose();
            }
            if (e.getSource() == RacersItem) {
                CreateRacersTable();
            }
        }
    }


}
