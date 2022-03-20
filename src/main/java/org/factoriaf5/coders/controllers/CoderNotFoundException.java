package org.factoriaf5.coders.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "coder not found")
public class CoderNotFoundException extends RuntimeException {
}