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

import com.facebook.share.widget.ShareButton;

/**
 * Created by Nguyen Khang on 11/1/2017.
 */

public class ScanFragment extends Fragment {

    private ShareButton shareButton;

    private static final int SCAN_BARCODE = 123 ;
    private TextView txt_scannumber,txt_productname;

    private Button btn_clickscan;
    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scan, container, false);
        mContext= getActivity();
        findView(v);
        setOnClick();



        //

     /*   screenShootAdapter = new ScreenShootAdapter(getContext());
        viewPager.setAdapter(screenShootAdapter);*/

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


        txt_productname=v.findViewById(R.id.name);
       // viewPager = v.findViewById(R.id.screenshoot_slider);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SCAN_BARCODE) {
            if (resultCode == Activity.RESULT_OK) {
                String barcode;
            barcode = data.getStringExtra("barcode");
                ((ResideMenu) getActivity()).barcodeid=barcode;
           /*         txt_scannumber.setText(barcode);
                   txt_productname.setText(MyFirebaseDatabase.listproduct.get(barcode).getName());*/
                if (MyFirebaseDatabase.listproduct.get(barcode) == null) {
                    ((ResideMenu) getActivity()).changeFragment(R.id.main_reside_menu, new AddFragment());
                }
                else{
                  /*  txt_scannumber.setText(barcode);
                    txt_productname.setText(MyFirebaseDatabase.listproduct.get(barcode).getName());*/
                    ((ResideMenu) getActivity()).changeFragment(R.id.main_reside_menu, new ProductFragment());

                }
            }
        }
    }


}
