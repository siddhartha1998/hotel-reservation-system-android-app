package com.hotel.HamroKhaltiHotel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.models.User;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    private static final String TAG = "siddhi";
    EditText username,email;
    Button btnSubmit;
    TextView gotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        username = findViewById(R.id.forgotUsername);
        email = findViewById(R.id.forgotEmail);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        gotoLogin = (TextView) findViewById(R.id.gotoLogin);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textUsername = username.getText().toString().trim();
                String textEmail = email.getText().toString().trim();

                if(TextUtils.isEmpty(textUsername)){
                    username.setError("Username cannot be empty");
                    return;
                }
                if(TextUtils.isEmpty(textEmail)){
                    email.setError("Email cannot be empty");
                    return;
                }

                User user = new User();
                user.setUsername(textUsername);
                user.setEmail(textEmail);
                //Log.d(TAG, "onClick:Forgot button clicked");

                Call<MessageResponse>call = ApiClient.getPasswordServices().generateOtp(user);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getMessage().equals("Otp is generated check your email")) {
                                Intent intent = new Intent(getApplicationContext(), OTP.class);
                                intent.putExtra("username", textUsername);
                                startActivity(intent);
                            }
                            else {
                                    Log.d(TAG, "onResponse: Something is wrong "+response.body().getMessage());

                            }
                        }
                        else{
                            Log.d(TAG, "onResponse: Something is wrong "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Log.d(TAG, "onResponse: sorry cannot connect to the server "+t.getMessage());

                    }
                });
            }
        });
    }
}