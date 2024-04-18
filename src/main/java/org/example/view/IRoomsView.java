package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public interface IRoomsView {
    JPanel getMainPanel();
    String getSelectedSearchByValue();
    String getSelectedFilterByValue();
    void setTable(DefaultTableModel model);
    JPanel getCrudJPanel();
    String getNumarCamera();
    void setNumarCamera(String numarCamera);
    String getPretCamera();
    void setPretCamera(String pret);
    String getDisponibiliate();
    void setDisponibilitate(String disponibilitate);
    String getPozitie();
    void setPozitie(String pozitie);
    String getLocatie();
    void setLocatie(String locatie);
    JTable getTable();
}
