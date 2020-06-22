package at.htl.grieskirchen.weatherapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.List;
import java.util.Locale;


public class GeoLocation {

    public static void getAddress(final String locationAddress, final Context context, final Handler handler) {

        Thread thread = new Thread() {
            @Override
            public void run() {

                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
        try{
                List addressList = geocoder.getFromLocationName(locationAddress, 1);
                if(addressList != null && addressList.size() >0)
                {
                    Address address = (Address) addressList.get(0);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(address.getLatitude()).append(";");
                    stringBuilder.append(address.getLongitude()).append("");
                    result = stringBuilder.toString();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Message message = Message.obtain();
            message.setTarget(handler);
            if (result != null)
            {
             message.what =1;
                Bundle bundle = new Bundle();
                // first lat, secont long
               bundle.putString("adress",result);
               message.setData(bundle);
            }
            message.sendToTarget();
        }


            }

        };
    thread.start();
    }
}
