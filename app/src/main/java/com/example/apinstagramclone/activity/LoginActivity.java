package com.example.apinstagramclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apinstagramclone.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity
                            implements View.OnClickListener{
    Button btn_Login,btn_SignUp;
    EditText et_Email,et_Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_Login = findViewById(R.id.btnLogIn_LogInActivity);
        btn_SignUp = findViewById(R.id.btnSignUp_LogInActivity);

        et_Email = findViewById(R.id.etEmailLogIn);
        et_Password = findViewById(R.id.etPasswordLogIn);

        btn_Login.setOnClickListener(this);
        btn_SignUp.setOnClickListener(this);

        et_Password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == EditorInfo.IME_ACTION_DONE && event.getAction()== KeyEvent.ACTION_DOWN){
                    onClick(btn_Login);
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btnLogIn_LogInActivity:

                ParseUser.logInInBackground(et_Email.getText().toString(),
                        et_Password.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user==null&& e!=null){
                                    Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                Toast.makeText(LoginActivity.this, et_Email.getText().toString()+" is logged in.", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.btnSignUp_LogInActivity:
                startActivity(new Intent(LoginActivity.this,SignUp.class));
                finish();
                break;
            default:

        }
    }
}
