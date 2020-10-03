package com.targetmol.common.exception;

import com.google.common.collect.ImmutableMap;
import com.targetmol.common.emums.ExceptionEumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErpExcetpion  extends  RuntimeException{
        private ExceptionEumn exceptionEumnumn;
        protected static ImmutableMap.Builder<Class<? extends Throwable>, ExceptionEumn> builder = ImmutableMap.builder();
        //定义map，配置异常类型所对应的错误代码
        private static ImmutableMap<Class<? extends Throwable>,ExceptionEumn> EXCEPTIONS;

        static {

        }
}
