package com.applicationdevelopers.lordoffood.View.ContactUs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.applicationdevelopers.lordoffood.Interfaces.NetworkCallback;
import com.applicationdevelopers.lordoffood.Internet.AppStatus;
import com.applicationdevelopers.lordoffood.Model.Menu.MenuModel;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactUsActivity extends AppCompatActivity {
    ImageView idOrderHeaderBackBtn;
    private String TAG="ContactUsActivity";
    LinearLayout idContactUsLinearLayout,idEmailLinearLayout;
    TextView idTextContactLinearLayout1,idTextEmailTextView,idTextPhoneEmailMessage;
    RelativeLayout rel_web1,idProgressBarRelative,contactDetailsRelLayout;
    Button idSendEmail;
    EditText idTextNameEmailMessage,idTextMessageEmail;
    RelativeLayout rel_send;
    Spinner services_spinner;
    private List<MenuModel> menuModels;
    ArrayAdapter<String> arrayAdapterMenu;

    private Context context=this;
    private NetworkManager networkManager;
    String url1 = "https://www.taster.pk/api/sync/sections";
    String app_key="d72fbbccd9fe64c3a14f85d225a046f4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        initLayout();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void initLayout() {
        idOrderHeaderBackBtn=(ImageView) findViewById(R.id.idOrderHeaderBackBtn);
        idContactUsLinearLayout=(LinearLayout) findViewById(R.id.idContactUsLinearLayout);
        idTextContactLinearLayout1=(TextView) findViewById(R.id.idTextContactLinearLayout1);
        //rel_web1=(RelativeLayout) findViewById(R.id.rel_web1);
        idEmailLinearLayout=(LinearLayout) findViewById(R.id.idEmailLinearLayout);
        idTextEmailTextView=(TextView) findViewById(R.id.idTextEmailTextView);
        idTextPhoneEmailMessage=(TextView) findViewById(R.id.idTextPhoneEmailMessage);
        /*login=(Button) findViewById(R.id.login);*/
        idTextNameEmailMessage=(EditText) findViewById(R.id.idTextNameEmailMessage);
        idTextMessageEmail=(EditText) findViewById(R.id.idTextMessageEmail);
        idSendEmail=(Button) findViewById(R.id.idSendEmail);
        rel_send=(RelativeLayout) findViewById(R.id.rel_send);
        idProgressBarRelative=(RelativeLayout) findViewById(R.id.idProgressBarRelative);
        contactDetailsRelLayout=(RelativeLayout) findViewById(R.id.contactDetailsRelLayout);
        services_spinner=(Spinner) findViewById(R.id.services_spinner);
        idOrderHeaderBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { ContactUsActivity.this.finish();
            }
        });
        idContactUsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall();
            }
        });
        //send Email
        idSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(view);
            }
        });
        //send Email
        //get Services
        //get Services
        getMenus(app_key);
    }
    //make call
    private void makeCall(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ContactUsActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure to want to call?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                //if there is no previous page, close app
                String number=null;
                number=idTextContactLinearLayout1.getText().toString();
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
    //send Email
    private void sendEmail(View view){
        final String customerEmail="mail@lordoffood.com";
        if (!AppStatus.getInstance(getApplicationContext()).isOnline()) {
            Toast.makeText(getApplicationContext(), "Internet Not Available", Toast.LENGTH_SHORT).show();
        } else {
            if (idTextNameEmailMessage.getText().toString().isEmpty()) {
                Snackbar.make(view.getRootView(), "Enter Your Name", Snackbar.LENGTH_SHORT).show();
            } else if (idTextPhoneEmailMessage.getText().toString().isEmpty()) {
                Snackbar.make(view.getRootView(), "Please Enter Phone", Snackbar.LENGTH_SHORT).show();
            } else if (idTextMessageEmail.getText().toString().isEmpty()) {
                Snackbar.make(view.getRootView(), "Please Enter Message", Snackbar.LENGTH_SHORT).show();
            } else if (services_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Service")) {
                Snackbar.make(view.getRootView(), "Please Select Service First", Snackbar.LENGTH_SHORT).show();
            } else {
                //new
                //new
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"mail@lordoffood.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "FeedBack");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Name:" + idTextNameEmailMessage.getText().toString() + ",\n" +
                        "Phone#" + idTextPhoneEmailMessage.getText().toString() + ",\n" +
                        "Product Name:" + services_spinner.getSelectedItem().toString() + ",\n" +
                        "Description:" + idTextMessageEmail.getText().toString());
                emailIntent.setType("message/rfc822");
                try {
                    startActivity(Intent.createChooser(emailIntent,
                            "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ContactUsActivity.this,
                            "No email clients installed.",
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(ContactUsActivity.this, "Send Email", Toast.LENGTH_SHORT).show();
            }
        }
        idSendEmail.setOnTouchListener(new View.OnTouchListener() {
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
    //send Email
    private void getMenus(String app_key){
        if (!AppStatus.getInstance(context).isOnline()&& !AppStatus.getInstance(context).isNetworkOnline2()) {
            Toast.makeText(context, "No Internet Access", Toast.LENGTH_SHORT).show();
        }
        else {
            idProgressBarRelative.setVisibility(View.VISIBLE);
//            final ProgressDialog progressDialog = ProgressDialog.show(ContactUsActivity.this, "Please wait", "Loading......", false, false);
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
                                    menuModels.add(new MenuModel(name,Price,img_Url));
                                }
                            }
                            final String[] cateName=new String[menuModels.size()];
                            for (int i = 0; i < cateName.length; i++)
                            {
                                cateName[i]=menuModels.get(i).getMenuName();
                                arrayAdapterMenu = new ArrayAdapter<String>(ContactUsActivity.this, android.R.layout.simple_spinner_item, cateName);
                                arrayAdapterMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                services_spinner.setAdapter(arrayAdapterMenu);
                            }
                            idProgressBarRelative.setVisibility(View.GONE);
                            contactDetailsRelLayout.setVisibility(View.VISIBLE);
//                            progressDialog.dismiss();
                        }
                        else {
                            idProgressBarRelative.setVisibility(View.GONE);
//                            progressDialog.dismiss();
                            Log.wtf("Error found -> Menu ->","");
                            Toast.makeText(ContactUsActivity.this, "System Busy Try Again Later!!!", Toast.LENGTH_SHORT).show();

                        }
                    }
                    catch (JSONException e) {
                        idProgressBarRelative.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError volleyError) {
                    idProgressBarRelative.setVisibility(View.GONE);
//                    progressDialog.dismiss();
                    Toast.makeText(ContactUsActivity.this, "System Busy Try Again Later!!!", Toast.LENGTH_SHORT).show();
                    Log.wtf("Error found -> Menu ->",volleyError.toString());
                }
            });
        }
    }
}