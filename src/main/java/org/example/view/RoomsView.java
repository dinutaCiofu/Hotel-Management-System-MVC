package org.example.view;

import lombok.Getter;
import org.example.controller.ClientRoomsController;
import org.example.controller.EmployeeRoomsController;
import org.example.model.entities.RoomFloor;
import org.example.model.entities.RoomFloorMapper;
import org.example.model.entities.UserType;
import org.example.model.repository.HotelRepository;
import org.example.model.repository.RoomRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RoomsView implements Observer {
    private JPanel mainJPanel;
    private JLabel titleLabel;
    private JButton addRoomButton;
    private JButton updateButton;
    private JComboBox<Object> filterByComboBox;
    private JButton searchButton;
    private JTable table;
    private JLabel searchLabel;
    private JButton deleteButton;
    private JLabel filterByLabel;
    private JComboBox<Object> searchByComboBox;
    private JButton filterBtn;
    private JPanel crudPanel;
    private JTextField numberRoomTextField;
    private JTextField priceTextField;
    private JTextField isAvailableTextField;
    private JTextField floorTextField;
    private JTextField locationTextField;
    private JPanel imagesPanel;
    private JLabel imageLabel;
    private final UserType userLogged;
    private ClientRoomsController clientRoomsController;
    private EmployeeRoomsController employeeRoomsController;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    {
        $$$setupUI$$$();
    }

    private void $$$setupUI$$$() {
        this.addRoomButton = new JButton();
    }

    public RoomsView(UserType userLogged) {
        if (userLogged == UserType.CLIENT) {
            clientRoomsController = new ClientRoomsController(this);
        } else {
            employeeRoomsController = new EmployeeRoomsController(this);
        }
        roomRepository = new RoomRepository();
        hotelRepository = new HotelRepository();
        this.userLogged = userLogged;

        initComponents();
        initObservers();
    }

    private void initComponents(){
        DefaultComboBoxModel<Object> list = new DefaultComboBoxModel<>(generateFilterList().toArray());
        filterByComboBox.setModel(list);
        List<String> searchByList = new ArrayList<>();
        searchByList.add("Location");
        searchByList.add("Number");
        searchByComboBox.setModel(new DefaultComboBoxModel<>(searchByList.toArray()));
        if (this.userLogged == UserType.EMPLOYEE || this.userLogged == UserType.ADMINISTRATOR) {
            addRoomButton.setVisible(true);
            updateButton.setVisible(true);
            deleteButton.setVisible(true);
            crudPanel.setVisible(true);
        } else {
            addRoomButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            crudPanel.setVisible(false);
        }
        this.updateTable(table);
    }
    private void initObservers() {
        roomRepository.readAll().forEach(room -> room.attach(this));
        hotelRepository.readAll().forEach(hotel -> hotel.attach(this));
    }

    public List<String> generateFilterList() {
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

    public void updateTable(JTable table) {
        DefaultTableModel model;
        if (this.userLogged.equals(UserType.CLIENT))   {
            model = clientRoomsController.populateTable();
        } else {
            model = employeeRoomsController.populateTable();
        }
        table.setModel(model);
    }

    public void setTable(DefaultTableModel model) {
        table.setModel(model);
    }

    @Override
    public void update() {
        DefaultTableModel model;
        if (userLogged == UserType.CLIENT) {
            model = clientRoomsController.populateTable();
        } else {
            model = employeeRoomsController.populateTable();
        }
        table.setModel(model);
        table.repaint();
        numberRoomTextField.setText("");
        priceTextField.setText("");
        floorTextField.setText("");
        isAvailableTextField.setText("");
        locationTextField.setText("");
    }
}
