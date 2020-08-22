package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

class Utils {

    static void sendErrorMessage(Context context, CharSequence message, int duration) {
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
