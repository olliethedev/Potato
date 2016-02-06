package com.savage.potato.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class ExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        Log.i("ExampleActivity", "Hello");
        SimpleStringAccessor sup = new SimpleStringAccessor(); //generated based on @Assessor annotation in SimpleObject.java
        Toast.makeText(this, sup.getSimpleString(), Toast.LENGTH_LONG).show();
    }
}
