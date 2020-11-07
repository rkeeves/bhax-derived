package com.rkeeves.view;

import com.rkeeves.graphics.IconLoader;
import com.rkeeves.model.Place;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceWaypointFactory {

    private final IconLoader iconLoader;

    public PlaceWaypoint create(Place place){
        var icon = iconLoader.createIcon("uni.png",place.getName());
        return new PlaceWaypoint(place,icon);
    }
}
