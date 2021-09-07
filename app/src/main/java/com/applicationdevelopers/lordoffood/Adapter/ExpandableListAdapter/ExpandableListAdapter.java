package com.applicationdevelopers.lordoffood.Adapter.ExpandableListAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applicationdevelopers.lordoffood.Model.Menu.MenuModel;
import com.applicationdevelopers.lordoffood.R;

import java.util.HashMap;
import java.util.List;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<MenuModel> listDataHeader;
    private HashMap<MenuModel, List<MenuModel>> listDataChild;
    private DrawerLayout drawerLayout;

    public ExpandableListAdapter(Context context, List<MenuModel> listDataHeader,
                                 HashMap<MenuModel, List<MenuModel>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }
    public ExpandableListAdapter(Context context, List<MenuModel> listDataHeader,
                                 HashMap<MenuModel, List<MenuModel>> listChildData, DrawerLayout drawerLayout) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.drawerLayout=drawerLayout;
    }
    @Override
    public MenuModel getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).menuName;
        final  int id=getChild(groupPosition,childPosition).id;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_child, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.lblListItem);
//        if (childText.equalsIgnoreCase("Starter Menu"))
//        {
//            txtListChild.setTextColor(R.color.lord_of_food_color);
//            txtListChild.setTypeface(txtListChild.getTypeface(), Typeface.BOLD);
//            txtListChild.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            });
//        }
//        else {
//            Log.wtf("Ok", "Ok");
//        }
            if (id==1){
//            Log.wtf("Header Value -> ",childText);
                txtListChild.setTextColor(context.getResources().getColor(R.color.lord_of_food_color));
                txtListChild.setTypeface(txtListChild.getTypeface(), Typeface.BOLD);
                txtListChild.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
//            txtListChild.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            });
        }
        else if(id==0){
                txtListChild.setTextColor(context.getResources().getColor(R.color.headingTitleColor));
                txtListChild.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                txtListChild.setTypeface(txtListChild.getTypeface(), Typeface.NORMAL);
//            Toast.makeText(context, "Name:"+childText, Toast.LENGTH_SHORT).show();
        }
        txtListChild.setText(childText);
        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {

        if (this.listDataChild.get(this.listDataHeader.get(groupPosition)) == null)
            return 0;
        else
            return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                    .size();
    }

    @Override
    public MenuModel getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();

    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).menuName;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_header, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        ImageView drop_down_arrow=convertView.findViewById(R.id.drop_down_arrow);
        if (groupPosition==1){
            drop_down_arrow.setVisibility(View.VISIBLE);
        }
        else {
            drop_down_arrow.setVisibility(View.GONE);
        }
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
