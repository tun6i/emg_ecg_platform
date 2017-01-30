package activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

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
    Canvas canvas;
    Paint paint = new Paint();
    float x = 0;
    float y = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(5.f);
        paint.setAntiAlias(true);
        canvas.drawCircle(x, y, 20, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        paint.setColor(Color.RED);
        invalidate();
        return true;
    }
}
