package com.applicationdevelopers.lordoffood.View.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.applicationdevelopers.lordoffood.Adapter.ExpandableListAdapter.ExpandableListAdapter;
import com.applicationdevelopers.lordoffood.Controllers.Common;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.applicationdevelopers.lordoffood.Internet.AppStatus;
import com.applicationdevelopers.lordoffood.LocalDatabase.PersonDBHelper;
import com.applicationdevelopers.lordoffood.Model.Menu.MenuModel;
import com.applicationdevelopers.lordoffood.Model.ProductModel.ProductModel;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;
import com.applicationdevelopers.lordoffood.View.AboutUs.AboutUsActivity;
import com.applicationdevelopers.lordoffood.View.Cart.Fragment.ProductDetailsFragment;
import com.applicationdevelopers.lordoffood.View.Cart.OrderActivity;
import com.applicationdevelopers.lordoffood.View.ContactUs.ContactUsActivity;
import com.applicationdevelopers.lordoffood.View.Founder.FounderActivity;
import com.applicationdevelopers.lordoffood.View.Gallery.GalleryActivity;
import com.applicationdevelopers.lordoffood.View.Home.Fragment.FavouriteFragment;
import com.applicationdevelopers.lordoffood.View.Home.Fragment.Home_Fragment;
import com.applicationdevelopers.lordoffood.View.Home.Fragment.OrdersFragment;
import com.applicationdevelopers.lordoffood.View.Menu.MenuActivity;
import com.applicationdevelopers.lordoffood.View.Settings.SettingsActivity;
import com.applicationdevelopers.lordoffood.View.Share.ShareActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Home_Fragment home_fragment;
    private FavouriteFragment favouriteFragment;
    private OrdersFragment ordersFragment;
    private BottomNavigationView bottom_navigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView idToggleBtn,idHeaderCartBtn,idSearchDataFishItHome;
    NavigationView nav_view;
    public static TextView idMainPageCartHint,idAppVersionHome;
    public static int counter = 0;
    int i=0;
    private Context context=this;

    //new
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList;
    private long numberOfOrders;
    private PersonDBHelper dbHelper;
    private NetworkManager networkManager;
    String app_key="d72fbbccd9fe64c3a14f85d225a046f4";
    private List<ProductModel> productModels;
    private Handler handler = new Handler();
    boolean doubleBackToExitPressedOnce = false;
    //new
    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED){
                        //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                        popupSnackbarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED){
                        if (mAppUpdateManager != null){
                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                }
            };
    private AppUpdateManager mAppUpdateManager;
    private FakeAppUpdateManager fakeAppUpdateManager;
    private static final int RC_APP_UPDATE = 11;
    private String TAG="HomeActivity";
    List<MenuModel> childModelsList;
    List<MenuModel> finalChildModelsList;
    MenuModel menuModel;
//    new
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initLayout
        initLayout();
        // initLayout
    }
    //Initialization
    private void initLayout(){

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

        drawerLayout=findViewById(R.id.activity_main);
        idToggleBtn=(ImageView) findViewById(R.id.idToggleBtn);
        idSearchDataFishItHome=(ImageView) findViewById(R.id.idSearchDataFishItHome);
        nav_view=(NavigationView) findViewById(R.id.nav_view);
        idHeaderCartBtn=(ImageView) findViewById(R.id.idHeaderCartBtn);
        idMainPageCartHint=(TextView) findViewById(R.id.idMainPageCartHint);
        idAppVersionHome=(TextView) findViewById(R.id.idAppVersionHome);
        bottom_navigation=(BottomNavigationView) findViewById(R.id.bottom_navigation);
        //idHeaderCartBtn Click
        idHeaderCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, OrderActivity.class));
            }
        });
        //idHeaderCartBtn Click
//        Search
        idSearchDataFishItHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SearchDataActivity.class));
//                Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show();
            }
        });
//        Search
        //fragment
        home_fragment=new Home_Fragment();
        favouriteFragment=new FavouriteFragment();
        ordersFragment=new OrdersFragment();
        setFragment(home_fragment);
        //fragment
        idToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                    //Animation
                    final LayoutAnimationController controller =
                            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_left_to_right);
                    expandableListView.setLayoutAnimation(controller);
                    //Animation
                }
            }
        });
