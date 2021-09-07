package com.applicationdevelopers.lordoffood.View.Gallery;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.applicationdevelopers.lordoffood.Adapter.RecyclerAdapter;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.applicationdevelopers.lordoffood.Internet.AppStatus;
import com.applicationdevelopers.lordoffood.Model.Youtube.YoutubeVideoModel;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;
import com.applicationdevelopers.lordoffood.View.Founder.FounderActivity;
import com.google.android.youtube.player.YouTubeBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryActivity extends YouTubeBaseActivity {
    ImageView idOrderHeaderBackBtn;
    RecyclerView idfounderRecyclerView;
    RelativeLayout idProgressBarRelative;
    String app_key="d72fbbccd9fe64c3a14f85d225a046f4";
    String user_id="1592";
    private List<YoutubeVideoModel> youtubeVideoModelList=new ArrayList<>();;
    private NetworkManager networkManager;
    String url = "https://www.taster.pk/api/sync/videos";
    final private Context context=this;
    final String className = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initLayout();

    }
    private void initLayout(){
        idProgressBarRelative=(RelativeLayout) findViewById(R.id.idProgressBarRelative);
        idOrderHeaderBackBtn=(ImageView) findViewById(R.id.idOrderHeaderBackBtn);
        idfounderRecyclerView=(RecyclerView) findViewById(R.id.idfounderRecyclerView);
        idfounderRecyclerView.setHasFixedSize(true);
        idfounderRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        allClickListener();
        getVideos(app_key,user_id);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void allClickListener(){
        idOrderHeaderBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryActivity.this.finish();
            }
        });
    }
    private void getVideos(String app_key,String user_id){
        if (!AppStatus.getInstance(context).isOnline() && !AppStatus.getInstance(context).isNetworkOnline2()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_SHORT).show();
        }
        else {
            idProgressBarRelative.setVisibility(View.VISIBLE);
//            final ProgressDialog progressDialog = ProgressDialog.show(GalleryActivity.this, "Please wait", "Loading......", false, false);
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
                            RecyclerAdapter adapter=new RecyclerAdapter(GalleryActivity.this,youtubeVideoModelList,className);
                            idfounderRecyclerView.setAdapter(adapter);
                            Log.wtf("Videos -> getMyVideos ->  onSuccess -> ", result);
                        }
                        else {
                            Toast.makeText(GalleryActivity.this, "System Busy Try Again Later!!!", Toast.LENGTH_SHORT).show();
                            Log.wtf("Videos -> getMyVideos ->  onError -> ", result);
                        }
                        idProgressBarRelative.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                    }
                    catch (JSONException e)
                    {
                        Toast.makeText(GalleryActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(GalleryActivity.this, "System Busy Try Again Later!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}