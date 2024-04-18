package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public interface ICrudClienti {
    JPanel getJPanel();
    String getNume();
    String getEmail();
    String getParola();
    void setNume(String nume);
    void setEmail(String email);
    void setParola(String parola);
    void setTable(DefaultTableModel model);
    JTable getTable();
}
