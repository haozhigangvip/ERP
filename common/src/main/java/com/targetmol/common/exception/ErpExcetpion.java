package com.targetmol.common.exception;

import com.targetmol.common.emums.ExceptionEumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErpExcetpion  extends  RuntimeException{
        private ExceptionEumn exceptionEumnumn;
}
