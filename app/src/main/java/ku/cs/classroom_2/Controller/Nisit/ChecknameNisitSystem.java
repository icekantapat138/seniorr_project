package ku.cs.classroom_2.Controller.Nisit;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ku.cs.classroom_2.Controller.Profressor.SystemOpen;
import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class ChecknameNisitSystem extends AppCompatActivity {

    TextView lat, lon, courseidText, coursenameText, statusText;
    Button checknameBtn, getlatlonBtn;
    DBHelper dbHelper;
    public LocationManager locationManager;
    public LocationListener locationListener = new MyLocationLister();
    String lati, longti, latC, lonC, dis, ccode, grcode;
    private boolean gps_enable = false;
    private boolean network_enable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkname_nisit_system);

        dbHelper = new DBHelper(this);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        lat = (TextView) findViewById(R.id.lat);
        lon = (TextView) findViewById(R.id.lon);
        courseidText = (TextView) findViewById(R.id.courseidText);
        coursenameText = (TextView) findViewById(R.id.coursenameText);
        statusText = (TextView) findViewById(R.id.statusText);
        checknameBtn = (Button) findViewById(R.id.checknameBtn);
        getlatlonBtn = (Button) findViewById(R.id.getlatlonBtn);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String id = intent.getStringExtra("id");

        Cursor cursor = dbHelper.coursenameIs(id);
        while (cursor.moveToNext()) {
            String cname = cursor.getString(1);
            latC = cursor.getString(3);
            lonC = cursor.getString(4);
            dis = cursor.getString(5);
            String status = cursor.getString(6);
            courseidText.setText(id);
            coursenameText.setText(cname);
            statusText.setText(status);
        }

        Cursor cursor1 = dbHelper.courseRe(email, id);
        while (cursor1.moveToNext()){
            ccode = cursor1.getString(0);
        }

        grcode = generateID(ccode);

        checknameBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (statusText.getText().equals("ON")) {
                    Double distance = distance(Double.parseDouble(String.valueOf(lat.getText())), Double.parseDouble(String.valueOf(lon.getText())), Double.parseDouble(latC), Double.parseDouble(lonC));
                    Toast.makeText(ChecknameNisitSystem.this, "" + lat.getText() + "," + lon.getText() + "," + latC + "," + lonC, Toast.LENGTH_SHORT).show();
                    if (Double.parseDouble(dis) >= distance) {
                        dbHelper.addCheckID(grcode, ccode, email, id, "Check");
                        Toast.makeText(ChecknameNisitSystem.this, "Check Pass", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChecknameNisitSystem.this, "Out of range", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChecknameNisitSystem.this, "System is closed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getlatlonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
                checkLocationPermission();
                Toast.makeText(ChecknameNisitSystem.this, "get location success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return distance;
    }

    public String generateID(String idcode){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String id = idcode + "_" +dtf.format(now);
        return id;
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ChecknameNisitSystem.this);
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

