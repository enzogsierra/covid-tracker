package com.example.covidtracker.models;


public class LocationStats 
{
    private String country;
    private String state;
    private int latestTotal;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getLatestTotal() {
        return latestTotal;
    }

    public void setLatestTotal(int latestTotal) {
        this.latestTotal = latestTotal;
    }

    @Override
    public String toString() {
        return "LocationStats{" + "country=" + country + ", state=" + state + ", latestTotal=" + latestTotal + '}';
    }
}