//        new
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.action_home:{
//                        bottom_navigation.setBackgroundResource(R.color.headingTitleColor);
                        setFragment(home_fragment);
                        return true;
                    }
                    case R.id.action_favourite:{
//                        bottom_navigation.setBackgroundResource(R.color.headingTitleColor);
                        setFragment(favouriteFragment);
                        return true;
                    }
//                    case R.id.action_market:{
//                        setFragment(ordersFragment);
//                        return true;
//                    }

                }
                return false;
            }
        });
//        new
        nav_view.setNavigationItemSelectedListener(this);
        //new expandable
        expandableListView = findViewById(R.id.expandableListView);
        //new
        //new
        prepareMenuData();
        populateExpandableList();
        //new expandable
        handler.post(runnable);
        freeMemory();
    }
    //Set fragment
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main,fragment);
        fragmentTransaction.commit();
    }
    //Set fragment
    //Runable
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here
            dbHelper=new PersonDBHelper(HomeActivity.this);
            numberOfOrders=dbHelper.getOrderCount();
            idMainPageCartHint.setText(String.valueOf(numberOfOrders));
            // Repeat every 2 seconds
            handler.postDelayed(runnable, 2000);
        }
    };
    //Runable
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //drawer options
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //drawer options
    //prepare Menu
    private void prepareMenuData() {
        childList = new HashMap<>();
        menuModel = new MenuModel("Home", true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Menu", true, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        //new
        {
            if (!AppStatus.getInstance(getApplicationContext()).isOnline() && !AppStatus.getInstance(getApplicationContext()).isNetworkOnline2()) {
                Toast.makeText(getApplicationContext(), "No Internet Access", Toast.LENGTH_SHORT).show();
            }
            else
            {
                networkManager = new NetworkManager(this);
                String url = "https://www.taster.pk/api/sync/sections";
                JSONObject jsonObject = new JSONObject();
                finalChildModelsList = childModelsList;
                networkManager.jsonObjectRequest2(context,app_key, jsonObject, url, Request.Method.POST, new NetworkCallback() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject obj = new JSONObject(result);
                            JSONObject infoObj = obj.getJSONObject("data");
                            if (obj.getInt("status_code") == 200  && obj.getString("status").equals("success")) {
                                JSONArray jsonArraySections = infoObj.getJSONArray("sections");
                                //Starters
                                //new
                                {
                                    //new
                                    for (int i=0;i<jsonArraySections.length();i++){
                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(i);
                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                        String menuName=jsonObjectGetData.getString("name");
                                        if (menuName.contains("Deal")){
                                            Log.wtf("MenuName ->",menuName);
                                        }
                                        else {
                                            finalChildModelsList.add(new MenuModel(menuName,false,false,1));
                                        }
                                        for (int j = 0; j < jsonArray1.length(); j++) {
                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(j);
                                            String cate = jsonObjectGetData.getString("name");
                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
                                            String views = jsonObjectPakJinnahSpecial.getString("views");
                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
                                            //productModels.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, "Pak Jinnah Special Items"));
                                           if (name.contains("Deal")){
                                               Log.wtf("Name ->",name);
                                           }
                                           else {
                                               finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, cate,i));
                                           }
                                        }
                                    }
                                    //new
//                                    {
////                                    //Starters
//                                    {
////                                        finalChildModelsList.add(new MenuModel("Pak Jinnah Special Item", false, false, 1));
////                                        finalChildModelsList.add(new MenuModel("Starter", false, false, 1));
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(1);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String cate = jsonObjectGetData.getString("name");
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            //productModels.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, "Pak Jinnah Special Items"));
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, cate));
//                                        }
//                                    }
//                                    //Starters
//                                    //Fish
//                                    {
////                                        finalChildModelsList.add(new MenuModel("Fish", false, false, 1));
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(2);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Fish"));
//                                        }
//                                    }
//                                    //Fish
//                                    //Fried
//                                    {
////                                        finalChildModelsList.add(new MenuModel("Fried", false, false, 1));
//                                        //finalChildModelsList.add(new MenuModel("Special Chicken Dish", false, false, 1));
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(3);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Fried"));
//                                        }
//                                    }
//                                    //Fried
//                                    //Baked
//                                    {
////                                        finalChildModelsList.add(new MenuModel("Baked", false, false, 1));
//                                        //finalChildModelsList.add(new MenuModel("Special Mutton Dish", false, false, 1));
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(4);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Baked"));
//                                        }
//                                    }
//                                    //Baked
//                                    //Chicken Kababs
//                                    {
////                                        finalChildModelsList.add(new MenuModel("Chicken Kababs", false, false, 1));
//                                        //finalChildModelsList.add(new MenuModel("Rice", false, false, 1));
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(5);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Chicken Kababs"));
//                                        }
//                                    }
//                                    //Chicken Kababs
//                                    //Mutton Tikka & Raan
//                                    {
////                                        finalChildModelsList.add(new MenuModel("Mutton Tikka & Raan", false, false, 1));
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(6);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Mutton Tikka & Raan"));
//                                        }
//                                    }
//                                    //Mutton Tikka & Raan
//                                    //Mutton Kabab
//                                    {
////                                        finalChildModelsList.add(new MenuModel("Mutton Kabab", false, false, 1));
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(7);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Mutton Kabab"));
//                                        }
//                                    }
//                                    //Mutton Kabab
//                                    //Seasonal
//                                    {
////                                        finalChildModelsList.add(new MenuModel("Seasonal", false, false, 1));
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(8);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
////                                        finalChildModelsList.add(new MenuModel("Chicken Karahi", false, false, 1));
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(9);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
////                                        finalChildModelsList.add(new MenuModel("Chicken Handi", false, false, 1));
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(10);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    }
//                                    Toast.makeText(context, "List:"+finalChildModelsList, Toast.LENGTH_SHORT).show();
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(9);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(10);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(11);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(12);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(13);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(14);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(15);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(16);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(17);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(18);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
//                                    {
//
//                                        JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(19);
//                                        JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
//                                        for (int i = 0; i < jsonArray1.length(); i++) {
//                                            JSONObject jsonObjectPakJinnahSpecial = jsonArray1.getJSONObject(i);
//                                            String name = jsonObjectPakJinnahSpecial.getString("ad_name");
//                                            String Price = jsonObjectPakJinnahSpecial.getString("ad_price");
//                                            String img_Url = jsonObjectPakJinnahSpecial.getString("ad_image");
//                                            String location = jsonObjectPakJinnahSpecial.getString("city_name");
//                                            String open_status = jsonObjectPakJinnahSpecial.getString("open_status");
//                                            String views = jsonObjectPakJinnahSpecial.getString("views");
//                                            //String state_name=jsonObjectPakJinnahSpecial.getString("state_name");
//                                            String description_post = jsonObjectPakJinnahSpecial.getString("description_post");
//                                            finalChildModelsList.add(new MenuModel(name, false, false, 0, Price, img_Url, description_post, open_status, "Seasonal"));
//                                        }
//                                    }
                                    //Seasonal
                                }
                                //new
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "System Is Busy!!!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        //new
        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }

