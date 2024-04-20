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
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ClientRoomsController {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomsView roomsView;

    public ClientRoomsController(RoomsView roomsView) {
        this.roomsView = roomsView;
        this.roomRepository = new RoomRepository();
        this.hotelRepository = new HotelRepository();
    }

    public DefaultTableModel setTableColumns() {
        DefaultTableModel model = new DefaultTableModel();
        List<String> columns = List.of("ID", "numberRoom", "price", "isAvailable", "floor", "location");
        model.setRowCount(0);
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        for (String column : columns) {
            model.addColumn(column);
        }
        return model;
    }

    private String getRoomLocation(Room room) {

        if (room.getLocation() != null) {
            System.out.println("Existing hotel");
            UUID hotelId = room.getLocation().getId();
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
            UUID id = room.getId();
            String numberRoom = room.getNrRoom();
            String price = String.valueOf(room.getPrice());
            String isAvailable = room.getIsAvailable() ? "Available" : "Unavailable";
            String floor = RoomFloorMapper.mapToFloorString(room.getFloor());
            String location = getRoomLocation(room) != null ? getRoomLocation(room) : "Undefined";

            model.addRow(new Object[]{id, numberRoom, price, isAvailable, floor, location});
        }
    }

    public DefaultTableModel populateTableAfterSearch() {
        DefaultTableModel model = setTableColumns();
        List<Room> rooms = roomRepository.readAll();

        if (roomsView.getSelectedSearchByValue().equals("Number")) {
            rooms.sort(Comparator.comparing(Room::getNrRoom));
        } else if (roomsView.getSelectedSearchByValue().equals("Location")) {
            rooms.sort(Comparator.comparing(Room::getLocation));
        }
        setTableRows(model, rooms);
        roomsView.setTable(model);
        return model;
    }

    public DefaultTableModel populateTableAfterFilter(List<RoomFacilities> facilities) {
        DefaultTableModel model = setTableColumns();
        List<Room> rooms = roomRepository.readAll();

        if (!facilities.isEmpty() && roomsView.getSelectedFilterByValue().equals("Facilities")) {
            rooms = rooms.stream()
                    .filter(camera -> camera.getFacilities().containsAll(facilities))
                    .collect(Collectors.toList());
        } else if (roomsView.getSelectedFilterByValue().equals("isAvailable")) {
            rooms = rooms.stream()
                    .filter(camera -> camera.getIsAvailable().equals(true))
                    .collect(Collectors.toList());
        } else if (roomsView.getSelectedFilterByValue().equals("Price")) {
            rooms.sort(Comparator.comparing(Room::getPrice));
        } else {
            String selectedPosition = roomsView.getSelectedFilterByValue();
            rooms = rooms.stream()
                    .filter(camera -> RoomFloorMapper.mapToFloorString(camera.getFloor()).equalsIgnoreCase(selectedPosition))
                    .collect(Collectors.toList());
        }

        setTableRows(model, rooms);
        roomsView.setTable(model);
        return model;
    }
}
