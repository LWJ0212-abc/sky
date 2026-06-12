package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


//    Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '小智' for key 'employee.idx_username'
    /**
     * @param ex:
      * @return Result
     * @author lwj
     * @description 捕获插入异常
     * @date 2026/6/12 18:45
     */
    @ExceptionHandler
    public Result sqlIntegrityConsExHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        log.error("ExceptionHandler 捕捉到的exception {}", message);
        if(message.contains("Duplicate entry")){
            String[] messages = message.split(" ");
            String username=messages[2];
            String msg=username+ MessageConstant.ALREADY_EXISTS;
            log.error(msg);
            return Result.error(msg);
        }else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }


    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){

        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

}
