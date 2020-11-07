package com.rkeeves.view;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

import java.awt.*;
import java.awt.geom.Point2D;

public class PlaceWaypointPainter extends WaypointPainter<PlaceWaypoint> {

    @Override
    protected void doPaint(Graphics2D g, JXMapViewer jxMapViewer, int width, int height) {
        for (PlaceWaypoint placeWaypoint : getWaypoints()) {
            Point2D point = jxMapViewer.getTileFactory().geoToPixel(
                    placeWaypoint.getPosition(), jxMapViewer.getZoom());
            Rectangle rectangle = jxMapViewer.getViewportBounds();
            int x = (int)(point.getX() - rectangle.getX());
            int y = (int)(point.getY() - rectangle.getY());
            var label = placeWaypoint.getIconLabel();
            label.setLocation(x - label.getWidth() / 2, y - label.getHeight() / 2);
        }
    }
}