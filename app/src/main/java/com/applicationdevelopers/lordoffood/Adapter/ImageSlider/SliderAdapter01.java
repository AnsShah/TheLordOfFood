package com.applicationdevelopers.lordoffood.Adapter.ImageSlider;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applicationdevelopers.lordoffood.Model.ProductModel.ProductImagesModel;
import com.applicationdevelopers.lordoffood.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter01 extends SliderViewAdapter<SliderAdapter01.SliderAdapterVH> {
    private Context context;
    private int mCount;
    private List<ProductImagesModel> productImagesModels;

    public SliderAdapter01(Context context, List<ProductImagesModel> productImagesModels) {
        this.context = context;
        this.productImagesModels=productImagesModels;

    }

    public void setCount(int count)
    {
        this.mCount = count;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        switch (position) {
            case 0:
                /* viewHolder.textViewDescription.setText("HealthCare professionals ");*/
                viewHolder.textViewDescription.setTextSize(16);
                viewHolder.textViewDescription.setTextColor(Color.WHITE);

                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(productImagesModels.get(position).getImagesUrl())
                        /*    .fitCenter()*/
                        .into(viewHolder.imageViewBackground);
                break;
            case 1:
                /* viewHolder.textViewDescription.setText("HealthCare professionals ");*/
                viewHolder.textViewDescription.setTextSize(16);
                viewHolder.textViewDescription.setTextColor(Color.WHITE);
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(productImagesModels.get(position).getImagesUrl())
                        /*    .fitCenter()*/
                        .into(viewHolder.imageViewBackground);
                break;
            default:
                viewHolder.textViewDescription.setTextSize(16);
                viewHolder.textViewDescription.setTextColor(Color.WHITE);
                /*  viewHolder.textViewDescription.setText("Ohhhh! look at this!");*/
                viewHolder.imageGifContainer.setVisibility(View.GONE);

                Glide.with(viewHolder.itemView)
                        .load(R.drawable.img_loading_icon)
                        /*     .fitCenter()*/
                        .into(viewHolder.imageViewBackground);
                break;

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
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
