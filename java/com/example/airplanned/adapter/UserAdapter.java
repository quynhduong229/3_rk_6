package com.example.airplanned.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airplanned.R;
import com.example.airplanned.model.LodgingType;
import com.example.airplanned.model.Trip;
import com.example.airplanned.model.User;
import com.example.airplanned.model.UserType;


import org.w3c.dom.Text;

import java.util.List;

/**
 * @author Julie Duong
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserAdapterVH> {
    private List<User> userResponseList;
    private Context context;
    public UserAdapter() {

    }

    public void setData(List<User> userResponseList) {
        this.userResponseList = userResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserAdapter.UserAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new UserAdapter.UserAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_users,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterVH holder, int position) {
        User userResponse = userResponseList.get(position);
        holder.userName.setText(userResponse.getName());
        holder.userEmail.setText(userResponse.getEmailId());
        UserType userType = userResponse.getAccountType();
        if(userType == UserType.ADMIN) {
            holder.userAccountType.setText("ADMIN");
        }
        if(userType == UserType.BASIC) {
            holder.userAccountType.setText("BASIC");
        }
        if(userType == UserType.POSTER) {
            holder.userAccountType.setText("POSTER");
        }
    }

    @Override
    public int getItemCount() {
        return userResponseList.size();
    }

    public class UserAdapterVH extends RecyclerView.ViewHolder {
        TextView userName, userEmail, userAccountType;
        private UserAdapter adapter;
        public UserAdapterVH(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
            userAccountType = itemView.findViewById(R.id.accountType);

        }

        public UserAdapterVH linkAdapterUser(UserAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}