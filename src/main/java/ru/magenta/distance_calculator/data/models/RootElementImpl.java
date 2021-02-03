package ru.magenta.distance_calculator.data.models;

import ru.magenta.distance_calculator.db.models.ConcatData;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Корневой элемент XML. Содерит список объединенных данных (город от + город до + расстояние)
 */
@XmlRootElement
@XmlType(name = "data")
public class RootElementImpl implements RootElement<ConcatData> {
    private List<ConcatData> data;

    public RootElementImpl() {
    }

    public RootElementImpl(List<ConcatData> data) {
        this.data = data;
    }

    public List<ConcatData> getData() {
        return data;
    }

    public void setData(List<ConcatData> data) {
        this.data = data;
    }
}
