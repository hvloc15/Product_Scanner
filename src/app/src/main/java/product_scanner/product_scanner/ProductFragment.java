package product_scanner.product_scanner;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

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
    private PlaceAdapter placeAdapter;
   private ArrayList<Place> place_list;
    private CallbackManager callbackManager;
    private AlertDialog alertDialog;
    private String place="";

    private FloatingActionButton fab_plus,fab_fb,fab_scan,fab_edit;
    private Animation fabOpen,fabClose,fabClockwise,fabAntiClockwise;
    private boolean isOpen=false;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        findView(v);
        product=MyFirebaseDatabase.listproduct.get(((ResideMenu) getActivity()).barcodeid);
        screenShootAdapter = new ScreenShootAdapter(getContext(),product.getUrl());
        viewPager.setAdapter(screenShootAdapter);

        setUpUI();
        setOnClick();

        setUpFabAnim();



        return v;
    }

    private void setUpFabAnim() {
        fabOpen= AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        fabClose= AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        fabClockwise= AnimationUtils.loadAnimation(getContext(),R.anim.rotate_clockwise);
        fabAntiClockwise= AnimationUtils.loadAnimation(getContext(),R.anim.rotate_anticlockwise);
    }
    private void setOnClick() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q = quantity.getText().toString();
                q = q.replaceFirst("^0+(?!$)", "");
                if (q.equals("") ||q.equals("0"))
                    Toast.makeText(getContext(),getResources().getString(R.string.please_enter_the_quantity), Toast.LENGTH_SHORT).show();
                else {

                    if(place.equals(""))
                        Toast.makeText(getContext(),getString(R.string.please_choose_the_price),Toast.LENGTH_SHORT).show();
                    else {
                        Boolean check = ResideMenu.addToCartDatabase.insertData(product, q,place);
                        if (check)
                            Toast.makeText(getContext(), getString(R.string.insert_successfully), Toast.LENGTH_SHORT).show();
                        else
                                Toast.makeText(getContext(), getString(R.string.unsuccessful), Toast.LENGTH_SHORT).show();

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
                place_list = getPlaceList();
               placeAdapter = new PlaceAdapter(getContext(), R.layout.place_item, place_list);
                final ListView listView = mView.findViewById(R.id.place_list);
                listView.setAdapter(placeAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ViewGroup viewGroup = (ViewGroup)view;
                        TextView txtprice = (TextView)viewGroup.findViewById(R.id.place_price);
                        TextView txtplace = viewGroup.findViewById(R.id.place_name);
                        place=txtplace.getText().toString();
                        float scale = getContext().getResources().getDisplayMetrics().density;
                        priceButton.setMaxWidth((int)(120*scale+0.5f));
                        priceButton.setBackgroundColor(0x4000000);
                        priceButton.setText(txtprice.getText().toString());
                        alertDialog.dismiss();
                    }
                });
                place_menu_builder.setView(mView);
                alertDialog=place_menu_builder.create();
                alertDialog.show();

            }
        });

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen)
                {
                    closeAnimation();
                    isOpen=false;
                }
                else{
                    openAnimation();
                    isOpen=true;
                }
            }
        });

        fab_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((ResideMenu)getActivity()).internet.isOnline()) {
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(screenShootAdapter.getImage())
                            .build();
                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    ShareDialog.show(ProductFragment.this, content);
                }
                else
                    Toast.makeText(getContext(),getResources().getString(R.string.please_turn_on_the_internet),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void openAnimation() {
        fab_plus.startAnimation(fabClockwise);
        fab_edit.startAnimation(fabOpen);
        fab_fb.startAnimation(fabOpen);
        fab_scan.startAnimation(fabOpen);
        fab_edit.setClickable(true);
        fab_scan.setClickable(true);
        fab_fb.setClickable(true);

    }
    private void closeAnimation() {


        fab_scan.startAnimation(fabClose);
        fab_fb.startAnimation(fabClose);
        fab_edit.startAnimation(fabClose);
        fab_plus.startAnimation(fabAntiClockwise);
        fab_edit.setClickable(false);
        fab_scan.setClickable(false);
        fab_fb.setClickable(false);

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

        fab_fb=v.findViewById(R.id.fab_fb);
        fab_plus=v.findViewById(R.id.fab_main);
        fab_scan=v.findViewById(R.id.fab_scan);
        fab_edit=v.findViewById(R.id.fab_edit);

    }

}
