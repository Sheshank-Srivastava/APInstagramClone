package com.example.apinstagramclone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apinstagramclone.R;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListItem> {

    private Context context;
    private List<String> mUserList;

    public UserListAdapter(Context context, List<String> mUserList) {
        this.context = context;
        this.mUserList = mUserList;
    }

    @NonNull
    @Override
    public UserListItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserListItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListItem holder, int position) {
        holder.txtUserName.setText(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class UserListItem  extends RecyclerView.ViewHolder{
        TextView txtUserName;
        public UserListItem(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txt_userName);
        }
    }
}
