package product_scanner.product_scanner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/29/2017.
 */

public class SearchFragment extends android.support.v4.app.Fragment {
    private ListView listBar;
    private EditText inputBar;
    private Button backBut;
    private Context mContext;
    private Button delBut;
    ArrayAdapter<String> bar_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        mContext= getActivity();
        findView(v);
        setUI_searchinglist();
        inputBar.setEnabled(true);
        setOnClick();
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){
                    ((ResideMenu)getActivity()).hidekeyboard(view);
                }
                return true;

            }
        });
        //
     /*   screenShootAdapter = new ScreenShootAdapter(getContext());
        viewPager.setAdapter(screenShootAdapter);*/
        return v;

    }
    public void setUI_searchinglist(){
        ArrayList<String> arBar = new ArrayList<>();
        arBar = getBarcodeList();
        bar_adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arBar);
        listBar.setAdapter(bar_adapter);
        inputBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    bar_adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        listBar.clearTextFilter();
        listBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String barcode_choose = (String)adapterView.getItemAtPosition(i);
                ((ResideMenu) getActivity()).barcodeid=barcode_choose;
                ((ResideMenu) getActivity()).changeFragmentNavigate(R.id.main_reside_menu, new ProductFragment());
            }
        });
    }
    //Get list from Database (but not successfull)
    private ArrayList<String> getBarcodeList() {



        ArrayList<String> generated_list = new ArrayList<>();



        //Loop to get list
        for (String key: MyFirebaseDatabase.listproduct.keySet())
        {
            generated_list.add(key);
        }

        return generated_list;
    }

    private void setOnClick() {
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputBar.setText("");
            }
        });
    }

    private void findView(View v) {
        inputBar = v.findViewById(R.id.input_barcode);
        // viewPager = v.findViewById(R.id.screenshoot_slider);
        listBar = v.findViewById(R.id.list_barcode);
        backBut = v.findViewById(R.id.back_btn);
        delBut = v.findViewById(R.id.del_btn);
    }
}
