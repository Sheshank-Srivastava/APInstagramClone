package com.example.apinstagramclone.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apinstagramclone.R;
import com.parse.ParseUser;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    EditText et_UserName, et_Email, et_Password;
    Button btn_SignUp, btn_LogIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_Email =  findViewById(R.id.etEmailSignUp);
        et_UserName=  findViewById(R.id.etUserNameSignUp);
        et_Password=  findViewById(R.id.etPasswordSignUp);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSignUp:
                final ParseUser appUser =  new ParseUser();
                appUser.setEmail(et_Email.getText().toString().trim());
                appUser.setUsername(et_UserName.getText().toString().trim());
                appUser.setPassword(et_Password.getText().toString().trim());
            break;
            case R.id.btnLogIn:
                break;
        }
    }
}