package ru.magenta.distance_calculator.db.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Объединение двух 'соседних' городов и 'дороги' между ними
 */
@XmlRootElement
@XmlType(name = "concat")
@SuppressWarnings("unused")
public class ConcatData {
    private City cityFrom;
    private City cityTo;
    private double distance;

    public ConcatData() {
    }

    public ConcatData(City cityFrom, City cityTo, double distance) {
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.distance = distance;
    }

    public City getCityFrom() {
        return cityFrom;
    }

    public City getCityTo() {
        return cityTo;
    }

    public double getDistance() {
        return distance;
    }

    @XmlElement(name = "cityFrom")
    public void setCityFrom(City cityFrom) {
        this.cityFrom = cityFrom;
    }

    @XmlElement(name = "cityTo")
    public void setCityTo(City cityTo) {
        this.cityTo = cityTo;
    }

    @XmlElement(name = "distance")
    public void setDistance(double distance) {
        this.distance = distance;
    }
}
