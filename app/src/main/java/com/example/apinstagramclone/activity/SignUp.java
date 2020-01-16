package com.example.apinstagramclone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_Email = findViewById(R.id.etEmailSignUp);
        et_UserName = findViewById(R.id.etUserNameSignUp);
        et_Password = findViewById(R.id.etPasswordSignUp);

        btn_SignUp = findViewById(R.id.btnSignUp);
        btn_LogIn = findViewById(R.id.btnLogIn);

        btn_SignUp.setOnClickListener(this);
        btn_LogIn.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
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
        }
    }
}