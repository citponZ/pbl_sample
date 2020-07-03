package jp.co.softbank.fy20.springbootaks.exception;


import java.sql.SQLException;
import org.springframework.dao.DuplicateKeyException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        String message = e.getMessage();
        model.addAttribute("message", message);
        return "exception/exception";
    }

    /*
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException e, Model model) {
        String message = e.getMessage();
        model.addAttribute("message", message);
        return "exception/exception";
    }
    */

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String duplicateKeyException(DuplicateKeyException e, Model model) {
        String message = "この語句は登録されています。";
        model.addAttribute("message", message);
        return "exception/exception";
    }
}