package product_scanner.product_scanner;

/**
 * Created by nguye on 11/18/2017.
 */

public class Product {
    private String ID;
    private String name;
    private String price;
    private String info;
    private String imageURL;

    public  void Product()  {};

    public void Product(String ID, String name, String price, String info, String imageURL){
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.info = info;
        this.imageURL = imageURL;
    }

    //GET
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }

    public String getImageURL() {
        return imageURL;
    }

    //SET

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }



}
