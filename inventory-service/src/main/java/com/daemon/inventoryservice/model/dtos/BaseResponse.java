package com.daemon.inventoryservice.model.dtos;

public record BaseResponse (String[] errorMessages){
    public boolean hasErrors(){
        return errorMessages.length > 0 && errorMessages !=null;
    }
}
