package com.gk.jaikisaan.models.responses;

public class CreateAccountResponse {
    private String username;
    private String accountType;
    private boolean createdStatus;
    private String errorMessage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isCreatedStatus() {
        return createdStatus;
    }

    public void setCreatedStatus(boolean createdStatus) {
        this.createdStatus = createdStatus;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String usertype) {
        this.accountType = usertype;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
