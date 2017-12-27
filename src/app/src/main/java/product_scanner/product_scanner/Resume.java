package product_scanner.product_scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Resume extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        finish();
    }
    public static void resumeActivity(Context context){

            Intent i= new Intent(context,Resume.class);
              i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
             context.startActivity(i);

    }

    public static void restartActivity(Context context){
        ((Activity)context).finish();
        Intent i =((Activity) context).getIntent();
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(i);
    }
}
