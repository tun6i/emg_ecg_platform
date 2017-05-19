package activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.os.Vibrator;

/**
 * Custom image view for HeatmapFragment
 * Overrides onDraw method to draw custom shapes and objects
 */
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


    /**
     * Select correct circle/electrode for relocation on the screen
     * Active circle is ready to be moved
     */
    private void activateChannel() {
        float tmpX = x;
        float tmpY = y;

        // Get positions from all circles
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

        /*
         * Check if finger position is within a circle
         * Activate that circle
         */
        if (((tmpX >= c1X - (ovalWidth + precision)) & (tmpX <= c1X + ovalWidth + precision)) & (tmpY >= c1Y - (ovalHeight + precision)) & (tmpY <= c1Y + ovalHeight + precision)) {
            v.vibrate(firstDuration);
            activeCircle = circle1;
            isChannelActive = true;
        } else if (((tmpX >= c2X - (ovalWidth + precision)) & (tmpX <= c2X + ovalWidth + precision)) & (tmpY >= c2Y - (ovalHeight + precision)) & (tmpY <= c2Y + ovalHeight + precision)) {
            v.vibrate(firstDuration);
            activeCircle = circle2;
            isChannelActive = true;
        } else if (((tmpX >= c3X - (ovalWidth + precision)) & (tmpX <= c3X + ovalWidth + precision)) & (tmpY >= c3Y - (ovalHeight + precision)) & (tmpY <= c3Y + ovalHeight + precision)) {
            v.vibrate(firstDuration);
            activeCircle = circle3;
            isChannelActive = true;
        } else if (((tmpX >= c4X - (ovalWidth + precision)) & (tmpX <= c4X + ovalWidth + precision)) & (tmpY >= c4Y - (ovalHeight + precision)) & (tmpY <= c4Y + ovalHeight + precision)) {
            v.vibrate(firstDuration);
            activeCircle = circle4;
            isChannelActive = true;
        } else if (((tmpX >= c5X - (ovalWidth + precision)) & (tmpX <= c5X + ovalWidth + precision)) & (tmpY >= c5Y - (ovalHeight + precision)) & (tmpY <= c5Y + ovalHeight + precision)) {
            v.vibrate(firstDuration);
            activeCircle = circle5;
            isChannelActive = true;
        } else if (((tmpX >= c6X - (ovalWidth + precision)) & (tmpX <= c6X + ovalWidth + precision)) & (tmpY >= c6Y - (ovalHeight + precision)) & (tmpY <= c6Y + ovalHeight + precision)) {
            v.vibrate(firstDuration);
            activeCircle = circle6;
            isChannelActive = true;
        }
    }

    // Finger position
    static float x = 0;
    static float y = 0;

    /*
     * Available channels.
     * Min. 1
     * Max. 6
     */
    static int channels = 1;

    // 6 electrodes
    static Circle circle1 = new Circle();
    static Circle circle2 = new Circle();
    static Circle circle3 = new Circle();
    static Circle circle4 = new Circle();
    static Circle circle5 = new Circle();
    static Circle circle6 = new Circle();

    // Currently selected channel to move
    static Circle activeCircle = circle1;

    // TextLabel protperties of the circle
    static Paint paint = new Paint();

    private boolean isChannelActive = false;

    // Precision (0 circle must be hit exactly in the center)
    private int precision = 20;

    // Vibration feedback
    private Vibrator v = (Vibrator) this.getContext().getSystemService(Context.VIBRATOR_SERVICE);

    // Vibration duration
    private int firstDuration = 25;
    private int secondDuration = 25;

    // Circle size
    int ovalWidth = 30;
    int ovalHeight = 80;

    /**
     * Overrides onDraw method to draw custom shapes and objects
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Set form and color of the labels
        paint.setTextSize(ovalWidth);
        paint.setUnderlineText(true);
        paint.setARGB(255, 255, 255, 255);

        // Draw 1 to 6 circles & labels depending on available channels
        switch (channels) {
            case 1:
                canvas.drawOval(circle1.getCenterX() - ovalWidth, circle1.getCenterY() - ovalHeight,
                        circle1.getCenterX() + ovalWidth, circle1.getCenterY() + ovalHeight,circle1.paint);
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + ovalWidth / 2 , paint);
                break;
            case 2:
                canvas.drawOval(circle1.getCenterX() - ovalWidth, circle1.getCenterY() - ovalHeight,
                        circle1.getCenterX() + ovalWidth, circle1.getCenterY() + ovalHeight,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - ovalWidth, circle2.getCenterY() - ovalHeight,
                        circle2.getCenterX() + ovalWidth, circle2.getCenterY() + ovalHeight,circle2.paint);
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH2", circle2.getCenterX() - ovalWidth, circle2.getCenterY() + ovalWidth / 2 , paint);
                break;
            case 3:
                canvas.drawOval(circle1.getCenterX() - ovalWidth, circle1.getCenterY() - ovalHeight,
                        circle1.getCenterX() + ovalWidth, circle1.getCenterY() + ovalHeight,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - ovalWidth, circle2.getCenterY() - ovalHeight,
                        circle2.getCenterX() + ovalWidth, circle2.getCenterY() + ovalHeight,circle2.paint);
                canvas.drawOval(circle3.getCenterX() - ovalWidth, circle3.getCenterY() - ovalHeight,
                        circle3.getCenterX() + ovalWidth, circle3.getCenterY() + ovalHeight,circle3.paint);
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH2", circle2.getCenterX() - ovalWidth, circle2.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH3", circle3.getCenterX() - ovalWidth, circle3.getCenterY() + ovalWidth / 2, paint);

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
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH2", circle2.getCenterX() - ovalWidth, circle2.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH3", circle3.getCenterX() - ovalWidth, circle3.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH4", circle4.getCenterX() - ovalWidth, circle4.getCenterY() + ovalWidth / 2, paint);

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
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH2", circle2.getCenterX() - ovalWidth, circle2.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH3", circle3.getCenterX() - ovalWidth, circle3.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH4", circle4.getCenterX() - ovalWidth, circle4.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH5", circle5.getCenterX() - ovalWidth, circle5.getCenterY() + ovalWidth / 2, paint);
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
                canvas.drawText("CH1", circle1.getCenterX() - ovalWidth, circle1.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH2", circle2.getCenterX() - ovalWidth, circle2.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH3", circle3.getCenterX() - ovalWidth, circle3.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH4", circle4.getCenterX() - ovalWidth, circle4.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH5", circle5.getCenterX() - ovalWidth, circle5.getCenterY() + ovalWidth / 2, paint);
                canvas.drawText("CH6", circle6.getCenterX() - ovalWidth, circle6.getCenterY() + ovalWidth / 2, paint);

                break;
        }

        // Redraw
        invalidate();

    }


    /**
     * Touch behaviour
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Performed action
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {

            // Select & activate circle
            case MotionEvent.ACTION_DOWN:
                if (!isChannelActive) {
                    // Wenn Channel deaktiviert ist, dann aktiviere ihn.
                    activateChannel();
                    Log.d("long", "Active Channel");
                }
                break;

            // Release & deactivate circle
            case MotionEvent.ACTION_UP:
                if(isChannelActive) {
                    v.vibrate(secondDuration);
                }
                isChannelActive = false;
                break;

            // Relocate active circle
            case MotionEvent.ACTION_MOVE:
                if (isChannelActive) {

                    // Check screen bounds
                    if (x - ovalWidth < 0) {
                        x = 30;
                    } else if (x + ovalWidth > getWidth()) {
                        x = getWidth() - 30;
                    }

                    // Check screen bounds
                    if (y - ovalHeight < 0) {
                        y = 75;
                    } else if (y + ovalHeight > getHeight()){
                        y = getHeight() - 75;
                    }

                    // Set new position
                    activeCircle.setCenterX(x);
                    activeCircle.setCenterY(y);
                }
                break;
            // Do nothing
            default:
                super.onTouchEvent(event);

        }
        // Redraw
        invalidate();
        return true;
    }

    /**
     * Set circle color
     * @param r red
     * @param g green
     * @param b black
     * @param circle circle
     */
    public static void setColor(int r, int g, int b, Circle circle) {
        circle.getPaint().setARGB(150, r, g, b);
    }

    /**
     * Class for circle objects
     */
    private static class Circle {

        // Center
        private float centerX = 100;
        private float centerY = 100;

        // Circle properties
        private Paint paint = new Paint();

        Circle() {
            paint.setStrokeWidth(5.f);
            paint.setAntiAlias(true);
            paint.setAlpha(150);
        }

        float getCenterX() {
            return centerX;
        }

        float getCenterY() {
            return centerY;
        }

        void setCenterX(float x) {
            this.centerX = x;
        }

        void setCenterY(float y) {
            this.centerY = y;
        }

        Paint getPaint() {
            return paint;
        }
    }

    /**
     * Initialize values
     */
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
        ovalWidth = 30;
        ovalHeight = 80;
    }
}
