package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout homeRel;
    private ProgressBar PageLoading;
    private TextView cityNameTV,tempTV,conditionTV,recyclerTV;
    private ImageView backIV,searchIV,tempIV;
    private RecyclerView weatherRV;
    private TextInputEditText cityED;
    private ArrayList<weatherRVModel> weatherRVModelArrayList;
    private weatherAdapter weatherAdapter;
    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        homeRel = findViewById(R.id.RelHome);
        PageLoading = findViewById(R.id.progressBar);
        cityNameTV = findViewById(R.id.cityName);
        tempTV = findViewById(R.id.tempTV);
        conditionTV = findViewById(R.id.condition);
        recyclerTV = findViewById(R.id.todayTV);
        backIV = findViewById(R.id.backIV);
        searchIV = findViewById(R.id.SearchIc);
        tempIV = findViewById(R.id.tempIV);
        weatherRV = findViewById(R.id.recyclerView);
        cityED = findViewById(R.id.cityEd);
        weatherRVModelArrayList = new ArrayList<>();
        weatherAdapter = new weatherAdapter(this,weatherRVModelArrayList);
        weatherRV.setAdapter(weatherAdapter);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(location!=null){
            cityName = getCityName(location.getLongitude(),location.getLatitude());
            getWeatherInfo(cityName);
        }else{
            cityName = "london";
            getWeatherInfo(cityName);
        }

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityED.getText().toString();
                if (city.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter city Name", Toast.LENGTH_SHORT).show();
                }else {
                    cityNameTV.setText(cityName);
                    getWeatherInfo(city);
                }
            }
        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "Please provide permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private String getCityName(double longitude, double latitude){
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude,longitude,10);
            for (Address adr:addresses){
                if(adr!=null){
                    String City = adr.getLocality();
                    if(City!=null && !City.equals("")){
                        cityName = City;
                    }else {
                        Log.d("TAG","CITY NOT FOUND");
                        Toast.makeText(this, "User City Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return cityName;
    }
    private void getWeatherInfo(String cityName){
        String url = "http://api.weatherapi.com/v1/forecast.json?key=270f664cdc164470b0170210241507&q="+cityName+"&days=1&aqi=yes&alerts=yes";
        cityNameTV.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                PageLoading.setVisibility(View.GONE);
                homeRel.setVisibility(View.VISIBLE);
                weatherRVModelArrayList.clear();
                try {
                    String temperature = response.getJSONObject("current").getString("temp_c");
                    tempTV.setText(temperature+"°C");
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionIcon)).into(tempIV);
                    conditionTV.setText(condition);
                    if (isDay == 1){
                        Picasso.get().load("https://i0.wp.com/static.vecteezy.com/system/resources/previews/002/596/183/large_2x/blue-sky-and-clouds-wallpaper-background-and-sunny-day-free-photo.jpg?ssl=1").into(backIV);
                    }else {
                        Picasso.get().load("https://images.rawpixel.com/image_800/cHJpdmF0ZS9sci9pbWFnZXMvd2Vic2l0ZS8yMDIyLTA1L2ZsNDU5ODY3NDcwNzEtaW1hZ2Uta3R4cmFnYWIuanBn.jpg").into(backIV);
                    }
                    JSONObject forecast = response.getJSONObject("forecast");
                    JSONObject forecast0 = forecast.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray = forecast0.getJSONArray("hour");

                    for (int i = 0;i<hourArray.length();i++){
                        JSONObject hourobj = hourArray.getJSONObject(i);
                        String time =  hourobj.getString("time");
                        String temper = hourobj.getString("temp_c");
                        String img = hourobj.getJSONObject("condition").getString("icon");
                        String wind = hourobj.getString("wind_kph");
                        weatherRVModelArrayList.add(new weatherRVModel(time,temper,img,wind));
                    }
                    weatherAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Please Enter Valid City Name", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }

}