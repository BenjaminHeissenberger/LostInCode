package at.htl.grieskirchen.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

import android.content.DialogInterface;
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

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main";
    public List<Weather>weatherList = new ArrayList<>();
    String CITY;
    String API = "6e7126dd0d4a9044c59cdc3013a08c0f";
    private ViewPager mSlideViewPager;
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

            add();


            }
        });

//btn_settings.findViewById(R.id.btn_settings);
//btn_settings.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//    }
//});

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

    public void add()
    {
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
                                   geoLocation.getAddress(CITY, getApplicationContext(), new GeoHandler() );


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
                    weatherTask = new WeatherTask(CITY,sliderAdapter,weatherList);
                    weatherTask.execute(String.valueOf(newlat),String.valueOf(newlot));
                    Log.d(TAG, "handleMessage: Lat" + newlat + " lot " + newlot  );



                    break;

                default:
                    address = null;
                            break;


            }
        }
    }


}
