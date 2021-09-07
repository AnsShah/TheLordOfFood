package com.applicationdevelopers.lordoffood.Adapter.MenuAdapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.applicationdevelopers.lordoffood.Controllers.GlobalClass;
import com.applicationdevelopers.lordoffood.LocalDatabase.PersonDBHelper;
import com.applicationdevelopers.lordoffood.Model.Menu.MenuModel;
import com.applicationdevelopers.lordoffood.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<MenuModel> menuModels;
    private List<MenuModel> menuModelListfull;
    String qty="";
    Snackbar snackbar;
    public MenuAdapter(Context context, List<MenuModel> menuModels) {
        this.context = context;
        this.menuModels = menuModels;
        menuModelListfull=new ArrayList<>(menuModels);
    }
    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recipe_list_row,parent,false);
        // productModel=new ProductModel();
        return new MenuAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final MenuAdapter.ViewHolder holder, final int position) {
        //Glide.with(context).load(imageurl).into(holder.product_image);
        /* Glide.with(context).load(productmodels.get(position).getProduct_image()).into(holder.product_image);*/
        holder.product_name.setText(menuModels.get(position).getMenuName());
        holder.price.setText("Rs."+menuModels.get(position).getProduct_price());
        //show Images
        GlobalClass.getInstance().getImageLoader().get(
                menuModels.get(position).getProduct_image(),
                ImageLoader.getImageListener(holder.product_image, R.drawable.img_loading_icon, R.drawable.img_loading_icon)
        );
        //Download and show in imageView
        GlobalClass.getInstance().getImageLoader().get(menuModels.get(position).getProduct_image(), new ImageLoader.ImageListener() {
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
//        Add To Cart
        holder.card_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuModels.get(position).getOpenStatus().equalsIgnoreCase("Open Now")){
                    PersonDBHelper dbHandler = new PersonDBHelper(context);
                    Cursor res=dbHandler.getSpecificData(holder.product_name.getText().toString());
                    if(res.getCount()==0){
                        qty="1";
                        dbHandler.insertProductOrder(menuModels.get(position).getMenuName(),menuModels.get(position).getProduct_price()
                                ,qty,"Deals",menuModels.get(position).getProduct_image());
                        //Toast.makeText(context,"Added to cart", Toast.LENGTH_LONG).show();
                        Snackbar.make(v,"Added to cart", Snackbar.LENGTH_SHORT).show();
                        return;
                    }else {
                        int qty1=0;
                        while (res.moveToNext()){
                            qty1=(res.getInt(3));
                        }
                        int newQty=0;
                        newQty=qty1+1;
                        qty=String.valueOf(newQty);
                        boolean isUpdate =dbHandler.updateData(menuModels.get(position).getMenuName(),menuModels.get(position).getProduct_price(),qty
                                ,"Deals");
                        if(isUpdate==true){
                            Snackbar.make(v,"Added to cart", Snackbar.LENGTH_SHORT).show();
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
//                    Snackbar.make(v,"Sorry! Closed Now", Snackbar.LENGTH_SHORT).show();
                    snackbar = Snackbar.make(v, "Sorry!!! Closed Now", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(context.getResources().getColor(R.color.redColor));
                    snackbar.show();
//                    Toast.makeText(context, "Sorry! Closed Now", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        Add To Cart
    }
    @Override
    public int getItemCount() {
        return menuModels.size();
    }
    @Override
    public Filter getFilter() {
        return productFilter;
    }
    private Filter productFilter=new Filter() {
        String filterpattern;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MenuModel> filterdList=new ArrayList<>();
            if (constraint==null || constraint.length()==0)
            {
                filterdList.addAll(menuModelListfull);
            }
            else
            {
                filterpattern=constraint.toString().toLowerCase().trim();
            }
            for (MenuModel productModel:menuModelListfull)
            {
                if (productModel.getMenuName().toLowerCase().contains(filterpattern)) {
                    filterdList.add(productModel);
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterdList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            menuModels.clear();
            menuModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView product_name;
        TextView price;
        ImageView product_image;
        CardView card_click;
        TextView idRecipeCategoryRowCategory;
        LinearLayout idAddToCartLayout;
        RelativeLayout idProductRelLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.idRecipeCategoryRowTitle);
            price = (TextView) itemView.findViewById(R.id.idRecipeCategoryRowPrice);
            product_image =(ImageView) itemView.findViewById(R.id.idRecipeCategoryRowImage);
            idAddToCartLayout=(LinearLayout) itemView.findViewById(R.id.idAddToCartLayout);
            card_click=(CardView) itemView.findViewById(R.id.idRewardCardView);
        }
    }
    public void filterList(ArrayList<MenuModel> filtered)
    {
        menuModels=filtered;
        notifyDataSetChanged();
    }
}