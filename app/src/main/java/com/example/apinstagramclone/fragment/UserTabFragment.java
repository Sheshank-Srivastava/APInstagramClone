package com.example.apinstagramclone.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apinstagramclone.R;
import com.example.apinstagramclone.activity.UsersPosts;
import com.example.apinstagramclone.adapter.UserListAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

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
    private TextView txtLoadingMessege;
    private ParseQuery<ParseUser> mParseQuery;

    public UserTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(UserTabFragment.class.getSimpleName(), "OnCreate");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_user_tab, container, false);
        recyclerUserList = view.findViewById(R.id.recycler_userList);
        txtLoadingMessege = view.findViewById(R.id.txt_LoadingMessege);
        txtLoadingMessege.setVisibility(View.VISIBLE);
        mParseQuery = ParseUser.getQuery();
        mUserList = new ArrayList<>();
        mParseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        mParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
//                txtLoadingMessege.setVisibility(View.GONE);
                recyclerUserList.setVisibility(View.VISIBLE);
                if (e != null && objects.size() <= 0) return;
                for (ParseUser mUser : objects) {
                    mUserList.add(mUser.getUsername());
                }
            }
        });


        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerUserList.setLayoutManager(mLayoutManager);
        mUserListAdapter = new UserListAdapter(getContext(), mUserList, new UserListAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View v,int position) {
                Intent intent =  new Intent(getActivity(), UsersPosts.class);
                intent.putExtra("username",mUserList.get(position));
                startActivity(intent);
            }
        });
        recyclerUserList.setAdapter(mUserListAdapter);
        txtLoadingMessege.animate().alpha(0).setDuration(2000);
        return view;
    }

}
