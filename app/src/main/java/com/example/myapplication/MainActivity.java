package com.example.myapplication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    public static Socket socket;
    public static ObjectOutputStream objectOutputStream;
    public static ObjectInputStream objectInputStream;
    public static String name;
    private boolean debugMode = false;
    private Class debugClass = JudgeActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (debugMode) {
            startActivity(new Intent(MainActivity.this, debugClass));
        }
        final Button connectButton = findViewById(R.id.connectButton);
        final EditText playerName = findViewById(R.id.playerName);
        final EditText serverIP = findViewById(R.id.serverIP);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = playerName.getText().toString();
                String ip = serverIP.getText().toString();
                if (!name.isEmpty() && !ip.isEmpty()) {
                    new AsyncAction().execute(serverIP.getText().toString(), name);
                }
            }
        });
    }

    private class AsyncAction extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... strings) {
            if(android.os.Debug.isDebuggerConnected()) {
                android.os.Debug.waitForDebugger();
            }
            try {
                socket = new Socket(strings[GeneralConstants.serverIPIndex], GeneralConstants.serverPortNumber);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.flush();
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                objectOutputStream.writeUTF(name);
                objectOutputStream.flush();
                startActivity(new Intent(MainActivity.this, LobbyActivity.class));
            } catch (Exception e) {
                startActivity(new Intent(MainActivity.this, ConnectionError.class));
            }
            finish();
            return null;
        }
    }
}