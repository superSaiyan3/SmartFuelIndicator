package com.example.android.smartfuelindicator;

/**
 * Created by viscabarca on 15/04/17.
 */

public class Globals {
    private static Globals instance;
    private static float fuelValue;
    public static String model;
    public static double mileage;

    private Globals(){

    }

    public void setMileage(double value) {
        Globals.mileage=value;
    }

    public double getMileage() {
        return Globals.mileage;
    }

    public void setModel(String anotherString) {
        Globals.model=anotherString;
    }

    public String getModel() {
        return Globals.model;
    }

    public void setFuelValue(float value) {
        Globals.fuelValue = value;
    }

    public float getFuelValue() {
        return Globals.fuelValue;
    }

    public static synchronized Globals getInstance() {
        if(instance == null) {
            instance = new Globals();
        }
        return instance;
    }
}
