package product_scanner.product_scanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by nguye on 11/22/2017.
 */

public class ScreenShootAdapter extends PagerAdapter{
    private int[] imageResources = {
            R.drawable.nike_sample1,
            R.drawable.nike_sample2,
    };

    private Context context;
    private LayoutInflater layoutInflater;
    public ScreenShootAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        return imageResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.screenshoot_product, container, false);
        ImageView imageView = (ImageView)itemView.findViewById(R.id.slider_image);

        imageView.setImageResource(imageResources[position]);
        container.addView(itemView);

        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object ){
        container.removeView((LinearLayout)object);
    }

}
