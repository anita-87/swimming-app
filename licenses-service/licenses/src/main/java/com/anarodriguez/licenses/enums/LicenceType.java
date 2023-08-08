package com.anarodriguez.licenses.enums;

public enum LicenceType {
    COACH, MASTER, SWIMMING;

    public static LicenceType fromString(String type) {
        if (type.equals("Entrenador")) {
            return COACH;
        }
        if (type.equals("Master")) {
            return MASTER;
        } else {
            return SWIMMING;
        }
    }
}
