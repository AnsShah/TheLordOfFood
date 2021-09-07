package com.applicationdevelopers.lordoffood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MainActivity extends AppCompatActivity {

    ProgressBar simpleProgressBar;
    CircularImageView profile_image;
    RelativeLayout idProgressBarRelative;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleProgressBar=(ProgressBar) findViewById(R.id.simpleProgressBar);
        idProgressBarRelative=(RelativeLayout) findViewById(R.id.idProgressBarRelative);
//        profile_image=(CircularImageView) findViewById(R.id.profile_image);
        simpleProgressBar.setVisibility(View.VISIBLE);
//        KProgressHUD.create(MainActivity.this)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Please wait")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .show();
//        KProgressHUD hud = KProgressHUD.create(MainActivity.this)
//                .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
//                .setLabel("Please wait")
//                .setMaxProgress(100)
//                .show();
//        hud.setProgress(90);
//        ImageView imageView = new ImageView(this);
//        imageView.setImageResource(R.mipmap.ic_launcher);
//        KProgressHUD.create(MainActivity.this)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Please wait")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .show();
    }
}