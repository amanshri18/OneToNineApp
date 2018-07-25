package com.retailer.user11.onetonineapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class Utils {

    // Preference files
    public static final String USER_PREFERENCE = "UserPreferences";
    public static final String TAG = "fragment";
    public static final String SETTING_PREFERENCE = "SettingPreferences";
    private static final String DISPLAY_MESSAGE_ACTION = "com.myapp.services.DISPLAY_MESSAGE";
    private static ProgressDialog progressDialog;


    public static void generateHashKey_Local(Context context) {
        // Add code to print out the key hash
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    @SuppressLint("NewApi")
    public static final void recreateActivityCompat(final Activity a) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            a.recreate();
        } else {
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finish();
            a.overridePendingTransition(0, 0);
            a.startActivity(intent);
            a.overridePendingTransition(0, 0);
        }
    }

    public static String getKeyHash_GooglePlay(final Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w("error", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }


    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            Log.e("keyboard_error", e.toString());
        }
    }


    public static String getMobileBrand() {
        return Build.BRAND;
    }

    public static String getMobileModel() {
        return Build.MODEL;
    }

    public static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }


    public static String getWifiMacAddress(Context mContext) {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        return macAddress;
    }

    public static String getDeviceMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            Log.e("mac_error", ex.toString());
        }
        return "02:00:00:00:00:00";
    }


    public static boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }


    public static void showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        progressDialog.dismiss();

    }

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     * </p>
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        context.sendBroadcast(intent);
    }

    /**
     * Save String content to User Shared Preference. Like menu, wishlist item, etc.
     *
     * @param context {@link Activity} {@link Context}
     * @param key     Key name as {@link String}
     * @param value   Value as {@link String}
     */
    public static void saveUserPreference(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }


    /**
     * Save boolean content to User Shared Preference. Like menu, wishlist item, etc.
     *
     * @param context {@link Activity} {@link Context}
     * @param key     Key name as {@link Boolean}
     * @param value   Value as {@link String}
     */
    public static void saveUserPreference(Context context, String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Save Integer content to User Shared Preference. Like menu, wishlist item, etc.
     *
     * @param context {@link Activity} {@link Context}
     * @param key     Key name as {@link Integer}
     * @param value   Value as {@link String}
     */
    public static void saveUserPreference(Context context, String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Get string content from User Shared Preference.
     *
     * @param context {@link Activity} {@link Context}
     * @param key     Key name as {@link String}
     * @return Value as {@link String}
     */
    public static String getStringUserPreference(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }


    /**
     * Get boolean content from User Shared Preference.
     *
     * @param context {@link Activity} {@link Context}
     * @param key     Key name as {@link Boolean}
     * @return Value as {@link String}
     */
    public static boolean getBooleanUserPreference(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }

    /**
     * Get int content from User Shared Preference.
     *
     * @param context {@link Activity} {@link Context}
     * @param key     Key name as {@link Integer}
     * @return Value as {@link String}
     */
    public static int getIntegerUserPreference1(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, -1);
    }

    /**
     * Check if preference already exists.
     *
     * @param context {@link Activity} {@link Context}
     * @param key     Key name to check existence
     * @return True if it exists else false.
     */
    public static boolean isUserPreferenceExists(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPref.contains(key);
    }

    /**
     * Remove a content from any Shared Preference by it's key and file name.
     *
     * @param context {@link Activity} {@link Context}
     * @param key     Key name as {@link String}
     * @return True if successfully deleted the preference else False.
     */
    public static boolean removePreference(Context context, String preference, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPref.edit().remove(key).commit();
    }

    /**
     * Delete all Preferences.
     *
     * @param context {@link Activity} {@link Context}
     * @return True if successfully deleted all preferences else False.
     */
    public static boolean clearAllPreference(Context context) {
        SharedPreferences sharedUserPref = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences sharedSettingPref = context.getSharedPreferences(SETTING_PREFERENCE, Context.MODE_PRIVATE);
        return sharedUserPref.edit().clear().commit() && sharedSettingPref.edit().clear().commit();
    }


    /**
     * Check user internet connectivity.
     *
     * @param context Activity context
     * @return Connected to internet or not. (True / False)
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    /**
     * A Toast message if device is not connected to internet.
     *
     * @param context Activity context.
     */
    public static void offlineMessage(Context context) {
        //Toast.makeText(context, context.getResources().getString(R.string.device_offline_message), Toast.LENGTH_LONG).show();
    }


    public static Toast customMessage(Context ctx, String message) {
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;

    }


}
