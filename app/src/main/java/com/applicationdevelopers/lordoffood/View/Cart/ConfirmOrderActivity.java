package com.applicationdevelopers.lordoffood.View.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.applicationdevelopers.lordoffood.Controllers.Common;
import com.applicationdevelopers.lordoffood.Controllers.Location;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.applicationdevelopers.lordoffood.Interfaces.locationListener;
import com.applicationdevelopers.lordoffood.Internet.AppStatus;
import com.applicationdevelopers.lordoffood.LocalDatabase.PersonDBHelper;
import com.applicationdevelopers.lordoffood.Model.Common.AreaModel;
import com.applicationdevelopers.lordoffood.Model.Messages.Errors;
import com.applicationdevelopers.lordoffood.Model.Messages.Message;
import com.applicationdevelopers.lordoffood.Model.Person.Person;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;
import com.applicationdevelopers.lordoffood.View.Home.HomeActivity;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConfirmOrderActivity extends AppCompatActivity implements OnMapReadyCallback {
    ImageView idOrderHeaderBackBtn;
    Button idCancelOrderButton, idConfirmOrderButton;
    EditText idConfirmOrderName, idConfirmOrderEmail, idConfirmOrderPhone,
            idConfirmOrderAddress, idConfirmOrderComment;
    RelativeLayout idRelativeLayoutAreas;
    Spinner idSpinnerAreas;
    private NetworkManager networkManager;
    String url = "https://www.taster.pk/api/sync/order";
    String order_app_key = "d72fbbccd9fe64c3a14f85d225a046f4";
    PersonDBHelper dbHelper;
    private Context context = this;
    Dialog loading;
    ProgressBar progressBar;
    SharedPreferences.Editor editor;
    //shared preference
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME = "PrefsFile";
    //    Location
    private GoogleMap mMap;
    private Location location;
    private Double currentLat, currentLng;
    private Marker riderMarket;
    private boolean pickupPlacesSelected = false;
    private String mPlaceLocation;
    private SupportMapFragment mapFragment;
    ImageView idchecklocation;
    int i = 0;
//    Location

//    New
    String pro_name=null;
    String Image_URL=null;
    double price1,quantity1,totalQuantity=0;
    String product_qty,product_price;
    ArrayAdapter<String> arrayAdapter;
    private List<AreaModel> areaModels;
//    Notification
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    final String TAG = "NOTIFICATION TAG";
    String title,message,toreceicver="maillordoffood";
//    Notification
//    New
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        initLayout();
    }

    private void initLayout() {

        networkManager=new NetworkManager(context);
//        Location
        location = new Location(this, new locationListener() {
            //Used for receiving notifications from the LocationManager when the location has changed.
            // These methods are called if the LocationListener has been registered with the location manager service using the
            @Override
            public void locationResponse(LocationResult response) {
                // Add a icon_marker in Sydney and move the camera
                currentLat = response.getLastLocation().getLatitude();
                currentLng = response.getLastLocation().getLongitude();
                Common.currenLocation = new LatLng(response.getLastLocation().getLatitude(), response.getLastLocation().getLongitude());
                displayLocation();
                if (mPlaceLocation == null) {
                    loadAllAvailableDriver(new LatLng(currentLat, currentLng));
                    getCompleteAddressString(currentLat, currentLng);
                    mPlaceLocation=getCompleteAddressString(currentLat,currentLng);
                }
                else {
                    location.stopUpdateLocation();
                }

            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        Location
        idOrderHeaderBackBtn = (ImageView) findViewById(R.id.idOrderHeaderBackBtn);
        idCancelOrderButton = (Button) findViewById(R.id.idCancelOrderButton);
        idConfirmOrderButton = (Button) findViewById(R.id.idConfirmOrderButton);
        idConfirmOrderName = (EditText) findViewById(R.id.idConfirmOrderName);
        idConfirmOrderEmail = (EditText) findViewById(R.id.idConfirmOrderEmail);
        idConfirmOrderPhone = (EditText) findViewById(R.id.idConfirmOrderPhone);
        idConfirmOrderAddress = (EditText) findViewById(R.id.idConfirmOrderAddress);
        idConfirmOrderComment = (EditText) findViewById(R.id.idConfirmOrderComment);
        idchecklocation = (ImageView) findViewById(R.id.idchecklocation);
        idRelativeLayoutAreas=(RelativeLayout) findViewById(R.id.idRelativeLayoutAreas);
        idSpinnerAreas=(Spinner) findViewById(R.id.idSpinnerAreas);

        loading = new Dialog(context);
        loading.setContentView(R.layout.loading);
        progressBar = (ProgressBar) loading.findViewById(R.id.spn_1);

        idCancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmOrderActivity.this.finish();
            }
        });
        idOrderHeaderBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmOrderActivity.this.finish();
            }
        });
        idConfirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmOrder(idConfirmOrderName.getText().toString(), idConfirmOrderEmail.getText().toString(), idConfirmOrderPhone.getText().toString(),
                        idConfirmOrderAddress.getText().toString(), idConfirmOrderComment.getText().toString());
            }
        });
        idConfirmOrderButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    view.setBackgroundColor(Color.parseColor("#D50000"));
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }


                return false;
            }
        });
        idCancelOrderButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    view.setBackgroundColor(Color.parseColor("#D50000"));
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                return false;
            }
        });
        //Shared Pref
        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        getPreferencesData();
        //Shared Pref
