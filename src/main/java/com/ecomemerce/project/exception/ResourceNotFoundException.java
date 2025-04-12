package com.ecomemerce.project.exception;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String filed;
    String filedName;
    Long fliedId;

    public ResourceNotFoundException() {

    }
    public ResourceNotFoundException(
            String resourceName,
            String filed,
            String filedName) {
        super(String.format("%s not found with %s : %s", resourceName, filed, filedName));
        this.resourceName = resourceName;
        this.filed = filed;
        this.filedName = filedName;

    }
    public ResourceNotFoundException(
            String resourceName,
            String filed,
            Long fliedId
    ){
        super(String.format("%s not found with %s : %d", resourceName, filed, fliedId));
        this.resourceName = resourceName;
        this.filed = filed;
        this.fliedId = fliedId;
    }


}
