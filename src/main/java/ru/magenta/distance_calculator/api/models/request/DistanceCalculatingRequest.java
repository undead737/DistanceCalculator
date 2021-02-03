package ru.magenta.distance_calculator.api.models.request;

import ru.magenta.distance_calculator.db.models.City;
import ru.magenta.distance_calculator.math.CalculationType;

import java.util.List;

public class DistanceCalculatingRequest {
    private CalculationType type;
    private List<City> fromCities;
    private List<City> toCities;

    public DistanceCalculatingRequest(CalculationType type, List<City> fromCities, List<City> toCities) {
        this.type = type;
        this.fromCities = fromCities;
        this.toCities = toCities;
    }

    public DistanceCalculatingRequest() {
    }

    public CalculationType getType() {
        return type;
    }

    public void setType(CalculationType type) {
        this.type = type;
    }

    public List<City> getFromCities() {
        return fromCities;
    }

    public void setFromCities(List<City> fromCities) {
        this.fromCities = fromCities;
    }

    public List<City> getToCities() {
        return toCities;
    }

    public void setToCities(List<City> toCities) {
        this.toCities = toCities;
    }
}
