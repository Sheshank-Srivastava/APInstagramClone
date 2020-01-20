package com.example.apinstagramclone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apinstagramclone.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.jetbrains.annotations.Nullable;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText et_UserName, et_Email, et_Password;
    Button btn_SignUp, btn_LogIn;
    LinearLayout linear_signupRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("Sign Up");

        et_Email = findViewById(R.id.etEmailSignUp);
        et_UserName = findViewById(R.id.etUserNameSignUp);
        et_Password = findViewById(R.id.etPasswordSignUp);


        linear_signupRoot = findViewById(R.id.linear_signupRoot);

        btn_SignUp = findViewById(R.id.btnSignUp);
        btn_LogIn = findViewById(R.id.btnLogIn);


        btn_SignUp.setOnClickListener(this);
        btn_LogIn.setOnClickListener(this);
        linear_signupRoot.setOnClickListener(this);

        et_Password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btn_SignUp);
                }
                return false;
            }
        });

        if (ParseUser.getCurrentUser() != null) {

            transitionToSocialMediaactivity();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:


                if (et_Email.getText().toString().equals("") ||
                        et_UserName.getText().toString().equals("") ||
                        et_Password.getText().toString().equals("")) {
                    Toast.makeText(this, "Email,UserName,Password is  required", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ParseUser appUser = new ParseUser();
                appUser.setEmail(et_Email.getText().toString().trim());
                appUser.setUsername(et_UserName.getText().toString().trim());
                appUser.setPassword(et_Password.getText().toString().trim());

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("SigningUp");
                progressDialog.setMessage("Signing up in Progress");
                progressDialog.show();

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            transitionToSocialMediaactivity();
                            Toast.makeText(SignUp.this, appUser.getUsername() + " is signed up.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUp.this, "There is an error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();
                    }
                });
                break;
            case R.id.btnLogIn:
                startActivity(new Intent(SignUp.this, LoginActivity.class));

                finish();
                break;

            case R.id.linear_signupRoot:
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void transitionToSocialMediaactivity() {
        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}