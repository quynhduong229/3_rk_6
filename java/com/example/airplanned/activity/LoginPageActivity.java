package com.example.airplanned.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.airplanned.R;
import com.example.airplanned.api.ApiClientFactory;
import com.example.airplanned.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The purpose of this activity is to allow the user to login by inputting
 * their email and password, it allows links to the register page in case it is a new user
 * @author Saiyara Iftekharuzzaman and Julie Duong
 */

public class LoginPageActivity extends AppCompatActivity {
    private TextView etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.et_LoginScreen_Username);
        etPassword = findViewById(R.id.et_LoginScreen_Password);
        btnLogin = findViewById(R.id.btn_LoginScreen_Login);
        btnRegister = findViewById(R.id.btn_LoginScreen_Register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                    Toast.makeText(LoginPageActivity.this, "Email/ PassWord required", Toast.LENGTH_LONG).show();
                }else {
                    logIn();

                }
            }
        });

        //sends to register screen
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),RegisterPageActivity.class));
            }
        });
    }

    public void logIn() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        Call<Integer> call = ApiClientFactory.GetUserApi().logInUser(email, password);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == 1) {
                    Toast.makeText(LoginPageActivity.this, "Login successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginPageActivity.this, HomePageActivity.class).putExtra("email", email));
                }
                Toast.makeText(LoginPageActivity.this, "Email/Password is wrong", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(LoginPageActivity.this, "Login fails", Toast.LENGTH_LONG).show();
            }
        });
    }


}
