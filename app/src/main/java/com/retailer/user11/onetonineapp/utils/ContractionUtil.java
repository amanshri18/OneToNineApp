package com.retailer.user11.onetonineapp.utils;

import android.content.Context;

import com.retailer.user11.onetonineapp.R;

import java.util.Formatter;
import java.util.Locale;


public final class ContractionUtil {

    public static String stringForTime(Context ctx, int timeSec) {
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder, Locale.getDefault());
        int seconds = timeSec % 60;
        int minutes = (timeSec / 60) % 60;
        int hours = timeSec / 3600;
        builder.setLength(0);
        if (hours > 0) {
            return formatter.format(ctx.getString(R.string.snackbar_time_format_h), new Object[]{Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString();
        } else if (minutes > 0) {
            return formatter.format(ctx.getString(R.string.snackbar_time_format_m), new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString();
        } else {
            return formatter.format(ctx.getString(R.string.snackbar_time_format_s), new Object[]{Integer.valueOf(seconds)}).toString();
        }
    }
}
