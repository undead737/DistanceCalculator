package ru.magenta.distance_calculator.math.algorithms;

import ru.magenta.distance_calculator.math.algorithms.models.GraphNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Алгоритмы поиска, которым для работы необходима начальная инициализация всех нод
 * @param <E> объект, представляющий собой две вершины и связь между ними
 */
public abstract class PreInitAlgorithm<E> {
    //Список всех вершин
    protected static final List<GraphNode> graphNodes;

    static {
        graphNodes = new ArrayList<>();
    }

    public PreInitAlgorithm(InitMethod<E> initMethod) throws Exception{
        graphNodes.clear();
        init(initMethod);
    }

    /**
     * Инициализация
     */
    public void init(InitMethod<E> initMethod) throws Exception{
        for (E temp : initMethod.getData()){
            addNode(temp);
        }
    }

    public abstract void addNode(E data) throws Exception ;
}
