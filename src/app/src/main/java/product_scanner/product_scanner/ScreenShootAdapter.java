package product_scanner.product_scanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by nguye on 11/22/2017.
 */

public class ScreenShootAdapter extends PagerAdapter{
    private int[] imageResources = {
            R.drawable.nike_sample1,
            R.drawable.nike_sample2,
    };


    private Glide glide;
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

     //   imageView.setImageResource(imageResources[position]);
        glide.with(context)
                .load("https://cloud.netlifyusercontent.com/assets/344dbf88-fdf9-42bb-adb4-46f01eedd629/68dd54ca-60cf-4ef7-898b-26d7cbe48ec7/10-dithering-opt.jpg")
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .optionalFitCenter())
                .into(imageView);
        container.addView(itemView);

        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object ){
        container.removeView((LinearLayout)object);
    }

}
