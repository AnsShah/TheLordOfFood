package com.applicationdevelopers.lordoffood.View.Home.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applicationdevelopers.lordoffood.Adapter.FavouriteAdapter.FavouriteAdapter;
import com.applicationdevelopers.lordoffood.LocalDatabase.PersonDBHelper;
import com.applicationdevelopers.lordoffood.NetworkManager.NetworkManager;
import com.applicationdevelopers.lordoffood.R;

public class FavouriteFragment extends Fragment {

    RecyclerView idFavouriteRecyclerview;
    LinearLayout idNoOfFavourites;

    private RecyclerView.LayoutManager mLayoutManager;
    private PersonDBHelper personDBHelper;
    private long orderCount;
    TextView idOrderContinueBtn;
    private FavouriteAdapter favouriteAdapter;
    double price1,quantity1;
    private Handler handler = new Handler();
    private String filter = "";
    NetworkManager networkManager;
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favourite, container, false);
        initLayout(view);
        return view;
    }
    private void initLayout(View view){
        context=getContext();
        networkManager=new NetworkManager(context);
        idNoOfFavourites=(LinearLayout) view.findViewById(R.id.idNoOfFavourites);
        idFavouriteRecyclerview=(RecyclerView) view.findViewById(R.id.idFavouriteRecyclerview);

        //RecyclerView
        idFavouriteRecyclerview.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        idFavouriteRecyclerview.setLayoutManager(mLayoutManager);
        //RecyclerView
        //init Local Db
        personDBHelper=new PersonDBHelper(context);
        //init Local Db
        handler.post(runnable);
        //populate recyclerview
        populaterecyclerView(filter);
        /*dbHelper=new PersonDBHelper(this);
        orderCount=dbHelper.getOrderCount();*/
        freeMemory();
    }
    private void checkOrder(){
        orderCount=personDBHelper.getFavouriteCount();
        if (orderCount == 0)
        {
            //idMarketPlaceFooter.setVisibility(View.GONE);
            idNoOfFavourites.setVisibility(View.VISIBLE);
        }
        else {
            idNoOfFavourites.setVisibility(View.GONE);
        }
    }
    private void populaterecyclerView(String filter) {
        /* localDbHelper = new LocalDbHelper(getContext());*/
        favouriteAdapter = new FavouriteAdapter(personDBHelper.productListingFavourite(filter), getContext(), idFavouriteRecyclerview);
        idFavouriteRecyclerview.setAdapter(favouriteAdapter);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here
            //check order
            checkOrder();
            //check order
            // Repeat every 2 seconds
            handler.postDelayed(runnable, 2000);
        }
    };
    public void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}