package activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

import de.fachstudie.fachstudie_template.R;

public class HeatmapFragment extends Fragment {

    private class CustomDrawableView extends View {
        private ShapeDrawable mDrawable;

        public CustomDrawableView(Context context) {
            super(context);

            int x = 100;
            int y = 100;
            int width = 300;
            int height = 50;

            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable.getPaint().setColor(0xff74AC23);
            mDrawable.setBounds(x, y, x + width, y + height);
        }

        protected void onDraw(Canvas canvas) {
            mDrawable.draw(canvas);
        }
    }
    Bitmap b = Bitmap.createBitmap(600, 800, Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(b);
    Paint paint = new Paint();
    ImageView view;



    public HeatmapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_heatmap, container, false);


        view = (ImageView) rootView.findViewById(R.id.imageView2);
        view.setClickable(true);
        view.setImageBitmap(b);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(6F);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        c.drawLine(0, 0, 500, 500, paint);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.drawLine(123,123,0,0, paint);
            }
        });





        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
