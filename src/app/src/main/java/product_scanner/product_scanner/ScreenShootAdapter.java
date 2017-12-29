package product_scanner.product_scanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by nguye on 11/22/2017.
 */

public class ScreenShootAdapter extends PagerAdapter{
/*    private int[] imageResources = {
            R.drawable.nike_sample1,
            R.drawable.nike_sample2,
    };*/

    private String url;
    private Glide glide;
    private Context context;
    private LayoutInflater layoutInflater;
    private ImageView imageView;
    public ScreenShootAdapter(Context context,String url){
        this.context = context;
        this.url=url;
    }


    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.screenshoot_product, container, false);
         imageView = (ImageView)itemView.findViewById(R.id.slider_image);

     //   imageView.setImageResource(imageResources[position]);
        glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(R.drawable.loading)
                        .optionalFitCenter())
                .into(imageView);
        container.addView(itemView);

        return itemView;
    }

    public Bitmap getImage(){
        imageView.buildDrawingCache();
        return imageView.getDrawingCache();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object ){
        container.removeView((LinearLayout)object);
    }

}
