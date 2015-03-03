package com.lesmtech.homespotterchallenge;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Te on 2/13/15.
 */
public class FetchInfoService extends IntentService {


    public static final String RESPONSE_STATE = "response_state";
    public static final String RESPONSE_STRING = "response_type";
    public static final String RESPONSE_MESSAGE = "response_message";
    public static final String WEATHER_INFO = "weatherInfo";

    private String Url;
    private OkHttpClient okHttpClient = new OkHttpClient();

    public FetchInfoService() {
        super("FetchInfoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int type = intent.getIntExtra("type", -1);
        switch (type) {
            case MainActivity.FETCH_WEATHER_INFO:
                Url = intent.getStringExtra("url");
                fetchWeatherInfo(Url);
                break;
            default:
                break;
        }
        Log.i("FetchInfoService", "onHandle");
    }

    private void fetchWeatherInfo(String Url) {
        try {
            Request request = new Request.Builder().url(Url).build();
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.i("FetchWeatherInfo", "Unsuccessful");
                Intent broadcastIntent = new Intent(MainActivity.WeatherInfoBroadcast.PROCESS_RESPONSE);
                broadcastIntent.putExtra(RESPONSE_STATE, false);
                broadcastIntent.putExtra(RESPONSE_STRING, WEATHER_INFO);
                broadcastIntent.putExtra(RESPONSE_MESSAGE, response.body().string());
                sendBroadcast(broadcastIntent);
            } else {
                Intent broadcastIntent = new Intent(MainActivity.WeatherInfoBroadcast.PROCESS_RESPONSE);
                broadcastIntent.putExtra(RESPONSE_STATE, true);
                broadcastIntent.putExtra(RESPONSE_STRING, WEATHER_INFO);
                broadcastIntent.putExtra(RESPONSE_MESSAGE, response.body().string());
                sendBroadcast(broadcastIntent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
