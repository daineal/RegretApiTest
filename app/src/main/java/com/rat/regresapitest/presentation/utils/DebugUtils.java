package com.rat.regresapitest.presentation.utils;

import android.widget.Toast;

import com.rat.regresapitest.RatApplication;
import com.rat.regresapitest.BuildConfig;

public class DebugUtils {

    public static void showDebugErrorMessage(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
            Toast.makeText(RatApplication.getInstance(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
