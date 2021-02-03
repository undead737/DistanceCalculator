package ru.magenta.distance_calculator.math.algorithms.models;

import ru.magenta.distance_calculator.math.CalculationType;

import java.util.List;

/**
 * Результат расчета пути
 */
@SuppressWarnings("unused")
public class DistanceCalculatingResult {
    private CalculationType type;
    private int fromCity;
    private int toCity;
    private List<ResultSearch> resultsSearch;

    public DistanceCalculatingResult(CalculationType type, int fromCity, int toCity, List<ResultSearch> resultsSearch) {
        this.type = type;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.resultsSearch = resultsSearch;
    }

    public DistanceCalculatingResult() {
    }

    public CalculationType getType() {
        return type;
    }

    public int getFromCity() {
        return fromCity;
    }

    public int getToCity() {
        return toCity;
    }

    public List<ResultSearch> getResultsSearch() {
        return resultsSearch;
    }
}
