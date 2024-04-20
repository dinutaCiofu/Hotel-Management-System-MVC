package org.example.view;

import org.example.controller.EmployeeRoomsController;
import org.example.controller.ClientRoomsController;
import org.example.model.entities.RoomFacilities;
import org.example.model.entities.RoomFloor;
import org.example.model.entities.RoomFloorMapper;
import org.example.model.entities.UserType;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoomsView implements Observer {
    private JPanel mainJPanel;
    private JLabel titleLabel;
    private JButton addCameraButton;
    private JButton updateButton;
    private JComboBox filterByComboBox;
    private JButton searchButton;
    private JTable table;
    private JLabel searchLabel;
    private JButton deleteButton;
    private JLabel filterByLabel;
    private JComboBox searchByComboBox;
    private JButton filterBtn;
    private JPanel crudPanel;
    private JTextField numberRoomTextField;
    private JTextField priceTextField;
    private JTextField isAvailableTextField;
    private JTextField floorTextField;
    private JTextField locationTextField;
    private final UserType userLogged;
    private final ClientRoomsController clientRoomsController;
    private final EmployeeRoomsController employeeRoomsController;

    public RoomsView(UserType userLogged) {
        filterByComboBox.setModel(new DefaultComboBoxModel(generateFilterList().toArray()));
        clientRoomsController = new ClientRoomsController(this);
        employeeRoomsController = new EmployeeRoomsController(this);
        List<String> searchByList = new ArrayList<>();
        searchByList.add("Location");
        searchByList.add("Number");
        searchByComboBox.setModel(new DefaultComboBoxModel(searchByList.toArray()));
        this.userLogged = userLogged;
        System.out.println(userLogged);
        if (userLogged == UserType.EMPLOYEE) {
            addCameraButton.setVisible(true);
            updateButton.setVisible(true);
            deleteButton.setVisible(true);
            crudPanel.setVisible(true);
        } else {
            addCameraButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            crudPanel.setVisible(false);
        }
        searchButton.addActionListener(e -> clientRoomsController.populateTableAfterSearch());
        filterBtn.addActionListener(e -> clientRoomsController.populateTableAfterFilter(List.of(RoomFacilities.values())));
        addCameraButton.addActionListener(e -> employeeRoomsController.addCamera());
        updateButton.addActionListener(e -> employeeRoomsController.updateCamera());
        deleteButton.addActionListener(e -> employeeRoomsController.deleteCamera());
    }

    private List<String> generateFilterList() {
        List<String> filterList = new ArrayList<>();
        filterList.add("Facilities");
        filterList.add("Availability");
        filterList.add("Price");
        List<RoomFloor> roomFloorList = List.of(RoomFloor.values());
        for (RoomFloor roomFloor : roomFloorList) {
            String roomFloorString = RoomFloorMapper.mapToFloorString(roomFloor);
            if (!roomFloorString.isEmpty()) {
                filterList.add(roomFloorString);
            }
        }
        return filterList;
    }

    public JPanel getMainPanel() {
        return this.mainJPanel;
    }

    public String getSelectedSearchByValue() {
        return (String) searchByComboBox.getSelectedItem();
    }

    public String getSelectedFilterByValue() {
        return (String) filterByComboBox.getSelectedItem();
    }


    public void updateTable(JTable table, DefaultTableModel model) {
        model = clientRoomsController.populateTableAfterSearch();
        table.setModel(model);
    }

    public JTable getTable() {
        return table;
    }


    public void setTable(DefaultTableModel model) {
        table.setModel(model);
    }

    public JPanel getCrudJPanel() {
        return this.crudPanel;
    }

    public String getRoomNumber() {
        return this.numberRoomTextField.getText();
    }

    public void setRoomNumber(String roomNumber) {
        this.numberRoomTextField.setText(roomNumber);
    }

    public String getPriceRoom() {
        return this.priceTextField.getText();
    }

    public void setPriceRoom(String priceRoom) {
        this.priceTextField.setText(priceRoom);
    }

    public String getAvailability() {
        return this.isAvailableTextField.getText();
    }

    public void setAvailability(String availability) {
        this.isAvailableTextField.setText(availability);
    }

    public String getFloor() {
        return this.floorTextField.getText();
    }

    public void setFloor(String floor) {
        this.floorTextField.setText(floor);
    }

    public String getLocation() {
        return this.locationTextField.getText();
    }

    public void setLocation(String location) {
        this.locationTextField.setText(location);
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


    @Override
    public void update() {

    }
}
