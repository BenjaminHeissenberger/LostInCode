package at.htl.grieskirchen.weatherapp;

import android.os.AsyncTask;

import com.androdocs.httprequest.HttpRequest;

public class SimpleWeatherTask extends AsyncTask<String, Void, String> {
    String API = "6e7126dd0d4a9044c59cdc3013a08c0f";
    @Override
    protected String doInBackground(String... strings) {
        return HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/forecast?lat=" + strings[0] +  "&lon=" + strings[1] + "&units=metric&appid=" + API);
    }
}
