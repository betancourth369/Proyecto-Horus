package com.project.back_horus.exception;

public class ResourceNotFoundException extends BusinessException{
    public ResourceNotFoundException(String mensaje){
        super(mensaje);
    }
}
