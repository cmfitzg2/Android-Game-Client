package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JudgeActivity extends AppCompatActivity {

    private EditText drawingSubject;
    private Button sendButton, clearButton, randomizeButton;
    private TextView waiting;
    public boolean running = false;
    private List<String> randomSubjects;
    private Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge);
        sendButton = findViewById(R.id.sendButton);
        clearButton = findViewById(R.id.clearButton);
        randomizeButton = findViewById(R.id.randomizeButton);
        drawingSubject = findViewById(R.id.drawingSubject);
        waiting = findViewById(R.id.waitingPlayers);
        randomSubjects = buildSubjectList();
    }

    public void confirmSend(View view) {
        if (!drawingSubject.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
            builder.setTitle("Confirm subject");
            builder.setMessage("Are you sure you'd like the subject to be \"" + drawingSubject.getText().toString() + "\"?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    drawingSubject.setVisibility(View.GONE);
                    sendButton.setVisibility(View.GONE);
                    clearButton.setVisibility(View.GONE);
                    randomizeButton.setVisibility(View.GONE);
                    waiting.setVisibility(View.VISIBLE);
                    new AsyncAction().execute();

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
    }

    private class AsyncAction extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... strings) {
            if(android.os.Debug.isDebuggerConnected()) {
                android.os.Debug.waitForDebugger();
            }
            try {
                MainActivity.objectOutputStream.writeUTF(drawingSubject.getText().toString());
                MainActivity.objectOutputStream.flush();
            } catch (IOException e) {
                Log.e("Judge Activity", "error writing the drawing subject", e);
                startActivity(new Intent(JudgeActivity.this, ConnectionError.class));
            }
            try {
                running = true;
                while (running) {
                    Thread.sleep(0);
                    if (MainActivity.objectInputStream.available() > 0) {
                        String message = MainActivity.objectInputStream.readUTF();
                        if (message.equals(GeneralConstants.drawingFinished)) {
                            startActivity(new Intent(JudgeActivity.this, PostGameActivity.class));
                            running = false;
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("Judge Activity", "error during alert loop", e);
                startActivity(new Intent(JudgeActivity.this, ConnectionError.class));
            }
            return null;
        }
    }

    public void randomizeSubject(View view) {
        if (randomSubjects.size() == 0) {
            randomSubjects = buildSubjectList();
        }
        int index = 0;
        if (randomSubjects.size() > 1) {
            index = random.nextInt(randomSubjects.size() - 1);
        }
        drawingSubject.setText(randomSubjects.get(index));
        randomSubjects.remove(index);
    }

    public void clearText(View view) {
        drawingSubject.setText("");
    }

    private List<String> buildSubjectList() {
        List<String> list = new ArrayList<>();
        list.add("Pikachu");
        list.add("Snorlax");
        list.add("Donkey Kong");
        list.add("Yoshi");
        list.add("Mario");
        list.add("Snake");
        list.add("Monkey");
        list.add("Dog");
        list.add("Baby");
        list.add("Squirtle");
        list.add("Cat in the hat");
        list.add("Robot");
        list.add("Monster");
        list.add("Winnie the Pooh");
        list.add("Dragon");
        list.add("Cow");
        list.add("Penguin");
        list.add("Snail");
        list.add("Surfing");
        list.add("Ninja");
        list.add("Lighthouse");
        list.add("Butterfly");
        list.add("Apple");
        list.add("Worm");
        list.add("Pillow");
        list.add("Mug");
        list.add("Ring");
        list.add("Sonic");
        list.add("Light bulb");
        list.add("Fish");
        list.add("Tooth");
        list.add("Night");
        list.add("Treasure");
        list.add("Piano");
        list.add("Phone");
        list.add("Death");
        list.add("King");
        list.add("Airplane");
        list.add("SpongeBob");
        list.add("Basket");
        list.add("Village");
        list.add("The burden of responsibility");
        list.add("The folly of man");
        list.add("Waffle");
        list.add("Water");
        list.add("Molecule");
        list.add("Iron Maiden Album");
        list.add("Spider");
        list.add("Flower");
        list.add("Panda");
        list.add("Optical Illusion");
        list.add("Whale");
        list.add("Bee");
        list.add("Knight");
        list.add("Waterfall");
        list.add("Grandma");
        list.add("Stocks");
        list.add("Meme");
        list.add("Bread");
        list.add("Math");
        list.add("Atom");
        list.add("Electron");
        list.add("Rope");
        list.add("Gun");
        list.add("Alien");
        list.add("Pizza");
        list.add("Tea");
        list.add("Coffee");
        list.add("Ear");
        list.add("Dragon Ball Z");
        list.add("Taxation");
        list.add("Criminal");
        list.add("Jail");
        list.add("Hippie");
        list.add("Anime");
        return list;
    }
}
