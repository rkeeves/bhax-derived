package com.rkeeves.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BasicPlaceRepository implements PlaceRepository {

    private Map<String, Place> cities = new HashMap<>();

    public BasicPlaceRepository(Map<String, Place> cities) {
        this.cities = cities;
    }

    public Place getByName(String name){
        return cities.get(name);
    }

    public Place add(Place place){
        return cities.put(place.getName(), place);
    }

    @Override
    public Stream<Place> getAll() {
        return cities.values().stream();
    }
}
