package com.svalero.appCamisetas.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Shirt {
    @PrimaryKey
    @NonNull
    private String model;
    @ColumnInfo
    private String description;
    @ColumnInfo
    private String brand;
    @ColumnInfo
    private double latitude;
    @ColumnInfo
    private double longitude;

    public Shirt(){

    }

    public Shirt(String model, String description, String brand, double latitude, double longitude) {
        this.model = model;
        this.description = description;
        this.brand = brand;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
