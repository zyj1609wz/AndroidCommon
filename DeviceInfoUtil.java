package com.wifi.analytics;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by marks on 2016/8/19.
 */
public class DeviceInfoUtil {

    /**
     *  获取应用包名 (om.yiba.www.test)
     * @param context
     * @return
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取应用版本号(1.0)
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String pkName = context.getPackageName();
        String versionName = "";
        try {
            synchronized (DeviceInfoUtil.class) {
                versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取deviceId(b0d62f4417308971)
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        String deviceId = "" ;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        }
        return deviceId ;
    }

    /**
     * 获取AndroidId(b0d62f4417308971)
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取本地语言
     * zh
     * @param context
     * @return
     */
    public static String getLocalLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return locale.getLanguage();
    }

    /**
     * 获取本地国家
     * CN
     * @param context
     * @return
     */
    public static String getLocalCountry(Context context) {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        if (TextUtils.isEmpty(country)) {
            locale = context.getResources().getConfiguration().locale;
            country = locale.getCountry();
        }
        return country;
    }

    /**
     * 获取设备名字
     * @return
     */
    public static String getDeveiceName() {
        return Build.BRAND + Build.MODEL;
    }

    /**
     * 获取手机系统版本号
     * @return
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备唯一ID
     * "ffffffff-e73a-3bf2-ffff-ffffe73a3bf2"
     * @param context
     * @return
     */
    public static String getAndroidUniqueCode(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) + "";
        if (!TextUtils.isEmpty(androidId)) {
            return new UUID(androidId.hashCode(), ((long) androidId.hashCode() << 32) | androidId.hashCode()).toString();
        }

        //serialNumber: cb333555
        String serialNumber = Build.SERIAL;
        if (!TextUtils.isEmpty(serialNumber)) {
            return new UUID(serialNumber.hashCode(), ((long) serialNumber.hashCode() << 32) | serialNumber.hashCode()).toString();
        }

        String deviceId = "";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId() + "";
        }
        return new UUID(deviceId.hashCode(), ((long) deviceId.hashCode() << 32) | deviceId.hashCode()).toString();
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 获取App Name
     * @param context
     * @return
     */
    public static String getAppName(Context context){
        PackageManager pm = context.getPackageManager();
        return context.getApplicationInfo().loadLabel(pm).toString();
    }
}
