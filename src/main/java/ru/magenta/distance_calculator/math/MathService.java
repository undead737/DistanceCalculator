package ru.magenta.distance_calculator.math;

import ru.magenta.distance_calculator.api.models.request.DistanceCalculatingRequest;
import ru.magenta.distance_calculator.api.models.response.DistanceP2PCalculatingResponse;
import ru.magenta.distance_calculator.db.DBService;
import ru.magenta.distance_calculator.db.connection.MySqlConnection;
import ru.magenta.distance_calculator.db.models.City;
import ru.magenta.distance_calculator.math.algorithms.CalculatingDataRequest;
import ru.magenta.distance_calculator.math.algorithms.GraphTraversalAlgorithm;
import ru.magenta.distance_calculator.math.algorithms.PathfindingAlgorithm;
import ru.magenta.distance_calculator.math.algorithms.models.DistanceCalculatingResult;
import ru.magenta.distance_calculator.math.algorithms.models.ResultSearch;
import ru.magenta.distance_calculator.math.pointToPoint.DistanceMath;
import ru.magenta.distance_calculator.math.pointToPoint.DistanceUnits;
import ru.magenta.distance_calculator.math.pointToPoint.MapPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MathService {

    private static PathfindingAlgorithm algorithm;
    private static final DBService DB_SERVICE;
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(MySqlConnection.class.getName());
        DB_SERVICE = new DBService();
        try {
            //Отдаем для наполнения все города, у которых есть связь с другимиы
            algorithm = new GraphTraversalAlgorithm(new DBService()::getConcatData);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
    }

    /**
     * Вычисляем расстояние между двумя точками по их координатам
     *
     * @param coordinatesFrom первая координата
     * @param coordinatesTo   творая координата
     * @return расстояние между ними
     */
    public DistanceP2PCalculatingResponse getDistanceBetweenTwoPoints(String coordinatesFrom, String coordinatesTo) throws Exception {
        return new DistanceP2PCalculatingResponse(String.format("%.3f",DistanceMath.betweenTwoMapPointsMoreAccurately(MapPoint.parse(coordinatesFrom), MapPoint.parse(coordinatesTo), DistanceUnits.KILOMETERS)));
    }

    /**
     * @param requestData списки городов + методы поиска путей
     * @return Пары городов и результат поиска путей между ними
     */
    public List<DistanceCalculatingResult> getCalculatingDistanceResult(DistanceCalculatingRequest requestData) throws Exception {
        //Результаты поиска
        List<DistanceCalculatingResult> resultList = new ArrayList<>();
        //Составляем список пар Первый_город-Второй_город
        List<CalculatingDataRequest> temp = new ArrayList<>();
        requestData.getFromCities().forEach(from -> requestData.getToCities().forEach(to -> {
            //Исключаем пару одинаковых городов
            if (!from.equals(to)) {
                temp.add(new CalculatingDataRequest(from, to));
            }
        }));
        switch (requestData.getType()) {
            case ALL:
                resultList.addAll(algorithm.getResults(temp));
                resultList.addAll(getDistanceBetweenTwoCity(temp));
                break;
            case CROWFLIGHT:
                resultList.addAll(getDistanceBetweenTwoCity(temp));
                break;
            case DISTANCE_MATRIX:
                resultList.addAll(algorithm.getResults(temp));
                break;
        }
        return resultList;
    }

    /**
     * Сделал для тестов. Берем для поиска связей все годора
     */
    public List<DistanceCalculatingResult> getCalculatingDistanceResult() throws Exception{
        DistanceCalculatingRequest requestData = new DistanceCalculatingRequest();
        requestData.setType(CalculationType.ALL);
        requestData.setFromCities(DB_SERVICE.getAllCities());
        List<City> cities = new ArrayList<>();
        cities.add(requestData.getFromCities().get(0));
        requestData.setToCities(cities);
        return getCalculatingDistanceResult(requestData);
    }

    /**
     * Вычисляем расстояние между двумя городами
     * @param data список пар городов
     * @return результаты поиска
     */
    private List<DistanceCalculatingResult> getDistanceBetweenTwoCity(List<CalculatingDataRequest> data) throws Exception {
        List<DistanceCalculatingResult> resultList = new ArrayList<>();
        for (CalculatingDataRequest temp : data) {
            List<ResultSearch> tempResult = new ArrayList<>();
            tempResult.add(new ResultSearch("", String.format("%.3f",DistanceMath.betweenTwoMapPointsMoreAccurately(MapPoint.parseByCity(temp.getFromCity()), MapPoint.parseByCity(temp.getToCity()), DistanceUnits.KILOMETERS))));
            resultList.add(new DistanceCalculatingResult(CalculationType.CROWFLIGHT, temp.getFromCity().getId(), temp.getToCity().getId(), tempResult));
        }
        return resultList;
    }

    /**
     * Обновляем данные для аглоритма
     */
    public void updateAlgorithm(){
        try {
            algorithm = new GraphTraversalAlgorithm(new DBService()::getConcatData);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
    }
}
