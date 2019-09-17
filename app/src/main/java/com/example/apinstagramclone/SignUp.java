package com.example.apinstagramclone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button butSaveObject,butGetAllKB;
    private EditText etName, etPunchSpeed, etPunchPower, etKickSpeed, etKickPower;
    private TextView txtGetData;

    private String allKickBoxer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        butSaveObject = findViewById(R.id.butSaveObject);
        butGetAllKB = findViewById(R.id.butGetAllKB);
        etName = findViewById(R.id.etName);
        etPunchSpeed = findViewById(R.id.etPunchSpeed);
        etPunchPower = findViewById(R.id.etPunchPower);
        etKickSpeed = findViewById(R.id.etKickSpeed);
        etKickPower = findViewById(R.id.etKickPower);
        txtGetData = findViewById(R.id.txtGetData);

        butSaveObject.setOnClickListener(SignUp.this);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("HKbPVRq3HC", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object!=null&&e == null) {
                            txtGetData.setText(object.get("name").toString()+"\n"+object.get("punchPower").toString()+"\n"+object.get("punchSpeed").toString());
                        }
                    }
                });
            }
        });

        butGetAllKB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxer = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(objects.size()>0 && e==null){
                            for(ParseObject i:objects){
                                allKickBoxer+=i.get("name")+"\n";
                            }
                            Toast.makeText(SignUp.this, allKickBoxer    , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

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
