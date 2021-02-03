package ru.magenta.distance_calculator.api.models.request;

/**
 * Данные для расчета расстояния между двумя точками
 */
public class DistanceP2PCalculatingRequest {
    private String firstPosition;
    private String secondPosition;

    public DistanceP2PCalculatingRequest(String firstPosition, String secondPosition) {
        this.firstPosition = firstPosition;
        this.secondPosition = secondPosition;
    }

    public DistanceP2PCalculatingRequest() {
    }

    public String getFirstPosition() {
        return firstPosition;
    }

    public void setFirstPosition(String firstPosition) {
        this.firstPosition = firstPosition;
    }

    public String getSecondPosition() {
        return secondPosition;
    }

    public void setSecondPosition(String secondPosition) {
        this.secondPosition = secondPosition;
    }
}
