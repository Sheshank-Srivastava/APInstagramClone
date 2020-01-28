package com.example.apinstagramclone.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apinstagramclone.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListItem> {

    private Context context;
    private List<String> mUserList;
    private ItemClickListener itemClickListener;

    public UserListAdapter(Context context, List<String> mUserList, ItemClickListener itemClickListener) {
        this.context = context;
        this.mUserList = mUserList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public UserListItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);

        return new UserListItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserListItem holder, final int position) {
        holder.txtUserName.setText(mUserList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClicked(v, holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                parseQuery.whereEqualTo("username", mUserList.get(position));

                parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
//                            Toast.makeText(context, user.get("profileProfess")+"", Toast.LENGTH_SHORT).show();
                            final PrettyDialog prettyDialog = new PrettyDialog(context);
                            prettyDialog.setTitle(user.getUsername() + "'s Info")
                                    .setMessage(user.get("profileBio") + "\n" +
                                            user.get("profileProfess") + "\n" +
                                            user.get("profileHobbies") + "\n" +
                                            user.get("profileSport") + "\n")
                                    .setIcon(R.drawable.ic_person)
                                    .addButton(
                                            "OK",       //Button Text
                                            R.color.pdlg_color_white, // button Text Color
                                            R.color.pdlg_color_green,   //button backgroung color
                                            new PrettyDialogCallback() {
                                                @Override
                                                public void onClick() {
                                                    prettyDialog.dismiss();
                                                }
                                            }
                                    ).show();


                        }
                    }
                });
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class UserListItem extends RecyclerView.ViewHolder {
        TextView txtUserName;

        public UserListItem(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txt_userName);
        }
    }

    public interface ItemClickListener {
        public void onItemClicked(View v, int position);
    }
}
