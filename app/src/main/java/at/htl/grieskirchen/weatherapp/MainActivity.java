package at.htl.grieskirchen.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public List<Weather>weatherList = new ArrayList<>();
    String CITY = "dhaka,bd";
    String API = "6e7126dd0d4a9044c59cdc3013a08c0f";
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private static MainActivity sInstance = null;
    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        sInstance = this;
        //mSlideViewPager = findViewById(R.id.slideViewPager);

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
        //sliderAdapter = new SliderAdapter(this);

        //mSlideViewPager.setAdapter(sliderAdapter);
    }

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
                addressTxt = findViewById(R.id.address);
                updated_atTxt = findViewById(R.id.updated_at);
                statusTxt = findViewById(R.id.status);
                tempTxt = findViewById(R.id.temp);
                temp_minTxt = findViewById(R.id.temp_min);
                temp_maxTxt = findViewById(R.id.temp_max);
                sunriseTxt = findViewById(R.id.sunrise);
                sunsetTxt = findViewById(R.id.sunset);
                windTxt = findViewById(R.id.wind);
                pressureTxt = findViewById(R.id.pressure);
                humidityTxt = findViewById(R.id.humidity);

                addressTxt.setText(address);
                updated_atTxt.setText(updatedAtText);
                statusTxt.setText(weatherDescription.toUpperCase());
                tempTxt.setText(temp);
//                temp_minTxt.setText(list.get(position).getTempMin());
//                temp_maxTxt.setText(list.get(position).getTempMax());
//                sunriseTxt.setText(new SimpleDateFormat("hh:mm").format(new Date(list.get(position).getSunrise() * 1000)));
//                sunsetTxt.setText(new SimpleDateFormat("hh:mm").format(new Date(list.get(position).getSunset() * 1000)));
//                windTxt.setText(list.get(position).getWindSpeed());
//                pressureTxt.setText(list.get(position).getPressure());
//                humidityTxt.setText(list.get(position).getHumidity());
               weatherList.add(new Weather(address, updatedAtText, temp, tempMin, tempMax, pressure, humidity, sunrise, sunset,windSpeed, weatherDescription));

                /* Views populated, Hiding the loader, Showing the main design */
                //findViewById(R.id.loader).setVisibility(View.GONE);
                //findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                //findViewById(R.id.loader).setVisibility(View.GONE);
                //findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }

        }
    }
}
