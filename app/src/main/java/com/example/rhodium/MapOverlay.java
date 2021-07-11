package com.example.rhodium;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

//import org.osmdroid.ResourceProxy;

import org.osmdroid.api.IMapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import com.example.rhodium.R;

public class MapOverlay extends ItemizedOverlay<OverlayItem> {
    public ArrayList<OverlayItem> overlayItemList;

    public MapOverlay(Drawable pDefaultMarker) {
        super(pDefaultMarker);
        overlayItemList = new ArrayList<OverlayItem>(100);
    }

    public void addItem(GeoPoint geoPoint, String title, String snippet) {
        OverlayItem newItem = new OverlayItem(title, snippet, geoPoint);
        overlayItemList.add(newItem);
        populate();
    }

    public void clearItems() {
        overlayItemList.clear();
    }

    @Override
    public boolean onSnapToItem(int arg0, int arg1, Point arg2, IMapView arg3) {
        return false;
    }

    @Override
    protected OverlayItem createItem(int arg0) {
        return overlayItemList.get(arg0);
    }

    @Override
    public int size() {
        return overlayItemList.size();
    }
}
