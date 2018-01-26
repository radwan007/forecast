package com.parent.forecast.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.parent.forecast.database.DatabaseHelper;
import com.parent.forecast.models.CityModel;
import com.parent.forecast.R;

/**
 * Created by mohamedradwan on 1/21/18.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<CityModel> itemModelList;

    DatabaseHelper mydb ;

    public CustomAdapter(Context context, ArrayList<CityModel> modelList) {
        this.context = context;
        this.itemModelList = modelList;


    }
    @Override
    public int getCount() {
        return itemModelList.size();
    }
    @Override
    public Object getItem(int position) {
        return itemModelList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = null;
        if (convertView == null) {

            mydb = new DatabaseHelper(context);

            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.card_item, null);
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            Button imgRemove = (Button) convertView.findViewById(R.id.imgRemove);
            final CityModel m = itemModelList.get(position);
            tvName.setText(m.getName());
            // click listiner for remove button
            imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mydb.delete_cities(m.getName());
                    itemModelList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }


    public void setData(ArrayList<CityModel> list) {
        itemModelList = list;
        notifyDataSetChanged();
    }

}