package com.example.airplanned.activity;

import static com.example.airplanned.api.ApiClientFactory.GetUserApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.airplanned.R;
import com.example.airplanned.api.ApiClientFactory;
import com.example.airplanned.model.GlobalVariables;
import com.example.airplanned.model.User;


import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class displays the homepage by initially welcoming the user back
 * then displaying most recent trip planned
 * also has a bottom navigation
 * and links to the profile page
 * @author Saiyara Iftekharuzzaman
 */
public class HomePageActivity extends AppCompatActivity {


    private TextView plannedFlightsTxt;
    private ImageView hp_iv_profile;
    private ImageView hp_iv_flight;
    private ImageView hp_iv_post;
    private ImageView hp_iv_archPost;
    private ImageView hp_iv_hotel;
    private ImageView hp_iv_trip;
    private ImageView hp_iv_vacaImage;
    private Button hp_btn_recVaca;
    private ImageView hp_btn_chat;
    private TextView hp_tv_recTitle;
    private ImageView hp_iv_happy;
    private ImageView hp_iv_sad;
    private ImageView hp_iv_neutral;
    String name;
    String email;
    String password;
    int id;


    /**
     * this method creates the home page and links layout to functionality
     *
     * @param savedInstanceState current saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        plannedFlightsTxt = findViewById(R.id.tv_homePage_RecentTrip);
        hp_iv_profile = findViewById(R.id.iv_homePage_profile);
        hp_iv_flight = findViewById(R.id.iv_homePage_flight);
        hp_iv_post = findViewById(R.id.iv_homePage_explore);
        hp_iv_archPost = findViewById(R.id.iv_homepage_archive);
        hp_iv_trip = findViewById(R.id.iv_homePage_planner);
        hp_iv_hotel = findViewById(R.id.iv_homePage_hotel);
        hp_iv_vacaImage = findViewById(R.id.iv_homePage_ReccomendedImage);
        hp_btn_recVaca = findViewById(R.id.btn_homePage_recPage);
        hp_tv_recTitle = findViewById(R.id.tv_homePage_ReccomendedTrip);
        hp_iv_happy = findViewById(R.id.iv_homepage_happy);
        hp_iv_sad = findViewById(R.id.iv_homepage_sad);
        hp_iv_neutral = findViewById(R.id.iv_homepage_neutral);
        hp_btn_chat = findViewById(R.id.btn_homePage_chat);


        /**
         * This is a GET call to get information of a user who logged in.
         * @author Julie Duong
         */
        id = getIntent().getIntExtra("emailid", 0);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Call<User> call = ApiClientFactory.GetUserApi().getUserbyEmail(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                name = user.getName();
                password = user.getPassword();
                id = user.getId();

                Toast.makeText(getApplicationContext(), "Welcome back " + name + " !", Toast.LENGTH_SHORT).show();

                GlobalVariables globalVariables = (GlobalVariables) getApplicationContext();
                globalVariables.setCurrent(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        /**
         * clicks happy
         */
        hp_iv_happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Thank you for using our app!", Toast.LENGTH_SHORT).show();

            }
        });

        /**
         * clicks sad
         */
        hp_iv_sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "We hope to improve your experience!", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * clicks a neutral
         */
        hp_iv_neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "It's the perfect time to book a trip!", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * sends to chat
         */
        hp_btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ChatPageActivity.class).putExtra("emailid", id));
            }
        });

        /**
         * button click to recommend a place to visit
         */
        hp_btn_recVaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hp_tv_recTitle.setText("");

                int[] images = {R.drawable.austrailia, R.drawable.greece, R.drawable.ireland, R.drawable.spain, R.drawable.mexico};

                String[] countries = {"Australia", "Greece", "Ireland", "Italy", "Mexico"};

                Random rand = new Random();
                int numb = rand.nextInt(images.length);

                hp_iv_vacaImage.setImageResource(images[numb]);
                hp_tv_recTitle.setText(countries[numb]);

            }
        });

        /**
         * button click sends to profile page
         */
        hp_iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(HomePageActivity.this, ProfilePageActivity.class);
                intent1.putExtra("email", email);
                intent1.putExtra("passwordToProfile", password);
                intent1.putExtra("name", name);
                intent1.putExtra("emailid", id);
                startActivity(intent1);

            }
        });

        /**
         * button click sends to post page
         */
        hp_iv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), PostPageActivity.class));
            }
        });

        /**
         * button click sends to flight page
         */
        hp_iv_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, FlightPageActivity.class).putExtra("emailid", id));
            }
        });

        /**
         * button click sends to archived post page
         */
        hp_iv_archPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ArchivedPostPageActivity.class));
            }
        });

        /**
         * button click sends to trip page
         */
        hp_iv_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, TripPlannedList.class);
                intent.putExtra("emailid", id);
                startActivity(intent);
            }
        });

        /**
         * button click sends to hotel page
         */
        hp_iv_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, LodgingPageActivity.class).putExtra("emailid", id));
            }
        });

    }

}
