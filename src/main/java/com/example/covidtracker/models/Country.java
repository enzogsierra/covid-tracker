package com.example.covidtracker.models;


public class Country 
{
    private String name;
    private int newCases;
    private int totalCases;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int cases) {
        this.newCases = cases;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int cases) {
        this.totalCases = cases;
    }

    @Override
    public String toString() {
        return "Country{" + "name=" + name + ", newCases=" + newCases + ", totalCases=" + totalCases + '}';
    }
}
