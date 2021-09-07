package com.applicationdevelopers.lordoffood.Controllers;

import com.applicationdevelopers.lordoffood.Interfaces.IFCMService;
import com.applicationdevelopers.lordoffood.Retrofit.GoogleMapsAPI;
import com.applicationdevelopers.lordoffood.Retrofit.IFCMClient;
import com.applicationdevelopers.lordoffood.Retrofit.IGoogleAPI;
import com.google.android.gms.maps.model.LatLng;

public class Common {
    public static LatLng currenLocation;

    public static final String fcmURL="https://fcm.googleapis.com/";
    public static final String googleAPIUrl="https://maps.googleapis.com";
    public static final String app_key="d72fbbccd9fe64c3a14f85d225a046f4";
    public static final String Taster_app_key="c730e2d7b6f74a2c663d4cd748a7cad5";
    public static final String setting_url="https://www.taster.pk/api/sync/settings";
    public static final String allData_url="https://www.taster.pk/api/sync";
    public static final String areas_url="https://www.taster.pk/api/app/areas";
    public static final String sections_url="https://www.taster.pk/api/sync/sections";
    public static final String user_Id="1592";
    public static final String NOTIFICATION_TITLE="Order Status";
    public static final String NOTIFICATION_MESSAGE="New Order has been received check it out";
    public static final String FCM_API = "https://fcm.googleapis.com/fcm/send";
    public static final String serverKey = "key=" + "AAAA-mfuA3w:APA91bG4uv0zkVaheINlGV_ia7xkvMojZM5FozjVg9z0Pm02xPbUaqfMa4sx8dsrbT-iOHIXeuSNCNbWcEH6L4KIVXvKdo1hFMmfdyczdLxAoDDNgbRQHtzHo3a1lMdHp-1juk-J6X6k";
    public static final String contentType = "application/json";
    public static final String TAG = "NOTIFICATION TAG";

    private static double baseFare=2.55;
    private static double timeRate=0.35;
    private static double distanceRate=1.75;

    public static double getPrice(double km, int min){
        return (baseFare+(timeRate*min)+(distanceRate*km));
    }
    public static IGoogleAPI getGoogleService(){
        return GoogleMapsAPI.getClient(googleAPIUrl).create(IGoogleAPI.class);
    }
    public static IFCMService getFCMService(){
        return IFCMClient.getClient(fcmURL).create(IFCMService.class);
    }
}
