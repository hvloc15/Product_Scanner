package product_scanner.product_scanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Nguyen Khang on 11/2/2017.
 */
    public class DrawingView extends View {
        private boolean haveTouch = false;
        private Rect touchArea;
        private Paint paint;

        public DrawingView(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint = new Paint();
            paint.setColor(getResources().getColor(R.color.white));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            haveTouch = false;
        }

        public void setHaveTouch(boolean val, Rect rect) {
            haveTouch = val;
            touchArea = rect;
        }

        @Override
        public void onDraw(Canvas canvas) {
            if(haveTouch){
                canvas.drawRect(
                        touchArea.left, touchArea.top, touchArea.right, touchArea.bottom,
                        paint);
            }
        }
    }

