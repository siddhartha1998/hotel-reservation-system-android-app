package com.hotel.HamroKhaltiHotel.services;

import com.hotel.HamroKhaltiHotel.models.Customer;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.models.Reservation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReservationApiInterface {

    @POST("temporaryReservation/newTemporaryReservation/hotel/{hotelId}/room/{roomId}")
    Call<MessageResponse> reserveRoom(@Header("Authorization")String token,@Path("hotelId")Long hotelId, @Path("roomId")Long roomId, @Body Reservation reservation);
}