//        Check GPS STATUS
        statusCheck();
        getAreas();
//        Check GPS STATUS
    }
    private void confirmOrder(String Name, String Email, String Phone, String Address, String Comments) {
        if (!AppStatus.getInstance(getApplicationContext()).isOnline() && !AppStatus.getInstance(context).isNetworkOnline2()) {
            Toast.makeText(getApplicationContext(), "Internet Not Available", Toast.LENGTH_SHORT).show();
        }
        else {
            if (Name.isEmpty() || Name.equals("")) {
                idConfirmOrderName.setError("Enter Your Name");
                idConfirmOrderName.requestFocus();
            } else if (Email.isEmpty() || Email.equals("")) {
                idConfirmOrderEmail.setError("Enter Your Email");
                idConfirmOrderEmail.requestFocus();
            } else if (Phone.isEmpty() || Phone.equals("")) {
                idConfirmOrderPhone.setError("Enter Your Phone Number");
                idConfirmOrderPhone.requestFocus();
            } else if (Address.isEmpty() || Address.equals("")) {
                idConfirmOrderAddress.setError("Enter Your Address");
                idConfirmOrderAddress.requestFocus();
            }
            else {
                 if (idSpinnerAreas.getVisibility()==View.VISIBLE){
                    if (idSpinnerAreas.getSelectedItem().equals("Select")){
                        ((TextView)idSpinnerAreas.getSelectedView()).setError("Please Select Area");
                        ((TextView)idSpinnerAreas.getSelectedView()).requestFocus();
                        Toast.makeText(context, "Please Select Delivery Area", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        loading.show();
                        loading.setCancelable(false);
                        Circle circle = new Circle();
                        progressBar.setIndeterminateDrawable(circle);
                        int id = 0;
                        String name = "";
                        String price;
                        String quantity;
                        String imgUrl;
                        String cate="";
                        int arrId[];
                        dbHelper = new PersonDBHelper(this);
                        List<Person> productModels = dbHelper.productListinCart("");
                        String username = idConfirmOrderName.getText().toString();
                        String userEmail = idConfirmOrderEmail.getText().toString();
                        String userPhone = idConfirmOrderPhone.getText().toString();
                        String userAddress = idConfirmOrderAddress.getText().toString();
                        String comment = idConfirmOrderComment.getText().toString();
                        String area=idSpinnerAreas.getSelectedItem().toString();
                        networkManager = new NetworkManager(getApplicationContext());
                        int arrayId[] = new int[productModels.size()];
                        String arrayName[] = new String[productModels.size()];
                        String arrayPrice[] = new String[productModels.size()];
                        String arrayQuantity[] = new String[productModels.size()];
                        String arrayImg[] = new String[productModels.size()];
                        String arrayImage[] = new String[productModels.size()];
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("order_app_key", order_app_key);
                        params.put("order_name", username);
                        params.put("order_email", userEmail);
                        params.put("order_phone", userPhone);
                        params.put("order_address", userAddress);
                        for (int i = 0; i < productModels.size(); i++) {
                            id = (int) productModels.get(i).getId();
                            name = productModels.get(i).getPname();
                            price = productModels.get(i).getPrice();
                            quantity = productModels.get(i).getQty();
                            imgUrl = productModels.get(i).getImageUrl();
                            cate=productModels.get(i).getCategory();
                            String arrayTest[] = new String[productModels.size()];
                            arrayId[i] = id;
                            arrayName[i] = name;
                            arrayPrice[i] = price;
                            arrayQuantity[i] = quantity;
                            arrayImg[i] = imgUrl;
                            arrayImage[i] = imgUrl;
                            pro_name="";
                            quantity1=0;
                            Image_URL="";
                            product_qty="";
                            product_price="";
                        }
                /*for(int i=0;i<productModels.size();i++){
                    params.put("order_cart","["+"{"+"\"id\":" + arrayId[i] + "," + "\"ad_name\":" + "\"" + arrayName[i] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[i] + "\"," + "\"price\":" + arrayPrice[i] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[i] + "\"," + "\"qty\":" + arrayQuantity[i]+""+"}"+"]");
                }*/
                        if (productModels.size() == 1) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        } else if (productModels.size() == 2) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        } else if (productModels.size() == 3) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        } else if (productModels.size() == 4) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        } else if (productModels.size() == 5) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        } else if (productModels.size() == 6) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        } else if (productModels.size() == 7) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        } else if (productModels.size() == 8) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        } else if (productModels.size() == 9) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        }
                        else if (productModels.size() == 10) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        }
                        else if (productModels.size() == 11) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[10] + "," + "\"ad_name\":" + "\"" + arrayName[10] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[10] + "\"," + "\"price\":" + arrayPrice[10] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[10] + "\"," + "\"qty\":" + arrayQuantity[10] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        }
                        else if (productModels.size() == 12) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[10] + "," + "\"ad_name\":" + "\"" + arrayName[10] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[10] + "\"," + "\"price\":" + arrayPrice[10] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[10] + "\"," + "\"qty\":" + arrayQuantity[10] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[11] + "," + "\"ad_name\":" + "\"" + arrayName[11] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[11] + "\"," + "\"price\":" + arrayPrice[11] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[11] + "\"," + "\"qty\":" + arrayQuantity[11] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        }
                        else if (productModels.size() == 13) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[10] + "," + "\"ad_name\":" + "\"" + arrayName[10] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[10] + "\"," + "\"price\":" + arrayPrice[10] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[10] + "\"," + "\"qty\":" + arrayQuantity[10] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[11] + "," + "\"ad_name\":" + "\"" + arrayName[11] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[11] + "\"," + "\"price\":" + arrayPrice[11] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[11] + "\"," + "\"qty\":" + arrayQuantity[11] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[12] + "," + "\"ad_name\":" + "\"" + arrayName[12] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[12] + "\"," + "\"price\":" + arrayPrice[12] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[12] + "\"," + "\"qty\":" + arrayQuantity[12] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        }
                        else if (productModels.size() == 14) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[10] + "," + "\"ad_name\":" + "\"" + arrayName[10] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[10] + "\"," + "\"price\":" + arrayPrice[10] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[10] + "\"," + "\"qty\":" + arrayQuantity[10] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[11] + "," + "\"ad_name\":" + "\"" + arrayName[11] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[11] + "\"," + "\"price\":" + arrayPrice[11] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[11] + "\"," + "\"qty\":" + arrayQuantity[11] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[12] + "," + "\"ad_name\":" + "\"" + arrayName[12] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[12] + "\"," + "\"price\":" + arrayPrice[12] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[12] + "\"," + "\"qty\":" + arrayQuantity[12] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[13] + "," + "\"ad_name\":" + "\"" + arrayName[13] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[13] + "\"," + "\"price\":" + arrayPrice[13] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[13] + "\"," + "\"qty\":" + arrayQuantity[13] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        }
                        else if (productModels.size() == 15) {
                            params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[10] + "," + "\"ad_name\":" + "\"" + arrayName[10] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[10] + "\"," + "\"price\":" + arrayPrice[10] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[10] + "\"," + "\"qty\":" + arrayQuantity[10] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[11] + "," + "\"ad_name\":" + "\"" + arrayName[11] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[11] + "\"," + "\"price\":" + arrayPrice[11] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[11] + "\"," + "\"qty\":" + arrayQuantity[11] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[12] + "," + "\"ad_name\":" + "\"" + arrayName[12] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[12] + "\"," + "\"price\":" + arrayPrice[12] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[12] + "\"," + "\"qty\":" + arrayQuantity[12] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[13] + "," + "\"ad_name\":" + "\"" + arrayName[13] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[13] + "\"," + "\"price\":" + arrayPrice[13] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[13] + "\"," + "\"qty\":" + arrayQuantity[12] + "" + "}" + ","
                                    + "{" + "\"id\":" + arrayId[14] + "," + "\"ad_name\":" + "\"" + arrayName[14] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[14] + "\"," + "\"price\":" + arrayPrice[14] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[14] + "\"," + "\"qty\":" + arrayQuantity[13] + "" + "}" + "]");
                            Log.d("Parameter:", params.toString());
                        }
                        params.put("order_comments", comment);
                        params.put("order_app_currency", "Rs");
                        params.put("order_area",area);
                        networkManager.JsonObjectOrderRequest(params, url, Request.Method.POST, new NetworkCallback() {
                            @Override
                            public void onSuccess(String result) {
                                dbHelper = new PersonDBHelper(context);
                                dbHelper.deletetableRecord(context);
                                editor = mPrefs.edit();
                                editor.putString("Pref_Name", idConfirmOrderName.getText().toString());
                                editor.putString("Pref_Email", idConfirmOrderEmail.getText().toString());
                                editor.putString("Pref_Phone", idConfirmOrderPhone.getText().toString());
                                editor.putString("Pref_Address", idConfirmOrderAddress.getText().toString());
                                editor.putString("Pref_Comment", idConfirmOrderComment.getText().toString());
                                editor.apply();
                                sendNotification(context);
                                Toast.makeText(ConfirmOrderActivity.this, "Order Successful", Toast.LENGTH_SHORT).show();
                                orderSuccessfulPopup(context);
                                Log.wtf("Sent Order Successfully", result);
                                Log.d("My Msg", "jsonObjectRequest response: " + result.toString());
                                loading.dismiss();
                            }
                            @Override
                            public void onError(VolleyError volleyError) {
                                orderUnSuccessfulPopup(context);
//                        Toast.makeText(ConfirmOrderActivity.this, "Error" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.wtf("Upload Order Error", "error");
                                loading.dismiss();
                            }
                        });
                    }
                }
                 else {
                     loading.show();
                     loading.setCancelable(false);
                     Circle circle = new Circle();
                     progressBar.setIndeterminateDrawable(circle);
                     int id = 0;
                     String name = "";
                     String price;
                     String quantity;
                     String imgUrl;
                     String cate="";
                     int arrId[];
                     dbHelper = new PersonDBHelper(this);
                     List<Person> productModels = dbHelper.productListinCart("");
                     String username = idConfirmOrderName.getText().toString();
                     String userEmail = idConfirmOrderEmail.getText().toString();
                     String userPhone = idConfirmOrderPhone.getText().toString();
                     String userAddress = idConfirmOrderAddress.getText().toString();
                     String comment = idConfirmOrderComment.getText().toString();
//                String area=idSpinnerAreas.getSelectedItem().toString();
                     networkManager = new NetworkManager(getApplicationContext());
                     int arrayId[] = new int[productModels.size()];
                     String arrayName[] = new String[productModels.size()];
                     String arrayPrice[] = new String[productModels.size()];
                     String arrayQuantity[] = new String[productModels.size()];
                     String arrayImg[] = new String[productModels.size()];
                     String arrayImage[] = new String[productModels.size()];
                     Map<String, String> params = new HashMap<String, String>();
                     params.put("order_app_key", order_app_key);
                     params.put("order_name", username);
                     params.put("order_email", userEmail);
                     params.put("order_phone", userPhone);
                     params.put("order_address", userAddress);
                     for (int i = 0; i < productModels.size(); i++) {
                         id = (int) productModels.get(i).getId();
                         name = productModels.get(i).getPname();
                         price = productModels.get(i).getPrice();
                         quantity = productModels.get(i).getQty();
                         imgUrl = productModels.get(i).getImageUrl();
                         cate=productModels.get(i).getCategory();
                         String arrayTest[] = new String[productModels.size()];
                         arrayId[i] = id;
                         arrayName[i] = name;
                         arrayPrice[i] = price;
                         arrayQuantity[i] = quantity;
                         arrayImg[i] = imgUrl;
                         arrayImage[i] = imgUrl;
                         pro_name="";
                         quantity1=0;
                         Image_URL="";
                         product_qty="";
                         product_price="";
                     }
                /*for(int i=0;i<productModels.size();i++){
                    params.put("order_cart","["+"{"+"\"id\":" + arrayId[i] + "," + "\"ad_name\":" + "\"" + arrayName[i] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[i] + "\"," + "\"price\":" + arrayPrice[i] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[i] + "\"," + "\"qty\":" + arrayQuantity[i]+""+"}"+"]");
                }*/
                     if (productModels.size() == 1) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     } else if (productModels.size() == 2) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     } else if (productModels.size() == 3) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     } else if (productModels.size() == 4) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     } else if (productModels.size() == 5) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     } else if (productModels.size() == 6) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     } else if (productModels.size() == 7) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     } else if (productModels.size() == 8) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     } else if (productModels.size() == 9) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     }
                     else if (productModels.size() == 10) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     }
                     else if (productModels.size() == 11) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[10] + "," + "\"ad_name\":" + "\"" + arrayName[10] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[10] + "\"," + "\"price\":" + arrayPrice[10] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[10] + "\"," + "\"qty\":" + arrayQuantity[10] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     }
                     else if (productModels.size() == 12) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[10] + "," + "\"ad_name\":" + "\"" + arrayName[10] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[10] + "\"," + "\"price\":" + arrayPrice[10] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[10] + "\"," + "\"qty\":" + arrayQuantity[10] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[11] + "," + "\"ad_name\":" + "\"" + arrayName[11] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[11] + "\"," + "\"price\":" + arrayPrice[11] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[11] + "\"," + "\"qty\":" + arrayQuantity[11] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     }
                     else if (productModels.size() == 13) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[10] + "," + "\"ad_name\":" + "\"" + arrayName[10] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[10] + "\"," + "\"price\":" + arrayPrice[10] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[10] + "\"," + "\"qty\":" + arrayQuantity[10] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[11] + "," + "\"ad_name\":" + "\"" + arrayName[11] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[11] + "\"," + "\"price\":" + arrayPrice[11] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[11] + "\"," + "\"qty\":" + arrayQuantity[11] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[12] + "," + "\"ad_name\":" + "\"" + arrayName[12] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[12] + "\"," + "\"price\":" + arrayPrice[12] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[12] + "\"," + "\"qty\":" + arrayQuantity[12] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     }
                     else if (productModels.size() == 14) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[10] + "," + "\"ad_name\":" + "\"" + arrayName[10] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[10] + "\"," + "\"price\":" + arrayPrice[10] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[10] + "\"," + "\"qty\":" + arrayQuantity[10] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[11] + "," + "\"ad_name\":" + "\"" + arrayName[11] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[11] + "\"," + "\"price\":" + arrayPrice[11] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[11] + "\"," + "\"qty\":" + arrayQuantity[11] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[12] + "," + "\"ad_name\":" + "\"" + arrayName[12] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[12] + "\"," + "\"price\":" + arrayPrice[12] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[12] + "\"," + "\"qty\":" + arrayQuantity[12] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[13] + "," + "\"ad_name\":" + "\"" + arrayName[13] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[13] + "\"," + "\"price\":" + arrayPrice[13] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[13] + "\"," + "\"qty\":" + arrayQuantity[13] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     }
                     else if (productModels.size() == 15) {
                         params.put("order_cart", "[" + "{" + "\"id\":" + arrayId[0] + "," + "\"ad_name\":" + "\"" + arrayName[0] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[0] + "\"," + "\"price\":" + arrayPrice[0] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[0] + "\"," + "\"qty\":" + arrayQuantity[0] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[1] + "," + "\"ad_name\":" + "\"" + arrayName[1] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[1] + "\"," + "\"price\":" + arrayPrice[1] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[1] + "\"," + "\"qty\":" + arrayQuantity[1] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[2] + "," + "\"ad_name\":" + "\"" + arrayName[2] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[2] + "\"," + "\"price\":" + arrayPrice[2] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[2] + "\"," + "\"qty\":" + arrayQuantity[2] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[3] + "," + "\"ad_name\":" + "\"" + arrayName[3] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[3] + "\"," + "\"price\":" + arrayPrice[3] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[3] + "\"," + "\"qty\":" + arrayQuantity[3] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[4] + "," + "\"ad_name\":" + "\"" + arrayName[4] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[4] + "\"," + "\"price\":" + arrayPrice[4] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[4] + "\"," + "\"qty\":" + arrayQuantity[4] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[5] + "," + "\"ad_name\":" + "\"" + arrayName[5] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[5] + "\"," + "\"price\":" + arrayPrice[5] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[5] + "\"," + "\"qty\":" + arrayQuantity[5] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[6] + "," + "\"ad_name\":" + "\"" + arrayName[6] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[6] + "\"," + "\"price\":" + arrayPrice[6] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[6] + "\"," + "\"qty\":" + arrayQuantity[6] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[7] + "," + "\"ad_name\":" + "\"" + arrayName[7] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[7] + "\"," + "\"price\":" + arrayPrice[7] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[7] + "\"," + "\"qty\":" + arrayQuantity[7] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[8] + "," + "\"ad_name\":" + "\"" + arrayName[8] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[8] + "\"," + "\"price\":" + arrayPrice[8] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[8] + "\"," + "\"qty\":" + arrayQuantity[8] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[9] + "," + "\"ad_name\":" + "\"" + arrayName[9] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[9] + "\"," + "\"price\":" + arrayPrice[9] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[9] + "\"," + "\"qty\":" + arrayQuantity[9] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[10] + "," + "\"ad_name\":" + "\"" + arrayName[10] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[10] + "\"," + "\"price\":" + arrayPrice[10] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[10] + "\"," + "\"qty\":" + arrayQuantity[10] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[11] + "," + "\"ad_name\":" + "\"" + arrayName[11] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[11] + "\"," + "\"price\":" + arrayPrice[11] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[11] + "\"," + "\"qty\":" + arrayQuantity[11] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[12] + "," + "\"ad_name\":" + "\"" + arrayName[12] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[12] + "\"," + "\"price\":" + arrayPrice[12] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[12] + "\"," + "\"qty\":" + arrayQuantity[12] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[13] + "," + "\"ad_name\":" + "\"" + arrayName[13] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[13] + "\"," + "\"price\":" + arrayPrice[13] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[13] + "\"," + "\"qty\":" + arrayQuantity[12] + "" + "}" + ","
                                 + "{" + "\"id\":" + arrayId[14] + "," + "\"ad_name\":" + "\"" + arrayName[14] + "\"" + "," + "\"ad_link\":" + "\"" + arrayImage[14] + "\"," + "\"price\":" + arrayPrice[14] + "," + "\"ad_image_thumbs\":" + "\"" + arrayImg[14] + "\"," + "\"qty\":" + arrayQuantity[13] + "" + "}" + "]");
                         Log.d("Parameter:", params.toString());
                     }
                     params.put("order_comments", comment);
                     params.put("order_app_currency", "Rs");
                     params.put("order_area","");
                     networkManager.JsonObjectOrderRequest(params, url, Request.Method.POST, new NetworkCallback() {
                         @Override
                         public void onSuccess(String result) {
                             dbHelper = new PersonDBHelper(context);
                             dbHelper.deletetableRecord(context);
                             editor = mPrefs.edit();
                             editor.putString("Pref_Name", idConfirmOrderName.getText().toString());
                             editor.putString("Pref_Email", idConfirmOrderEmail.getText().toString());
                             editor.putString("Pref_Phone", idConfirmOrderPhone.getText().toString());
                             editor.putString("Pref_Address", idConfirmOrderAddress.getText().toString());
                             editor.putString("Pref_Comment", idConfirmOrderComment.getText().toString());
                             editor.apply();
                             sendNotification(context);
                             Toast.makeText(ConfirmOrderActivity.this, "Order Successful", Toast.LENGTH_SHORT).show();
                             orderSuccessfulPopup(context);
                             Log.wtf("Sent Order Successfully", result);
                             Log.d("My Msg", "jsonObjectRequest response: " + result.toString());
                             loading.dismiss();
                         }
                         @Override
                         public void onError(VolleyError volleyError) {
                             orderUnSuccessfulPopup(context);
//                        Toast.makeText(ConfirmOrderActivity.this, "Error" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                             Log.wtf("Upload Order Error", "error");
                             loading.dismiss();
                         }
                     });
                 }
            }
        }

    }
    private void orderSuccessfulPopup(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.order_successfull_popup, null, false);
        Button btn_BackToHome=(Button) inflatedView.findViewById(R.id.btn_BackToHome);
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
        btn_BackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmOrderActivity.this,HomeActivity.class));
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
    private void orderUnSuccessfulPopup(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.order_unsuccessful_popup, null, false);
        Button btn_BackToCart=(Button) inflatedView.findViewById(R.id.btn_BackToCart);
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
        btn_BackToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmOrderActivity.this,OrderActivity.class));
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
    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("Pref_Name")) {
            String name = sp.getString("Pref_Name", "");
            if (name.isEmpty()) {
                idConfirmOrderName.setText("");
            } else {
                idConfirmOrderName.setText(name);
            }
        }
        if (sp.contains("Pref_Email")) {
            String Email = sp.getString("Pref_Email", "");
            if (Email.isEmpty()) {
                idConfirmOrderEmail.setText("");
            } else {
                idConfirmOrderEmail.setText(Email);
            }
        }
        if (sp.contains("Pref_Phone")) {
            String phone = sp.getString("Pref_Phone", "");
            if (phone.isEmpty()) {
                idConfirmOrderPhone.setText("");
            } else {
                idConfirmOrderPhone.setText(phone);
            }
        }
        if (sp.contains("Pref_Address")) {
            String address = sp.getString("Pref_Address", "");
            if (address.isEmpty()) {
                idConfirmOrderAddress.setText("");
            } else {
                idConfirmOrderAddress.setText(address);
            }

        }
        if (sp.contains("Pref_Comment")) {
            String comment = sp.getString("Pref_Comment", "");
            if (comment.isEmpty()) {
                idConfirmOrderComment.setText("");
            } else {
                idConfirmOrderComment.setText(comment);
            }
        }
    }
    //    Location
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.uber_style_map));
//        new
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (riderMarket != null)
                    riderMarket.remove();
                riderMarket = mMap.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
                        .title("Me"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                getCompleteAddressString(latLng.latitude, latLng.longitude);
            }
        });
