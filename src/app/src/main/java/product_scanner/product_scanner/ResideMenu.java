package product_scanner.product_scanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;

import static product_scanner.product_scanner.MyFirebaseAuth.mAuth;

public class ResideMenu extends AppCompatActivity implements View.OnClickListener {
    private com.special.ResideMenu.ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemScan;
    private ResideMenuItem itemSignout;
    private ResideMenuItem itemSignin;
    private List<ResideMenuItem> menuItems_Anonymous, menuItems_User;
    private boolean isOpen=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reside_menu);

        setUpMenu();
        addItemsforMenu();
        menuItems_Anonymous= itemsforAnonymous();
        menuItems_User= itemsforUser();

        if( savedInstanceState == null ) {

            mAuth = FirebaseAuth.getInstance();
            changeFragment(R.id.main_reside_menu, new ScanFragment());
        }
        else
            isOpen=savedInstanceState.getBoolean("state");

        if(isOpen) {
            resideMenu.openMenu(com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("state",isOpen);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(MyFirebaseAuth.isLogging()){
            resideMenu.setMenuItems(menuItems_User, com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);
        }
        else
            resideMenu.setMenuItems(menuItems_Anonymous, com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);
    }



    private ResideMenuItem addItem(int icon, String title){
        ResideMenuItem item= new ResideMenuItem(this,icon,title);
        item.setOnClickListener(this);
        return item;
    }
    private void addItemsforMenu(){
        itemProfile  = addItem(R.drawable.icon_profile,  "Profile");
        itemScan  = addItem( R.drawable.icon_scan,  "Scan");
        itemSignout= addItem(R.drawable.icon_logout, "Log out");
        itemScan  = addItem( R.drawable.icon_scan,  "Scan");
        itemSignin= addItem(R.drawable.icon_logout, "Sign in");

    }

    private List<ResideMenuItem> itemsforAnonymous() {
        List<ResideMenuItem> result=new ArrayList<>();
        result.add(itemScan);
        result.add(itemSignin);
        return result;
    }

    private   List<ResideMenuItem> itemsforUser() {
        List<ResideMenuItem> result=new ArrayList<>();
        result.add(itemScan);
        result.add(itemProfile);
        result.add(itemSignout);
        return result;
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new com.special.ResideMenu.ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // You can disable a direction by setting ->
        resideMenu.setSwipeDirectionDisable(com.special.ResideMenu.ResideMenu.DIRECTION_RIGHT);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }
    private com.special.ResideMenu.ResideMenu.OnMenuListener menuListener = new com.special.ResideMenu.ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            isOpen=true;
        }

        @Override
        public void closeMenu() {
        isOpen=false;
        }
    };
    @Override
    public void onClick(View view) {
         if(view == itemSignout){
            MyFirebaseAuth.mAuth.signOut();
           resideMenu.setMenuItems(menuItems_Anonymous, com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);
        }
        else if(view == itemSignin){
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
        }
        else{
             if (view == itemHome){
                 changeFragment(R.id.main_reside_menu,new HomeFragment());
             }
             else if(view == itemProfile)
                 changeFragment(R.id.main_reside_menu,new ProfileFragment());
             else if(view == itemScan){
                 changeFragment(R.id.main_reside_menu,new ScanFragment());
             }

             resideMenu.closeMenu();
         }


    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void changeFragment(int fragment,Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


}
