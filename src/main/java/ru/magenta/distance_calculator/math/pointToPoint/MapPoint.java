package ru.magenta.distance_calculator.math.pointToPoint;

import ru.magenta.distance_calculator.db.models.City;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Для построения объекта используются фабричные методы (строковый формат координат)
 * Если объект строится на основе данных из таблицы, используется конструктор
 * Корректость формата входной строки проверяем регулярными выражениями
 */
public final class MapPoint {
    private final double latitude;
    private final double longitude;

    /**
     * DD.ddd
     * Градусы с десятичной дробной частью
     * Пример: 51.93800051, 59.95486189
     *
     * @param point координаты в текстовом формате
     * @return если формат корректен, возвращает 'Точку На Карте'
     */
    public static MapPoint initAsDecimalDegrees(String point) throws MapPointInitException {
        if (point == null) throw new MapPointInitException("Point can't be null!");
        Matcher matcher = Pattern.compile("^([\\d]+[.][\\d]+)[,][ ]([\\d]+[.][\\d]+)$").matcher(point);
        if (matcher.find()) {
            return new MapPoint(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(2)));
        } else throw new MapPointInitException("Incorrect point format!");
    }

    /**
     * DD MM.mmm
     * Градусы и минуты с дробью
     * Пример: 41 24.2028, 2 10.4418
     *
     * @param point координаты в текстовом формате
     * @return если формат корректен, возвращает 'Точку На Карте'
     */
    public static MapPoint initAsDegreesAndMinutes(String point) throws MapPointInitException {
        if (point == null) throw new MapPointInitException("Point can't be null!");
        Matcher matcher = Pattern.compile("^([\\d]+)[ ]([\\d]+[.][\\d]+)[,][ ]([\\d]+)[ ]([\\d]+[.][\\d]+)$").matcher(point);
        if (matcher.find()) {
            double temp_latitude = Double.parseDouble(matcher.group(1)) + (Double.parseDouble(matcher.group(2)) / 60);
            double temp_longitude = Double.parseDouble(matcher.group(3)) + (Double.parseDouble(matcher.group(4)) / 60);
            return new MapPoint(temp_latitude, temp_longitude);
        } else throw new MapPointInitException("Incorrect point format!");
    }

    /**
     * DD MM SS.sss
     * Градусы, минуты и секунды
     * Пример: 41°24'12.2, 2°10'26.5
     *
     * @param point координаты в текстовом формате
     * @return если формат корректен, возвращает 'Точку На Карте'
     */
    public static MapPoint initAsDegreesMinutesAndSeconds(String point) throws MapPointInitException {
        if (point == null) throw new MapPointInitException("Point can't be null!");
        Matcher matcher = Pattern.compile("^([\\d]+)[°]([\\d]+)[']([\\d]+[.][\\d]+)[,][ ]([\\d]+)[°]([\\d]+)[']([\\d]+[.][\\d]+)$").matcher(point);
        if (matcher.find()) {
            double temp_latitude = Double.parseDouble(matcher.group(1)) + (Double.parseDouble(matcher.group(2)) * 60 + Double.parseDouble(matcher.group(3))) / 3600;
            double temp_longitude = Double.parseDouble(matcher.group(4)) + (Double.parseDouble(matcher.group(5)) * 60 + Double.parseDouble(matcher.group(6))) / 3600;
            return new MapPoint(temp_latitude, temp_longitude);
        } else throw new MapPointInitException("Incorrect point format!");
    }

    public MapPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Чтобы не подбирать фабричный метод для построения объекта :)
     * @param point координаты в текстовом формате
     * @return построенный объект
     * @throws MapPointInitException если не получится создать объект на основе входных данных
     */
    public static MapPoint parse(String point) throws MapPointInitException {
        {
            try {
                return MapPoint.initAsDecimalDegrees(point);
            } catch (MapPointInitException e) {
                try {
                    return MapPoint.initAsDegreesAndMinutes(point);
                } catch (MapPointInitException e1) {
                    try {
                        return MapPoint.initAsDegreesMinutesAndSeconds(point);
                    } catch (Exception ignored) {
                    }
                } catch (Exception ignored) {
                }
            } catch (Exception ignored) {
            }
        }
        throw new MapPointInitException("Incorrect point format!");
    }

    public static MapPoint parseByCity(City city) throws MapPointInitException{
        return parse(city.getLatitude() + ", " + city.getLongitude());
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapPoint mapPoint = (MapPoint) o;
        return Double.compare(mapPoint.latitude, latitude) == 0 && Double.compare(mapPoint.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "MapPoint{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
