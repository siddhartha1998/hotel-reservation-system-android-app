package com.hotel.HamroKhaltiHotel.services;

import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.models.ProfilePicture;
import com.hotel.HamroKhaltiHotel.models.Room;
import com.hotel.HamroKhaltiHotel.models.RoomPicture;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RoomApiInterface {

    @GET("room/getNonReservedRoomOfMyHotel/{hotelId}")
    Call<List<Room>> getRoomDetail(@Header("Authorization") String token, @Path("hotelId") Long hotelId);

    @GET("downloadPicture/downloadRoomFile/{fileName}")
    Call<RoomPicture> getProfilePictureHotel(@Path("fileName") String fileName);
    @GET("roomPicture/getRoomPictureLink/{id}")
    Call<List<RoomPicture>> getRoomProfilePicture(@Header("Authorization") String token, @Path("id") Long id);
}
