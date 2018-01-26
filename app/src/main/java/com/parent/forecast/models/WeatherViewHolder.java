package com.parent.forecast.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.parent.forecast.R;

public class WeatherViewHolder extends RecyclerView.ViewHolder {
    public TextView itemDate;
    public TextView itemTemperature;
    public TextView itemDescription;
    public TextView itemPressure;
    public TextView itemHumidity;
    public View lineView;

    public WeatherViewHolder(View view) {
        super(view);
        this.itemDate = (TextView) view.findViewById(R.id.itemDate);
        this.itemTemperature = (TextView) view.findViewById(R.id.itemTemperature);
        this.itemDescription = (TextView) view.findViewById(R.id.itemDescription);
        this.itemPressure = (TextView) view.findViewById(R.id.itemPressure);
        this.itemHumidity = (TextView) view.findViewById(R.id.itemHumidity);
        this.lineView = view.findViewById(R.id.lineView);
    }
}