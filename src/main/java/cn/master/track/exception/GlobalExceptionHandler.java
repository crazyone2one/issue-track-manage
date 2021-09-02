package cn.master.track.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Created by 11's papa on 2021/08/26
 * @version 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * NullPointerException
     *
     * @param exception NullPointerException
     * @param model     Model
     * @return java.lang.String
     */
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "error/404";
    }

    /**
     * ArithmeticException
     *
     * @param exception ArithmeticException
     * @param model     Model
     * @return java.lang.String
     */
    @ExceptionHandler(ArithmeticException.class)
    public String handleArithmeticException(ArithmeticException exception, Model model) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("exMsg", exception.getMessage());
        model.addAttribute("message", map);
        return "error/500";
    }

    /**
     * Exception
     *
     * @param exception Exception
     * @param model     Model
     * @return java.lang.String
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "error/404";
    }
}
