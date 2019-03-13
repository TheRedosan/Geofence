package com.redosan.geofence;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Locale;

public class InfoActivity extends AppCompatActivity {

    private Geocoder geo;
    private LatLng marcador;

    private Address dir;
    private TextView tvCiudad;
    private TextView tvDireccion;
    private Button btBorrar;
    private Button btDesactivar;
    private Button btFijar;
    private EditText etRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();

        marcador = getIntent().getParcelableExtra("latlong");

        geo = new Geocoder(this, Locale.getDefault());
        try {
            dir = (geo.getFromLocation(marcador.latitude, marcador.longitude, 1)).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tvCiudad.setText(dir.getLocality());
        tvDireccion.setText(dir.getAddressLine(0));

    }

    private void initView() {
        tvCiudad = findViewById(R.id.tvCiudad);
        tvDireccion = findViewById(R.id.tvDireccion);
        btBorrar = findViewById(R.id.btBorrar);
        btDesactivar = findViewById(R.id.btDesactivar);
        btFijar = findViewById(R.id.btFijar);
        etRadio = findViewById(R.id.etRadio);
    }
}
