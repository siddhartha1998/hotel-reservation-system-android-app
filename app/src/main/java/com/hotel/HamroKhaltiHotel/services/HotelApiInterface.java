package com.hotel.HamroKhaltiHotel.services;

import com.hotel.HamroKhaltiHotel.models.Hotel;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface HotelApiInterface {

    @GET("hotel/getHotelDetail")
    Call<List<Hotel>>getHotelDetail(@Header("Authorization") String token);
}
