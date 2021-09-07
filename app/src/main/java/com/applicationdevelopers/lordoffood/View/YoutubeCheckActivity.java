package com.applicationdevelopers.lordoffood.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.applicationdevelopers.lordoffood.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class YoutubeCheckActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView,youTubeView1,youTubeView2;
    String api_key="AIzaSyDQqVEEIntQwXhNZZn0ZnK63h0PVI8NB6Y";
    String[] VideoID = {"8_bOFNrgvWo", "VHERpiDiHxc", "RmfdhUWrK14","YqLNCNFtyHc","giwfN540OSk","eKlBOqdjG4g","aXAZniHo8oI","J2NjhBAzl7Y","4rvTBl_7HgU","KpY_q95AmNA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_check);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(api_key, this);
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        List<String> list=new ArrayList<>();
        list.add("8_bOFNrgvWo");
        list.add("VHERpiDiHxc");
        list.add("RmfdhUWrK14");
        list.add("YqLNCNFtyHc");

        if (!wasRestored) {
            player.loadVideos(list); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(api_key, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}