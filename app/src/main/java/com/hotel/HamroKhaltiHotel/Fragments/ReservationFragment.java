package com.hotel.HamroKhaltiHotel.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hotel.HamroKhaltiHotel.Activities.LoginActivity;
import com.hotel.HamroKhaltiHotel.R;
import com.hotel.HamroKhaltiHotel.models.MessageResponse;
import com.hotel.HamroKhaltiHotel.models.Reservation;
import com.hotel.HamroKhaltiHotel.models.Room;
import com.hotel.HamroKhaltiHotel.utils.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ReservationFragment extends Fragment {

    private Room room;
    private EditText hotelName, roomNumber, checkInDate, checkOutDate, noOfGuest, idType, idNumber;
    private Button btnReserve;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation, container, false);

         hotelName = v.findViewById(R.id.reserveHotelname);
         roomNumber = v.findViewById(R.id.reserveRoomNumber);
         checkInDate = v.findViewById(R.id.reserveCheckInDate);
         checkOutDate = v.findViewById(R.id.reserveCheckOutDate);
         noOfGuest = v.findViewById(R.id.reserveNoOfGuest);
         idType = v.findViewById(R.id.reserveIdType);
         idNumber = v.findViewById(R.id.reserveIdNumber);
         btnReserve = v.findViewById(R.id.btnReserve);

          room = getArguments().getParcelable("data");

        Log.d(TAG, "onCreateView: Data: "+room.getHotel().getHotelName());

        hotelName.setText(room.getHotel().getHotelName());
        roomNumber.setText(room.getRoomNumber().toString());
        hotelName.setEnabled(false);
        roomNumber.setEnabled(false);

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inDate = checkInDate.getText().toString();
                String outDate = checkOutDate.getText().toString();
                String guest = noOfGuest.getText().toString();
                String typeOfId = idType.getText().toString();
                String idNum = idNumber.getText().toString();

                Reservation reservation = new Reservation();
                reservation.setHotelName(room.getHotel().getHotelName());
                reservation.setRoomNumber(room.getRoomNumber().toString());
                reservation.setCheckInDate(inDate);
                reservation.setCheckOutDate(outDate);
                reservation.setNoOfGuest(guest);
                reservation.setIdType(typeOfId);
                reservation.setIdNumber(idNum);

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token","");

                Long hotelId = room.getHotel().getId();
                Long roomId  = room.getId();

                Call<MessageResponse> call = ApiClient.getReservationServices().reserveRoom(token,hotelId,roomId, reservation);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getMessage().equals("Room Reserved successfully!")){
                               Toast.makeText(getContext(),"Room Reserve Successfully!", Toast.LENGTH_SHORT).show();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HotelFragment()).addToBackStack(null).commit();
                                Log.d(TAG, "onResponse: Room reserved successfully");
                            }else{
                                Log.d(TAG, "onResponse: something is wrong "+response.body().getMessage());
                            }
                        }else{
                            Log.d(TAG, "onResponse: Something is wrong "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: Sorry cannot connect to the server "+t.getMessage());
                    }
                });


            }
        });
        
        return v;
    }
}