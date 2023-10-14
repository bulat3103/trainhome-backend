package com.example.trainhome.controllers;

import com.example.trainhome.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.ParseException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionApiHandler extends ResponseEntityExceptionHandler {

    private static Map<Object, Object> doModel(HttpStatus httpStatus, String message) {
        Map<Object, Object> model = new HashMap<>();
        model.put("code", httpStatus.value());
        model.put("message", message);
        model.put("timestamp", Instant.now());
        return model;
    }

    @ExceptionHandler(InvalidDataException.class)
    protected ResponseEntity<Object> invalidDataException(InvalidDataException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(doModel(
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage()));
    }

    @ExceptionHandler(NoSuchPersonException.class)
    protected ResponseEntity<Object> noSuchPersonException(NoSuchPersonException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(doModel(
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage()));
    }

    @ExceptionHandler(WrongPersonException.class)
    protected ResponseEntity<Object> wrongPersonException(WrongPersonException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(doModel(
                        HttpStatus.NOT_ACCEPTABLE,
                        exception.getMessage()));
    }

    @ExceptionHandler(NoSuchGroupChatException.class)
    protected ResponseEntity<Object> noSuchGroupChatException(NoSuchGroupChatException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(doModel(
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage()));
    }

    @ExceptionHandler(ParseException.class)
    protected ResponseEntity<Object> parseException(ParseException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(doModel(
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage()));
    }

    @ExceptionHandler(RecommendationNotFoundException.class)
    protected ResponseEntity<Object> recommendationNotFoundException(RecommendationNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(doModel(
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage()));
    }
}
