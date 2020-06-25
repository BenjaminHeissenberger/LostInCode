package at.htl.grieskirchen.weatherapp;

import android.os.AsyncTask;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class WeatherTask extends AsyncTask<String, Void, String> {
    String CITY;
    String API = "6e7126dd0d4a9044c59cdc3013a08c0f";
    SliderAdapter sliderAdapter;
    List<Weather>weatherList;
    int isrefresh;
    double lon;
    double lat;

    public WeatherTask(String CITY, SliderAdapter sliderAdapter, List<Weather> weatherList, int isrefresh) {
        this.CITY = CITY;
        this.sliderAdapter = sliderAdapter;
        this.weatherList = weatherList;
        this.isrefresh = isrefresh;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        /* Showing the ProgressBar, Making the main design GONE */
        //findViewById(R.id.loader).setVisibility(View.VISIBLE);
        //findViewById(R.id.mainContainer).setVisibility(View.GONE);
        //findViewById(R.id.errorText).setVisibility(View.GONE);
    }

    protected String doInBackground(String... args) {
        if(isrefresh > 0)
        {
      args[0] = String.valueOf(weatherList.get(isrefresh).getLat());
            args[1] = String.valueOf(weatherList.get(isrefresh).getLon());
        }
        String response = HttpRequest.excuteGet(
                "https://api.openweathermap.org/data/2.5/weather?lat=" + args[0] +  "&lon=" + args[1] + "&units=metric&appid=" + API);
       lat = Double.valueOf(args[1]);
        lon = Double.valueOf(args[1]);
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
            String updatedAtText = "Updated at: " + new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY).format(new Date(updatedAt * 1000));
            String temp = Math.round(Double.parseDouble(main.getString("temp"))) + "°C";
            String tempMin = "Min Temp: " + Math.round(Double.parseDouble(main.getString("temp_min"))) + "°C";
            String tempMax = "Max Temp: " + Math.round(Double.parseDouble(main.getString("temp_max"))) + "°C";
            String pressure = main.getString("pressure")+ " hPa";
            String humidity = main.getString("humidity")+ " %";

            Long sunrise = sys.getLong("sunrise");
            Long sunset = sys.getLong("sunset");
            String windSpeed = wind.getString("speed")+ " m/s";
            String weatherDescription = weather.getString("description");

            String address = CITY;
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

            if(isrefresh >= 0)
            {
                    weatherList.set(isrefresh, new Weather(address, updatedAtText, temp, tempMin, tempMax, pressure, humidity, sunrise, sunset,windSpeed, weatherDescription,lon,lat));
            }
            else
            {
                weatherList.add(new Weather(address, updatedAtText, temp, tempMin, tempMax, pressure, humidity, sunrise, sunset,windSpeed, weatherDescription,lon,lat ));

            }

            sliderAdapter.notifyDataSetChanged();


//                                    startActivity(getIntent());

            /* Views populated, Hiding the loader, Showing the main design */
            //findViewById(R.id.loader).setVisibility(View.GONE);
            //findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


        } catch (JSONException e) {
            //findViewById(R.id.loader).setVisibility(View.GONE);
            //findViewById(R.id.errorText).setVisibility(View.VISIBLE);
        }

    }
}
