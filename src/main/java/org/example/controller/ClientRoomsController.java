package org.example.controller;

import org.example.model.entities.*;
import org.example.model.entities.Image;
import org.example.model.repository.HotelRepository;
import org.example.model.repository.ImageRepository;
import org.example.model.repository.RoomRepository;
import org.example.utils.LanguageManager;
import org.example.view.RoomsView;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class ClientRoomsController {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ImageRepository imageRepository;
    private final RoomsView roomsView;

    public ClientRoomsController(RoomsView roomsView) {
        this.roomsView = roomsView;
        this.roomRepository = new RoomRepository();
        this.imageRepository = new ImageRepository();
        this.hotelRepository = new HotelRepository();
        this.roomsView.getFilterBtn().addActionListener(e -> populateTableAfterFilter(List.of(RoomFacilities.AC, RoomFacilities.TV)));
//        this.roomsView.getSearchButton().addActionListener(e -> populateTableAfterSearch());
        this.roomsView.getSearchButton().addActionListener(e -> showImages());
//        this.roomsView.getShowImagesButton().addActionListener(e -> showImages());
    }



    private void showImages() {
        JFrame frame = new JFrame();
        int selectedRow = this.roomsView.getTable().getSelectedRow();
        if (selectedRow != -1){
            Long roomId = (Long) this.roomsView.getTable().getValueAt(selectedRow, 0);
            List<Image> images = imageRepository.findAllImagesByRoomId(roomId);
            JPanel imagePanel = createImagePanel(images);

            JDialog dialog = new JDialog(frame, "Imagini", true);
            dialog.add(imagePanel);
            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
            frame.add(imagePanel);
        }
    }

    private JPanel createImagePanel(List<Image> images) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        for (Image image : images) {
            ImageIcon icon = new ImageIcon(image.getPath());
            JLabel label = new JLabel(icon);
            panel.add(label);
        }
        return panel;
    }

    public DefaultTableModel setTableColumns() {
        DefaultTableModel model = new DefaultTableModel();
        List<String> columns = List.of("ID", "numberRoom", "price", "isAvailable", "floor", "location");
        model.setRowCount(0);
        model = new DefaultTableModel() {
        };

        for (String column : columns) {
            model.addColumn(column);
        }
        return model;
    }

    private String getRoomLocation(Room room) {

        if (room.getLocation() != null) {
            System.out.println("Existing hotel");
            Long hotelId = room.getLocation().getId();
            Hotel hotel = hotelRepository.findById(hotelId);
            if (hotel != null) {
                return hotel.getName();
            }
        } else {
            System.out.println("not found");
            System.out.println(room.getLocation());
        }
        return null;
    }

    public void setTableRows(DefaultTableModel model, List<Room> rooms) {
        for (Room room : rooms) {
            Long id = room.getId();
            String numberRoom = room.getNrRoom();
            String price = String.valueOf(room.getPrice());
            String isAvailable = room.getIsAvailable() ? "Available" : "Unavailable";
            String floor = RoomFloorMapper.mapToFloorString(room.getFloor());
            String location = getRoomLocation(room) != null ? getRoomLocation(room) : "Undefined";

            model.addRow(new Object[]{id, numberRoom, price, isAvailable, floor, location});
        }
    }

    public void populateTableAfterSearch() {
        DefaultTableModel model = setTableColumns();
        List<Room> rooms = roomRepository.readAll();

        if (Objects.requireNonNull(roomsView.getSearchByComboBox().getSelectedItem()).equals(LanguageManager.getString("numberRoomField"))) {
            rooms.sort(Comparator.comparing(Room::getNrRoom));
        } else if (roomsView.getSearchByComboBox().getSelectedItem().equals(LanguageManager.getString("locationField"))) {
            rooms.sort(Comparator.comparing(Room::getLocation));
        }

        setTableRows(model, rooms);
        roomsView.setTable(model);
    }

    public DefaultTableModel populateTable() {
        DefaultTableModel model = setTableColumns();
        List<Room> rooms = roomRepository.readAll();
        setTableRows(model, rooms);
        roomsView.setTable(model);
        return model;
    }

    public void populateTableAfterFilter(List<RoomFacilities> facilities) {
        DefaultTableModel model = setTableColumns();
        List<Room> rooms = roomRepository.readAll();

        if (!facilities.isEmpty() && Objects.requireNonNull(roomsView.getFilterByComboBox().getSelectedItem()).equals("Facilities")) {
            rooms = rooms.stream()
                    .filter(room -> room.getFacilities().stream().anyMatch(facilities::contains))
                    .collect(Collectors.toList());

        } else if (Objects.requireNonNull(roomsView.getFilterByComboBox().getSelectedItem()).equals("Availability")) {
            rooms = rooms.stream()
                    .filter(room -> room.getIsAvailable().equals(true))
                    .collect(Collectors.toList());
        } else if (roomsView.getFilterByComboBox().getSelectedItem().equals("Price")) {
            rooms.sort(Comparator.comparing(Room::getPrice));
        } else {
            String selectedPosition = roomsView.getFilterByComboBox().getSelectedItem().toString();
            rooms = rooms.stream()
                    .filter(room -> RoomFloorMapper.mapToFloorString(room.getFloor()).equalsIgnoreCase(selectedPosition))
                    .collect(Collectors.toList());
        }

        setTableRows(model, rooms);
        roomsView.setTable(model);
    }
}
