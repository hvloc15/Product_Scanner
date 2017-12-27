package product_scanner.product_scanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import static product_scanner.product_scanner.MyFirebaseAuth.mAuth;
import static product_scanner.product_scanner.MyFirebaseAuth.mUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private  EditText emailEditText, passwordEditText;
    private   ProgressBar progressBar;
    private  ImageButton passwordReveal;
    private  boolean checkPassReveal = false;
    public LinearLayout linearLayout;
    private InputMethodManager imm;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findView();
        setOnClickItem();
        denyCopyPassword();
        facebook();


    }

    private void facebook(){
        /*AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            startActivity(new Intent(SignInActivity.this, ResideMenu.class));
        }*/
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity(new Intent(SignInActivity.this, ResideMenu.class));
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }
    private void denyCopyPassword() {
        passwordEditText.setLongClickable(false); //deny copy password to clipboard
    }

    private void setOnClickItem() {
        findViewById(R.id.textView_signUp).setOnClickListener(this);
        findViewById(R.id.button_signIn).setOnClickListener(this);
        findViewById(R.id.textView_passwordReset).setOnClickListener(this);
        passwordReveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPassReveal) {
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    checkPassReveal = true;
                } else {
                    passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                    checkPassReveal = false;
                }
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });
    }

    private void findView() {
        emailEditText =  findViewById(R.id.editText_email);
        passwordEditText = findViewById(R.id.editText_password);
        progressBar = findViewById(R.id.progressbar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#00B6D6"), android.graphics.PorterDuff.Mode.SRC_ATOP);//set color for progressBar
        passwordReveal = findViewById(R.id.imageButton_passwordReveal);
        linearLayout= (LinearLayout) findViewById(R.id.view);
        LoginButton loginButton = findViewById(R.id.login_button);
        linearLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                hidekeyboard(view);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.textView_signUp: {
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            }
            case R.id.button_signIn: {
                userLogin();
                break;
            }
            case R.id.textView_passwordReset: {
                startActivity(new Intent(this, ResetPasswordActivity.class));
                break;
            }

        }
    }


    private void userLogin() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(email.isEmpty()){
            emailEditText.setError("Email is required.");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please enter a valid email.");
            emailEditText.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordEditText.setError("Password is required.");
            passwordEditText.requestFocus();
            return;
        }

        if(password.length() < 8){
            passwordEditText.setError("Password length must be at least 8.");
            passwordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(!MyFirebaseAuth.isLogging())
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                else
                    finish();
            }

        });
    }
    private void hidekeyboard(View view) {

        imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @Override
    protected void onStart() {
        super.onStart();
       mUser= mAuth.getCurrentUser();
        if(mUser!=null){
            //changetoMainMenu();
            finish();
        }
    }

    private void changetoMainMenu() {
        startActivity(new Intent(this, ResideMenu.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
