package com.applicationdevelopers.lordoffood.View.Share;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applicationdevelopers.lordoffood.R;
import com.google.zxing.WriterException;

public class ShareActivity extends AppCompatActivity {

    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private ImageView qrCodeIV,idOrderHeaderBackBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String link="https://play.google.com/store/apps/details?id=com.applicationdevelopers.lordoffood";
    TextView idShareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        initLayout();

    }

    private void initLayout(){
        // initializing all variables.
        qrCodeIV = findViewById(R.id.idIVQrcode);
        idOrderHeaderBackBtn=(ImageView) findViewById(R.id.idOrderHeaderBackBtn);
        idShareBtn=(TextView) findViewById(R.id.idShareBtn);

        //Back
        idOrderHeaderBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareActivity.this.finish();
            }
        });
        //Back
        //Share
        idShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                //  String shareBody = "Here is the share content body";
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.applicationdevelopers.lordoffood");
                /*Fire!*/
                try {
                    startActivity(Intent.createChooser(intent,"Share Using"));
                }
                catch (android.content.ActivityNotFoundException ex) {

                    Toast.makeText(getApplicationContext(),"Please Install Sharing Application", Toast.LENGTH_LONG).show();
                }

            }
        });
        //Share
        generateQrCode();

    }
    private void generateQrCode(){
        //code gen
        // below line is for getting
        // the windowmanager service.
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(link, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString());
        }
        //code gen
    }
}
