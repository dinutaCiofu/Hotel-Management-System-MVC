package org.example.model.entities;

public class RoomFloorMapper {
    public static String mapToFloorString(RoomFloor roomFloor) {
        return switch (roomFloor) {
            case FLOOR_0 -> "FLOOR_0";
            case FLOOR_1 -> "FLOOR_1";
            case FLOOR_2 -> "FLOOR_2";
            case FLOOR_3 -> "FLOOR_3";
        };
    }

    public static RoomFloor mapToFloorRoom(String floorString) {
        return switch (floorString) {
            case "FLOOR_0" -> RoomFloor.FLOOR_0;
            case "FLOOR_1" -> RoomFloor.FLOOR_1;
            case "FLOOR_2" -> RoomFloor.FLOOR_2;
            case "FLOOR_3" -> RoomFloor.FLOOR_3;
            default -> throw new IllegalArgumentException("Invalid floor string: " + floorString);
        };
    }
}
