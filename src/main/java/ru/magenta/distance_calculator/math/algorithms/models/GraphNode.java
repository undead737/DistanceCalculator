package ru.magenta.distance_calculator.math.algorithms.models;

import ru.magenta.distance_calculator.math.algorithms.GraphNodeException;

import java.util.*;

/**
 * Вершина графа. Имеет ссылки на все соседние вершины. Так мы определяем все достижимые вершины от заданной вершины
 *
 * Если вершина A имеет ссылку на соседа B, то и B должен иметь ссылку на соседа A
 */
public class GraphNode {
    private final int id;
    private final String name;
    private final Map<GraphNode, Double> endPoints;

    public GraphNode(int id, String name) {
        this.id = id;
        this.name = name;
        this.endPoints = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<GraphNode, Double> getEndPoints() {
        return endPoints;
    }

    public void addEndPoint(GraphNode node, double distance) throws GraphNodeException {
        //if (id > 0 && distance > 0) {
        if (distance > 0) {
            Double temp = this.endPoints.get(node);
            if (temp != null) if (temp.equals(distance)) return;
            this.endPoints.put(node, distance);
        } else throw new GraphNodeException("Distance between points must be > 0");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode that = (GraphNode) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
