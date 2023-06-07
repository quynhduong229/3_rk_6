package com.example.airplanned.activity;

import static com.example.airplanned.api.ApiClientFactory.GetPostApi;
import static com.example.airplanned.api.ApiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airplanned.R;
import com.example.airplanned.adapter.PostAdapter;
import com.example.airplanned.api.SlimCallback;
import com.example.airplanned.model.GlobalVariables;
import com.example.airplanned.model.Post;
import com.example.airplanned.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchivedPostPageActivity extends AppCompatActivity {

    ListView listView;
    PostAdapter adapter;
    ArrayList<Post> yourPosts = new ArrayList<>();
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivedposts_page);

        listView = findViewById(R.id.lv_archPage_postDisplay);
        back = findViewById(R.id.iv_archPage_back);





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),HomePageActivity.class));
            }
        });

        GlobalVariables globalVariables = (GlobalVariables) getApplicationContext();


        GetPostApi().getPostbyUser(globalVariables.getCurrent().getEmailId()).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                yourPosts= (ArrayList<Post>) response.body();
                adapter = new PostAdapter(getApplicationContext(), yourPosts,1);
                //bind the adapter to the listview
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure:" + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );

    }
    }

