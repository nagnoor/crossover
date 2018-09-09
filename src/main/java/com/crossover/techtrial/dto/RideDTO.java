package com.crossover.techtrial.dto;

public class RideDTO {

    private Long id;

    private String startTime;

    private String endTime;

    private Long distance;

    private PersonDTO driver;

    private PersonDTO rider;

    public RideDTO(String startTime, String endTime, Long distance, PersonDTO driver, PersonDTO rider) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
        this.driver = driver;
        this.rider = rider;
    }

    public RideDTO() {
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

    public PersonDTO getDriver() {
        return driver;
    }

    public void setDriver(PersonDTO driver) {
        this.driver = driver;
    }

    public PersonDTO getRider() {
        return rider;
    }

    public void setRider(PersonDTO rider) {
        this.rider = rider;
    }


    @Override
    public String toString() {
        return "RideDTO{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", distance=" + distance +
                ", driver=" + driver +
                ", rider=" + rider +
                '}';
    }
}
