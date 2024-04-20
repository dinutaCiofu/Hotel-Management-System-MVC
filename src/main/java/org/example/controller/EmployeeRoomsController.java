package org.example.controller;

import org.example.model.entities.Hotel;
import org.example.model.entities.Room;
import org.example.model.entities.RoomFacilities;
import org.example.model.entities.RoomFloorMapper;
import org.example.model.repository.HotelRepository;
import org.example.model.repository.RoomRepository;
import org.example.view.RoomsView;
import org.springframework.stereotype.Controller;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.UUID;

@Controller
public class EmployeeRoomsController extends ClientRoomsController {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomsView roomsView;

    public EmployeeRoomsController(RoomsView roomsView) {
        super(roomsView);
        this.roomsView = roomsView;
        this.roomRepository = new RoomRepository();
        this.hotelRepository = new HotelRepository();
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

    public void addCamera() {
        String numberRoom = roomsView.getRoomNumber();
        String price = roomsView.getPriceRoom();
        String isAvailable = roomsView.getAvailability();
        String floor = roomsView.getFloor();
        String location = roomsView.getLocation();
        Hotel hotel = hotelRepository.findByName(location);
        if (hotel != null) {
            Room room = roomFromStrings(numberRoom, price, isAvailable, floor, hotel);
            hotel.getRooms().add(room);
            roomRepository.save(room);
            hotelRepository.update(hotel);
        }
    }

    public void updateCamera() {
        DefaultTableModel model = (DefaultTableModel) roomsView.getTable().getModel();
        int selectedRow = roomsView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            UUID id = (UUID) model.getValueAt(selectedRow, 0);
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
            }

        }
    }

    public void deleteCamera() {
        DefaultTableModel model = (DefaultTableModel) roomsView.getTable().getModel();
        int selectedRow = roomsView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            UUID id = (UUID) model.getValueAt(selectedRow, 0);
            String location = (String) model.getValueAt(selectedRow, 5);
            Hotel hotel = hotelRepository.findByName(location);
            if (hotel != null) {
                Room room = roomRepository.findById(id);
                hotel.getRooms().remove(room);
                hotelRepository.update(hotel);
                roomRepository.delete(room);
                model.removeRow(selectedRow);
            }
        }
    }
}
