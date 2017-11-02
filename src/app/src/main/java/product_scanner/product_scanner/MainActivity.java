package product_scanner.product_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final int SCAN_BARCODE = 123 ;
    private TextView txt_scannumber;
    private Button btn_clickscan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setOnClick();
    }

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SCAN_BARCODE){
            if(resultCode== Activity.RESULT_OK){
                txt_scannumber.setText  (data.getStringExtra("barcode"));
            }
        }
    }
}
