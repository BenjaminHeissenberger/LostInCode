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

    public Weather(String address, String updatedTime, String temp, String tempMin, String tempMax, String pressure, String humidity, long sunrise, long sunset, String windSpeed, String weatherDescription) {
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
}
