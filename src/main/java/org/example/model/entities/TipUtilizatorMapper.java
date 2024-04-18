package org.example.model.entities;

public class TipUtilizatorMapper {
    public static String mapToTipUtilizatorString(TipUtilizator tipUtilizator) {
        switch (tipUtilizator) {
            case CLIENT:
                return "Client";
            case ADMINISTRATOR:
                return "Administrator";
            case ANGAJAT:
                return "Angajat";
            default:
                return "";
        }
    }

    public static TipUtilizator mapToTipUtilizator(String tipUtilizatorString) {
        switch (tipUtilizatorString) {
            case "Client":
                return TipUtilizator.CLIENT;
            case "Administrator":
                return TipUtilizator.ADMINISTRATOR;
            case "Angajat":
                return TipUtilizator.ANGAJAT;
            default:
                return null;
        }
    }
}