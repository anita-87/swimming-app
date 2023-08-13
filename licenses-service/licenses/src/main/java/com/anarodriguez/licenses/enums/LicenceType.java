package com.anarodriguez.licenses.enums;

public enum LicenceType {
    COACH, MASTER, SWIMMING;

    public static LicenceType fromString(String type) {
        if (type.equals("Entrenador") || type.equals("coach")) {
            return COACH;
        }
        if (type.equals("Master") || type.equals("master")) {
            return MASTER;
        }if (type.equals("Deportista") || type.equals("swim")) {
            return SWIMMING;
        } else {
            throw new IllegalArgumentException("Invalid licence type '" + type +"'");
        }
    }
}
