package com.hotel.HamroKhaltiHotel.services;

import com.hotel.HamroKhaltiHotel.models.ChangePassword;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PasswordApiInterface {

    @PUT("ForgetPassword/changePassword/{username}")
    Call<MessageResponse>changePassword(@Header("Authorization") String token, @Path("username") String username,
    @Body ChangePassword changePassword);

    @POST("ForgetPassword/generateOtp")
    Call<MessageResponse> generateOtp(@Body User user);

    @POST("ForgetPassword/validateOtp/{username}")
    Call<MessageResponse> validateOtp(@Path("username") String username, @Query("otpnum") int otpnum);
}
