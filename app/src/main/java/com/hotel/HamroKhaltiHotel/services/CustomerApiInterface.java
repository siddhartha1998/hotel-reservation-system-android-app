package com.hotel.HamroKhaltiHotel.services;

import com.hotel.HamroKhaltiHotel.models.Customer;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerApiInterface {

    @POST("customer/saveCustomerDetails")
    Call<MessageResponse> addCustomerDetail(@Header("Authorization") String token, @Body Customer customer);

    @PUT("customer/updateCustomerDetail/{id}")
    Call<MessageResponse>EditCustomerDetail(@Header("Authorization") String token, @Path("id")Long id,@Body Customer customer);

    @GET("customer/getPersonalDataOfCustomer")
    Call<Customer>getMyDetail(@Header("Authorization") String token);
}
