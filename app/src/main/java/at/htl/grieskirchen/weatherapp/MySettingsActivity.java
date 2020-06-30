package at.htl.grieskirchen.weatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MySettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new MySettingsFragment())
                .commit();
    }

}
