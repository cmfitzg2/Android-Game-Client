package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ConnectionError extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_error);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ConnectionError.this, MainActivity.class));
    }
}
