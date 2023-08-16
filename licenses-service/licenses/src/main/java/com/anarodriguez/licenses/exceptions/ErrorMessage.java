package com.anarodriguez.licenses.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class ErrorMessage implements Serializable {

    private String timestamp;
    private int status;
    private String error;
    private String message;
}
