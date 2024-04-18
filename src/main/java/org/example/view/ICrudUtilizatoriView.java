package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public interface ICrudUtilizatoriView {
    JPanel getPanel();
    String getNume();
    String getEmail();
    String getParola();
    String getTipUtilizator();
    void setNume(String nume);
    void setEmail(String email);
    void setParola(String parola);
    void setTipUtilizator(String tipUtilizator);
    void setTable(DefaultTableModel model);
    JTable getTable();
}
