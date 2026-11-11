package com.project.back_horus.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String mensaje){
        super(mensaje);
    }
}
