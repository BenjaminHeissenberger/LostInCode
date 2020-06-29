package at.htl.grieskirchen.weatherapp;

import java.util.List;

public class WeatherPlacesPerUserListObject {
    List<WeatherPlacesPerUser>list;

    public List<WeatherPlacesPerUser> getList() {
        return list;
    }

    public WeatherPlacesPerUserListObject(List<WeatherPlacesPerUser> list) {
        this.list = list;
    }
}
