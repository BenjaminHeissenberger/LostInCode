package at.htl.grieskirchen.weatherapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Main";
    public List<Weather>weatherList = new ArrayList<>();
    String CITY = "vienna,at";
    String API = "6e7126dd0d4a9044c59cdc3013a08c0f";
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private  String filename = "cities";
    private static MainActivity sInstance = null;
    TextView [] dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        CITY = readin();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sInstance = this;
        weatherList.add(new Weather("a","a","a","a","a","a","a",1,1,"a","a"));
        weatherList.add(new Weather("b","b","b","b","b","b","b",2,2,"b","b"));

        weatherList.add(new Weather("e","e","e","e","e","e","r",1,1, "w","w"));
        weatherTask wt = new weatherTask();
        wt.execute();
        try {
            wt.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sliderAdapter = new SliderAdapter(weatherList, this);
        mDotLayout = findViewById(R.id.dotsLayout);
        mSlideViewPager = findViewById(R.id.slideViewPager);
        mSlideViewPager.setAdapter(sliderAdapter);
        dotIndicator(0);

        mSlideViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }});}

    public static MainActivity getInstance() {
        return sInstance;
    }
    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Showing the ProgressBar, Making the main design GONE */
            //findViewById(R.id.loader).setVisibility(View.VISIBLE);
            //findViewById(R.id.mainContainer).setVisibility(View.GONE);
            //findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");
//                addressTxt = findViewById(R.id.address);
//                addressTxt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getInstance());
//                            builder.setTitle("Enter Text");
//
//// Set up the input
//                            final EditText input = new EditText(MainActivity.getInstance());
//// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
//                            builder.setView(input);
//
//// Set up the buttons
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    CITY = input.getText().toString();
//                                    write(CITY);
//                                    finish();
//                                    startActivity(getIntent());
//
//                                }
//                            });
//                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//                            });
//
//                            builder.show();
//
//
//                    }
//                });
//                updated_atTxt = findViewById(R.id.updated_at);
//                statusTxt = findViewById(R.id.status);
//                tempTxt = findViewById(R.id.temp);
//                temp_minTxt = findViewById(R.id.temp_min);
//                temp_maxTxt = findViewById(R.id.temp_max);
//                sunriseTxt = findViewById(R.id.sunrise);
//                sunsetTxt = findViewById(R.id.sunset);
//                windTxt = findViewById(R.id.wind);
//                pressureTxt = findViewById(R.id.pressure);
//                humidityTxt = findViewById(R.id.humidity);
//
//                addressTxt.setText(address);
//                updated_atTxt.setText(updatedAtText);
//                statusTxt.setText(weatherDescription.toUpperCase());
//                tempTxt.setText(temp);
//                temp_minTxt.setText(list.get(position).getTempMin());
//                temp_maxTxt.setText(list.get(position).getTempMax());
//                sunriseTxt.setText(new SimpleDateFormat("hh:mm").format(new Date(list.get(position).getSunrise() * 1000)));
//                sunsetTxt.setText(new SimpleDateFormat("hh:mm").format(new Date(list.get(position).getSunset() * 1000)));
//                windTxt.setText(list.get(position).getWindSpeed());
//                pressureTxt.setText(list.get(position).getPressure());
//                humidityTxt.setText(list.get(position).getHumidity());
               weatherList.add(new Weather(address, updatedAtText, temp, tempMin, tempMax, pressure, humidity, sunrise, sunset,windSpeed, weatherDescription));
                sliderAdapter.notifyDataSetChanged();
                /* Views populated, Hiding the loader, Showing the main design */
                //findViewById(R.id.loader).setVisibility(View.GONE);
                //findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                //findViewById(R.id.loader).setVisibility(View.GONE);
                //findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }

        }
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
        String mFileContent = "0";
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
            dots[i].setTextColor(getResources().getColor(R.color.aluminum));
            mDotLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }
}
