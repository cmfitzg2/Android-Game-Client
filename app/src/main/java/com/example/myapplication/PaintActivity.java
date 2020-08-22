package com.example.myapplication;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import beans.Drawing;

public class PaintActivity extends AppCompatActivity {

    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        paintView = findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        View rootView = paintView.getRootView();
        paintView.init(metrics, rootView);
        paintView.setSmallBrush((ImageView) rootView.findViewById(R.id.smallBrush));
        paintView.setMedBrush((ImageView) rootView.findViewById(R.id.medBrush));
        paintView.setLargeBrush((ImageView) rootView.findViewById(R.id.largeBrush));
        paintView.setWhiteBrush((ImageView) rootView.findViewById(R.id.whiteBrush));
        paintView.setBlackBrush((ImageView) rootView.findViewById(R.id.blackBrush));
        paintView.setRedBrush((ImageView) rootView.findViewById(R.id.redBrush));
        paintView.setOrangeBrush((ImageView) rootView.findViewById(R.id.orangeBrush));
        paintView.setYellowBrush((ImageView) rootView.findViewById(R.id.yellowBrush));
        paintView.setGreenBrush((ImageView) rootView.findViewById(R.id.greenBrush));
        paintView.setBlueBrush((ImageView) rootView.findViewById(R.id.blueBrush));
        paintView.setPurpleBrush((ImageView) rootView.findViewById(R.id.purpleBrush));
        paintView.setPinkBrush((ImageView) rootView.findViewById(R.id.pinkBrush));
        paintView.setBrownBrush((ImageView) rootView.findViewById(R.id.brownBrush));
        paintView.setGrayBrush((ImageView) rootView.findViewById(R.id.grayBrush));
        //paintView.setEraser((ImageView) rootView.findViewById(R.id.eraser));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        //ignore back button
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void finishDrawing(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle("Confirm done");
        builder.setMessage("Are you sure you'd like to finish?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Drawing drawing = paintView.finishDrawing(view);
                new AsyncAction(drawing).execute();
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
        private Drawing drawing;

        AsyncAction(Drawing drawing) {
            this.drawing = drawing;
        }

        protected Void doInBackground(String... strings) {
            if(android.os.Debug.isDebuggerConnected()) {
                android.os.Debug.waitForDebugger();
            }
            try {
                Thread.sleep(0);
                MainActivity.objectOutputStream.writeInt(drawing.getxStart());
                MainActivity.objectOutputStream.writeInt(drawing.getyStart());
                MainActivity.objectOutputStream.writeInt(drawing.getxFinish());
                MainActivity.objectOutputStream.writeInt(drawing.getyFinish());
                MainActivity.objectOutputStream.writeInt(drawing.getPixels().length);
                MainActivity.objectOutputStream.write(drawing.getPixels());
                MainActivity.objectOutputStream.flush();
                GeneralConstants.playerLimit = 0;
                startActivity(new Intent(PaintActivity.this, LobbyActivity.class));
            } catch (Exception e) {
                startActivity(new Intent(PaintActivity.this, ConnectionError.class));
            }
            return null;
        }
    }

    public void clearCanvas(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle("Confirm delete");
        builder.setMessage("Do you want to reset the canvas?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                paintView.clear();
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

    public void updateSmallBrushImage(View view) {
        paintView.updateSmallBrushImage(view);
    }

    public void updateMediumBrushImage(View view) {
        paintView.updateMediumBrushImage(view);
    }

    public void updateLargeBrushImage(View view) {
        paintView.updateLargeBrushImage(view);
    }

    public void setBrushColorWhite(View view) {
        paintView.setBrushColor(view, 255, 255, 255, "white");
    }

    public void setBrushColorBlack(View view) {
        paintView.setBrushColor(view, 0, 0, 0, "black");
    }

    public void setBrushColorRed(View view) {
        paintView.setBrushColor(view, 255, 0, 0, "red");
    }

    public void setBrushColorOrange(View view) {
        paintView.setBrushColor(view, 255, 128, 0, "orange");
    }

    public void setBrushColorYellow(View view) {
        paintView.setBrushColor(view, 255, 255, 0, "yellow");
    }

    public void setBrushColorGreen(View view) {
        paintView.setBrushColor(view, 0, 255, 0, "green");
    }

    public void setBrushColorBlue(View view) {
        paintView.setBrushColor(view, 0, 0, 255, "blue");
    }

    public void setBrushColorPurple(View view) {
        paintView.setBrushColor(view, 128, 0, 255, "purple");
    }

    public void setBrushColorBrown(View view) {
        paintView.setBrushColor(view, 99, 48, 0, "brown");
    }

    public void setBrushColorGray(View view) {
        paintView.setBrushColor(view, 82, 82, 82, "gray");
    }

    public void setBrushColorPink(View view) {
        paintView.setBrushColor(view, 233, 50, 187, "pink");
    }

/*    public void setBrushEraser(View view) {
        paintView.setBrushEraser(view);
    }*/

    public void undoPrevious(View view) {
        paintView.undoPrevious(view);
    }
}
