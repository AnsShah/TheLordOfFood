package com.applicationdevelopers.lordoffood.View.Invoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.applicationdevelopers.lordoffood.Adapter.Invoice.InvoiceAdapter;
import com.applicationdevelopers.lordoffood.LocalDatabase.PersonDBHelper;
import com.applicationdevelopers.lordoffood.Model.Person.Person;
import com.applicationdevelopers.lordoffood.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvoiceActivity extends AppCompatActivity {
    ImageView idInvoiceStoreLogo,idInvoiceProductImage;
    TextView idTextShopName,idTextOrderNumber,idTextOrderDate,
            idTextPaymentMethod,idTextAddress,idTextUserName,
            idTextUserEmail,idTextUserPhone,idInvoiceProductName,
            idInvoiceProductQuantity,idInvoiceProductUnitPrice,
            idInvoiceProductPrice,idInvoiceProductsTotalPrice,idInvoiceStoreOrderDesc,idInvoiceProductsTotalPrice1,idInvoiceProductsTotalPriceGst;
    RecyclerView idInvoiceRecyclerview;
    private PersonDBHelper dbHelper;
    private InvoiceAdapter invoiceAdapter;
    private String filter = "";
    private RecyclerView.LayoutManager mLayoutManager;
    double price1,quantity1;
    ImageView idOrderHeaderBackBtn;
    int gst=0;
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME = "PrefsFile";
    String gstString="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        //Initialization
        initLayout();
        //Initialization
    }
    @Override
    protected void onResume() {
        super.onResume();
        initLayout();
    }
    private void initLayout(){

        //        get pref data
        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        getPreferencesData();
//        get pref data

        idInvoiceStoreLogo=(ImageView) findViewById(R.id.idInvoiceStoreLogo);
        idInvoiceProductImage=(ImageView) findViewById(R.id.idInvoiceProductImage);
        idTextShopName=(TextView) findViewById(R.id.idTextShopName);
        idTextOrderNumber=(TextView) findViewById(R.id.idTextOrderNumber);
        idTextOrderDate=(TextView) findViewById(R.id.idTextOrderDate);
        idTextPaymentMethod=(TextView) findViewById(R.id.idTextPaymentMethod);
        idTextAddress=(TextView) findViewById(R.id.idTextAddress);
        idTextUserName=(TextView) findViewById(R.id.idTextUserName);
        idTextUserEmail=(TextView) findViewById(R.id.idTextUserEmail);
        idTextUserPhone=(TextView) findViewById(R.id.idTextUserPhone);
        idInvoiceProductName=(TextView) findViewById(R.id.idInvoiceProductName);
        idInvoiceProductQuantity=(TextView) findViewById(R.id.idInvoiceProductQuantity);
        idInvoiceProductUnitPrice=(TextView) findViewById(R.id.idInvoiceProductUnitPrice);
        idInvoiceProductPrice=(TextView) findViewById(R.id.idInvoiceProductPrice);
        idInvoiceProductsTotalPrice=(TextView) findViewById(R.id.idInvoiceProductsTotalPrice);
        idInvoiceStoreOrderDesc=(TextView) findViewById(R.id.idInvoiceStoreOrderDesc);
        idInvoiceProductsTotalPrice1=(TextView) findViewById(R.id.idInvoiceProductsTotalPrice1);
        idInvoiceProductsTotalPriceGst=(TextView) findViewById(R.id.idInvoiceProductsTotalPriceGst);
        idInvoiceRecyclerview=(RecyclerView) findViewById(R.id.idInvoiceRecyclerview);
        idOrderHeaderBackBtn=(ImageView) findViewById(R.id.idOrderHeaderBackBtn);

        double totalprice=0;
        dbHelper = new PersonDBHelper(this);
        List<Person> f=dbHelper.productListinCart( "");
        for(int i=0;i<f.size();i++){
            String quantity=f.get(i).getQty();
            String price=f.get(i).getPrice();
            price1 = Double.parseDouble(price);
            quantity1 = Double.parseDouble(quantity);
            totalprice=(quantity1*price1)+totalprice;}
        int totalInt= (int) totalprice;
        if (gstString.equals("") && gstString.equals("0")){
//                Toast.makeText(OrderActivity.this, "0", Toast.LENGTH_SHORT).show();
            idInvoiceProductsTotalPrice.setText("Rs."+totalInt);
            idInvoiceProductsTotalPriceGst.setText("Rs.0");
            idInvoiceProductsTotalPrice1.setText("Rs."+totalInt);
        }
        else {
//                Toast.makeText(OrderActivity.this, "gst:"+gstString, Toast.LENGTH_SHORT).show();
            gst=Integer.parseInt(gstString);
            double res = (totalInt / 100.0f) * gst;
            int totalPrice1=(int) res+totalInt;
            Log.wtf("Total Price -> ",""+totalPrice1+" ,GST -> "+res+" ,Price without gst ->"+totalInt);
            idInvoiceProductsTotalPrice.setText("Rs."+totalInt);
            idInvoiceProductsTotalPriceGst.setText("Rs."+(int)res);
            idInvoiceProductsTotalPrice1.setText("Rs."+totalPrice1);
        }
        //RecyclerView
        idInvoiceRecyclerview.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        idInvoiceRecyclerview.setLayoutManager(mLayoutManager);
        populaterecyclerView(filter);

        //get current Date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        idTextOrderDate.setText(Html.fromHtml("<b>" +" Order Date:" + "</b>"+formattedDate));
        //get current Date
        //Back Button
        idOrderHeaderBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvoiceActivity.this.finish();
            }
        });
        //Back Button
    }
    private void populaterecyclerView(String filter) {
        dbHelper = new PersonDBHelper(this);
        invoiceAdapter = new InvoiceAdapter(dbHelper.productListinCart(filter), this, idInvoiceRecyclerview);
        idInvoiceRecyclerview.setAdapter(invoiceAdapter);
    }
    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("order_gst_percent")) {
            gstString = sp.getString("order_gst_percent", "");
        }
    }
}