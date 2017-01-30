package activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView{
    Paint paint = new Paint();

    public CustomImageView(Context context) {
        super(context);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5.f);
        paint.setAntiAlias(true);
    }

    public CustomImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomImageView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, 0, 500, 500 , paint);
    }
}
