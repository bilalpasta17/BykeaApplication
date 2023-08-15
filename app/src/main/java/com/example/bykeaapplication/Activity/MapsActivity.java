package com.example.bykeaapplication.Activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bykeaapplication.R;
import com.example.bykeaapplication.databinding.ActivityMapsBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Random;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private ActivityMapsBinding binding;
    private final int FINE_PERMISSION_CODE = 1;
    private Location currentLocation;
    private TextView drop;

    private Circle rangeCircle;
    private ValueAnimator circleAnimator;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private GoogleMap googleMap;
    private static final int NUM_RANDOM_MARKERS = 10; // Number of random markers to generate
    private static final int MAX_DISTANCE = 300; // Maximum distance in meters from the user's current location
    private Location mainLocation;
    private Marker[] markers;

    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imageView=findViewById(R.id.imageView8);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        clickListeners();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.btnDropOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.parent.setVisibility(View.VISIBLE);

            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.parent.setVisibility(View.GONE);
            }
        });
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyCVadrNRSVOWOyXNTSWXi-kCM6O8pqz2gk");
        }
        PlacesClient placesClient = Places.createClient(this);
        final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autoComplete);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng latLng = place.getLatLng();
                String placeName = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

    }

    private void clickListeners() {
        binding.car.setOnClickListener(this);
        binding.rickshaw.setOnClickListener(this);
        binding.car1.setOnClickListener(this);
        binding.ride.setOnClickListener(this);
    }

    private void getLastLocation() {
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        // Check and request location permission
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Enable My Location button and show the current location on the map
        googleMap.setMyLocationEnabled(true);

        // Add a marker at the user's current location
        getLastKnownLocation();

    }

    private void addCurrentLocationMarker(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Add a marker at the user's current location with a custom marker icon
//        MarkerOptions markerOptions = new MarkerOptions()
//                .position(new LatLng(latitude, longitude))
//                .title("Your Location")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location_icon)); // Use your custom drawable resource for the current location icon
//        googleMap.addMarker(markerOptions);

        // Add a circle around the current location to represent the range
    }

    // Get the user's last known location and add a marker
    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        mainLocation = location;
                        // Get user's current location coordinates
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        Random random = new Random();
                        for (int i = 0; i < NUM_RANDOM_MARKERS; i++) {
                            // Generate random offsets within the maximum distance
                            double latOffset = (random.nextDouble() * 2 - 1) * (MAX_DISTANCE / 111000.0); // 1 degree of latitude is approximately 111 km
                            double lngOffset = (random.nextDouble() * 2 - 1) * (MAX_DISTANCE / (111000.0 * Math.cos(Math.toRadians(latitude))));

                            // Calculate the random latitude and longitude
                            double randomLatitude = latitude + latOffset;
                            double randomLongitude = longitude + lngOffset;

                            LatLng latLng = new LatLng(randomLatitude, randomLongitude);

//                       / Add a marker at the random location with custom marker icon
//                            googleMap.addMarker(new MarkerOptions()
//                                    .position(latLng)
//                                    .title("Custom Marker " + (i + 1))
//                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike))); // Use your custom drawable resource here
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("End").icon(bitmapDescriptorFromVector(MapsActivity.this, R.drawable.bike));

                            Marker mMarker = googleMap.addMarker(markerOptions);
                            CircleOptions circleOptions = new CircleOptions()
                                    .center(new LatLng(latitude, longitude))
                                    .radius(MAX_DISTANCE) // Radius in meters
                                    .strokeColor(Color.BLUE) // Color of the circle's border
                                    .fillColor(Color.parseColor("#ADD8E6")); // Color of the circle's fill with alpha (transparency)


                            rangeCircle = googleMap.addCircle(circleOptions);
                            startCircleAnimation();

                            // Move the camera to the user's current location
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        }
                    }
                });
    }

    private void addMarker(Location location, int x) {
        if (location != null) {
            // Get user's current location coordinates
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Random random = new Random();
            LatLng latLng = null;
            int drawable = -1;
            for (int i = 0; i < NUM_RANDOM_MARKERS; i++) {
                // Generate random offsets within the maximum distance
                double latOffset = (random.nextDouble() * 2 - 1) * (400 / 111000.0); // 1 degree of latitude is approximately 111 km
                double lngOffset = (random.nextDouble() * 2 - 1) * (350 / (111000.0 * Math.cos(Math.toRadians(latitude))));

                // Calculate the random latitude and longitude
                double randomLatitude = latitude + latOffset;
                double randomLongitude = longitude + lngOffset;

                latLng = new LatLng(randomLatitude, randomLongitude);

//                       / Add a marker at the random location with custom marker icon
//                            googleMap.addMarker(new MarkerOptions()
//                                    .position(latLng)
//                                    .title("Custom Marker " + (i + 1))
//                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike))); // Use your custom drawable resource here
                drawable = -1;

                switch (x) {
                    case 1: {
                        //Rickshaw
                        drawable = R.drawable.newrickshaw;
                        break;
                    }

                    case 2: {
                        //Car
                        drawable = R.drawable.newcarac;
                        break;
                    }
                    case 3: {
                        //Bike
                        drawable = R.drawable.bike;
                        break;
                    }
                    case 4: {
                        //car1
                        drawable = R.drawable.newcarmini;
                        break;
                    }


                }


            }
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("End").icon(bitmapDescriptorFromVector(MapsActivity.this, drawable));

            Marker mMarker = googleMap.addMarker(markerOptions);
            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(latitude, longitude))
                    .radius(MAX_DISTANCE) // Radius in meters
                    .strokeColor(Color.BLUE) // Color of the circle's border
                    .fillColor(Color.parseColor("#ADD8E6")); // Color of the circle's fill with alpha (transparency)


            rangeCircle = googleMap.addCircle(circleOptions);
            startCircleAnimation();

            // Move the camera to the user's current location
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }


    private void startCircleAnimation() {
        if (circleAnimator == null) {
            circleAnimator = ValueAnimator.ofInt(0, MAX_DISTANCE * 2); // Double the range for pulsating effect
            circleAnimator.setDuration(1500); // Duration of the animation in milliseconds (adjust as needed)
            circleAnimator.setRepeatCount(ValueAnimator.INFINITE); // Infinite repeat
            circleAnimator.setRepeatMode(ValueAnimator.RESTART);
            circleAnimator.addUpdateListener(valueAnimator -> {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                rangeCircle.setRadius(animatedValue);
            });
        }
        circleAnimator.start();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vector) {

        Drawable drawable = ContextCompat.getDrawable(context, vector);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }

    // Handle the result of the location permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, enable My Location button and show the current location on the map
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // Add a marker at the user's current location
                getLastKnownLocation();
            }
        }


    }
    @Override
    public void onClick(View v) {
        int x = -1;
        if (v.getId() == R.id.ride) {
            x = 1;
        } else if (v.getId() == R.id.rickshaw) {
            x = 2;
        } else if (v.getId() == R.id.car) {
            x = 3;
        } else if (v.getId() == R.id.car1) {
            x = 4;
        }

        if (x != -1) {
//
//            for (Marker marker : markers) {
//                int drawable = (int) marker.getTag();
//                if (drawable == x) {
//                    marker.setVisible(true);
//                } else {
//                    marker.setVisible(false);
//                }
//            }
//
            addMarker(mainLocation, x);
        }
    }

}

