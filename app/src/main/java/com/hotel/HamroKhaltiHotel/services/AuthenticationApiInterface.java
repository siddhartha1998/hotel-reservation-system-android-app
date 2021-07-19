package com.hotel.HamroKhaltiHotel.services;

import com.hotel.HamroKhaltiHotel.models.LoginRequest;
import com.hotel.HamroKhaltiHotel.models.LoginResponse;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApiInterface {
    @POST("auth/signin")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("auth/signup")
    Call<MessageResponse> addUser(@Body User user);
}
