package org.example.model.entities;

public class PozitieCameraMapper {
    public static String mapToPozitieString(PozitieCamera pozitieCamera) {
        switch (pozitieCamera) {
            case PARTER:
                return "Parter";
            case ETAJ_1:
                return "Etaj 1";
            case ETAJ_2:
                return "Etaj 2";
            case ETAJ_3:
                return "Etaj 3";
            default:
                return "";
        }
    }

    public static PozitieCamera mapToPozitieCamera(String pozitieString) {
        switch (pozitieString) {
            case "Parter":
                return PozitieCamera.PARTER;
            case "Etaj 1":
                return PozitieCamera.ETAJ_1;
            case "Etaj 2":
                return PozitieCamera.ETAJ_2;
            case "Etaj 3":
                return PozitieCamera.ETAJ_3;
            default:
                return null;
        }
    }
}