/*
package com.example.bykeaapplication.Activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bykeaapplication.R;
import com.example.bykeaapplication.databinding.ActivityMapsBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private ActivityMapsBinding binding;
    private final int FINE_PERMISSION_CODE = 1;
    private Location currentLocation;
    private TextView drop;

    private Circle rangeCircle;
    private ValueAnimator circleAnimator;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private GoogleMap googleMap;
    private static final int NUM_RANDOM_MARKERS = 10; // Number of random markers to generate
    private static final int MAX_DISTANCE = 300; // Maximum distance in meters from the user's current location
    private Location mainLocation;
    private Marker[] markers;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize imageView and set click listener to handle back press
        imageView = findViewById(R.id.imageView8);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Set up click listeners for various buttons
        clickListeners();

        // Initialize fusedLocationClient for location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Retrieve the SupportMapFragment and get notified when the map is ready
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Set up button click listeners for DropOff and Back buttons
        binding.btnDropOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.parent.setVisibility(View.VISIBLE); // Show the drop-off UI
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.parent.setVisibility(View.GONE); // Hide the drop-off UI
            }
        });

        // Initialize Places API client for location autocomplete
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "YOUR_API_KEY");
        }
        PlacesClient placesClient = Places.createClient(this);
        final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autoComplete);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng latLng = place.getLatLng();
                String placeName = place.getName();
                // Handle the selected place
            }

            @Override
            public void onError(@NonNull Status status) {
                // Handle errors in place selection
            }
        });
    }

    // Set up click listeners for various buttons
    private void clickListeners() {
        binding.car.setOnClickListener(this);
        binding.rickshaw.setOnClickListener(this);
        binding.car1.setOnClickListener(this);
        binding.ride.setOnClickListener(this);
    }

    // Retrieve the last known location of the user
    private void getLastLocation() {
        // Implement the logic to retrieve the last known location
    }

    // Callback when the map is ready
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        // Check and request location permission
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Enable My Location button and show the current location on the map
        googleMap.setMyLocationEnabled(true);

        // Add a marker at the user's current location
        getLastKnownLocation();
    }

    // Add a marker at the current location
    private void addCurrentLocationMarker(@NonNull Location location) {
        // Implement the logic to add a marker at the current location
    }

    // Get the user's last known location and add markers
    private void getLastKnownLocation() {
        // Implement the logic to retrieve and use the user's last known location
    }

    // Add markers based on location and selected vehicle type
    private void addMarker(Location location, int x) {
        // Implement the logic to add markers based on location and vehicle type
    }

    // Start pulsating animation for the range circle
    private void startCircleAnimation() {
        // Implement the logic to start the pulsating animation for the range circle
    }

    // Convert a vector drawable to a BitmapDescriptor
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vector) {
        // Implement the logic to convert a vector drawable to a BitmapDescriptor
    }

    // Handle the result of the location permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Implement the logic to handle the location permission request result
    }

    // Handle button clicks for vehicle selection
    @Override
    public void onClick(View v) {
        int x = -1;
        if (v.getId() == R.id.ride) {
            x = 1;
        } else if (v.getId() == R.id.rickshaw) {
            x = 2;
        } else if (v.getId() == R.id.car) {
            x = 3;
        } else if (v.getId() == R.id.car1) {
            x = 4;
        }

        if (x != -1) {
            // Handle vehicle marker addition based on selected type
            addMarker(mainLocation, x);
        }
    }
}




 */

