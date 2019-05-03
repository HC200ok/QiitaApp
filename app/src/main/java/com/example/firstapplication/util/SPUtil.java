package com.example.firstapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {
    public static boolean saveToken (Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        boolean result = editor.commit();
        return result;
    }

    public static String getToken (Context context) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        return token;
    }

    public static boolean removeToken (Context context) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("token");
        boolean result = editor.commit();
        return result;
    }
}
