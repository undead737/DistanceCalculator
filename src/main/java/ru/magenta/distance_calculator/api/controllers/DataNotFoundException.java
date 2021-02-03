package ru.magenta.distance_calculator.api.controllers;

public class DataNotFoundException extends Exception{
    public DataNotFoundException() {
        super("Data not found");
    }
}
