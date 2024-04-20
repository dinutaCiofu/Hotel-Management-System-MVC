package org.example.model.entities;

public class RoomFloorMapper {
    public static String mapToFloorString(RoomFloor roomFloor) {
        return switch (roomFloor) {
            case FLOOR_0 -> "FLOOR 0";
            case FLOOR_1 -> "FLOOR 1";
            case FLOOR_2 -> "FLOOR 2";
            case FLOOR_3 -> "FLOOR 3";
        };
    }

    public static RoomFloor mapToFloorRoom(String floorString) {
        return switch (floorString) {
            case "FLOOR 0" -> RoomFloor.FLOOR_0;
            case "FLOOR 1" -> RoomFloor.FLOOR_1;
            case "FLOOR 2" -> RoomFloor.FLOOR_2;
            case "FLOOR 3" -> RoomFloor.FLOOR_3;
            default -> null;
        };
    }
}
