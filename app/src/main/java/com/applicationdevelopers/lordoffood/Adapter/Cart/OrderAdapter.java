package com.applicationdevelopers.lordoffood.Adapter.Cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applicationdevelopers.lordoffood.LocalDatabase.PersonDBHelper;
import com.applicationdevelopers.lordoffood.Model.Person.Person;
import com.applicationdevelopers.lordoffood.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private List<Person> mPeopleList;

    private Context mContext;
    private RecyclerView mRecyclerV;
    private String TAG="OrderAdapter";
    private double counter = 0;
    private int quantity=0;
    public OrderAdapter(List<Person> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.order_row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        public TextView idOrderRowOrderTitle;
        public  TextView idOrderRowOrderCategory;
        public TextView idOrderRowNewPricee;
        public  TextView idOrderRowOrderquantity;
        public CircleImageView idOrderRowImage;
        public  TextView idOrderRowQuantityincrease;
        public TextView idOrderRowQuantitydecrease;
        public ImageView removeOrder;
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
        holder.idOrderRowOrderquantity.setText(person.getQty());
        holder.idOrderRowNewPricee.setText(person.getPrice());
        holder.idOrderRowOrderCategory.setText(person.getCategory());
        Glide.with(mContext).load(person.getImageUrl()).into(holder.idOrderRowImage);

        //Increase Quantity
        holder.idOrderRowQuantityincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = Double.parseDouble(holder.idOrderRowOrderquantity.getText().toString().trim());
                if (counter > 50) counter = 0;
                counter++;
                quantity= (int) counter;
                holder.idOrderRowOrderquantity.setText(String.valueOf(quantity));
                PersonDBHelper dbHandler = new PersonDBHelper(mContext);
                boolean isUpdate =dbHandler.updateData(person.getPname(),person.getPrice(),holder.idOrderRowOrderquantity.getText().toString(),holder.idOrderRowOrderCategory.getText().toString());
                if(isUpdate==true){
                    Log.d(TAG,"Order quantity"+holder.idOrderRowOrderquantity.getText().toString());
                }
            }
        });
        //Increase Quantity
        //Decrease Quantity
        holder.idOrderRowQuantitydecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Double.parseDouble(holder.idOrderRowOrderquantity.getText().toString().trim()) > 1) {
                    counter = Double.parseDouble(holder.idOrderRowOrderquantity.getText().toString().trim());
                    if(counter >= 2)
                        counter--;
                    PersonDBHelper dbHandler = new PersonDBHelper(mContext);
                    boolean isUpdate =dbHandler.updateData(person.getPname(),person.getPrice(),holder.idOrderRowOrderquantity.getText().toString(),holder.idOrderRowOrderCategory.getText().toString());
                    if(isUpdate==true){
                        Log.d(TAG,"Order quantity"+holder.idOrderRowOrderquantity.getText().toString());
                    }
                    else
                        counter = 1;
                    quantity= (int) counter;
                    holder.idOrderRowOrderquantity.setText(String.valueOf(quantity));
                    boolean isUpdate1 =dbHandler.updateData(person.getPname(),person.getPrice(),holder.idOrderRowOrderquantity.getText().toString(),holder.idOrderRowOrderCategory.getText().toString());
                    if(isUpdate1==true){
                        Log.d(TAG,"Order quantity"+holder.idOrderRowOrderquantity.getText().toString());
                    }
                }
            }
        });
        //Decrease Quantity
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
                        dbHelper.deleteOrder(person.getPname(), mContext);

                        mPeopleList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mPeopleList.size());
                        notifyDataSetChanged();
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

    }
    @Override
    public int getItemCount() {
        return mPeopleList.size();

    }
    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }
}
