package com.anarodriguez.licenses.enums;

public enum Gender {
    MALE, FEMALE;

    public static Gender fromString(String gender) {
        if (gender.equals("Masculino")) {
            return MALE;
        }
        return FEMALE;
    }
}
