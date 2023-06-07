package com.example.airplanned.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.airplanned.R;
import com.example.airplanned.adapter.UserAdapter;
import com.example.airplanned.api.ApiClientFactory;
import com.example.airplanned.model.Trip;
import com.example.airplanned.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * This page is to let the user look for his/her contact.
 * @author Julie Duong
 */
public class FriendListPageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list_page);
        recyclerView = findViewById(R.id.listRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new UserAdapter();
        getAllUser();
    }

    /**
     * This method is to show all the users registered.
     */
    public void getAllUser() {
        Call<List<User>> call = ApiClientFactory.GetUserApi().getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                    List<User> users = response.body();
                    adapter.setData(users);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

}