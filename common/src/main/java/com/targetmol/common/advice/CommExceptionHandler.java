package com.targetmol.common.advice;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


//异常通用类
@ControllerAdvice //默认情况下链接所有Controller
public class CommExceptionHandler {
    @ExceptionHandler({ErpExcetpion.class})     //拦截RunTimeExceprion 异常
    public ResponseEntity<ExceptionResult> handleException(ErpExcetpion e){
        ExceptionEumn  myenum =e.getExceptionEumnumn();
        return ResponseEntity.status(myenum.getCode()).body(new ExceptionResult(e.getExceptionEumnumn()));
    }
}
