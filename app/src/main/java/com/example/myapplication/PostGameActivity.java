package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostGameActivity extends AppCompatActivity {

    private List<Button> buttons = new ArrayList<>();
    private List<Button> visibleButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postgame);
        buttons.add((Button) findViewById(R.id.oneButton));
        buttons.add((Button) findViewById(R.id.twoButton));
        buttons.add((Button) findViewById(R.id.threeButton));
        buttons.add((Button) findViewById(R.id.fourButton));
        buttons.add((Button) findViewById(R.id.fiveButton));
        buttons.add((Button) findViewById(R.id.sixButton));
        buttons.add((Button) findViewById(R.id.sevenButton));
        for (int i = 0; i < GeneralConstants.playerMax - 1; i++) {
            if (i < GeneralConstants.playerLimit - 1) {
                visibleButtons.add(buttons.get(i));
            } else {
                buttons.get(i).setVisibility(View.GONE);
            }
        }
    }

    public void selectNumber(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle("Confirm selection");
        builder.setMessage("Are you sure you'd like to select this player?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Button winnerButton = (Button) view;
                int winner = Integer.parseInt(winnerButton.getText().toString());
                new AsyncAction(winner).execute();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class AsyncAction extends AsyncTask<String, Void, Void> {
        int winner;

        AsyncAction(int winner) {
            this.winner = winner;
        }

        protected Void doInBackground(String... strings) {
            if(android.os.Debug.isDebuggerConnected()) {
                android.os.Debug.waitForDebugger();
            }
            Log.d("PostGameActivity", "Attempting to send winner");
            try {
                MainActivity.objectOutputStream.writeInt(winner);
                MainActivity.objectOutputStream.flush();
                GeneralConstants.playerLimit = 0;
                startActivity(new Intent(PostGameActivity.this, LobbyActivity.class));
            } catch (IOException e) {
                startActivity(new Intent(PostGameActivity.this, ConnectionError.class));
            }
            return null;
        }
    }
}
