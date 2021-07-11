package com.example.rhodium;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.rhodium.data.model.Parameter;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MapActivity extends AppCompatActivity {
    private Context context;
    private MapView mapView = null;
    private IMapController mapController;
    private MapOverlay[] mapOverlays;
    private MyLocationNewOverlay locationOverlay;
    private CompassOverlay compassOverlay;
    private RotationGestureOverlay rotationGestureOverlay;
    private int[] levelColors;

    private int LEVEL_COUNT = 6;
    private int UPDATE_LOCATION_INTERVAL = 8000;

    public static  GeoPoint lastKnownLocation ;

    private String ipAddress = "8.8.8.8";
    private static final String GET_URL = "http://quera.ir/";
    private static final String Aparat = "http://https://www.aparat.com/";
    private int NUMBER_OF_PACKTETS = 1;
    private int requestCount = 5;
    private ConnectivityManager connectivityManager;
    public static int downSpeed=0 ;
    public static int upSpeed=0 ;
    public int cell_id;

    private void setupMap() {
        this.mapView = (MapView) findViewById(R.id.mapView);
        this.mapView.setTileSource(TileSourceFactory.MAPNIK);
//        this.mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);


        this.mapView.setClickable(true);
        this.mapView.setBuiltInZoomControls(true);
        this.mapView.setMultiTouchControls(true);

        this.mapView.getTileProvider().createTileCache();

        this.mapController = mapView.getController();
        this.mapController.setZoom(16);
        this.mapController.setCenter(new GeoPoint(35.762209, 51.418103));

        this.locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this.context), this.mapView);
        this.locationOverlay.enableMyLocation();
//        this.locationOverlay.enableFollowLocation();
        this.mapView.getOverlays().add(this.locationOverlay);

        this.compassOverlay = new CompassOverlay(this.context, new InternalCompassOrientationProvider(this.context), this.mapView);
        this.compassOverlay.enableCompass();
        this.mapView.getOverlays().add(this.compassOverlay);

        this.rotationGestureOverlay = new RotationGestureOverlay(context, this.mapView);
        this.rotationGestureOverlay.setEnabled(true);
        this.mapView.getOverlays().add(this.rotationGestureOverlay);

