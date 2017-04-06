package activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.os.Vibrator;

import java.util.List;

public class CustomImageView extends ImageView{

    public CustomImageView(Context context) {
        super(context);

    }

    public CustomImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomImageView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    /*final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("LongTouch", "Longpress detected");
            if (!isChannelActive) {
                // Wenn Channel deaktiviert ist, dann aktiviere ihn.
                activateChannel(e);
                Log.d("long", "Active Channel");
            }
            return true;
        }
    });*/



    private void activateChannel(MotionEvent event) {
        // X und Y - Werte aus dem Event.
        float tmpX = x;
        float tmpY = y;

        float c1X = circle1.getCenterX();
        float c1Y = circle1.getCenterY();
        float c2X = circle2.getCenterX();
        float c2Y = circle2.getCenterY();
        float c3X = circle3.getCenterX();
        float c3Y = circle3.getCenterY();
        float c4X = circle4.getCenterX();
        float c4Y = circle4.getCenterY();
        float c5X = circle5.getCenterX();
        float c5Y = circle5.getCenterY();
        float c6X = circle6.getCenterX();
        float c6Y = circle6.getCenterY();

        if (((tmpX >= c1X - (ovalWidth + precision)) & (tmpX <= c1X + ovalWidth + precision)) & (tmpY >= c1Y - (ovalHeight + precision)) & (tmpY <= c1Y + ovalHeight + precision)) {
            Log.d("long", "Channel1 getroffen");
            v.vibrate(firstDuration);
            activeCircle = circle1;
            isChannelActive = true;
        } else if (((tmpX >= c2X - (ovalWidth + precision)) & (tmpX <= c2X + ovalWidth + precision)) & (tmpY >= c2Y - (ovalHeight + precision)) & (tmpY <= c2Y + ovalHeight + precision)) {
            Log.d("long", "Channel2 getroffen");
            v.vibrate(firstDuration);
            activeCircle = circle2;
            isChannelActive = true;
        } else if (((tmpX >= c3X - (ovalWidth + precision)) & (tmpX <= c3X + ovalWidth + precision)) & (tmpY >= c3Y - (ovalHeight + precision)) & (tmpY <= c3Y + ovalHeight + precision)) {
            Log.d("long", "Channel3 getroffen");
            v.vibrate(firstDuration);
            activeCircle = circle3;
            isChannelActive = true;
        } else if (((tmpX >= c4X - (ovalWidth + precision)) & (tmpX <= c4X + ovalWidth + precision)) & (tmpY >= c4Y - (ovalHeight + precision)) & (tmpY <= c4Y + ovalHeight + precision)) {
            Log.d("long", "Channel4 getroffen");
            v.vibrate(firstDuration);
            activeCircle = circle4;
            isChannelActive = true;
        } else if (((tmpX >= c5X - (ovalWidth + precision)) & (tmpX <= c5X + ovalWidth + precision)) & (tmpY >= c5Y - (ovalHeight + precision)) & (tmpY <= c5Y + ovalHeight + precision)) {
            Log.d("long", "Channel5 getroffen");
            v.vibrate(firstDuration);
            activeCircle = circle5;
            isChannelActive = true;
        } else if (((tmpX >= c6X - (ovalWidth + precision)) & (tmpX <= c6X + ovalWidth + precision)) & (tmpY >= c6Y - (ovalHeight + precision)) & (tmpY <= c6Y + ovalHeight + precision)) {
            Log.d("long", "Channel6 getroffen");
            v.vibrate(firstDuration);
            activeCircle = circle6;
            isChannelActive = true;
        }
    }

    static float x = 0;
    static float y = 0;
    static int channels = 1;

    static Circle circle1 = new Circle();
    static Circle circle2 = new Circle();
    static Circle circle3 = new Circle();
    static Circle circle4 = new Circle();
    static Circle circle5 = new Circle();
    static Circle circle6 = new Circle();
    static Circle activeCircle = circle1;
    static Paint paint = new Paint();

    private boolean isChannelActive = false;
    // Precision (0 Circle muss genau in der Mitte getroffen werden)
    private int precision = 20;
    private Vibrator v = (Vibrator) this.getContext().getSystemService(Context.VIBRATOR_SERVICE);
    private int firstDuration = 25;
    private int secondDuration = 25;
    int ovalWidth = 30;
    int ovalHeight = 80;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(30);
        paint.setUnderlineText(true);
        paint.setARGB(255, 255, 255, 255);