//        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Gallery", true, false); //Menu of Our Deals
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Deals", true, false); //Menu of Share
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("About Us", true, false); //Menu of About Us
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Contact Us", true, false); //Menu of Contact Us
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Founder", true, false); //Menu of Share
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Share", true, false); //Menu of Share
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Settings", true, false); //Menu of Share
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
//        menuModel = new MenuModel("Youtube", true, false); //Menu of Share
//        headerList.add(menuModel);
//        if (!menuModel.hasChildren) {
//            childList.put(menuModel, null);
//        }
    }
    //Prepare Menu
    //Populate Recycler
    private void populateExpandableList() {
        expandableListAdapter = new ExpandableListAdapter(HomeActivity.this, headerList, childList,drawerLayout);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (headerList.get(groupPosition).isGroup) {
                    /*Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();*/
                    /*Toast.makeText(HomeActivity.this, ""+headerList.get(groupPosition).menuName, Toast.LENGTH_SHORT).show();*/
                    if (headerList.get(groupPosition).menuName.equalsIgnoreCase("Home")){
                        final Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.frame_main);
                        if (fragmentInFrame instanceof Home_Fragment){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new Home_Fragment(), null).addToBackStack(null).commit();
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                    else if (headerList.get(groupPosition).menuName.equalsIgnoreCase("About Us")){
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
                    }
                    else if (headerList.get(groupPosition).menuName.equalsIgnoreCase("Contact Us")){
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
                    }
                    else if (headerList.get(groupPosition).menuName.equalsIgnoreCase("Gallery")){
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomeActivity.this, GalleryActivity.class));
                    }
                    else if (headerList.get(groupPosition).menuName.equalsIgnoreCase("Share")){
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomeActivity.this, ShareActivity.class));
                    }
                    else if (headerList.get(groupPosition).menuName.equalsIgnoreCase("Founder")){
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomeActivity.this, FounderActivity.class));
                    }
                    else if (headerList.get(groupPosition).menuName.equalsIgnoreCase("Deals")){
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomeActivity.this, MenuActivity.class));
                    }
                    else if (headerList.get(groupPosition).menuName.equalsIgnoreCase("Settings")){
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                    }
//                    else if (headerList.get(groupPosition).menuName.equalsIgnoreCase("Youtube")){
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        startActivity(new Intent(HomeActivity.this, YoutubeCheckActivity.class));
//                    }
                }

                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if (model.menuName.length() > 0) {
                        if (model.id==1){
                            Log.wtf("Header Value -> ",model.menuName);
                            drawerLayout.closeDrawer(GravityCompat.START);
//                            Log.wtf("Header Value -> ",childText);
//                            txtListChild.setTextColor(R.color.lord_of_food_color);
//                            txtListChild.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    drawerLayout.closeDrawer(GravityCompat.START);
//                                }
//                            });
                        }
                        else{
                            //Toast.makeText(HomeActivity.this, ""+model.menuName, Toast.LENGTH_SHORT).show();
                            //product Details Fragment
                            AppCompatActivity activity = (AppCompatActivity) HomeActivity.this;
                            Fragment fragment = new ProductDetailsFragment();
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_main,fragment)
                                    .addToBackStack(null)
                                    .commit();
                            Bundle bundle = new Bundle();
                            bundle.putString("product_name",model.menuName);
                            bundle.putString("price",model.product_price);
                            bundle.putString("product_image",model.product_image);
                            bundle.putString("product_category",model.cate);
                            bundle.putString("product_desc",model.description_post);
                            bundle.putString("status",model.openStatus);
                            bundle.putInt("sectionId",model.sectionId);
                            fragment.setArguments(bundle);
                            //Toast.makeText(HomeActivity.this, "Name:"+model.menuName+"\n"+"Price:"+model.product_price+"\n"+"Status:"+model.openStatus, Toast.LENGTH_SHORT).show();
                            //product Details Fragment
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                }

                return false;
            }
        });
    }
    //Populate Recycler
    //Memory Management
    @Override
    protected void onDestroy() {
        super.onDestroy();
        freeMemory();
    }

    @Override
    protected void onStart() {
        super.onStart();
      checkUpdate();
    }

    public void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
    //Memory Management
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new Home_Fragment()).commit();
        final Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.frame_main);
        bottom_navigation.setSelectedItemId(R.id.action_home);
        if (fragmentInFrame instanceof Home_Fragment){
            //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Are you sure to want exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    finishAffinity();
                    //if user pressed "yes", then he is allowed to exit from application
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    //new
    private void checkUpdate() {
        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, HomeActivity.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                    HomeActivity.this.popupSnackbarForCompleteUpdate();
                } else {
                    Log.e(TAG, "checkForAppUpdateAvailability: something else");
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e(TAG, "onActivityResult: app download failed");
            }
        }
    }
    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAppUpdateManager != null) {
                    mAppUpdateManager.completeUpdate();
                }
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mAppUpdateManager != null) {
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }
    //new
}