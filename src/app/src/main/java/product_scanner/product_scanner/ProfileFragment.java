package product_scanner.product_scanner;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.KeyListener;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Nguyen Khang on 11/1/2017.
 */

public class ProfileFragment extends Fragment {
    private FirebaseUser user;
    private TextView userName, userType;
    private EditText userEmail, editTextPassword, editTextRePassword;
    private ImageView avatar;
    private Button buttonChangePass;
    private LinearLayout linearLayoutPassword, linearLayoutRePassword;
    private View line1, line2;
    private ImageButton passwordReveal3, passwordReveal4;
    private boolean checkChangePass = true; //check the label of button
    private boolean checkPassReveal = false, checkPassReveal2 = false;


    private Glide glide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        findView(v);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){
                    ((ResideMenu)getActivity()).hidekeyboard(view);
                }
                return true;

            }
        });
        setupEditable(v);
        setOnClick(v);
        getUserInfo();


        return v;
    }



    private void getUserInfo(){
        user =  MyFirebaseAuth.mAuth.getInstance().getCurrentUser();
        if (user != null) {
//            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUri = user.getPhotoUrl();


            userEmail.setText(email);

            glide.with(this.getContext())
                    .load(photoUri)
                    .apply(RequestOptions.circleCropTransform()
                            .placeholder(R.drawable.avatar_img).circleCrop()
                            .optionalFitCenter())
                    .into(avatar);
        }
    }



    private void setupEditable(View v){
        //make edittext non-editable


        editTextPassword.setTag(editTextPassword.getKeyListener());
        editTextRePassword.setTag(editTextRePassword.getKeyListener());


        editTextPassword.setKeyListener(null);
        editTextRePassword.setKeyListener(null);

        linearLayoutPassword.setVisibility(v.GONE);
        linearLayoutRePassword.setVisibility(v.GONE);
        line1.setVisibility(v.GONE);
        line2.setVisibility(v.GONE);

    }


    private void findView(View v){
        userName = v.findViewById(R.id.name);
        userEmail = v.findViewById(R.id.email);
        editTextPassword = v.findViewById(R.id.editText_password2);
        editTextRePassword = v.findViewById(R.id.editText_re_password2);
        userType = v.findViewById(R.id.type);
        avatar = v.findViewById(R.id.avatar);
        buttonChangePass = v.findViewById(R.id.button_change_pass);
        linearLayoutPassword = v.findViewById(R.id.linearLayout_password2);
        linearLayoutRePassword = v.findViewById(R.id.linearLayout_re_password2);
        line1 = v.findViewById(R.id.password_line1);
        line2 = v.findViewById(R.id.password_line2);
        passwordReveal3 = v.findViewById(R.id.imageButton_passwordReveal3);
        passwordReveal4 = v.findViewById(R.id.imageButton_passwordReveal4);
        userEmail.setKeyListener(null);
        editTextPassword.setLongClickable(false); //deny copy password to clipboard
        editTextRePassword.setLongClickable(false);



    }

    private void setOnClick(View v){



        buttonChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View thisView = view;
                if(checkChangePass){
                    checkChangePass = false;
                    editProfile(view);
                    buttonChangePass.setText(R.string.submit);

                } else {
                    final String password = editTextPassword.getText().toString();
                    String repassword = editTextRePassword.getText().toString();
                    if(password.equals(repassword)) {
                        checkChangePass = true;
                        setupEditable(view);
                        buttonChangePass.setText(R.string.changePass);
                        //UPDATE FIREBASE



                        MyFirebaseAuth.mUser = FirebaseAuth.getInstance().getCurrentUser();
                        if(password.length() < 8){
                            editTextPassword.setError(getString(R.string.password_length));
                            editTextPassword.requestFocus();
                            return;
                        } else {

                            user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(thisView.getContext(), "Password updated", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            Toast.makeText(thisView.getContext(), "Error auth failed", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(thisView.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }



//                        if(user.updatePassword(password).isSuccessful()){
//                            Toast.makeText(thisView.getContext(), "Password updated", Toast.LENGTH_SHORT).show();
//                        }else
//                            Toast.makeText(thisView.getContext(), "Error auth failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Confirm password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                //UPDATE FIREBASE



            }
        });

        passwordReveal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPassReveal) {
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    checkPassReveal = true;
                } else {
                    editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                    checkPassReveal = false;
                }
                editTextPassword.setSelection(editTextPassword.getText().length());
            }
        });

        passwordReveal4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPassReveal2) {
                    editTextRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    checkPassReveal2 = true;
                } else {
                    editTextRePassword.setTransformationMethod(new PasswordTransformationMethod());
                    checkPassReveal2 = false;
                }
                editTextRePassword.setSelection(editTextRePassword.getText().length());
            }
        });
    }

    private void editProfile(View v){
        editTextPassword.setKeyListener((KeyListener) editTextPassword.getTag());
        editTextRePassword.setKeyListener((KeyListener) editTextRePassword.getTag());
        linearLayoutPassword.setVisibility(v.VISIBLE);
        linearLayoutRePassword.setVisibility(v.VISIBLE);
        line1.setVisibility(v.VISIBLE);
        line2.setVisibility(v.VISIBLE);

        return;
    }



}



