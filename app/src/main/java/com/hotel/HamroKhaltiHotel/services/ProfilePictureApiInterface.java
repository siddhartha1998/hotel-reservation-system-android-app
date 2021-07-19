package com.hotel.HamroKhaltiHotel.services;

import com.hotel.HamroKhaltiHotel.models.ProfilePicture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProfilePictureApiInterface {
/*
    @GET("profilePicture/")*/

    @GET("downloadPicture/downloadFile/{fileName}")
    Call<ProfilePicture> getProfilePictureHotel(@Path("fileName") String fileName);
    @GET("profilePicture/getProfilePictureLink/{id}")
    Call<ProfilePicture> getHotelProfilePicture(@Header("Authorization") String token, @Path("id") Long id);
}
