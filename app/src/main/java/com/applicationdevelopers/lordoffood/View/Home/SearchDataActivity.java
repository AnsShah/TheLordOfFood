package com.applicationdevelopers.lordoffood.View.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.applicationdevelopers.lordoffood.Adapter.ProductAdapter.ProductAdapter;
import com.applicationdevelopers.lordoffood.Adapter.ProductAdapter.SearchProductAdapter;
import com.applicationdevelopers.lordoffood.Controllers.Common;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.applicationdevelopers.lordoffood.Model.Menu.MenuModel;
import com.applicationdevelopers.lordoffood.Model.ProductModel.ProductModel;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchDataActivity extends AppCompatActivity {

    ImageView idSearchImageView;
    EditText search_product_name;
    RecyclerView idRecyclerFilter;
    private NetworkManager networkManager;
    private List<ProductModel> productModelsList;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context=this;
    SearchProductAdapter productAdapter;
    LinearLayout idLinearLayoutSearch;
    RelativeLayout idProgressBarRelative;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_data);

        initLayout();

    }
    private void initLayout(){
        idSearchImageView=(ImageView) findViewById(R.id.idSearchImageView);
        search_product_name=(EditText) findViewById(R.id.search_product_name);
        idRecyclerFilter=(RecyclerView) findViewById(R.id.idSearchData);
        idLinearLayoutSearch=(LinearLayout) findViewById(R.id.idLinearLayoutSearch);
        idProgressBarRelative=(RelativeLayout) findViewById(R.id.idProgressBarRelative);
        idRecyclerFilter.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        idRecyclerFilter.setLayoutManager(mLayoutManager);
//        ViewCompat.setNestedScrollingEnabled(idRecyclerFilter, false);
//        get Products
           getProducts();
//        get Products
        search_product_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                return false;
            }
        });
        //Filter
        search_product_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*categoryAdapter.filter1(editable.toString());*/
                filter(editable.toString());
            }
        });
        //Filter
    }
    private void filter(String text) {
        ArrayList<ProductModel> filteredList = new ArrayList<>();
        for (ProductModel item : productModelsList) {
            if (item.getProduct_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        productAdapter.filterList(filteredList);
    }
    private void getProducts(){
        idProgressBarRelative.setVisibility(View.VISIBLE);
        networkManager = new NetworkManager(context);
        productModelsList=new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        networkManager.jsonObjectRequest2(context, Common.app_key, jsonObject, Common.sections_url, Request.Method.POST, new NetworkCallback() {
            @Override
            public void onSuccess(String result) {
                idProgressBarRelative.setVisibility(View.GONE);
                idLinearLayoutSearch.setVisibility(View.VISIBLE);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject infoObj = obj.getJSONObject("data");
                    if (obj.getInt("status_code") == 200 && obj.getString("status").equals("success")) {
                        JSONArray jsonArraySections = infoObj.getJSONArray("sections");
                        for (int i=0;i<jsonArraySections.length();i++){
                            JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(i);
                            JSONArray jsonArray1 = (JSONArray) jsonObjectGetData.get("products");
                            String menuName=jsonObjectGetData.getString("name");
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
                                    productModelsList.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, cate));
                                }
                            }
                        }
                        productAdapter = new SearchProductAdapter(context, productModelsList,idRecyclerFilter);
                        idRecyclerFilter.setAdapter(productAdapter);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "System Is Busy!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(VolleyError volleyError) {
                idProgressBarRelative.setVisibility(View.GONE);
//                idLinearLayoutSearch.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "System Is Busy!Try Again Later", Toast.LENGTH_SHORT).show();
                Log.wtf("Error ->",volleyError.getMessage());
            }
        });
    }
}