package com.company;

import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HtmlSave {
    public static void saveHtmlFile (DefaultTableModel RacersModel){

            PrintWriter pwRacers = null;
            try {
                pwRacers = new PrintWriter(new FileWriter("C:\\Users\\Александр\\Desktop\\RaceRecords\\resources\\RacersInfo.html"));
            } catch (IOException e){
                e.printStackTrace();
            }
            pwRacers.println("<TABLE BORDER><TR><TH>Гонщик<TH>Команда<TH>Количество очков</TR>");
            for (int i=0;i<RacersModel.getRowCount();i++){
                int sq = i*i;
                pwRacers.println("<TR><TD>"+ (String) RacersModel.getValueAt(i,0)+"<TD>"+ (String) RacersModel.getValueAt(i,1)+"<TD>"+ (String) RacersModel.getValueAt(i,2)+"<TD>"+ (String) RacersModel.getValueAt(i,3));
            }
            pwRacers.println("</TABLE>");
            pwRacers.close();

    }
}
