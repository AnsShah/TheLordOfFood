package com.applicationdevelopers.lordoffood.View.Cart.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.applicationdevelopers.lordoffood.Adapter.ImageSlider.SliderAdapter01;
import com.applicationdevelopers.lordoffood.Adapter.ProductAdapter.ProductAdapter;
import com.applicationdevelopers.lordoffood.Adapter.RelatedProductAdapter.RelatedProductAdapter;
import com.applicationdevelopers.lordoffood.Controllers.Common;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.applicationdevelopers.lordoffood.Internet.AppStatus;
import com.applicationdevelopers.lordoffood.LocalDatabase.PersonDBHelper;
import com.applicationdevelopers.lordoffood.Model.ProductModel.ProductImagesModel;
import com.applicationdevelopers.lordoffood.Model.ProductModel.ProductModel;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;
import com.applicationdevelopers.lordoffood.View.Gallery.GalleryActivity;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailsFragment extends Fragment {

    TextView idTextProductTitle,idProductPrice,idTextContactLinearLayout,idTextTimingLinearLayout,idTextPhone,idProductDescText,idRecipeCategoryRowPrice;
    LinearLayout idAddToCartLayout;
    RecyclerView idRecyclerViewRelatedProducts;
    ImageView idProductImage,idImageViewFbLordOfFood;
    LinearLayout idContactLinearLayout,idTimingLinearLayout,idContactInfoLinearLayout
            ,idTimingTableLinearLayout,idPhoneLinearLayout,idProductAddToCartLayout,idRelatedProductLinearLayout;
    ImageView idAddToFavouriteProductDetail;
    String qty="1";
    int sectionId=0;
    String category="";
    private String TAG="ProductDetailsFragment";
    RecyclerView product_Grid01;
    SliderView imageSlider01;
    //private ProductImagesModel productImagesModel;
    private List<ProductImagesModel> productImagesModels;
    String imageurl="";
    private List<ProductModel> productModelsTotal;
    private NetworkManager networkManager;
    String app_key="d72fbbccd9fe64c3a14f85d225a046f4";
    RelativeLayout priceTag_arrow;
     Context context;
     PersonDBHelper personDBHelper;
    public static String FACEBOOK_URL = "https://www.facebook.com/lordoffood/";
    public static String FACEBOOK_PAGE_ID = "lordoffood";
//public static String FACEBOOK_URL = "http://www.facebook.com/constructionmart.2020";
//    public static String FACEBOOK_PAGE_ID = "constructionmart.2020";
private List<ProductModel> productModels;
Snackbar snackbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_details, container, false);
        initLayout(view);
        return view;
    }
    private void initLayout(View view){
        context=getContext();
        personDBHelper=new PersonDBHelper(context);
        idTextProductTitle=(TextView) view.findViewById(R.id.idTextProductTitle);
        idProductPrice=(TextView) view.findViewById(R.id.idProductPrice);
        idTextContactLinearLayout=(TextView) view.findViewById(R.id.idTextContactLinearLayout);
        idTextTimingLinearLayout=(TextView) view.findViewById(R.id.idTextTimingLinearLayout);
        idTextPhone=(TextView) view.findViewById(R.id.idTextPhone);
        idAddToCartLayout=(LinearLayout) view.findViewById(R.id.idAddToCartLayout);
        idProductImage=(ImageView) view.findViewById(R.id.idProductImage);
        idContactLinearLayout=(LinearLayout) view.findViewById(R.id.idContactLinearLayout);
        idTimingLinearLayout=(LinearLayout) view.findViewById(R.id.idTimingLinearLayout);
        idContactInfoLinearLayout=(LinearLayout) view.findViewById(R.id.idContactInfoLinearLayout);
        idTimingTableLinearLayout=(LinearLayout) view.findViewById(R.id.idTimingTableLinearLayout);
        idPhoneLinearLayout=(LinearLayout) view.findViewById(R.id.idPhoneLinearLayout);
        idProductAddToCartLayout=(LinearLayout) view.findViewById(R.id.idProductAddToCartLayout);
        idRelatedProductLinearLayout=(LinearLayout) view.findViewById(R.id.idRelatedProductLinearLayout);
        imageSlider01=(SliderView) view.findViewById(R.id.imageSlider01);
        idProductDescText=(TextView) view.findViewById(R.id.idProductDescText);
        idRecipeCategoryRowPrice=(TextView) view.findViewById(R.id.idRecipeCategoryRowPrice);
        priceTag_arrow=(RelativeLayout) view.findViewById(R.id.priceTag_arrow);
        idImageViewFbLordOfFood=(ImageView) view.findViewById(R.id.idImageViewFbLordOfFood);
        idAddToFavouriteProductDetail=(ImageView) view.findViewById(R.id.idAddToFavouriteProductDetail);
        idRecyclerViewRelatedProducts=(RecyclerView) view.findViewById(R.id.idRecyclerViewRelatedProducts);
        LinearLayoutManager managers1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        idRecyclerViewRelatedProducts.setLayoutManager(managers1);

        //product_Grid01=(RecyclerView) view.findViewById(R.id.product_Grid01);
        //Clicks
//        new
        idAddToFavouriteProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavourite(v);
            }
        });
