package com.applicationdevelopers.lordoffood.View.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.applicationdevelopers.lordoffood.R;

public class SettingsActivity extends AppCompatActivity {

    ImageView idOrderHeaderBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        idOrderHeaderBackBtn=(ImageView) findViewById(R.id.idOrderHeaderBackBtn);

        //back
        idOrderHeaderBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.finish();
            }
        });
        //back


        //Theme
        int theme=getSharedPreferences("theme",MODE_PRIVATE).getInt("mode",2);
        switch (theme){
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
//Theme
        RadioGroup radioGroup=findViewById(R.id.radiogroup);
        //Theme
        switch (theme){
            case 0:
                radioGroup.check(R.id.light);
                break;
            case 1:
                radioGroup.check(R.id.dark);
                break;
            case 2:
                radioGroup.check(R.id.auto);

        }
        //Theme
        //Radio Group
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.light:
                        SettingsActivity.this.getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("mode", 0).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case R.id.dark:
                        SettingsActivity.this.getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("mode", 1).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case R.id.auto:
                        SettingsActivity.this.getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("mode", 2).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

                }
            }
        });
        //Radio Group
    }
}