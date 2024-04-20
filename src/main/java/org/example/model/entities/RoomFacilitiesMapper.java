package org.example.model.entities;

public class RoomFacilitiesMapper {
    public static String mapToFacilitiesString(RoomFacilities roomFacilities) {
        return switch (roomFacilities) {
            case WIFI -> "Wi-Fi";
            case TV -> "TV";
            case AC -> "AC";
            case ROOM_SERVICE -> "Room service";
            case MINI_BAR -> "Mini bar";
        };
    }
}
