package com.example.dartswithfriends.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dartswithfriends.Preferences.MySettingsActivity;
import com.example.dartswithfriends.R;

import java.util.Map;

public class PlayDart extends AppCompatActivity {

    private TableLayout screen;

    //    Preferences
    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener;
    private static final int RQ_PREFERENCES = 8764;
    private boolean darkmode;
    private boolean notifications;
    private boolean gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dartspiel);

        screen = findViewById(R.id.right_screen);

        //        Preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        preferencesChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String key) {
                PlayDart.this.preferenceChanged(sharedPrefs, key);
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(preferencesChangeListener);

        darkmode = prefs.getBoolean("darkmode", false);
        notifications = prefs.getBoolean("notes", true);
        gps = prefs.getBoolean("gps", true);

        setDarkMode();
    }

    //    Preferences
    private void preferenceChanged(SharedPreferences sharedPrefs, String key) {
        Map<String, ?> allEntries = sharedPrefs.getAll();
        if(key.equals("darkmode")) {
            darkmode = sharedPrefs.getBoolean(key, false);
            setDarkMode();
        }else if(key.equals("notes")){
            notifications = sharedPrefs.getBoolean(key,true);
        }else if(key.equals("gps")){
            gps = sharedPrefs.getBoolean(key,true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.pref:
                Intent intent = new Intent(this, MySettingsActivity.class);
                startActivityForResult(intent, RQ_PREFERENCES);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //    Darkmode
    private void setDarkMode(){
        if(darkmode) {
            screen.setBackgroundColor(Color.GRAY);
        }else{
            screen.setBackgroundColor(Color.parseColor("#20B451"));
        }
    }
}
