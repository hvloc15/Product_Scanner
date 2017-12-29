package product_scanner.product_scanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResideMenu extends AppCompatActivity implements View.OnClickListener {
    private com.special.ResideMenu.ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private InputMethodManager imm;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemScan;
    private ResideMenuItem itemSignout;
    private ResideMenuItem itemCart;
    private ResideMenuItem itemSignin;
    private ResideMenuItem itemAdd;
    public  static AddToCartDatabase addToCartDatabase;
    private List<ResideMenuItem> menuItems_Anonymous, menuItems_User;
    protected String barcodeid="";
    private boolean isOpen=false;

    private final static int MY_PERMISSIONS_REQUEST_CAMERA=1003;
    private boolean isInitDB;
    private final String STORE_KEY="Store dbstate";
    public final static String LOCALE="locale";
    public SharedPreferences sharedPreferences;
    private ResideMenuItem itemLanguage;
    private String locale;
    public CheckInternet internet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reside_menu);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        internet=new CheckInternet(this);
        setUpResideMenu();
        setUpMenuItems();
        initAddtoCart_DB();

        if( savedInstanceState == null ) {
            askPermission();
          MyFirebaseAuth.init();
          MyFirebaseDatabase.initDb();
          MyFirebaseStorage.init();
          MyFirebaseDatabase.getData();
          changeFragment(R.id.main_reside_menu, new ScanFragment());
        }
        else {
            isOpen = savedInstanceState.getBoolean("state");

        }
        if(isOpen) {
            resideMenu.openMenu(com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    private String getLocale() {
       return sharedPreferences.getString(LOCALE,"en");
    }

    private void changetocurrentFragment(int item) {
        if(item==0)
            changeFragment(R.id.main_reside_menu, new ScanFragment());
        else if(item==1)
            changeFragment(R.id.main_reside_menu, new LanguagesFragment());
        else if(item==2)
            changeFragment(R.id.main_reside_menu, new ItemFragment());
    }

    private void initAddtoCart_DB() {
     /*    if(!isInitDB) {
             this.deleteDatabase(AddToCartDatabase.DATABASE_NAME);
         }*/
         addToCartDatabase=new AddToCartDatabase(this);
    }

    private void askPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CAMERA);

        }

    }


    private boolean getDBState(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            return savedInstanceState.getBoolean(STORE_KEY);
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("state",isOpen);
        outState.putBoolean(STORE_KEY,true);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MyFirebaseAuth.isLogging()){
            resideMenu.setMenuItems(menuItems_User, com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);
        }
        else
            resideMenu.setMenuItems(menuItems_Anonymous, com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);
    }

    public void setUpMenuItems() {
        locale=getLocale();
        setLocale(locale);
        addItemsforMenu();
        menuItems_Anonymous= itemsforAnonymous();
        menuItems_User= itemsforUser();
    }

    @Override
    protected void onStart() {
        super.onStart();



    }



    private ResideMenuItem addItem(int icon, String title){
        ResideMenuItem item= new ResideMenuItem(this,icon,title);
        item.setOnClickListener(this);
        return item;
    }
    private void addItemsforMenu(){
        itemProfile  = addItem(R.drawable.icon_profile, getString(R.string.profile));
        itemScan  = addItem( R.drawable.icon_scan, getString(R.string.scan));
        itemSignout= addItem(R.drawable.icon_logout, getString(R.string.logout));
        itemSignin= addItem(R.drawable.icon_logout,getString(R.string.sign_in));
        itemAdd= addItem(R.drawable.icon_insert,getString(R.string.addproduct));
        itemCart= addItem(R.drawable.icon_cart,getString(R.string.cart));

        itemLanguage= addItem(R.drawable.icon_language, getString(R.string.language));


    }

    private List<ResideMenuItem> itemsforAnonymous() {
        List<ResideMenuItem> result=new ArrayList<>();
        result.add(itemScan);
        result.add(itemCart);
        result.add(itemLanguage);
        result.add(itemSignin);
        return result;
    }

    private   List<ResideMenuItem> itemsforUser() {
        List<ResideMenuItem> result=new ArrayList<>();
        result.add(itemScan);
        result.add(itemCart);
        result.add(itemLanguage);
        result.add(itemProfile);
        result.add(itemSignout);

        return result;
    }

    private void setUpResideMenu() {

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
             getFragmentManager().popBackStack();
             if(view == itemProfile)
                 changeFragment(R.id.main_reside_menu,new ProfileFragment());
             else if(view == itemScan){

                 changeFragment(R.id.main_reside_menu,new ScanFragment());

             }
             else if(view == itemAdd){
                 changeFragment(R.id.main_reside_menu,new AddFragment());
             }
            else if(view ==itemCart){

                 changeFragment(R.id.main_reside_menu,new ItemFragment());

             }

             else if(view== itemLanguage){

                 changeFragment(R.id.main_reside_menu, new LanguagesFragment());
             }


             resideMenu.closeMenu();
         }


    }

    public void changeFragmentNavigate(int fragment,Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    public void changeFragment(int fragment,Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                    .replace(fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    public void hidekeyboard(View view) {

        imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    private boolean isStackFragmentEmpty(){
        return getFragmentManager().getBackStackEntryCount()==0;
    }
    /*@Override
    public void onBackPressed() {
        if(isStackFragmentEmpty()) {
            if (!isOpen)
                resideMenu.openMenu(com.special.ResideMenu.ResideMenu.DIRECTION_LEFT);
            else
                super.onBackPressed();
        }
        else{
            getFragmentManager().popBackStack();
        }

    }*/
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
