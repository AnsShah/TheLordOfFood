package com.applicationdevelopers.lordoffood.View.Home.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.applicationdevelopers.lordoffood.Adapter.ImageSlider.SliderAdapterStartUp;
import com.applicationdevelopers.lordoffood.Adapter.ProductAdapter.ProductAdapter;
import com.applicationdevelopers.lordoffood.Controllers.Common;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.applicationdevelopers.lordoffood.Internet.AppStatus;
import com.applicationdevelopers.lordoffood.Model.Banner.BannerModel;
import com.applicationdevelopers.lordoffood.Model.ProductModel.ProductModel;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class Home_Fragment extends Fragment {
    LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4,linearLayout5,linearLayout6,linearlayout7,linearLayout8,linearLayout9,linearLayout10,linearLayout11,linearLayout12,linearLayout13,linearLayout14,linearLayout15,
    linearLayout16,linearLayout17,linearLayout18,linearLayout19,linearLayout20,linearLayout21,linearLayout22,linearLayout23,linearLayout24,linearLayout25;

    TextView dTextViewSection01,idTextViewDescriptionSection01,dTextViewSection02,idTextViewDescriptionSection02,
            dTextViewSection03,idTextViewDescriptionSection03,dTextViewSection04,idTextViewDescriptionSection04
            ,dTextViewSection05,idTextViewDescriptionSection05,dTextViewSection06,idTextViewDescriptionSection06,dTextViewSection07,idTextViewDescriptionSection07,
    dTextViewSection08,idTextViewDescriptionSection08,dTextViewSection09,idTextViewDescriptionSection09,dTextViewSection010,idTextViewDescriptionSection010,dTextViewSection011,idTextViewDescriptionSection011,dTextViewSection012,idTextViewDescriptionSection012,dTextViewSection013,idTextViewDescriptionSection013,dTextViewSection014,idTextViewDescriptionSection014,dTextViewSection015,idTextViewDescriptionSection015,
    dTextViewSection016,idTextViewDescriptionSection016,dTextViewSection017,idTextViewDescriptionSection017,dTextViewSection018,idTextViewDescriptionSection018,dTextViewSection019,idTextViewDescriptionSection019,dTextViewSection020,idTextViewDescriptionSection020,
            dTextViewSection021,idTextViewDescriptionSection021,dTextViewSection022,idTextViewDescriptionSection022,dTextViewSection023,idTextViewDescriptionSection023
            ,dTextViewSection024,idTextViewDescriptionSection024,dTextViewSection025,idTextViewDescriptionSection025;
    SliderView sliderView;
    private NetworkManager networkManager;
    String app_key="d72fbbccd9fe64c3a14f85d225a046f4";
    private List<ProductModel> productModels;
    private List<ProductModel> idSpecialChickenKarahi;
    private List<ProductModel> idLofSpecialMuttonKabab;
    private List<ProductModel> idSpecialChickenBurger;
    private List<ProductModel> idSpicyProducts;
    private List<ProductModel> idLOFSpecialPizza;
    private List<ProductModel> idLOFMidNightDeals;
    private List<ProductModel> idLOFSection08Model;
    private List<ProductModel> idLOFSection09Model;
    private List<ProductModel> idLOFSection010Model;
    private List<ProductModel> idLOFSection011Model;
    private List<ProductModel> idLOFSection012Model;
    private List<ProductModel> idLOFSection013Model;
    private List<ProductModel> idLOFSection014Model;
    private List<ProductModel> idLOFSection015Model;
    private List<ProductModel> idLOFSection016Model;
    private List<ProductModel> idLOFSection017Model;
    private List<ProductModel> idLOFSection018Model;
    private List<ProductModel> idLOFSection019Model;
    private List<ProductModel> idLOFSection020Model;
    private List<ProductModel> idLOFSection021Model;
    private List<ProductModel> idLOFSection022Model;
    private List<ProductModel> idLOFSection023Model;
    private List<ProductModel> idLOFSection024Model;
    private List<ProductModel> idLOFSection025Model;
    private List<BannerModel> bannerModelList;
    private SnapHelper snapHelper=new LinearSnapHelper();
    private SnapHelper snapHelper1=new LinearSnapHelper();
    private SnapHelper snapHelper2=new LinearSnapHelper();
    private SnapHelper snapHelper3=new LinearSnapHelper();
    private SnapHelper snapHelper4=new LinearSnapHelper();
    private SnapHelper snapHelper5=new LinearSnapHelper();
    private SnapHelper snapHelper6=new LinearSnapHelper();
    private SnapHelper snapHelper7=new LinearSnapHelper();
    private SnapHelper snapHelper8=new LinearSnapHelper();
    private SnapHelper snapHelper9=new LinearSnapHelper();
    private SnapHelper snapHelper10=new LinearSnapHelper();
    private SnapHelper snapHelper11=new LinearSnapHelper();
    private SnapHelper snapHelper12=new LinearSnapHelper();
    private SnapHelper snapHelper13=new LinearSnapHelper();
    private SnapHelper snapHelper14=new LinearSnapHelper();
    private SnapHelper snapHelper15=new LinearSnapHelper();
    private SnapHelper snapHelper16=new LinearSnapHelper();
    private SnapHelper snapHelper17=new LinearSnapHelper();
    private SnapHelper snapHelper18=new LinearSnapHelper();
    private SnapHelper snapHelper19=new LinearSnapHelper();
    private SnapHelper snapHelper20=new LinearSnapHelper();
    private SnapHelper snapHelper21=new LinearSnapHelper();
    private SnapHelper snapHelper22=new LinearSnapHelper();
    private SnapHelper snapHelper23=new LinearSnapHelper();
    private SnapHelper snapHelper24=new LinearSnapHelper();

    RecyclerView idProduct_Grid_SpecialHandi,idSpecialChickenKarahi_Recycler,idSpecialMuttonKabab_Recycler
            ,idSpecialChickenBurger_Recycler,idLatestSpicyProducts_Recycler,idLOFSpecialPizza_Recycler,idLOFMidNightDeals_Recycler
            ,idLOFSection08_Recycler,idLOFSection09_Recycler,idLOFSection010_Recycler,idLOFSection011_Recycler,idLOFSection012_Recycler,idLOFSection013_Recycler,idLOFSection014_Recycler,idLOFSection015_Recycler
            ,idLOFSection016_Recycler,idLOFSection017_Recycler,idLOFSection018_Recycler,idLOFSection019_Recycler,idLOFSection020_Recycler
            ,idLOFSection021_Recycler,idLOFSection022_Recycler,idLOFSection023_Recycler,idLOFSection024_Recycler,idLOFSection025_Recycler;
    ProgressBar idProgressBar;
    RelativeLayout idProgressBarRelative,idSection1,idRelSection2,idRelSection3,idRelSection4,idRelSection5,idRelSection6,idRelSection7,idRelSection8,idRelSection9,
            idRelSection10,idRelSection11,idRelSection12,idRelSection13,idRelSection14,idRelSection15,idRelSection16,idRelSection17,idRelSection18,
            idRelSection19,idRelSection20,idRelSection21,idRelSection22,idRelSection23,idRelSection24,idRelSection25;
    NestedScrollView main;
    ExpandableListAdapter expandableListAdapter;
    //new Scroll
    ProductAdapter productAdapter;
    int scrollCount = 0;
    //new Scroll
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home_, container, false);
        //Initialization
        initLayout(view);
        //Initialization
        initLinearLayout(view);
        return view;
    }
    private void initLinearLayout(View view){
        linearLayout1=view.findViewById(R.id.linear_value_packs1);
        linearLayout2=view.findViewById(R.id.linear_value_packs2);
        linearLayout3=view.findViewById(R.id.linear_value_packs3);
        linearLayout4=view.findViewById(R.id.linear_value_packs4);
        linearLayout5=view.findViewById(R.id.linear_value_packs5);
        linearLayout6=view.findViewById(R.id.linear_value_packs6);
        linearlayout7=view.findViewById(R.id.linear_value_packs7);
        linearLayout8=view.findViewById(R.id.linear_value_packs8);
        linearLayout9=view.findViewById(R.id.linear_value_packs9);
        linearLayout10=view.findViewById(R.id.linear_value_packs10);
        linearLayout11=view.findViewById(R.id.linear_value_packs11);
        linearLayout12=view.findViewById(R.id.linear_value_packs12);
        linearLayout13=view.findViewById(R.id.linear_value_packs13);
        linearLayout14=view.findViewById(R.id.linear_value_packs14);
        linearLayout15=view.findViewById(R.id.linear_value_packs15);
        linearLayout16=view.findViewById(R.id.linear_value_packs16);
        linearLayout17=view.findViewById(R.id.linear_value_packs17);
        linearLayout18=view.findViewById(R.id.linear_value_packs18);
        linearLayout19=view.findViewById(R.id.linear_value_packs19);
        linearLayout20=view.findViewById(R.id.linear_value_packs20);
        linearLayout21=view.findViewById(R.id.linear_value_packs21);
        linearLayout22=view.findViewById(R.id.linear_value_packs22);
        linearLayout23=view.findViewById(R.id.linear_value_packs23);
        linearLayout24=view.findViewById(R.id.linear_value_packs24);
        linearLayout25=view.findViewById(R.id.linear_value_packs25);
    }
    //Initialization
    private void initLayout(View view){
//        TextView
        dTextViewSection01= view.findViewById(R.id.idTextViewSection01);
        idTextViewDescriptionSection01= view.findViewById(R.id.idTextViewDescriptionSection01);
        dTextViewSection02= view.findViewById(R.id.idTextViewSection02);
        idTextViewDescriptionSection02= view.findViewById(R.id.idTextViewDescriptionSection02);
        dTextViewSection03= view.findViewById(R.id.idTextViewSection03);
        idTextViewDescriptionSection03= view.findViewById(R.id.idTextViewDescriptionSection03);
        dTextViewSection04= view.findViewById(R.id.idTextViewSection04);
        idTextViewDescriptionSection04= view.findViewById(R.id.idTextViewDescriptionSection04);
        dTextViewSection05= view.findViewById(R.id.idTextViewSection05);
        idTextViewDescriptionSection05= view.findViewById(R.id.idTextViewDescriptionSection05);
        dTextViewSection06= view.findViewById(R.id.idTextViewSection06);
        idTextViewDescriptionSection06= view.findViewById(R.id.idTextViewDescriptionSection06);
        dTextViewSection07= view.findViewById(R.id.idTextViewSection07);
        idTextViewDescriptionSection07= view.findViewById(R.id.idTextViewDescriptionSection07);
        dTextViewSection08= view.findViewById(R.id.idTextViewSection08);
        idTextViewDescriptionSection08= view.findViewById(R.id.idTextViewDescriptionSection08);
        dTextViewSection09= view.findViewById(R.id.idTextViewSection09);
        idTextViewDescriptionSection09= view.findViewById(R.id.idTextViewDescriptionSection09);
        dTextViewSection010= view.findViewById(R.id.idTextViewSection010);
        idTextViewDescriptionSection010= view.findViewById(R.id.idTextViewDescriptionSection010);
        dTextViewSection011= view.findViewById(R.id.idTextViewSection011);
        idTextViewDescriptionSection011= view.findViewById(R.id.idTextViewDescriptionSection011);
        dTextViewSection012= view.findViewById(R.id.idTextViewSection012);
        idTextViewDescriptionSection012= view.findViewById(R.id.idTextViewDescriptionSection012);
        dTextViewSection013= view.findViewById(R.id.idTextViewSection013);
        idTextViewDescriptionSection013= view.findViewById(R.id.idTextViewDescriptionSection013);
        dTextViewSection014= view.findViewById(R.id.idTextViewSection014);
        idTextViewDescriptionSection014= view.findViewById(R.id.idTextViewDescriptionSection014);
        dTextViewSection015= view.findViewById(R.id.idTextViewSection015);
        idTextViewDescriptionSection015= view.findViewById(R.id.idTextViewDescriptionSection015);
        dTextViewSection016= view.findViewById(R.id.idTextViewSection016);
        idTextViewDescriptionSection016= view.findViewById(R.id.idTextViewDescriptionSection016);
        dTextViewSection017= view.findViewById(R.id.idTextViewSection017);
        idTextViewDescriptionSection017= view.findViewById(R.id.idTextViewDescriptionSection017);
        dTextViewSection018= view.findViewById(R.id.idTextViewSection018);
        idTextViewDescriptionSection018= view.findViewById(R.id.idTextViewDescriptionSection018);
        dTextViewSection019= view.findViewById(R.id.idTextViewSection019);
        idTextViewDescriptionSection019= view.findViewById(R.id.idTextViewDescriptionSection019);
        dTextViewSection020= view.findViewById(R.id.idTextViewSection020);
        idTextViewDescriptionSection020= view.findViewById(R.id.idTextViewDescriptionSection020);
        dTextViewSection021= view.findViewById(R.id.idTextViewSection021);
        idTextViewDescriptionSection021= view.findViewById(R.id.idTextViewDescriptionSection021);
        dTextViewSection022= view.findViewById(R.id.idTextViewSection022);
        idTextViewDescriptionSection022= view.findViewById(R.id.idTextViewDescriptionSection022);
        dTextViewSection023= view.findViewById(R.id.idTextViewSection023);
        idTextViewDescriptionSection023= view.findViewById(R.id.idTextViewDescriptionSection023);
        dTextViewSection024= view.findViewById(R.id.idTextViewSection024);
        idTextViewDescriptionSection024= view.findViewById(R.id.idTextViewDescriptionSection024);
        dTextViewSection025= view.findViewById(R.id.idTextViewSection025);
        idTextViewDescriptionSection025= view.findViewById(R.id.idTextViewDescriptionSection025);

//        Relative Layouts
        idSection1=(RelativeLayout) view.findViewById(R.id.idSection1);
        idRelSection2=(RelativeLayout) view.findViewById(R.id.idRelSection2);
        idRelSection3=(RelativeLayout) view.findViewById(R.id.idRelSection3);
        idRelSection4=(RelativeLayout) view.findViewById(R.id.idRelSection4);
        idRelSection5=(RelativeLayout) view.findViewById(R.id.idRelSection5);
        idRelSection6=(RelativeLayout) view.findViewById(R.id.idRelSection6);
        idRelSection7=(RelativeLayout) view.findViewById(R.id.idRelSection7);
        idRelSection8=(RelativeLayout) view.findViewById(R.id.idRelSection8);
        idRelSection9=(RelativeLayout) view.findViewById(R.id.idRelSection9);
        idRelSection10=(RelativeLayout) view.findViewById(R.id.idRelSection10);
        idRelSection11=(RelativeLayout) view.findViewById(R.id.idRelSection11);
        idRelSection12=(RelativeLayout) view.findViewById(R.id.idRelSection12);
        idRelSection13=(RelativeLayout) view.findViewById(R.id.idRelSection13);
        idRelSection14=(RelativeLayout) view.findViewById(R.id.idRelSection14);
        idRelSection15=(RelativeLayout) view.findViewById(R.id.idRelSection15);
        idRelSection16=(RelativeLayout) view.findViewById(R.id.idRelSection16);
        idRelSection17=(RelativeLayout) view.findViewById(R.id.idRelSection17);
        idRelSection18=(RelativeLayout) view.findViewById(R.id.idRelSection18);
        idRelSection19=(RelativeLayout) view.findViewById(R.id.idRelSection19);
        idRelSection20=(RelativeLayout) view.findViewById(R.id.idRelSection20);
        idRelSection21=(RelativeLayout) view.findViewById(R.id.idRelSection21);
        idRelSection22=(RelativeLayout) view.findViewById(R.id.idRelSection22);
        idRelSection23=(RelativeLayout) view.findViewById(R.id.idRelSection23);
        idRelSection24=(RelativeLayout) view.findViewById(R.id.idRelSection24);
        idRelSection25=(RelativeLayout) view.findViewById(R.id.idRelSection25);
//        Relative Layouts

//        TextView
        sliderView=(SliderView) view.findViewById(R.id.imageSlider);
        idProduct_Grid_SpecialHandi=(RecyclerView) view.findViewById(R.id.idProduct_Grid_SpecialHandi);
        idSpecialChickenKarahi_Recycler=(RecyclerView) view.findViewById(R.id.idSpecialChickenKarahi_Recycler);
        idSpecialMuttonKabab_Recycler=(RecyclerView) view.findViewById(R.id.idSpecialMuttonKabab_Recycler);
        idSpecialChickenBurger_Recycler=(RecyclerView) view.findViewById(R.id.idSpecialChickenBurger_Recycler);
        idLatestSpicyProducts_Recycler=(RecyclerView) view.findViewById(R.id.idLatestSpicyProducts_Recycler);
        idLOFSpecialPizza_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSpecialPizza_Recycler);
        idLOFMidNightDeals_Recycler=(RecyclerView) view.findViewById(R.id.idLOFMidNightDeals_Recycler);
        idLOFSection08_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection08_Recycler);
        idLOFSection09_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection09_Recycler);
        idLOFSection010_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection010_Recycler);
        idLOFSection011_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection011_Recycler);
        idLOFSection012_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection012_Recycler);
        idLOFSection013_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection013_Recycler);
        idLOFSection014_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection014_Recycler);
        idLOFSection015_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection015_Recycler);
        idLOFSection016_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection016_Recycler);
        idLOFSection017_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection017_Recycler);
        idLOFSection018_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection018_Recycler);
        idLOFSection019_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection019_Recycler);
        idLOFSection020_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection020_Recycler);
        idLOFSection021_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection021_Recycler);
        idLOFSection022_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection022_Recycler);
        idLOFSection023_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection023_Recycler);
        idLOFSection024_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection024_Recycler);
        idLOFSection025_Recycler=(RecyclerView) view.findViewById(R.id.idLOFSection025_Recycler);
        idProgressBar=(ProgressBar) view.findViewById(R.id.idProgressBar);
        idProgressBarRelative=(RelativeLayout) view.findViewById(R.id.idProgressBarRelative);
        main=(NestedScrollView) view.findViewById(R.id.main);
        LinearLayoutManager managers1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idProduct_Grid_SpecialHandi.setLayoutManager(managers1);
        LinearLayoutManager managers11 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idSpecialChickenKarahi_Recycler.setLayoutManager(managers11);
        LinearLayoutManager special_chicken_dish_managers = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idSpecialMuttonKabab_Recycler.setLayoutManager(special_chicken_dish_managers);
        LinearLayoutManager special_mutton_dish_managers = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idSpecialChickenBurger_Recycler.setLayoutManager(special_mutton_dish_managers);
        LinearLayoutManager rice_managers = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLatestSpicyProducts_Recycler.setLayoutManager(rice_managers);
        LinearLayoutManager desserts_managers = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSpecialPizza_Recycler.setLayoutManager(desserts_managers);
        LinearLayoutManager midnightDeals_managers = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFMidNightDeals_Recycler.setLayoutManager(midnightDeals_managers);
        LinearLayoutManager managersSection8 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection08_Recycler.setLayoutManager(managersSection8);
        LinearLayoutManager managersSection10 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection09_Recycler.setLayoutManager(managersSection10);
        LinearLayoutManager managersSection11 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection010_Recycler.setLayoutManager(managersSection11);
        LinearLayoutManager managersSection12 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection011_Recycler.setLayoutManager(managersSection12);
        LinearLayoutManager managersSection13 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection012_Recycler.setLayoutManager(managersSection13);
        LinearLayoutManager managersSection14 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection013_Recycler.setLayoutManager(managersSection14);
        LinearLayoutManager managersSection15 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection014_Recycler.setLayoutManager(managersSection15);
        LinearLayoutManager managersSection16 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection015_Recycler.setLayoutManager(managersSection16);
        LinearLayoutManager managersSection17 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection016_Recycler.setLayoutManager(managersSection17);
        LinearLayoutManager managersSection18 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection017_Recycler.setLayoutManager(managersSection18);
        LinearLayoutManager managersSection19 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection018_Recycler.setLayoutManager(managersSection19);
        LinearLayoutManager managersSection20 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection019_Recycler.setLayoutManager(managersSection20);
        LinearLayoutManager managersSection9 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection020_Recycler.setLayoutManager(managersSection9);
        LinearLayoutManager managersSection21 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection021_Recycler.setLayoutManager(managersSection21);
        LinearLayoutManager managersSection22 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection022_Recycler.setLayoutManager(managersSection22);
        LinearLayoutManager managersSection23 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection023_Recycler.setLayoutManager(managersSection23);
        LinearLayoutManager managersSection24 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection024_Recycler.setLayoutManager(managersSection24);
        LinearLayoutManager managersSection25 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        idLOFSection025_Recycler.setLayoutManager(managersSection25);
