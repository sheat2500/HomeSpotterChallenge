package com.lesmtech.homespotterchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lesmtech.homespotterchallenge.Model.WeatherInfoModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;

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
    }

    public static class WeatherInfoFragment extends Fragment {

        @InjectView(R.id.city)
        TextView city;

        @InjectView(R.id.weather)
        TextView weather;

        @InjectView(R.id.temp_min)
        TextView temp_min;

        @InjectView(R.id.temp_max)
        TextView temp_max;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_weather_info, container, false);
            ButterKnife.inject(this,v);
            WeatherInfoModel mWeatherModel = MainActivity.WeatherInfoModel;
            city.setText(mWeatherModel.name);
            weather.setText(mWeatherModel.weather.get(0).description);
            temp_min.setText(mWeatherModel.main.temp_min);
            temp_max.setText(mWeatherModel.main.temp_max);
            return v;
        }


    }
}
