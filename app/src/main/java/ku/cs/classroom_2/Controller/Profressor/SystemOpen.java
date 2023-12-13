package ku.cs.classroom_2.Controller.Profressor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class SystemOpen extends AppCompatActivity {

    EditText distanceText;
    TextView courseidText, coursenameText, lat, lon;
    Switch switch1;
    DBHelper dbHelper;
    public LocationManager locationManager;
    public LocationListener locationListener = new MyLocationLister();
    String lati,longti;
    private boolean gps_enable = false;
    private boolean network_enable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_open);

        dbHelper = new DBHelper(this);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        distanceText = (EditText) findViewById(R.id.distanceText);
        courseidText = (TextView) findViewById(R.id.courseidText);
        coursenameText = (TextView) findViewById(R.id.coursenameText);
        lat = (TextView) findViewById(R.id.lat);
        lon = (TextView) findViewById(R.id.lon);
        switch1 = (Switch) findViewById(R.id.switch1);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String id = intent.getStringExtra("id");

        Cursor cursor = dbHelper.coursenameIs(id);
        while (cursor.moveToNext()) {
            String cname = cursor.getString(1);
            String status = cursor.getString(6);
            courseidText.setText(id);
            coursenameText.setText(cname);
            if (status.equals("ON")){
                switch1.setChecked(true);
            } else if (status.equals(null)){
                switch1.setChecked(false);
            } else {
                switch1.setChecked(false);
            }
        }

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch1.isChecked()) {
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_SHORT).show();
                    getMyLocation();
                } else {
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_SHORT).show();
                    lat.setText("");
                    lon.setText("");
                    boolean isSuccess = dbHelper.updateStatusLatLong(id, null, null, null, "OFF");
                    if (isSuccess) {
                        Toast.makeText(SystemOpen.this, "OFF", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SystemOpen.this, "System Not Open", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        checkLocationPermission();
    }

    class MyLocationLister implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            String courseid = courseidText.getText().toString();
            System.out.println(courseid);
            if (location != null) {
                locationManager.removeUpdates(locationListener);
                lati = "" + location.getLatitude();
                longti = "" + location.getLongitude();

                lat.setText(lati);
                lon.setText(longti);
                String dis = String.valueOf(distanceText.getText());
                boolean isSuccess = dbHelper.updateStatusLatLong(courseid, lati, longti, dis, "ON");
                if (isSuccess) {
                    Toast.makeText(SystemOpen.this, "System is Open", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SystemOpen.this, "System can't Open", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onLocationChanged(@NonNull List<Location> locations) {
            LocationListener.super.onLocationChanged(locations);
        }

        @Override
        public void onFlushComplete(int requestCode) {
            LocationListener.super.onFlushComplete(requestCode);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener.super.onStatusChanged(provider, status, extras);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    }

    public void getMyLocation() {
        try {
            gps_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception e) {

        }

        try {
            network_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {

        }

        if (!gps_enable && !network_enable) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SystemOpen.this);
            builder.setTitle("Attention");
            builder.setMessage("Sorry, Location is not available, please enable location service");

            builder.create().show();
        }

        if (gps_enable) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        if (network_enable) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    private boolean checkLocationPermission() {
        int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int location2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listPermission = new ArrayList<>();

        if (location != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (location2 != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!listPermission.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermission.toArray(new String[listPermission.size()]), 1);
        }
        return true;
    }


}