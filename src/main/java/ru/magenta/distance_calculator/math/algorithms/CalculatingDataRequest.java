package ru.magenta.distance_calculator.math.algorithms;

import ru.magenta.distance_calculator.db.models.City;

public class CalculatingDataRequest {
    private final City fromCity;
    private final City toCity;

    public CalculatingDataRequest(City fromCity, City toCity) {
        this.fromCity = fromCity;
        this.toCity = toCity;
    }

    public City getFromCity() {
        return fromCity;
    }

    public City getToCity() {
        return toCity;
    }
}
