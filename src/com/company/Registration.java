package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Registration extends JFrame {
    Handler handler = new Handler();

    Box box1, box2, box3, mainbox;
    JButton button_ok, button_cancel;
    JTextField FieldLogin;
    JLabel LableLogin, passwLabel;
    JPasswordField passwField;

    public Registration () {
        super ("Вход администратора");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        createGUIRegistration();
        passwField.addActionListener(handler);
        button_ok.addActionListener(handler);
        button_cancel.addActionListener(handler);
    }

    private void createGUIRegistration() {
        // Настройка первой панели:
        box1 = Box.createHorizontalBox();
        LableLogin = new JLabel("Введите логин");
        FieldLogin = new JTextField(25);
        box1.add(LableLogin);
        box1.add(Box.createHorizontalStrut(14));
        box1.add(FieldLogin);

        box2 = Box.createHorizontalBox();
        passwLabel = new JLabel("Введите пароль");
        passwField = new JPasswordField(10);
        box2.add(Box.createHorizontalGlue());
        box2.add(passwLabel);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(passwField);

        // Настройка второй горизонтальной панели:
        box3 = Box.createHorizontalBox();
        button_ok = new JButton("Начать работу");
        button_cancel = new JButton("Отмена");
        box3.add(button_ok);
        box3.add(Box.createHorizontalStrut(100));
        box3.add(button_cancel);

        //Размещаем три горизонтальные панели на одной вертикали
        mainbox = Box.createVerticalBox();
        mainbox.setBorder(new EmptyBorder(20,20,20,20));
        mainbox.add(box1);
        mainbox.add(Box.createVerticalStrut(12));
        mainbox.add(box2);
        mainbox.add(Box.createVerticalStrut(17));
        mainbox.add(box3);
        setContentPane(mainbox);
        pack();
    }

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if ((e.getSource() == button_ok) || (e.getSource() == passwField)){
                try{
                    // Проверка логина:
                    String strlogin;
                    strlogin = FieldLogin.getText();
                    if (strlogin.length() == 0)
                        throw new NullPointerException();

                    // Проверка пароля:
                    String strPassw;
                    strPassw = passwField.getText();
                    if (strPassw.length() == 0)
                        throw new myPasswordException();

                    // Проверка правильности логина и пароля
                    boolean log = false, passw = false, all = false;
                    String[][] str_Yes = OpenLogPasswFile();

                    for (int i= 0; (str_Yes[i][0] != null) && (str_Yes[i][1] != null); i++){
                        if(strlogin.equals(str_Yes[i][0]) && strPassw.equals(str_Yes[i][1])) all = true;
                        if(strlogin.equals(str_Yes[i][0])) log = true;
                        if(strPassw.equals(str_Yes[i][1])) passw = true;
                    }

                    if(!log) throw new myLogException();
                    if(!passw) throw new myPasswordException();

                    if(all){
                        //Запись в файл и запускаем другое окно
                        MyForm myForm = new MyForm();
                        myForm.setVisible(true);
                        myForm.setLocationRelativeTo(null);
                        dispose();
                    }else
                        throw new myPasswordException();

                } catch (NullPointerException ex){
                    JOptionPane.showMessageDialog(null, "<html><p> Введите логин </p>", "Ошибка", JOptionPane.WARNING_MESSAGE );
                } catch (myLogException meEx) {
                    JOptionPane.showMessageDialog(null,meEx.getMessage());
                } catch (myPasswordException meEx) {
                    JOptionPane.showMessageDialog(null,meEx.getMessage());
                }
            }
            if (e.getSource()== button_cancel){
                dispose();
            }
        }
    }
    private String[][] OpenLogPasswFile(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Александр\\Desktop\\RaceRecords\\users\\UserLogPassw.txt"));

            String string;
            String [][] str1 = new String [100][2];

            int i = 0;

            do {
                string = reader.readLine();
                if (string!= null){
                    String [] str = string.split("\\|");
                    if (str.length == 2){
                        str1[i] = str;
                        i++;
                    }
                }
            }while (string != null);
            reader.close();
            return str1;
        } catch (FileNotFoundException e2){
            e2.printStackTrace();
        } catch (IOException e1){
            e1.printStackTrace();
        }
        return null;
    }

    private class myLogException extends Exception{
        public myLogException(){
            super("Неправильный логин!");
        }
    }

    private class myPasswordException extends Exception{
        public myPasswordException(){
            super("Неправильный пароль!");
            passwField.setText("");
        }
    }
}
