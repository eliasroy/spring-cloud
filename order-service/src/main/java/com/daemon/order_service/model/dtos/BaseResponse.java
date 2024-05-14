package com.daemon.order_service.model.dtos;

public record BaseResponse (String[] errorMessages){
    public boolean hasErrors(){
        return errorMessages.length > 0 && errorMessages !=null;
    }
}
