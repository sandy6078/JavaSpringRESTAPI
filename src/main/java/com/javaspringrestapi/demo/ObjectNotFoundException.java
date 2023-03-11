package com.javaspringrestapi.demo;

@SuppressWarnings("serial")
public class ObjectNotFoundException extends RuntimeException {
    
    public ObjectNotFoundException(String name, Long id) {
        super("Unable to find " + name + " " + id);
    }
    
}