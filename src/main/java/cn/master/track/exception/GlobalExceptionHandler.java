package cn.master.track.exception;

import cn.master.track.enums.ExceptionEnum;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
        model.addAttribute("result",
                ResponseResult.error(ExceptionEnum.BODY_NOT_MATCH.getResultCode(),
                        ExceptionEnum.BODY_NOT_MATCH.getResultMsg() + ":" + exception.getMessage()));
        return "error/error";
    }

    /**
     * 抛出异常算术条件时抛出。 例如，“除以零”的整数会抛出此类的一个实例
     *
     * @param exception ArithmeticException
     * @param model     Model
     * @return java.lang.String
     */
    @ExceptionHandler(ArithmeticException.class)
    public String handleArithmeticException(ArithmeticException exception, Model model) {
        model.addAttribute("result",
                ResponseResult.error(ExceptionEnum.BODY_NOT_MATCH.getResultCode(),
                        ExceptionEnum.BODY_NOT_MATCH.getResultMsg() + ":" + exception.getMessage()));
        return "error/error";
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
        model.addAttribute("result",
                ResponseResult.error(ExceptionEnum.NOT_FOUND.getResultCode(),
                        ExceptionEnum.NOT_FOUND.getResultMsg() + ":" + exception.getMessage()));
        return "error/error";
    }
}
