package at.htl.grieskirchen.weatherapp;

public class Weather {
    private String address;
    private String updatedTime;
    private String temp;
    private String tempMin;
    private String tempMax;
    private String pressure;
    private String humidity;
    private long sunrise;
    private long sunset;
    private String windSpeed;
    private String weatherDescription;
    private double lon;
    private double lat;

    public Weather(String address, String updatedTime, String temp, String tempMin, String tempMax, String pressure, String humidity, long sunrise, long sunset, String windSpeed, String weatherDescription, double lon, double let) {
        this.address = address;
        this.updatedTime = updatedTime;
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.windSpeed = windSpeed;
        this.weatherDescription = weatherDescription;
        this.lat = lat;
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public String getTemp() {
        return temp;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
