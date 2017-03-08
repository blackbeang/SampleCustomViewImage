package org.androidtown.graphics.custom.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by BlackBean on 2017-03-08.
 */

public class CustomViewImage extends View {

    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private Paint mPaint;

    public CustomViewImage(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public CustomViewImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    public CustomViewImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        createCacheBitmap(w, h);
        testDrawing();
    }

    private void createCacheBitmap(int w, int h) {
        cacheBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);
    }

    private void testDrawing() {
        //cacheCanvas.drawColor(Color.YELLOW);

        //mPaint.setColor(Color.RED);
        //cacheCanvas.drawRect(100, 100, 200, 200, mPaint);

        Bitmap srcImg = BitmapFactory.decodeResource(getResources(), R.drawable.sample);
        cacheCanvas.drawBitmap(srcImg, 30, 30, mPaint);

        Matrix horInverseMatrix = new Matrix();
        horInverseMatrix.setScale(-1, 1);
        Bitmap horInverseImg = Bitmap.createBitmap(srcImg, 0, 0, srcImg.getWidth(), srcImg.getHeight(), horInverseMatrix, false);
        cacheCanvas.drawBitmap(horInverseImg, 30, srcImg.getHeight()+60, mPaint);

        Matrix verInverseMatrix = new Matrix();
        verInverseMatrix.setScale(1, -1);
        Bitmap verInverseImg = Bitmap.createBitmap(srcImg, 0, 0, srcImg.getWidth(), srcImg.getHeight(), verInverseMatrix, false);
        cacheCanvas.drawBitmap(verInverseImg, 30, srcImg.getHeight()*2+90, mPaint);

        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
        Bitmap scaledImg = Bitmap.createScaledBitmap(srcImg, srcImg.getWidth()*2, srcImg.getHeight()*2, false);
        cacheCanvas.drawBitmap(scaledImg, srcImg.getWidth()+30, 30, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(cacheBitmap != null)
            canvas.drawBitmap(cacheBitmap, 0, 0, null);
    }
}
