package com.beastpotato.potato.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        Log.i("ExampleActivity", "Hello");
        TextView tv = (TextView) findViewById(R.id.text);
        SimpleStringAccessor sup = new SimpleStringAccessor(); //generated based on @Assessor annotation in SimpleObject.java
        tv.setText(sup.getSimpleString());
    }
}
