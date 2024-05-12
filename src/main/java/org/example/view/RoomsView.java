package org.example.view;

import lombok.Getter;
import org.example.controller.ClientRoomsController;
import org.example.controller.EmployeeRoomsController;
import org.example.model.entities.RoomFloor;
import org.example.model.entities.RoomFloorMapper;
import org.example.model.entities.UserType;
import org.example.model.repository.HotelRepository;
import org.example.model.repository.ImageRepository;
import org.example.model.repository.RoomRepository;
import org.example.utils.LanguageManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RoomsView extends JPanel implements Observer {
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
    private JScrollPane imageScrollPane;
    private JPanel imagePanel;
    private JButton addImgBtn;
    private JLabel nrRoomLabel;
    private JLabel priceLabel;
    private JLabel availabilityLabel;
    private JLabel positionLabel;
    private JLabel locationLabel;
    private final UserType userLogged;
    private ClientRoomsController clientRoomsController;
    private EmployeeRoomsController employeeRoomsController;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ImageRepository imageRepository;

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
        imageRepository = new ImageRepository();
        this.userLogged = userLogged;

        initComponents();
        initObservers();
        this.titleLabel.setText(LanguageManager.getString("titleLabel"));
        this.filterByLabel.setText(LanguageManager.getString("filterByLabel"));
        this.searchButton.setText(LanguageManager.getString("searchButton"));
        this.searchLabel.setText(LanguageManager.getString("searchLabel"));
        this.filterBtn.setText(LanguageManager.getString("filterBtn"));
        this.addRoomButton.setText(LanguageManager.getString("addRoomButton"));
        this.updateButton.setText(LanguageManager.getString("updateButton"));
        this.deleteButton.setText(LanguageManager.getString("deleteButton"));
        this.addImgBtn.setText(LanguageManager.getString("addImgBtn"));
        this.nrRoomLabel.setText(LanguageManager.getString("numberRoomField"));
        this.priceLabel.setText(LanguageManager.getString("priceField"));
        this.availabilityLabel.setText(LanguageManager.getString("availability"));
        this.locationLabel.setText(LanguageManager.getString("locationField"));
        this.positionLabel.setText(LanguageManager.getString("floorField"));
//        setupImagesPanel();
    }

    private void initComponents(){
        DefaultComboBoxModel<Object> list = new DefaultComboBoxModel<>(generateFilterList().toArray());
        filterByComboBox.setModel(list);
        List<String> searchByList = new ArrayList<>();
        searchByList.add(LanguageManager.getString("numberRoomField"));
        searchByList.add(LanguageManager.getString("locationField"));
        searchByComboBox.setModel(new DefaultComboBoxModel<>(searchByList.toArray()));
        if (this.userLogged == UserType.EMPLOYEE || this.userLogged == UserType.ADMINISTRATOR) {
            addRoomButton.setVisible(true);
            updateButton.setVisible(true);
            deleteButton.setVisible(true);
            crudPanel.setVisible(true);
            addImgBtn.setVisible(true);
        } else {
            addRoomButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            crudPanel.setVisible(false);
            addImgBtn.setVisible(false);
        }
        this.updateTable(table);
    }
    private void initObservers() {
        roomRepository.readAll().forEach(room -> room.attach(this));
        hotelRepository.readAll().forEach(hotel -> hotel.attach(this));
    }

    public List<String> generateFilterList() {
        List<String> filterList = new ArrayList<>();
        filterList.add(LanguageManager.getString("facilities"));
        filterList.add(LanguageManager.getString("availability"));
        filterList.add(LanguageManager.getString("price"));
        List<RoomFloor> roomFloorList = List.of(RoomFloor.values());
        for (RoomFloor roomFloor : roomFloorList) {
            String roomFloorString = RoomFloorMapper.mapToFloorString(roomFloor);
            if (!roomFloorString.isEmpty()) {
                filterList.add(roomFloorString);
            }
        }
        return filterList;
    }

    public void displayRoomImages(Long roomId) {
        List<String> imagePaths = imageRepository.findById(roomId);
        System.out.println("Images" + imagePaths);
//        if (imagePaths != null) {
//            imagePanel.removeAll();
//            for (String path : imagePaths) {
//                System.out.println(path);
//                ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT));
//                JLabel label = new JLabel(icon);
//                imagePanel.add(label);
//            }
//            imagePanel.revalidate();
//            imagePanel.repaint();
//        }
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

//    private void setupImagesPanel() {
//        this.imagePanel = new JPanel(new GridLayout(0, 3, 10, 10));  // Adjust columns and gaps
//        imageScrollPane = new JScrollPane(imagePanel);
//        imageScrollPane.setPreferredSize(new Dimension(600, 200));
//    }



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
