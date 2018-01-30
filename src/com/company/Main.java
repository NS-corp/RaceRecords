package com.company;

import org.apache.log4j.Logger;

public class Main {
    public static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        log.info("Старт работы");
        Registration reg = new Registration();
        reg.setVisible(true);
        reg.setLocationRelativeTo(null);

        /*MyForm app = new MyForm();
        app.setVisible(true);*/
    }
}
