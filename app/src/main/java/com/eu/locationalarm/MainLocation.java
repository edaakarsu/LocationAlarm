package com.eu.locationalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.eu.locationalarm.util.BuildAlertMessageNoGps;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainLocation extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;


    GoogleMap googleMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    public android.location.Location firstLocation;

    private LocationManager locationManager;

    private BuildAlertMessageNoGps alertMessages;

    List<Marker> markers = new ArrayList<Marker>();
    Marker myMarker = null;

    double firstLatitude;
    double firstLongitude;
    double lastLatitude;
    double lastLongitude;
    LatLng firstLatLng;
    LatLng lastLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_location);

        init();

    }

    private void init() {

        initToolbar();

        initLocationManager();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap; // getting map.

    }

    private void initLocationManager() {

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        checkProviderEnabled(locationManager);
    }




    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        dialog.dismiss();
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(callGPSSettingIntent,111);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();
        if (!isFinishing()) {
            alert.show();
        }

    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

        }

        Task<android.location.Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null) {

                    //Toast.makeText(getApplicationContext(), location.getLatitude() + " / " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MainLocation.this);

                    firstLocation = location;

                }
            }
        });
    }

    private void checkProviderEnabled(LocationManager locationManager) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000,0,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(android.location.Location location) {

                            if(firstLatitude==0.0 && firstLongitude==0.0){
                                firstLatitude = location.getLatitude();
                                firstLongitude = location.getLongitude();
                                firstLatLng = new LatLng(firstLatitude,firstLongitude);
                                getMyLocation(googleMap,firstLatitude,firstLongitude,firstLatLng);
                            }else {
                                lastLatitude = location.getLatitude();
                                lastLongitude = location.getLongitude();
                                lastLatLng = new LatLng(lastLatitude, lastLongitude);
                                getMyLocation (googleMap,lastLatitude, lastLongitude,lastLatLng);
                            }
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
        } else if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(android.location.Location location) {

                    if(firstLatitude==0.0 && firstLongitude==0.0){
                        firstLatitude = location.getLatitude();
                        firstLongitude = location.getLongitude();
                        firstLatLng = new LatLng(firstLatitude,firstLongitude);
                        getMyLocation(googleMap,firstLatitude,firstLongitude,firstLatLng);
                    }else {
                        lastLatitude = location.getLatitude();
                        lastLongitude = location.getLongitude();
                        lastLatLng = new LatLng(lastLatitude, lastLongitude);
                        getMyLocation (googleMap,lastLatitude, lastLongitude,lastLatLng);
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }


    }

    private void getMyLocation(GoogleMap googleMap, double latitude, double longitude, LatLng latLng) {

        Geocoder geocoder = new Geocoder(getApplicationContext());

        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);

            String coordinates = addressList.get(0).getCountryCode() + " : " + latLng.toString();



            if (latLng != null) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                if (myMarker != null) myMarker.remove();
                myMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title("You are here: " + coordinates));
                //String dialog = "You are here:\n" + coordinates;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private void initToolbar() {

        toolbar=findViewById(R.id.tbLocation);
        setSupportActionBar(toolbar);

        // for adding back button
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainSelectProfile.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            Intent intent = new Intent(this,MainSelectProfile.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.menu_add);
        item.setVisible(false);
        return true;
    }



}
