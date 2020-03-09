package com.tbagrel1.lunch.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO: improve
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "entity not found")
public class NotFoundException extends RuntimeException {

}
