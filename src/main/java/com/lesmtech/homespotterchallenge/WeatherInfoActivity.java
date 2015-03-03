package com.lesmtech.homespotterchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Te on 3/3/15.
 */
public class WeatherInfoActivity extends FragmentActivity {

    public WeatherInfoFragment mWeatherInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        mWeatherInfoFragment = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.weatherInfoFragment);
        getSupportFragmentManager().beginTransaction().show(mWeatherInfoFragment).commit();
    }

    public static class WeatherInfoFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_weather_info, container, false);
            return v;
        }
    }
}
