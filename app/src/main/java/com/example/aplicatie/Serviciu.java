package com.example.aplicatie;

public class Serviciu {
    public String serviceName,servicePrice,serviceType;
    public Serviciu() {

    }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Serviciu(String serviceName, String servicePrice, String serviceType){
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceType = serviceType;
    }



}
