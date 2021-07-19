package com.hotel.HamroKhaltiHotel.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hotel.HamroKhaltiHotel.services.AuthenticationApiInterface;
import com.hotel.HamroKhaltiHotel.services.CustomerApiInterface;
import com.hotel.HamroKhaltiHotel.services.EmailVerificationApiInterface;
import com.hotel.HamroKhaltiHotel.services.HotelApiInterface;
import com.hotel.HamroKhaltiHotel.services.PasswordApiInterface;
import com.hotel.HamroKhaltiHotel.services.ProfilePictureApiInterface;
import com.hotel.HamroKhaltiHotel.services.ReservationApiInterface;
import com.hotel.HamroKhaltiHotel.services.RoomApiInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit getRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                // .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://192.168.137.57:8080/api/")
                .client(okHttpClient)
                .build();
        return retrofit;
    }
    public static AuthenticationApiInterface getUserService() {
        AuthenticationApiInterface userService = getRetrofit().create(AuthenticationApiInterface.class);
        return userService;
    }

    public static AuthenticationApiInterface getUserRegistrationService(){
        AuthenticationApiInterface userRegisterService=getRetrofit().create(AuthenticationApiInterface.class);
        return userRegisterService;
    }

    public static PasswordApiInterface getPasswordServices(){
        PasswordApiInterface passwordApiInterface=getRetrofit().create(PasswordApiInterface.class);
        return passwordApiInterface;
    }

    public static EmailVerificationApiInterface getEmailVerificationServices(){
        EmailVerificationApiInterface emailVerificationApiInterface = getRetrofit().create(EmailVerificationApiInterface.class);
        return emailVerificationApiInterface;
    }

    public static HotelApiInterface getHotelServices(){
        HotelApiInterface hotelApiInterface = getRetrofit().create(HotelApiInterface.class);
        return hotelApiInterface;
    }

    public static RoomApiInterface getRoomServices(){
        RoomApiInterface roomApiInterface = getRetrofit().create(RoomApiInterface.class);
        return roomApiInterface;
    }

    public static ReservationApiInterface getReservationServices(){
        ReservationApiInterface reservationApiInterface = getRetrofit().create(ReservationApiInterface.class);
        return reservationApiInterface;
    }

    public static CustomerApiInterface getCustomerServices(){
        CustomerApiInterface customerApiInterface = getRetrofit().create(CustomerApiInterface.class);
        return  customerApiInterface;
    }

    public static ProfilePictureApiInterface getProfileService(){
        ProfilePictureApiInterface profilePictureApiInterface = getRetrofit().create(ProfilePictureApiInterface.class);
        return profilePictureApiInterface;
    }

}
