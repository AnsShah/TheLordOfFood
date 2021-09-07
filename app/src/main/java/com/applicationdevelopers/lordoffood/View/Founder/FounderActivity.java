package com.applicationdevelopers.lordoffood.View.Founder;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.applicationdevelopers.lordoffood.Adapter.RecyclerAdapter;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.applicationdevelopers.lordoffood.Internet.AppStatus;
import com.applicationdevelopers.lordoffood.Model.Youtube.YoutubeVideoModel;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class FounderActivity extends YouTubeBaseActivity {
    ImageView idOrderHeaderBackBtn,idImageViewFounder;
    RecyclerView idfounderRecyclerView;
    RelativeLayout idProgressBarRelative;
    TextView idTextviewHeadingFounder,idTextviewDetailsFounder,idTextviewSubHeadingFounder;
    ScrollView idScrollViewFounder;
//    String api_key="AIzaSyCuIy8CSYeJFyP-UH3Avsgx1Tqm4DsCUhU";
    Vector<YoutubeVideoModel> youtubeVideoModels=new Vector<>();
//    String app_key="6bc2ecca59d6ac77cc9d192fd8779cb2";
    String app_key="d72fbbccd9fe64c3a14f85d225a046f4";
    String app_key1="c730e2d7b6f74a2c663d4cd748a7cad5";
    /* String app_key="54072f485cdb7897ebbcaf7525139561";
    String app_key1="6bc2ecca59d6ac77cc9d192fd8779cb2";*/
    String user_id="1592";
    private List<YoutubeVideoModel> youtubeVideoModelList;
    private NetworkManager networkManager;
    String url = "https://www.taster.pk/api/sync/videos";
    String founderUrl = "https://www.taster.pk/api/app/founder";
    final private Context context=this;
    final String className = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_founder);

        initLayout();

    }
    private void initLayout(){
        idImageViewFounder=(ImageView) findViewById(R.id.idImageViewFounder);
        idOrderHeaderBackBtn=(ImageView) findViewById(R.id.idOrderHeaderBackBtn);
        idTextviewSubHeadingFounder=(TextView) findViewById(R.id.idTextviewSubHeadingFounder);
        idfounderRecyclerView=(RecyclerView) findViewById(R.id.idfounderRecyclerView);
        idTextviewDetailsFounder=(TextView) findViewById(R.id.idTextviewDetailsFounder);
        idTextviewHeadingFounder=(TextView) findViewById(R.id.idTextviewHeadingFounder);
        idProgressBarRelative=(RelativeLayout) findViewById(R.id.idProgressBarRelative);
        idScrollViewFounder=(ScrollView) findViewById(R.id.idScrollViewFounder);
        idfounderRecyclerView.setHasFixedSize(true);
        idfounderRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        allClickListener();
        getVideos(app_key,user_id);
        getFounderDetails(app_key,user_id);

    }
    @SuppressLint("ClickableViewAccessibility")
    private void allClickListener(){
        idOrderHeaderBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FounderActivity.this.finish();
            }
        });
    }
    private void getVideos(String app_key,String user_id){
        if (!AppStatus.getInstance(context).isOnline() && !AppStatus.getInstance(context).isNetworkOnline2()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_SHORT).show();
        }
        else {
            idProgressBarRelative.setVisibility(View.VISIBLE);
            youtubeVideoModelList=new ArrayList<>();
//            final ProgressDialog progressDialog = ProgressDialog.show(FounderActivity.this, "Please wait", "Loading......", false, false);
            networkManager = new NetworkManager(context);
            JSONObject jsonObject = new JSONObject();
            //put Parameters
            Map<String, String> params = new HashMap<String, String>();
            params.put("app_key", app_key);
            params.put("user_id", user_id);
            //put Parameters
            networkManager.JsonObjectRequest(params, url, Request.Method.POST, new NetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        JSONObject infoObj = obj.getJSONObject("data");
                        if (obj.getInt("status_code") == 200  && obj.getString("status").equals("success")) {
                            JSONArray jsonArrayMenu = infoObj.getJSONArray("videos");
//                            JSONObject jsonArrayMenu1 = infoObj.getJSONObject(0);
//                            JSONObject jsonObjectDetails=infoObj.getJSONObject("cms");
//                            idTextviewHeadingFounder.setText(jsonObjectDetails.getString("heading"));
//                            idTextviewDetailsFounder.setText(jsonObjectDetails.getString("detail"));
//                            Glide.with(context).load(jsonObjectDetails.getString("image")).into(idImageViewFounder);
//                            idTextviewHeadingFounder.setText(jsonArrayDetails.getString("heading"));
                            for (int i = 0; i < jsonArrayMenu.length(); i++) {
                                JSONObject jsonArrayVideosJSONObject = jsonArrayMenu.getJSONObject(i);
                                String url=jsonArrayVideosJSONObject.getString("video");
                                String vedio_key=jsonArrayVideosJSONObject.getString("v_key");
                                int id=jsonArrayVideosJSONObject.getInt("id");
                                String name=jsonArrayVideosJSONObject.getString("name");
                                String is_featured=jsonArrayVideosJSONObject.getString("is_featured");
//                                youtubeVideoModels.add( new YoutubeVideoModel(url,id,name,is_featured,vedio_key));
                                youtubeVideoModelList.add( new YoutubeVideoModel(url,id,name,is_featured,vedio_key));
                            }
                            idScrollViewFounder.setVisibility(View.VISIBLE);
                            RecyclerAdapter adapter=new RecyclerAdapter(FounderActivity.this,youtubeVideoModelList,className);
                            idfounderRecyclerView.setAdapter(adapter);
                            Log.wtf("Videos -> getMyVideos ->  onSuccess -> ", result);
                        }
                        else {
                              Toast.makeText(FounderActivity.this, "System Busy Try Again Later!!!", Toast.LENGTH_SHORT).show();
                            Log.wtf("Videos -> getMyVideos ->  onError -> ", result);
                        }
                        idProgressBarRelative.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                    }
                    catch (JSONException e)
                    {
                        Toast.makeText(FounderActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        idProgressBarRelative.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError volleyError) {
                    idProgressBarRelative.setVisibility(View.GONE);
//                    progressDialog.dismiss();
                    Log.wtf("Videos -> getMyVideos ->  onError -> ", volleyError);
                    Toast.makeText(FounderActivity.this, "System Busy Try Again Later!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void getFounderDetails(String app_key,String user_id){
        if (!AppStatus.getInstance(context).isOnline() && !AppStatus.getInstance(context).isNetworkOnline2()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_SHORT).show();
        }
        else {
            idProgressBarRelative.setVisibility(View.VISIBLE);
//            final ProgressDialog progressDialog = ProgressDialog.show(FounderActivity.this, "Please wait", "Loading......", false, false);
            networkManager = new NetworkManager(context);
            JSONObject jsonObject = new JSONObject();
            //put Parameters
            Map<String, String> params = new HashMap<String, String>();
            params.put("app_key", app_key1);
            params.put("user_id", user_id);
            //put Parameters
            networkManager.JsonObjectRequest(params, founderUrl, Request.Method.POST, new NetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        JSONObject infoObj = obj.getJSONObject("data");
                        if (obj.getInt("status_code") == 200  && obj.getString("status").equals("success")) {
                            JSONObject jsonObjectDetails=infoObj.getJSONObject("cms");
                            String subHeading=jsonObjectDetails.getString("sub_heading");
                            String heading=jsonObjectDetails.getString("heading");
                            String details=jsonObjectDetails.getString("detail");
                            if (!details.isEmpty() && !details.equals("")){
                                details=details.replace("<p>","");
                                details=details.replace("</p>","");
                                details=details.replace("<br />","");
                                details=details.replace("</strong>","");
                                details=details.replace("<strong>","");
                                idTextviewDetailsFounder.setText(details);
                            }
                            if (!heading.equals("") || !heading.isEmpty() || !heading.equals(" ") || heading!=null){
                                idTextviewHeadingFounder.setText(heading);
                            }
                            if (!subHeading.equals("") || !subHeading.isEmpty() || !subHeading.equals(" ")){
                                idTextviewSubHeadingFounder.setText(jsonObjectDetails.getString("sub_heading"));
                            }
                            idScrollViewFounder.setVisibility(View.VISIBLE);
                            Glide.with(context).load(jsonObjectDetails.getString("image")).into(idImageViewFounder);
                            Log.wtf("FounderDetails -> getFounderDetails ->  onSuccess -> ", result);
                        }
                        else {
                            Toast.makeText(FounderActivity.this, "System Busy Try Again Later!!!", Toast.LENGTH_SHORT).show();
                            Log.wtf("FounderDetails -> getFounderDetails ->  onError -> ", result);
                        }
                        idProgressBarRelative.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                    }
                    catch (JSONException e)
                    {
                        idProgressBarRelative.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError volleyError) {
                    idProgressBarRelative.setVisibility(View.GONE);
//                    progressDialog.dismiss();
                    Log.wtf("Videos -> getMyVideos ->  onError -> ", volleyError);
                    Toast.makeText(FounderActivity.this, "System Busy Try Again Later!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}