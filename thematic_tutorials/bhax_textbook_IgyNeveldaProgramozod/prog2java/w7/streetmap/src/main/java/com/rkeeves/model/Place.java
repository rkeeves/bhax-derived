package com.rkeeves.model;

import lombok.Data;
import org.jxmapviewer.viewer.GeoPosition;

@Data
public class Place {

    private final String name;

    private final GeoPosition geoPosition;
}
