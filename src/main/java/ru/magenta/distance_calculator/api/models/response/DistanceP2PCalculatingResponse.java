package ru.magenta.distance_calculator.api.models.response;

public class DistanceP2PCalculatingResponse {
    private String distance;

    public DistanceP2PCalculatingResponse(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
