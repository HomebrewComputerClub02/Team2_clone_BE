package com.homebrewtify.demo.exception;

import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponse;
import com.homebrewtify.demo.config.BaseResponseStatus;
import io.swagger.models.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(value = {BaseException.class})
    protected BaseResponse<BaseResponseStatus> customExceptionHandler(BaseException e){
       log.error("Exception :"+e.getStatus().getMessage()+" / code: "+e.getStatus().getCode());
       return new BaseResponse<>(e.getStatus());
    }
}
