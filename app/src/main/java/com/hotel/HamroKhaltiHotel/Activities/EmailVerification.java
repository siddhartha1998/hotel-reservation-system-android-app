package com.hotel.HamroKhaltiHotel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailVerification extends AppCompatActivity {
    private static final String TAG = "siddhi";
    private  String username;
    private Button  btnVerify;
    private EditText otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        btnVerify = (Button) findViewById(R.id.btnOTPSubmit);
        otp = findViewById(R.id.inputOTP);
        username = getIntent().getStringExtra("username");

        Log.d(TAG, "onCreate: Username "+username);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OTP = otp.getText().toString().trim();

                if (TextUtils.isEmpty(OTP)) {
                    otp.setError("Otp cannot be empty");
                    return;
                }
                if (OTP.length() > 6 || OTP.length() < 6) {
                    otp.setError("Otp should be of 6 characters long");
                    return;
                }



               int emailVerifiedOtp = Integer.parseInt(OTP);
               Call<MessageResponse>call1 = ApiClient.getEmailVerificationServices().verifyEmail(username, emailVerifiedOtp);
                call1.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getMessage().equals("Email verified you can log into the system now.")){

                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                        else {
                            Log.d(TAG, "onResponse: Sorry something is wrong " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: Sorry cannot connect to the server "+t.getMessage());
                    }
                });
            }
        });
    }}