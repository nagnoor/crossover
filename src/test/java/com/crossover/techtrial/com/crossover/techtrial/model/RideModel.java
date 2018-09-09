package com.crossover.techtrial.com.crossover.techtrial.model;

public class RideModel {

    private Long id;

    private String startTime;

    private String endTime;

    private Long distance;

    private PersonModel driver;

    private PersonModel rider;

    public RideModel(String startTime, String endTime, Long distance, PersonModel driver, PersonModel rider) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
        this.driver = driver;
        this.rider = rider;
    }

    public RideModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public PersonModel getDriver() {
        return driver;
    }

    public void setDriver(PersonModel driver) {
        this.driver = driver;
    }

    public PersonModel getRider() {
        return rider;
    }

    public void setRider(PersonModel rider) {
        this.rider = rider;
    }
}
