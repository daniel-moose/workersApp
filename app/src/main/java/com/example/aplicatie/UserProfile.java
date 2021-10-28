package com.example.aplicatie;

public class UserProfile {
    public String userFirstName;
    public String userLastName;
    public String userEmail;
    public String userPhone;
    public String userJob;
    public String userPassword;
    public String userStatus;
    public String userID;
    public String inConversation;

    public UserProfile(){
    }

    public UserProfile(String userFirstName, String userLastName, String userEmail, String userPhone, String userJob, String userPassword, String userStatus,String userID,String inConversation) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userJob = userJob;
        this.userPassword= userPassword;
        this.userStatus = userStatus;
        this.userID = userID;
        this.inConversation = inConversation;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserID(){return userID;}
    public void setUserID(String userID){this.userID=userID;}

    public String getInConversation() {
        return inConversation;
    }

    public void setInConversation(String inConversation) {
        this.inConversation = inConversation;
    }
}
