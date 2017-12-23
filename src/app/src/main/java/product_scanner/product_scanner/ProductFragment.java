package product_scanner.product_scanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ProductFragment extends Fragment {
    private ScreenShootAdapter screenShootAdapter;
    private ViewPager viewPager;
    private TextView name;
    private Button priceButton;
    private Product product;
    private ShareButton shareButton;
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

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.nike_sample1);
               SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(screenShootAdapter.getImage())
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareButton.setShareContent(content);*/

            }
        });
        return v;
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
        shareButton=v.findViewById(R.id.fb_share_button);
     /*   callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);*/
    }

}
