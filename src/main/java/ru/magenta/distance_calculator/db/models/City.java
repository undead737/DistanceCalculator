package ru.magenta.distance_calculator.db.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("unused")
@XmlRootElement
@XmlType(name = "city")
public class City extends DBEntity {
    private String name;
    private String latitude;
    private String longitude;

    public City() {
        super();
    }

    public City(int id, String name, String latitude, String longitude) {
        super(id);
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    @XmlElement(name = "latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @XmlElement(name = "longitude")
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
