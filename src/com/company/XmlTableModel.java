package com.company;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class XmlTableModel extends DefaultTableModel {
    private XmlParams xmlParams;

    /**
     * Параметры необходимые для работы модели в XMl форматах
     */
    public static class XmlParams {
        String rootElementName;
        String tableElementName;
        String[] attributes;

        XmlParams(String rootElementName, String tableElementName, String[] attributes){
            this.rootElementName = rootElementName;
            this.tableElementName = tableElementName;
            this.attributes = attributes;
        }

        public String getRootElementName() {
            return rootElementName;
        }

        public String getTableElementName() {
            return tableElementName;
        }

        public String[] getAttributes() {
            return attributes;
        }
    }

    public XmlTableModel(Vector<? extends Vector> data, Vector<?> columnNames, XmlParams xmlParams) {
        super(data, columnNames);
        this.xmlParams = xmlParams;
    }

    public XmlTableModel(Object[][] data, Object[] columnNames, XmlParams xmlParams) {
        super(data, columnNames);
        this.xmlParams = xmlParams;
    }

    public XmlParams getXmlParams() {
        return xmlParams;
    }

    public void setXmlParams(XmlParams xmlParams) {
        this.xmlParams = xmlParams;
    }
}
