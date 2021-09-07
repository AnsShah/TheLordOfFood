package com.applicationdevelopers.lordoffood.View.AboutUs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.applicationdevelopers.lordoffood.Internet.AppStatus;
import com.applicationdevelopers.lordoffood.R;

public class AboutUsActivity extends AppCompatActivity {
    ImageView idOrderHeaderBackBtn;
    private String TAG="AboutUsActivity";
    RelativeLayout rel_web;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initLayout();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void initLayout() {
        idOrderHeaderBackBtn=(ImageView) findViewById(R.id.idOrderHeaderBackBtn);
        rel_web=(RelativeLayout) findViewById(R.id.rel_web);
        login=(Button) findViewById(R.id.login);
        idOrderHeaderBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutUsActivity.this.finish();
            }
        });

       /*rel_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutUsActivity.this, "okay", Toast.LENGTH_SHORT).show();
                openWebPage("https://www.demodubai.com/");
            }
        });*/
        login.setOnTouchListener(new View.OnTouchListener() {
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppStatus.getInstance(getApplicationContext()).isOnline() && !AppStatus.getInstance(getApplicationContext()).isNetworkOnline2()) {
                    Toast.makeText(getApplicationContext(), "Internet Not Available", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(AboutUsActivity.this, "oak", Toast.LENGTH_SHORT).show();
                    openWebPage("https://www.hubsol.net/lordfood_new/");
                }
                //Toast.makeText(ContactUsActivity.this, "oak", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //open web
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                startActivity(intent);
            }
            catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(AboutUsActivity.this,"Please Install Browser", Toast.LENGTH_LONG).show();
            }
        }
    }
    //open web
}