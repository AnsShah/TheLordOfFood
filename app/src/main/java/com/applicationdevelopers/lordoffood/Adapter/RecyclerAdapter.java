package com.applicationdevelopers.lordoffood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.applicationdevelopers.lordoffood.Model.Person.Person;
import com.applicationdevelopers.lordoffood.Model.Youtube.YoutubeVideoModel;
import com.applicationdevelopers.lordoffood.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VideoInfoHolder> {

    //these ids are the unique id for each video
    String[] VideoID = {"8_bOFNrgvWo", "VHERpiDiHxc", "RmfdhUWrK14","YqLNCNFtyHc","giwfN540OSk","eKlBOqdjG4g","aXAZniHo8oI","J2NjhBAzl7Y","4rvTBl_7HgU","KpY_q95AmNA"};
    Context ctx;
    String api_key="AIzaSyDQqVEEIntQwXhNZZn0ZnK63h0PVI8NB6Y";
    private List<YoutubeVideoModel> youtubeVideoModelList;
    private String className="";
    public RecyclerAdapter(Context context, List<YoutubeVideoModel> youtubeVideoModelList,String className) {
        this.ctx = context;
        this.youtubeVideoModelList=youtubeVideoModelList;
        this.className=className;
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_video_check_layout, parent, false);
        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
        if (className.equalsIgnoreCase("FounderActivity")){
            if (!youtubeVideoModelList.get(position).getIs_featured().equalsIgnoreCase("No")){
                final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                    }
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailView.setVisibility(View.VISIBLE);
                        holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                    }
                };
                holder.youTubeThumbnailView.initialize(api_key, new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {

                        youTubeThumbnailLoader.setVideo(youtubeVideoModelList.get(position).getVideo());
                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);

                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                        //write something for failure
                    }
                });
                //new
//            holder.youTubeThumbnailView.setVisibility(View.VISIBLE);
//            holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeVideoModelList.get(position).getVideo_url())));
//               Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, api_key, youtubeVideoModelList.get(position).getVideo());
//                ctx.startActivity(intent);
                    }
                });
                //new
            }
            else {
                holder.youTubeThumbnailView.setVisibility(View.GONE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.GONE);
//            Toast.makeText(ctx, "No Video In Founder", Toast.LENGTH_SHORT).show();
            }
        }
        else if (className.equalsIgnoreCase("GalleryActivity")){
            final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                }
                @Override
                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                    holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                }
            };
            holder.youTubeThumbnailView.initialize(api_key, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {

                    youTubeThumbnailLoader.setVideo(youtubeVideoModelList.get(position).getVideo());
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);

                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                    //write something for failure
                }
            });
            //new
//            holder.youTubeThumbnailView.setVisibility(View.VISIBLE);
//            holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeVideoModelList.get(position).getVideo_url())));
//               Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, api_key, youtubeVideoModelList.get(position).getVideo());
//                ctx.startActivity(intent);
                }
            });
            //new
        }
    }

    @Override
    public int getItemCount() {
        return youtubeVideoModelList.size();
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder{
        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;
        YouTubePlayerView youtube_youtubeView;
        public VideoInfoHolder(View itemView) {
            super(itemView);
            youtube_youtubeView=(YouTubePlayerView) itemView.findViewById(R.id.youtube_youtubeView);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        }
    }
}
