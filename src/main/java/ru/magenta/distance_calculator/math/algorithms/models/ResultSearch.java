package ru.magenta.distance_calculator.math.algorithms.models;

/**
 * Информация о результате поиска пути
 */
public class ResultSearch {
    private String nodesPath;
    private String distance;

    /**
     *
     * @param nodesPath все пройденные вершины
     * @param distance расстояние между крайними вершинами
     */
    public ResultSearch(String nodesPath, String distance) {
        this.nodesPath = nodesPath;
        this.distance = distance;
    }

    public String getNodesPath() {
        return nodesPath;
    }

    public void setNodesPath(String nodesPath) {
        this.nodesPath = nodesPath;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
