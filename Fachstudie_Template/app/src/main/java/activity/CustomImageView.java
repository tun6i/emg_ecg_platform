package activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(30);
        paint.setUnderlineText(true);
        paint.setARGB(255, 255, 255, 255);

        switch (channels) {
            case 1:
                canvas.drawOval(circle1.getCenterX() - 30, circle1.getCenterY() - 75, circle1.getCenterX() + 30, circle1.getCenterY() + 75,circle1.paint);
                canvas.drawText("CH1", circle1.getCenterX() - 30, circle1.getCenterY() + 15, paint);
                activeCircle = circle1;
                break;
            case 2:
                canvas.drawOval(circle1.getCenterX() - 30, circle1.getCenterY() - 75, circle1.getCenterX() + 30, circle1.getCenterY() + 75,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - 30, circle2.getCenterY() - 75, circle2.getCenterX() + 30, circle2.getCenterY() + 75,circle2.paint);
                canvas.drawText("CH1", circle1.getCenterX() - 30, circle1.getCenterY() + 15, paint);
                canvas.drawText("CH2", circle2.getCenterX() - 30, circle2.getCenterY() + 15, paint);
                activeCircle = circle2;

                break;
            case 3:
                canvas.drawOval(circle1.getCenterX() - 30, circle1.getCenterY() - 75, circle1.getCenterX() + 30, circle1.getCenterY() + 75,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - 30, circle2.getCenterY() - 75, circle2.getCenterX() + 30, circle2.getCenterY() + 75,circle2.paint);
                canvas.drawOval(circle3.getCenterX() - 30, circle3.getCenterY() - 75, circle3.getCenterX() + 30, circle3.getCenterY() + 75,circle3.paint);
                canvas.drawText("CH1", circle1.getCenterX() - 30, circle1.getCenterY() + 15, paint);
                canvas.drawText("CH2", circle2.getCenterX() - 30, circle2.getCenterY() + 15, paint);
                canvas.drawText("CH3", circle3.getCenterX() - 30, circle3.getCenterY() + 15, paint);
                activeCircle = circle3;

                break;
            case 4:
                canvas.drawOval(circle1.getCenterX() - 30, circle1.getCenterY() - 75, circle1.getCenterX() + 30, circle1.getCenterY() + 75,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - 30, circle2.getCenterY() - 75, circle2.getCenterX() + 30, circle2.getCenterY() + 75,circle2.paint);
                canvas.drawOval(circle3.getCenterX() - 30, circle3.getCenterY() - 75, circle3.getCenterX() + 30, circle3.getCenterY() + 75,circle3.paint);
                canvas.drawOval(circle4.getCenterX() - 30, circle4.getCenterY() - 75, circle4.getCenterX() + 30, circle4.getCenterY() + 75,circle4.paint);
                canvas.drawText("CH1", circle1.getCenterX() - 30, circle1.getCenterY() + 15, paint);
                canvas.drawText("CH2", circle2.getCenterX() - 30, circle2.getCenterY() + 15, paint);
                canvas.drawText("CH3", circle3.getCenterX() - 30, circle3.getCenterY() + 15, paint);
                canvas.drawText("CH4", circle4.getCenterX() - 30, circle4.getCenterY() + 15, paint);
                activeCircle = circle4;

                break;
            case 5:
                canvas.drawOval(circle1.getCenterX() - 30, circle1.getCenterY() - 75, circle1.getCenterX() + 30, circle1.getCenterY() + 75,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - 30, circle2.getCenterY() - 75, circle2.getCenterX() + 30, circle2.getCenterY() + 75,circle2.paint);
                canvas.drawOval(circle3.getCenterX() - 30, circle3.getCenterY() - 75, circle3.getCenterX() + 30, circle3.getCenterY() + 75,circle3.paint);
                canvas.drawOval(circle4.getCenterX() - 30, circle4.getCenterY() - 75, circle4.getCenterX() + 30, circle4.getCenterY() + 75,circle4.paint);
                canvas.drawOval(circle5.getCenterX() - 30, circle5.getCenterY() - 75, circle5.getCenterX() + 30, circle5.getCenterY() + 75,circle5.paint);
                canvas.drawText("CH1", circle1.getCenterX() - 30, circle1.getCenterY() + 15, paint);
                canvas.drawText("CH2", circle2.getCenterX() - 30, circle2.getCenterY() + 15, paint);
                canvas.drawText("CH3", circle3.getCenterX() - 30, circle3.getCenterY() + 15, paint);
                canvas.drawText("CH4", circle4.getCenterX() - 30, circle4.getCenterY() + 15, paint);
                canvas.drawText("CH5", circle5.getCenterX() - 30, circle5.getCenterY() + 15, paint);
                activeCircle = circle5;
                break;
            case 6:
                canvas.drawOval(circle1.getCenterX() - 30, circle1.getCenterY() - 75, circle1.getCenterX() + 30, circle1.getCenterY() + 75,circle1.paint);
                canvas.drawOval(circle2.getCenterX() - 30, circle2.getCenterY() - 75, circle2.getCenterX() + 30, circle2.getCenterY() + 75,circle2.paint);
                canvas.drawOval(circle3.getCenterX() - 30, circle3.getCenterY() - 75, circle3.getCenterX() + 30, circle3.getCenterY() + 75,circle3.paint);
                canvas.drawOval(circle4.getCenterX() - 30, circle4.getCenterY() - 75, circle4.getCenterX() + 30, circle4.getCenterY() + 75,circle4.paint);
                canvas.drawOval(circle5.getCenterX() - 30, circle5.getCenterY() - 75, circle5.getCenterX() + 30, circle5.getCenterY() + 75,circle5.paint);
                canvas.drawOval(circle6.getCenterX() - 30, circle6.getCenterY() - 75, circle6.getCenterX() + 30, circle6.getCenterY() + 75,circle6.paint);
                canvas.drawText("CH1", circle1.getCenterX() - 30, circle1.getCenterY() + 15, paint);
                canvas.drawText("CH2", circle2.getCenterX() - 30, circle2.getCenterY() + 15, paint);
                canvas.drawText("CH3", circle3.getCenterX() - 30, circle3.getCenterY() + 15, paint);
                canvas.drawText("CH4", circle4.getCenterX() - 30, circle4.getCenterY() + 15, paint);
                canvas.drawText("CH5", circle5.getCenterX() - 30, circle5.getCenterY() + 15, paint);
                canvas.drawText("CH6", circle6.getCenterX() - 30, circle6.getCenterY() + 15, paint);
                activeCircle = circle6;

                break;
        }
        invalidate();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        activeCircle.setCenterX(x);
        activeCircle.setCenterY(y);
        invalidate();
        return true;
    }

    public static void setColor(int r, int g, int b, Circle circle) {
        circle.getPaint().setARGB(150, r, g, b);
    }

    private static class Circle {
        private float centerX = 30;
        private float centerY = 75;
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
}
