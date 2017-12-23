package product_scanner.product_scanner;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;



/**
 * Created by ASUS on 12/12/2017.
 */

public class ItemFragment extends Fragment {
    private ListView listView;
    private ItemAdapter itemAdapter;
    private ArrayList<ItemProduct> itemlist;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list, container, false);
        itemlist = ResideMenu.addToCartDatabase.getProductList();

        findView(v);




        return v;
    }

    private String getTotal(ArrayList<Product> list) {
        Integer totalNum = 0;
        for (int i = 0; i < list.size(); i++){
      /*      totalNum += Integer.parseInt(list.get(i).getPrice());*/
        }
        return totalNum.toString();
    }



    private void findView(View v) {
        itemAdapter = new ItemAdapter(v.getContext(),R.layout.product_item, itemlist);
        listView = (ListView)v.findViewById(R.id.list_item);
        listView.setAdapter(itemAdapter);
    }

}
