package product_scanner.product_scanner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Nguyen Khang on 11/16/2017.
 */

public class MyFirebaseAuth {
    static public FirebaseAuth mAuth;
    static public FirebaseUser mUser;
    static boolean isLogging(){

        return (mAuth.getCurrentUser()!=null);
    }
}
