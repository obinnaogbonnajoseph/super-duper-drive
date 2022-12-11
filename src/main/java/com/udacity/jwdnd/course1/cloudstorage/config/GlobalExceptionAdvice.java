package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(
            MaxUploadSizeExceededException ex) {

        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.getModel().put("errorMsg", "File too large!");
        return modelAndView;
    }
}
