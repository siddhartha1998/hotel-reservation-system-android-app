package com.hotel.HamroKhaltiHotel.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.models.User;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    TextView goToLogin;
    private Button btnRegister;
    private EditText inputUsername, inputEmail, inputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnRegister = findViewById(R.id.btnRegister);
        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        goToLogin = (TextView)findViewById(R.id.gotoLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = inputUsername.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);

                Call<MessageResponse> call = ApiClient.getUserService().addUser(user);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if(response.body().getMessage().equals("New User Registered Successfully")) {
                                Intent intent = new Intent(SignUpActivity.this, EmailVerification.class);
                                intent.putExtra("username",username);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this, "Some error has occurred", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignUpActivity.this, "Something is wrong "+response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, "Sorry cannot connect to the server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}