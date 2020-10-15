package com.targetmol.common.advice;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//通用异常处理
@ControllerAdvice //默认情况下会拦截所有的Controller
public class CommExceptionHandler {
    @ExceptionHandler(ErpExcetpion.class) //拦截RuntimeException异常
    public ResponseEntity<ExceptionResult> handleExceprion(ErpExcetpion e){
        ExceptionEumn eumn=e.getExceptionEumnumn();
        return ResponseEntity.status(200).body(new ExceptionResult(e.getExceptionEumnumn() ));
    }

}