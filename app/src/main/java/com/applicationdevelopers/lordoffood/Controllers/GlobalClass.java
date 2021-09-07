package com.applicationdevelopers.lordoffood.Controllers;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.applicationdevelopers.lordoffood.Model.LruBitmapCache;
import com.applicationdevelopers.lordoffood.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import androidx.multidex.MultiDex;


/**
 * Created by Syed Ans Ali on 06-07-2021.
 */

public class GlobalClass extends Application {

    /**For Volley Image*/
    public static final String TAG = GlobalClass.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static GlobalClass mInstance;


    public boolean isFlavourHasProduct = false;
    public boolean isFlavourHasRecipe = false;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized GlobalClass getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                    && Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                try {
                    ProviderInstaller.installIfNeeded(GlobalClass.this);
                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil.showErrorNotification(e.getConnectionStatusCode(), GlobalClass.this);
//                    return;
                } catch (GooglePlayServicesNotAvailableException e) {
//                    return;
                }

                HttpStack stack = null;
                try {
                    stack = new HurlStack(null, new TLSSocketFactory());
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                    Log.d("Your Wrapper Class", "Could not create new stack for TLS v1.2");
                    stack = new HurlStack();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    Log.d("Your Wrapper Class", "Could not create new stack for TLS v1.2");
                    stack = new HurlStack();
                }
                mRequestQueue = Volley.newRequestQueue(GlobalClass.this, stack);
            }
            else {
                mRequestQueue = Volley.newRequestQueue(GlobalClass.this);
            }
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**For Multi Dex*/
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    /**For deal location*/
    private String dealLocation;

    public String getDealLocation() {
        return dealLocation;
    }

    public void setDealLocation(String dealLocation) {
        this.dealLocation = dealLocation;
    }

    /**
     * Tracker
     **/
    // The following line should be changed to include the correct property id.
    private static final String PROPERTY_ID = "UA-127958011-1";

    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     *
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public GlobalClass() {
        super();
    }
    synchronized public Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
    /*private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    *//**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     *//*
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sAnalytics = GoogleAnalytics.getInstance(this);
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }*/
}


