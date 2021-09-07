package com.applicationdevelopers.lordoffood.Adapter.Invoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applicationdevelopers.lordoffood.Model.Person.Person;
import com.applicationdevelopers.lordoffood.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {
    private List<Person> mPeopleList;

    private Context mContext;
    private RecyclerView mRecyclerV;
    private String TAG="InvoiceAdapter";
    private double counter = 0;
    public InvoiceAdapter(List<Person> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }
    @Override
    public InvoiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.invoice_product_details_layout01, parent, false);
        // set the view's size, margins, paddings and layout parameters
        InvoiceAdapter.ViewHolder vh = new InvoiceAdapter.ViewHolder(v);
        return vh;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View layout;
        public ImageView idInvoiceProductImage;
        public TextView idInvoiceProductName;
        public TextView idInvoiceProductQuantity;
        public TextView idInvoiceProductUnitPrice;
        public TextView idInvoiceProductPrice;
        // public ElegantNumberButton btnquantity;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            idInvoiceProductImage=(ImageView) v.findViewById(R.id.idInvoiceProductImage);
            idInvoiceProductName=(TextView) v.findViewById(R.id.idInvoiceProductName);
            idInvoiceProductQuantity=(TextView) v.findViewById(R.id.idInvoiceProductQuantity);
            idInvoiceProductUnitPrice=(TextView) v.findViewById(R.id.idInvoiceProductUnitPrice);
            idInvoiceProductPrice=(TextView) v.findViewById(R.id.idInvoiceProductPrice);
        }
    }
    public void add(int position, Person person) {
        mPeopleList.add(position, person);
        notifyItemInserted(position);
    }
    @Override
    public void onBindViewHolder(final InvoiceAdapter.ViewHolder holder, final int position) {
        final Person person = mPeopleList.get(position);
        Glide.with(mContext).load(person.getImageUrl()).into(holder.idInvoiceProductImage);
        holder.idInvoiceProductName.setText(person.getPname());
        holder.idInvoiceProductQuantity.setText(person.getQty());
        holder.idInvoiceProductUnitPrice.setText("Rs."+person.getPrice());
        double price=Double.parseDouble(person.getPrice());
        double quantity=Double.parseDouble(person.getQty());
        double total=(quantity*price);
        int totalInt= (int) total;
        holder.idInvoiceProductPrice.setText("Rs."+totalInt);
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
