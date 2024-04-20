package org.example.model.entities;

public class UserTypeMapper {
    public static String mapToUserTypeString(UserType userType) {
        return switch (userType) {
            case CLIENT -> "Client";
            case ADMINISTRATOR -> "Administrator";
            case EMPLOYEE -> "Employee";
        };
    }

    public static UserType mapToUserType(String userTypeString) {
        return switch (userTypeString) {
            case "Client" -> UserType.CLIENT;
            case "Administrator" -> UserType.ADMINISTRATOR;
            case "Employee" -> UserType.EMPLOYEE;
            default -> null;
        };
    }
}