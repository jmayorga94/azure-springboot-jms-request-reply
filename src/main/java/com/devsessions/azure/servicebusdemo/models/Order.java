package com.devsessions.azure.servicebusdemo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public class Order implements Serializable {

    private static final long serialVersionUID = -295422703255886286L;

    private UUID id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    private boolean IsProccesed;

    public boolean isProccesed() {
        return IsProccesed;
    }

    public void setIsProccesed(boolean proccesed) {
        IsProccesed = false;
    }



    public Order(String name, boolean isProccesed) {
        this.id = UUID.randomUUID();
        this.name = name;
        IsProccesed = isProccesed;
    }

}