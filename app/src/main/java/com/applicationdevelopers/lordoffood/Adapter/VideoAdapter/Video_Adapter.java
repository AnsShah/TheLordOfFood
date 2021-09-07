package com.applicationdevelopers.lordoffood.Adapter.VideoAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applicationdevelopers.lordoffood.Model.Youtube.YoutubeVideoModel;
import com.applicationdevelopers.lordoffood.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.ViewHolder> {
    private Context context;
    private List<YoutubeVideoModel> uploads_videos;
    String api_key="AIzaSyDQqVEEIntQwXhNZZn0ZnK63h0PVI8NB6Y";
    private static final int RECOVERY_REQUEST = 1;

    public Video_Adapter(Context context, List<YoutubeVideoModel> uploads_videos) {
        this.context = context;
        this.uploads_videos = uploads_videos;
    }

    @NonNull
    @Override
    public Video_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_video_layout,parent,false);
        // productModel=new ProductModel();
        return new Video_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Video_Adapter.ViewHolder holder, final int position) {

//        holder.youtubeVideoPlayer.loadData(uploads_videos.get(position).getVideo_url(),"text/html","utf-8");
       final List<String> videoList=new ArrayList<>();
       videoList.add(uploads_videos.get(position).getVideo_url());
        holder.youtubeVideoPlayer.initialize(api_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                List<String> list=new ArrayList<>();
                list.add("8_bOFNrgvWo");
                list.add("VHERpiDiHxc");
                list.add("RmfdhUWrK14");
                list.add("YqLNCNFtyHc");

                if (!b) {
                    youTubePlayer.loadVideos(list); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return uploads_videos.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

       YouTubePlayerView youtubeVideoPlayer;
       YouTubePlayer.OnInitializedListener onInitializedListener;

        public ViewHolder(View itemView) {
            super(itemView);
            youtubeVideoPlayer=(YouTubePlayerView) itemView.findViewById(R.id.youtubeVideoPlayer);

        }
    }
}

