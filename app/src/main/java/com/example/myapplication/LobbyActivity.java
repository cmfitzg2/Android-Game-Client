package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import static com.example.myapplication.MainActivity.objectInputStream;

public class LobbyActivity extends Activity implements Runnable {

    public boolean running = true;
    public String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        new Thread(this).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void run() {
        state = "idle";
        new HandleMessaging().execute();
    }

    @Override
    public void onBackPressed() {
        //ignore back button
    }

    private class HandleMessaging extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... strings) {
            if(android.os.Debug.isDebuggerConnected()) {
                android.os.Debug.waitForDebugger();
            }
            while (running) {
                try {
                    Thread.sleep(0);
                    if (objectInputStream.available() > 0 ) {
                        if (GeneralConstants.playerLimit == 0) {
                            GeneralConstants.playerLimit = objectInputStream.readInt();
                            String message = objectInputStream.readUTF();
                            if (message.equals(GeneralConstants.paintGame)) {
                                Log.d("Lobby", "Player limit: " + GeneralConstants.playerLimit);
                            }
                            String judge = objectInputStream.readUTF();
                            boolean isJudge = Boolean.parseBoolean(judge);
                            if (isJudge) {
                                Log.d("Lobby", "Starting paint game as judge");
                                startActivity(new Intent(LobbyActivity.this, JudgeActivity.class));
                                running = false;
                            }
                        } else {
                            //receiving notice to start activity
                            String start = objectInputStream.readUTF();
                            if (start.equals(GeneralConstants.startPaintGame)) {
                                Log.d("Lobby", "Starting paint game");
                                startActivity(new Intent(LobbyActivity.this, PaintActivity.class));
                                running = false;
                            }
                        }
                    }
                } catch (Exception e) {
                    startActivity(new Intent(LobbyActivity.this, ConnectionError.class));
                    running = false;
                }
            }
            return null;
        }
    }

    private static final String TAG = "LobbyActivity";
    @Override public void onResume() {
        Log.d(TAG, "onResume:");
        super.onResume();
    }
    @Override public void onPause() {
        Log.d(TAG, "onPause:");
        super.onPause();
    }
    @Override public void onStop() {
        Log.d(TAG, "onStop:");
        super.onStop();
    }
    @Override public void onDestroy() {
        Log.d(TAG, "onDestroy:");
        super.onDestroy();
    }
}
