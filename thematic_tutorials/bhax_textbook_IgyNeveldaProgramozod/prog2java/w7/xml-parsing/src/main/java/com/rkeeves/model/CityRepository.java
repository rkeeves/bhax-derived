package com.rkeeves.model;

import java.util.*;
import java.util.function.Consumer;

public class CityRepository implements Consumer<City> {

    private final Map<String, List<City>> cities = new HashMap<>();

    @Override
    public void accept(City city) {
        cities.computeIfAbsent(city.getStateCode(),key -> new LinkedList<>())
                .add(city);
    }

    public Set<String> getStates(){
        return cities.keySet();
    }

    public List<City> getCitiesForState(String stateCode){
        return cities.computeIfAbsent(stateCode,key -> new LinkedList<>());
    }
}
