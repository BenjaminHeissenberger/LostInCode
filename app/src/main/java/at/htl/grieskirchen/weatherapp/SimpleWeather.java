package at.htl.grieskirchen.weatherapp;

public class SimpleWeather {
    private String datum;
    private String desc;
    private String temp;

    public String getDatum() {
        return datum;
    }

    public String getDesc() {
        return desc;
    }

    public String getTemp() {
        return temp;
    }

    public SimpleWeather(String datum, String desc, String temp) {
        this.datum = datum;
        this.desc = desc;
        this.temp = temp;
    }
}
