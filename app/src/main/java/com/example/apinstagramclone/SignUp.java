package com.example.apinstagramclone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button butSaveObject;
    private EditText etName, etPunchSpeed, etPunchPower, etKickSpeed, etKickPower;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        butSaveObject = findViewById(R.id.butSaveObject);
        etName = findViewById(R.id.etName);
        etPunchSpeed = findViewById(R.id.etPunchSpeed);
        etPunchPower = findViewById(R.id.etPunchPower);
        etKickSpeed = findViewById(R.id.etKickSpeed);
        etKickPower = findViewById(R.id.etKickPower);

        butSaveObject.setOnClickListener(SignUp.this);
    }

    @Override
    public void onClick(View v) {
        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", etName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(etPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(etPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(etKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(etKickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(SignUp.this, kickBoxer.get("name") + " is saved to server.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUp.this, kickBoxer.get("name") + " is now save." + e.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("InstaError", e.toString());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    // Function to save data to the server
    public void helloWorldTrapped(View view) {
//        ParseObject boxer = new ParseObject("Boxer");
//        boxer.put("punch_speed", 200);
//        boxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    Toast.makeText(SignUp.this, "Boxer Object is Saved successfully", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        final ParseObject kickBoxer = new ParseObject("KickBoxer");
        kickBoxer.put("name", "Tanshu");
        kickBoxer.put("punchSpeed", 1000);
        kickBoxer.put("punchPower", 2000);
        kickBoxer.put("kickSpeed", 3000);
        kickBoxer.put("kickPower", 4000);
        kickBoxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUp.this, kickBoxer.get("name") + "  is Saved to server.", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
