package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import beans.Drawing;
import beans.FingerPath;

public class PaintView extends View {
    private final int SMALL_BRUSH_SIZE = 30;
    private final int MED_BRUSH_SIZE = 45;
    private final int LARGE_BRUSH_SIZE = 75;
    private int height;
    private int width;
    public static final int DEFAULT_COLOR = Color.BLACK;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private View buttonDivider;
    private View colorScroll;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private boolean smallBrushOn = true;
    private boolean medBrushOn = false;
    private boolean largeBrushOn = false;
    private ImageView smallBrush, medBrush, largeBrush;
    private ImageView whiteBrush, blackBrush, redBrush, orangeBrush, yellowBrush, greenBrush, blueBrush, purpleBrush,
            brownBrush, grayBrush, pinkBrush;
    private ImageView eraser;
    private String selectedColor = "black";
    private ArrayList<float[]> pathPoints = new ArrayList<>();

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
    }

    public void init(DisplayMetrics metrics, View rootView) {
        height = metrics.heightPixels;
        width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        strokeWidth = SMALL_BRUSH_SIZE;
        //Elements
        buttonDivider = rootView.findViewById(R.id.buttonDivider);
        colorScroll = rootView.findViewById(R.id.colorScroll);
    }

    public void clear() {
        backgroundColor = DEFAULT_BG_COLOR;
        paths.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        if (colorScroll != null && buttonDivider != null) {
            canvas.clipRect(0, colorScroll.getBottom(), width, buttonDivider.getTop());
        }
        mCanvas.drawColor(backgroundColor);

        for (FingerPath fp : paths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mPaint.setMaskFilter(null);
            mCanvas.drawPath(fp.path, mPaint);

        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y) {
        mPath = new Path();
        FingerPath fp = new FingerPath(currentColor, strokeWidth, mPath);
        paths.add(fp);
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            float x2 = (x + mX) / 2;
            float y2 = (y + mY) / 2;
            pathPoints.add(new float[]{x2, y2});
            mPath.quadTo(mX, mY, x2, y2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (y < buttonDivider.getY()) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchStart(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchMove(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touchUp();
                    invalidate();
                    break;
            }
        }
        return true;
    }

    public Drawing finishDrawing(View view) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] pixels = bos.toByteArray();
        return new Drawing(0, colorScroll.getBottom(), width, buttonDivider.getTop(), pixels);
    }

    public void updateSmallBrushImage(View view) {
        if (smallBrushOn) {
            return;
        }
        smallBrushOn = true;
        strokeWidth = SMALL_BRUSH_SIZE;
        smallBrush.setImageResource(R.drawable.brush_small_on);
        if (medBrushOn) {
            medBrush.setImageResource(R.drawable.brush_med_off);
            medBrushOn = false;
        } else {
            largeBrush.setImageResource(R.drawable.brush_large_off);
            largeBrushOn = false;
        }
    }

    public void updateMediumBrushImage(View view) {
        if (medBrushOn) {
            return;
        }
        medBrushOn = true;
        strokeWidth = MED_BRUSH_SIZE;
        medBrush.setImageResource(R.drawable.brush_med_on);
        if (smallBrushOn) {
            smallBrush.setImageResource(R.drawable.brush_small_off);
            smallBrushOn = false;
        } else {
            largeBrush.setImageResource(R.drawable.brush_large_off);
            largeBrushOn = false;
        }
    }

    public void updateLargeBrushImage(View view) {
        if (largeBrushOn) {
            return;
        }
        largeBrushOn = true;
        strokeWidth = LARGE_BRUSH_SIZE;
        largeBrush.setImageResource(R.drawable.brush_large_on);
        if (smallBrushOn) {
            smallBrush.setImageResource(R.drawable.brush_small_off);
            smallBrushOn = false;
        } else {
            medBrush.setImageResource(R.drawable.brush_med_off);
            medBrushOn = false;
        }
    }

    public void undoPrevious(View view) {
        if (paths.size() > 0) {
            paths.remove(paths.size() - 1);
            mPath.reset();
            invalidate();
        }
    }

    private void updateSelectedColor(String color) {
        if (selectedColor.equals(color)) {
            return;
        }
        switch (color) {
            case "white":
                whiteBrush.setImageResource(R.drawable.brush_white_on);
                break;
            case "black":
                blackBrush.setImageResource(R.drawable.brush_black_on);
                break;
            case "red":
                redBrush.setImageResource(R.drawable.brush_red_on);
                break;
            case "orange":
                orangeBrush.setImageResource(R.drawable.brush_orange_on);
                break;
            case "yellow":
                yellowBrush.setImageResource(R.drawable.brush_yellow_on);
                break;
            case "green":
                greenBrush.setImageResource(R.drawable.brush_green_on);
                break;
            case "blue":
                blueBrush.setImageResource(R.drawable.brush_blue_on);
                break;
            case "purple":
                purpleBrush.setImageResource(R.drawable.brush_purple_on);
                break;
            case "pink":
                pinkBrush.setImageResource(R.drawable.brush_pink_on);
                break;
            case "brown":
                brownBrush.setImageResource(R.drawable.brush_brown_on);
                break;
            case "gray":
                grayBrush.setImageResource(R.drawable.brush_gray_on);
                break;
            case "eraser":
                eraser.setImageResource(R.drawable.eraser_on);
                break;
        }
        switch (selectedColor) {
            case "white":
                whiteBrush.setImageResource(R.drawable.brush_white_off);
                break;
            case "black":
                blackBrush.setImageResource(R.drawable.brush_black_off);
                break;
            case "red":
                redBrush.setImageResource(R.drawable.brush_red_off);
                break;
            case "orange":
                orangeBrush.setImageResource(R.drawable.brush_orange_off);
                break;
            case "yellow":
                yellowBrush.setImageResource(R.drawable.brush_yellow_off);
                break;
            case "green":
                greenBrush.setImageResource(R.drawable.brush_green_off);
                break;
            case "blue":
                blueBrush.setImageResource(R.drawable.brush_blue_off);
                break;
            case "purple":
                purpleBrush.setImageResource(R.drawable.brush_purple_off);
                break;
            case "pink":
                pinkBrush.setImageResource(R.drawable.brush_pink_off);
                break;
            case "brown":
                brownBrush.setImageResource(R.drawable.brush_brown_off);
                break;
            case "gray":
                grayBrush.setImageResource(R.drawable.brush_gray_off);
                break;
            case "eraser":
                eraser.setImageResource(R.drawable.eraser_off);
                break;
        }
        selectedColor = color;
    }

    public void setSmallBrush(ImageView smallBrush) {
        this.smallBrush = smallBrush;
    }

    public void setMedBrush(ImageView medBrush) {
        this.medBrush = medBrush;
    }

    public void setLargeBrush(ImageView largeBrush) {
        this.largeBrush = largeBrush;
    }

    public void setWhiteBrush(ImageView whiteBrush) {
        this.whiteBrush = whiteBrush;
    }

    public void setBlackBrush(ImageView blackBrush) {
        this.blackBrush = blackBrush;
    }

    public void setRedBrush(ImageView redBrush) {
        this.redBrush = redBrush;
    }

    public void setOrangeBrush(ImageView orangeBrush) {
        this.orangeBrush = orangeBrush;
    }

    public void setYellowBrush(ImageView yellowBrush) {
        this.yellowBrush = yellowBrush;
    }

    public void setGreenBrush(ImageView greenBrush) {
        this.greenBrush = greenBrush;
    }

    public void setBlueBrush(ImageView blueBrush) {
        this.blueBrush = blueBrush;
    }

    public void setPurpleBrush(ImageView purpleBrush) {
        this.purpleBrush = purpleBrush;
    }

    public void setPinkBrush(ImageView pinkBrush) {
        this.pinkBrush = pinkBrush;
    }

    public void setBrownBrush(ImageView brownBrush) {
        this.brownBrush = brownBrush;
    }

    public void setGrayBrush(ImageView grayBrush) {
        this.grayBrush = grayBrush;
    }

    public void setEraser(ImageView eraser) {
        this.eraser = eraser;
    }

    public void setBrushColor(View view, int r, int g, int b, String name) {
        this.currentColor = Color.rgb(r, g, b);
        updateSelectedColor(name);
    }

/*    public void setBrushEraser(View view) {
        this.currentColor = Color.rgb(255, 255, 255);
        updateSelectedColor("eraser");
    }*/
}