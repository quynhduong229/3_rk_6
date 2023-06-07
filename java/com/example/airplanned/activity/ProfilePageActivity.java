package com.example.airplanned.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airplanned.R;
import com.example.airplanned.api.ApiClientFactory;
import com.example.airplanned.model.User;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is to show user's profile page
 * includes their name, email and password.
 * The users can also update their password.
 * We can also log out and delete account in this page.
 *
 * @author Julie Duong
 */

public class ProfilePageActivity extends AppCompatActivity {
    TextView userEmail, passwordUser, userName;
    TextView friendList;
    ImageView backButton;
    EditText passwordEdit;
    String email;
    String password;
    Button deleteAccount, logout;
    String nameUser;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        logout = findViewById(R.id.logout);
        friendList = findViewById(R.id.friendList);
        backButton = findViewById(R.id.backButton);
        userEmail = findViewById(R.id.email_user);
        userName = findViewById(R.id.nameUser);
        passwordUser = findViewById(R.id.user_password);
        deleteAccount = findViewById(R.id.deleteAccount);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProfilePageActivity.this, HomePageActivity.class);

                startActivity(intent1);
            }
        });


        /**
         * log out method
         */
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePageActivity.this, LoginPageActivity.class));
            }
        });


        /**
         * delete account method
         */
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ProfilePageActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.deleteaccount);
                Button delete = dialog.findViewById(R.id.yesdelete);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<Integer> call = ApiClientFactory.GetUserApi().deleteAccount(email);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                startActivity(new Intent(ProfilePageActivity.this, LoginPageActivity.class));
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        /**
         * update password method
         */
        passwordUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(ProfilePageActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.activity_update_password))
                        .setExpanded(true, 1300)
                        .create();
                dialog.show();
                passwordEdit = findViewById(R.id.passEditBox);
                Button updateButton = findViewById(R.id.passEditUpdate);
                passwordEdit.setText(passwordUser.getText().toString());

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String update = passwordEdit.getText().toString();
                        Call<Integer> call = ApiClientFactory.GetUserApi().updatePassword(email, update);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                System.out.print(response.body());
                                if(response.isSuccessful()) {
                                    passwordUser.setText(update);
                                    dialog.dismiss();
                                    Toast.makeText(ProfilePageActivity.this, "Updated Sucessfully", Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
        });


        friendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePageActivity.this, FriendListPageActivity.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePageActivity.this, HomePageActivity.class).putExtra("email",email));
            }
        });

        id = getIntent().getIntExtra("emailid", 0);
        nameUser = getIntent().getStringExtra("name");
        userName.setText(nameUser);
        email = getIntent().getStringExtra("email");
        userEmail.setText(email);
        password = getIntent().getStringExtra("passwordToProfile");
        passwordUser.setText(password);
    }
}
