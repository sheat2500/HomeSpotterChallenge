# HomeSpotter Challenge
## Introduction
*  Connect to the service
*  Parse the data from the service into a format readable by the application
*  Display current weather information within the app
*  Does not crash
*  Handles common error scenarios (such as) No network/service api unavailable
*  Bad/Malformed data
*  Uses git and github for source control
*  Includes a readme with instructions for building and verifying the functionality of the app

## Details 
- FetchInfoService (IntentService) to fetch data from server and communicate with MainActivity
- WeatherInfoBroadcast, receive JSON from IntentService and parse JSON to Model(Google GSON)
- isNetworkAvailable Method in Activity to check whether Network is available
- WeatherInfoActivity (FragmentActivity within a fragment) to show more information about current weather, also for further development in the activity to manage more fragments

## Demo
![alt tag](https://github.com/sheat2500/HackeratiAnimationChallenge/blob/master/demo/pic1.png?raw=true)

## Reference
- [GSON] (https://code.google.com/p/google-gson/)
- [OKhttp] (https://github.com/square/okhttp)
- [butterknife] (https://github.com/JakeWharton/butterknife)
