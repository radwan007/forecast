package com.parent.forecast.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by MAICHEL on 08/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String db_name = "forecast.db";
    public static final String city_forecast = "city_forecast";
    public static final String cities = "cities";

    protected ExternalDbOpenHelper mDbHelper;


    public DatabaseHelper(Context context) {
        super(context, db_name, null, 1);
//       SQLiteDatabase db = this.getWritableDatabase();

        //mDbHelper = ExternalDbOpenHelper.getInstance(context);
       // SQLiteDatabase db = mDbHelper.getDb();
        mDbHelper = ExternalDbOpenHelper.getInstance(context);

    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
/*
        sqLiteDatabase.execSQL("create table if not exists " + service_parameters +" (id INTEGER PRIMARY KEY AUTOINCREMENT, service_type TEXT, " +
                "external_system_id INTEGER, payment_service_id INTEGER, name_ar TEXT, name_en TEXT, position INTEGER, " +
                "visible TEXT, required TEXT, type TEXT, is_client_id TEXT, default_value TEXT, min_length INTEGER, max_length INTEGER)");


        sqLiteDatabase.execSQL("create table  if not exists " + service_provider_categories +" (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name_ar TEXT, " +
                "name_en TEXT, " +
                "description_ar TEXT, " +
                "description_en TEXT, " +
                "icon TEXT)");

        sqLiteDatabase.execSQL("create table  if not exists " + service_providers +" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "service_provider_category_id INTEGER, " +
                "name_ar TEXT, " +
                "name_en TEXT, " +
                "description_ar TEXT, " +
                "description_en TEXT, " +
                "logo TEXT)");

        sqLiteDatabase.execSQL("create table if not exists " + services +" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "service_provider_id INTEGER, " +
                "name_ar TEXT, " +
                "name_en TEXT, " +
                "description_ar TEXT, " +
                "description_en TEXT, " +
                "icon TEXT)");

        sqLiteDatabase.execSQL("create table if not exists " + options + "(name TEXT, value TEXT)");*/

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public boolean insert_city_forecast(Context context,  String  cityname, String  date, String temperature, String description,
                                             String windspeed,  String pressure, String humidity, String rain, String weatherid,
                                             String icon) {

       // SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db = mDbHelper.getDb();


        ContentValues contentValues = new ContentValues();

        contentValues.put("city_name", cityname.toLowerCase());
        contentValues.put("date", date);
        contentValues.put("temperature", temperature);
        contentValues.put("description", description);
        contentValues.put("windspeed", windspeed);
       // contentValues.put("winddirection", winddirection);
        contentValues.put("pressure", pressure);
        contentValues.put("humidity", humidity);
        contentValues.put("rain", rain);
        contentValues.put("weatherid", weatherid);
        contentValues.put("icon", icon);


        long result = db.insert(city_forecast, null, contentValues);
        if(result == -1)
        {
            //Toast.makeText(context, "Something wrong in service parameters", Toast.LENGTH_SHORT).show();
            Log.i("Forecast","Something wrong in Forecast ");
            return false;
        }
        else {
//            Toast.makeText(context, "Forecast successfully inserted" , Toast.LENGTH_LONG).show();
            Log.i("Forecast","Forecast successfully inserted");
            return true;
        }

    }

    public boolean insert_cities(Context context, String name) {

       // SQLiteDatabase db = this.getWritableDatabase();
         SQLiteDatabase db = mDbHelper.getDb();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);

        long result = db.insert(cities, null, contentValues);
        if(result == -1)
        {
            Toast.makeText(context, "Something wrong in service_provider_categories", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            //Toast.makeText(context, "service_provider_categories successfully inserted" , Toast.LENGTH_LONG).show();
            return true;
        }
    }


    public void delete_cities (String cityname) {

        Log.i("cityid",""+cityname);


        SQLiteDatabase db = mDbHelper.getDb();

        db.execSQL("DELETE FROM " + city_forecast+ " WHERE  city_name='"+cityname.toLowerCase()+"'");
        db.execSQL("DELETE FROM " + cities+ " WHERE  name='"+cityname.toLowerCase()+"'");
        //db.close();




    }



    public Cursor get_CityID (String CityName) {

        SQLiteDatabase db = mDbHelper.getDb();

        Cursor res = db.rawQuery("SELECT id FROM cities WHERE name = "+CityName, null);
        try{
            if (res != null) {
                res.moveToFirst();
            }
            return res;
        }catch(Exception e)
        {
            return null;
        }
    }



    public Cursor get_options () {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT value as last_update FROM options WHERE name = 'last_update'", null);
        try{
            if (res != null) {
                res.moveToFirst();
            }
            return res;
        }catch(Exception e)
        {
            return null;
        }
    }
    public boolean CheckIsDataAlreadyInDBorNot() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT value as last_update FROM options WHERE name = 'last_update'", null);
        if(cursor == null){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public void delete() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ city_forecast);
        db.execSQL("delete from "+ cities);
        db.close();
    }

    public ArrayList get_all_records() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from service_provider_categories", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            String t1 = cursor.getString(0);
            String t2 = cursor.getString(1);
            String t3 = cursor.getString(2);
            String t4 = cursor.getString(3);
            String t5 = cursor.getString(4);
            String t6 = cursor.getString(5);
            arrayList.add(t1 + "-" + t2 + ":" + t3 + "-" + t4 + "-" + t5 + "-" + t6);
            cursor.moveToNext();
        }

        return arrayList;
    }


}
