package main.backend.model.car;

import main.backend.db.repository.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Car implements Model<String> {
    private String carPlate;
    private String brand;
    private String model;
    private Integer seats;
    private BigDecimal price;
    private Transmission transmission;

    public Car(String carPlate, String brand, String model, Integer seats, BigDecimal price, Transmission transmission) {
        this.carPlate = carPlate;
        this.brand = brand;
        this.model = model;
        this.seats = seats;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
        this.transmission = transmission;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    @Override
    public String getPrimaryKey() {
        return carPlate;
    }

    @Override
    public void setPrimaryKey(String pk) {
        carPlate = pk;
    }
}
