package com.example.mcsprojectakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.PeriodicSync;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsActivity extends AppCompatActivity implements OnMapReadyCallback, IBaseGpsListener {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";
    private static final int PERMISSION_LOCATION = 1000;
    int userId;

    private GoogleMap mapForm;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeForm.class);
        intent.putExtra(SEND_ID, userId);

        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        SupportMapFragment fragment = SupportMapFragment.newInstance();
        fragment.getMapAsync(this);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_mapform, fragment).commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
        } else {
            showLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_LOCATION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showLocation();
            }
        } else {
            Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @SuppressLint("MissingPermission")
    private void showLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //check if gps enabled
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //start locating
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            //enable gps
            Toast.makeText(this, "Enable GPS!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapForm = googleMap;
        System.out.println("hola mundo");

        LatLng GeoPosTienda1 = new LatLng(37.203741, -3.609705);
        mapForm.addMarker(new MarkerOptions().position(GeoPosTienda1).title("TIENDA 1")).showInfoWindow();

        LatLng GeoPosTienda2 = new LatLng(37.193582, -3.622148);
        mapForm.addMarker(new MarkerOptions().position(GeoPosTienda2).title("TIENDA 2")).showInfoWindow();

        LatLng GeoPosTienda3 = new LatLng(37.188955, -3.602182);
        mapForm.addMarker(new MarkerOptions().position(GeoPosTienda3).title("TIENDA 3")).showInfoWindow();

        mapForm.moveCamera(CameraUpdateFactory.newLatLngZoom(GeoPosTienda1, 10));
    }

    //show location as string
    private String hereLocation(Location location) {
        return "Lat: " + location.getLatitude() + ", Lon: " + location.getLongitude();
    }

    @Override
    public void onLocationChanged(Location location) {
        //update Location
        System.out.println(hereLocation(location));
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mapForm.addMarker(new MarkerOptions().position(currentLocation).title("Current Location")).showInfoWindow();

    }

    @Override
    public void onProviderDisabled(String provider) {
        //empty
    }

    @Override
    public void onProviderEnabled(String provider) {
        //empty
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onGpsStatusChanged(int event) {

    }
}