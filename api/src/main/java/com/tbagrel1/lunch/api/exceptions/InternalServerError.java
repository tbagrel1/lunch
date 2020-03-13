package com.tbagrel1.lunch.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO: improve
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "invalid input")
public class InternalServerError extends RuntimeException {

}
