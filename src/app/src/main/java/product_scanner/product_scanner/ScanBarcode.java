package product_scanner.product_scanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanBarcode extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 1001;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private MyCameraView cameraView;
    private DrawingView drawingView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        findView();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScanBarcode.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

        }

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS) // Format supported by our app
                .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024) // Here the size of camera
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build();
        cameraView.setCameraSource(cameraSource);
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ex) {
                    ex.printStackTrace(); // Return the errors and why it crashes
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
             barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {// Called function when detected
                final SparseArray<Barcode> barcodes=detections.getDetectedItems();
                if (barcodes.size()>0){
                    Intent returnIntent =new Intent();
                    returnIntent.putExtra("barcode",barcodes.valueAt(0).displayValue);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                    //testing adding product layout

                }
            }
        });

        cameraView.setDrawingView(drawingView);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void findView() {
        cameraView = findViewById(R.id.camera_view);
        drawingView =  findViewById(R.id.drawing_view);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),R.string.success,Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(),R.string.get_permission,Toast.LENGTH_SHORT).show();
                }
                  break;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onDestroy() { //when we close the app
        super.onDestroy();
        cameraSource.release();
        barcodeDetector.release();

    }

}
