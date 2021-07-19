package com.hotel.HamroKhaltiHotel.services;

import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EmailVerificationApiInterface {

    @POST("email/generateOtp/{username}")
    Call<MessageResponse> generateOtp(@Body User user);

    @POST("email/emailVerification/{username}")
    Call<MessageResponse> verifyEmail(@Path("username") String username, @Query("otpnum") int otpnum);

}
