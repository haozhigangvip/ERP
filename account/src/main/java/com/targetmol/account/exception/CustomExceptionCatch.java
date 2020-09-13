package com.targetmol.account.exception;


import com.google.common.collect.ImmutableMap;
import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.common.vo.ResultMsg;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**课程管理自定义的异常类，其中定义异常类型所对应的错误代码
 * @author Administrator
 * @version 1.0
 **/
@ControllerAdvice//控制器增强
public class CustomExceptionCatch  extends ErpExcetpion {
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ExceptionEumn> builder = ImmutableMap.builder();
    //定义map，配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>,ExceptionEumn> EXCEPTIONS;
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultMsg exception(Exception exception) {
        exception.printStackTrace();
        //记录日志
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();//EXCEPTIONS构建成功
        }
        //从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ExceptionEumn resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode != null) {
            return new ResultMsg(resultCode);
        } else {
            //返回99999异常

            return new ResultMsg(ExceptionEumn.NETWOK_IS_BUSY.getCode(),ExceptionEumn.NETWOK_IS_BUSY.getMsg());
        }
    }


    static {
        builder.put(AccessDeniedException.class, ExceptionEumn.PERMESSION_DENIED);
    }
}