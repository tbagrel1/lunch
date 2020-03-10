package com.tbagrel1.lunch.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO: improve
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "invalid input")
public class BadRequestException extends RuntimeException {

}
