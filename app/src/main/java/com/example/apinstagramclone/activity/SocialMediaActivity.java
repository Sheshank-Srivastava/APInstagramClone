package com.example.apinstagramclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apinstagramclone.R;
import com.parse.ParseUser;

public class SocialMediaActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        getSupportActionBar().setTitle("Social Media Activity");
        btn_logout =  findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                ParseUser.getCurrentUser().logOut();
                startActivity(new Intent(SocialMediaActivity.this,SignUp.class));
            break;
        }
    }
}
