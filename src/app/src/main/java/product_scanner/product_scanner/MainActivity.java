package product_scanner.product_scanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;



public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private static final int SCAN_BARCODE = 123 ;
    private TextView txt_scannumber;
    private Button btn_clickscan;
	private TextView test;

	private MyFirebaseStorage myFirebaseStorage;    //FirebaseStorage

    //Product detail
//    ViewPager viewPager;
//    ScreenShootAdapter screenShootAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // myFirebaseStorage.mStorageRef = FirebaseStorage.getInstance().getReference();
        findView();
        setOnClick();

       /* screenShootAdapter = new ScreenShootAdapter(this);
        viewPager.setAdapter(screenShootAdapter);*/

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);




 //       screenShootAdapter = new ScreenShootAdapter(this);
 //       viewPager.setAdapter(screenShootAdapter);

    }
    Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.eye);
    SharePhoto photo = new SharePhoto.Builder()
            .setBitmap(image)
            .build();
    SharePhotoContent content = new SharePhotoContent.Builder()
            .addPhoto(photo)
            .build();
    ShareButton shareButton = findViewById(R.id.fb_share_button);




    private void setOnClick() {
        btn_clickscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,ResideMenu.class);
                startActivityForResult(i,SCAN_BARCODE);
            }
        });
    }

    private void findView() {
        txt_scannumber= findViewById(R.id.code_info);
        btn_clickscan=findViewById(R.id.btn_clickscan);
//        viewPager = (ViewPager)findViewById(R.id.screenshoot_slider);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SCAN_BARCODE){
            if(resultCode== Activity.RESULT_OK){
                txt_scannumber.setText  (data.getStringExtra("barcode"));
                super.onActivityResult(requestCode, resultCode, data);
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


}
