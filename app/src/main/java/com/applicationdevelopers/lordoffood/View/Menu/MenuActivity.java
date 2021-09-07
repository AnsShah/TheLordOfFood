package com.applicationdevelopers.lordoffood.View.Menu;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.applicationdevelopers.lordoffood.Adapter.MenuAdapter.MenuAdapter;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.applicationdevelopers.lordoffood.Internet.AppStatus;
import com.applicationdevelopers.lordoffood.Model.Menu.MenuModel;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    ImageView idOrderHeaderBackBtn;
    RecyclerView idRecyclerViewMenu;
    private List<MenuModel> menuModels;
    ProgressBar idProgressBar;
    private NetworkManager networkManager;
    RelativeLayout idProgressBarRelative;

    String app_key="d72fbbccd9fe64c3a14f85d225a046f4";
    String url = "https://www.taster.pk/api/sync/menu";
    String url1 = "https://www.taster.pk/api/sync/sections";
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initLayout();
    }
    private void initLayout(){
        idOrderHeaderBackBtn=(ImageView) findViewById(R.id.idOrderHeaderBackBtn);
        idRecyclerViewMenu=(RecyclerView) findViewById(R.id.idRecyclerViewMenu);
        idProgressBarRelative=(RelativeLayout) findViewById(R.id.idProgressBarRelative);
        idRecyclerViewMenu.setHasFixedSize(true);
        idRecyclerViewMenu.setLayoutManager( new LinearLayoutManager(this));

        allClickListener();

        getMenus(app_key);

    }
    @SuppressLint("ClickableViewAccessibility")
    private void allClickListener(){
        idOrderHeaderBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActivity.this.finish();
            }
        });
    }
    private void getMenus(String app_key){
        if (!AppStatus.getInstance(context).isOnline()  && !AppStatus.getInstance(getApplicationContext()).isNetworkOnline2()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_SHORT).show();
        }
        else {
            idProgressBarRelative.setVisibility(View.VISIBLE);
//            final ProgressDialog progressDialog = ProgressDialog.show(MenuActivity.this, "Please wait", "Loading......", false, false);
            //initialize Arrays
            menuModels = new ArrayList<>();
            networkManager = new NetworkManager(context);
            JSONObject jsonObject = new JSONObject();
            networkManager.jsonObjectRequest2(context,app_key, jsonObject, url1, Request.Method.POST, new NetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    try{
                        JSONObject obj = new JSONObject(result);
                        JSONObject infoObj = obj.getJSONObject("data");

                        if (obj.getInt("status_code") == 200 && obj.getString("status").equals("success")) {
                            JSONArray jsonArraySections = infoObj.getJSONArray("sections");
                                for (int j=0;j<jsonArraySections.length();j++){
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(j);
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /* productModels = new ArrayList<>();*/
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
                                        String name = jsonObjectPakJinnahSpecial.getString("ad_name");
                                        String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
                                        String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
                                        String location = jsonObjectPakJinnahSpecial.getString("city_name");
                                        String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
                                        String views = jsonObjectPakJinnahSpecial.getString("views");
                                        //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
                                        String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
                                       // productModels.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Dubai", description_post, "Special Chicken Handi"));
                                        if (!name.contains("Deal")){
                                            Log.wtf("Name ->",name);
                                        }
                                        else {
                                            menuModels.add(new MenuModel(name,Price,img_Url,open_status));
                                        }
                                    }
                                }
                            MenuAdapter menuAdapter=new MenuAdapter(context,menuModels);
                            idRecyclerViewMenu.setAdapter(menuAdapter);
//                            progressDialog.dismiss();
                            idProgressBarRelative.setVisibility(View.GONE);
                        }
                        else {
                            idProgressBarRelative.setVisibility(View.GONE);
//                            progressDialog.dismiss();
                            Log.wtf("Error found -> Menu ->","");
                            Toast.makeText(MenuActivity.this, "System Busy Try Again!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (JSONException e) {
                        idProgressBarRelative.setVisibility(View.GONE);
                        Toast.makeText(MenuActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError volleyError) {
                    idProgressBarRelative.setVisibility(View.GONE);
//                    progressDialog.dismiss();
                    Log.wtf("Error found -> Menu ->",volleyError.toString());
                    Toast.makeText(MenuActivity.this, "System Busy Try Again!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
/* try {
                        JSONObject obj = new JSONObject(result);
                        JSONObject infoObj = obj.getJSONObject("data");
                        if (obj.getInt("status_code") == 200 && obj.getString("status").equals("success")) {
                            JSONArray jsonArrayMenu = infoObj.getJSONArray("menu");
                            for (int i = 0; i < jsonArrayMenu.length(); i++) {
                                JSONObject jsonArrayAdsJSONObject = jsonArrayMenu.getJSONObject(i);
                                String name=jsonArrayAdsJSONObject.getString("name");
                                //String price=jsonArrayAdsJSONObject.getString("1200");
                                menuModels.add(new MenuModel(name, "1200"));
                            }
                            MenuAdapter menuAdapter=new MenuAdapter(context,menuModels);
                            idRecyclerViewMenu.setAdapter(menuAdapter);
                        }
                        else {
                            Log.wtf("Error found -> Menu ->","");
                            Toast.makeText(MenuActivity.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (JSONException e){
                        idProgressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                        Toast.makeText(MenuActivity.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                    }*/