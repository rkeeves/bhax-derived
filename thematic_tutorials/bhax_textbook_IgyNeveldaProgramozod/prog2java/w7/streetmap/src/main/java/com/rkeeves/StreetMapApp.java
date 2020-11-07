package com.rkeeves;

import com.rkeeves.graphics.IconLoader;
import com.rkeeves.model.PlaceRepositoryFactory;
import com.rkeeves.view.PlaceWaypoint;
import com.rkeeves.view.PlaceWaypointFactory;
import com.rkeeves.view.PlaceWaypointPainter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.io.File;
import java.util.stream.Collectors;

public class StreetMapApp {

    public static void main(String[] args) {

        var placeRepository =  new PlaceRepositoryFactory().getPlaceRepository();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        var tileFactory = new DefaultTileFactory(info);
        tileFactory.setThreadPoolSize(8);

        var cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        var mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);
        mapViewer.setZoom(10);

        var debrecen = new GeoPosition(47.53333, 21.63333);
        var debrsecen = new GeoPosition(47.53333, 21.63333);
        mapViewer.setAddressLocation(debrecen);

        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        var iconLoader = new IconLoader();
        var placeWaypointFactory = new PlaceWaypointFactory(iconLoader);
        var waypoints = placeRepository
                .getAll()
                .map(placeWaypointFactory::create)
                .collect(Collectors.toSet());

        WaypointPainter<PlaceWaypoint> swingWaypointPainter = new PlaceWaypointPainter();
        swingWaypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(swingWaypointPainter);

        waypoints.
                stream()
                .map(PlaceWaypoint::getIconLabel)
                .forEach(mapViewer::add);

        JFrame frame = new JFrame("Debrecen");
        frame.getContentPane().add(mapViewer);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
