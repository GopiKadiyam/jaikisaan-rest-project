package com.gk.jaikisaan.models.account;

import java.util.List;

public class UserDetails {


    private Name name;
    private List<Vendor> vendorTypeNames;
    private List<Crop> cropnames;
    private Address address;
    private String email;
    private Long primaryPhonenumber;
    private Long secondaryPhonenumber;

    public UserDetails() {
    }
    // admin user details constructor
    public UserDetails(Name name, Address address,String email, Long primaryPhonenumber, Long secondaryPhonenumber) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.primaryPhonenumber = primaryPhonenumber;
        this.secondaryPhonenumber = secondaryPhonenumber;
    }
    // farmer user details constructor
    public UserDetails(Name name, List<Crop> cropnames, Address address,String email, Long primaryPhonenumber, Long secondaryPhonenumber) {
        this.name = name;
        this.address = address;
        this.cropnames = cropnames;
        this.email = email;
        this.primaryPhonenumber = primaryPhonenumber;
        this.secondaryPhonenumber = secondaryPhonenumber;
    }
    // admin vendor details constructor
    public UserDetails(Name name,Address address,  List<Vendor> vendorTypeNames, String email,  Long primaryPhonenumber, Long secondaryPhonenumber) {
        this.name = name;
        this.vendorTypeNames = vendorTypeNames;
        this.address = address;
        this.email = email;
        this.primaryPhonenumber = primaryPhonenumber;
        this.secondaryPhonenumber = secondaryPhonenumber;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Vendor> getVendorTypeNames() {
        return vendorTypeNames;
    }

    public void setVendorTypeNames(List<Vendor> vendorTypeNames) {
        this.vendorTypeNames = vendorTypeNames;
    }

    public List<Crop> getCropnames() {
        return cropnames;
    }

    public void setCropnames(List<Crop> cropnames) {
        this.cropnames = cropnames;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getPrimaryPhonenumber() {
        return primaryPhonenumber;
    }

    public void setPrimaryPhonenumber(Long primaryPhonenumber) {
        this.primaryPhonenumber = primaryPhonenumber;
    }

    public Long getSecondaryPhonenumber() {
        return secondaryPhonenumber;
    }

    public void setSecondaryPhonenumber(Long secondaryPhonenumber) {
        this.secondaryPhonenumber = secondaryPhonenumber;
    }


}