//        new
        idContactLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContactInfo();
            }
        });
        idTimingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimingInfo();
            }
        });
        idPhoneLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall();
            }
        });
        idProductAddToCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
        idImageViewFbLordOfFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Facebook", Toast.LENGTH_SHORT).show();
//                final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Please wait", "Loading......", false, false);
//                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
//                String facebookUrl = getFacebookPageURL(context,progressDialog);
//                facebookIntent.setData(Uri.parse(facebookUrl));
//                if(facebookIntent.resolveActivity(context.getPackageManager()) != null){
//                    try {
//                        progressDialog.dismiss();
//                        startActivity(facebookIntent);
//                    }
//                    catch (android.content.ActivityNotFoundException ex) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getContext(),"Please Install Facebook", Toast.LENGTH_LONG).show();
//                    }
//                }
////                newFacebookIntent(context.getPackageManager(),FACEBOOK_URL);
//                progressDialog.dismiss();
                startActivity(getOpenFacebookIntent());
            }
        });
        //Clicks
        binddata();
        this.clickEffect(idContactLinearLayout);
        this.clickEffect(idTimingLinearLayout);

        //new Slider
        productImagesModels=new ArrayList<>();
        productImagesModels.add(new ProductImagesModel(imageurl));
        productImagesModels.add(new ProductImagesModel(imageurl));

        //Image Slider
        final SliderAdapter01 adapter = new SliderAdapter01(getActivity(),productImagesModels);
        adapter.setCount(2);
        imageSlider01.setSliderAdapter(adapter);
        imageSlider01.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider01.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        /*sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);*/
        imageSlider01.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        imageSlider01.setIndicatorSelectedColor(Color.WHITE);
        imageSlider01.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider01.startAutoCycle();
        imageSlider01.setScrollTimeInSec(4);
        //Image Slider
        //new Slider
//        getRelated Product
          getRelatedProducts(sectionId);
