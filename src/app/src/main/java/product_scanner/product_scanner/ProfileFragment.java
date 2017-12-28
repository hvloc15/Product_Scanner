package product_scanner.product_scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Nguyen Khang on 11/1/2017.
 */

public class ProfileFragment extends Fragment {
    private FirebaseUser user;
    TextView userName, userType;
    EditText userEmail, userPhone, userAddress;
    ImageView avatar;
    Spinner settingSpinner;
    Button buttonSubmit;


    private Glide glide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        findView(v);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        settingSpinner.setAdapter(myAdapter);

        setOnClick(v);
        getUserInfo();


        return v;
    }

    private void getUserInfo(){
        user =  MyFirebaseAuth.mAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photoUri = user.getPhotoUrl().toString();

            userName.setText(name);
            userEmail.setText(email);

            glide.with(this.getContext())
                    .load(photoUri)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.avatar_img)
                            .optionalFitCenter())
                    .into(avatar);
        }
    }



    private void findView(View v){
        userName = v.findViewById(R.id.name);
        userEmail = v.findViewById(R.id.email);
        userPhone = v.findViewById(R.id.phone);
        userAddress = v.findViewById(R.id.address);
        userType = v.findViewById(R.id.type);
        avatar = v.findViewById(R.id.avatar);
        settingSpinner = v.findViewById(R.id.spinner_menu);
        buttonSubmit = v.findViewById(R.id.button_submit);
        buttonSubmit.setVisibility(View.GONE);

        userEmail.setTag(userEmail.getKeyListener());
        userPhone.setTag(userPhone.getKeyListener());
        userAddress.setTag(userAddress.getKeyListener());

        userEmail.setKeyListener(null);
        userPhone.setKeyListener(null);
        userAddress.setKeyListener(null);

    }

    private void setOnClick(View v){
        settingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        //edit profile
                        editProfile(view);
                        break;
                    case 1:

                        break;
                    default:
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail.setKeyListener(null);
                userPhone.setKeyListener(null);
                userAddress.setKeyListener(null);
                //UPDATE FIREBASE


                buttonSubmit.setVisibility(View.GONE);
                Toast.makeText(view.getContext(),getString(R.string.update_sucessful), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editProfile(View v){
        buttonSubmit.setVisibility(View.VISIBLE);

        userEmail.setKeyListener((KeyListener) userEmail.getTag());
        userPhone.setKeyListener((KeyListener) userPhone.getTag());
        userAddress.setKeyListener((KeyListener) userAddress.getTag());
    }


}
