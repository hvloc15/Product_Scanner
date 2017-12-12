package product_scanner.product_scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ProductFragment extends Fragment {
    private ScreenShootAdapter screenShootAdapter;
    private ViewPager viewPager;
    private TextView name,price;
    private Product product;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        findView(v);
        product=MyFirebaseDatabase.listproduct.get(((ResideMenu) getActivity()).barcodeid);
        screenShootAdapter = new ScreenShootAdapter(getContext(),product.getUrl());
        viewPager.setAdapter(screenShootAdapter);
        setUpUI();
        return v;
    }

    private void setUpUI() {
        name.setText(product.getName());
    }

    private void findView(View v) {
        viewPager = v.findViewById(R.id.screenshoot_slider);
        name= v.findViewById(R.id.tx_name);
        price=v.findViewById(R.id.tx_price);
    }

}
