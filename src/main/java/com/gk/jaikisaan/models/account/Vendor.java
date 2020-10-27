package com.gk.jaikisaan.models.account;

public class Vendor {
    private String vendorName;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorName='" + vendorName + '\'' +
                '}';
    }
}
