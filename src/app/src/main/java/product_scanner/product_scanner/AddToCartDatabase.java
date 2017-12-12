package product_scanner.product_scanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nguyen Khang on 12/12/2017.
 */

public class AddToCartDatabase extends SQLiteOpenHelper {
    final static String DATABASE_NAME="Cart.db";
    final static String TABLE_NAME="product_table";
    final static String COL0="BarcodeID";
    final static String COL1="Name";
    final static String COL2="Quantity";
    final static String COL3="Price";


    public AddToCartDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME+" ( "+COL0+" TEXT PRIMARY KEY, "+COL1+" TEXT, "+ COL2+" TEXT, "+COL3+"TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }


    public  boolean updateData(Product product,String barcode,String quantity,String place){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL0,barcode);
        contentValues.put(COL1,product.getName());
        contentValues.put(COL2,quantity);
        contentValues.put(COL3,product.getSources().get(place));
        int num=db.update(TABLE_NAME,contentValues,COL0+" = "+"'"+barcode+"'",null);
        if(num>0)
            return true;
        else
            return false;
    }

    public boolean insertData(Product product,String barcode,String quantity,String place){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL0,barcode);
        contentValues.put(COL1,product.getName());
        contentValues.put(COL2,quantity);
        contentValues.put(COL3,product.getSources().get(place));
        long check=db.insert(TABLE_NAME,null,contentValues);
        return check!=-1;
    }
    public void deleteData(String  barcode){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,COL0 + "=" + "'"+barcode+"'",null);
    }
}