package com.applicationdevelopers.lordoffood.View.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applicationdevelopers.lordoffood.Adapter.Cart.OrderAdapter;
import com.applicationdevelopers.lordoffood.LocalDatabase.PersonDBHelper;
import com.applicationdevelopers.lordoffood.Model.Person.Person;
import com.applicationdevelopers.lordoffood.R;
import com.applicationdevelopers.lordoffood.View.Invoice.InvoiceActivity;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    ImageView idOrderHeaderBackBtn;
    RecyclerView idOrderRecyclerview;
    LinearLayout idNoOfOrders,idGstLinearLayout;
    private Handler handler = new Handler();
    private RecyclerView.LayoutManager mLayoutManager;
    private PersonDBHelper dbHelper;
    double price1,quantity1;
    TextView idTotalOrderAmount,idGstTotalOrderAmount;
    private String filter = "";
    private OrderAdapter orderAdapter;
    private long orderCount;
    private String TAG="OrderActivity";
    TextView idOrderContinueBtn;
    Button idButtonPreview;
    LinearLayout idOrderPlaceFooter;
    int gst=0;
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME = "PrefsFile";
    String gstString="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Initialization
        initLayout();
        //Initialization

    }
    @Override
    protected void onResume() {
        super.onResume();
        initLayout();
    }
    private void initLayout() {

//        get pref data
        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        getPreferencesData();
//        get pref data
        dbHelper=new PersonDBHelper(this);
        idOrderHeaderBackBtn=(ImageView) findViewById(R.id.idOrderHeaderBackBtn);
        idOrderRecyclerview=(RecyclerView) findViewById(R.id.idOrderRecyclerview);
        idNoOfOrders=(LinearLayout) findViewById(R.id.idNoOfOrders);
        idTotalOrderAmount=(TextView) findViewById(R.id.idTotalOrderAmount);
        idOrderContinueBtn=(TextView) findViewById(R.id.idOrderContinueBtn);
        idGstTotalOrderAmount=(TextView) findViewById(R.id.idGstTotalOrderAmount);
        idButtonPreview=(Button) findViewById(R.id.idButtonPreview);
        idOrderPlaceFooter=(LinearLayout) findViewById(R.id.idOrderPlaceFooter);
        idGstLinearLayout=(LinearLayout) findViewById(R.id.idGstLinearLayout);

        //RecyclerView
        idOrderRecyclerview.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        idOrderRecyclerview.setLayoutManager(mLayoutManager);
        //RecyclerView
        idOrderHeaderBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderActivity.this.finish();
            }
        });
        idButtonPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderActivity.this, InvoiceActivity.class));
            }
        });
        idOrderContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderCount=dbHelper.getOrderCount();
                if (orderCount == 0)
                {
                    Toast.makeText(OrderActivity.this, "Cart Is Empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(OrderActivity.this, ConfirmOrderActivity.class));
                }
            }
        });
        handler.post(runnable);
        //populate recyclerview
        populaterecyclerView(filter);
        freeMemory();
    }
    private void checkOrder(){
        dbHelper=new PersonDBHelper(this);
        orderCount=dbHelper.getOrderCount();
        if (orderCount == 0)
        {
            //idMarketPlaceFooter.setVisibility(View.GONE);
            idNoOfOrders.setVisibility(View.VISIBLE);
        }
        else {
            idNoOfOrders.setVisibility(View.GONE);
        }
        if (orderCount == 0)
        {
            //idMarketPlaceFooter.setVisibility(View.GONE);
            idButtonPreview.setVisibility(View.GONE);
            idOrderPlaceFooter.setVisibility(View.GONE);
        }
        else {
            idButtonPreview.setVisibility(View.VISIBLE);
            idOrderPlaceFooter.setVisibility(View.VISIBLE);
        }
    }
    private void populaterecyclerView(String filter) {
        dbHelper = new PersonDBHelper(this);
        orderAdapter = new OrderAdapter(dbHelper.productListinCart(filter), this, idOrderRecyclerview);
        idOrderRecyclerview.setAdapter(orderAdapter);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here
            double totalprice=0;
            List<Person> f=dbHelper.productListinCart( "");
            for(int i=0;i<f.size();i++){
                String quantity=f.get(i).getQty();
                String price=f.get(i).getPrice();
                price1 = Double.parseDouble(price);
                //Toast.makeText(getApplicationContext(),"price:"+price,Toast.LENGTH_SHORT).show();price1 = Double.parseDouble(price);
                quantity1 = Double.parseDouble(quantity);
                totalprice=(quantity1*price1)+totalprice;}
            int totalPrice= (int) totalprice;
            if (gstString.equals("") && gstString.equals("0")){
//                Toast.makeText(OrderActivity.this, "0", Toast.LENGTH_SHORT).show();
                idTotalOrderAmount.setText("Rs. "+String.valueOf(totalPrice));
                idGstLinearLayout.setVisibility(View.GONE);
            }
            else {
                idGstLinearLayout.setVisibility(View.VISIBLE);
//                Toast.makeText(OrderActivity.this, "gst:"+gstString, Toast.LENGTH_SHORT).show();
                gst=Integer.parseInt(gstString);
                double res = (totalPrice / 100.0f) * gst;
                int totalPrice1=(int) res+totalPrice;
                Log.wtf("Total Price -> ",""+totalPrice1+" ,GST -> "+res+" ,Price without gst ->"+totalPrice);
                idTotalOrderAmount.setText("Rs. "+String.valueOf(totalPrice1));
                idGstTotalOrderAmount.setText("Rs. "+(int) res);
            }
            //check order
            checkOrder();
            //check order
            // Repeat every 2 seconds
            handler.postDelayed(runnable, 2000);
        }
    };
    public void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("order_gst_percent")) {
            gstString = sp.getString("order_gst_percent", "");
        }
    }
}