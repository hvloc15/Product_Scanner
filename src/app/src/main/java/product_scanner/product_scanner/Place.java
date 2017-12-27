package product_scanner.product_scanner;

/**
 * Created by ASUS on 12/21/2017.
 */

public class Place {
    String place;
    String price;
    Place(String place, String price){
        this.place=place;
        this.price=price;
    }
    String getPlace(){
        return this.place;
    }
    String getPrice(){
        return this.price;
    }
}
