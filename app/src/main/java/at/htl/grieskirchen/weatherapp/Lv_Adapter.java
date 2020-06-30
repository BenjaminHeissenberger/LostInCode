package at.htl.grieskirchen.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Lv_Adapter extends BaseAdapter {
    private List<SimpleWeather> simpleWeathers = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;

    public Lv_Adapter(Context ctx, int layoutId, List<SimpleWeather> simpleWeathers) {
        this.simpleWeathers = simpleWeathers;
        this.layoutId = layoutId;
        // Um das Layout anzeigen zu können, benötigen wir eine Referenz auf den LayoutInflater
        // diesen erhalten wir mittels getSystemService, müssen das Systemservice allerdings
        // dann in einen LayoutInflater casten.
        this.inflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    // Nun müssen wir dafür sorgen, dass unser Parameter bei den Events die korrekten Werte für
    // id, pos, etc. zurückliefert.

    // Anzahl der vorhandenen Elemente
    @Override
    public int getCount() {
        return simpleWeathers.size();
    }
    // Liefert das Objekt an Position i aus der Liste
    @Override
    public Object getItem(int i) {
        return simpleWeathers.get(i);
    }
    // Liefert die ID des Objekts bei Anwendungen mit Datenbanken
    @Override
    public long getItemId(int i) {
        return 0;
    }
    // Hier erfolg das 'Binding' zwischen Datenquelle und View Komponenten aus dem XML-Layout
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SimpleWeather simpleWeather = simpleWeathers.get(i);
        View listItem = (view == null) ? inflater.inflate(this.layoutId, null) : view;
        ((TextView) listItem.findViewById(R.id.tv_time)).setText(simpleWeather.getDatum());
        ((TextView) listItem.findViewById(R.id.tv_condition)).setText(simpleWeather.getDesc());
        ((TextView) listItem.findViewById(R.id.tv_temperature)).setText(simpleWeather.getTemp());
        return listItem;
    }
}
