package com.parent.forecast.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.parent.forecast.R;
import com.parent.forecast.adapters.ViewPagerAdapter;
import com.parent.forecast.adapters.WeatherRecyclerAdapter;
import com.parent.forecast.fragments.RecyclerViewFragment;
import com.parent.forecast.loader.ForecastLoader;
import com.parent.forecast.models.Weather;

public class ForecastDetailsActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<ArrayList<Weather>>{


    private static Map<String, Integer> speedUnits = new HashMap<>(3);
    private static Map<String, Integer> pressUnits = new HashMap<>(3);
    private static boolean mappingsInitialised = false;

    ViewPager viewPager;
    TabLayout tabLayout;

    View appView;

    private  ArrayList<Weather> longTermTodayWeather = new ArrayList<>();
    private ArrayList<Weather> longTermTomorrowWeather = new ArrayList<>();
    private ArrayList<Weather> longTermWeatherthird = new ArrayList<>();
    private ArrayList<Weather> longTermWeatherfourth = new ArrayList<>();
    private ArrayList<Weather> longTermWeatherfive = new ArrayList<>();

    String thirdday,fourthday,fiveday;
    String[] days = new String[] { "SATURDAY" , "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};

    static String Cityname;
    public static void start(Context context, String CityName) {

        Intent starter = new Intent(context, ForecastDetailsActivity.class);
        Cityname = CityName;
        context.startActivity(starter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initiate activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_details);
        appView = findViewById(R.id.viewApp);


        // Load toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(Cityname);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Define Back Button Function
                    onBackPressed();
                }
            });

        }

        // Initialize viewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        getSupportLoaderManager().initLoader(0, null, this);

    }



    public WeatherRecyclerAdapter getAdapter(int id) {
        WeatherRecyclerAdapter weatherRecyclerAdapter;

        if (id == 0) {
            weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, longTermTodayWeather);
        } else if (id == 1) {
            weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, longTermTomorrowWeather);
        } else if(id == 2) {
            weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, longTermWeatherthird);
        } else if(id == 3){
            weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, longTermWeatherfourth);
        } else {
            weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, longTermWeatherfive);
        }
        return weatherRecyclerAdapter;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void initMappings() {
        if (mappingsInitialised)
            return;
        mappingsInitialised = true;
        speedUnits.put("m/s", R.string.speed_unit_mps);
        speedUnits.put("kph", R.string.speed_unit_kph);
        speedUnits.put("mph", R.string.speed_unit_mph);
        speedUnits.put("kn", R.string.speed_unit_kn);

        pressUnits.put("hPa", R.string.pressure_unit_hpa);
        pressUnits.put("kPa", R.string.pressure_unit_kpa);
        pressUnits.put("mm Hg", R.string.pressure_unit_mmhg);
    }

    private String localize(SharedPreferences sp, String preferenceKey, String defaultValueKey) {
        return localize(sp, this, preferenceKey, defaultValueKey);
    }

    public static String localize(SharedPreferences sp, Context context, String preferenceKey, String defaultValueKey) {
        String preferenceValue = sp.getString(preferenceKey, defaultValueKey);
        String result = preferenceValue;
        if ("speedUnit".equals(preferenceKey)) {
            if (speedUnits.containsKey(preferenceValue)) {
                result = context.getString(speedUnits.get(preferenceValue));
            }
        } else if ("pressureUnit".equals(preferenceKey)) {
            if (pressUnits.containsKey(preferenceValue)) {
                result = context.getString(pressUnits.get(preferenceValue));
            }
        }
        return result;
    }


    private void updateLongTermWeatherUI(String thirdday,String fourthday ,String fiveday) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundleToday = new Bundle();
        bundleToday.putInt("day", 0);
        RecyclerViewFragment recyclerViewFragmentToday = new RecyclerViewFragment();
        recyclerViewFragmentToday.setArguments(bundleToday);
        viewPagerAdapter.addFragment(recyclerViewFragmentToday, getString(R.string.today));

        Bundle bundleTomorrow = new Bundle();
        bundleTomorrow.putInt("day", 1);
        RecyclerViewFragment recyclerViewFragmentTomorrow = new RecyclerViewFragment();
        recyclerViewFragmentTomorrow.setArguments(bundleTomorrow);
        viewPagerAdapter.addFragment(recyclerViewFragmentTomorrow, getString(R.string.tomorrow));


        Bundle bundlethird = new Bundle();
        bundlethird.putInt("day", 2);
        RecyclerViewFragment recyclerViewFragmentthird = new RecyclerViewFragment();
        recyclerViewFragmentthird.setArguments(bundlethird);
        viewPagerAdapter.addFragment(recyclerViewFragmentthird, thirdday);



        Bundle bundlefourth = new Bundle();
        bundlefourth.putInt("day", 3);
        RecyclerViewFragment recyclerViewFragmentfourth = new RecyclerViewFragment();
        recyclerViewFragmentfourth.setArguments(bundlefourth);
        viewPagerAdapter.addFragment(recyclerViewFragmentfourth, fourthday);



        Bundle bundlefive = new Bundle();
        bundlefive.putInt("day", 4);
        RecyclerViewFragment recyclerViewFragmentfive = new RecyclerViewFragment();
        recyclerViewFragmentfive.setArguments(bundlefive);
        viewPagerAdapter.addFragment(recyclerViewFragmentfive, fiveday);


        int currentPage = viewPager.getCurrentItem();

        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

            currentPage = 0;

        viewPager.setCurrentItem(currentPage, false);


    }


    @Override
    public Loader<ArrayList<Weather>> onCreateLoader(int id, Bundle args) {
        return new ForecastLoader(this, Cityname);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Weather>> loader, ArrayList<Weather> data) {



        for (int i = 0; i < data.size(); i++) {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.parseLong(data.get(i).get_date()));

            Calendar today = Calendar.getInstance();
            if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                longTermTodayWeather.add(data.get(i));

            } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 1) {
                longTermTomorrowWeather.add(data.get(i));
            } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 2) {
                longTermWeatherthird.add(data.get(i));
               thirdday=days[cal.get(Calendar.DAY_OF_WEEK)];
            } else if(cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 3) {
                longTermWeatherfourth.add(data.get(i));
                fourthday= days[cal.get(Calendar.DAY_OF_WEEK)];
            } else if(cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 4){
                longTermWeatherfive.add(data.get(i));
                fiveday = days[cal.get(Calendar.DAY_OF_WEEK)];


            }


        }

        updateLongTermWeatherUI(thirdday,fourthday,fiveday);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Weather>> loader)
    {

    }


}
