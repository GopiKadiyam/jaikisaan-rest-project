package com.gk.jaikisaan.models.account;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class AccountDetails {
    @Id
    private String id;

    private String username;
    private String password;
    private String accountType;
    private String adminkey;


    private UserDetails userDetails;

    public AccountDetails() {
    }

    //Basic Account Details
    public AccountDetails(String username, String accountType) {
        this.username = username;
        this.accountType = accountType;
    }


    //Basic Account Details with accountType
    public AccountDetails(String username, String password, String accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    // admin account details constructor
    public AccountDetails(String username, String password, String accountType, String adminkey, UserDetails userDetails) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.adminkey = adminkey;
        this.userDetails = userDetails;

    }

    // farmer/vendor account details constructor
    public AccountDetails(String username, String password, String accountType, UserDetails userDetails) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.userDetails = userDetails;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAdminkey() {
        return adminkey;
    }

    public void setAdminkey(String adminkey) {
        this.adminkey = adminkey;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }


}
