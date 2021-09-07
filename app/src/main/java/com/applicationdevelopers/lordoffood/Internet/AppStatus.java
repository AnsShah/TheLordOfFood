package com.applicationdevelopers.lordoffood.Internet;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class AppStatus {
    private static AppStatus instance = new AppStatus();
    static Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected = false;
    public static AppStatus getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }
    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }
    @TargetApi(Build.VERSION_CODES.M)
    public  boolean isNetworkOnline1(Context context) {
        boolean isOnline = false;
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkCapabilities capabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());  // need ACCESS_NETWORK_STATE permission
            isOnline = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOnline;
    }
    public  boolean isNetworkOnline2() {
        boolean isOnline = false;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("ping -c 1 8.8.8.8");
            int waitFor = p.waitFor();
            isOnline = waitFor == 0;    // only when the waitFor value is zero, the network is online indeed

            // BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            // String str;
            // while ((str = br.readLine()) != null) {
            //     System.out.println(str);     // you can get the ping detail info from Process.getInputStream()
            // }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return isOnline;
    }
    public  boolean isNetworkOnline3() {
        boolean isOnline = false;
        try {
            URL url = new URL("http://www.google.com"); // or your server address
            // URL url = new URL("http://www.baidu.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Connection", "close");
            conn.setConnectTimeout(3000);
            isOnline = conn.getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isOnline;
    }
    public static boolean isNetworkOnline4() {
        boolean isOnline = false;
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("8.8.8.8", 53), 3000);
            // socket.connect(new InetSocketAddress("114.114.114.114", 53), 3000);
            isOnline = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isOnline;
    }
}
