package at.htl.grieskirchen.weatherapp;

import android.os.AsyncTask;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationTask extends AsyncTask<String, Void, String> {



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        /* Showing the ProgressBar, Making the main design GONE */
        //findViewById(R.id.loader).setVisibility(View.VISIBLE);
        //findViewById(R.id.mainContainer).setVisibility(View.GONE);
        //findViewById(R.id.errorText).setVisibility(View.GONE);
    }

    protected String doInBackground(String... args) {
        return HttpRequest.excuteGet("https://eu1.locationiq.com/v1/reverse.php?key=344da6fad40201&lat=" + args[0] + "&lon=" + args[1] + "&format=json");
    }

    @Override
    protected void onPostExecute(String result) {

    }
}

