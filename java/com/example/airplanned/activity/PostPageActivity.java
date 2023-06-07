package com.example.airplanned.activity;

import static com.example.airplanned.api.ApiClientFactory.GetPostApi;
import static com.example.airplanned.api.ApiClientFactory.GetUserApi;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.airplanned.R;
import com.example.airplanned.adapter.PostAdapter;
import com.example.airplanned.model.GlobalVariables;
import com.example.airplanned.model.Post;
import com.example.airplanned.model.User;
import com.example.airplanned.model.UserType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The purpose of this activity to show the user other user's and their own posts
 * allows the user to interact with posts through likes and comments
 * @author Saiyara Iftekharuzzaman
 */

public class PostPageActivity extends AppCompatActivity {
    //for post page layout
    ImageView addPost;

    //this is for the pop up window to add a post
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newPost_title, newPost_description;
    private ImageView newPost_Image;
    private Button newPost_post, newPost_addImage, newPost_cancel;

    //post item
    ImageView backbtn;

    //refresh button
    ImageView refreshPost;


    ListView listView;
    PostAdapter adapter;


    ArrayList<Post> postsArrayList = new ArrayList<Post>();

    /**
     * this method sets up the post page activity and connects the layout with functionality
     *
     * @param savedInstanceState current instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);


        listView = findViewById(R.id.tv_postPage_postDisplay);
        backbtn = findViewById(R.id.iv_postPage_back);
        addPost = findViewById(R.id.iv_postPage_add);
        refreshPost = findViewById(R.id.iv_postPage_refresh);


        displayPosts();


        //back button
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), HomePageActivity.class));
            }
        });


        //adds pop-up window
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create the pop up
                createNewPostDialog();
            }
        });

        refreshPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPosts();
            }
        });
    }


    /**
     * this method creates a pop up window to allow the user to make a post
     * takes the user input and then makes a post object and adds it to the list
     * through a retrofit call
     */
    public void createNewPostDialog() {

        //send emailId
        Intent intent = getIntent();
        String emailFromLogin;
        if (intent.getStringExtra("email_key") == null) {
            emailFromLogin = intent.getStringExtra("email_key1");
        } else {
            emailFromLogin = intent.getStringExtra("email_key");
        }

        dialogBuilder = new AlertDialog.Builder(this);
        final View postPopupView = getLayoutInflater().inflate(R.layout.popup_add_post, null);
        newPost_title = (EditText) postPopupView.findViewById(R.id.et_pg_title);
        newPost_description = (EditText) postPopupView.findViewById(R.id.et_pg_description);
        //newPost_Image = (ImageView) postPopupView.findViewById(R.id.iv_pg_image);
        newPost_cancel = (Button) postPopupView.findViewById(R.id.btn_pg_cancel);
        newPost_addImage = (Button) postPopupView.findViewById(R.id.btn_pg_addImage);
        newPost_post = (Button) postPopupView.findViewById(R.id.btn_pg_post);

        dialogBuilder.setView(postPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newPost_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalVariables globalVariables = (GlobalVariables) getApplicationContext();
                System.out.print(globalVariables.getCurrent());
                Post nPost = new Post(newPost_title.getText().toString(), newPost_description.getText().toString(), "12/5/21" ,globalVariables.getCurrent());


                postCreateUser(nPost,globalVariables.getCurrent().getId());

                dialog.dismiss();
            }
        });


        newPost_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //be able to add an image from user gallery (phone) and then set the
                //layout box to that image to then send it to the newly created post




                Toast.makeText(getApplicationContext(), "Image added", Toast.LENGTH_LONG).show();
                //newPost_Image.setImageResource();
            }
        });

        newPost_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    /**
     * this method creates the options menu and allows the user to search through it
     *
     * @param pmenu the current instance of the menu
     * @return boolean if the character exists in the list
     */
    @Override
    public boolean onCreateOptionsMenu(Menu pmenu) {
        getMenuInflater().inflate(R.menu.postmenu, pmenu);
        MenuItem myActionMenuItem = pmenu.findItem(R.id.action_psearch);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    adapter.pfilter("");
                    listView.clearTextFilter();
                } else {
                    adapter.pfilter(s);
                }
                return true;
            }
        });
        return true;
    }

    /**
     * this method tracks if something is clicked on the options menu
     *
     * @param item item in the layout which is clicked
     * @return boolean of if an item is selected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Toast.makeText(getApplicationContext(), "id:  " + id, Toast.LENGTH_SHORT).show();

        if (id == R.id.action_psearch) {
            //do your functionality here
            //String title = postsArrayList.get(id).getPostTitle();
            Toast.makeText(getApplicationContext(), "Post selected:  " + item.getTitle(), Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * this method is retrofit call to display all the posts in the database
     * it will convert to post objects then add to a list to display the posts
     */
    public void displayPosts() {

        GetPostApi().getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                System.out.print(postsArrayList);
                postsArrayList = (ArrayList<Post>) response.body();
                adapter = new PostAdapter(getApplicationContext(), postsArrayList,0);

                //bind the adapter to the listview
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure:" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
        );

    }

    public void createP(Post nPost){
        GetPostApi().createPost(nPost).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                if (response.body() == 1) {
                    Toast.makeText(getApplicationContext(), "New post created", Toast.LENGTH_LONG).show();
                } else if (response.body() == 0) {
                    Toast.makeText(getApplicationContext(), "response body is : 0", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed post", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void postCreateUser(Post nPost, int a){
        GetPostApi().createPostUser(nPost,a).enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                System.out.print(response.body());
                if (response.body() == 1) {
                    Toast.makeText(getApplicationContext(), "New post created", Toast.LENGTH_LONG).show();
                } else if (response.body() == 0) {
                    Toast.makeText(getApplicationContext(), "response body is : 0", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                System.out.print(t.getMessage());
                Toast.makeText(getApplicationContext(), "failed post" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}