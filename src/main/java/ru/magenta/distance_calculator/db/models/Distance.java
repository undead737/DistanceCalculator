package ru.magenta.distance_calculator.db.models;

@SuppressWarnings("unused")
public class Distance extends DBEntity {
    private int fromCity;
    private int toCity;
    private double distance;

    public Distance(int id, int fromCity, int toCity, double distance) {
        super(id);
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.distance = distance;
    }

    public Distance(int fromCity, int toCity, double distance) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.distance = distance;
    }

    public int getFromCity() {
        return fromCity;
    }

    public void setFromCity(int fromCity) {
        this.fromCity = fromCity;
    }

    public int getToCity() {
        return toCity;
    }

    public void setToCity(int toCity) {
        this.toCity = toCity;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
