package at.htl.grieskirchen.weatherapp;

import android.app.Activity;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailedView extends Activity {

    private TextView mTextView;
    ListView listView;
    List<SimpleWeather>simpleWeathers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        String json = getIntent().getExtras().getString("json");
        listView = findViewById(R.id.lv);
        simpleWeathers = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray list = null;
            for(int i = 2; i < 40;i = i + 2) {
                list = jsonObject.getJSONArray("list");
                JSONObject item = list.getJSONObject(i);
                String desc = item.getJSONArray("weather").getJSONObject(0).get("description").toString();
                JSONObject main = item.getJSONObject("main");
                String temp = Math.round(Double.parseDouble(main.get("temp").toString()))+"°C";
                String time = item.getString("dt_txt");
                SimpleDateFormat sdfToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = null;
                try {
                    date1 = sdfToDate.parse(time);
                    
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM. HH:mm");
                simpleWeathers.add(new SimpleWeather(sdf.format(date1),desc,temp));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Lv_Adapter lv_adapter = new Lv_Adapter(this,R.layout.lv_item_layout,simpleWeathers);
        listView.setAdapter(lv_adapter);
    }
}