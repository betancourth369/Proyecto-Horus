package com.project.back_horus.exception;

public class DuplicateResourceException extends BusinessException{
    public DuplicateResourceException(String mensaje){
        super(mensaje);
    }
}
