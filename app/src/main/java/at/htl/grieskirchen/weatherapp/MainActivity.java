package at.htl.grieskirchen.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Watchable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main";
    public List<Weather>weatherList = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;
    String CITY;
    String API = "6e7126dd0d4a9044c59cdc3013a08c0f";
    private ViewPager mSlideViewPager;
    LocationManager locationManager;
    private static final int RQ_ACCESS_FINE_LOCATION = 123;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private  String filename = "cities";
    ImageButton btn_add;
    private double newlot;
    private double newlat;
    public WeatherTask weatherTask;
private ImageButton btn_settings;
    private static MainActivity sInstance = null;
    TextView [] dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        CITY = readin();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sInstance = this;



        sliderAdapter = new SliderAdapter(weatherList, this);
        mDotLayout = findViewById(R.id.dotsLayout);
        mSlideViewPager = findViewById(R.id.slideViewPager);
        mSlideViewPager.setAdapter(sliderAdapter);
        dotIndicator(0);

        mSlideViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout ll = findViewById(R.id.linearlayout);
                if(backround(weatherList.get(position))) {

                    ll.setBackgroundResource(R.drawable.gradient_day_nice);
                }
                else{
                    ll.setBackgroundResource(R.drawable.gradient_day_ugly);
                }
                if(weatherList.get(position).getSunset()-60*60*24 < java.time.Instant.now().getEpochSecond() && (weatherList.get(position).getSunrise()) > java.time.Instant.now().getEpochSecond()){
                    ll.setBackgroundResource(R.drawable.gradient_night);
                }
            }

            @Override
            public void onPageSelected(int position) {
                LinearLayout ll = findViewById(R.id.linearlayout);
                if(backround(weatherList.get(position))) {

                    ll.setBackgroundResource(R.drawable.gradient_day_nice);
                }
                else{
                    ll.setBackgroundResource(R.drawable.gradient_day_ugly);
                }
                if(weatherList.get(position).getSunset()-60*60*24 < java.time.Instant.now().getEpochSecond() && (weatherList.get(position).getSunrise()) > java.time.Instant.now().getEpochSecond()){
                    ll.setBackgroundResource(R.drawable.gradient_night);
                }

                dotIndicator(position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }});

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            add(false);


            }
        });
        checkPermissionGPS();
//btn_settings.findViewById(R.id.btn_settings);
//btn_settings.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//    }
//});
     //   Toolbar toolbar = findViewById(R.id.toolbar);
   //     setSupportActionBar(toolbar);
//       Button side_menu_Button = findViewById(R.id.main_side_button);
//       side_menu_Button.setOnClickListener(view -> {
//          ;
//           Toast.makeText(MainActivity.getInstance(), "WIESO,", Toast.LENGTH_LONG ).show();
//       });

        ImageButton delete = findViewById(R.id.btn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if(weatherList.size() > 0){
    weatherList.remove(mSlideViewPager.getCurrentItem());
    sliderAdapter.notifyDataSetChanged();

}
                Log.d(TAG, "onClick: removed " + mSlideViewPager.getCurrentItem());
            }
        });

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(weatherList.size() > 0 ) {
                    weatherTask = new WeatherTask(weatherList.get(mSlideViewPager.getCurrentItem()).getAddress(),sliderAdapter,weatherList,mSlideViewPager.getCurrentItem());
                    weatherTask.execute(String.valueOf(newlat),String.valueOf(newlot));

                }


                refreshLayout.setRefreshing(false);




            }
        });

  }

    public static MainActivity getInstance() {
        return sInstance;
    }


    private void aktuelisierdieduhuan() {

    }

    public void write(String input)
    {

        try {
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE );
            PrintWriter out = new PrintWriter(new OutputStreamWriter(fos));
            out.println(input);
            out.flush();
            out.close();
        } catch (FileNotFoundException exp) {
            Log.d(TAG, exp.getStackTrace().toString());
        }


        }
    public String readin(){
        String mFileContent = "vienna,AT";
        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = in.readLine()) != null ) {
                buffer.append(line);
            }
            mFileContent = buffer.toString();
            in.close();
        } catch (IOException exp) {
            Log.d(TAG, exp.getStackTrace().toString());
        }
        return mFileContent;
    }
    public void dotIndicator(int position){
        mDotLayout.removeAllViews();
        dots = new TextView[weatherList.size()];
        for(int i =0; i < dots.length; i++){
            dots[i] =  new TextView(getApplicationContext());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.base));
            mDotLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    public boolean backround(Weather w){
        if(w.getWeatherDescription().contains("clear")||w.getWeatherDescription().contains("light")||w.getWeatherDescription().contains("few")||w.getWeatherDescription().contains("moderate")){
            return true;
        }
            return false;

    }

    public void add(boolean refresh)
    {
        if(refresh == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getInstance());
            builder.setTitle("Enter City");

// Set up the input
            final EditText input = new EditText(MainActivity.getInstance());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CITY = input.getText().toString();

                    GeoLocation geoLocation = new GeoLocation();
                    geoLocation.getAddress(CITY, getApplicationContext(), new GeoHandler());


                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }

    }


    private class GeoHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String address;
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    address = bundle.getString("adress");
                    String[] latlon = address.split(";");
                    newlat = Double.valueOf(latlon[0]);
                    newlot = Double.valueOf(latlon[1]);
                    weatherTask = new WeatherTask(CITY,sliderAdapter,weatherList,-1);
                    weatherTask.execute(String.valueOf(newlat),String.valueOf(newlot));
                    Log.d(TAG, "handleMessage: Lat" + newlat + " lot " + newlot  );



                    break;

                default:
                    address = null;
                            break;


            }
        }
    }

    private void checkPermissionGPS() {
        Log.d(TAG, "checkPermissionGPS");
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ permission },
                    RQ_ACCESS_FINE_LOCATION );
        } else {
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);

            Location location = locationManager.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER);
            int i = 0;
            double lon = location.getLongitude();
            double lat = location.getLatitude();
            String sJson = "";
            LocationTask lt = new LocationTask();
            lt.execute(String.valueOf(lat),String.valueOf(lon));
            try {
                String response = lt.get();
                JSONObject jsonObject = new JSONObject(response);

                try{CITY = jsonObject.getJSONObject("address").getString("city");}catch (Exception e){}
                try{CITY = jsonObject.getJSONObject("address").getString("town");}catch (Exception e){}
                try{CITY = jsonObject.getJSONObject("address").getString("village");}catch (Exception e){}

                GeoLocation geoLocation = new GeoLocation();
                geoLocation.getAddress(CITY, getApplicationContext(), new GeoHandler() );
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != RQ_ACCESS_FINE_LOCATION) return;
        if (grantResults.length > 0 &&
                grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Permission ACCESS_FINE_LOCATION denied!",Toast.LENGTH_SHORT);
        } else {
            gpsGranted();
        }
    }

    private void gpsGranted() {
        checkPermissionGPS();
    }

}
