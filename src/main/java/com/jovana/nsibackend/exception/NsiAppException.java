package com.jovana.nsibackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jovana on 05.11.2018
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NsiAppException extends RuntimeException {

    public NsiAppException(String message) {
        super(message);
    }

    public NsiAppException(String message, Throwable cause) {
        super(message, cause);
    }

}
