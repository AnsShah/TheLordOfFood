package com.applicationdevelopers.lordoffood.Adapter.ImageSlider;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.applicationdevelopers.lordoffood.Model.Banner.BannerModel;
import com.applicationdevelopers.lordoffood.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Designed & Developed By Syed Ans Ali
 * Date: 09/07/2021
 */
public class SliderAdapterStartUp extends SliderViewAdapter<SliderAdapterStartUp.SliderAdapterVH> {
    private Context context;
    private int mCount;
    private List<BannerModel> bannerModelList;

    public SliderAdapterStartUp(Context context) {
        this.context = context;
    }
    public SliderAdapterStartUp(Context context , @NotNull List<BannerModel> bannerModelList) {
        this.context = context;
        this.bannerModelList=bannerModelList;
        mCount=bannerModelList.size();
    }
    public void setCount(int count)
    {
        this.mCount = count;
    }
    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item01, null);
        return new SliderAdapterStartUp.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterStartUp.SliderAdapterVH viewHolder, final int position) {
//        switch (position) {
//            case 0:
//                /* viewHolder.textViewDescription.setText("HealthCare professionals ");*/
//                viewHolder.textViewDescription.setTextSize(16);
//                viewHolder.textViewDescription.setTextColor(Color.WHITE);
//
//                viewHolder.imageGifContainer.setVisibility(View.GONE);
//               /* Glide.with(viewHolder.itemView)
//                        .load(R.drawable.r4)
//                        *//*    .fitCenter()*//*
//                        .into(viewHolder.imageViewBackground);*/
//                Picasso.with(context)
//                        .load(R.drawable.banner1)
//                        .placeholder(R.drawable.img_loading_icon)
//                        .into(viewHolder.imageViewBackground);
//                break;
//            case 1:
//                /* viewHolder.textViewDescription.setText("HealthCare professionals ");*/
//                viewHolder.textViewDescription.setTextSize(16);
//                viewHolder.textViewDescription.setTextColor(Color.WHITE);
//                viewHolder.imageGifContainer.setVisibility(View.GONE);
//               /* Glide.with(viewHolder.itemView)
//                        .load(R.drawable.r5)
//                        *//*    .fitCenter()*//*
//                        .into(viewHolder.imageViewBackground);*/
//                Picasso.with(context)
//                        .load(R.drawable.banner10)
//                        .placeholder(R.drawable.img_loading_icon)
//                        .into(viewHolder.imageViewBackground);
//                break;
//            case 2:
//                /*        viewHolder.textViewDescription.setText("Medical Excellence");*/
//                viewHolder.textViewDescription.setTextSize(16);
//                viewHolder.textViewDescription.setTextColor(Color.WHITE);
//                viewHolder.imageGifContainer.setVisibility(View.GONE);
//               /* Glide.with(viewHolder.itemView)
//                        .load(R.drawable.r6)
//                        *//*.fitCenter()*//*
//                        .into(viewHolder.imageViewBackground);*/
//                Picasso.with(context)
//                        .load(R.drawable.banner11)
//                        .placeholder(R.drawable.img_loading_icon)
//                        .into(viewHolder.imageViewBackground);
//                break;
//        }
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        viewHolder.imageGifContainer.setVisibility(View.GONE);
        Picasso.with(context)
                .load(bannerModelList.get(position).getBannerImage())
                .placeholder(R.drawable.img_loading_icon)
                .into(viewHolder.imageViewBackground);

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return bannerModelList.size();
        //return mCount;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
