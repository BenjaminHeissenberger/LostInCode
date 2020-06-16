package at.htl.grieskirchen.weatherapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private  List<Weather>list;

    public SliderAdapter(List<Weather>list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container,false);

        TextView addressTxt = view.findViewById(R.id.address);
        TextView updated_atTxt = view.findViewById(R.id.updated_at);
        TextView statusTxt = view.findViewById(R.id.status);
        TextView tempTxt = view.findViewById(R.id.temp);
        TextView temp_minTxt = view.findViewById(R.id.temp_min);
        TextView temp_maxTxt = view.findViewById(R.id.temp_max);
        TextView sunriseTxt = view.findViewById(R.id.sunrise);
        TextView sunsetTxt = view.findViewById(R.id.sunset);
        TextView windTxt = view.findViewById(R.id.wind);
        TextView pressureTxt = view.findViewById(R.id.pressure);
        TextView humidityTxt = view.findViewById(R.id.humidity);
        
        addressTxt.setText(list.get(position).getAddress());
        updated_atTxt.setText(list.get(position).getUpdatedTime());
        statusTxt.setText(list.get(position).getWeatherDescription().toUpperCase());
        tempTxt.setText(list.get(position).getTemp());
        temp_minTxt.setText(list.get(position).getTempMin());
        temp_maxTxt.setText(list.get(position).getTempMax());
        sunriseTxt.setText(new SimpleDateFormat("hh:mm").format(new Date(list.get(position).getSunrise() * 1000)));
        sunsetTxt.setText(new SimpleDateFormat("hh:mm").format(new Date(list.get(position).getSunset() * 1000)));
        windTxt.setText(list.get(position).getWindSpeed()+" m/s");
        pressureTxt.setText(list.get(position).getPressure());
        humidityTxt.setText(list.get(position).getHumidity());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout)object);
    }
}
