package product_scanner.product_scanner;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class ProductFragment extends Fragment {
    private ScreenShootAdapter screenShootAdapter;
    private ViewPager viewPager;


    private EditText quantity;
    private Button add;


    private LinearLayout linearLayout;

    private Button shareB;

    private TextView name;
    private Button priceButton;
    private Product product;


    private CallbackManager callbackManager;
    private AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        findView(v);
        product=MyFirebaseDatabase.listproduct.get(((ResideMenu) getActivity()).barcodeid);
        screenShootAdapter = new ScreenShootAdapter(getContext(),product.getUrl());
        viewPager.setAdapter(screenShootAdapter);

        setUpUI();
        setOnClick();



        return v;
    }

    private void setOnClick() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q = quantity.getText().toString();
                q = q.replaceFirst("^0+(?!$)", "");
                if (q.equals(""))
                    Toast.makeText(getContext(), "Please enter the quantity", Toast.LENGTH_SHORT).show();
                else {

                    Boolean check = ResideMenu.addToCartDatabase.insertData(product, q, "Circle K");
                    if (check) {
                        Toast.makeText(getContext(), "Insert successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        check = ResideMenu.addToCartDatabase.updateData(product, q, "Circle K");
                        if (check)
                            Toast.makeText(getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        linearLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ((ResideMenu) getActivity()).hidekeyboard(view);
            }
        });

        priceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final AlertDialog.Builder place_menu_builder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.place_menu_option, null);
                ArrayList<Place> place_list = getPlaceList();
                PlaceAdapter placeAdapter = new PlaceAdapter(getContext(), R.layout.place_item, place_list);
                final ListView listView = mView.findViewById(R.id.place_list);
                listView.setAdapter(placeAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ViewGroup viewGroup = (ViewGroup)view;
                        TextView txt = (TextView)viewGroup.findViewById(R.id.place_price);
                        float scale = getContext().getResources().getDisplayMetrics().density;
                        priceButton.setMaxWidth((int)(120*scale+0.5f));
                        priceButton.setBackgroundColor(0x4000000);
                        priceButton.setText(txt.getText().toString());
                        alertDialog.dismiss();
                    }
                });
                place_menu_builder.setView(mView);
                alertDialog=place_menu_builder.create();
                alertDialog.show();

            }
        });



    }

    private ArrayList<Place> getPlaceList() {
        ArrayList<Place> generated_list = new ArrayList<Place>();
        Iterator it = product.getSources().entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            generated_list.add(new Place(pair.getKey().toString(), pair.getValue().toString()));
        }
        return generated_list;
    }

    private void setUpShare(final Bitmap img) {



    }

    private void setUpUI() {
        name.setText(product.getName());
    }

    private void findView(View v) {
        viewPager = v.findViewById(R.id.screenshoot_slider);
        name= v.findViewById(R.id.tx_name);
        priceButton=v.findViewById(R.id.tx_price);
        add=v.findViewById(R.id.button_add_to_cart);
        quantity=v.findViewById(R.id.editView_quantity);
        linearLayout=v.findViewById(R.id.main_view_product);
        shareB=v.findViewById(R.id.fb_share);


    }

}
