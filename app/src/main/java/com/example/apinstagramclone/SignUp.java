package com.example.apinstagramclone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SignUp extends AppCompatActivity {

    private TextView txtGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGetData = findViewById(R.id.txtDataServer);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUp.this, "Text Area Is clicked", Toast.LENGTH_SHORT).show();
                ParseQuery<ParseObject> parseQuary = ParseQuery.getQuery("Boxer");
                parseQuary.getInBackground("Lu6HZ4okHA", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object != null && e == null){

                            txtGetData.setText(object.get("punch_speed").toString());
                        }else{
                            Toast.makeText(SignUp.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("Instagram",e.toString());
                        }

                    }
                });
            }
        });
    }
}
