package com.applicationdevelopers.lordoffood.View.Splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import com.applicationdevelopers.lordoffood.BuildConfig;
import com.applicationdevelopers.lordoffood.Controllers.Common;
import com.applicationdevelopers.lordoffood.Interfaces.FirebaseCallback;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.applicationdevelopers.lordoffood.Internet.AppStatus;
import com.applicationdevelopers.lordoffood.Model.UpdateModel.UpdateModel;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;
import com.applicationdevelopers.lordoffood.View.Cart.ConfirmOrderActivity;
import com.applicationdevelopers.lordoffood.View.ContactUs.ContactUsActivity;
import com.applicationdevelopers.lordoffood.View.Home.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    ImageView logo_splash;
    Timer timer;
    private Context context=this;
    NetworkManager networkManager;
    String order_gst_percentString="";
    int order_gst_percent=0;
    SharedPreferences.Editor editor;
    //shared preference
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME = "PrefsFile";
    DatabaseReference databaseReferenceUpdate;
    private List<UpdateModel> updateModelList;
    String app_name="",app_version="";
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        networkManager=new NetworkManager(context);
        logo_splash=(ImageView) findViewById(R.id.logo_splash);
//Shared Pref
        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //check theme
        int theme=getSharedPreferences("theme",MODE_PRIVATE).getInt("mode",2);
        switch (theme){
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        }
        //check theme
        if (!AppStatus.getInstance(context).isOnline() && !AppStatus.getInstance(context).isNetworkOnline2()){
            Toast.makeText(context, "No Internet Access!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            checkUpdate();
        }
    }
    private void getSettings(){
     //put Parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_key", Common.app_key);
        networkManager.JsonObjectRequest(params, Common.setting_url, Request.Method.POST, new NetworkCallback() {
            @Override
            public void onSuccess(String result) {
                Log.wtf("My Settings -> getMySettings ->  onSuccess -> ", result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject infoObj = obj.getJSONObject("data");
                    if (obj.getInt("status_code") == 200 && obj.getString("status").equals("success")) {
                        JSONObject jsonObject=infoObj.getJSONObject("settings");
//                        JSONArray jsonArraySettings = infoObj.getJSONArray("settings");
                        order_gst_percentString=jsonObject.getString("order_gst_percent");
//                        for (int i=0;i<jsonArraySettings.length();i++){
//                            JSONObject jsonArraySettingsJSONObject = jsonArraySettings.getJSONObject(i);
//                        }
                        Log.wtf("My Settings -> getMySettings ->  My GST-> ",order_gst_percentString);
                        editor = mPrefs.edit();
                        editor.putString("order_gst_percent", order_gst_percentString);
                        editor.apply();
                    }
                    else {
//                        Toast.makeText(SplashActivity.this, "System Busy Try Again Later!!!", Toast.LENGTH_SHORT).show();
                        Log.wtf("My Settings -> getMySettings ->  onError -> ","Try Again");
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.wtf("My Settings -> getMySettings ->  onException -> ", e);
                }
            }
            @Override
            public void onError(VolleyError volleyError) {
                Log.wtf("My Settings -> getMySettings ->  onError -> ",""+volleyError.getMessage());
            }
        });
    }
    private void checkUpdate(){
        databaseReferenceUpdate= FirebaseDatabase.getInstance().getReference("Applications");
        networkManager.getData(databaseReferenceUpdate, new FirebaseCallback() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(context, "No Update Found", Toast.LENGTH_SHORT).show();
                }
                else {
                    updateModelList=new ArrayList<>();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        UpdateModel updateModel=childSnapshot.getValue(UpdateModel.class);
                        updateModelList.add(updateModel);
                        app_name = childSnapshot.child("app_name").getValue().toString();
                        app_version= childSnapshot.child("app_version").getValue().toString();
                    }
//                    Toast.makeText(context, "App Ver:"+app_version+"\nApp Version:"+versionName, Toast.LENGTH_SHORT).show();
                    if (app_version.equalsIgnoreCase(versionName)){
                        //timer
                        timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(SplashActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },3000);
                        getSettings();
                        //timer
//                        Toast.makeText(context, "App Updated", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        updateAppPopup(context);
//                        Toast.makeText(context, "Need Update", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

            }
        });
    }
    private void updateAppPopup(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.need_update_popup, null, false);
        Button btn_UpdateApp=(Button) inflatedView.findViewById(R.id.btn_UpdateApp);
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
//         set height depends on the device size
        final PopupWindow filterPopup = new PopupWindow(inflatedView, width, height, true);
//         set a background drawable with rounders corners
//          filterPopup.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_corner_with_light_gray));
//        filterPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        filterPopup.setHeight(WindowManager.LayoutParams.MATCH_PARENT);

//         allowCardPopup.setAnimationStyle(R.style.PopupAnimation);
//         make it focusable to show the keyboard to enter in `EditText`
        filterPopup.setOutsideTouchable(false);
//         filterPopup.setFocusable(true);
//         make it outside touchable to dismiss the popup window
        filterPopup.setOutsideTouchable(true);

//         show the popup at bottom of the screen and set some margin at bottom ie,
        filterPopup.showAtLocation(inflatedView, Gravity.CENTER, 0, 0);

        filterPopup.getContentView().setFocusableInTouchMode(true);
//        Back To Home
        btn_UpdateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
//        Back To Home
        filterPopup.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    filterPopup.dismiss();
                    return true;
                }
                return false;
            }
        });
    }
}