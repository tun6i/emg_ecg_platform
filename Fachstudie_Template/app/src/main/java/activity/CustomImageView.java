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

    float x = 0;
    float y = 0;
    static int channels = 1;

    Circle circle1 = new Circle();
    Circle circle2 = new Circle();
    Circle circle3 = new Circle();
    Circle circle4 = new Circle();
    Circle circle5 = new Circle();
    Circle circle6 = new Circle();
    Circle activeCircle = circle1;
    Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(30);
        paint.setUnderlineText(true);
        paint.setARGB(255, 255, 255, 255);

        switch (channels) {
            case 1:
                canvas.drawOval(circle1.getCenterX(), circle1.getCenterY(), circle1.getCenterX() + 65, circle1.getCenterY() + 150,circle1.paint);
                canvas.drawText("CH1", circle1.getCenterX() + 5, circle1.getCenterY() + 75, paint);
                activeCircle = circle1;
                break;
            case 2:
                canvas.drawOval(circle1.getCenterX(), circle1.getCenterY(), circle1.getCenterX() + 65, circle1.getCenterY() + 150,circle1.paint);
                canvas.drawOval(circle2.getCenterX(), circle2.getCenterY(), circle2.getCenterX() + 65, circle2.getCenterY() + 150,circle2.paint);
                canvas.drawText("CH1", circle1.getCenterX() + 5, circle1.getCenterY() + 75, paint);
                canvas.drawText("CH2", circle2.getCenterX() + 5, circle2.getCenterY() + 75, paint);


                activeCircle = circle2;

                break;
            case 3:
                canvas.drawOval(circle1.getCenterX(), circle1.getCenterY(), circle1.getCenterX() + 65, circle1.getCenterY() + 150,circle1.paint);
                canvas.drawOval(circle2.getCenterX(), circle2.getCenterY(), circle2.getCenterX() + 65, circle2.getCenterY() + 150,circle2.paint);
                canvas.drawOval(circle3.getCenterX(), circle3.getCenterY(), circle3.getCenterX() + 65, circle3.getCenterY() + 150,circle3.paint);
                canvas.drawText("CH1", circle1.getCenterX() + 5, circle1.getCenterY() + 75, paint);
                canvas.drawText("CH2", circle2.getCenterX() + 5, circle2.getCenterY() + 75, paint);
                canvas.drawText("CH3", circle3.getCenterX() + 5, circle3.getCenterY() + 75, paint);

                activeCircle = circle3;

                break;
            case 4:
                canvas.drawOval(circle1.getCenterX(), circle1.getCenterY(), circle1.getCenterX() + 65, circle1.getCenterY() + 150,circle1.paint);
                canvas.drawOval(circle2.getCenterX(), circle2.getCenterY(), circle2.getCenterX() + 65, circle2.getCenterY() + 150,circle2.paint);
                canvas.drawOval(circle3.getCenterX(), circle3.getCenterY(), circle3.getCenterX() + 65, circle3.getCenterY() + 150,circle3.paint);
                canvas.drawOval(circle4.getCenterX(), circle4.getCenterY(), circle4.getCenterX() + 65, circle4.getCenterY() + 150,circle4.paint);
                canvas.drawText("CH1", circle1.getCenterX() + 5, circle1.getCenterY() + 75, paint);
                canvas.drawText("CH2", circle2.getCenterX() + 5, circle2.getCenterY() + 75, paint);
                canvas.drawText("CH3", circle3.getCenterX() + 5, circle3.getCenterY() + 75, paint);
                canvas.drawText("CH4", circle4.getCenterX() + 5, circle4.getCenterY() + 75, paint);


                activeCircle = circle4;

                break;
            case 5:
                canvas.drawOval(circle1.getCenterX(), circle1.getCenterY(), circle1.getCenterX() + 65, circle1.getCenterY() + 150,circle1.paint);
                canvas.drawOval(circle2.getCenterX(), circle2.getCenterY(), circle2.getCenterX() + 65, circle2.getCenterY() + 150,circle2.paint);
                canvas.drawOval(circle3.getCenterX(), circle3.getCenterY(), circle3.getCenterX() + 65, circle3.getCenterY() + 150,circle3.paint);
                canvas.drawOval(circle4.getCenterX(), circle4.getCenterY(), circle4.getCenterX() + 65, circle4.getCenterY() + 150,circle4.paint);
                canvas.drawOval(circle5.getCenterX(), circle5.getCenterY(), circle5.getCenterX() + 65, circle5.getCenterY() + 150,circle5.paint);
                canvas.drawText("CH1", circle1.getCenterX() + 5, circle1.getCenterY() + 75, paint);
                canvas.drawText("CH2", circle2.getCenterX() + 5, circle2.getCenterY() + 75, paint);
                canvas.drawText("CH3", circle3.getCenterX() + 5, circle3.getCenterY() + 75, paint);
                canvas.drawText("CH4", circle4.getCenterX() + 5, circle4.getCenterY() + 75, paint);
                canvas.drawText("CH5", circle5.getCenterX() + 5, circle5.getCenterY() + 75, paint);

                activeCircle = circle5;

                break;
            case 6:
                canvas.drawOval(circle1.getCenterX(), circle1.getCenterY(), circle1.getCenterX() + 65, circle1.getCenterY() + 150,circle1.paint);
                canvas.drawOval(circle2.getCenterX(), circle2.getCenterY(), circle2.getCenterX() + 65, circle2.getCenterY() + 150,circle2.paint);
                canvas.drawOval(circle3.getCenterX(), circle3.getCenterY(), circle3.getCenterX() + 65, circle3.getCenterY() + 150,circle3.paint);
                canvas.drawOval(circle4.getCenterX(), circle4.getCenterY(), circle4.getCenterX() + 65, circle4.getCenterY() + 150,circle4.paint);
                canvas.drawOval(circle5.getCenterX(), circle5.getCenterY(), circle5.getCenterX() + 65, circle5.getCenterY() + 150,circle5.paint);
                canvas.drawOval(circle6.getCenterX(), circle6.getCenterY(), circle6.getCenterX() + 65, circle6.getCenterY() + 150,circle6.paint);
                canvas.drawText("CH1", circle1.getCenterX() + 5, circle1.getCenterY() + 75, paint);
                canvas.drawText("CH2", circle2.getCenterX() + 5, circle2.getCenterY() + 75, paint);
                canvas.drawText("CH3", circle3.getCenterX() + 5, circle3.getCenterY() + 75, paint);
                canvas.drawText("CH4", circle4.getCenterX() + 5, circle4.getCenterY() + 75, paint);
                canvas.drawText("CH5", circle5.getCenterX() + 5, circle5.getCenterY() + 75, paint);
                canvas.drawText("CH6", circle6.getCenterX() + 5, circle6.getCenterY() + 75, paint);

                activeCircle = circle6;

                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        activeCircle.setCenterX(x);
        activeCircle.setCenterY(y);
        invalidate();
        return true;
    }

    public void setColor(int r, int g, int b, Circle circle) {
        circle.getPaint().setARGB(150, r, g, b);
    }

    private class Circle {
        private float centerX = 0;
        private float centerY = 0;
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