//        new
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        location.onRequestPermissionResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onStart() {
        super.onStart();
        displayLocation();
        location.inicializeLocation();
    }
    @Override
    protected void onStop() {
        super.onStop();
        location.stopUpdateLocation();
    }
    //Display Current
    private void displayLocation() {
        if (currentLat != null && currentLng != null) {
            loadAllAvailableDriver(new LatLng(currentLat, currentLng));
        } else {
            Message.messageError(context, Errors.WITHOUT_LOCATION);
        }
    }
    //Display Current
    private void loadAllAvailableDriver(final LatLng location) {
        if (!pickupPlacesSelected) {
            if (riderMarket != null)
                riderMarket.remove();
            riderMarket = mMap.addMarker(new MarkerOptions().position(location)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
//            .title(getResources().getString(R.string.you))
        }
    }
    @SuppressLint("LongLogTag")
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                idConfirmOrderAddress.setText(strAdd);
//                E/My Current loction address
                Log.wtf("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.wtf("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.wtf("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }
    //check gps status
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.gps_status_layout, null);
        builder.setView(view);
        builder.setCancelable(false);
        TextView txt_nope = view.findViewById(R.id.txt_nope);
        TextView txt_yup = view.findViewById(R.id.txt_yup);
        txt_nope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                builder.dismiss();
            }
        });
        txt_yup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                builder.dismiss();
            }
        });
        builder.show();
    }
    //check gps status
