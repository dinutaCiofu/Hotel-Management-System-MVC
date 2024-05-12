package org.example.controller;

import org.example.model.entities.RoomFloor;
import org.example.model.entities.RoomFloorMapper;
import org.example.model.repository.RoomRepository;
import org.jfree.data.general.DefaultPieDataset;

public class RoomStatisticsController {
    private final RoomRepository roomRepository;

    public RoomStatisticsController() {
        this.roomRepository = new RoomRepository();

    }

    public DefaultPieDataset<String> getRoomStatisticsData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for (RoomFloor floor : RoomFloor.values()) {
            dataset.setValue(RoomFloorMapper.mapToFloorString(floor), 0);
        }
        roomRepository.readAll().forEach(room -> {
            String floorString = RoomFloorMapper.mapToFloorString(room.getFloor());
            Number count = dataset.getValue(floorString);
            dataset.setValue(floorString, count.intValue() + 1);
        });
        return dataset;
    }
}