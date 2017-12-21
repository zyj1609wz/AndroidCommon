package com.wifi.analytics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by ${zhaoyanjun} on 2017/4/13.
 */

public class NetUtil {

    public static final int NETTYPE_NO = 0;
    public static final int NETTYPE_WIFI = 1;
    public static final int NETTYPE_2G = 2;
    public static final int NETTYPE_3G = 3;
    public static final int NETTYPE_4G = 4;


    /**
     * 判断是否有网络可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断是否 WiFi 连接
     *
     * @param context
     * @return
     */
    public static boolean isWiFiConnected(Context context) {
        return getAPNType(context) == 1;
    }

    /**
     * 获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NETTYPE_NO;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            return NETTYPE_WIFI;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                return NETTYPE_4G;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                return NETTYPE_3G;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                return NETTYPE_2G;
            } else {
                return NETTYPE_2G;
            }
        }
        return NETTYPE_NO;
    }

    public static String getNetType(Context context) {
        int type = getAPNType(context);
        String netType = "";
        switch (type) {
            case NETTYPE_NO:
                netType = "NONETWORK";
                break;
            case NETTYPE_2G:
                netType = "2G";
                break;
            case NETTYPE_3G:
                netType = "3G";
                break;
            case NETTYPE_4G:
                netType = "4G";
                break;
            case NETTYPE_WIFI:
                netType = "WIFI";
                break;
        }
        return netType;
    }

    /**
     * 获取当前连接WiFi的 WifiInfo
     *
     * @param context
     * @return
     */
    public static android.net.wifi.WifiInfo getCurrentWifiInfo(Context context) {
        android.net.wifi.WifiManager wifiManager = (android.net.wifi.WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo();
    }

    /**
     * 获取当前WiFi 连接的名字
     *
     * @param context
     * @return
     */
    public static String getCurrentWifiName(Context context) {
        WifiInfo wifiInfo = getCurrentWifiInfo(context);
        String wifiName = wifiInfo.getSSID();
        String wifiBssid = wifiInfo.getBSSID();

        if (wifiName.contains("\"")) {
            wifiName = wifiName.replaceAll("\"", "");
        }

        if (TextUtils.isEmpty(wifiName) || TextUtils.isEmpty(wifiBssid)) {
            return null;
        }

        if ("00:00:00:00:00:00".equals(wifiBssid)) {
            return null;
        }

        if ("<unknown ssid>".equals(wifiName)) {
            return null;
        }

        return wifiName ;
    }


}
