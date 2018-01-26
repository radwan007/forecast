package com.parent.forecast.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.parent.forecast.models.CityModel;


public class CitiesLoader extends AbstractQueryLoader<ArrayList<CityModel>> {


    public CitiesLoader(Context context) {
        super(context);
    }


    @Override
    public ArrayList<CityModel> loadInBackground() {

        ArrayList<CityModel> results = null;
        Cursor GroupCursor = null;
        try {


            final SQLiteDatabase database = mDbHelper.getDb();

            GroupCursor = database.rawQuery("SELECT * FROM cities", null);

            if (GroupCursor != null && GroupCursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    int _id = GroupCursor.getInt(0);
                    String name = GroupCursor.getString(1);

                    results.add(new CityModel(_id,name));
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