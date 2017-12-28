package product_scanner.product_scanner;

/**
 * Created by Nguyen Khang on 12/21/2017.
 */

public class ItemProduct {
    private String name,price,quantity;



    public ItemProduct( String name, String price, String quantity) {

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }
}
