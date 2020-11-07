package com.rkeeves.model;

import java.util.stream.Stream;

public interface PlaceRepository {

    Place getByName(String name);

    Place add(Place place);

    Stream<Place> getAll();
}
