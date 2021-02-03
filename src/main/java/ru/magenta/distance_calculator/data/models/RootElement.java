package ru.magenta.distance_calculator.data.models;

import java.util.List;

/**
 * Корневой элемент для XML
 * @param <E> Объект, который представляет выгружаемые данные. Список вложенных в корневой элементов
 */
public interface RootElement<E> {
    void setData(List<E> data);
    List<E> getData();
}
