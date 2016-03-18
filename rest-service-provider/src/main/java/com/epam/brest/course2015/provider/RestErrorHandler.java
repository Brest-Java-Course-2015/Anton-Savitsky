package com.epam.brest.course2015.provider;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by xalf on 26/10/15.
 */
@ControllerAdvice
public class RestErrorHandler {
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleDataAccessException(DataAccessException ex) {
        return "DataAccessException: " + ex.getLocalizedMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "IllegalArgumentException: " + ex.getLocalizedMessage();
    }
}
