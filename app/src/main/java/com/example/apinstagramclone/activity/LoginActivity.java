package com.example.apinstagramclone.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apinstagramclone.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener {
    Button btn_Login, btn_SignUp;
    EditText et_Email, et_Password;
    LinearLayout linear_loginRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().setTitle("LogIn");

        btn_Login = findViewById(R.id.btnLogIn);
        btn_SignUp = findViewById(R.id.btnSignUp);

        linear_loginRoot = findViewById(R.id.linear_loginRoot);

        et_Email = findViewById(R.id.etEmail);
        et_Password = findViewById(R.id.etPassword);

        btn_Login.setOnClickListener(this);
        btn_SignUp.setOnClickListener(this);
        linear_loginRoot.setOnClickListener(this);
        et_Password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btn_Login);
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnLogIn:
                if (et_Email.getText().toString().equals("") ||
                        et_Password.getText().toString().equals("")) {
                    Toast.makeText(this, "Email ID and Password are reqired.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser.logInInBackground(et_Email.getText().toString(),
                        et_Password.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user == null && e != null) {
                                    Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                transitionToSocialMediaactivity();
//                                Toast.makeText(LoginActivity.this, et_Email.getText().toString()+" is logged in.", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.btnSignUp:
                startActivity(new Intent(LoginActivity.this, SignUp.class));
                finish();
                break;
            case R.id.linear_loginRoot:
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:

        }
    }

    private void transitionToSocialMediaactivity() {
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}
