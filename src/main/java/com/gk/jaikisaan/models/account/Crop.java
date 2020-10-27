package com.gk.jaikisaan.models.account;

public class Crop {
private String cropName;

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    @Override
    public String toString() {
        return "Crop{" +
                "cropName='" + cropName + '\'' +
                '}';
    }
}