//        this.mapOverlays.setFocusItemsOnTap(true);
    }


    public void initializeMarkers() {
        this.mapOverlays = new MapOverlay[this.LEVEL_COUNT];
        Drawable[] markers = new Drawable[this.LEVEL_COUNT];

        this.levelColors = new int[]{
                getResources().getColor(R.color.noSignal),
                getResources().getColor(R.color.veryPoor),
                getResources().getColor(R.color.poor),
                getResources().getColor(R.color.fair),
                getResources().getColor(R.color.good),
                getResources().getColor(R.color.excellent)
        };

        for (int i = 0; i < this.LEVEL_COUNT; i++) {
            Drawable icon = getResources().getDrawable(android.R.drawable.ic_menu_mylocation);
            markers[i] = icon.mutate();
            markers[i].setColorFilter(levelColors[i], PorterDuff.Mode.SRC_ATOP);

            int markerWidth = markers[i].getIntrinsicWidth();
            int markerHeight = markers[i].getIntrinsicHeight();
            markers[i].setBounds(0, markerHeight, markerWidth, 0);
        }

        for (int i = 0; i < this.LEVEL_COUNT; i++) {
            this.mapOverlays[i] = new MapOverlay(markers[i]);
            this.mapView.getOverlays().add(this.mapOverlays[i]);
        }
    }


    private void getMarkersFromDatabase() {
        List<Parameter> locations = MainActivity.appDatabase.parameterDao().getAll();

        for (int i = 0; i < locations.size(); i++) {
            double latitude = locations.get(i).getLatitude();
            double longitude = locations.get(i).getLongitude();
            int levelIdx = locations.get(i).getStrength_level();

            GeoPoint point = new GeoPoint(latitude, longitude);

            Marker description = makeDescriptionMarker(point, locations.get(i).toString(), "");
            this.mapView.getOverlays().add(description);

            this.mapOverlays[levelIdx].addItem(point, locations.get(i).toString(), "");
        }
    }


    public void updateMapMarkers() {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //add marker to map and database
                GeoPoint currentLocation = locationOverlay.getMyLocation();
                if (currentLocation != null)
                    if (lastKnownLocation != currentLocation) {
                        lastKnownLocation = currentLocation;

                        addMarker(currentLocation, "", "");
                    }

                handler.postDelayed(this, UPDATE_LOCATION_INTERVAL);
            }
        }, UPDATE_LOCATION_INTERVAL);
    }


    private void addMarker(GeoPoint point, String title, String snippet) {
        mapController.animateTo(lastKnownLocation);

        //add to database
        getCellInfo();
        List<Parameter> ps = MainActivity.appDatabase.parameterDao().loadByIDs(cell_id);
        Parameter p;
        p = ps.get(0);
        Marker description = makeDescriptionMarker(point, p.toString(), "");
        this.mapView.getOverlays().add(description);

        this.mapOverlays[p.getStrength_level()].addItem(point, title, snippet);

    }

    private void makeToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getTimeNow() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(calendar.getTime());

        //makeToast("Current Date and Time:" + formattedDate);
        return formattedDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void measureThroughput() {
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo.isConnected()) {
            NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            downSpeed = nc.getLinkDownstreamBandwidthKbps();
            upSpeed = nc.getLinkUpstreamBandwidthKbps();
            //makeToast("download" + Integer.toString(downSpeed));
            //makeToast("upload" + Integer.toString(upSpeed));
        }
    }

    private double getlatencyAndJitter() {
        double[] latencies = new double[this.requestCount];

        for (int i = 0; i < this.requestCount; ) {
            if ((latencies[i] = pingUrl()) > 0) {
                i++;
            }
        }

        // packet delay variation (PDV)
        double PDV = 0;
        for (int i = 0; i < this.requestCount - 1; i++)
             PDV += Math.abs(latencies[i] - latencies[i + 1]);

        double jitter = PDV / this.requestCount;
        //makeToast("jitter average: " + jitter);
        return jitter;
    }


    public double pingUrl() {
        String pingCommand = "/system/bin/ping -c " + this.NUMBER_OF_PACKTETS + " " + this.ipAddress;
        String latency = "";
        String inputLine;

        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(pingCommand);

            // gets the input stream to get the output of the executed command
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String latencyResult = null;
            while ((inputLine = bufferedReader.readLine()) != null) {
                latencyResult = inputLine;
            }

            //Extracting the average round trip time from the inputLine string
            String[] keyValue = latencyResult.split("=");
            String[] value = keyValue[1].split("/");
            latency = value[1];

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" Exception:" + e);
            return  0;
        }

        //makeToast("latency" + latency);
        return Double.valueOf(latency);
    }


    private double getHttpResponseTime() {
        long startTime = System.currentTimeMillis();
        sendRequest(GET_URL);
        long elapsedTime = System.currentTimeMillis() - startTime;
        String msg = "Total elapsed http request/response time in milliseconds: " + elapsedTime;
        //makeToast(msg);
        return elapsedTime;

    }


    private static void sendRequest(String url_) {
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url           = new URL(url_);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

    }

    private double getMultimediaTime() {
        long startTime = System.currentTimeMillis();
        sendRequest(Aparat);
        long elapsedTime = System.currentTimeMillis() - startTime;
        String msg = "Total elapsed http request/response time in milliseconds: " + elapsedTime;
        //makeToast(msg);
        return elapsedTime;

    }

    private Marker makeDescriptionMarker(GeoPoint point, String title, String snippet) {
        Marker description = new Marker(this.mapView);
        description.setPosition(point);
        description.setTextLabelBackgroundColor(Color.TRANSPARENT);
        description.setTextLabelForegroundColor(Color.TRANSPARENT);
        description.setTextLabelFontSize(60);
        description.setTextIcon("this is a long text");
        description.setTitle(title);

        description.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP);
        return description;
    }



    public void onButtonCurrentLocation() {
        Button backButton = (Button) findViewById(R.id.currentLocation);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapController.animateTo(lastKnownLocation);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        Configuration.getInstance().load(this.context, PreferenceManager.getDefaultSharedPreferences(this.context));
        setContentView(R.layout.activity_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        setupMap();

        initializeMarkers();
        this.locationOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                lastKnownLocation = locationOverlay.getMyLocation();
            }
        });

        getMarkersFromDatabase();

        updateMapMarkers();
        onButtonCurrentLocation();
    }


    @Override
    public void onResume() {
        super.onResume();
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up

        this.locationOverlay.enableMyLocation();
        this.compassOverlay.enableCompass();
    }


    @Override
    public void onPause() {
        super.onPause();
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        mapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up

        this.locationOverlay.disableMyLocation();
        this.compassOverlay.disableCompass();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void getCellInfo() {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

            int plmn = parseInt(tm.getNetworkOperator());

            double latency = pingUrl();
            double jitter = getlatencyAndJitter();
            double http_response_time = getHttpResponseTime();
            double multimedia_QoE = getMultimediaTime();
            String current_time = getTimeNow();
            measureThroughput();

//            double latency = 0;
//            double jitter = 0;
//            double http_response_time = 0;
//            double multimedia_QoE = 0;
//            String current_time = "null";

            List<CellInfo> allCellInfo = tm.getAllCellInfo();

            for (CellInfo cell : allCellInfo) {
                if (cell instanceof CellInfoGsm) {
                    CellSignalStrengthGsm signalStrengthGsm = ((CellInfoGsm) cell).getCellSignalStrength();
                    CellIdentityGsm cellIdentityGsm = ((CellInfoGsm) cell).getCellIdentity();

                    cell_id = cellIdentityGsm.getCid();
                    MainActivity.appDatabase.parameterDao().insert(
                            new Parameter(cellIdentityGsm.getCid(),
                                    MapActivity.lastKnownLocation.getLatitude(), MapActivity.lastKnownLocation.getLongitude(),
                                    'G', plmn, cellIdentityGsm.getLac(), -1,
                                    signalStrengthGsm.getAsuLevel(), signalStrengthGsm.getLevel(), -1, signalStrengthGsm.getLevel(),
                                    latency, jitter, http_response_time, multimedia_QoE, downSpeed, upSpeed, current_time));


                } else if (cell instanceof CellInfoWcdma) {
                    CellSignalStrengthWcdma signalStrengthWcdma = ((CellInfoWcdma) cell).getCellSignalStrength();
                    CellIdentityWcdma cellIdentityWcdma = ((CellInfoWcdma) cell).getCellIdentity();

                    cell_id = cellIdentityWcdma.getCid();
                    MainActivity.appDatabase.parameterDao().insert(
                            new Parameter(cellIdentityWcdma.getCid(),
                                    MapActivity.lastKnownLocation.getLatitude(), MapActivity.lastKnownLocation.getLongitude(),
                                    'U', plmn, cellIdentityWcdma.getLac(), -1,
                                    signalStrengthWcdma.getAsuLevel(), signalStrengthWcdma.getLevel(), -1, signalStrengthWcdma.getLevel(),
                                    latency, jitter, http_response_time, multimedia_QoE, downSpeed, upSpeed, current_time));


                } else if (cell instanceof CellInfoLte) {
                    CellSignalStrengthLte signalStrengthLte = ((CellInfoLte) cell).getCellSignalStrength();
                    CellIdentityLte cellIdentityLte = ((CellInfoLte) cell).getCellIdentity();

                    cell_id = cellIdentityLte.getCi();
                    MainActivity.appDatabase.parameterDao().insert(
                            new Parameter(cellIdentityLte.getCi(),
                                    MapActivity.lastKnownLocation.getLatitude(), MapActivity.lastKnownLocation.getLongitude(),
                                    'L', plmn, cellIdentityLte.getTac(), -1,
                                    signalStrengthLte.getRsrp(), signalStrengthLte.getRsrp(), signalStrengthLte.getRssnr(), signalStrengthLte.getLevel(),
                                    latency, jitter, http_response_time, multimedia_QoE, downSpeed, upSpeed, current_time));


                }
            }
        } catch (Exception e) {
        }
    }

}




