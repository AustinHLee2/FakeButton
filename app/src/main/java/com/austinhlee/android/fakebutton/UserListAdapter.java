package com.austinhlee.android.fakebutton;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Austin Lee on 3/5/2018.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private List<User> mUserList;
    private Context mContext;

    static class UserViewHolder extends RecyclerView.ViewHolder{

        TextView idTextView;
        TextView nameTextView;
        TextView emailTextView;
        TextView candidateTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            idTextView = (TextView)itemView.findViewById(R.id.id_text_view);
            nameTextView = (TextView)itemView.findViewById(R.id.name_text_view);
            emailTextView = (TextView)itemView.findViewById(R.id.email_text_view);
            candidateTextView = (TextView)itemView.findViewById(R.id.candidate_text_view);
        }
    }

    public UserListAdapter(Context context, List<User> userList){
        mUserList = userList;
        mContext = context;
    }

    @NonNull
    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mUserList.get(position);
        holder.idTextView.setText(Integer.toString(user.getId()));
        holder.nameTextView.setText(user.getName());
        holder.emailTextView.setText(user.getEmail());
        holder.candidateTextView.setText(user.getCandidate());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
