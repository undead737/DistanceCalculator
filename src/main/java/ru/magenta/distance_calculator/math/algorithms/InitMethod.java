package ru.magenta.distance_calculator.math.algorithms;

import java.util.List;

@FunctionalInterface
public interface InitMethod<E> {
    List<E> getData() throws Exception;
}
