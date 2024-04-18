package org.example.model.entities;

public class FacilitatiCameraMapper {
    public static String mapToFacilitatiString(FacilitatiCamera facilitatiCamera) {
        switch (facilitatiCamera) {
            case WIFI:
                return "Wi-Fi";
            case TV:
                return "TV";
            case AER_CONDITIONAT:
                return "Aer condiționat";
            case ROOM_SERVICE:
                return "Room service";
            case MINI_BAR:
                return "Mini bar";
            default:
                return "";
        }
    }
}
