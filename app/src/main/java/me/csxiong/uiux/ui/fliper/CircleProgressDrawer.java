package me.csxiong.uiux.ui.fliper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import me.csxiong.library.utils.XDisplayUtil;

public class CircleProgressDrawer {

    private Bitmap bitmap;

    private Canvas canvas;

    Paint backgroundPaint;

    Paint progressPaint;

    private int strokeWidth = XDisplayUtil.dpToPxInt(5);

    RectF size = new RectF();

    public CircleProgressDrawer(int width, int height) {
        size.set(0, 0, width, height);
        size.inset(strokeWidth / 2f, strokeWidth / 2f);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setColor(0x4d6a6a6b);
        backgroundPaint.setStrokeWidth(XDisplayUtil.dpToPxInt(5));

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(0xff32a852);
        progressPaint.setStrokeWidth(XDisplayUtil.dpToPxInt(5));
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void draw(int progress) {
        int angle = (int) (progress / (float) 100 * 360);

        canvas.drawArc(size, -90, 360, false, backgroundPaint);
        canvas.drawArc(size, -90, -angle, false, progressPaint);
    }

    public Bitmap getCircleProgressBitmap(int progress) {
        draw(progress);
        return bitmap;
    }
}
