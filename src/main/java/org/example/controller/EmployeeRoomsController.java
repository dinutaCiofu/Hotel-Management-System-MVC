package org.example.controller;

import org.example.model.entities.*;
import org.example.model.repository.HotelRepository;
import org.example.model.repository.ImageRepository;
import org.example.model.repository.RoomRepository;
import org.example.single_point_access.GUIFrameSinglePointAccess;
import org.example.view.RoomsView;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.List;

@Controller
public class EmployeeRoomsController extends ClientRoomsController {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ImageRepository imageRepository;
    private final RoomsView roomsView;

    public EmployeeRoomsController(RoomsView roomsView) {
        super(roomsView);
        this.roomsView = roomsView;
        this.roomRepository = new RoomRepository();
        this.hotelRepository = new HotelRepository();
        this.imageRepository = new ImageRepository();
        this.roomsView.getAddRoomButton().addActionListener(e -> this.addRoom());
        this.roomsView.getUpdateButton().addActionListener(e -> this.updateRoom());
        this.roomsView.getDeleteButton().addActionListener(e -> this.deleteRoom());
        this.roomsView.getFilterBtn().addActionListener(e -> populateTableAfterFilter(List.of(RoomFacilities.AC, RoomFacilities.TV)));
        this.roomsView.getSearchButton().addActionListener(e -> populateTableAfterSearch());

        this.roomsView.getAddImgBtn().addActionListener(e->{
            int selectedRow = this.roomsView.getTable().getSelectedRow();
            if (selectedRow != -1) {
                Long roomId = (Long) this.roomsView.getTable().getValueAt(selectedRow, 0);
                addImagesToRoom(roomId);
            } else {
                GUIFrameSinglePointAccess.showDialogMessage("Please select a room first");
               // JOptionPane.showMessageDialog(this, "Please select a room first", "No Room Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private Room roomFromStrings(String numberRoom, String price, String isAvailable, String floor, Hotel hotel) {
        Room room = new Room();
        room.setNrRoom(numberRoom);
        room.setPrice(Double.valueOf(price));
        room.setIsAvailable(isAvailable.equals("Available"));
        room.setFacilities(List.of(RoomFacilities.values()));
        room.setLocation(hotel);
        room.setFloor(RoomFloorMapper.mapToFloorRoom(floor));
        return room;
    }

    public void addRoom() {
        String numberRoom = roomsView.getNumberRoomTextField().getText();
        String price = roomsView.getPriceTextField().getText();
        String isAvailable = roomsView.getIsAvailableTextField().getText();
        String floor = roomsView.getFloorTextField().getText();
        String location = roomsView.getLocationTextField().getText();
        Hotel hotel = hotelRepository.findByName(location);
        if (hotel != null) {
            Room room = roomFromStrings(numberRoom, price, isAvailable, floor, hotel);
            hotel.getRooms().add(room);
            roomRepository.save(room);
            hotelRepository.update(hotel);
            roomsView.update();
        }
    }

    public void updateRoom() {
        DefaultTableModel model = (DefaultTableModel) roomsView.getTable().getModel();
        int selectedRow = roomsView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) model.getValueAt(selectedRow, 0);
            String numberRoom = (String) model.getValueAt(selectedRow, 1);
            String price = (String) model.getValueAt(selectedRow, 2);
            String isAvailable = (String) model.getValueAt(selectedRow, 3);
            String floor = (String) model.getValueAt(selectedRow, 4);
            String location = (String) model.getValueAt(selectedRow, 5);
            Hotel hotel = hotelRepository.findByName(location);
            if (hotel != null) {
                Room room = roomFromStrings(numberRoom, price, isAvailable, floor, hotel);
                room.setId(id);
                roomRepository.update(room);
                roomsView.update();
            }

        }
    }

    public void addImagesToRoom(Long roomId) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setDialogTitle("Select Images for Room");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            for (File file : files) {
                Image image = new Image();
                image.setPath(file.getAbsolutePath());
                image.setRoom(roomRepository.findById(roomId));
                imageRepository.save(image);
            }
        }
    }

    public void deleteRoom() {
        DefaultTableModel model = (DefaultTableModel) roomsView.getTable().getModel();
        int selectedRow = roomsView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) model.getValueAt(selectedRow, 0);
            String location = (String) model.getValueAt(selectedRow, 5);
            Hotel hotel = hotelRepository.findByName(location);
            if (hotel != null) {
                Room room = roomRepository.findById(id);
                hotel.getRooms().remove(room);
                hotelRepository.update(hotel);
                roomRepository.delete(room);
                model.removeRow(selectedRow);
                roomsView.update();
            }
        }
    }
}
