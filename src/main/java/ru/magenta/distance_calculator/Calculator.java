package ru.magenta.distance_calculator;

import ru.magenta.distance_calculator.api.controllers.CalculateController;
import ru.magenta.distance_calculator.api.controllers.CityController;
import ru.magenta.distance_calculator.api.controllers.DataController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Calculator extends Application {
    private final Set<Object> singletons = new HashSet<>();

    public Calculator() {
        singletons.add(new CityController());
        singletons.add(new DataController());
        singletons.add(new CalculateController());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
