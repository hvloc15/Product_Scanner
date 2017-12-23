package product_scanner.product_scanner;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * Created by ASUS on 12/12/2017.
 */

public class ItemFragment extends Fragment {
    private ListView listView;
    private ItemAdapter itemAdapter;
    private ArrayList<ItemProduct> itemlist;
    private TextView total;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list, container, false);
        itemlist = ResideMenu.addToCartDatabase.getProductList();
        findView(v);

        total.setText(getTotal(itemlist));

        return v;
    }

    private String getTotal(ArrayList<ItemProduct> list) {
        Integer totalNum = 0;
        for (int i = 0; i < list.size(); i++){
            Integer num=Integer.parseInt(list.get(i).getQuantity())*Integer.parseInt(list.get(i).getPrice());
            totalNum += num;
        }
        return totalNum.toString();
    }



    private void findView(View v) {
        itemAdapter = new ItemAdapter(v.getContext(),R.layout.product_item, itemlist);
        listView = (ListView)v.findViewById(R.id.list_item);
        listView.setAdapter(itemAdapter);
        total=v.findViewById(R.id.txt_total);
    }

}