        switch (channels) {
            case 1:
                canvas.drawOval(circle1.getCenterX() - ovalWidth, circle1.getCenterY() - ovalHeight,
                        circle1.getCenterX() + ovalWidth, circle1.getCenterY() + ovalHeight,circle1.paint);
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + 15, paint);
                break;
            case 2:
                canvas.drawOval(circle1.getCenterX() - ovalWidth, circle1.getCenterY() - ovalHeight,
                        circle1.getCenterX() + ovalWidth, circle1.getCenterY() + ovalHeight,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - ovalWidth, circle2.getCenterY() - ovalHeight,
                        circle2.getCenterX() + ovalWidth, circle2.getCenterY() + ovalHeight,circle2.paint);
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + 15, paint);
                canvas.drawText("CH2", circle2.getCenterX() - ovalWidth, circle2.getCenterY() + 15, paint);
                break;
            case 3:
                canvas.drawOval(circle1.getCenterX() - ovalWidth, circle1.getCenterY() - ovalHeight,
                        circle1.getCenterX() + ovalWidth, circle1.getCenterY() + ovalHeight,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - ovalWidth, circle2.getCenterY() - ovalHeight,
                        circle2.getCenterX() + ovalWidth, circle2.getCenterY() + ovalHeight,circle2.paint);
                canvas.drawOval(circle3.getCenterX() - ovalWidth, circle3.getCenterY() - ovalHeight,
                        circle3.getCenterX() + ovalWidth, circle3.getCenterY() + ovalHeight,circle3.paint);
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + 15, paint);
                canvas.drawText("CH2", circle2.getCenterX() - ovalWidth, circle2.getCenterY() + 15, paint);
                canvas.drawText("CH3", circle3.getCenterX() - ovalWidth, circle3.getCenterY() + 15, paint);

                break;
            case 4:
                canvas.drawOval(circle1.getCenterX() - ovalWidth, circle1.getCenterY() - ovalHeight,
                        circle1.getCenterX() + ovalWidth, circle1.getCenterY() + ovalHeight,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - ovalWidth, circle2.getCenterY() - ovalHeight,
                        circle2.getCenterX() + ovalWidth, circle2.getCenterY() + ovalHeight,circle2.paint);
                canvas.drawOval(circle3.getCenterX() - ovalWidth, circle3.getCenterY() - ovalHeight,
                        circle3.getCenterX() + ovalWidth, circle3.getCenterY() + ovalHeight,circle3.paint);
                canvas.drawOval(circle4.getCenterX() - ovalWidth, circle4.getCenterY() - ovalHeight,
                        circle4.getCenterX() + ovalWidth, circle4.getCenterY() + ovalHeight,circle4.paint);
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + 15, paint);
                canvas.drawText("CH2", circle2.getCenterX() - ovalWidth, circle2.getCenterY() + 15, paint);
                canvas.drawText("CH3", circle3.getCenterX() - ovalWidth, circle3.getCenterY() + 15, paint);
                canvas.drawText("CH4", circle4.getCenterX() - ovalWidth, circle4.getCenterY() + 15, paint);

                break;
            case 5:
                canvas.drawOval(circle1.getCenterX() - ovalWidth, circle1.getCenterY() - ovalHeight,
                        circle1.getCenterX() + ovalWidth, circle1.getCenterY() + ovalHeight,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - ovalWidth, circle2.getCenterY() - ovalHeight,
                        circle2.getCenterX() + ovalWidth, circle2.getCenterY() + ovalHeight,circle2.paint);
                canvas.drawOval(circle3.getCenterX() - ovalWidth, circle3.getCenterY() - ovalHeight,
                        circle3.getCenterX() + ovalWidth, circle3.getCenterY() + ovalHeight,circle3.paint);
                canvas.drawOval(circle4.getCenterX() - ovalWidth, circle4.getCenterY() - ovalHeight,
                        circle4.getCenterX() + ovalWidth, circle4.getCenterY() + ovalHeight,circle4.paint);
                canvas.drawOval(circle5.getCenterX() - ovalWidth, circle5.getCenterY() - ovalHeight,
                        circle5.getCenterX() + ovalWidth, circle5.getCenterY() + ovalHeight,circle5.paint);
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + 15, paint);
                canvas.drawText("CH2", circle2.getCenterX() - ovalWidth, circle2.getCenterY() + 15, paint);
                canvas.drawText("CH3", circle3.getCenterX() - ovalWidth, circle3.getCenterY() + 15, paint);
                canvas.drawText("CH4", circle4.getCenterX() - ovalWidth, circle4.getCenterY() + 15, paint);
                canvas.drawText("CH5", circle5.getCenterX() - ovalWidth, circle5.getCenterY() + 15, paint);
                break;
            case 6:
                canvas.drawOval(circle1.getCenterX() - ovalWidth, circle1.getCenterY() - ovalHeight,
                        circle1.getCenterX() + ovalWidth, circle1.getCenterY() + ovalHeight,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - ovalWidth, circle2.getCenterY() - ovalHeight,
                        circle2.getCenterX() + ovalWidth, circle2.getCenterY() + ovalHeight,circle2.paint);
                canvas.drawOval(circle3.getCenterX() - ovalWidth, circle3.getCenterY() - ovalHeight,
                        circle3.getCenterX() + ovalWidth, circle3.getCenterY() + ovalHeight,circle3.paint);
                canvas.drawOval(circle4.getCenterX() - ovalWidth, circle4.getCenterY() - ovalHeight,
                        circle4.getCenterX() + ovalWidth, circle4.getCenterY() + ovalHeight,circle4.paint);
                canvas.drawOval(circle5.getCenterX() - ovalWidth, circle5.getCenterY() - ovalHeight,
                        circle5.getCenterX() + ovalWidth, circle5.getCenterY() + ovalHeight,circle5.paint);
                canvas.drawOval(circle6.getCenterX() - ovalWidth, circle6.getCenterY() - ovalHeight,
                        circle6.getCenterX() + ovalWidth, circle6.getCenterY() + ovalHeight,circle6.paint);
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + 15, paint);
                canvas.drawText("CH2", circle2.getCenterX() - ovalWidth, circle2.getCenterY() + 15, paint);
                canvas.drawText("CH3", circle3.getCenterX() - ovalWidth, circle3.getCenterY() + 15, paint);
                canvas.drawText("CH4", circle4.getCenterX() - ovalWidth, circle4.getCenterY() + 15, paint);
                canvas.drawText("CH5", circle5.getCenterX() - ovalWidth, circle5.getCenterY() + 15, paint);
                canvas.drawText("CH6", circle6.getCenterX() - ovalWidth, circle6.getCenterY() + 15, paint);

                break;
        }
        invalidate();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!isChannelActive) {
                    // Wenn Channel deaktiviert ist, dann aktiviere ihn.
                    activateChannel(event);
                    Log.d("long", "Active Channel");
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isChannelActive) {
                    v.vibrate(secondDuration);
                }
                isChannelActive = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isChannelActive) {
                    if (x - ovalWidth < 0) {
                        x = 30;
                    } else if (x + ovalWidth > getWidth()) {
                        x = getWidth() - 30;
                    }

                    if (y - ovalHeight < 0) {
                        y = 75;
                    } else if (y + ovalHeight > getHeight()){
                        y = getHeight() - 75;
                    }

                    activeCircle.setCenterX(x);
                    activeCircle.setCenterY(y);
                }
                break;
            default:
                super.onTouchEvent(event);

        }
        invalidate();
        return true;
    }

    public static void setColor(int r, int g, int b, Circle circle) {
        circle.getPaint().setARGB(150, r, g, b);
    }

    private static class Circle {
        private float centerX = 100;
        private float centerY = 100;
        private Paint paint = new Paint();

        public Circle() {
            paint.setStrokeWidth(5.f);
            paint.setAntiAlias(true);
            paint.setAlpha(150);
        }

        public float getCenterX() {
            return centerX;
        }

        public float getCenterY() {
            return centerY;
        }

        public void setCenterX(float x) {
            this.centerX = x;
        }

        public void setCenterY(float y) {
            this.centerY = y;
        }

        public Paint getPaint() {
            return paint;
        }
    }

    public void initialize() {
        circle1.setCenterX(100);
        circle1.setCenterY(100);
        circle2.setCenterX(100);
        circle2.setCenterY(100);
        circle3.setCenterX(100);
        circle3.setCenterY(100);
        circle4.setCenterX(100);
        circle4.setCenterY(100);
        circle5.setCenterX(100);
        circle5.setCenterY(100);
        circle6.setCenterX(100);
        circle6.setCenterY(100);
        channels = 1;

    }
}
