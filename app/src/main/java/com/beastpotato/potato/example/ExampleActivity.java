package com.beastpotato.potato.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.beastpotato.potato.api.net.ApiRequest;
import com.beastpotato.potato.example.response.GetVideosInfoApiResponse;

public class ExampleActivity extends AppCompatActivity {
    private static String TAG = "ExampleActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        Log.i("ExampleActivity", "Hello");
        final TextView tv = (TextView) findViewById(R.id.text);
        //Network test
        GetVideosInfoApiRequest request = new GetVideosInfoApiRequest("https://community-vineapp.p.mashape.com", this);//Generated based on @Endpoint annotation in GetVideosInfo.java
        request.setApiKey("Sh7KOqP6lQmshVcLl2xFAG4BOfr9p1TfANBjsnduXWDjnqjNZY");
        request.setContentType("application/json");
        request.setCategory("popular");

        Log.i(TAG, "sending request...");
        request.send(new ApiRequest.RequestCompletion<GetVideosInfoApiResponse>() {
            @Override
            public void onResponse(GetVideosInfoApiResponse data) {
                tv.setText("Response records count:" + data.getData().getCount());
            }

            @Override
            public void onError(VolleyError error) {
                tv.setText(error.toString());
            }
        });
    }
}
