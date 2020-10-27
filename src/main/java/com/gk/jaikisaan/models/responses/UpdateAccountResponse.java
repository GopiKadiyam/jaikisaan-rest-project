package com.gk.jaikisaan.models.responses;

public class UpdateAccountResponse {
 private String userName;
 private Boolean updateStatus;
 private String errorMessage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Boolean updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
