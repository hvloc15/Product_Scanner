package product_scanner.product_scanner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nguye on 11/18/2017.
 */

public class Product {
     String name;
     HashMap<String, String> sources;
     HashMap<String,String> reviews;
        public Product(){
            sources=new HashMap<>();
            reviews=new HashMap<>();
        }
    public Product( String name,String place,String price) {

        this.name = name;
        sources=new HashMap<>();
        reviews=new HashMap<>();
        sources.put(place,price);

    }

    public String getName() {
        return name;
    }



    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("Name", name);
        result.put("Review",reviews);
        result.put("Source",sources);

        return result;
    }


}
