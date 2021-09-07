package com.applicationdevelopers.lordoffood.Adapter.ProductAdapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.applicationdevelopers.lordoffood.Controllers.GlobalClass;
import com.applicationdevelopers.lordoffood.LocalDatabase.PersonDBHelper;
import com.applicationdevelopers.lordoffood.Model.ProductModel.ProductModel;
import com.applicationdevelopers.lordoffood.R;
import com.applicationdevelopers.lordoffood.View.Cart.Fragment.ProductDetailsFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class SearchProductAdapter  extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<ProductModel> productmodels;
    private List<ProductModel> productmodelsfull;
    RecyclerView recyclerView;
    String qty="";
    Snackbar snackbar;
    int pos=0;
    public SearchProductAdapter(Context context, List<ProductModel> productmodels,RecyclerView recyclerView) {
        this.context = context;
        this.productmodels = productmodels;
        productmodelsfull=new ArrayList<>(productmodels);
        this.recyclerView=recyclerView;
    }
    @NonNull
    @Override
    public SearchProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_category_list_row_search,parent,false);
        // productModel=new ProductModel();
        return new SearchProductAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final SearchProductAdapter.ViewHolder holder, final int position) {
        holder.product_name.setText(productmodels.get(position).getProduct_name());
        holder.price.setText("Rs."+productmodels.get(position).getProduct_price());
        holder.idRecipeCategoryLocation.setText(productmodels.get(position).getLocation());
        String views=productmodels.get(position).getViews();
        if (views.equalsIgnoreCase("null") || views.isEmpty()){
            holder.idRecipeCategoryViews.setText("0 Views");
        }
        else {
            holder.idRecipeCategoryViews.setText(productmodels.get(position).getViews()+" Views");
        }
        if (productmodels.get(position).getOpen_status().equalsIgnoreCase("Close Now")){
            holder.idRecipeCategoryOpenStatus.setText(productmodels.get(position).getOpen_status());
            holder.idRecipeCategoryOpenStatus.setBackgroundResource(R.drawable.round_corner_pakjinnah_red);
        }
        else {
            holder.idRecipeCategoryOpenStatus.setText(productmodels.get(position).getOpen_status());
            holder.idRecipeCategoryOpenStatus.setBackgroundResource(R.drawable.round_corner_pakjinnah_green);
        }
        //Glide.with(context).load(imageurl).into(holder.product_image);
        /* Glide.with(context).load(productmodels.get(position).getProduct_image()).into(holder.product_image);*/
        holder.idRecipeCategoryRowCategory.setText(productmodels.get(position).getProduct_cat());
        //show Images
        GlobalClass.getInstance().getImageLoader().get(
                productmodels.get(position).getProduct_image(),
                ImageLoader.getImageListener(holder.product_image, R.drawable.img_loading_icon, R.drawable.img_loading_icon)
        );
        //Download and show in imageView
        GlobalClass.getInstance().getImageLoader().get(productmodels.get(position).getProduct_image(), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Image Load Error: " + error.getMessage());
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageView
                    //Glide.with(context).load(productmodels.get(position).getProduct_image()).into(holder.product_image);
                    holder.product_image.setImageBitmap(response.getBitmap());
                }
            }
        });
        //Show Images
        //Add to Cart
        holder.idAddToCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productmodels.get(position).getOpen_status().equalsIgnoreCase("Open Now")){
                    PersonDBHelper dbHandler = new PersonDBHelper(context);
                    Cursor res=dbHandler.getSpecificData(holder.product_name.getText().toString());
                    if(res.getCount()==0){
                        qty="1";
                        dbHandler.insertProductOrder(productmodels.get(position).getProduct_name(),productmodels.get(position).getProduct_price()
                                ,qty,productmodels.get(position).getProduct_cat(),productmodels.get(position).getProduct_image());
                        //Toast.makeText(context,"Added to cart", Toast.LENGTH_LONG).show();
                        Snackbar.make(view,"Added to cart", Snackbar.LENGTH_SHORT).show();
                        return;
                    }else {
                        int qty1=0;
                        while (res.moveToNext()){
                            qty1=(res.getInt(3));
                        }
                        int newQty=0;
                        newQty=qty1+1;
                        qty=String.valueOf(newQty);
                        boolean isUpdate =dbHandler.updateData(productmodels.get(position).getProduct_name(),productmodels.get(position).getProduct_price(),qty
                                ,productmodels.get(position).getProduct_cat());
                        if(isUpdate==true){
                            Snackbar.make(view,"Added to cart", Snackbar.LENGTH_SHORT).show();
                            //Toast.makeText(context,"Added to cart", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else {
                    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        vibrator.vibrate(500);
                    }
//                    snackbar = Snackbar.make(view,"Sorry! Closed Now", Snackbar.LENGTH_SHORT).show();
                    snackbar = Snackbar.make(view, "Sorry!!! Closed Now", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(context.getResources().getColor(R.color.redColor));
                    snackbar.show();
//                    Toast.makeText(context, "Sorry! Closed Now", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Add to Cart
//        //product Details Fragment
//        holder.idProductRelLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment fragment = new ProductDetailsFragment();
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frame_main,fragment)
//                        .addToBackStack(null)
//                        .commit();
//                Bundle bundle = new Bundle();
//                //bundle.putString("product_id",productmodels.get(position).getProduct_id());
//                bundle.putString("product_name",productmodels.get(position).getProduct_name());
//                bundle.putString("price",productmodels.get(position).getProduct_price());
//                bundle.putString("product_image",productmodels.get(position).getProduct_image());
//                bundle.putString("product_category",productmodels.get(position).getProduct_cat());
//                bundle.putString("product_desc",productmodels.get(position).getDescription_post());
//                bundle.putString("status",productmodels.get(position).getOpen_status());
//                bundle.putInt("sectionId",productmodels.get(position).getSectionId());
//                //bundle.putString("product_desc",productmodels.get(position).getProduct_desc());
//                //bundle.putString("product_image",productmodels.get(position).getImageURL());
//                fragment.setArguments(bundle);
//                //Toast.makeText(context, "Yes We Are Ready to go on!!!!!!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        //product Details Fragment
//        Next
        holder.idNextItemRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos=position;
                pos=pos+1;
                recyclerView.scrollToPosition(pos);
//                Toast.makeText(context, "Current Position:"+pos, Toast.LENGTH_SHORT).show();
            }
        });
