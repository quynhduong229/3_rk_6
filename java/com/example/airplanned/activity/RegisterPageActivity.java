package com.example.airplanned.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.airplanned.R;
import com.example.airplanned.api.ApiClientFactory;
import com.example.airplanned.model.User;
import com.example.airplanned.model.UserType;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The purpose of this activity is to allow the user to create an account
 * by inputting their email, name, password, and account type
 * @author Julie Duong and Saiyara Iftekharuzzaman
 */

public class RegisterPageActivity extends AppCompatActivity{

    private EditText usernameInput;
    private EditText email;
    private EditText password;
    private EditText accountType;
    private Button register;
    private Button returnToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        usernameInput = findViewById(R.id.til_registerPage_username);
        email = findViewById(R.id.til_registerPage_fullname);
        password = findViewById(R.id.til_registerPage_password);
        accountType = findViewById(R.id.til_registerPage_confirmPassword);
        register = findViewById(R.id.btn_registerPage_register);
        returnToLogin = findViewById(R.id.et_registerPage_returnLogin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()) {
                    email.setError("Full name is empty");
                    email.requestFocus();
                    return;
                }

                if (usernameInput.getText().toString().isEmpty()) {
                    usernameInput.setError("Full name is empty");
                    usernameInput.requestFocus();
                    return;
                }

                if (password.getText().toString().isEmpty()) {
                    password.setError("Password required");
                    password.requestFocus();
                    return;
                }

                if (password.getText().toString().length() < 6) {
                    password.setError("Passwords must at least 6 characters long");
                    password.requestFocus();
                    return;
                }

                //then allows the user to send it to the server

                else {
                    User user = new User();
                    user.setName(usernameInput.getText().toString());
                    user.setEmailId(email.getText().toString());
                    user.setPassword(password.getText().toString());
                    if(accountType.getText().toString().equalsIgnoreCase("basic")){
                        user.setAccountType(UserType.BASIC);
                    }else if(accountType.getText().toString().equalsIgnoreCase("admin")) {
                        user.setAccountType(UserType.ADMIN);
                    }else if(accountType.getText().toString().equalsIgnoreCase("poster")) {
                        user.setAccountType(UserType.POSTER);
                    }

                    Call<Integer> call = ApiClientFactory.GetUserApi().createUser(user);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if(response.isSuccessful()) {
                                startActivity(new Intent(RegisterPageActivity.this, LoginPageActivity.class));
                            }
                        }
                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });

                };
            }
        });

        //sends back to login screen
        returnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPageActivity.this, LoginPageActivity.class));
            }
        });
    }

}
