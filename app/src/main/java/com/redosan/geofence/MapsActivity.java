package com.redosan.geofence;

import android.content.Intent;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowLongClickListener {

    private GoogleMap mMap;
    private LatLng marcador;
    private Geocoder geo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(this);
        geo = new Geocoder(this, Locale.getDefault());
        mMap.setOnInfoWindowLongClickListener(this);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getUiSettings().setAllGesturesEnabled(true);

    }


    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(this,   latLng.longitude +" " + latLng.latitude, Toast.LENGTH_SHORT).show();
        marcador = new LatLng(latLng.latitude, latLng.longitude);

        try {
            mMap.addMarker(new MarkerOptions().position(marcador).title((geo.getFromLocation(latLng.latitude, latLng.longitude, 1)).get(0).getAddressLine(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(marcador)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        Intent iInfo = new Intent(this, InfoActivity.class);
        iInfo.putExtra("latlong", marker.getPosition());
        startActivity(iInfo);
    }

}
