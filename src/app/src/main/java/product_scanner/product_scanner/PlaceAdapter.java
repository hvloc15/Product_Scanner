package product_scanner.product_scanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/21/2017.
 */

public class PlaceAdapter extends ArrayAdapter<Place> {
    private final Context context;
    private int resouce;
    private ArrayList<Place> list;

    public PlaceAdapter(Context context,int resource, ArrayList<Place> placeList) {
        super(context, resource, placeList);
        this.context = context;
        this.resouce = resource;
        this.list = placeList;

    }

    public class ViewHolder{
        TextView place;
        TextView price;
    }
    public ViewHolder viewHolder;
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(this.resouce, null);
            viewHolder.place = (TextView) convertView.findViewById(R.id.place_name);
            viewHolder.place.setText(getItem(position).getPlace());
            viewHolder.price = (TextView) convertView.findViewById(R.id.place_price);
            viewHolder.price.setText(getItem(position).getPrice());
        }
        return convertView;
    }
}
