package product_scanner.product_scanner;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.HashMap;

/**
 * Created by Nguyen Khang on 12/1/2017.
 */

public class MyFirebaseDatabase  {

    public static DatabaseReference mDatabase;
    public static HashMap<String,Product> listproduct;
    public static void initDb(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Product").keepSynced(true);
        listproduct= new HashMap<>();


    }
    public static void uploadData(final ProgressDialog progressDialog,final Context context,final Product product ){
        mDatabase.child("Product").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                String key=product.getBarcodeid();

                      mutableData.child(key).setValue(product);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Toast.makeText(context,context.getResources().getString(R.string.success),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
    public static void getData(){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                listproduct.put(dataSnapshot.getKey(),dataSnapshot.getValue(Product.class));
                /*Log.w("Loc test","added" +" "+ dataSnapshot.getKey()+" "+dataSnapshot.getValue(Product.class).getName());

                Log.i("Loc test","added" +" "+ dataSnapshot.getKey()+" "+dataSnapshot.getValue(Product.class).toString());
 */           }

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
