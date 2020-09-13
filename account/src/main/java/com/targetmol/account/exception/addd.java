package com.targetmol.account.exception;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;

public class addd extends ErpExcetpion {
    static {
        builder.put(AccessDeniedException.class, ExceptionEumn.PERMESSION_DENIED);
    }
}
