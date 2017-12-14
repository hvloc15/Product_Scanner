package product_scanner.product_scanner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nguye on 11/18/2017.
 */

public class Product {

     String name;
     String url;
     HashMap<String, String> sources;
     HashMap<String,String> reviews;
        public Product(){
        }
    public Product( String name,String url,String place,String price) {

        this.name = name;
        this.url=url;
        sources=new HashMap<>();
        reviews=new HashMap<>();
        sources.put(place,price);

    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getSources() {
        return sources;
    }

    public HashMap<String, String> getReviews() {

        return reviews;
    }


    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("Name", name);
        result.put("Url",url);
        result.put("Review",reviews);
        result.put("Source",sources);

        return result;
    }


}
