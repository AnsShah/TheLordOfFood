package com.applicationdevelopers.lordoffood.Adapter.RelatedProductAdapter;

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
import com.applicationdevelopers.lordoffood.Adapter.ProductAdapter.ProductAdapter;
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

public class RelatedProductAdapter extends RecyclerView.Adapter<RelatedProductAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<ProductModel> productmodels;
    private List<ProductModel> productmodelsfull;
    RecyclerView recyclerView;
    String qty="";
    Snackbar snackbar;
    int pos=0;
    public RelatedProductAdapter(Context context, List<ProductModel> productmodels,RecyclerView recyclerView) {
        this.context = context;
        this.productmodels = productmodels;
        productmodelsfull=new ArrayList<>(productmodels);
        this.recyclerView=recyclerView;
    }
    @NonNull
    @Override
    public RelatedProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_product_layout,parent,false);
        // productModel=new ProductModel();
        return new RelatedProductAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final RelatedProductAdapter.ViewHolder holder, final int position) {
        //Glide.with(context).load(imageurl).into(holder.product_image);
        /* Glide.with(context).load(productmodels.get(position).getProduct_image()).into(holder.product_image);*/
        holder.title_item.setText(productmodels.get(position).getProduct_name());

//        holder.idRecipeCategoryRowCategory.setText(productmodels.get(position).getProduct_cat());
        //show Images
        GlobalClass.getInstance().getImageLoader().get(
                productmodels.get(position).getProduct_image(),
                ImageLoader.getImageListener(holder.related_image, R.drawable.img_loading_icon, R.drawable.img_loading_icon)
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
                    holder.related_image.setImageBitmap(response.getBitmap());
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
                    Cursor res=dbHandler.getSpecificData(holder.title_item.getText().toString());
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
        // Glide.with(context).load(productmodels.get(position).getImageURL()).into(holder.product_image);
        // Picasso.get().load(productmodels.get(position).getImageURL()).into(holder.product_image);

//       Image CLick
        holder.idProductImageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.idRelativeLayoutAddTocart.getVisibility()==View.VISIBLE){
                    holder.idRelativeLayoutAddTocart.setVisibility(View.GONE);
                }
                else {
                    holder.idRelativeLayoutAddTocart.setVisibility(View.VISIBLE);
                }
            }
        });
//       Image CLick
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
        TextView product_name,idRecipeCategoryRowPrice,idRecipeCategoryLocation,idRecipeCategoryViews,idRecipeCategoryOpenStatus,title_item;
        TextView price;
        ImageView product_image,related_image;
        CardView card_click,idProductImageCardView;
        TextView idRecipeCategoryRowCategory;
        LinearLayout idAddToCartLayout;
        RelativeLayout idProductRelLayout,idNextItemRelativeLayout,idPreviousItemRelativeLayout,idRelativeLayoutAddTocart;
        public ViewHolder(View itemView) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.idRecipeCategoryRowTitle);
            title_item = (TextView) itemView.findViewById(R.id.title_item);
            price = (TextView) itemView.findViewById(R.id.idRecipeCategoryRowPrice);
            idRecipeCategoryLocation=(TextView) itemView.findViewById(R.id.idRecipeCategoryLocation);
            idRecipeCategoryViews=(TextView) itemView.findViewById(R.id.idRecipeCategoryViews);
            idRecipeCategoryOpenStatus=(TextView) itemView.findViewById(R.id.idRecipeCategoryOpenStatus);
            product_image =(ImageView) itemView.findViewById(R.id.idRecipeCategoryRowImage);
            related_image =(ImageView) itemView.findViewById(R.id.related_image);
            idRecipeCategoryRowCategory=(TextView) itemView.findViewById(R.id.idRecipeCategoryRowCategory);
            idAddToCartLayout=(LinearLayout) itemView.findViewById(R.id.idAddToCartLayout);
            idProductRelLayout=(RelativeLayout) itemView.findViewById(R.id.idProductRelLayout);
            idNextItemRelativeLayout=(RelativeLayout) itemView.findViewById(R.id.idNextItemRelativeLayout);
            idPreviousItemRelativeLayout=(RelativeLayout) itemView.findViewById(R.id.idPreviousItemRelativeLayout);
            idRelativeLayoutAddTocart=(RelativeLayout) itemView.findViewById(R.id.idRelativeLayoutAddTocart);
            idProductImageCardView=(CardView) itemView.findViewById(R.id.idProductImageCardView);
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
