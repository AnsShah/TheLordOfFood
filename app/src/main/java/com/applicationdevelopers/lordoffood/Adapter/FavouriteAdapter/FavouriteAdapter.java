package com.applicationdevelopers.lordoffood.Adapter.FavouriteAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applicationdevelopers.lordoffood.LocalDatabase.PersonDBHelper;
import com.applicationdevelopers.lordoffood.Model.Person.Person;
import com.applicationdevelopers.lordoffood.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder>{
    private List<Person> mPeopleList;

    private Context mContext;
    private RecyclerView mRecyclerV;
    private String TAG="FavouriteAdapter";
    String qty="1";
    public FavouriteAdapter(List<Person> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.favourite_item_layout_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View layout;
        public TextView idOrderRowOrderTitle;
        public  TextView idOrderRowOrderCategory;
        public TextView idOrderRowNewPricee;
        public  TextView idOrderRowOrderquantity;
        public CircleImageView idOrderRowImage;
        public  TextView idOrderRowQuantityincrease;
        public TextView idOrderRowQuantitydecrease;
        public ImageView removeOrder;
        public  LinearLayout rootView,idAddToCartLayout;
        // public ElegantNumberButton btnquantity;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            idOrderRowOrderTitle=(TextView) v.findViewById(R.id.idOrderRowOrderTitle);
            idOrderRowOrderCategory=(TextView) v.findViewById(R.id.idOrderRowOrderCategory);
            idOrderRowNewPricee=(TextView) v.findViewById(R.id.idOrderRowNewPricee);
            idOrderRowOrderquantity=(TextView) v.findViewById(R.id.idOrderRowOrderquantity);
            idOrderRowImage=(CircleImageView) v.findViewById(R.id.idOrderRowImage);
            idOrderRowQuantityincrease=(TextView) v.findViewById(R.id.idOrderRowQuantityincrease);
            idOrderRowQuantitydecrease=(TextView) v.findViewById(R.id.idOrderRowQuantitydecrease);
            removeOrder=(ImageView) v.findViewById(R.id.removeOrder);
            rootView=(LinearLayout) v.findViewById(R.id.rootView);
            idAddToCartLayout=(LinearLayout) v.findViewById(R.id.idAddToCartLayout);

        }
    }
    public void add(int position, Person person) {
        mPeopleList.add(position, person);
        notifyItemInserted(position);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Person person = mPeopleList.get(position);
        holder.idOrderRowOrderTitle.setText(person.getPname());
        holder.idOrderRowNewPricee.setText(person.getPrice());
        holder.idOrderRowOrderCategory.setText(person.getCategory());
        //Glide.with(mContext).load(person.getImageUrl()).into(holder.idOrderRowImage);
        Picasso.with(mContext)
                .load(person.getImageUrl())
                .into(holder.idOrderRowImage);
        //Delete Order
        holder.removeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage(" Are you Sure to Delete Item?");
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PersonDBHelper dbHelper = new PersonDBHelper(mContext);
                        dbHelper.deleteFavourite(person.getPname(), mContext);
                        mPeopleList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mPeopleList.size());
                        notifyDataSetChanged();
                        //Snackbar.make(view,"Added to cart",Snackbar.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        //Delete Order
//        Add To Cart
        holder.idAddToCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDBHelper dbHandler = new PersonDBHelper(mContext);
                Cursor res=dbHandler.getSpecificData(holder.idOrderRowOrderTitle.getText().toString());
                if(res.getCount()==0){
                    qty="1";
                    dbHandler.insertProductOrder(person.getPname(),person.getPrice()
                            ,qty,person.getCategory(),person.getImageUrl());
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
                    boolean isUpdate =dbHandler.updateData(person.getPname(),person.getPrice(),qty
                            ,person.getCategory());
                    if(isUpdate==true){
                        Snackbar.make(v,"Added to cart", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(context,"Added to cart", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
//        Add To Cart

        //Go To Product
//        holder.rootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(mContext, ProductDetailActivity.class);
//                intent.putExtra("product_Name_Pro_Detail",person.getPname());
//                intent.putExtra("product_Price_Pro_Detail",person.getPrice());
//                intent.putExtra("product_Image_Pro_Detail",person.getImageUrl());
//                mContext.startActivity(intent);
//            }
//        });
//        //Go To Product
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }
    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }
}
