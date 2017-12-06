package product_scanner.product_scanner;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Nguyen Khang on 12/1/2017.
 */

public class MyFirebaseDatabase  {
    public static DatabaseReference mDatabase;
    public static HashMap<String,Product> listproduct;
    public static void initDb(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listproduct= new HashMap<>();
/*        Product product=new Product("Lavie Large","CircleK","600");
        Map<String,Object> productmap=product.toMap();
        Map<String,Object> map =new HashMap<>();
        map.put("/Product/8935005801012/",productmap);
        mDatabase.updateChildren(map);*/
    }
    public static void getData(){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                listproduct.put(dataSnapshot.getKey(),dataSnapshot.getValue(Product.class));

            Log.w("Loc test","added" +" "+ dataSnapshot.getKey()+" "+dataSnapshot.getValue(Product.class).toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.child("Product").addChildEventListener(childEventListener);
    }
}