//        Next
//        Previous
        holder.idPreviousItemRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos=position;
                pos=pos-1;
                recyclerView.scrollToPosition(pos);
//                Toast.makeText(context, "Current Position:"+pos, Toast.LENGTH_SHORT).show();
            }
        });
//        Previous
        // Glide.with(context).load(productmodels.get(position).getImageURL()).into(holder.product_image);
        // Picasso.get().load(productmodels.get(position).getImageURL()).into(holder.product_image);
    }
    @Override
    public int getItemCount() {
        return productmodels.size();
    }
    @Override
    public Filter getFilter() {
        return productFilter;
    }
    private Filter productFilter=new Filter() {
        String filterpattern;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProductModel> filterdList=new ArrayList<>();
            if (constraint==null || constraint.length()==0)
            {
                filterdList.addAll(productmodelsfull);
            }
            else
            {
                filterpattern=constraint.toString().toLowerCase().trim();
            }
            for (ProductModel productModel:productmodelsfull)
            {
                if (productModel.getProduct_name().toLowerCase().contains(filterpattern)) {
                    filterdList.add(productModel);
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterdList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productmodels.clear();
            productmodels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView product_name,idRecipeCategoryRowPrice,idRecipeCategoryLocation,idRecipeCategoryViews,idRecipeCategoryOpenStatus;
        TextView price;
        ImageView product_image;
        CardView card_click;
        TextView idRecipeCategoryRowCategory;
        LinearLayout idAddToCartLayout;
        RelativeLayout idProductRelLayout,idNextItemRelativeLayout,idPreviousItemRelativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.idRecipeCategoryRowTitle);
            price = (TextView) itemView.findViewById(R.id.idRecipeCategoryRowPrice);
            idRecipeCategoryLocation=(TextView) itemView.findViewById(R.id.idRecipeCategoryLocation);
            idRecipeCategoryViews=(TextView) itemView.findViewById(R.id.idRecipeCategoryViews);
            idRecipeCategoryOpenStatus=(TextView) itemView.findViewById(R.id.idRecipeCategoryOpenStatus);
            product_image =(ImageView) itemView.findViewById(R.id.idRecipeCategoryRowImage);
            idRecipeCategoryRowCategory=(TextView) itemView.findViewById(R.id.idRecipeCategoryRowCategory);
            idAddToCartLayout=(LinearLayout) itemView.findViewById(R.id.idAddToCartLayout);
            idProductRelLayout=(RelativeLayout) itemView.findViewById(R.id.idProductRelLayout);
            idNextItemRelativeLayout=(RelativeLayout) itemView.findViewById(R.id.idNextItemRelativeLayout);
            idPreviousItemRelativeLayout=(RelativeLayout) itemView.findViewById(R.id.idPreviousItemRelativeLayout);
            //card_click = itemView.findViewById(R.id.card_click);
            //idRecipeCategoryRowPrice=itemView.findViewById(R.id.idRecipeCategoryRowPrice);
        }
    }
    public void filterList(ArrayList<ProductModel> filtered)
    {
        productmodels=filtered;
        notifyDataSetChanged();
    }
}