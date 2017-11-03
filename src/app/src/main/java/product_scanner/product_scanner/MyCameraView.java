package product_scanner.product_scanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by Nguyen Khang on 11/2/2017.
 */

public class MyCameraView extends SurfaceView implements Camera.AutoFocusCallback {

    private CameraSource cameraSource;

    private DrawingView drawingView;
    private boolean drawingViewSet = false;

    public MyCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void setCameraSource(CameraSource cameraSource) {
        this.cameraSource = cameraSource;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();

            Rect touchRect = new Rect(
                    (int)(x - 190),
                    (int)(y - 128),
                    (int)(x + 190),
                    (int)(y + 128));

            final Rect targetFocusRect = new Rect(
                    touchRect.left * 2000/this.getWidth() - 1000,
                    touchRect.top * 2000/this.getHeight() - 1000,
                    touchRect.right * 2000/this.getWidth() - 1000,
                    touchRect.bottom * 2000/this.getHeight() - 1000);

            submitFocusAreaRect(targetFocusRect);
            if (drawingViewSet) {
                drawingView.setHaveTouch(true, touchRect);
                drawingView.invalidate();

                // Remove the square after some time
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        drawingView.setHaveTouch(false, new Rect(0, 0, 0, 0));
                        drawingView.invalidate();
                    }
                }, 1000);
            }

        }
        return false;
    }
    private void submitFocusAreaRect(final Rect focusArea) {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    Camera camera = (Camera) field.get(cameraSource);
                    if (camera != null) {
                        Camera.Parameters cameraParameters = camera.getParameters();

                        if(cameraParameters.getMaxNumFocusAreas() == 0) {
                            return;
                        }



                        ArrayList<Camera.Area> focusAreas = new ArrayList<>();
                        focusAreas.add(new Camera.Area(focusArea, 1000));

                        cameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                        cameraParameters.setFocusAreas(focusAreas);
                        camera.setParameters(cameraParameters);

                        camera.autoFocus(this);
                    }
                } catch (IllegalAccessException | RuntimeException e) {
                    e.getMessage();
                }

                break;
            }
        }

    }


    @Override
    public void onAutoFocus(boolean b, Camera camera) {

    }
    public void setDrawingView(DrawingView dView) {
        drawingView = dView;
        drawingViewSet = true;
    }
}