package product_scanner.product_scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;


public class ProductFragment extends Fragment {
    private ScreenShootAdapter screenShootAdapter;
    private ViewPager viewPager;
    private TextView name,price;
    private Product product;
    private ShareButton shareButton;
    private Button shareB;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        findView(v);
        product=MyFirebaseDatabase.listproduct.get(((ResideMenu) getActivity()).barcodeid);
        screenShootAdapter = new ScreenShootAdapter(getContext(),product.getUrl());
        viewPager.setAdapter(screenShootAdapter);
        setUpUI();

        /*callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.nike_sample1);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(screenShootAdapter.getImage())
                .setCaption("Loc is Pacho")
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        shareButton.setShareContent(content);
        ShareDialog.show(ProductFragment.this, content); */


        shareB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                       Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.nike_sample1);
               SharePhoto photo = new SharePhoto.Builder()
                       .setBitmap(image)
                       .setCaption("Messi Bucu")
                        //.setBitmap(screenShootAdapter.getImage())
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                ShareDialog.show(ProductFragment.this, content);

            }
        });
        return v;
    }

    private void setUpShare(final Bitmap img) {



    }

    private void setUpUI() {
        name.setText(product.getName());
    }

    private void findView(View v) {
        viewPager = v.findViewById(R.id.screenshoot_slider);
        name= v.findViewById(R.id.tx_name);
        price=v.findViewById(R.id.tx_price);
        shareButton=v.findViewById(R.id.fb_share_button);
        shareB=v.findViewById(R.id.fb_share);
    }

}