//        getRelated Product
    }
    //get data FROM Productpage
    private void binddata() {
        Bundle bundle = getArguments();
        category= bundle.getString("product_category");
        final String namePro =  bundle.getString("product_name");
        String price = bundle.getString("price");
        String desc=bundle.getString("product_desc");
        String status=bundle.getString("status");
        imageurl = bundle.getString("product_image");
        sectionId = bundle.getInt("sectionId");
        idProductDescText.setText(desc);
        idTextProductTitle.setText(namePro);
        idProductPrice.setText("Rs. "+price);
        if (status.equalsIgnoreCase("Close Now")){
            idRecipeCategoryRowPrice.setText(status);
            idRecipeCategoryRowPrice.setBackgroundResource(R.drawable.price_arrow_layout_red);
            priceTag_arrow.setBackgroundResource(R.drawable.rectangle_arrow_layout_red);
        }
        else {
            idRecipeCategoryRowPrice.setText(status);
            idRecipeCategoryRowPrice.setBackgroundResource(R.drawable.price_arrow_layout_green);
            priceTag_arrow.setBackgroundResource(R.drawable.rectangle_arrow_layout_green);
        }
        Cursor res=personDBHelper.getSpecificDataFavourite(namePro);
        if(!(res.getCount()==0)){
            //idAddToFavouriteProductDetail.setImageResource(R.drawable.fav1);
            idAddToFavouriteProductDetail.setColorFilter(getResources().getColor(R.color.lord_of_food_color));
        }
        //Glide.with(this).load(imageurl).into(idProductImage);
    }
    // show contact info
    private void showContactInfo(){
        idContactLinearLayout.setBackgroundResource(R.color.grey);
        idTimingLinearLayout.setBackgroundResource(R.drawable.product_border_shape_card);
        idContactInfoLinearLayout.setVisibility(View.VISIBLE);
        idTimingTableLinearLayout.setVisibility(View.GONE);
    }
    // show contact info
    //show Timing
    private void showTimingInfo(){
        idTimingLinearLayout.setBackgroundResource(R.color.grey);
        idContactLinearLayout.setBackgroundResource(R.drawable.product_border_shape_card);
        idContactInfoLinearLayout.setVisibility(View.GONE);
        idTimingTableLinearLayout.setVisibility(View.VISIBLE);
    }
    //show Timing
    //make call
    private void makeCall(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setMessage("Are you sure to want to call?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                //if there is no previous page, close app
                String number=null;
                number=idTextPhone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
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
    //make call
    //Add to cart
    private void addToCart(){
        Bundle bundle = getArguments();
        String category= bundle.getString("product_category");
        String name =  bundle.getString("product_name");
        String price = bundle.getString("price");
        String imageurl = bundle.getString("product_image");
        String status=bundle.getString("status");
        //Toast.makeText(getContext(), "url;"+imageurl, Toast.LENGTH_SHORT).show();
        if (status.equalsIgnoreCase("Open Now")){
            PersonDBHelper dbHandler = new PersonDBHelper(getContext());
            Cursor res=dbHandler.getSpecificData(idTextProductTitle.getText().toString());
            if(res.getCount()==0){
                dbHandler.insertProductOrder(name,price
                        ,qty,category,imageurl);
                Snackbar.make(getView(),"Added to cart",Snackbar.LENGTH_SHORT).show();
                //Toast.makeText(getContext(),"Added to cart", Toast.LENGTH_LONG).show();
                return;
            }else {
                int qty1=0;
                while (res.moveToNext()){
                    qty1=(res.getInt(3));
                }
                qty=String.valueOf(Integer.parseInt(qty)+qty1);
                boolean isUpdate =dbHandler.updateData(name,price,qty
                        ,category);
                if(isUpdate==true){
                    Snackbar.make(getView(),"Added to cart",Snackbar.LENGTH_SHORT).show();
                    // Toast.makeText(getContext(),"Added to cart", Toast.LENGTH_LONG).show();
                }
            }
        }else {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
//            Snackbar.make(getView(),"Sorry! Closed Now", Snackbar.LENGTH_SHORT).show();
            snackbar = Snackbar.make(getView(), "Sorry!!! Closed Now", Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(context.getResources().getColor(R.color.redColor));
            snackbar.show();
//            Toast.makeText(context, "Sorry! Closed Now", Toast.LENGTH_SHORT).show();
        }
    }
    //Add to cart
    //Button Click Effect
    public void clickEffect(LinearLayout linearLayout) {
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE)
                {
                    view.setBackgroundColor(Color.parseColor("#D50000"));
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                return false;
            }
        });
    }
    //Button Click Effect
    public String getFacebookPageURL(Context context,ProgressDialog progressDialog) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            progressDialog.dismiss();
            return FACEBOOK_URL; //normal web url
        }
    }
    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }
    public Intent getOpenFacebookIntent() {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/lordoffood"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
        }
    }
    private void addToFavourite(View view) {
        Bundle bundle = getArguments();
        String productName = bundle.getString("product_name");
        String productPrice = bundle.getString("price");
        String productImage = bundle.getString("product_image");
        String productCategory=bundle.getString("product_category");
        personDBHelper = new PersonDBHelper(context);
        Cursor res = personDBHelper.getSpecificDataFavourite(productName);
        if (res.getCount() == 0) {
            personDBHelper.insertProductFavourite(productName, productPrice
                    , productImage,productCategory);
            Snackbar.make(view, "Added to Favourite", Snackbar.LENGTH_SHORT).show();
            //idAddToFavouriteProductDetail.setImageResource(R.drawable.fav1);
            idAddToFavouriteProductDetail.setColorFilter(getResources().getColor(R.color.lord_of_food_color));
        } else {
            Snackbar.make(view, "Already In Favourite", Snackbar.LENGTH_SHORT).show();
        }
    }
    private void getRelatedProducts(final int sectionId){
        if (!AppStatus.getInstance(getContext()).isOnline() && !AppStatus.getInstance(getContext()).isNetworkOnline2()) {
            Toast.makeText(getContext(), "No Internet Access", Toast.LENGTH_SHORT).show();
        }
        else {
            productModels = new ArrayList<>();
            networkManager = new NetworkManager(getContext());
            JSONObject jsonObject = new JSONObject();
            networkManager.jsonObjectRequest2(getContext(), Common.app_key, jsonObject, Common.sections_url, Request.Method.POST, new NetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        JSONObject infoObj = obj.getJSONObject("data");
                        if (obj.getInt("status_code") == 200 && obj.getString("status").equals("success")) {
                            JSONArray jsonArraySections = infoObj.getJSONArray("sections");
                            {
                                JSONObject jsonObjectGetData = jsonArraySections.getJSONObject(sectionId);
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
                                    productModels.add(new ProductModel(name, Price, img_Url, location, open_status, views, "Pakistan", description_post, category));
                                }
                                RelatedProductAdapter relatedProductAdapter = new RelatedProductAdapter(getContext(), productModels,idRecyclerViewRelatedProducts);
                                idRecyclerViewRelatedProducts.setAdapter(relatedProductAdapter);
                            }
                            idRelatedProductLinearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError volleyError) {
                    Log.wtf("Error -> ",volleyError.getMessage());
                }
            });
        }
    }
}