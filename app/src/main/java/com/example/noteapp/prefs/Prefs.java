package com.example.noteapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private final SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }

    public void saveIsShown(){
        preferences.edit().putBoolean("isShown",true).apply();
    }

    public boolean isShown(){

        return preferences.getBoolean("isShown",false);
    }
    public void  deletedShownElement(){

        preferences.edit().remove("isShown").apply();
    }
}
