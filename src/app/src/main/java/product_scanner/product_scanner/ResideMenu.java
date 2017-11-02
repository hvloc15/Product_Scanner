package product_scanner.product_scanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.special.ResideMenu.ResideMenuItem;

public class ResideMenu extends AppCompatActivity implements View.OnClickListener {
    private com.special.ResideMenu.ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemScan;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reside_menu);

        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(R.id.main_reside_menu,new HomeFragment());
    }
    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new com.special.ResideMenu.ResideMenu(this);

        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "Profile");
        itemScan  = new ResideMenuItem(this, R.drawable.icon_scan,  "Scan");

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemScan.setOnClickListener(this);


        resideMenu.addMenuItem(itemHome, com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemScan, com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);

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

        }

        @Override
        public void closeMenu() {

        }
    };
    @Override
    public void onClick(View view) {
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

    public void changeFragment(int fragment,Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


}
