package product_scanner.product_scanner;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by nguye on 11/30/2017.
 */

public class MyFirebaseStorage {
    static public FirebaseStorage storage ;
    static public StorageReference storageRef;
    static public void init(){
        storage=FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }
}
