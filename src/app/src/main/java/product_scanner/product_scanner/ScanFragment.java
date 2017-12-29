package product_scanner.product_scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.widget.ShareButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Nguyen Khang on 11/1/2017.
 */

public class ScanFragment extends Fragment {

    private static final int SCAN_BARCODE = 123 ;
    private TextView inputBar;
    private Button btn_clickscan;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scan, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
                /*((ResideMenu) getActivity()).barcodeid = "8934588063053";
                ((ResideMenu) getActivity()).changeFragment(R.id.main_reside_menu, new ProductFragment());*/
            }
        });

        inputBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ResideMenu)getActivity()).changeFragmentNavigate(R.id.main_reside_menu,new SearchFragment());
            }
        });
    }

    private void findView(View v) {
        inputBar = v.findViewById(R.id.search_barcode);
        btn_clickscan=v.findViewById(R.id.btn_clickscan);
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
                    ((ResideMenu) getActivity()).changeFragmentNavigate(R.id.main_reside_menu, new AddFragment());
                }
                else{
                  /*  txt_scannumber.setText(barcode);
                    txt_productname.setText(MyFirebaseDatabase.listproduct.get(barcode).getName());*/
                    ((ResideMenu) getActivity()).changeFragmentNavigate(R.id.main_reside_menu, new ProductFragment());

                }
            }
        }
    }
}
