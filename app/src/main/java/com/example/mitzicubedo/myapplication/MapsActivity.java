package com.example.mitzicubedo.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    double lat = 0.0;
    double lng = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng somewhere = new LatLng(29.072967, -110.955919);
        mMap.addMarker(new MarkerOptions().position(somewhere).title("Marcardor en Hermosillo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(somewhere));

        myLocation();
    }

    private void addMarker(double lat, double lng) {
        LatLng currentPos = new LatLng(lat, lng);
        CameraUpdate myLocation = CameraUpdateFactory.newLatLngZoom(currentPos, 16);
        if (marker != null) {
            marker.remove();
        } else {
            marker = mMap.addMarker(new MarkerOptions().position(currentPos).title("Mi posición"));
            mMap.animateCamera(myLocation);
        }
    }

    private void updateLocation(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            addMarker(lat, lng);
        }
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }
    };

    private void myLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Location location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        updateLocation(location);
        //locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,loclistener);
    }
}
