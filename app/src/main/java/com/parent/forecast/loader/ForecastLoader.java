package com.parent.forecast.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.parent.forecast.models.Weather;
import java.util.ArrayList;


public class ForecastLoader extends AbstractQueryLoader<ArrayList<Weather>> {

    String Cityname;

    public ForecastLoader(Context context, String CityName) {
        super(context);

        this.Cityname = CityName;
    }


    @Override
    public ArrayList<Weather> loadInBackground() {

        ArrayList<Weather> results = null;
        Cursor GroupCursor = null;
        try {


            final SQLiteDatabase database = mDbHelper.getDb();

            Log.i("asdfghjg",Cityname);
            String selectQuery = "SELECT * FROM city_forecast WHERE city_name=?";
            GroupCursor  = database.rawQuery(selectQuery, new String[] { Cityname });


            if (GroupCursor != null && GroupCursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    int _id = GroupCursor.getInt(0);
                    int city_id = GroupCursor.getInt(1);
                    String date = GroupCursor.getString(2);
                    String temperature = GroupCursor.getString(3);
                    String description = GroupCursor.getString(4);
                    String windspeed = GroupCursor.getString(5);
                    String pressure = GroupCursor.getString(6);
                    String humidity = GroupCursor.getString(7);
                    String rain = GroupCursor.getString(8);
                    String weather_id = GroupCursor.getString(9);
                    String icon = GroupCursor.getString(10);

                    results.add(new Weather(_id, city_id,date,temperature,description,
                            windspeed,pressure,humidity,rain,weather_id,icon));
                } while (GroupCursor.moveToNext());
            }
        } finally {
            if (GroupCursor != null) {
                GroupCursor.close();
            }
        }

        return results;
    }
}