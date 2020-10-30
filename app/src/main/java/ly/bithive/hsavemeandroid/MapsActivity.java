package ly.bithive.hsavemeandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ly.bithive.hsavemeandroid.model.Clink;

import static ly.bithive.hsavemeandroid.COMMON.GET_UPDATE_URL;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    RequestQueue requestQueue;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Marker mClinkLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    List<Clink> clinks = new ArrayList<>();
    int selectedID = 0;
    private BottomSheetBehavior mBottomSheetBehavior1;
    View bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        requestQueue = Volley.newRequestQueue(this);
        getSupportActionBar().setTitle("Map Location Activity");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClinkProfile(view);
            }
        });
        bottomSheet = findViewById(R.id.bottom_sheet1);
        mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior1.setPeekHeight(0);
        mBottomSheetBehavior1.setHalfExpandedRatio(0.5f);
        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        mBottomSheetBehavior1.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
//                   // tapactionlayout.setVisibility(View.GONE);
//                }
//
//                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
//                    //tapactionlayout.setVisibility(View.GONE);
//                }
//
//                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
//                   // tapactionlayout.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });
    }

    private void openClinkProfile(View view) {
//        Snackbar.make(view, "Replace with your own action : " + selectedID, Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

        Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
        intent.putExtra("CLINIC_ID", String.valueOf(selectedID));
        startActivity(intent);

        // startActivity(new Intent(MapsActivity.this,ProfileActivity.class));
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                getClinksLocations();

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }
        }
    };

    private void getClinksLocations() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GET_UPDATE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                parseData(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY ERROR", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    private void parseData(JSONObject response) {
        try {
            JSONArray data = response.getJSONArray("data");
            for (int i = 0; i < 3; i++) {
                Clink clink = new Clink();
                JSONObject item = data.getJSONObject(i);
                clink.setId(item.getInt("id"))
                        .setName(item.getString("name"))
                        .setAddress(item.getString("address"))
                        .setLatitude(item.getString("latitude"))
                        .setLongitude(item.getString("longitude"))
                        .setPhone_number(item.getString("phone_number"))
                        .setStatus(Boolean.parseBoolean(item.getString("status")))
                        .setVisible(Boolean.parseBoolean(item.getString("visible")));
                //ظظ  .setVisible(item.getBoolean("visible"));
                clinks.add(clink);
            }
            drawClinkMarksMarks(clinks);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void drawClinkMarksMarks(List<Clink> clinks) {

        for (int i = 0; i < clinks.size(); i++) {
            final Clink clink = clinks.get(i);
            if (checkForDistance(clink)) {

                //Place current location marker
                LatLng latLng = new LatLng(clink.getLongitude(), clink.getLatitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(clink.getName());
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                mClinkLocationMarker = mGoogleMap.addMarker(markerOptions);


                mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker m) {

                        Clink clink1 = findUsingIterator(m.getTitle());

                        TextView tvName = findViewById(R.id.bsClinkName);
                        TextView tvAddress = findViewById(R.id.bsClinkAddress);
                        TextView tvDistance = findViewById(R.id.bsClinkDistance);
                        tvName.setText(clink1.getName());
                        tvAddress.setText(clink1.getAddress());
                        tvDistance.setText("" + calculateDistance(clink1) + "KM");
                        selectedID = clink1.getId();
                        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);
                        return true;
                    }
                });
            }
        }
    }

    private boolean checkForDistance(Clink clink) {
        double distance = calculateDistance(clink);
        return distance >= 100;
    }

    private double calculateDistance(Clink clink) {
        double theta = mLastLocation.getLongitude() - clink.getLongitude();
        double dist = Math.sin(deg2rad(mLastLocation.getLatitude()))
                * Math.sin(deg2rad(clink.getLatitude()))
                + Math.cos(deg2rad(mLastLocation.getLatitude()))
                * Math.cos(deg2rad(clink.getLatitude()))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
        //  return 0;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public Clink findUsingIterator(String name) {
        Iterator<Clink> iterator = clinks.iterator();
        while (iterator.hasNext()) {
            Clink clink = iterator.next();
            if (clink.getName().equals(name)) {
                return clink;
            }
        }
        return null;
    }
}