package ru.magenta.distance_calculator.math.algorithms;

import ru.magenta.distance_calculator.math.algorithms.models.DistanceCalculatingResult;

import java.util.List;

public interface PathfindingAlgorithm {
    List<DistanceCalculatingResult> getResults(List<CalculatingDataRequest> requestData) throws Exception;
}
