package ru.magenta.distance_calculator.math.algorithms;

import ru.magenta.distance_calculator.db.models.ConcatData;
import ru.magenta.distance_calculator.math.CalculationType;
import ru.magenta.distance_calculator.math.algorithms.models.GraphNode;
import ru.magenta.distance_calculator.math.algorithms.models.DistanceCalculatingResult;
import ru.magenta.distance_calculator.math.algorithms.models.ResultSearch;

import java.util.*;

/**
 * Алгоритм поиска пути методом обхода графа
 */
public final class GraphTraversalAlgorithm extends PreInitAlgorithm<ConcatData> implements PathfindingAlgorithm {

    /**
     * @param initMethod Передаем метод, который вернет нам массив данных, необходимых для инициализации
     */
    public GraphTraversalAlgorithm(InitMethod<ConcatData> initMethod) throws Exception{
        super(initMethod);
    }

    /**
     * Для каждой пары Первый_Город-Второй_Город ищем все пути между ними и посчитываем расстояние
     * @param requestData Коллекция пар городов, между которыми надо найти все возможные пути
     * @return Результаты поиска пути
     */
    @Override
    public List<DistanceCalculatingResult> getResults(List<CalculatingDataRequest> requestData) throws Exception{
        List<DistanceCalculatingResult> result = new ArrayList<>();
        for (CalculatingDataRequest request : requestData) {
            result.add(calculateDistance(request));
        }
        return result;
    }

    /**
     * Пытаемся найти города по исходным данным, готовим исходные данные для алгоритма поиска пути
     * @param requestData Исходные данные городов
     * @return Результат поиска пути
     * @throws GraphNodeException Исключение в случаях:
     *                   по исходным данным не нашлись города
     */
    private DistanceCalculatingResult calculateDistance(CalculatingDataRequest requestData) throws GraphNodeException {
        GraphNode nodeFrom = graphNodes.stream().filter(x -> x.getId() == requestData.getFromCity().getId()).findFirst().orElse(null);
        GraphNode nodeTo = graphNodes.stream().filter(x -> x.getId() == requestData.getToCity().getId()).findFirst().orElse(null);
        if (nodeFrom == null || nodeTo == null) throw new GraphNodeException("Graph node not found");
        //Обработанные вершины
        Set<GraphNode> processedNodes = new HashSet<>();
        //Сразу добавляем начальную вершину
        processedNodes.add(nodeFrom);
        //Список результатов
        List<ResultSearch> resultsSearch = new ArrayList<>();
        searchPath(processedNodes, nodeFrom, nodeTo, "", 0, resultsSearch);
        return new DistanceCalculatingResult(CalculationType.DISTANCE_MATRIX, requestData.getFromCity().getId(), requestData.getToCity().getId(), resultsSearch);
    }


    /**
     * Обход в глубину. Идем вглубь графа, рекурсивно заходя во все непосещенные ноды
     *
     * Достигнув тупика, возвращаемся к ближайшей вершине, у которой есть ранее не посещенные вершины.
     * Попутно записываем все пути, которые довели до нужной вершины
     *
     * Внутри метода используем локальные переменные для результатов, чтобы если вершина оказалась тупиковой,
     * изменения не сохранились
     *
     * @param processedNodes Список посещенных вершин
     * @param startNode      Вершина, у которой начинаем смотреть доступные вложенные вершины
     * @param endNode        Искомая вершина
     * @param pathToEndNode  Пройденный путь до искомой вершины
     * @param distance       Дистанция до искомой вершины
     * @param resultsSearch  Все результаты достижения искомой вершины
     */
    private void searchPath(Set<GraphNode> processedNodes, GraphNode startNode, GraphNode endNode, String pathToEndNode, double distance, List<ResultSearch> resultsSearch) {
        //Смотрим связанные вершины
        for (Map.Entry<GraphNode, Double> entry : startNode.getEndPoints().entrySet()){
            String temp_pathToEndNode = pathToEndNode;
            double temp_distance = distance;
            temp_pathToEndNode += startNode.getName() + "(" + startNode.getId() + ") " + " -> ";
            temp_distance += entry.getValue();
            //Если по пути мы не заходили на эту вершину
            if (!processedNodes.contains(entry.getKey())){
                if (entry.getKey().equals(endNode)){
                    //Сбрасываем результаты поиска
                    pathToEndNode = "";
                    distance = 0;
                    //Добавляем результаты поиска в итоговый список
                    resultsSearch.add(new ResultSearch(temp_pathToEndNode + endNode.getName(), String.format("%.3f",temp_distance)));
                }
                //Для следующей вершины передаем список всех посещенных до нее вершин
                Set<GraphNode> temp = new HashSet<>(processedNodes);
                temp.add(entry.getKey());
                //Рекурсия. Переходим к следующей вершине
                searchPath(temp, entry.getKey(), endNode, temp_pathToEndNode, temp_distance, resultsSearch);
            }
        }
    }

    /**
     * Добавление новой вершины графа
     *
     * @param data данные для добавления в таблицу вершин и связей
     * @throws GraphNodeException Ошибка добавления вершины в список
     */
    @Override
    public void addNode(ConcatData data) throws GraphNodeException {
        //Пытаемся получить основную вершину
        GraphNode nodeFrom = graphNodes.stream().filter(x -> x.getId() == data.getCityFrom().getId()).findFirst().orElse(null);
        //Пытаемся получить смежную вершину
        GraphNode nodeTo = graphNodes.stream().filter(x -> x.getId() == data.getCityTo().getId()).findFirst().orElse(null);
        //Если смежной нет, создаем, добавляем в список
        if (nodeTo == null) {
            nodeTo = new GraphNode(data.getCityTo().getId(), data.getCityTo().getName());
            //Если основной вершин, создаем, добавляем в список
            if (nodeFrom == null) {
                nodeFrom = new GraphNode(data.getCityFrom().getId(), data.getCityFrom().getName());
                graphNodes.add(nodeFrom);
            }
            graphNodes.add(nodeTo);
        }
        //Если основной вершин, создаем, добавляем в список
        if (nodeFrom == null) {
            nodeFrom = new GraphNode(data.getCityFrom().getId(), data.getCityFrom().getName());
            graphNodes.add(nodeFrom);
        }
        //К смежной добавляем основную вершину как соседа
        nodeTo.addEndPoint(nodeFrom, data.getDistance());
        //К основной вершине добавляем смежную как соседа
        nodeFrom.addEndPoint(nodeTo, data.getDistance());
    }
}
