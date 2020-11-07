package com.rkeeves.view;

import com.rkeeves.model.Place;
import lombok.Data;
import org.jxmapviewer.viewer.DefaultWaypoint;

import javax.swing.*;
import java.awt.*;

@Data
public class PlaceWaypoint extends DefaultWaypoint {

    private final Place place;

    private final JLabel iconLabel;

    public PlaceWaypoint(Place place, ImageIcon imageIcon) {
        super(place.getGeoPosition());
        this.place = place;
        this.iconLabel = new JLabel(place.getName(),imageIcon, JLabel.CENTER);
        this.iconLabel.setForeground(Color.BLACK);
        this.iconLabel.setVisible(true);
    }
}
