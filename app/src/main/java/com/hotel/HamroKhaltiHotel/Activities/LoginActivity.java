package com.hotel.HamroKhaltiHotel.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.LoginRequest;
import com.hotel.HamroKhaltiHotel.models.LoginResponse;
import com.hotel.HamroKhaltiHotel.services.AuthenticationApiInterface;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

EditText username,password;
Button btnLogin;
TextView forgotPassword;
TextView registerNow;
AuthenticationApiInterface authService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerNow = (TextView) findViewById(R.id.registerNow);
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword) ;
        btnLogin = (Button)findViewById(R.id.btnRegister);
        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        String savedToken = sharedPreferences.getString("token","");
        if(!savedToken.equals("")){
            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String textUsername = username.getText().toString().trim();
                String textPassword = password.getText().toString().trim();

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUsername(textUsername);
                loginRequest.setPassword(textPassword);
                Call<LoginResponse> call = ApiClient.getUserService().userLogin(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        System.out.println(response.code());
                        if(response.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            String token = response.body().getToken();

                            SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", "Bearer "+token);
                            editor.commit();

                                Intent intent =new Intent(LoginActivity.this, StartActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Your credentials did not matched", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "you could not connect to the server sorry."+ t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
}