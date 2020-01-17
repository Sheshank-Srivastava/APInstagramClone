package com.example.apinstagramclone.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apinstagramclone.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTabFragment extends Fragment {

    EditText et_profileName, et_profileBio, et_profileProfess, et_profileHobbies, et_profileSport;
    Button btn_updateInfo;

    public ProfileTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        et_profileName = view.findViewById(R.id.et_profileName);
        et_profileBio = view.findViewById(R.id.et_profileBio);
        et_profileProfess = view.findViewById(R.id.et_profileProfess);
        et_profileHobbies = view.findViewById(R.id.et_profileHobbies);
        et_profileSport = view.findViewById(R.id.et_profileSport);

        btn_updateInfo = view.findViewById(R.id.btn_updateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();
        et_profileName.setText(parseUser.get("profileName") != null ? parseUser.get("profileName").toString() : "");
        et_profileBio.setText(parseUser.get("profileBio") != null ? parseUser.get("profileBio").toString() : "");
        et_profileProfess.setText(parseUser.get("profileProfess") != null ? parseUser.get("profileProfess").toString() : "");
        et_profileHobbies.setText(parseUser.get("profileHobbies") != null ? parseUser.get("profileHobbies").toString() : "");
        et_profileSport.setText(parseUser.get("profileSport") != null ? parseUser.get("profileSport").toString() : "");

        btn_updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", et_profileName.getText().toString());
                parseUser.put("profileBio", et_profileBio.getText().toString());
                parseUser.put("profileProfess", et_profileProfess.getText().toString());
                parseUser.put("profileHobbies", et_profileHobbies.getText().toString());
                parseUser.put("profileSport", et_profileSport.getText().toString());
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Update");
                progressDialog.setMessage("Data is saving to the server");
                progressDialog.setCancelable(false);
                progressDialog.show();
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        progressDialog.dismiss();
                        if (e != null) {
                            Toast.makeText(getContext(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(getContext(), "Info Updated", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return view;
    }

}
