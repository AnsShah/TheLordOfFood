package com.applicationdevelopers.lordoffood.NetworkManager;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applicationdevelopers.lordoffood.Controllers.Common;
import com.applicationdevelopers.lordoffood.Controllers.NotificationService.MySingleton;
import com.applicationdevelopers.lordoffood.Controllers.TLSSocketFactory;
import com.applicationdevelopers.lordoffood.Interfaces.FirebaseCallback;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class NetworkManager {
    private Context context;
    private RequestQueue requestQueue;
    private int responseTimeout = 30000;  //30sec
    private int maxRetries = 0;
    public NetworkManager(Context context) {

        this.context = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            try {
                ProviderInstaller.installIfNeeded(context);
            } catch (GooglePlayServicesRepairableException e) {
                GooglePlayServicesUtil.showErrorNotification(e.getConnectionStatusCode(), context);
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
            requestQueue = Volley.newRequestQueue(context, stack);
            Log.wtf("on start null- kitkat", "on start null - kitkat");
        } else {
            Log.wtf("on start null- other", "on start null - other");
            requestQueue = Volley.newRequestQueue(context);
        }
    }
    public void jsonObjectRequest2(final Context context, final String app_key, final JSONObject jsonobject, final String url, final int request, final NetworkCallback networkCallback) {

        //post parameters
        try {
            jsonobject.put("app_key", app_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                request, url, jsonobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            networkCallback.onSuccess(response.toString());
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error.networkResponse != null) {
                        int responseCode = error.networkResponse.statusCode;
                        //if the password is incorrect and response code is 403
                        if (responseCode == 403) {
                            Toast.makeText(context, "Invalid Request", Toast.LENGTH_SHORT).show();
                        }
                        else if (responseCode ==501){
                            Toast.makeText(context, "Internal Server Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Server Busy. Please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjReq);
    }
    public void jsonObjectRequest3(final String app_key,final String user_id, final JSONObject jsonobject, final String url, final int request, final NetworkCallback networkCallback) {

        //post parameters
        try {
            jsonobject.put("app_key", app_key);
            jsonobject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                request, url, jsonobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            networkCallback.onSuccess(response.toString());
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error.networkResponse != null) {
                        int responseCode = error.networkResponse.statusCode;
                        //if the password is incorrect and response code is 403
                        if (responseCode == 403) {
                            Toast.makeText(context, "Invalid Request", Toast.LENGTH_SHORT).show();
                        }
                        else if (responseCode ==501){
                            Toast.makeText(context, "Internal Server Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Unable to connect to server. Please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjReq);
    }

    public void JsonObjectOrderRequest(final Map<String, String> params, final String url, final int request, final NetworkCallback networkCallback){

        StringRequest orderRequest = new StringRequest(request, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                networkCallback.onSuccess(response.toString());
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                networkCallback.onError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(orderRequest);
    }
    public void JsonObjectRequest(final Map<String, String> params, final String url, final int request, final NetworkCallback networkCallback){
        StringRequest orderRequest = new StringRequest(request, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                networkCallback.onSuccess(response.toString());
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                networkCallback.onError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(orderRequest);
    }
    public void sendNotification(JSONObject notification, final Context context) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Common.FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(Common.TAG, "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(Common.TAG, "onErrorResponse: Didn't work");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", Common.serverKey);
                params.put("Content-Type", Common.contentType);
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
    public void getData(DatabaseReference databaseReference, final FirebaseCallback firebaseCallback) {
        databaseReference.orderByChild("app_name").equalTo("Lord Of Food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    firebaseCallback.onDataChange(snapshot);
                } catch (Exception e) {
                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                try {
                    firebaseCallback.onCancelled(error);
                    //Toast.makeText(context, "Unable to connect to server. Please try again", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