//    Get Areas
    private void getAreas(){
        areaModels = new ArrayList<>();
        networkManager=new NetworkManager(context);
//put Parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_key", Common.Taster_app_key);
        params.put("user_id",Common.user_Id);
        networkManager.JsonObjectRequest(params, Common.areas_url, Request.Method.POST, new NetworkCallback() {
            @Override
            public void onSuccess(String result) {
                Log.wtf("My Areas -> getMyAreas ->  onSuccess -> ", result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject infoObj = obj.getJSONObject("data");
                    if (obj.getInt("status_code") == 200 && obj.getString("status").equals("success")) {
                        if (infoObj.length() == 0 || infoObj.toString().equals("[]")){
                            Log.wtf("Area ->","No Area Set By Admin");
                        }
                        else {
                            JSONArray jsonArrayArea = infoObj.getJSONArray("areas");
                            for (int i = 0; i < jsonArrayArea.length(); i++) {
                                JSONObject jsonArrayCityJSONObject = jsonArrayArea.getJSONObject(i);
                                String id = jsonArrayCityJSONObject.getString("id");
                                String name = jsonArrayCityJSONObject.getString("name");
                                areaModels.add(new AreaModel(id, name));
                            }
                            final String[] areaName = new String[areaModels.size()];
                            for (int i = 0; i < areaName.length; i++) {
                                areaName[i] = areaModels.get(i).getName();
                                arrayAdapter = new ArrayAdapter<String>(ConfirmOrderActivity.this, android.R.layout.simple_spinner_item, areaName);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                idSpinnerAreas.setAdapter(arrayAdapter);
                            }
                            idRelativeLayoutAreas.setVisibility(View.VISIBLE);
                        }
                        }
                    else {
//                        Toast.makeText(SplashActivity.this, "System Busy Try Again Later!!!", Toast.LENGTH_SHORT).show();
                        Log.wtf("My Areas -> getMyAreass ->  onError -> ","Try Again");
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.wtf("My Areas -> getMyAreas ->  onException -> ", e);
                }
            }
            @Override
            public void onError(VolleyError volleyError) {
                Log.wtf("My Areas -> getMyAreas->  onError -> ",""+volleyError.getMessage());
            }
        });
    }
//    Get Areas
private void sendNotification(Context context){
    TOPIC = "/topics/"+toreceicver; //topic has to match what the receiver subscribed to
    NOTIFICATION_TITLE =Common.NOTIFICATION_TITLE;
    NOTIFICATION_MESSAGE =Common.NOTIFICATION_MESSAGE;

    JSONObject notification = new JSONObject();
    JSONObject notifcationBody = new JSONObject();
    try {
        notifcationBody.put("title", NOTIFICATION_TITLE);
        notifcationBody.put("message", NOTIFICATION_MESSAGE);

        notification.put("to", TOPIC);
        notification.put("data", notifcationBody);
    } catch (JSONException e) {
        Log.e(TAG, "onCreate: " + e.getMessage() );
    }
    networkManager.sendNotification(notification,context);
}
}