package product_scanner.product_scanner;

/**
 * Created by Nguyen Khang on 12/21/2017.
 */

public class ItemProduct {
    private String barcode,name,price,quantity;

    public String getBarcode() {
        return barcode;
    }

    public ItemProduct(String barcode, String name, String price, String quantity) {
        this.barcode= barcode;
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
