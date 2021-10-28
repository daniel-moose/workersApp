package com.example.aplicatie;

public class Comanda {
    String serviceName,servicePrice,commandID,refusedCommand,acceptedCommand,completedCommand,beneficiaryID,providerID;

    public Comanda(){
    }
    public Comanda(String serviceName,String servicePrice, String commandID,String refusedCommand,String acceptedCommand,String completedCommand,String beneficiaryID,String providerID){
        this.serviceName = serviceName;
        this.servicePrice=servicePrice;
        this.commandID = commandID;
        this.refusedCommand = refusedCommand;
        this.acceptedCommand = acceptedCommand;
        this.completedCommand = completedCommand;
        this.beneficiaryID = beneficiaryID;
        this.providerID = providerID;
    }

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

    public String getCommandID() {
        return commandID;
    }

    public void setCommandID(String commandID) {
        this.commandID = commandID;
    }

    public String getRefusedCommand() {
        return refusedCommand;
    }

    public void setRefusedCommand(String refusedCommand) {
        this.refusedCommand = refusedCommand;
    }

    public String getAcceptedCommand() {
        return acceptedCommand;
    }

    public void setAcceptedCommand(String acceptedCommand) {
        this.acceptedCommand = acceptedCommand;
    }

    public String getCompletedCommand() {
        return completedCommand;
    }

    public void setCompletedCommand(String completedCommand) {
        this.completedCommand = completedCommand;
    }

    public String getBeneficiaryID() {
        return beneficiaryID;
    }

    public void setBeneficiaryID(String beneficiaryID) {
        this.beneficiaryID = beneficiaryID;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }

}
