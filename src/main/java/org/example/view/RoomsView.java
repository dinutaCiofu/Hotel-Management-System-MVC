package org.example.view;

import org.example.model.entities.FacilitatiCamera;
import org.example.model.entities.PozitieCamera;
import org.example.model.entities.PozitieCameraMapper;
import org.example.model.entities.TipUtilizator;
import org.example.controller.AngajatRoomsViewController;
import org.example.controller.ClientRoomsViewController;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoomsView implements IRoomsView, IAbstractView {
    private JPanel mainJPanel;
    private JLabel titluLabel;
    private JButton adaugaCameraButton;
    private JButton actualizeazaButton;
    private JComboBox filterByComboBox;
    private JButton searchButton;
    private JTable table;
    private JLabel searchLabel;
    private JButton stergeButton;
    private JLabel filterByLabel;
    private JComboBox searchByComboBox;
    private JButton filterBtn;
    private JPanel crudPanel;
    private JTextField numarCameraTextField;
    private JTextField pretTextField;
    private JTextField disponibilitateTextField;
    private JTextField pozitieTextField;
    private JTextField locatieTextField;
    private TipUtilizator userLogged;
    private ClientRoomsViewController clientRoomsViewController;
    private AngajatRoomsViewController angajatRoomsViewController;

    public RoomsView(TipUtilizator userLogged) {
        filterByComboBox.setModel(new DefaultComboBoxModel(generateFilterList().toArray()));
        clientRoomsViewController = new ClientRoomsViewController(this);
        angajatRoomsViewController = new AngajatRoomsViewController(this);
        List<String> searchByList = new ArrayList<>();
        searchByList.add("Locatie");
        searchByList.add("Numar");
        searchByComboBox.setModel(new DefaultComboBoxModel(searchByList.toArray()));
        this.userLogged = userLogged;
        System.out.println(userLogged);
        if (userLogged == TipUtilizator.ANGAJAT) {
            adaugaCameraButton.setVisible(true);
            actualizeazaButton.setVisible(true);
            stergeButton.setVisible(true);
            crudPanel.setVisible(true);
        } else {
            adaugaCameraButton.setVisible(false);
            actualizeazaButton.setVisible(false);
            stergeButton.setVisible(false);
            crudPanel.setVisible(false);
        }
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientRoomsViewController.populateTableAferSearch();
            }
        });
        filterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientRoomsViewController.populateTableAfterFilter(List.of(FacilitatiCamera.values()));
            }
        });
        adaugaCameraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angajatRoomsViewController.addCamera();
            }
        });
        actualizeazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angajatRoomsViewController.updateCamera();
            }
        });
        stergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angajatRoomsViewController.deleteCamera();
            }
        });
    }

    private List<String> generateFilterList() {
        List<String> filterList = new ArrayList<>();
        filterList.add("Facilitati");
        filterList.add("Disponibilitate");
        filterList.add("Pret");
        // adauga pozitiile camerelor la lista
        List<PozitieCamera> pozitieCameraList = List.of(PozitieCamera.values());
        for (PozitieCamera pozitieCamera : pozitieCameraList) {
            String pozitieCameraString = PozitieCameraMapper.mapToPozitieString(pozitieCamera);
            if (!pozitieCameraString.isEmpty()) {
                filterList.add(pozitieCameraString);
            }
        }
        return filterList;
    }

    @Override
    public JPanel getMainPanel() {
        return this.mainJPanel;
    }

    @Override
    public String getSelectedSearchByValue() {
        return (String) searchByComboBox.getSelectedItem();
    }

    @Override
    public String getSelectedFilterByValue() {
        return (String) filterByComboBox.getSelectedItem();
    }


    @Override
    public void updateTable(JTable table, DefaultTableModel model) {
        model = clientRoomsViewController.populateTableAferSearch();
        table.setModel(model);
    }

    @Override
    public JTable getTable() {
        return table;
    }


    @Override
    public void setTable(DefaultTableModel model) {
        table.setModel(model);
    }

    @Override
    public JPanel getCrudJPanel() {
        return this.crudPanel;
    }

    @Override
    public String getNumarCamera() {
        return this.numarCameraTextField.getText();
    }

    @Override
    public void setNumarCamera(String numarCamera) {
        this.numarCameraTextField.setText(numarCamera);
    }

    @Override
    public String getPretCamera() {
        return this.pretTextField.getText();
    }

    @Override
    public void setPretCamera(String pret) {
        this.pretTextField.setText(pret);
    }

    @Override
    public String getDisponibiliate() {
        return this.disponibilitateTextField.getText();
    }

    @Override
    public void setDisponibilitate(String disponibilitate) {
        this.disponibilitateTextField.setText(disponibilitate);
    }

    @Override
    public String getPozitie() {
        return this.pozitieTextField.getText();
    }

    @Override
    public void setPozitie(String pozitie) {
        this.pozitieTextField.setText(pozitie);
    }

    @Override
    public String getLocatie() {
        return this.locatieTextField.getText();
    }

    @Override
    public void setLocatie(String locatie) {
        this.locatieTextField.setText(locatie);
    }

    /**
     * @noinspection ALL
     */
    private Font getFont(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }


}