//        Adapter
        //networkManager=new NetworkManager(getContext());
        getAllData(app_key);
        getBanners();
    }
    //Get Data From Api
    private void getAllData(String app_key){
        if (!AppStatus.getInstance(getContext()).isOnline() && !AppStatus.getInstance(getContext()).isNetworkOnline2()) {
            Toast.makeText(getContext(), "No Internet Access", Toast.LENGTH_SHORT).show();
        }
        else {
            idProgressBarRelative.setVisibility(View.VISIBLE);
//            final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait", "Fetching Data In Progress......", false, false);
//            idProgressBar.setVisibility(View.VISIBLE);
            //initialize Arrays
            productModels = new ArrayList<>();
            idSpecialChickenKarahi = new ArrayList<>();
            idLofSpecialMuttonKabab = new ArrayList<>();
            idSpecialChickenBurger = new ArrayList<>();
            idSpicyProducts = new ArrayList<>();
            idLOFSpecialPizza = new ArrayList<>();
            idLOFMidNightDeals = new ArrayList<>();
            idLOFSection08Model=new ArrayList<>();
            idLOFSection09Model=new ArrayList<>();
            idLOFSection010Model=new ArrayList<>();
            idLOFSection011Model=new ArrayList<>();
            idLOFSection012Model=new ArrayList<>();
            idLOFSection013Model=new ArrayList<>();
            idLOFSection014Model=new ArrayList<>();
            idLOFSection015Model=new ArrayList<>();
            idLOFSection016Model=new ArrayList<>();
            idLOFSection017Model=new ArrayList<>();
            idLOFSection018Model=new ArrayList<>();
            idLOFSection019Model=new ArrayList<>();
            idLOFSection020Model=new ArrayList<>();
            idLOFSection021Model=new ArrayList<>();
            idLOFSection022Model=new ArrayList<>();
            idLOFSection023Model=new ArrayList<>();
            idLOFSection024Model=new ArrayList<>();
            idLOFSection025Model=new ArrayList<>();
            //initialize Arrays
            networkManager = new NetworkManager(getContext());
            String url = "https://www.taster.pk/api/sync/sections";
            JSONObject jsonObject = new JSONObject();
            networkManager.jsonObjectRequest2(getContext(),app_key, jsonObject, url, Request.Method.POST, new NetworkCallback() {
                @SuppressLint("LongLogTag")
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        JSONObject infoObj = obj.getJSONObject("data");
                        if (obj.getInt("status_code") == 200 && obj.getString("status").equals("success")) {
//                            idProgressBar.setVisibility(View.GONE);
//                            progressDialog.dismiss();
                            idProgressBarRelative.setVisibility(View.GONE);
                            main.setVisibility(View.VISIBLE);
                            JSONArray jsonArraySections = infoObj.getJSONArray("sections");
                            Log.wtf("Array Size:"," -> "+jsonArraySections.length());
                            //Special Chicken Handi
                            {
                                JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(0);
                                String desc=jsonObjectGetData.getString("sub_title");
                                dTextViewSection01.setText(jsonObjectGetData.getString("name"));
                                if (desc.isEmpty() || desc.equals("")){
                                    idTextViewDescriptionSection01.setVisibility(View.GONE);
                                }
                                else {
                                    idTextViewDescriptionSection01.setText(jsonObjectGetData.getString("sub_title"));
                                }
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
                                    productModels.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection01.getText().toString(),0));
                                }
//                                //new Scroll Recycler Auto
//                                final LinearLayoutManager linearLayoutManagerProduct = new LinearLayoutManager(getContext()){
//                                    @Override
//                                    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
//                                        try {
//                                            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(requireContext()) {
//                                                private static final float SPEED = 3500f;// Change this value (default=25f)
//
//                                                @Override
//                                                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
//                                                    return SPEED / displayMetrics.densityDpi;
//                                                }
//                                            };
//                                            smoothScroller.setTargetPosition(position);
//                                            startSmoothScroll(smoothScroller);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                };
//                                productAdapter = new ProductAdapter(getContext(), productModels,idProduct_Grid_SpecialHandi);
//                                autoScrollAnother();
//                                linearLayoutManagerProduct.setOrientation(LinearLayoutManager.HORIZONTAL);
//                                idProduct_Grid_SpecialHandi.setLayoutManager(linearLayoutManagerProduct);
//                                idProduct_Grid_SpecialHandi.setHasFixedSize(true);
//                                idProduct_Grid_SpecialHandi.setItemViewCacheSize(1000);
//                                idProduct_Grid_SpecialHandi.setDrawingCacheEnabled(true);
//                                idProduct_Grid_SpecialHandi.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//                                idProduct_Grid_SpecialHandi.setAdapter(productAdapter);
//                                //new Scroll Recycler Auto
                                ProductAdapter productadapter = new ProductAdapter(getContext(), productModels,idProduct_Grid_SpecialHandi);
                                snapHelper.attachToRecyclerView(idProduct_Grid_SpecialHandi);
                                idProduct_Grid_SpecialHandi.setAdapter(productadapter);
                                Log.d(dTextViewSection01.getText().toString(), jsonArray1.toString());
                            }
                            {
                            //Special Chicken Handi
                            //Special Chicken Karahi
                            {
                                if (jsonArraySections.length()<=1){
                                    dTextViewSection02.setVisibility(View.GONE);
                                    idTextViewDescriptionSection02.setVisibility(View.GONE);
                                    idSpecialChickenKarahi_Recycler.setVisibility(View.GONE);
                                    idRelSection2.setVisibility(View.GONE);
                                }
                                else {

                                    linearLayout2.setVisibility(View.VISIBLE);
                                    dTextViewSection02.setVisibility(View.VISIBLE);
                                    idSpecialChickenKarahi_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(1);
                                    dTextViewSection02.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection02.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection02.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Bar = new ArrayList<>();*/
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
                                        idSpecialChickenKarahi.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection02.getText().toString(),1));
                                    }
                                    ProductAdapter VALUE_PACKS_adapter = new ProductAdapter(getContext(), idSpecialChickenKarahi,idSpecialChickenKarahi_Recycler);
                                    idSpecialChickenKarahi_Recycler.setAdapter(VALUE_PACKS_adapter);
                                    snapHelper1.attachToRecyclerView(idSpecialChickenKarahi_Recycler);
                                    Log.d(dTextViewSection02.getText().toString(), jsonArray1.toString());
                                }
                            }
                            //Special Chicken Karahi
                            //LOF Special Mutton Kabab
                            {
                                if (jsonArraySections.length()<=2){
                                    dTextViewSection03.setVisibility(View.GONE);
                                    idTextViewDescriptionSection03.setVisibility(View.GONE);
                                    idSpecialMuttonKabab_Recycler.setVisibility(View.GONE);
                                    idRelSection3.setVisibility(View.GONE);
                                }
                                else {

                                    linearLayout3.setVisibility(View.VISIBLE);
                                    dTextViewSection03.setVisibility(View.VISIBLE);
                                    idSpecialMuttonKabab_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(2);
                                    dTextViewSection03.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection03.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection03.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Chicken = new ArrayList<>();*/
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
                                        idLofSpecialMuttonKabab.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection03.getText().toString(),2));
                                    }
                                    ProductAdapter MINERAL_WATER_adapter = new ProductAdapter(getContext(), idLofSpecialMuttonKabab,idSpecialMuttonKabab_Recycler);
                                    idSpecialMuttonKabab_Recycler.setAdapter(MINERAL_WATER_adapter);
                                    snapHelper2.attachToRecyclerView(idSpecialMuttonKabab_Recycler);
                                    Log.d(dTextViewSection03.getText().toString(), jsonArray1.toString());
                                }
                            }
                            //LOF Special Mutton Kabab
                            //Special Chicken burger
                            {
                                if (jsonArraySections.length()<=3){
                                    dTextViewSection04.setVisibility(View.GONE);
                                    idTextViewDescriptionSection04.setVisibility(View.GONE);
                                    idSpecialChickenBurger_Recycler.setVisibility(View.GONE);
                                    idRelSection4.setVisibility(View.GONE);
                                }
                                else {

                                    linearLayout4.setVisibility(View.VISIBLE);
                                    dTextViewSection04.setVisibility(View.VISIBLE);
                                    idSpecialChickenBurger_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(3);
                                    dTextViewSection04.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection04.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection04.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Mutton = new ArrayList<>();*/
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
                                        idSpecialChickenBurger.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection04.getText().toString(),3));
                                    }
                                    ProductAdapter SOFT_DRINKS_adapter = new ProductAdapter(getContext(), idSpecialChickenBurger,idSpecialChickenBurger_Recycler);
                                    idSpecialChickenBurger_Recycler.setAdapter(SOFT_DRINKS_adapter);
                                    snapHelper3.attachToRecyclerView(idSpecialChickenBurger_Recycler);
                                    Log.d(dTextViewSection04.getText().toString(), jsonArray1.toString());
                                }
                            }
                            //Special Chicken burger
                            //Latest Spicy Products
                            {
                                if (jsonArraySections.length()<=4){
                                    dTextViewSection05.setVisibility(View.GONE);
                                    idTextViewDescriptionSection05.setVisibility(View.GONE);
                                    idLatestSpicyProducts_Recycler.setVisibility(View.GONE);
                                    idRelSection5.setVisibility(View.GONE);
                                }
                                else {

                                    linearLayout5.setVisibility(View.VISIBLE);
                                    dTextViewSection05.setVisibility(View.VISIBLE);
                                    idLatestSpicyProducts_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(4);
                                    dTextViewSection05.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection05.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection05.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Rice = new ArrayList<>();*/
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
                                        idSpicyProducts.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection05.getText().toString(),4));
                                    }
                                    ProductAdapter rice_adapter = new ProductAdapter(getContext(), idSpicyProducts,idLatestSpicyProducts_Recycler);
                                    idLatestSpicyProducts_Recycler.setAdapter(rice_adapter);
                                    snapHelper4.attachToRecyclerView(idLatestSpicyProducts_Recycler);
                                    Log.d(dTextViewSection05.getText().toString(), jsonArray1.toString());
                                }
                            }
                            //Latest Spicy Products
                            //LOF Special Pizza
                            {
                                if (jsonArraySections.length()<=5){
                                    dTextViewSection06.setVisibility(View.GONE);
                                    idTextViewDescriptionSection06.setVisibility(View.GONE);
                                    idLOFSpecialPizza_Recycler.setVisibility(View.GONE);
                                    idRelSection6.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout6.setVisibility(View.VISIBLE);
                                    dTextViewSection06.setVisibility(View.VISIBLE);
                                    idLOFSpecialPizza_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(5);
                                    dTextViewSection06.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection06.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection06.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSpecialPizza.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection06.getText().toString(),5));
                                    }
                                    ProductAdapter desserts_adapter = new ProductAdapter(getContext(), idLOFSpecialPizza,idLOFSpecialPizza_Recycler);
                                    idLOFSpecialPizza_Recycler.setAdapter(desserts_adapter);
                                    snapHelper5.attachToRecyclerView(idLOFSpecialPizza_Recycler);
                                    Log.d(dTextViewSection06.getText().toString(), jsonArray1.toString());
                                }
                            }
                            //LOF Special Pizza
                            //LOF Midnight Deals
                            {
                                if (jsonArraySections.length()<=6){
                                    dTextViewSection07.setVisibility(View.GONE);
                                    idTextViewDescriptionSection07.setVisibility(View.GONE);
                                    idLOFMidNightDeals_Recycler.setVisibility(View.GONE);
                                    idRelSection7.setVisibility(View.GONE);
                                }
                                else {
                                    linearlayout7.setVisibility(View.VISIBLE);
                                    dTextViewSection07.setVisibility(View.VISIBLE);
                                    idLOFMidNightDeals_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(6);
                                    dTextViewSection07.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection07.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection07.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFMidNightDeals.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection07.getText().toString(),6));
                                    }
                                    ProductAdapter midnight_adapter = new ProductAdapter(getContext(), idLOFMidNightDeals,idLOFMidNightDeals_Recycler);
                                    idLOFMidNightDeals_Recycler.setAdapter(midnight_adapter);
                                    snapHelper6.attachToRecyclerView(idLOFMidNightDeals_Recycler);
                                    Log.d(dTextViewSection07.getText().toString(), jsonArray1.toString());
                                }
                            }
                            //LOF Midnight Deals
                            {
                                if (jsonArraySections.length()<=7){
                                    dTextViewSection08.setVisibility(View.GONE);
                                    idTextViewDescriptionSection08.setVisibility(View.GONE);
                                    idLOFSection08_Recycler.setVisibility(View.GONE);
                                    idRelSection8.setVisibility(View.GONE);

                                }
                                else {
                                    linearLayout8.setVisibility(View.VISIBLE);
                                    dTextViewSection08.setVisibility(View.VISIBLE);
                                    idLOFSection08_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(7);
                                    dTextViewSection08.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection08.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection08.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection08Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection08.getText().toString(),7));
                                    }
                                    ProductAdapter section08_adapter = new ProductAdapter(getContext(), idLOFSection08Model,idLOFSection08_Recycler);
                                    idLOFSection08_Recycler.setAdapter(section08_adapter);
                                    snapHelper7.attachToRecyclerView(idLOFSection08_Recycler);
                                    Log.d(dTextViewSection08.getText().toString(), jsonArray1.toString());
                                }
                            }
                            //new 9 to 20
                            {
                                if (jsonArraySections.length()<=8){
                                    dTextViewSection09.setVisibility(View.GONE);
                                    idTextViewDescriptionSection09.setVisibility(View.GONE);
                                    idLOFSection09_Recycler.setVisibility(View.GONE);
                                    idRelSection9.setVisibility(View.GONE);
                                }
                                else {
                                    dTextViewSection09.setVisibility(View.VISIBLE);
                                    idLOFSection09_Recycler.setVisibility(View.VISIBLE);
                                    linearLayout9.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(8);
                                    dTextViewSection09.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection09.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection09.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection09Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection09.getText().toString(),8));
                                    }
                                    ProductAdapter section09_adapter = new ProductAdapter(getContext(), idLOFSection09Model,idLOFSection09_Recycler);
                                    idLOFSection09_Recycler.setAdapter(section09_adapter);
                                    snapHelper8.attachToRecyclerView(idLOFSection09_Recycler);
                                    Log.d(dTextViewSection09.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=9){
                                    dTextViewSection010.setVisibility(View.GONE);
                                    idTextViewDescriptionSection010.setVisibility(View.GONE);
                                    idLOFSection010_Recycler.setVisibility(View.GONE);
                                    idRelSection10.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout10.setVisibility(View.VISIBLE);
                                    dTextViewSection010.setVisibility(View.VISIBLE);
                                    idLOFSection010_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(9);
                                    dTextViewSection010.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection010.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection010.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection010Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection010.getText().toString(),9));
                                    }
                                    ProductAdapter section08_adapter = new ProductAdapter(getContext(), idLOFSection010Model,idLOFSection010_Recycler);
                                    idLOFSection010_Recycler.setAdapter(section08_adapter);
                                    snapHelper9.attachToRecyclerView(idLOFSection010_Recycler);
                                    Log.d(dTextViewSection010.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=10){
                                    dTextViewSection011.setVisibility(View.GONE);
                                    idTextViewDescriptionSection011.setVisibility(View.GONE);
                                    idLOFSection011_Recycler.setVisibility(View.GONE);
//                                    idRelSection11.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout11.setVisibility(View.VISIBLE);
                                    dTextViewSection011.setVisibility(View.VISIBLE);
                                    idLOFSection011_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(10);
                                    dTextViewSection011.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection011.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection011.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection011Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection011.getText().toString(),10));
                                    }
                                    ProductAdapter section011_adapter = new ProductAdapter(getContext(), idLOFSection011Model,idLOFSection011_Recycler);
                                    idLOFSection011_Recycler.setAdapter(section011_adapter);
                                    snapHelper10.attachToRecyclerView(idLOFSection011_Recycler);
                                    Log.d(dTextViewSection011.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=11){
                                    dTextViewSection012.setVisibility(View.GONE);
                                    idTextViewDescriptionSection012.setVisibility(View.GONE);
                                    idLOFSection012_Recycler.setVisibility(View.GONE);
//                                    idRelSection12.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout12.setVisibility(View.VISIBLE);
                                    dTextViewSection012.setVisibility(View.VISIBLE);
                                    idLOFSection012_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(11);
                                    dTextViewSection012.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection012.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection012.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection012Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection012.getText().toString(),11));
                                    }
                                    ProductAdapter section08_adapter = new ProductAdapter(getContext(), idLOFSection012Model,idLOFSection012_Recycler);
                                    idLOFSection012_Recycler.setAdapter(section08_adapter);
                                    snapHelper11.attachToRecyclerView(idLOFSection012_Recycler);
                                    Log.d(dTextViewSection012.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=12){
                                    dTextViewSection013.setVisibility(View.GONE);
                                    idTextViewDescriptionSection013.setVisibility(View.GONE);
                                    idLOFSection013_Recycler.setVisibility(View.GONE);
//                                    idRelSection13.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout13.setVisibility(View.VISIBLE);
                                    dTextViewSection013.setVisibility(View.VISIBLE);
                                    idLOFSection013_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(12);
                                    dTextViewSection013.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection013.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection013.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection013Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection013.getText().toString(),12));
                                    }
                                    ProductAdapter section012_adapter = new ProductAdapter(getContext(), idLOFSection013Model,idLOFSection013_Recycler);
                                    idLOFSection013_Recycler.setAdapter(section012_adapter);
                                    snapHelper12.attachToRecyclerView(idLOFSection013_Recycler);
                                    Log.d(dTextViewSection013.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=13){
                                    dTextViewSection014.setVisibility(View.GONE);
                                    idTextViewDescriptionSection014.setVisibility(View.GONE);
                                    idLOFSection014_Recycler.setVisibility(View.GONE);
//                                    idRelSection14.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout14.setVisibility(View.VISIBLE);
                                    dTextViewSection014.setVisibility(View.VISIBLE);
                                    idLOFSection014_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(13);
                                    dTextViewSection014.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection014.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection014.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection014Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection014.getText().toString(),13));
                                    }
                                    ProductAdapter section014_adapter = new ProductAdapter(getContext(), idLOFSection014Model,idLOFSection014_Recycler);
                                    idLOFSection014_Recycler.setAdapter(section014_adapter);
                                    snapHelper13.attachToRecyclerView(idLOFSection014_Recycler);
                                    Log.d(dTextViewSection014.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=14){
                                    dTextViewSection015.setVisibility(View.GONE);
                                    idTextViewDescriptionSection015.setVisibility(View.GONE);
                                    idLOFSection015_Recycler.setVisibility(View.GONE);
//                                    idRelSection15.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout15.setVisibility(View.VISIBLE);
//                                    idRelSection15.setVisibility(View.VISIBLE);
                                    dTextViewSection015.setVisibility(View.VISIBLE);
                                    idLOFSection015_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(14);
                                    dTextViewSection015.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection015.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection015.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection015Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection015.getText().toString(),14));
                                    }
                                    ProductAdapter section015_adapter = new ProductAdapter(getContext(), idLOFSection015Model,idLOFSection015_Recycler);
                                    idLOFSection015_Recycler.setAdapter(section015_adapter);
                                    snapHelper14.attachToRecyclerView(idLOFSection015_Recycler);
                                    Log.d(dTextViewSection015.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=15){
                                    dTextViewSection016.setVisibility(View.GONE);
                                    idTextViewDescriptionSection016.setVisibility(View.GONE);
                                    idLOFSection016_Recycler.setVisibility(View.GONE);
//                                    idRelSection16.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout16.setVisibility(View.VISIBLE);
//                                    idRelSection16.setVisibility(View.VISIBLE);
                                    dTextViewSection016.setVisibility(View.VISIBLE);
                                    idLOFSection016_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(15);
                                    dTextViewSection016.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection016.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection016.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection016Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection016.getText().toString(),15));
                                    }
                                    ProductAdapter section016_adapter = new ProductAdapter(getContext(), idLOFSection016Model,idLOFSection016_Recycler);
                                    idLOFSection016_Recycler.setAdapter(section016_adapter);
                                    snapHelper15.attachToRecyclerView(idLOFSection016_Recycler);
                                    Log.d(dTextViewSection016.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=16){
                                    dTextViewSection017.setVisibility(View.GONE);
                                    idTextViewDescriptionSection017.setVisibility(View.GONE);
                                    idLOFSection017_Recycler.setVisibility(View.GONE);
//                                    idRelSection17.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout17.setVisibility(View.VISIBLE);
//                                    idRelSection17.setVisibility(View.VISIBLE);
                                    dTextViewSection017.setVisibility(View.VISIBLE);
                                    idLOFSection017_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(16);
                                    dTextViewSection017.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection017.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection017.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection017Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection017.getText().toString(),16));
                                    }
                                    ProductAdapter section017_adapter = new ProductAdapter(getContext(), idLOFSection017Model,idLOFSection017_Recycler);
                                    idLOFSection017_Recycler.setAdapter(section017_adapter);
                                    snapHelper16.attachToRecyclerView(idLOFSection017_Recycler);
                                    Log.d(dTextViewSection017.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=17){
                                    dTextViewSection018.setVisibility(View.GONE);
                                    idTextViewDescriptionSection018.setVisibility(View.GONE);
                                    idLOFSection018_Recycler.setVisibility(View.GONE);
//                                    idRelSection18.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout18.setVisibility(View.VISIBLE);
//                                    idRelSection18.setVisibility(View.VISIBLE);
                                    dTextViewSection018.setVisibility(View.VISIBLE);
                                    idLOFSection018_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(17);
                                    dTextViewSection018.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection018.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection018.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection018Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection018.getText().toString(),17));
                                    }
                                    ProductAdapter section018_adapter = new ProductAdapter(getContext(), idLOFSection018Model,idLOFSection018_Recycler);
                                    idLOFSection018_Recycler.setAdapter(section018_adapter);
                                    snapHelper17.attachToRecyclerView(idLOFSection018_Recycler);
                                    Log.d(dTextViewSection018.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=18){
                                    dTextViewSection019.setVisibility(View.GONE);
                                    idTextViewDescriptionSection019.setVisibility(View.GONE);
                                    idLOFSection019_Recycler.setVisibility(View.GONE);
//                                    idRelSection19.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout19.setVisibility(View.VISIBLE);
//                                    idRelSection19.setVisibility(View.VISIBLE);
                                    dTextViewSection019.setVisibility(View.VISIBLE);
                                    idLOFSection019_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(18);
                                    dTextViewSection019.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection019.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection019.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection019Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection019.getText().toString(),18));
                                    }
                                    ProductAdapter section019_adapter = new ProductAdapter(getContext(), idLOFSection019Model,idLOFSection019_Recycler);
                                    idLOFSection019_Recycler.setAdapter(section019_adapter);
                                    snapHelper18.attachToRecyclerView(idLOFSection019_Recycler);
                                    Log.d(dTextViewSection019.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=19){
                                    dTextViewSection020.setVisibility(View.GONE);
                                    idTextViewDescriptionSection020.setVisibility(View.GONE);
                                    idLOFSection020_Recycler.setVisibility(View.GONE);
//                                    idRelSection20.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout20.setVisibility(View.VISIBLE);
//                                    idRelSection20.setVisibility(View.VISIBLE);
                                    dTextViewSection020.setVisibility(View.VISIBLE);
                                    idLOFSection020_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(19);
                                    dTextViewSection020.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection020.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection020.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection020Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection020.getText().toString(),19));
                                    }
                                    ProductAdapter section020_adapter = new ProductAdapter(getContext(), idLOFSection020Model,idLOFSection020_Recycler);
                                    idLOFSection020_Recycler.setAdapter(section020_adapter);
                                    snapHelper19.attachToRecyclerView(idLOFSection020_Recycler);
                                    Log.d(dTextViewSection020.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=20){
                                    dTextViewSection021.setVisibility(View.GONE);
                                    idTextViewDescriptionSection021.setVisibility(View.GONE);
                                    idLOFSection021_Recycler.setVisibility(View.GONE);
//                                    idRelSection21.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout21.setVisibility(View.VISIBLE);
//                                    idRelSection21.setVisibility(View.VISIBLE);
                                    dTextViewSection021.setVisibility(View.VISIBLE);
                                    idLOFSection021_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(20);
                                    dTextViewSection021.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection021.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection021.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection021Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection020.getText().toString(),20));
                                    }
                                    ProductAdapter section021_adapter = new ProductAdapter(getContext(), idLOFSection021Model,idLOFSection021_Recycler);
                                    idLOFSection021_Recycler.setAdapter(section021_adapter);
                                    snapHelper20.attachToRecyclerView(idLOFSection021_Recycler);
                                    Log.d(dTextViewSection021.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=21){
                                    dTextViewSection022.setVisibility(View.GONE);
                                    idTextViewDescriptionSection022.setVisibility(View.GONE);
                                    idLOFSection022_Recycler.setVisibility(View.GONE);
//                                    idRelSection22.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout22.setVisibility(View.VISIBLE);
//                                    idRelSection22.setVisibility(View.VISIBLE);
                                    dTextViewSection022.setVisibility(View.VISIBLE);
                                    idLOFSection022_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(21);
                                    dTextViewSection022.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection022.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection022.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection022Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection020.getText().toString(),21));
                                    }
                                    ProductAdapter section022_adapter = new ProductAdapter(getContext(), idLOFSection022Model,idLOFSection022_Recycler);
                                    idLOFSection022_Recycler.setAdapter(section022_adapter);
                                    snapHelper21.attachToRecyclerView(idLOFSection022_Recycler);
                                    Log.d(dTextViewSection022.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=22){
                                    dTextViewSection023.setVisibility(View.GONE);
                                    idTextViewDescriptionSection023.setVisibility(View.GONE);
                                    idLOFSection023_Recycler.setVisibility(View.GONE);
//                                    idRelSection23.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout23.setVisibility(View.VISIBLE);
//                                    idRelSection23.setVisibility(View.VISIBLE);
                                    dTextViewSection023.setVisibility(View.VISIBLE);
                                    idLOFSection023_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(22);
                                    dTextViewSection023.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection023.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection023.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection023Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection020.getText().toString(),22));
                                    }
                                    ProductAdapter section023_adapter = new ProductAdapter(getContext(), idLOFSection023Model,idLOFSection023_Recycler);
                                    idLOFSection023_Recycler.setAdapter(section023_adapter);
                                    snapHelper22.attachToRecyclerView(idLOFSection023_Recycler);
                                    Log.d(dTextViewSection023.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=23){
                                    dTextViewSection024.setVisibility(View.GONE);
                                    idTextViewDescriptionSection024.setVisibility(View.GONE);
                                    idLOFSection024_Recycler.setVisibility(View.GONE);
//                                    idRelSection24.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout24.setVisibility(View.VISIBLE);
//                                    idRelSection24.setVisibility(View.VISIBLE);
                                    dTextViewSection024.setVisibility(View.VISIBLE);
                                    idLOFSection024_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(23);
                                    dTextViewSection024.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection024.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection024.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection024Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection020.getText().toString(),23));
                                    }
                                    ProductAdapter section024_adapter = new ProductAdapter(getContext(), idLOFSection024Model,idLOFSection024_Recycler);
                                    idLOFSection024_Recycler.setAdapter(section024_adapter);
                                    snapHelper23.attachToRecyclerView(idLOFSection024_Recycler);
                                    Log.d(dTextViewSection024.getText().toString(), jsonArray1.toString());
                                }
                            }
                            {
                                if (jsonArraySections.length()<=24){
                                    dTextViewSection025.setVisibility(View.GONE);
                                    idTextViewDescriptionSection025.setVisibility(View.GONE);
                                    idLOFSection025_Recycler.setVisibility(View.GONE);
//                                    idRelSection25.setVisibility(View.GONE);
                                }
                                else {
                                    linearLayout25.setVisibility(View.VISIBLE);
//                                    idRelSection25.setVisibility(View.VISIBLE);
                                    dTextViewSection025.setVisibility(View.VISIBLE);
                                    idLOFSection025_Recycler.setVisibility(View.VISIBLE);
                                    JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(24);
                                    dTextViewSection025.setText(jsonObjectGetData.getString("name"));
                                    String desc=jsonObjectGetData.getString("sub_title");
//                                    Toast.makeText(getContext(), "Name:"+jsonObjectGetData.getString("name"), Toast.LENGTH_SHORT).show();
                                    if (desc.isEmpty() || desc.equals("")){
                                        idTextViewDescriptionSection025.setVisibility(View.GONE);
                                    }
                                    else {
                                        idTextViewDescriptionSection025.setText(jsonObjectGetData.getString("sub_title"));
                                    }
                                    JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                                    /*productModels_Desserts = new ArrayList<>();*/
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
                                        idLOFSection025Model.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, dTextViewSection020.getText().toString(),24));
                                    }
                                    ProductAdapter section025_adapter = new ProductAdapter(getContext(), idLOFSection025Model,idLOFSection025_Recycler);
                                    idLOFSection025_Recycler.setAdapter(section025_adapter);
                                    snapHelper24.attachToRecyclerView(idLOFSection025_Recycler);
                                    Log.d(dTextViewSection025.getText().toString(), jsonArray1.toString());
                                }
                            }
                            //new 9 to 25
                            }
                        }
                        else {
                            idProgressBarRelative.setVisibility(View.GONE);
//                            progressDialog.dismiss();
//                            idProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "System Is Busy Try Again!!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        idProgressBarRelative.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        idProgressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(VolleyError volleyError) {
                    idProgressBarRelative.setVisibility(View.GONE);
//                    progressDialog.dismiss();
//                    idProgressBar.setVisibility(View.GONE);
                    Log.wtf("Error -> ",volleyError.getMessage());
//                    Toast.makeText(getContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "System Is Busy Try Again!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //Get Data From Api
    private void getBanners(){
        bannerModelList=new ArrayList<>();
        //put Parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_key", Common.app_key);
        networkManager.JsonObjectRequest(params, Common.allData_url, Request.Method.POST, new NetworkCallback() {
            @Override
            public void onSuccess(String result) {
                Log.wtf("My Settings -> getMySettings ->  onSuccess -> ", result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject infoObj = obj.getJSONObject("data");
                    if (obj.getInt("status_code") == 200 && obj.getString("status").equals("success")) {
//                        JSONObject jsonObject=infoObj.getJSONObject("banners");
//                        JSONArray jsonArraySettings = infoObj.getJSONArray("sections");
                        JSONArray jsonArraySettings = infoObj.getJSONArray("banners");
                        for (int i=0;i<jsonArraySettings.length();i++){
                            JSONObject jsonArraySettingsJSONObject = jsonArraySettings.getJSONObject(i);
                            String banner_image=jsonArraySettingsJSONObject.getString("banner_image");
                            bannerModelList.add(new BannerModel(banner_image));
//                            Toast.makeText(getContext(), "Banner Image:"+banner_image, Toast.LENGTH_SHORT).show();
                        }
                        //Image Slider
                        final SliderAdapterStartUp adapter = new SliderAdapterStartUp(getActivity(),bannerModelList);
                        adapter.setCount(bannerModelList.size());
                        sliderView.setSliderAdapter(adapter);
                        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
                        /* sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);*/
                        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                        sliderView.setIndicatorSelectedColor(Color.WHITE);
                        sliderView.setIndicatorUnselectedColor(Color.GRAY);
                        sliderView.startAutoCycle();
                        sliderView.setScrollTimeInSec(4);
                        //Image Slider
                    }
                    else {
                        Toast.makeText(getContext(), "System Is Busy Try Again!!!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "System Is Busy Try Again!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void autoScrollAnother() {
        scrollCount = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                idProduct_Grid_SpecialHandi.smoothScrollToPosition((scrollCount++));
                if (scrollCount == productAdapter.getItemCount() - 2) {
                    productModels.addAll(productModels);
                    productAdapter.notifyDataSetChanged();
                }
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }
}