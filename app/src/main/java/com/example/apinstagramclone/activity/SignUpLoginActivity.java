package com.example.apinstagramclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apinstagramclone.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpLoginActivity extends AppCompatActivity {

    Button butSignUp, butLogIn;
    EditText etSignUpName, etSignupPassword, etLogInName, etLogInPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);
        /**
         * EditText Definition
         */
        etSignUpName =  findViewById(R.id.etSignUpUserName);
        etSignupPassword=  findViewById(R.id.etSignUpPassword);
        etLogInName =  findViewById(R.id.etLogInUserName);
        etLogInPassword =  findViewById(R.id.etLogInPassword);
        /**
         * Button Definition
         */
        butSignUp = findViewById(R.id.butSignUp);
        butLogIn = findViewById(R.id.butLogIn);


        /**
         * button for the function of sign Up
         */
        butSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(etSignUpName.getText().toString());
                appUser.setPassword(etSignupPassword.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                         if(e == null){
                             Toast.makeText(SignUpLoginActivity.this, appUser.getUsername()+" is Signed Up successfully", Toast.LENGTH_SHORT).show();
                         }
                         else{
                             Toast.makeText(SignUpLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                         }
                    }
                });
            }
        });

        /**
         * Button for the function of Login
         */
        butLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(etLogInName.getText().toString(),
                        etLogInPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e == null){
                            Toast.makeText(SignUpLoginActivity.this, user.get("username")+" is Logged-In", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUpLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
