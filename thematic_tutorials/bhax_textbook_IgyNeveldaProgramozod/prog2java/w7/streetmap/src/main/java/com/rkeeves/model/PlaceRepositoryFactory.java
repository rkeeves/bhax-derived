package com.rkeeves.model;

import org.jxmapviewer.viewer.GeoPosition;

import java.util.HashMap;
import java.util.Map;

public class PlaceRepositoryFactory {

    Map<String, Place> cities = new HashMap<>();

    public PlaceRepositoryFactory() {
        init();
    }

    private void init() {
        add("Böszörményi Campus", 47.549427, 21.609392);
        add("HQ", 47.553511, 21.621548);
        add("Kassai Campus", 47.542811, 21.641552);
    }

    private void add(String name, double latitude, double longitude){
        cities.put(name, new Place(name, new GeoPosition(latitude, longitude)));
    }

    public PlaceRepository getPlaceRepository(){
        return new BasicPlaceRepository(cities);
    }
}
