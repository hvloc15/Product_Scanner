package product_scanner.product_scanner;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Nguyen Khang on 11/16/2017.
 */

public class MyFirebaseAuth {
    static public FirebaseAuth mAuth;
    static public FirebaseUser mUser;
    static void init(){
        mAuth=FirebaseAuth.getInstance();
    }
    static boolean isLogging(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            return (mAuth.getCurrentUser()!=null);
        }
        return (mAuth.getCurrentUser()!=null);
    }
}
