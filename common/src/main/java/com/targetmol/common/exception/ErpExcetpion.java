package com.targetmol.common.exception;

import com.targetmol.common.emums.ExceptionEumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErpExcetpion  extends  RuntimeException{
        private ExceptionEumn exceptionEumnumn;
}
