package com.example.toprecipe;

import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitud;
    double longitud;
    String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        latitud = getIntent().getExtras().getDouble("latitud");
        longitud = getIntent().getExtras().getDouble("longitud");
        nombre = getIntent().getExtras().getString("nombre");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> direccion = null;

        try {
            direccion = geocoder.getFromLocation(latitud, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ciudad = direccion.get(0).getLocality();

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitud, longitud);
        CameraUpdate miUbicacion=CameraUpdateFactory.newLatLngZoom(sydney,16);
        mMap.addMarker(new MarkerOptions().position(sydney).title(ciudad));
        mMap.animateCamera(miUbicacion);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Toast.makeText(getApplicationContext(),"Aqui se encuentra " + nombre, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
