package product_scanner.product_scanner;

import android.content.Context;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailEditText, passwordEditText, rePasswordEdidText;
    private ProgressBar progressBar;
    private ImageButton passwordReveal, passwordReveal2;
    private boolean checkPassReveal = false, checkPassReveal2 = false;

    private FirebaseAuth mAuth;
    private LinearLayout linearLayout;
    private InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mAuth = FirebaseAuth.getInstance();
        findView();


        passwordEditText.setLongClickable(false); //deny copy password to clipboard
        rePasswordEdidText.setLongClickable(false);

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

        passwordReveal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPassReveal2) {
                    rePasswordEdidText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    checkPassReveal2 = true;
                } else {
                    rePasswordEdidText.setTransformationMethod(new PasswordTransformationMethod());
                    checkPassReveal2 = false;
                }
                rePasswordEdidText.setSelection(rePasswordEdidText.getText().length());
            }
        });


    }

    private void findView() {
        emailEditText = (EditText) findViewById(R.id.editText_email);
        passwordEditText = (EditText) findViewById(R.id.editText_password);
        rePasswordEdidText = (EditText) findViewById(R.id.editText_re_password);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00B6D6"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        passwordReveal = (ImageButton)findViewById(R.id.imageButton_passwordReveal);
        passwordReveal2 = (ImageButton)findViewById(R.id.imageButton_passwordReveal2);
        findViewById(R.id.button_signUp).setOnClickListener(this);
        findViewById(R.id.textView_signIn).setOnClickListener(this);
        linearLayout= (LinearLayout) findViewById(R.id.view);
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
            case R.id.button_signUp: {
                registerUser();
                break;
            }
            case R.id.textView_signIn: {
                finish();
                break;
            }
        }
    }

    private void registerUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String repassword = rePasswordEdidText.getText().toString();

        if(email.isEmpty()){
            emailEditText.setError("Email is required.");
            emailEditText.requestFocus();

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

        if(!repassword.equals(password)){
            rePasswordEdidText.setError("Confirm password is wrong.");
            rePasswordEdidText.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Register Success.", Toast.LENGTH_SHORT).show();
                } else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "This email is already registered.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void hidekeyboard(View view) {

        imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}
