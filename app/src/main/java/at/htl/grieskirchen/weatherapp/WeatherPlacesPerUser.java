package at.htl.grieskirchen.weatherapp;

import java.util.List;

public class WeatherPlacesPerUser {
    private final String uid;
    public List<String> weatherplaces;


    public void setWeatherplaces(List<String> weatherplaces) {
        this.weatherplaces = weatherplaces;
    }

    public String getUid() {
        return uid;
    }

    public List<String> getWeatherplaces() {
        return weatherplaces;
    }

    public WeatherPlacesPerUser(String uid, List<String> weatherplaces) {
        this.uid = uid;
        this.weatherplaces = weatherplaces;
    }
}
