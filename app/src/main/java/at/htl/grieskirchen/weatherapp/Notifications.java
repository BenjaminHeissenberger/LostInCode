package at.htl.grieskirchen.weatherapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notifications extends Application {

    public static final String CHANNEL_ID = "Weather";

    @Override
    public void onCreate() {
        super.onCreate();

        creatheNotificationChannels();
    }

    private void creatheNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Weather",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Show the weather of the Location");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }
}
