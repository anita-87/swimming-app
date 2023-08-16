package com.anarodriguez.licenses.exceptions;

public class InvalidLicenceTypeException extends RuntimeException {

    public InvalidLicenceTypeException(String licenceType) {
        super("Invalid licence type " + licenceType);
    }
}
