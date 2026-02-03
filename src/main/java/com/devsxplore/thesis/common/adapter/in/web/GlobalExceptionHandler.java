package com.devsxplore.thesis.common.adapter.in.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public String handleRequest(RuntimeException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }
}
