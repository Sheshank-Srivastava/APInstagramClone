package com.example.apinstagramclone.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apinstagramclone.R;
import com.example.apinstagramclone.adapter.UserListAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserTabFragment extends Fragment {

    private RecyclerView recyclerUserList;
    private UserListAdapter mUserListAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<String> mUserList;

    public UserTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_user_tab, container, false);

        ParseQuery<ParseUser> mParseQuery = ParseUser.getQuery();
        mUserList = new ArrayList<>();
        mParseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        mParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e != null && objects.size() <= 0) return;
                for (ParseUser mUser : objects){
                    mUserList.add(mUser.getUsername());
                }
            }
        });
        recyclerUserList = view.findViewById(R.id.recycler_userList);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerUserList.setLayoutManager(mLayoutManager);
        mUserListAdapter =  new UserListAdapter(getContext(),mUserList);
        recyclerUserList.setAdapter(mUserListAdapter);

        return view;
    }

}
