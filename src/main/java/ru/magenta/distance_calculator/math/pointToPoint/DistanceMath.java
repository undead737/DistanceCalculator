package ru.magenta.distance_calculator.math.pointToPoint;

/**
 * Подсчет расстояния между двумя точками на карте по координатам широты и долготы
 */
@SuppressWarnings("unused")
public final class DistanceMath {

    //Радиус земли
    public static final double EARTH_RADIUS = 6372.795;

    /**
     * Более точный результат. Используется, чтобы избежать проблем с небольшими расстояниями
     *
     * @param firstPoint  координаты первой точки
     * @param secondPoint координаты второй точки
     * @param units       единицы измерения
     * @return расстояние между ними
     */
    public static double betweenTwoMapPointsMoreAccurately(MapPoint firstPoint, MapPoint secondPoint, DistanceUnits units) {
        double result = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(((secondPoint.getLatitude() * Math.PI / 180) - (firstPoint.getLatitude() * Math.PI / 180)) / 2), 2)
                + Math.cos(firstPoint.getLatitude() * Math.PI / 180) * Math.cos(secondPoint.getLatitude() * Math.PI / 180)
                * Math.pow(Math.sin(((secondPoint.getLongitude() * Math.PI / 180) - (firstPoint.getLongitude() * Math.PI / 180)) / 2), 2))) * EARTH_RADIUS;
        switch (units) {
            case KILOMETERS:
            default:
                return result;
            case METERS:
                return result * 1000;
        }
    }

    /**
     * Результат точный, если точки далеко друг от друга
     *
     * @param firstPoint  координаты первой точки
     * @param secondPoint координаты второй точки
     * @param units       единицы измерения
     * @return расстояние между ними
     */
    public static double betweenTwoMapPoints(MapPoint firstPoint, MapPoint secondPoint, DistanceUnits units) {
        double result = Math.asin(Math.sin(firstPoint.getLatitude()) * Math.sin(secondPoint.getLatitude())
                + Math.cos(firstPoint.getLatitude()) * Math.cos(secondPoint.getLatitude())
                * Math.cos(firstPoint.getLongitude() - secondPoint.getLongitude())) * EARTH_RADIUS;
        switch (units) {
            case KILOMETERS:
            default:
                return result;
            case METERS:
                return result * 1000;
        }
    }
}
