package com.app.pinpotha_beta.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class localeHelper {
    private static final String SELECTED_LANGUAGE = "language";

    public static void setLocale(Activity activity, String language) {
        updateResources(activity, language);
    }

    public static void updateResources(Activity activity, String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        SharedPreferences preferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_LANGUAGE, langCode);
        editor.apply();
    }

    public static void loadLocale(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String language = preferences.getString(SELECTED_LANGUAGE, "");
        setLocale(activity, language);
    }

    public static String loadSelectedLocale(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getString(SELECTED_LANGUAGE, "");
    }
}
