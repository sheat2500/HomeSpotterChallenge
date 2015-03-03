package com.lesmtech.homespotterchallenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lesmtech.homespotterchallenge.Model.WeatherInfoModel;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity {

    public final static int FETCH_WEATHER_INFO = 0;

    private WeatherInfoBroadcast mWeatherInfoBroadcast = new WeatherInfoBroadcast();

    // Standard request URL
    private final String url = "http://api.openweathermap.org/data/2.5/weather?q=";

    private String requestURL;

    private ProgressDialog mProgressDialog;


    private static WeatherInfoModel WeatherInfoModel = new WeatherInfoModel();

    // BroadcastReceiver
    public class WeatherInfoBroadcast extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.lesmtech.broadreceive.MainActivity.WeatherInfoBroadcast";

        @Override
        public void onReceive(Context context, Intent intent) {

            boolean responseStatu = intent.getBooleanExtra(FetchInfoService.RESPONSE_STATE, false);
            String responseString = intent.getStringExtra(FetchInfoService.RESPONSE_STRING);
            String responseMessage = intent.getStringExtra(FetchInfoService.RESPONSE_MESSAGE);

            System.out.println(responseMessage.toString());
            if (responseStatu) {
                if (responseString.equals(FetchInfoService.WEATHER_INFO)) {
                    // Gson Application
                    GsonBuilder mGsonBuilder = new GsonBuilder();
                    Gson mGson = mGsonBuilder.create();
                    WeatherInfoModel mWeatherInfoModel = mGson.fromJson(responseMessage, WeatherInfoModel.class);
                    WeatherInfoModel = mWeatherInfoModel;
                    renderView(mWeatherInfoModel);
                } else {
                    // May have other services
                }
            } else {
                displayToast("Check Your NetWork");
            }
        }
    }

    @InjectView(R.id.city)
    TextView city;

    @InjectView(R.id.weather)
    TextView weather;

    @InjectView(R.id.main)
    TextView main;

    @InjectView(R.id.location)
    Spinner location;

    @InjectView(R.id.more)
    Button buttom_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Wait");
    }

    // OnClick to fetchWeatherData from Service
    @OnClick(R.id.fetchWeatherData)
    void fetchWeatherData() {
        // Get weather data of the city
        requestURL = url + location.getSelectedItem().toString();
        // Start Service;
        startFetchWeatherInfoService();
    }

    @OnClick(R.id.more)
    void moreData(){
        Intent intent = new Intent("com.lesmtech.homespotterchallenge.weatherInfoActivity");
        startActivity(intent);
    }


    private void startFetchWeatherInfoService() {

        if (!isNetworkAvailable()) {
            displayToast("No NetWork");
        } else {
            mProgressDialog.show();
            Intent intent = new Intent(this, FetchInfoService.class);
            intent.putExtra("type", FETCH_WEATHER_INFO);
            intent.putExtra("url", requestURL);
            startService(intent);
            displayToast("Start Service");
        }
    }

    public void renderView(WeatherInfoModel mWeatherInfoModel) {
        city.setText(mWeatherInfoModel.name);
        weather.setText(mWeatherInfoModel.weather.get(0).main);
        main.setText(mWeatherInfoModel.main.humidity);
        buttom_more.setVisibility(View.VISIBLE);
        mProgressDialog.cancel();
    }

    // Register BroadReceiver
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(WeatherInfoBroadcast.PROCESS_RESPONSE);
        registerReceiver(mWeatherInfoBroadcast, intentFilter);
    }

    // Unregister BroadReceiver
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mWeatherInfoBroadcast);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // NetWork
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.refresh:
                if(requestURL == null){
                    // Do Nothing
                }
                else{
                    startFetchWeatherInfoService();
                }
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
