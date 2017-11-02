package product_scanner.product_scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Nguyen Khang on 11/1/2017.
 */

public class ScanFragment extends Fragment {
    private static final int SCAN_BARCODE = 123 ;
    private TextView txt_scannumber;
    private Button btn_clickscan;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scan, container, false);
        mContext= getActivity();
        findView(v);
        setOnClick();
        return v;
    }
    private void setOnClick() {
        btn_clickscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(),ScanBarcode.class);
                startActivityForResult(i,SCAN_BARCODE);
            }
        });
    }

    private void findView(View v) {
        txt_scannumber= v.findViewById(R.id.code_info);
        btn_clickscan=v.findViewById(R.id.btn_clickscan);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode == SCAN_BARCODE){
                if(resultCode== Activity.RESULT_OK){
                    txt_scannumber.setText(data.getStringExtra("barcode"));

                }
            }
        }

}
