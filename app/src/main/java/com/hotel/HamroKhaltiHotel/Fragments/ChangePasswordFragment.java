package com.hotel.HamroKhaltiHotel.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.hotel.HamroKhaltiHotel.Activities.LoginActivity;
import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.ChangePassword;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    private static final String TAG = "Siddhi";
    private TextInputLayout etNewPassword, etConfirmPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private MaterialButton btnChangePassword;
    private String token,username;
    private Long userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.change_password, container, false);

        newPassword = v.findViewById(R.id.newPassword);
        confirmPassword = v.findViewById(R.id.confirmPassword);
        btnChangePassword = v.findViewById(R.id.btnChangePassword);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        username = sharedPreferences.getString("username", "");
//        userId = sharedPreferences.getLong("id", 0);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nPassword = newPassword.getText().toString();
                String cPassword = confirmPassword.getText().toString();
                Log.d(TAG, "onCreateView: New Password " + newPassword);
                Log.d(TAG, "onCreateView: confirm password " + confirmPassword);

                if (TextUtils.isEmpty(nPassword)) {
                    newPassword.setError("New Password cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(cPassword)) {
                    confirmPassword.setError("Confirm Password cannot be empty");
                    return;
                }

                if(nPassword.equals(cPassword)) {
                    ChangePassword changePassword = new ChangePassword();
                    changePassword.setNewPassword(nPassword);
                    changePassword.setConfirmPassword(cPassword);
                    changePassword.setUsername(username);


                    Call<MessageResponse> call = ApiClient.getPasswordServices().changePassword(token, username, changePassword);
                    call.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getMessage().equals("Password Changed Successfully")) {
                                    Log.d(TAG, "onResponse: Password changed");
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().clear().commit();
                                    Intent intoLogin = new Intent(getContext(), LoginActivity.class);
                                    startActivity(intoLogin);

                                } else {
                                    Log.d(TAG, "onResponse: Something is wrong " + response.code());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Log.d(TAG, "onFailure: Sorry cannot connect to the server");
                        }
                    });
                }else{
                    confirmPassword.setError("New Password and Confirm Password should be same");
                    return;
                }


            }
        });


        return v;
    }
}
